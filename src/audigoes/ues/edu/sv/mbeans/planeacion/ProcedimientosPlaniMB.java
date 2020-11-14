package audigoes.ues.edu.sv.mbeans.planeacion;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;
import audigoes.ues.edu.sv.entities.planificacion.ProcedimientoPlanificacion;
import audigoes.ues.edu.sv.entities.planificacion.ProgramaPlanificacion;

@ManagedBean(name = "proplaMB")
@ViewScoped
public class ProcedimientosPlaniMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<ProcedimientoPlanificacion> filteredProcedimiento;
	private Auditoria auditoria;
	private ProgramaPlanificacion programa;

	public ProcedimientosPlaniMB() {
		super(new ProcedimientoPlanificacion());
	}

	@PostConstruct
	public void init() {
		try {
			super.init();
			//fillProcedimientos();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void fillProcedimientos() {
		try {
			setListado((List<ProcedimientoPlanificacion>) audigoesLocal.findByNamedQuery(ProcedimientoPlanificacion.class,
					"procedimientos.planificacion.by.programa",
					new Object[] { 1 }));
		} catch (Exception e) {
			e.printStackTrace();
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

		ProcedimientoPlanificacion proc = (ProcedimientoPlanificacion) value;
		return proc.getProNombre().toLowerCase().contains(filterText);
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
	public ProcedimientoPlanificacion getRegistro() {
		return (ProcedimientoPlanificacion) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProcedimientoPlanificacion> getListado() {
		return (List<ProcedimientoPlanificacion>) super.getListado();
	}

	@Override
	public void afterSaveNew() {
		getListado().add(getRegistro());
		super.afterSaveNew();
	}

	public List<ProcedimientoPlanificacion> getFilteredProcedimiento() {
		return filteredProcedimiento;
	}

	public void setFilteredProcedimiento(List<ProcedimientoPlanificacion> filteredProcedimiento) {
		this.filteredProcedimiento = filteredProcedimiento;
	}

	public Auditoria getAuditoria() {
		return auditoria;
	}

	public void setAuditoria(Auditoria auditoria) {
		this.auditoria = auditoria;
	}

	public ProgramaPlanificacion getPrograma() {
		return programa;
	}

	public void setPrograma(ProgramaPlanificacion programa) {
		this.programa = programa;
	}

	
	
}
