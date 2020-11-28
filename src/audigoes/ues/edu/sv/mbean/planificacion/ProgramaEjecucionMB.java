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
import audigoes.ues.edu.sv.entities.ejecucion.ProgramaEjecucion;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;
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
	private String textoCorreo="";
	
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
		textoCorreo="<p><strong>AUDIGOES LE INFORMA:</strong></p>" + 
				"<p>Se ha enviado para su revisi&oacute;n el programa de ejecuci&oacute;n "
				+ "correspondiente a la auditor&iacute;a <strong>"
				+getRegistro().getAuditoria().getTipoAuditoria().getTpaAcronimo()+"-"+getRegistro().getAuditoria().getAudAnio()+
				"-"+getRegistro().getAuditoria().getAudCorrelativo()
				+"</strong> por lo que se le pide ingresar al sistema para realizarlo.</p>\r\n"
				+"<p>Atte.-</p>";
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
			cc = "javiierramos93@gmail.com";
			to = "javiieramos93@gmail.com";
			subject = "Correo de Prueba";
			
			body = texto;
			logo = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/images/logo-azul.png");
			
			SendMailAttach mail = new SendMailAttach(from, cc, to, subject, body, null, logo);
			mail.send();
		} catch (Exception e) {
			// TODO: handle exception
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
			System.out.println("auditoria "+auditoria.getAudId());
			setListado((List<ProgramaEjecucion>) audigoesLocal.findByNamedQuery(ProgramaEjecucion.class,
					"programa.ejecucion.by.auditoria",
					new Object[] { auditoria.getAudId() }));
			if(!getListado().isEmpty()) {
				setRegistro(getListado().get(0));
				proejeMB.setPrograma(getRegistro());
				proejeMB.fillProcedimientos();
			} else {
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
				//addWarn(new FacesMessage("Advertencia", "Auditoria No cuenta con Programa de Planificación"));
			}
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
		return programa.getPreObjE().toLowerCase().contains(filterText);
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
		//getListado().add(getRegistro());
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

	
	
}
