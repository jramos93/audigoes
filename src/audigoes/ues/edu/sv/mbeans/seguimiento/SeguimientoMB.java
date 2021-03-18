package audigoes.ues.edu.sv.mbeans.seguimiento;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.mail.Address;
import javax.mail.internet.InternetAddress;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.administracion.Usuario;
import audigoes.ues.edu.sv.entities.ejecucion.ComentarioHallazgo;
import audigoes.ues.edu.sv.entities.informe.CedulaNota;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;
import audigoes.ues.edu.sv.entities.planeacion.AuditoriaResponsable;
import audigoes.ues.edu.sv.entities.seguimiento.Comentario;
import audigoes.ues.edu.sv.entities.seguimiento.Recomendacion;
import audigoes.ues.edu.sv.entities.seguimiento.Seguimiento;
import audigoes.ues.edu.sv.mbeans.ejecucion.CedulaMB;
import audigoes.ues.edu.sv.util.SendMailAttach;

@ManagedBean(name = "segMB")
@ViewScoped
public class SeguimientoMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Usuario> auditoresList;
	private Usuario coordinador;
	private Auditoria auditoria;
	private String textoCorreo = "";

	private List<Seguimiento> filteredSeguimientos;

	private Seguimiento seguimientoSelected;
	private Recomendacion recomendacionSelected;
	private CedulaNota cedulaSelected;
	private List<Recomendacion> recomendacionesSeguimientoList;
	private List<CedulaNota> cedulaNotaList;
	private List<Comentario> comentariosSeguimientoList;
	private Comentario comentarioRecomendacion;

	@ManagedProperty(value = "#{recMB}")
	private RecomendacionMB recMB = new RecomendacionMB();

	@ManagedProperty(value = "#{comMB}")
	private ComentarioMB comMB = new ComentarioMB();

	@ManagedProperty(value = "#{ceduMB}")
	private CedulaMB ceduMB = new CedulaMB();

	@ManagedProperty(value = "#{informeSegMB}")
	private InformeSeguimientoMB informeSegMB = new InformeSeguimientoMB();

	public SeguimientoMB() {
		super(new Seguimiento());
	}

	@PostConstruct
	public void init() {
		try {
			configBean();
			Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			setAuditoria((Auditoria) sessionMap.get("auditoria"));

			// obtenerRecomendaciones();
			obtenerCoordinador();
			obtenerSeguimiento();
			obtenerCedulaNotaList();

			if (getAuditoria() != null) {
				if (getRegistro().getSegEstado() == 1) {
					informeSegMB.setSeguimiento(getRegistro());
					informeSegMB.onInforme();
				} else {
					System.out.println("No hay para informe");
				}
			}

			super.init();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void mostrarComentarios() {
		obtenerRecomendaciones();
		recMB.setSeguimiento(getRegistro());
		recMB.mostrarComentarios();
		comMB.setSeguimiento(getRegistro());
		comMB.setCedula(getCedulaSelected());
		comMB.obtenerComentario();
	}

	public void obtenerRecomendaciones() {
		if (getAuditoria() != null) {
			getRecMB().setAuditoria(getAuditoria());
			getRecMB().setCedula(getCedulaSelected());
			getRecMB().fillRecomendacionesCedulaNota();
		}
	}

	@SuppressWarnings("unchecked")
	public void obtenerCedulaNotaList() {
		try {
			System.out.println("obtenerCedulaNotaList");
			if (getAuditoria() != null) {
				ceduMB.setAuditoria(getAuditoria());
				if (getStatus().equals("NEW")) {
					System.out.println("new");
					ceduMB.setListado((List<CedulaNota>) audigoesLocal.findByNamedQuery(CedulaNota.class,
							"cedula.nota.por.auditoria", new Object[] { getAuditoria().getAudId() }));
				}
				if (getStatus().equals("EDIT")) {
					System.out.println("edit");
					ceduMB.setListado((List<CedulaNota>) audigoesLocal.findByNamedQuery(CedulaNota.class,
							"cedula.nota.seguimiento.por.auditoria",
							new Object[] { getAuditoria().getAudId(), getRegistro().getSegId() }));
				}
			} else {
				System.out.println("no hay auditoria");
			}

		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Problemas al obtener hallazgos de auditor�a"));
		}
	}

	public void onCedulaSelected() {
		try {
			getCedulaSelected().setSelected(true);
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Hubo un problema con la selecci�n del hallazgo"));
		}
	}

	@SuppressWarnings("unchecked")
	public void obtenerCoordinador() {
		try {
			if (getAuditoria() != null) {
				setAuditoresList((List<Usuario>) audigoesLocal.findByNamedQuery(Usuario.class,
						"seguimiento.auditores.asignados", new Object[] { getAuditoria().getAudId() }));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void obtenerSeguimiento() {
		try {
			if (getAuditoria() != null) {
				System.out.println(" " + getAuditoria().getAudId());
				setListado((List<Seguimiento>) audigoesLocal.findByNamedQuery(Seguimiento.class,
						"seguimiento.get.by.auditoria", new Object[] { getAuditoria().getAudId() }));
				if (!getListado().isEmpty()) {
					setRegistro(getListado().get(getListado().size() - 1));
					onEdit();
					if (getRegistro().getSegFecFin() != null) {
						onNew();
					}
				} else {
					System.out.println("No hay seguimiento realizado");
					onNew();
				}

				setListado((List<Seguimiento>) audigoesLocal.findByNamedQuery(Seguimiento.class,
						"historial.seguimiento.get.by.auditoria", new Object[] { getAuditoria().getAudId() }));
			}
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Problemas al obtener seguimiento de auditor�a"));
		}
	}

	public void onRowSeguimientoSelected() {
		seguimientoSelected.setSelected(true);
	}

	@SuppressWarnings("unchecked")
	public void obtenerRecomendacionesSeguimiento() {
		/* Mostrar recomendaciones de seguimiento seleccionado */
		// ssetStatus("RECOMENDACIONES_SEGUIMIENTO");
		try {
			setRecomendacionesSeguimientoList((List<Recomendacion>) audigoesLocal.findByNamedQuery(Recomendacion.class,
					"seguimiento.recomendaciones.seguimiento.selected",
					new Object[] { seguimientoSelected.getSegId() }));
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Problemas al obtener recomendaciones del seguimiento seleccionado"));
		}
	}

	@SuppressWarnings("unchecked")
	public void obtenerComentariosSeguimiento() {
		/* Mostrar comentarios de seguimiento seleccionado */
		// setStatus("COMENTARIOS_SEGUIMIENTO_HIST");
		try {
			setComentariosSeguimientoList((List<Comentario>) audigoesLocal.findByNamedQuery(Comentario.class,
					"comentarios.seguimiento.historial", new Object[] { getSeguimientoSelected().getSegId() }));
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Problemas al obtener comentarios del seguimiento seleccionado"));
		}
	}

	public void mostrarComentariosHistorial() {
		recMB.setSeguimiento(getSeguimientoSelected());
		recMB.setRegistro(getRecomendacionSelected());
		recMB.mostrarComentarios();
	}

	public void preparaCorreoInicioSeguimiento() {
		textoCorreo = "<p><strong>AUDIGOES LE INFORMA:</strong></p>"
				+ "<p>Se notifica que se ha dado por iniciado el seguimiento "
				+ "correspondiente a la auditor&iacute;a <strong>" + getAuditoria().getTipoAuditoria().getTpaAcronimo()
				+ "-" + getAuditoria().getAudAnio() + "-" + getAuditoria().getAudCorrelativo()
				+ "</strong> denominado <strong>" + getAuditoria().getAudNombre()
				+ " </strong> por lo que se le pide ingresar al sistema para realizar los comentarios correspondientes.</p>\r\n"
				+ "<p>Atte.-</p>";
	}

	public void onEnviarCorreoInicioSeguimiento() {
		Usuario usr = buscarCoordinador(getAuditoria());
		if (usr != null) {
			try {
				/*
				 * getRegistro().setCedEstado(2); onSave(); if (!correoRevision(textoCorreo,
				 * usr)) { addWarn(new FacesMessage("Error en el envio del correo")); }
				 * revisarPermisos();
				 */
				setStatus("VIEW");
			} catch (Exception e) {
				e.printStackTrace();
				addWarn(new FacesMessage("Error al enviar a revisi�n"));
			}
		}
	}

	@SuppressWarnings("unchecked")
	public Usuario buscarCoordinador(Auditoria auditoria) {
		Usuario usuario = null;
		try {
			List<AuditoriaResponsable> responsables = (List<AuditoriaResponsable>) audigoesLocal.findByNamedQuery(
					AuditoriaResponsable.class, "find.coordinador.by.auditoria", new Object[] { auditoria.getAudId() });
			if (!responsables.isEmpty()) {
				usuario = responsables.get(0).getUsuario();
				return usuario;
			} else {
				addWarn(new FacesMessage("Error, la auditoria no cuenta con un coordinador asignado"));
				return usuario;
			}
		} catch (Exception e) {
			addWarn(new FacesMessage("Error al identificar  el coordinador de la auditoria"));
			return usuario;
		}
	}

	public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
		if (filterText == null || filterText.equals("")) {
			return true;
		}
		int filterInt = getInteger(filterText);

		Auditoria auditoria = (Auditoria) value;
		return auditoria.getAudNombre().toLowerCase().contains(filterText)
				|| auditoria.getAudDescripcion().toLowerCase().contains(filterText)
				|| auditoria.getAudId() == filterInt;
	}

	private int getInteger(String string) {
		try {
			return Integer.valueOf(string);
		} catch (NumberFormatException ex) {
			return 0;
		}
	}

	public void iniciarSeguimiento() {
		try {
			if (getStatus().equals("NEW")) {
				getRegistro().setAuditoria(getAuditoria());
				getRegistro().setSegFecInicio(getToday());
				onSaveNew();
				getListado().add(getRegistro());
				onEdit();
			} else if (getStatus().equals("EDIT")) {
				getRegistro().setSegFecInicio(getToday());
				onSaveEdit();
				onEdit();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void asignarSeguimiento() {
		try {
			if (getStatus().equals("NEW")) {
				getRegistro().setAuditoria(getAuditoria());
				getRegistro().setSegEstado(1);
				onSaveNew();
				getListado().add(getRegistro());
				onEdit();

				auditoria.setAudFase(6);
				audigoesLocal.update(auditoria);

				CedulaNota ced;
				for (CedulaNota c : ceduMB.getListado()) {
					if (c.getCedValorizacion() != 2) {
						ced = new CedulaNota();
						ced.setAuditoria(c.getAuditoria());
						ced.setSeguimiento(getRegistro());
						ced.setCedEstado(8);
						ced.setCedValorizacion(c.getCedValorizacion());
						ced.setUsuario1(c.getUsuario1());
						ced.setCedTitulo(c.getCedTitulo());
						ced.setCedReferencia(c.getCedReferencia());
						ced.setCedCondicion(c.getCedCondicion());
						ced.setCedCriterio(c.getCedCriterio());
						ced.setCedFechaElaboro(c.getCedFechaElaboro());
						ced.setCedCausa(c.getCedCausa());
						ced.setCedEfecto(c.getCedEfecto());
						ced.setFecCrea(c.getFecCrea());
						ced.setUsuCrea(c.getUsuCrea());
						ced.setRegActivo(c.getRegActivo());
						audigoesLocal.insert(ced);
					}
				}
			} else if (getStatus().equals("EDIT")) {
				getRegistro().setSegFecInicio(getToday());
				onSaveEdit();
				onEdit();
			}

			notificarCorreoSeguimiento(textoCorreo);

			init();
			revisarPermisos();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void notificarCorreoSeguimiento(String texto) {
		String from;
		String cc;
		String to;
		String subject;
		String text;
		String attach;
		String logo;
		String body;
		Address[] toList;
		Address[] toCc;

		try {
			from = "audigoes.ues@gmail.com";
			cc = "javiieramos93@gmail.com";
			subject = "Notificaci�n Asignaci�n de Seguimiento";

			String tolist = "";
			for (AuditoriaResponsable r : auditoria.getAuditoriaResponsable()) {
				if (r.getAurRol() != 0) {
					tolist = tolist + r.getUsuario().getUsuCorreo() + ",";
				}
			}
			System.out.println(" " + tolist);
			tolist = "javiieramos93@gmail.com, jramos.j2011@gmail.com,";
			toList = InternetAddress.parse(tolist);

			body = texto;
			logo = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/images/logo-azul.png");

			SendMailAttach mail = new SendMailAttach(from, toList, cc, subject, body, null, logo);
			mail.sendManyTo();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void finalizarSeguimiento() {
		try {
			getRegistro().setSegFecFin(getToday());
			getRegistro().setSegEstado(2);
			getListado().get(getListado().size() - 1).setSegFecFin(getToday());
			onSaveEdit();
			onNew();
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Problemas al finalizar seguimiento"));
		}
	}

	public void onSaveComentario() {
		if (comMB.beforeSave()) {
			try {
				comMB.getRegistro().setSeguimiento(getRegistro());
				comMB.onSave();
			} catch (Exception e) {
				e.printStackTrace();
				addWarn(new FacesMessage(SYSTEM_NAME, "Problema al guardar comentario"));
			}
		}
	}

	public void onSaveComentarioEnProceso() {
		if (comMB.beforeSave()) {
			comMB.getRegistro().setComRecEstado(8);
			comMB.getRegistro().setSeguimiento(getRegistro());
			comMB.onSave();
			try {
				getCedulaSelected().setCedEstado(8);
				audigoesLocal.update(getCedulaSelected());
				addInfo(new FacesMessage(SYSTEM_NAME, "Estado de hallazgo actualizado a EN PROCESO"));
			} catch (Exception e) {
				e.printStackTrace();
				addWarn(new FacesMessage(SYSTEM_NAME, "Problema al actualizar estado del hallazgo"));
			}
		}
	}

	public void onSaveComentarioImplementado() {
		if (comMB.beforeSave()) {
			comMB.getRegistro().setComRecEstado(9);
			comMB.getRegistro().setSeguimiento(getRegistro());
			comMB.onSave();
			try {
				getCedulaSelected().setCedEstado(9);
				audigoesLocal.update(getCedulaSelected());
				addInfo(new FacesMessage(SYSTEM_NAME, "Estado de hallazgo actualizado"));
			} catch (Exception e) {
				e.printStackTrace();
				addWarn(new FacesMessage(SYSTEM_NAME, "Problema al actualizar estado del hallazgo"));
			}
		}
	}

	public void onSaveComentarioNoImplementar() {
		if (comMB.beforeSave()) {
			comMB.getRegistro().setComRecEstado(10);
			comMB.getRegistro().setSeguimiento(getRegistro());
			comMB.onSave();
			try {
				getCedulaSelected().setCedEstado(10);
				audigoesLocal.update(getCedulaSelected());
				addInfo(new FacesMessage(SYSTEM_NAME, "Estado de hallazgo actualizado a NO IMPLEMENTADO"));
			} catch (Exception e) {
				e.printStackTrace();
				addWarn(new FacesMessage(SYSTEM_NAME, "Problema al actualizar estado del hallazgo"));
			}

		}
	}

	/* GETS y SETS */
	@Override
	public Seguimiento getRegistro() {
		return (Seguimiento) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Seguimiento> getListado() {
		return (List<Seguimiento>) super.getListado();
	}

	public Usuario getCoordinador() {
		return coordinador;
	}

	public void setCoordinador(Usuario coordinador) {
		this.coordinador = coordinador;
	}

	public List<Usuario> getAuditoresList() {
		return auditoresList;
	}

	public void setAuditoresList(List<Usuario> auditoresList) {
		this.auditoresList = auditoresList;
	}

	public Auditoria getAuditoria() {
		return auditoria;
	}

	public void setAuditoria(Auditoria auditoria) {
		this.auditoria = auditoria;
	}

	public RecomendacionMB getRecMB() {
		return recMB;
	}

	public void setRecMB(RecomendacionMB recMB) {
		this.recMB = recMB;
	}

	public ComentarioMB getComMB() {
		return comMB;
	}

	public void setComMB(ComentarioMB comMB) {
		this.comMB = comMB;
	}

	public void prepararCorreo() {
		textoCorreo = "<p><strong>AUDIGOES LE INFORMA:</strong></p>"
				+ "<p>Se le notifica que ha sido asignado como auditor para la fase de seguimiento "
				+ "correspondiente a la auditor&iacute;a <strong>"
				+ getRegistro().getAuditoria().getTipoAuditoria().getTpaAcronimo() + "-"
				+ getRegistro().getAuditoria().getAudAnio() + "-" + getRegistro().getAuditoria().getAudCorrelativo()
				+ "</strong> por lo que se le pide ingresar al sistema para iniciar su seguimiento.</p>\r\n"
				+ "<p>Atte.-</p>";
	}

	public void onEnviarRevision() {
		correoRevision(textoCorreo);
	}

	public void correoRevision(String texto) {
		String from;
		String cc;
		String to;
		String subject;
		String text;
		String attach;
		String logo;
		String body;
		Address[] toList;
		Address[] toCc;

		try {
			from = "audigoes.ues@gmail.com";
			cc = "wilmer.grijalva@gmail.com";
			to = "wilmer.grijalva@gmail.com";
			subject = "Correo de Prueba";

			body = texto;
			logo = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/images/logo-azul.png");

			SendMailAttach mail = new SendMailAttach(from, cc, to, subject, body, null, logo);
			mail.send();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public String getTextoCorreo() {
		return textoCorreo;
	}

	public void setTextoCorreo(String textoCorreo) {
		this.textoCorreo = textoCorreo;
	}

	public List<Seguimiento> getFilteredSeguimientos() {
		return filteredSeguimientos;
	}

	public void setFilteredSeguimientos(List<Seguimiento> filteredSeguimientos) {
		this.filteredSeguimientos = filteredSeguimientos;
	}

	public Seguimiento getSeguimientoSelected() {
		return seguimientoSelected;
	}

	public void setSeguimientoSelected(Seguimiento seguimientoSelected) {
		this.seguimientoSelected = seguimientoSelected;
	}

	public List<Recomendacion> getRecomendacionesSeguimientoList() {
		return recomendacionesSeguimientoList;
	}

	public Comentario getComentarioRecomendacion() {
		return comentarioRecomendacion;
	}

	public void setRecomendacionesSeguimientoList(List<Recomendacion> recomendacionesSeguimientoList) {
		this.recomendacionesSeguimientoList = recomendacionesSeguimientoList;
	}

	public void setComentarioRecomendacion(Comentario comentarioRecomendacion) {
		this.comentarioRecomendacion = comentarioRecomendacion;
	}

	public List<Comentario> getComentariosSeguimientoList() {
		return comentariosSeguimientoList;
	}

	public void setComentariosSeguimientoList(List<Comentario> comentariosSeguimientoList) {
		this.comentariosSeguimientoList = comentariosSeguimientoList;
	}

	public Recomendacion getRecomendacionSelected() {
		return recomendacionSelected;
	}

	public void setRecomendacionSelected(Recomendacion recomendacionSelected) {
		this.recomendacionSelected = recomendacionSelected;
	}

	public List<CedulaNota> getCedulaNotaList() {
		return cedulaNotaList;
	}

	public void setCedulaNotaList(List<CedulaNota> cedulaNotaList) {
		this.cedulaNotaList = cedulaNotaList;
	}

	public CedulaNota getCedulaSelected() {
		return cedulaSelected;
	}

	public void setCedulaSelected(CedulaNota cedulaSelected) {
		this.cedulaSelected = cedulaSelected;
	}

	public CedulaMB getCeduMB() {
		return ceduMB;
	}

	public void setCeduMB(CedulaMB ceduMB) {
		this.ceduMB = ceduMB;
	}

	public InformeSeguimientoMB getInformeSegMB() {
		return informeSegMB;
	}

	public void setInformeSegMB(InformeSeguimientoMB informeSegMB) {
		this.informeSegMB = informeSegMB;
	}

	public void revisarPermisos() {
		if (getAuditoria() != null) {
			setPerEdit(false);
			setPerNew(false);

			setRolCoordinadorAuditoria(getObjAppsSession().isCoordinador(getObjAppsSession().getUsuario().getUsuId(),
					getAuditoria().getAuditoriaResponsable()));

			System.out.println(" " + getStatus());
			if (getStatus().equals("EDIT")) {
				switch (getRegistro().getSegEstado()) {
				case 1:
					if (isRolAuditor()) {
						setPerEdit(true);
					} else {
						setPerEnviar(false);
					}
					setPerAutorizar(false);
					break;
				case 2:
					if (isRolCoordinadorAuditoria()) {
						setPerEdit(true);
						if (isPerAutorizar()) {
							setPerAutorizar(true);
						}
					} else {
						setPerAutorizar(false);
					}
					setPerEnviar(false);
					break;
				default:
					break;
				}
			}
		}
	}

	@Override
	protected void configBean() {
		// TODO Auto-generated method stub
		super.configBean();
		revisarPermisos();

	}
}
