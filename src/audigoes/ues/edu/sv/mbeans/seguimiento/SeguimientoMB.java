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

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.administracion.Usuario;
import audigoes.ues.edu.sv.entities.informe.CedulaNota;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;
import audigoes.ues.edu.sv.entities.seguimiento.Comentario;
import audigoes.ues.edu.sv.entities.seguimiento.Recomendacion;
import audigoes.ues.edu.sv.entities.seguimiento.Seguimiento;
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
	private ComentarioMB comMB  = new ComentarioMB(); 

	public SeguimientoMB() {
		super(new Seguimiento());
	}

	@PostConstruct
	public void init() {
		try {
			configBean();
			Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			setAuditoria((Auditoria) sessionMap.get("auditoria"));

			//obtenerRecomendaciones();
			obtenerCoordinador();
			obtenerSeguimiento();
			obtenerCedulaNotaList();

			// super.init();
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
			if(getAuditoria() != null) {
				setCedulaNotaList((List<CedulaNota>) audigoesLocal.findByNamedQuery(CedulaNota.class,
						"cedula.nota.por.auditoria", new Object[] {getAuditoria().getAudId()}));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Problemas al obtener hallazgos de auditoría"));
		}
	}
	
	public void onCedulaSelected() {
		try {
			getCedulaSelected().setSelected(true);
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Hubo un problema con la selección del hallazgo"));
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
				setListado((List<Seguimiento>) audigoesLocal.findByNamedQuery(Seguimiento.class,
						"seguimiento.get.by.auditoria", new Object[] { getAuditoria().getAudId() }));
				if (!getListado().isEmpty()) {
					setRegistro(getListado().get(getListado().size() - 1));
					onEdit();
					if (getRegistro().getSegFecFin() != null) {
						onNew();
					}
				} else {
					onNew();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Problemas al obtener seguimiento de auditoría"));
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

	public void finalizarSeguimiento() {
		try {
			getRegistro().setSegFecFin(getToday());
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
				addInfo(new FacesMessage(SYSTEM_NAME,"Estado de hallazgo actualizado a EN PROCESO"));
			} catch (Exception e) {
				e.printStackTrace();
				addWarn(new FacesMessage(SYSTEM_NAME,"Problema al actualizar estado del hallazgo"));
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
				addInfo(new FacesMessage(SYSTEM_NAME,"Estado de hallazgo actualizado"));
			} catch (Exception e) {
				e.printStackTrace();
				addWarn(new FacesMessage(SYSTEM_NAME,"Problema al actualizar estado del hallazgo"));
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
				addInfo(new FacesMessage(SYSTEM_NAME,"Estado de hallazgo actualizado a NO IMPLEMENTADO"));
			} catch (Exception e) {
				e.printStackTrace();
				addWarn(new FacesMessage(SYSTEM_NAME,"Problema al actualizar estado del hallazgo"));
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
				+ "<p>Se le notifica que la fase de seguimiento a iniciado"
				+ "correspondiente a la auditor&iacute;a <strong>"
				+ getRegistro().getAuditoria().getTipoAuditoria().getTpaAcronimo() + "-"
				+ getRegistro().getAuditoria().getAudAnio() + "-" + getRegistro().getAuditoria().getAudCorrelativo()
				+ "</strong> por lo que se le pide ingresar al sistema para revisarlo.</p>\r\n" + "<p>Atte.-</p>";
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

	@Override
	protected void configBean() {
		// TODO Auto-generated method stub
		super.configBean();
	}
}
