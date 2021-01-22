package audigoes.ues.edu.sv.mbeans.ejecucion;

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
import audigoes.ues.edu.sv.entities.administracion.BitacoraActividades;
import audigoes.ues.edu.sv.entities.administracion.Usuario;
import audigoes.ues.edu.sv.entities.ejecucion.ComentarioHallazgo;
import audigoes.ues.edu.sv.entities.ejecucion.ProgramaEjecucion;
import audigoes.ues.edu.sv.entities.informe.CedulaNota;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;
import audigoes.ues.edu.sv.entities.planeacion.AuditoriaResponsable;
import audigoes.ues.edu.sv.mbean.planificacion.BitacoraActividadMB;
import audigoes.ues.edu.sv.mbeans.administracion.ArchivoMB;
import audigoes.ues.edu.sv.mbeans.seguimiento.RecomendacionMB;
import audigoes.ues.edu.sv.util.SendMailAttach;

@ManagedBean(name = "ceduMB")
@ViewScoped
public class CedulaMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<CedulaNota> filteredCedula;
	private Auditoria auditoria;
	private String textoCorreo = "";
	private String textoCorreoObs = "";
	private String textoCorreoFin = "";
	private String textoCorreoUnidad = "";
	private String textoCorreoDevolver = "";
	private Usuario usuDevolver;
	private ComentarioHallazgo hallazgo;

	private List<ProgramaEjecucion> programasList;

	@ManagedProperty(value = "#{recMB}")
	private RecomendacionMB recMB = new RecomendacionMB();

	@ManagedProperty(value = "#{evdMB}")
	private EvidenciaMB evdMB = new EvidenciaMB();

	@ManagedProperty(value = "#{arcMB}")
	private ArchivoMB arcMB = new ArchivoMB();

	private List<Usuario> usuariosList;
	private List<Usuario> selectedUsuariosList;

	@ManagedProperty(value = "#{bitaMB}")
	private BitacoraActividadMB bitaMB = new BitacoraActividadMB();

	@ManagedProperty(value = "#{comeMB}")
	private ComentarioHallazgoMB comeMB = new ComentarioHallazgoMB();

	public CedulaMB() {
		super(new CedulaNota());
	}

	@PostConstruct
	public void init() {
		try {
			super.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onRowSelect() {
		super.onRowSelect();
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

	public void onCancelHallazgo() {
		setStatus("VIEW");
	}

	@SuppressWarnings("unchecked")
	public void fillNotas() {
		try {
//			setListado((List<ProgramaPlanificacion>) audigoesLocal.findByNamedQuery(ProgramaPlanificacion.class,
//					"programa.by.auditoria",
//					new Object[] { auditoria.getAudId() }));
			Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			setAuditoria(((Auditoria) sessionMap.get("auditoria")));

			setListado((List<CedulaNota>) audigoesLocal.findByNamedQuery(CedulaNota.class, "notas.by.auditoria",
					new Object[] { auditoria.getAudId() }));
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage("Advertencia", "No se pudo obtener las auditorias"));
		}
	}

	public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
		if (filterText == null || filterText.equals("")) {
			return true;
		}

		CedulaNota ced = (CedulaNota) value;
		return ced.getCedTitulo().toLowerCase().contains(filterText)
				|| ced.getCedCondicion().toLowerCase().contains(filterText)
				|| ced.getCedCriterio().toLowerCase().contains(filterText)
				|| ced.getCedCausa().toLowerCase().contains(filterText);
	}

	@Override
	public void onEditSelected() {
		// TODO Auto-generated method stub
		super.onEditSelected();
		recMB.setAuditoria(auditoria);
		recMB.setCedula(getRegistro());
		recMB.fillRecomendaciones();

		evdMB.setCedula(getRegistro());
		evdMB.fillByCedula();

		arcMB.setCedula(getRegistro());
		arcMB.fillByCedula(getRegistro());

		comeMB.setCedula(getRegistro());
		comeMB.fillComentariosByCedula();
	}

	@Override
	public void onShowSelected() {
		recMB.setAuditoria(auditoria);
		recMB.setCedula(getRegistro());
		recMB.fillRecomendaciones();

		evdMB.setCedula(getRegistro());
		evdMB.fillByCedula();

		arcMB.setCedula(getRegistro());
		arcMB.fillByCedula(getRegistro());

		comeMB.setCedula(getRegistro());
		comeMB.fillComentariosByCedula();
		super.onShowSelected();
	}

	@Override
	public void afterCancel() {
		super.afterCancel();
	}

	@Override
	public boolean beforeSaveNew() {
		getRegistro().setAuditoria(auditoria);
		getRegistro().setCedFechaElaboro(getToday());
		getRegistro().setUsuario1(getObjAppsSession().getUsuario());
		getRegistro().setCedEstado(1);
		getRegistro().setCedValorizacion(1);
		return super.beforeSaveNew();
	}

	public void prepararCorreo() {
		textoCorreo = "<p><strong>AUDIGOES LE INFORMA:</strong></p>"
				+ "<p>Se ha enviado para su revisi&oacute;n el hallazgo "
				+ "correspondiente a la auditor&iacute;a <strong>"
				+ getRegistro().getAuditoria().getTipoAuditoria().getTpaAcronimo() + "-"
				+ getRegistro().getAuditoria().getAudAnio() + "-" + getRegistro().getAuditoria().getAudCorrelativo()
				+ "</strong> denominado <strong>" + getRegistro().getCedTitulo()
				+ " </strong> por lo que se le pide ingresar al sistema para realizarlo.</p>\r\n" + "<p>Atte.-</p>";
	}

	public void prepararCorreoObs() {
		textoCorreoObs = "<p><strong>AUDIGOES LE INFORMA:</strong></p>" + "<p>Se ha revisado el hallazgo "
				+ "correspondiente a la auditor&iacute;a <strong>"
				+ getRegistro().getAuditoria().getTipoAuditoria().getTpaAcronimo() + "-"
				+ getRegistro().getAuditoria().getAudAnio() + "-" + getRegistro().getAuditoria().getAudCorrelativo()
				+ "</strong> denominado <strong>" + getRegistro().getCedTitulo()
				+ " </strong> con las siguientes observaciones, por lo que se pide ingresar al sistema para realizarlo.</p>\r\n"
				+ "<p>Atte.-</p>";
	}

	public void prepararCorreoFin() {
		textoCorreoFin = "<p><strong>AUDIGOES LE INFORMA:</strong></p>" + "<p>Se ha revisado el hallazgo "
				+ "correspondiente a la auditor&iacute;a <strong>"
				+ getRegistro().getAuditoria().getTipoAuditoria().getTpaAcronimo() + "-"
				+ getRegistro().getAuditoria().getAudAnio() + "-" + getRegistro().getAuditoria().getAudCorrelativo()
				+ "</strong> denominado <strong>" + getRegistro().getCedTitulo()
				+ " </strong> por lo que se le pide ingresar al sistema para comunicarlo a la unidad correspondiente</p>\r\n"
				+ "<p>Atte.-</p>";
	}

	public void prepararCorreoObsAnalisis() {
		textoCorreoObs = "<p><strong>AUDIGOES LE INFORMA:</strong></p>"
				+ "<p>Se han revisado los comentarios del hallazgo " + "correspondiente a la auditor&iacute;a <strong>"
				+ getRegistro().getAuditoria().getTipoAuditoria().getTpaAcronimo() + "-"
				+ getRegistro().getAuditoria().getAudAnio() + "-" + getRegistro().getAuditoria().getAudCorrelativo()
				+ "</strong> denominado <strong>" + getRegistro().getCedTitulo()
				+ " </strong> con las siguientes observaciones, por lo que se pide ingresar al sistema para realizarlo.</p>\r\n"
				+ "<p>Atte.-</p>";
	}

	public void prepararCorreoFinAnalisis() {
		textoCorreoFin = "<p><strong>AUDIGOES LE INFORMA:</strong></p>"
				+ "<p>Se han finalizado la revisión de los comentarios del hallazgo "
				+ "correspondiente a la auditor&iacute;a <strong>"
				+ getRegistro().getAuditoria().getTipoAuditoria().getTpaAcronimo() + "-"
				+ getRegistro().getAuditoria().getAudAnio() + "-" + getRegistro().getAuditoria().getAudCorrelativo()
				+ "</strong> denominado <strong>" + getRegistro().getCedTitulo() + " </strong></p>\r\n"
				+ "<p>Atte.-</p>";
	}

	public void prepararCorreoUnidad() {
		textoCorreoUnidad = "<p><strong>AUDIGOES LE INFORMA:</strong></p>" + "<p>La Unidad de Auditoría Interna ha "
				+ "identificado el siguiente hallazgo preliminar " + "correspondiente a la auditor&iacute;a <strong>"
				+ getRegistro().getAuditoria().getTipoAuditoria().getTpaAcronimo() + "-"
				+ getRegistro().getAuditoria().getAudAnio() + "-" + getRegistro().getAuditoria().getAudCorrelativo()
				+ "</strong> por lo que se le pide ingresar al sistema para comunicarlo a la unidad correspondiente</p>\r\n"
				+ "<p><strong>Título: </strong>" + getRegistro().getCedTitulo() + "</p>"
				+ "<p><strong>Condición: </strong>" + getRegistro().getCedCondicion() + "</p>"
				+ "<p><strong>Criterio:</strong> " + getRegistro().getCedCriterio() + "</p>" + "<br/><p>Atte.-</p>";
	}

	public void prepararCorreoAnalisis() {
		textoCorreo = "<p><strong>AUDIGOES LE INFORMA:</strong></p>"
				+ "<p>Se ha enviado para su revisi&oacute;n los comentarios del hallazgo "
				+ "correspondiente a la auditor&iacute;a <strong>"
				+ getRegistro().getAuditoria().getTipoAuditoria().getTpaAcronimo() + "-"
				+ getRegistro().getAuditoria().getAudAnio() + "-" + getRegistro().getAuditoria().getAudCorrelativo()
				+ "</strong> denominado <strong>" + getRegistro().getCedTitulo()
				+ " </strong> por lo que se le pide ingresar al sistema para realizarlo.</p>\r\n" + "<p>Atte.-</p>";
	}

	public void prepararCorreoDevolver(ComentarioHallazgo comentario) {
		textoCorreoDevolver = "<p><strong>AUDIGOES LE INFORMA:</strong></p>"
				+ "<p>Se ha dado revisión a sus comentarios brindados para el hallazgo preliminar "
				+ "correspondiente a la auditor&iacute;a <strong>"
				+ getRegistro().getAuditoria().getTipoAuditoria().getTpaAcronimo() + "-"
				+ getRegistro().getAuditoria().getAudAnio() + "-" + getRegistro().getAuditoria().getAudCorrelativo()
				+ "</strong> denominado <strong>" + getRegistro().getCedTitulo()
				+ " </strong> , solicitando que nos brinde nueva información:</p>\r\n" + "<p>Atte.-</p>";
		setUsuDevolver(comentario.getUsuario());
		setHallazgo(comentario);
	}

	public void onEnviarRevision() {
		Usuario usr = buscarCoordinador(getRegistro().getAuditoria());
		if (usr != null) {
			try {
				getRegistro().setCedEstado(2);
				onSave();
				if (!correoRevision(textoCorreo, usr)) {
					addWarn(new FacesMessage("Error en el envio del correo"));
				}
				revisarPermisos();
				setStatus("VIEW");
			} catch (Exception e) {
				e.printStackTrace();
				addWarn(new FacesMessage("Error al enviar a revisión"));
			}
		}
	}

	public void onEnviarRevisionAnalisis() {
		Usuario usr = buscarCoordinador(getRegistro().getAuditoria());
		if (usr != null) {
			try {
				getRegistro().setCedEstado(6);
				onSave();
				if (!correoRevision(textoCorreo, usr)) {
					addWarn(new FacesMessage("Error en el envio del correo"));
				}
				revisarPermisos();
				setStatus("VIEW");
			} catch (Exception e) {
				e.printStackTrace();
				addWarn(new FacesMessage("Error al enviar a revisión"));
			}
		}
	}

	public void onEnviarObservacion() {
		try {
			getRegistro().setCedEstado(1);
			getRegistro().setUsuario2(getObjAppsSession().getUsuario());
			getRegistro().setCedFechaReviso(getToday());
			onSave();
			try {
				if (!correoObservacion(textoCorreoObs, getObjAppsSession().getUsuario())) {
					addWarn(new FacesMessage("Error en el envio del correo"));
				}
				revisarPermisos();
			} catch (Exception e) {
				e.printStackTrace();
				addWarn(new FacesMessage("Error al enviar a revisión"));
			}

			setStatus("VIEW");
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage("Error al enviar a revisión"));
		}
	}

	public void onFinalizar() {
		try {
			getRegistro().setCedEstado(3);
			getRegistro().setUsuario2(getObjAppsSession().getUsuario());
			getRegistro().setCedFechaReviso(getToday());
			onSave();
			revisarPermisos();
			if (!correoFin(textoCorreoFin, getObjAppsSession().getUsuario())) {
				addWarn(new FacesMessage("Error en el envio del correo"));
			}
			setStatus("VIEW");
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage("Error al finalizar las observaciones"));
		}
	}

	public void onEnviarObservacionAnalisis() {
		try {
			getRegistro().setCedEstado(5);
			getRegistro().setUsuario2(getObjAppsSession().getUsuario());
			getRegistro().setCedFechaReviso(getToday());
			onSave();
			try {
				if (!correoObservacion(textoCorreoObs, getObjAppsSession().getUsuario())) {
					addWarn(new FacesMessage("Error en el envio del correo"));
				}
				revisarPermisos();
			} catch (Exception e) {
				e.printStackTrace();
				addWarn(new FacesMessage("Error al enviar a revisión"));
			}

			setStatus("VIEW");
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage("Error al enviar a revisión"));
		}
	}

	public void onFinalizarAnalisis() {
		try {
			getRegistro().setCedEstado(7);
			getRegistro().setUsuario2(getObjAppsSession().getUsuario());
			getRegistro().setCedFechaReviso(getToday());
			onSave();
			revisarPermisos();
			if (!correoFin(textoCorreoFin, getObjAppsSession().getUsuario())) {
				addWarn(new FacesMessage("Error en el envio del correo"));
			}
			int contador = 0;

			for (CedulaNota c : getListado()) {
				if (c.getCedEstado() != 7) {
					contador = contador + 1;
				}
			}

			if (contador == 0) {
				BitacoraActividades a = bitaMB.buscarActividad(14, getRegistro().getAuditoria());
				if (a != null) {
					bitaMB.finalizarActividad(14, getRegistro().getAuditoria(), getObjAppsSession().getUsuario());
					
				}

				Auditoria aud = getRegistro().getAuditoria();
				aud.setAudFase(4);
				aud.setAudFechaInforme(getToday());
				audigoesLocal.update(aud);
			}
			setStatus("VIEW");
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage("Error al finalizar las observaciones"));
		}
	}

	public void onEnviarUnidad() {

		if (getSelectedUsuariosList() != null) {
			Usuario usr = getObjAppsSession().getUsuario();
			if (usr != null) {
				try {
					onEdit();
					getRegistro().setCedEstado(4);
					getRegistro().setCedFechaComunicacion(getToday());
					onSave();
					revisarPermisos();
					if (!correoUnidad(textoCorreoUnidad, getSelectedUsuariosList(), usr)) {
						addWarn(new FacesMessage("Error en el envio del correo"));
					} else {
						BitacoraActividades a = bitaMB.buscarActividad(13, getRegistro().getAuditoria());
						if (a == null) {
							bitaMB.iniciarActividad(13, "Comunicación de hallazgos preliminares",
									getRegistro().getAuditoria(), getObjAppsSession().getUsuario());
						}

						ComentarioHallazgo com;
						for (Usuario u : selectedUsuariosList) {
							com = new ComentarioHallazgo();
							com.setCedulaNota(getRegistro());
							com.setUsuario(u);
							com.setAuditoria(getRegistro().getAuditoria());
							com.setComeEnviado(0);
							com.setFecCrea(getToday());
							com.setUsuCrea(getObjAppsSession().getUsuario().getUsuUsuario());
							com.setRegActivo(1);

							audigoesLocal.insert(com);
							System.out.println("u " + u.getUsuNombre());
						}

					}
					setStatus("VIEW");
				} catch (Exception e) {
					e.printStackTrace();
					addWarn(new FacesMessage("Error al comunicar la nota"));
				}
			}
		} else {
			addWarn(new FacesMessage("Error, seleccione al responsable"));
		}
	}

	public void onDevolverUnidad() {
		try {
			if (getUsuDevolver() != null && getHallazgo() != null) {
				onEdit();
				getRegistro().setCedEstado(4);
				getRegistro().setCedFechaComunicacion(getToday());
				onSave();
				revisarPermisos();
				if (!correoDevolverUnidad(textoCorreoUnidad, getUsuDevolver(), getObjAppsSession().getUsuario())) {
					addWarn(new FacesMessage("Error en el envio del correo"));
				} else {
					getHallazgo().setComeEnviado(0);
					getHallazgo().setFecModi(getToday());
					getHallazgo().setUsuModi(getObjAppsSession().getUsuario().getUsuUsuario());

					audigoesLocal.update(getHallazgo());

				}
				BitacoraActividades a = bitaMB.buscarActividad(13, getRegistro().getAuditoria());
				if (a != null) {
					if (a.getBitaFechaFin() != null) {
						a.setBitaFechaFin(null);
						audigoesLocal.update(a);
					}
				}
				setStatus("VIEW");
			}
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage("Error, hubo un problema al enviar el correo"));
		}
	}

	public boolean correoRevision(String texto, Usuario coordinador) {
		String from;
		String cc;
		String to;
		String subject;
		String attach;
		String logo;
		String body;
		Address[] toList;
		Address[] toCc;

		try {
			from = "audigoes.ues@gmail.com";
			to = coordinador.getUsuCorreo();
			subject = "Solicitud de revisión de hallazgo de auditoría";
			String toccs = "";

			for (AuditoriaResponsable r : auditoria.getAuditoriaResponsable()) {
				if (r.getAurRol() != 0) {
					toccs = toccs + r.getUsuario().getUsuCorreo() + ",";
				}
			}
			toCc = InternetAddress.parse(toccs);

			body = texto;
			logo = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/images/logo-azul.png");

			SendMailAttach mail = new SendMailAttach(from, to, toCc, subject, body, null, logo);
			mail.sendManyCc();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean correoObservacion(String texto, Usuario coordinador) {
		String from;
		String cc;
		String to;
		String subject;
		String attach;
		String logo;
		String body;
		Address[] toList;
		Address[] toCc;

		try {
			from = "audigoes.ues@gmail.com";
			cc = coordinador.getUsuCorreo();
			subject = "Observaciones a hallazgo de auditoría";
			String tolists = "";

			for (AuditoriaResponsable r : auditoria.getAuditoriaResponsable()) {
				if (r.getAurRol() != 0) {
					tolists = tolists + r.getUsuario().getUsuCorreo() + ",";
				}
			}
			toList = InternetAddress.parse(tolists);

			body = texto;
			logo = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/images/logo-azul.png");

			SendMailAttach mail = new SendMailAttach(from, toList, cc, subject, body, null, logo);
			mail.sendManyTo();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;// TODO: handle exception
		}
	}

	public boolean correoFin(String texto, Usuario coordinador) {
		String from;
		String cc;
		String to;
		String subject;
		String attach;
		String logo;
		String body;
		Address[] toList;
		Address[] toCc;

		try {
			from = "audigoes.ues@gmail.com";
			cc = coordinador.getUsuCorreo();
			subject = "Finalización de revisión de hallazgo de auditoría";
			String tolists = "";

			for (AuditoriaResponsable r : auditoria.getAuditoriaResponsable()) {
				if (r.getAurRol() != 0) {
					tolists = tolists + r.getUsuario().getUsuCorreo() + ",";
				}
			}
			toList = InternetAddress.parse(tolists);
			body = texto;
			logo = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/images/logo-azul.png");

			SendMailAttach mail = new SendMailAttach(from, toList, cc, subject, body, null, logo);
			mail.sendManyTo();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;// TODO: handle exception
		}
	}

	public boolean correoDevolverUnidad(String texto, Usuario usuario, Usuario auditor) {
		String from;
		String cc;
		String to;
		String subject;
		String attach;
		String logo;
		String body;
		Address[] toList;
		Address[] toCc;

		try {
			from = "audigoes.ues@gmail.com";
			cc = auditor.getUsuCorreo();
			subject = "Comunicación de Situación Encontrada";
			to = usuario.getUsuCorreo();
			body = texto;
			logo = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/images/logo-azul.png");

			SendMailAttach mail = new SendMailAttach(from, cc, to, subject, body, null, logo);
			mail.send();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;// TODO: handle exception
		}
	}

	public boolean correoUnidad(String texto, List<Usuario> usuarios, Usuario auditor) {
		String from;
		String cc;
		String to;
		String subject;
		String attach;
		String logo;
		String body;
		Address[] toList;
		Address[] toCc;

		try {
			from = "audigoes.ues@gmail.com";
			cc = auditor.getUsuCorreo();
			subject = "Comunicación de Situación Encontrada";
			// to = usuario.getUsuCorreo();
			String tolists = "";

			for (Usuario u : usuarios) {
				tolists = tolists + u.getUsuCorreo() + ",";
			}
			toList = InternetAddress.parse(tolists);
			body = texto;
			logo = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/images/logo-azul.png");

			SendMailAttach mail = new SendMailAttach(from, toList, cc, subject, body, null, logo);
			mail.sendManyTo();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;// TODO: handle exception
		}
	}

	@SuppressWarnings("unchecked")
	public void fillUsuarios() {
		try {
			setUsuariosList((List<Usuario>) audigoesLocal.findByNamedQuery(Usuario.class,
					"usuarios.auditoria.responsable", new Object[] { getAuditoria().getAudId() }));
			// System.out.println(" size " + getUsuariosList().size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void bitacora() {
		int contador = 0;

		for (CedulaNota c : getListado()) {
			if (c.getCedEstado() == 4) {
				contador = contador + 1;
			}
		}

		if (contador == 0) {
			bitaMB.finalizarActividad(13, getAuditoria(), getObjAppsSession().getUsuario());
		}

	}

	@Override
	protected void afterRowSelect() {
		fillUsuarios();
		revisarPermisos();
		super.afterRowSelect();
	}

	/* GETS y SETS */

	@Override
	public CedulaNota getRegistro() {
		return (CedulaNota) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CedulaNota> getListado() {
		return (List<CedulaNota>) super.getListado();
	}

	@Override
	public void afterSave() {
		super.afterSave();
		onEdit();
	}

	@Override
	public void afterSaveNew() {
		getListado().add(getRegistro());
		super.afterSaveNew();
	}

	@Override
	public void afterNew() {
		revisarPermisos();
		super.afterNew();
	}

	public List<CedulaNota> getFilteredCedula() {
		return filteredCedula;
	}

	public void setFilteredCedula(List<CedulaNota> filteredCedula) {
		this.filteredCedula = filteredCedula;
	}

	public Auditoria getAuditoria() {
		return auditoria;
	}

	public void setAuditoria(Auditoria auditoria) {
		this.auditoria = auditoria;
	}

	public String getTextoCorreo() {
		return textoCorreo;
	}

	public void setTextoCorreo(String textoCorreo) {
		this.textoCorreo = textoCorreo;
	}

	public String getTextoCorreoObs() {
		return textoCorreoObs;
	}

	public void setTextoCorreoObs(String textoCorreoObs) {
		this.textoCorreoObs = textoCorreoObs;
	}

	public String getTextoCorreoFin() {
		return textoCorreoFin;
	}

	public void setTextoCorreoFin(String textoCorreoFin) {
		this.textoCorreoFin = textoCorreoFin;
	}

	public List<ProgramaEjecucion> getProgramasList() {
		return programasList;
	}

	public void setProgramasList(List<ProgramaEjecucion> programasList) {
		this.programasList = programasList;
	}

	public RecomendacionMB getRecMB() {
		return recMB;
	}

	public void setRecMB(RecomendacionMB recMB) {
		this.recMB = recMB;
	}

	public EvidenciaMB getEvdMB() {
		return evdMB;
	}

	public void setEvdMB(EvidenciaMB evdMB) {
		this.evdMB = evdMB;
	}

	public ArchivoMB getArcMB() {
		return arcMB;
	}

	public void setArcMB(ArchivoMB arcMB) {
		this.arcMB = arcMB;
	}

	public String getTextoCorreoUnidad() {
		return textoCorreoUnidad;
	}

	public void setTextoCorreoUnidad(String textoCorreoUnidad) {
		this.textoCorreoUnidad = textoCorreoUnidad;
	}

	public List<Usuario> getUsuariosList() {
		return usuariosList;
	}

	public void setUsuariosList(List<Usuario> usuariosList) {
		this.usuariosList = usuariosList;
	}

	public List<Usuario> getSelectedUsuariosList() {
		return selectedUsuariosList;
	}

	public void setSelectedUsuariosList(List<Usuario> selectedUsuariosList) {
		this.selectedUsuariosList = selectedUsuariosList;
	}

	public BitacoraActividadMB getBitaMB() {
		return bitaMB;
	}

	public void setBitaMB(BitacoraActividadMB bitaMB) {
		this.bitaMB = bitaMB;
	}

	public ComentarioHallazgoMB getComeMB() {
		return comeMB;
	}

	public void setComeMB(ComentarioHallazgoMB comeMB) {
		this.comeMB = comeMB;
	}

	public String getTextoCorreoDevolver() {
		return textoCorreoDevolver;
	}

	public void setTextoCorreoDevolver(String textoCorreoDevolver) {
		this.textoCorreoDevolver = textoCorreoDevolver;
	}

	public Usuario getUsuDevolver() {
		return usuDevolver;
	}

	public void setUsuDevolver(Usuario usuDevolver) {
		this.usuDevolver = usuDevolver;
	}

	public ComentarioHallazgo getHallazgo() {
		return hallazgo;
	}

	public void setHallazgo(ComentarioHallazgo hallazgo) {
		this.hallazgo = hallazgo;
	}

	public void revisarPermisos() {
		super.configBean();

		if (getAuditoria() != null) {
			setPerEdit(false);
			setPerNew(false);

			setRolCoordinadorAuditoria(getObjAppsSession().isCoordinador(getObjAppsSession().getUsuario().getUsuId(),
					getAuditoria().getAuditoriaResponsable()));
			switch (getRegistro().getCedEstado()) {
			case 1:
				if (isRolAuditor()) {
					setPerEdit(true);
					setPerNew(true);
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
			case 3:
				if (isRolAuditor()) {
					setPerEnviar(true);
				}
				if (isRolCoordinador() || isRolCoordinadorAuditoria()) {
					setPerEnviar(false);
				}
				setPerEdit(false);
				setPerAutorizar(false);
				break;
			case 4:
				setPerEnviar(false);
				if (isRolAuditor()) {
					setPerEdit(true);

				}
				setPerNew(false);
				setPerAutorizar(false);
				break;
			case 5:
				if (isRolAuditor()) {
					setPerEnviar(true);
					setPerEdit(true);
					setPerNew(true);
				}
				if (isRolCoordinador() || isRolCoordinadorAuditoria()) {
					setPerEnviar(false);
					setPerEdit(false);
					setPerAutorizar(false);
				}
				break;
			case 6:

				setPerEnviar(false);
				setPerEdit(false);
				//setPerAutorizar(false);
				System.out.println("6");
				if (isRolCoordinadorAuditoria()) {
					System.out.println("6 isRolCoordinadorAuditoria");
					setPerEdit(true);
					if (isPerAutorizar()) {
						System.out.println("6 isPerAutorizar");
						setPerAutorizar(true);
					}
				} else {
					setPerAutorizar(false);
				}
				break;
			default:
				if (isRolAuditor()) {
					setPerEdit(true);
				}
				break;
			}
		}
	}

}
