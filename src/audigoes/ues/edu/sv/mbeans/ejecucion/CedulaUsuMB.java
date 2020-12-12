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

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.administracion.Usuario;
import audigoes.ues.edu.sv.entities.ejecucion.ProgramaEjecucion;
import audigoes.ues.edu.sv.entities.informe.CedulaNota;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;
import audigoes.ues.edu.sv.entities.planeacion.AuditoriaResponsable;
import audigoes.ues.edu.sv.mbeans.administracion.ArchivoMB;
import audigoes.ues.edu.sv.mbeans.seguimiento.RecomendacionMB;
import audigoes.ues.edu.sv.util.SendMailAttach;

@ManagedBean(name = "ceduUsuMB")
@ViewScoped
public class CedulaUsuMB extends AudigoesController implements Serializable {
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

	private List<ProgramaEjecucion> programasList;

	@ManagedProperty(value = "#{recMB}")
	private RecomendacionMB recMB = new RecomendacionMB();

	@ManagedProperty(value = "#{evdMB}")
	private EvidenciaMB evdMB = new EvidenciaMB();

	@ManagedProperty(value = "#{arcMB}")
	private ArchivoMB arcMB = new ArchivoMB();

	public CedulaUsuMB() {
		super(new CedulaNota());
	}

	@PostConstruct
	public void init() {
		try {
			super.init();
			fillNotas();
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

			setListado((List<CedulaNota>) audigoesLocal.findByNamedQuery(CedulaNota.class, "notas.by.usuario",
					new Object[] {}));
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

	public void onEnviarRevision() {
		Usuario usr = buscarCoordinador(getRegistro().getAuditoria());
		if (usr != null) {
			try {
				getRegistro().setCedEstado(2);
				onSave();
				correoObservacion(textoCorreo, getRegistro().getUsuario1(), usr);
				setStatus("VIEW");
			} catch (Exception e) {
				e.printStackTrace();
				addWarn(new FacesMessage("Error al enviar a revisión"));
			}
		}
	}

	public void onEnviarObservacion() {
		Usuario usr = buscarCoordinador(getRegistro().getAuditoria());
		if (usr != null) {
			try {
				getRegistro().setCedEstado(1);
				getRegistro().setUsuario2(getObjAppsSession().getUsuario());
				getRegistro().setCedFechaReviso(getToday());
				onSave();
				correoRevision(textoCorreoObs, getRegistro().getUsuario1(), usr);
				setStatus("VIEW");
			} catch (Exception e) {
				e.printStackTrace();
				addWarn(new FacesMessage("Error al enviar las observaciones"));
			}
		}
	}

	public void onFinalizar() {
		Usuario usr = buscarCoordinador(getRegistro().getAuditoria());
		if (usr != null) {
			try {
				getRegistro().setCedEstado(3);
				getRegistro().setUsuario2(getObjAppsSession().getUsuario());
				getRegistro().setCedFechaReviso(getToday());
				onSave();
				correoFin(textoCorreoFin, getRegistro().getUsuario1(), usr);
				setStatus("VIEW");
			} catch (Exception e) {
				e.printStackTrace();
				addWarn(new FacesMessage("Error al finalizar las observaciones"));
			}
		}
	}

	public void onEnviarUnidad() {
		Usuario usr = getObjAppsSession().getUsuario();
		if (usr != null) {
			try {
				getRegistro().setCedEstado(4);
				getRegistro().setCedFechaComunicacion(getToday());
				onSave();
				correoUnidad(textoCorreoUnidad, usr, getObjAppsSession().getUsuario());
				setStatus("VIEW");
			} catch (Exception e) {
				e.printStackTrace();
				addWarn(new FacesMessage("Error al finalizar las observaciones"));
			}
		}
	}

	public void correoRevision(String texto, Usuario auditor, Usuario coordinador) {
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
			subject = "Solicitud de revisión programa de ejecución";
			cc = auditor.getUsuCorreo();
			body = texto;
			logo = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/images/logo-azul.png");

			SendMailAttach mail = new SendMailAttach(from, cc, to, subject, body, null, logo);
			mail.send();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void correoObservacion(String texto, Usuario auditor, Usuario coordinador) {
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
			subject = "Observaciones al programa de ejecución";
			to = auditor.getUsuCorreo();
			body = texto;
			logo = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/images/logo-azul.png");

			SendMailAttach mail = new SendMailAttach(from, cc, to, subject, body, null, logo);
			mail.send();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void correoFin(String texto, Usuario auditor, Usuario coordinador) {
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
			subject = "Finalización de revisión al programa de ejecución";
			to = auditor.getUsuCorreo();
			body = texto;
			logo = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/images/logo-azul.png");

			SendMailAttach mail = new SendMailAttach(from, cc, to, subject, body, null, logo);
			mail.send();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void correoUnidad(String texto, Usuario usuario, Usuario auditor) {
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
			subject = "Finalización de revisión al programa de ejecución";
			to = usuario.getUsuCorreo();
			body = texto;
			logo = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/images/logo-azul.png");

			SendMailAttach mail = new SendMailAttach(from, cc, to, subject, body, null, logo);
			mail.send();
		} catch (Exception e) {
			// TODO: handle exception
		}
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
		// getListado().add(getRegistro());
		super.afterSaveNew();
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

}
