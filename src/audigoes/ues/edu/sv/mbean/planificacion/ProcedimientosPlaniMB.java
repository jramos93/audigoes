package audigoes.ues.edu.sv.mbean.planificacion;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.ws.rs.GET;

import org.primefaces.event.FileUploadEvent;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;
import audigoes.ues.edu.sv.entities.planificacion.ProcedimientoPlanificacion;
import audigoes.ues.edu.sv.entities.planificacion.ProgramaPlanificacion;
import audigoes.ues.edu.sv.mbeans.administracion.ArchivoMB;

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

	@ManagedProperty(value = "#{arcMB}")
	private ArchivoMB arcMB = new ArchivoMB();

	public ProcedimientosPlaniMB() {
		super(new ProcedimientoPlanificacion());
	}

	@PostConstruct
	public void init() {
		try {
			super.init();
			// fillProcedimientos();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void fillProcedimientos() {
		try {
			setListado(
					(List<ProcedimientoPlanificacion>) audigoesLocal.findByNamedQuery(ProcedimientoPlanificacion.class,
							"procedimientos.planificacion.by.programa", new Object[] { programa.getPrpId() }));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handleFileUpload(FileUploadEvent event) {
		try {
			System.out.println(" archivo "+event.getFile().getFileName());
			if (getStatus().equals("NEW")) {
				onSave();
				onEdit();
			} 
			this.arcMB.onNew();
			this.arcMB.getRegistro().setProcedimientoPlanificacion(getRegistro());
			this.arcMB.getRegistro().setArcArchivo(event.getFile().getContent());
			this.arcMB.getRegistro().setArcNombre(event.getFile().getFileName());
			this.arcMB.getRegistro().setArcExt(event.getFile().getContentType());
			this.arcMB.getRegistro().setFecCrea(getToday());
			this.arcMB.getRegistro().setUsuCrea(getObjAppsSession().getUsuario().getUsuUsuario());
			this.arcMB.getRegistro().setRegActivo(1);
			audigoesLocal.insert(this.arcMB.getRegistro());
			//this.arcMB.afterSaveNew();
			this.arcMB.getListado().add(this.arcMB.getRegistro());
			if (!getStatus().equals("NEW")) {
				addWarn(new FacesMessage(SYSTEM_NAME, "Archivo Guardado con Éxito"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Problemas al guardar archivo."));
		}

	}

	public void showAuditorias() {
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void afterSave() {
		super.afterSave();
		onEdit();
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
	public void afterNew() {
		// TODO Auto-generated method stub
		super.afterNew();
	}
	
	@Override
	protected void afterEdit() {
		// TODO Auto-generated method stub
		super.afterEdit();
		arcMB.fillByPlanificacion(getRegistro());
	}

	@Override
	public boolean beforeSaveNew() {
		getRegistro().setProFechaElaboro(getToday());
		getRegistro().setUsuario1(getObjAppsSession().getUsuario());
		getRegistro().setProgramaPlanificacion(programa);
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

	public ArchivoMB getArcMB() {
		return arcMB;
	}

	public void setArcMB(ArchivoMB arcMB) {
		this.arcMB = arcMB;
	}

}
