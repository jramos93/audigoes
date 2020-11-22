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
import audigoes.ues.edu.sv.entities.ejecucion.ProcedimientoEjecucion;
import audigoes.ues.edu.sv.entities.ejecucion.ProgramaEjecucion;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;
import audigoes.ues.edu.sv.entities.planificacion.ProcedimientoPlanificacion;
import audigoes.ues.edu.sv.entities.planificacion.ProgramaPlanificacion;
import audigoes.ues.edu.sv.mbeans.administracion.ArchivoMB;

@ManagedBean(name = "proejeMB")
@ViewScoped
public class ProcedimientosEjeMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<ProcedimientoEjecucion> filteredProcedimiento;
	private Auditoria auditoria;
	private ProgramaEjecucion programa;
	
	@ManagedProperty(value = "#{arcMB}")
	private ArchivoMB arcMB = new ArchivoMB();

	public ProcedimientosEjeMB() {
		super(new ProcedimientoEjecucion());
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
			setListado((List<ProcedimientoEjecucion>) audigoesLocal.findByNamedQuery(ProcedimientoEjecucion.class,
					"procedimientos.ejecucion.by.programa",
					new Object[] { programa.getPreId() }));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void afterEdit() {
		// TODO Auto-generated method stub
		super.afterEdit();
		arcMB.fillByEjecucion(getRegistro());
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

		ProcedimientoEjecucion proc = (ProcedimientoEjecucion) value;
		return proc.getPejNombre().toLowerCase().contains(filterText);
	}
	
	@Override
	public void afterCancel() {
		super.afterCancel();
	}
	
	@Override
	public boolean beforeSaveNew() {
		getRegistro().setUsuario1(getObjAppsSession().getUsuario());
		getRegistro().setProgramaEjecucion(programa);
		getRegistro().setPejFechaElaboro(getToday());
		return super.beforeSaveNew();
	}
	
	public void handleFileUpload(FileUploadEvent event) {
		try {
			System.out.println(" archivo "+event.getFile().getFileName());
			if (getStatus().equals("NEW")) {
				onSave();
				onEdit();
			} 
			this.arcMB.onNew();
			this.arcMB.getRegistro().setProcedimientoEjecucion(getRegistro());
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

	/* GETS y SETS */

	@Override
	public ProcedimientoEjecucion getRegistro() {
		return (ProcedimientoEjecucion) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProcedimientoEjecucion> getListado() {
		return (List<ProcedimientoEjecucion>) super.getListado();
	}

	@Override
	public void afterSaveNew() {
		getListado().add(getRegistro());
		super.afterSaveNew();
	}

	public List<ProcedimientoEjecucion> getFilteredProcedimiento() {
		return filteredProcedimiento;
	}

	public void setFilteredProcedimiento(List<ProcedimientoEjecucion> filteredProcedimiento) {
		this.filteredProcedimiento = filteredProcedimiento;
	}

	public Auditoria getAuditoria() {
		return auditoria;
	}

	public void setAuditoria(Auditoria auditoria) {
		this.auditoria = auditoria;
	}

	public ProgramaEjecucion getPrograma() {
		return programa;
	}

	public void setPrograma(ProgramaEjecucion programa) {
		this.programa = programa;
	}

	public ArchivoMB getArcMB() {
		return arcMB;
	}

	public void setArcMB(ArchivoMB arcMB) {
		this.arcMB = arcMB;
	}

	
	
}
