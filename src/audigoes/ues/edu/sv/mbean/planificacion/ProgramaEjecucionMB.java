package audigoes.ues.edu.sv.mbean.planificacion;

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
import audigoes.ues.edu.sv.entities.ejecucion.ProcedimientoEjecucion;
import audigoes.ues.edu.sv.entities.ejecucion.ProgramaEjecucion;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;
import audigoes.ues.edu.sv.entities.planeacion.AuditoriaResponsable;
import audigoes.ues.edu.sv.util.SendMailAttach;

@ManagedBean(name = "pejeMB")
@ViewScoped
public class ProgramaEjecucionMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<ProgramaEjecucion> filteredPrograma;
	private Auditoria auditoria;
	private String textoCorreo = "";
	private String textoCorreoObs = "";
	private String textoCorreoFin = "";

	private List<ProgramaEjecucion> programasList;

	@ManagedProperty(value = "#{proejeMB}")
	private ProcedimientosEjeMB proejeMB = new ProcedimientosEjeMB();

	public ProgramaEjecucionMB() {
		super(new ProgramaEjecucion());
	}

	@PostConstruct
	public void init() {
		try {
			super.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void prepararCorreo() {
		textoCorreo = "<p><strong>AUDIGOES LE INFORMA:</strong></p>"
				+ "<p>Se ha enviado para su revisi&oacute;n el programa de ejecuci&oacute;n "
				+ "correspondiente a la auditor&iacute;a <strong>"
				+ getRegistro().getAuditoria().getTipoAuditoria().getTpaAcronimo() + "-"
				+ getRegistro().getAuditoria().getAudAnio() + "-" + getRegistro().getAuditoria().getAudCorrelativo()
				+ "</strong> por lo que se le pide ingresar al sistema para realizarlo.</p>\r\n" + "<p>Atte.-</p>";
	}

	public void prepararCorreoObs() {
		textoCorreoObs = "<p><strong>AUDIGOES LE INFORMA:</strong></p>"
				+ "<p>Se han enviado las observaciones del programa de ejecuci&oacute;n "
				+ "correspondiente a la auditor&iacute;a <strong>"
				+ getRegistro().getAuditoria().getTipoAuditoria().getTpaAcronimo() + "-"
				+ getRegistro().getAuditoria().getAudAnio() + "-" + getRegistro().getAuditoria().getAudCorrelativo()
				+ "</strong> por lo que se le pide ingresar al sistema para solventarlas.</p>\r\n" + "<p>Atte.-</p>";
	}

	public void prepararCorreoFin() {
		textoCorreoFin = "<p><strong>AUDIGOES LE INFORMA:</strong></p>"
				+ "<p>Se ha finalizado la revisión del programa de ejecuci&oacute;n "
				+ "correspondiente a la auditor&iacute;a <strong>"
				+ getRegistro().getAuditoria().getTipoAuditoria().getTpaAcronimo() + "-"
				+ getRegistro().getAuditoria().getAudAnio() + "-" + getRegistro().getAuditoria().getAudCorrelativo()
				+ "</strong> por lo que puede proceder con el desarrollo de la fase de ejecuci&oacute;n</p>\r\n"
				+ "<p>Atte.-</p>";
	}

	public void onEnviarRevision() {
		Usuario usr = buscarCoordinador(getRegistro().getAuditoria());
		if (usr != null) {
			try {
				getRegistro().setPreEstado(2);
				System.out.println("status");
				onSave();
				correoObservacion(textoCorreo, getRegistro().getUsuario1(), usr);
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
				getRegistro().setPreEstado(1);
				getRegistro().setUsuario2(getObjAppsSession().getUsuario());
				getRegistro().setPreFechaReviso(getToday());
				System.out.println("status");
				onSave();
				correoRevision(textoCorreoObs, getRegistro().getUsuario1(), usr);
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
				getRegistro().setPreEstado(3);
				getRegistro().setUsuario2(getObjAppsSession().getUsuario());
				getRegistro().setPreFechaReviso(getToday());
				System.out.println("status");
				onSave();
				correoFin(textoCorreoFin, getRegistro().getUsuario1(), usr);
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

	@Override
	public void onRowSelect() {
		super.onRowSelect();
		System.out.println("" + getRegistro().getPreNombre());

		proejeMB.setPrograma(getRegistro());
		proejeMB.fillProcedimientos();
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

	@SuppressWarnings("unchecked")
	public void fillPrograma() {
		try {
//			setListado((List<ProgramaPlanificacion>) audigoesLocal.findByNamedQuery(ProgramaPlanificacion.class,
//					"programa.by.auditoria",
//					new Object[] { auditoria.getAudId() }));
			Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			setAuditoria(((Auditoria) sessionMap.get("auditoria")));

			setListado((List<ProgramaEjecucion>) audigoesLocal.findByNamedQuery(ProgramaEjecucion.class,
					"programa.ejecucion.by.auditoria", new Object[] { auditoria.getAudId() }));

			if (!getListado().isEmpty()) {
				setRegistro(getListado().get(0));
				proejeMB.setPrograma(getRegistro());
				proejeMB.fillProcedimientos();
				onEdit();
			} else {
				System.out.println("no hay");
				onNew();
//				getRegistro().setAuditoria(auditoria);
//				getRegistro().setFecCrea(getToday());
//				getRegistro().setUsuCrea(getObjAppsSession().getUsuario().getUsuUsuario());
//				getRegistro().setRegActivo(1);
//				getRegistro().setUsuario1(getObjAppsSession().getUsuario());
//				getRegistro().setPreFechaElaboro(getToday());
//				audigoesLocal.insert(getRegistro());
//				
//				proejeMB.setPrograma(getRegistro());
//				proejeMB.fillProcedimientos();
				// addWarn(new FacesMessage("Advertencia", "Auditoria No cuenta con Programa de
				// Planificación"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage("Advertencia", "Auditoria No cuenta con Programa de Ejecucion"));
		}
	}

	public void iniciarPrograma() {
		try {
			onNew();
			getRegistro().setAuditoria(auditoria);
			getRegistro().setFecCrea(getToday());
			getRegistro().setUsuCrea(getObjAppsSession().getUsuario().getUsuUsuario());
			getRegistro().setRegActivo(1);
			getRegistro().setUsuario1(getObjAppsSession().getUsuario());
			getRegistro().setPreFechaElaboro(getToday());
			audigoesLocal.insert(getRegistro());

			proejeMB.setPrograma(getRegistro());
			proejeMB.fillProcedimientos();
			addInfo(new FacesMessage("Confirmación", "Programa de ejecución iniciado correctamente"));
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage("Advertencia", "Error en el inicio de un nuevo programa!"));
		}
	}

	public void utilizarPrograma() {
		try {
			ProgramaEjecucion nuevoPrograma = new ProgramaEjecucion();
			nuevoPrograma.setAuditoria(auditoria);
			nuevoPrograma.setFecCrea(getToday());
			nuevoPrograma.setUsuCrea(getObjAppsSession().getUsuario().getUsuUsuario());
			nuevoPrograma.setRegActivo(1);
			nuevoPrograma.setUsuario1(getObjAppsSession().getUsuario());
			nuevoPrograma.setPreFechaElaboro(getToday());
			nuevoPrograma.setPreNombre("Copia de " + getRegistro().getPreNombre());
			nuevoPrograma.setPreObjE(getRegistro().getPreObjE());
			nuevoPrograma.setPreObjG(getRegistro().getPreObjG());
			nuevoPrograma.setPreFechaInicio(getRegistro().getPreFechaInicio());
			nuevoPrograma.setPreFechaFin(getRegistro().getPreFechaFin());
			nuevoPrograma.setPreEstado(1);
			audigoesLocal.insert(nuevoPrograma);

			ProcedimientoEjecucion nuevoProcedimiento;
			for (ProcedimientoEjecucion p : proejeMB.getListado()) {
				nuevoProcedimiento = new ProcedimientoEjecucion();
				nuevoProcedimiento.setProgramaEjecucion(nuevoPrograma);
				nuevoProcedimiento.setPejDescripcion(p.getPejDescripcion());
				nuevoProcedimiento.setPejNombre(p.getPejNombre());
				nuevoProcedimiento.setFecCrea(getToday());
				nuevoProcedimiento.setUsuario1(getObjAppsSession().getUsuario());
				nuevoProcedimiento.setUsuCrea(getObjAppsSession().getUsuario().getUsuUsuario());
				nuevoProcedimiento.setPejFechaElaboro(getToday());
				nuevoProcedimiento.setPejEstado(1);
				nuevoProcedimiento.setRegActivo(1);
				
				audigoesLocal.insert(nuevoProcedimiento);
			}
			
			fillPrograma();
			
			addInfo(new FacesMessage("Confirmación","Programa migrado correctamente!"));
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage("Advertencia", "Error en el inicio de un nuevo programa!"));
		}
	}

	@SuppressWarnings("unchecked")
	public void buscarProgramas() {
		try {
			setProgramasList((List<ProgramaEjecucion>) audigoesLocal.findByNamedQuery(ProgramaEjecucion.class,
					"list.programas.ejecucion", new Object[] {}));
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage("Advertencia", "Auditoria No cuenta con Programa de Ejecucion"));
		}
	}

	public void showAuditorias() {
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
		if (filterText == null || filterText.equals("")) {
			return true;
		}

		ProgramaEjecucion programa = (ProgramaEjecucion) value;
		return programa.getPreNombre().toLowerCase().contains(filterText);
	}

	@Override
	public void afterCancel() {
		super.afterCancel();
	}

	@Override
	public boolean beforeSaveNew() {
		return super.beforeSaveNew();
	}

	/* GETS y SETS */

	@Override
	public ProgramaEjecucion getRegistro() {
		return (ProgramaEjecucion) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProgramaEjecucion> getListado() {
		return (List<ProgramaEjecucion>) super.getListado();
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

	public List<ProgramaEjecucion> getFilteredPrograma() {
		return filteredPrograma;
	}

	public void setFilteredPrograma(List<ProgramaEjecucion> filteredPrograma) {
		this.filteredPrograma = filteredPrograma;
	}

	public Auditoria getAuditoria() {
		return auditoria;
	}

	public void setAuditoria(Auditoria auditoria) {
		this.auditoria = auditoria;
	}

	public ProcedimientosEjeMB getProejeMB() {
		return proejeMB;
	}

	public void setProejeMB(ProcedimientosEjeMB proejeMB) {
		this.proejeMB = proejeMB;
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

}
