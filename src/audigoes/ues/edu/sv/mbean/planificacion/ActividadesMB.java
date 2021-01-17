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
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;
import audigoes.ues.edu.sv.entities.planificacion.Actividad;
import audigoes.ues.edu.sv.entities.planificacion.ProcedimientoPlanificacion;

@ManagedBean(name = "actMB")
@ViewScoped
public class ActividadesMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<ProcedimientoPlanificacion> filteredProcedimiento;
	private Auditoria auditoria;

	@ManagedProperty(value = "#{bitaMB}")
	private BitacoraActividadMB bitaMB = new BitacoraActividadMB();

	public ActividadesMB() {
		super(new Actividad());
	}

	@PostConstruct
	public void init() {
		try {
			//super.init();

			fillActividades();
			if (getListado() != null) {
				if (getListado().size() == 0) {
					inicializarActividades();
					fillActividades();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDelete() {
		// TODO Auto-generated method stub
		super.onDelete();
	}

	@SuppressWarnings("unchecked")
	public void fillActividades() {
		try {
			Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			setAuditoria(((Auditoria) sessionMap.get("auditoria")));
			if (auditoria != null) {
				setListado((List<Actividad>) audigoesLocal.findByNamedQuery(Actividad.class, "actividades.by.auditoria",
						new Object[] { auditoria.getAudId() }));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void inicializarActividades() {
		try {
			Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			setAuditoria(((Auditoria) sessionMap.get("auditoria")));
			Actividad a;

			a = new Actividad();
			a.setActNombre("Elaboraci�n Programa de Planificaci�n");
			a.setActDescripcion("Elaboraci�n, revisi�n y aprobaci�n del programa de planificaci�n de auditor�a");
			a.setFecCrea(getToday());
			a.setActFecIni(getToday());
			a.setUsuCrea(getObjAppsSession().getUsuario().getUsuUsuario());
			a.setActTipo(2);
			a.setAuditoria(auditoria);
			a.setRegActivo(1);

			audigoesLocal.insert(a);

			a = new Actividad();
			a.setActNombre("Elaboraci�n Memorando de Planificaci�n");
			a.setActDescripcion("Elaboraci�n, revisi�n y aprobaci�n del memorando de planificaci�n de auditor�a");
			a.setFecCrea(getToday());
			a.setActFecIni(getToday());
			a.setUsuCrea(getObjAppsSession().getUsuario().getUsuUsuario());
			a.setActTipo(2);
			a.setAuditoria(auditoria);
			a.setRegActivo(1);

			audigoesLocal.insert(a);

			a = new Actividad();
			a.setActNombre("Elaboraci�n Programa de Auditor�a");
			a.setActDescripcion(
					"Elaboraci�n, revisi�n y aprobaci�n del programa de auditoria de auditor�a (programa de ejecuci�n)");
			a.setFecCrea(getToday());
			a.setActFecIni(getToday());
			a.setUsuCrea(getObjAppsSession().getUsuario().getUsuUsuario());
			a.setActTipo(2);
			a.setAuditoria(auditoria);
			a.setRegActivo(1);

			audigoesLocal.insert(a);

			a = new Actividad();
			a.setActNombre("Desarrollo de procedimientos de ejecuci�n");
			a.setActDescripcion(
					"An�lisis, recolecci�n de evidencia, redacci�n de narrativas para el desarrollo de los procedimientos de auditor�a");
			a.setFecCrea(getToday());
			a.setActFecIni(getToday());
			a.setUsuCrea(getObjAppsSession().getUsuario().getUsuUsuario());
			a.setActTipo(2);
			a.setAuditoria(auditoria);
			a.setRegActivo(1);

			audigoesLocal.insert(a);

			a = new Actividad();
			a.setActNombre("Comunicaci�n de hallazgos preliminares");
			a.setActDescripcion(
					"Elaboraci�n, revisi�n y aprobaci�n de lista de hallazgos para posterior comunicaci�n a la unidad auditada");
			a.setFecCrea(getToday());
			a.setActFecIni(getToday());
			a.setUsuCrea(getObjAppsSession().getUsuario().getUsuUsuario());
			a.setActTipo(2);
			a.setAuditoria(auditoria);
			a.setRegActivo(1);

			audigoesLocal.insert(a);

			a = new Actividad();
			a.setActNombre("An�lisis de comentarios de unidad");
			a.setActDescripcion("An�lisis y valorizaci�n de los comentarios brindados por la unidad auditada");
			a.setFecCrea(getToday());
			a.setUsuCrea(getObjAppsSession().getUsuario().getUsuUsuario());
			a.setActTipo(2);
			a.setActFecIni(getToday());
			a.setAuditoria(auditoria);
			a.setRegActivo(1);

			audigoesLocal.insert(a);

			a = new Actividad();
			a.setActNombre("Elaboraci�n de borrador de informe");
			a.setActDescripcion("Elaboraci�n, revisi�n y aprobaci�n de borrador de informe de auditor�a");
			a.setFecCrea(getToday());
			a.setActFecIni(getToday());
			a.setUsuCrea(getObjAppsSession().getUsuario().getUsuUsuario());
			a.setActTipo(2);
			a.setAuditoria(auditoria);
			a.setRegActivo(1);

			audigoesLocal.insert(a);

			a = new Actividad();
			a.setActNombre("Convocatoria a lectura de borrador de informe");
			a.setActDescripcion("Convocatoria para lectura del borrador de informe y levantamiento de acta de lectura");
			a.setFecCrea(getToday());
			a.setActFecIni(getToday());
			a.setUsuCrea(getObjAppsSession().getUsuario().getUsuUsuario());
			a.setActTipo(2);
			a.setAuditoria(auditoria);
			a.setRegActivo(1);

			audigoesLocal.insert(a);

			a = new Actividad();
			a.setActNombre("Elaboraci�n de infome");
			a.setActDescripcion("Elaboraci�n, revisi�n y aprobaci�n del informe de auditor�a");
			a.setFecCrea(getToday());
			a.setActFecIni(getToday());
			a.setUsuCrea(getObjAppsSession().getUsuario().getUsuUsuario());
			a.setActTipo(2);
			a.setAuditoria(auditoria);
			a.setRegActivo(1);

			audigoesLocal.insert(a);
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage("Error al incializar lista de actividades"));
		}
	}

	public void iniciarPlanificacion() {
		try {
			auditoria.setAudFechaPlanificacion(getToday());
			auditoria.setAudFase(2);
			if (audigoesLocal.update(auditoria) != null) {
				bitaMB.finalizarActividad(2, auditoria, getObjAppsSession().getUsuario());
			}
			addInfo(new FacesMessage("Fase de planificaci�n iniciada correctamente"));
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

	@Override
	public void afterSave() {
		super.afterSave();
	}

	public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
		if (filterText == null || filterText.equals("")) {
			return true;
		}

		Actividad actividad = (Actividad) value;
		return actividad.getActNombre().toLowerCase().contains(filterText);
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
	}

	@Override
	public boolean beforeSaveNew() {
		getRegistro().setAuditoria(auditoria);
		getRegistro().setActTipo(1);
		return super.beforeSaveNew();
	}

	/* GETS y SETS */

	@Override
	public Actividad getRegistro() {
		return (Actividad) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Actividad> getListado() {
		return (List<Actividad>) super.getListado();
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

	public BitacoraActividadMB getBitaMB() {
		return bitaMB;
	}

	public void setBitaMB(BitacoraActividadMB bitaMB) {
		this.bitaMB = bitaMB;
	}

}
