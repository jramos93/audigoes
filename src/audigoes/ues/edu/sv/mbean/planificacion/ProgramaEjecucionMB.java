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

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.ejecucion.ProgramaEjecucion;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;

@ManagedBean(name = "pejeMB")
@ViewScoped
public class ProgramaEjecucionMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<ProgramaEjecucion> filteredPrograma;
	private Auditoria auditoria;
	
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

	
	
}
