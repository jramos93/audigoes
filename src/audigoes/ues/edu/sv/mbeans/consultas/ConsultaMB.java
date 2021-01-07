package audigoes.ues.edu.sv.mbeans.consultas;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.administracion.Unidad;
import audigoes.ues.edu.sv.entities.ejecucion.ProcedimientoEjecucion;
import audigoes.ues.edu.sv.entities.ejecucion.ProgramaEjecucion;
import audigoes.ues.edu.sv.entities.informe.CedulaNota;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;
import audigoes.ues.edu.sv.entities.planeacion.AuditoriaResponsable;
import audigoes.ues.edu.sv.entities.planificacion.Actividad;
import audigoes.ues.edu.sv.entities.planificacion.ProcedimientoPlanificacion;
import audigoes.ues.edu.sv.entities.planificacion.ProgramaPlanificacion;
import audigoes.ues.edu.sv.entities.seguimiento.Recomendacion;
import audigoes.ues.edu.sv.entities.seguimiento.Seguimiento;
import audigoes.ues.edu.sv.entities.vistas.StatsAuditoriaEstado;

@ManagedBean(name = "consMB")
@ViewScoped
public class ConsultaMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Auditoria auditoria;
	private CedulaNota cedulaNota;
	private ProgramaPlanificacion programaPlanificacion;
	private ProgramaEjecucion programaEjecucion;
	// private Seguimiento seguimiento;

	private List<Unidad> filteredUnidades;

	private List<Auditoria> auditoriasList;
	private List<Auditoria> filteredAuditorias;

	private List<CedulaNota> cedulaNotasList;
	private List<CedulaNota> filteredCedulaNotas;

	/* Listado para mostrar hallazgos de auditoría seleccionada */
	private List<CedulaNota> hallazgoAuditoriaList;

	/* Listado para mostrar todos los hallazgos sin finalizar */
	private List<CedulaNota> hallazgosList;
	private List<CedulaNota> filteredHallazgosList;

	private List<Recomendacion> recomendacionesList;

	private List<Actividad> actividadesList;
	private List<AuditoriaResponsable> auditoriaResponsablesList;

	private List<ProcedimientoPlanificacion> procedimientosPlanificacionList;

	private List<ProcedimientoEjecucion> procedimientosEjecucionList;

	private List<StatsAuditoriaEstado> statsList;

	private Date fechaInicio;
	private Date fechaFin;

	private String strFechaInicio;
	private String strFechaFin;

	private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	public ConsultaMB() {
		// super(new Unidad());
	}

	@PostConstruct
	public void init() {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.set(GregorianCalendar.getInstance().get(Calendar.YEAR), Calendar.JANUARY, 1);
			setFechaInicio(calendar.getTime());
			calendar.set(GregorianCalendar.getInstance().get(Calendar.YEAR), Calendar.DECEMBER, 31);
			setFechaFin(calendar.getTime());
			fillAuditoriasNoFinalizadas();
			fillHallasgosNoFinalizados();
			// fillAuditoriasList();
			// fillStatsAuditoriaEstado();
			// super.init();
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Nos se pudieron cargar los datos de consulta"));
		}
	}

	@SuppressWarnings("unchecked")
	public void fillAuditoriasNoFinalizadas() {
		try {
			setAuditoriasList(
					(List<Auditoria>) audigoesLocal.findByNamedQuery(Auditoria.class, "consulta.auditorias.institucion",
							new Object[] { getObjAppsSession().getUsuario().getInstitucion().getInsId(),
									getFechaInicio(), getFechaFin() }));
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Problema al cargar datos de auditorías"));
		}
	}

	/*
	 * @SuppressWarnings("unchecked") public void fillAuditoriasList() { try {
	 * setAuditoriasList( (List<Auditoria>)
	 * audigoesLocal.findByNamedQuery(Auditoria.class,
	 * "consulta.auditorias.institucion", new Object[] {
	 * getObjAppsSession().getUsuario().getInstitucion().getInsId(),
	 * getFechaInicio(), getFechaFin() })); } catch (Exception e) {
	 * e.printStackTrace(); addWarn(new FacesMessage(SYSTEM_NAME,
	 * "Problema al cargar datos de auditorías")); } }
	 */

	/*
	 * public void fillStatsAuditoriaEstado() { try {
	 * 
	 * } catch (Exception e) { // TODO: handle exception } }
	 */

	/*
	 * @SuppressWarnings("unchecked") public void fillHallazgosList() { try {
	 * setCedulaNotasList((List<CedulaNota>)
	 * audigoesLocal.findByNamedQuery(CedulaNota.class,
	 * "consulta.cedulanota.institucion", new Object[] {
	 * getObjAppsSession().getUsuario().getInstitucion().getInsId(),
	 * getFechaInicio(), getFechaFin() })); } catch (Exception e) {
	 * e.printStackTrace(); } }
	 */
	@SuppressWarnings("unchecked")
	public void fillHallasgosNoFinalizados() {
		try {
			setHallazgosList((List<CedulaNota>) audigoesLocal.findByNamedQuery(CedulaNota.class,
					"consulta.hallazgos.no.finalizados",
					new Object[] { getObjAppsSession().getUsuario().getInstitucion().getInsId() }));
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Problema al cargar datos de consulta de hallazgos"));
		}
	}

	@SuppressWarnings("unchecked")
	public void fillHallazgosAuditoria() {
		try {
			setHallazgoAuditoriaList((List<CedulaNota>) audigoesLocal.findByNamedQuery(CedulaNota.class,
					"consulta.notas.by.auditoria", new Object[] { getAuditoria().getAudId() }));
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Problema al cargar hallazgos de la auditoría seleccionada"));
		}
	}

	@SuppressWarnings("unchecked")
	public void fillActividadesList() {
		try {
			setActividadesList((List<Actividad>) audigoesLocal.findByNamedQuery(Actividad.class,
					"consulta.actividades.list.auditoria", new Object[] { getAuditoria().getAudId() }));
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Problema al obtener listado de actividades"));
		}
	}

	@SuppressWarnings("unchecked")
	public void fillAuditoriaResponsablesList() {
		try {
			setAuditoriaResponsablesList(
					(List<AuditoriaResponsable>) audigoesLocal.findByNamedQuery(AuditoriaResponsable.class,
							"consulta.responsables.auditoria", new Object[] { getAuditoria().getAudId() }));
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Problema al obtener personal asignado a la auditoría"));
		}
	}

	@SuppressWarnings("unchecked")
	public void obtenerProgramaPlanificacion() {
		List<ProgramaPlanificacion> programaListado;
		try {
			programaListado = (List<ProgramaPlanificacion>) audigoesLocal.findByNamedQuery(ProgramaPlanificacion.class,
					"consulta.programa.by.auditoria", new Object[] { getAuditoria().getAudId() });
			if (!programaListado.isEmpty()) {
				setProgramaPlanificacion(programaListado.get(0));
			} else {
				setProgramaPlanificacion(new ProgramaPlanificacion());
			}
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Problema al cargar programa de planificación"));
		}
	}

	@SuppressWarnings("unchecked")
	public void fillProcedimientosPlanificacionList() {
		try {
			setProcedimientosPlanificacionList((List<ProcedimientoPlanificacion>) audigoesLocal.findByNamedQuery(
					ProcedimientoPlanificacion.class, "consulta.procedimientos.planificacion.by.programa",
					new Object[] { getProgramaPlanificacion().getPrpId() }));
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Problema al cargar procedimientos de planificación"));
		}
	}

	@SuppressWarnings("unchecked")
	public void obtenerProgramaEjecucion() {
		List<ProgramaEjecucion> programaListado;
		try {
			programaListado = (List<ProgramaEjecucion>) audigoesLocal.findByNamedQuery(ProgramaEjecucion.class,
					"consulta.programa.ejecucion.by.auditoria", new Object[] { getAuditoria().getAudId() });
			if (!programaListado.isEmpty()) {
				setProgramaEjecucion(programaListado.get(0));
			} else {
				setProgramaEjecucion(new ProgramaEjecucion());
			}
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Problema al cargar programa de ejecución"));
		}
	}

	@SuppressWarnings("unchecked")
	public void fillProcedimientosEjecucionList() {
		try {
			setProcedimientosEjecucionList((List<ProcedimientoEjecucion>) audigoesLocal.findByNamedQuery(
					ProcedimientoEjecucion.class, "consulta.procedimientos.ejecucion.by.programa",
					new Object[] { getProgramaEjecucion().getPreId() }));
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Problema al cargar procedimientos de ejecución"));
		}
	}

	@SuppressWarnings("unchecked")
	public void obtenerSeguimiento() {
		List<Seguimiento> segListado;
		try {
			segListado = (List<Seguimiento>) audigoesLocal.findByNamedQuery(Seguimiento.class,
					"consulta.seguimiento.by.auditoria", new Object[] { getAuditoria().getAudId() });
			if (!segListado.isEmpty()) {
				// setSeguimiento(segListado.get(0));
			} else {
				// setSeguimiento(new Seguimiento());
			}
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Problema en obtener seguimiento de la auditoría"));
		}
	}

	@SuppressWarnings("unchecked")
	public void fillRecomendacionesList() {
		try {
			setRecomendacionesList((List<Recomendacion>) audigoesLocal.findByNamedQuery(Recomendacion.class,
					"consulta.recomendacioens.by.cedula.nota", new Object[] { getCedulaNota().getCedId() }));
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Problema al cargar recomendaciones"));
		}
	}

	public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
		if (filterText == null || filterText.equals("")) {
			return true;
		}
		int filterInt = getInteger(filterText);

		Unidad unidad = (Unidad) value;
		return unidad.getUniNombre().toLowerCase().contains(filterText)
				|| unidad.getUniIniciales().toLowerCase().contains(filterText) || unidad.getUniId() == filterInt;
	}

	public void mostrarDetalleAuditoria() {
		setStatus("DETALLE_AUDITORIA");
		fillActividadesList();
		fillAuditoriaResponsablesList();
		obtenerProgramaPlanificacion();
		if (getProgramaPlanificacion().getPrpId() > 0) {
			fillProcedimientosPlanificacionList();
		}
		obtenerProgramaEjecucion();
		if (getProgramaEjecucion().getPreId() > 0) {
			fillProcedimientosEjecucionList();
		}
		fillHallazgosAuditoria();
		/*
		 * obtenerSeguimiento(); if (getSeguimiento().getSegId() > 0) {
		 * fillRecomendacionesList(); }
		 */

	}

	public void mostrarRecomendacionesHallazgo() {
		setStatus("RECOMENDACIONES_HALLAZGO");
		fillRecomendacionesList();
	}

	private int getInteger(String string) {
		try {
			return Integer.valueOf(string);
		} catch (NumberFormatException ex) {
			return 0;
		}
	}

	public void onCancelRecomendaciones() {
		setStatus("DETALLE_AUDITORIA");
	}

	@Override
	public void onCancel() {
		setStatus("SEARCH");
		super.onCancel();
	}

	/* GETS y SETS */

	@Override
	public Unidad getRegistro() {
		return (Unidad) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Unidad> getListado() {
		return (List<Unidad>) super.getListado();
	}

	@Override
	public boolean beforeSaveNew() {
		getRegistro().setInstitucion(getObjAppsSession().getUsuario().getInstitucion());
		return super.beforeSaveNew();
	}

	@Override
	public void afterSaveNew() {
		getListado().add(getRegistro());
		super.afterSaveNew();
	}

	public List<Unidad> getFilteredUnidades() {
		return filteredUnidades;
	}

	public void setFilteredUnidades(List<Unidad> filteredUnidades) {
		this.filteredUnidades = filteredUnidades;
	}

	public List<Auditoria> getAuditoriasList() {
		return auditoriasList;
	}

	public void setAuditoriasList(List<Auditoria> auditoriasList) {
		this.auditoriasList = auditoriasList;
	}

	public List<Auditoria> getFilteredAuditorias() {
		return filteredAuditorias;
	}

	public void setFilteredAuditorias(List<Auditoria> filteredAuditorias) {
		this.filteredAuditorias = filteredAuditorias;
	}

	public Auditoria getAuditoria() {
		return auditoria;
	}

	public void setAuditoria(Auditoria auditoria) {
		this.auditoria = auditoria;
	}

	public List<CedulaNota> getCedulaNotasList() {
		return cedulaNotasList;
	}

	public void setCedulaNotasList(List<CedulaNota> cedulaNotasList) {
		this.cedulaNotasList = cedulaNotasList;
	}

	public List<CedulaNota> getFilteredCedulaNotas() {
		return filteredCedulaNotas;
	}

	public void setFilteredCedulaNotas(List<CedulaNota> filteredCedulaNotas) {
		this.filteredCedulaNotas = filteredCedulaNotas;
	}

	public CedulaNota getCedulaNota() {
		return cedulaNota;
	}

	public void setCedulaNota(CedulaNota cedulaNota) {
		this.cedulaNota = cedulaNota;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public SimpleDateFormat getFormatter() {
		return formatter;
	}

	public void setFormatter(SimpleDateFormat formatter) {
		this.formatter = formatter;
	}

	public String getStrFechaInicio() {
		return strFechaInicio;
	}

	public void setStrFechaInicio(String strFechaInicio) {
		this.strFechaInicio = strFechaInicio;
	}

	public String getStrFechaFin() {
		return strFechaFin;
	}

	public void setStrFechaFin(String strFechaFin) {
		this.strFechaFin = strFechaFin;
	}

	public List<StatsAuditoriaEstado> getStatsList() {
		return statsList;
	}

	public void setStatsList(List<StatsAuditoriaEstado> statsList) {
		this.statsList = statsList;
	}

	public List<Actividad> getActividadesList() {
		return actividadesList;
	}

	public void setActividadesList(List<Actividad> actividadesList) {
		this.actividadesList = actividadesList;
	}

	public List<AuditoriaResponsable> getAuditoriaResponsablesList() {
		return auditoriaResponsablesList;
	}

	public void setAuditoriaResponsablesList(List<AuditoriaResponsable> auditoriaResponsablesList) {
		this.auditoriaResponsablesList = auditoriaResponsablesList;
	}

	public ProgramaPlanificacion getProgramaPlanificacion() {
		return programaPlanificacion;
	}

	public void setProgramaPlanificacion(ProgramaPlanificacion programaPlanificacion) {
		this.programaPlanificacion = programaPlanificacion;
	}

	public List<ProcedimientoPlanificacion> getProcedimientosPlanificacionList() {
		return procedimientosPlanificacionList;
	}

	public void setProcedimientosPlanificacionList(List<ProcedimientoPlanificacion> procedimientosPlanificacionList) {
		this.procedimientosPlanificacionList = procedimientosPlanificacionList;
	}

	public ProgramaEjecucion getProgramaEjecucion() {
		return programaEjecucion;
	}

	public List<ProcedimientoEjecucion> getProcedimientosEjecucionList() {
		return procedimientosEjecucionList;
	}

	public void setProgramaEjecucion(ProgramaEjecucion programaEjecucion) {
		this.programaEjecucion = programaEjecucion;
	}

	public void setProcedimientosEjecucionList(List<ProcedimientoEjecucion> procedimientosEjecucionList) {
		this.procedimientosEjecucionList = procedimientosEjecucionList;
	}

	public List<CedulaNota> getHallazgoAuditoriaList() {
		return hallazgoAuditoriaList;
	}

	public void setHallazgoAuditoriaList(List<CedulaNota> hallazgoAuditoriaList) {
		this.hallazgoAuditoriaList = hallazgoAuditoriaList;
	}

	/*
	 * public Seguimiento getSeguimiento() { return seguimiento; }
	 */

	public List<Recomendacion> getRecomendacionesList() {
		return recomendacionesList;
	}

	/*
	 * public void setSeguimiento(Seguimiento seguimiento) { this.seguimiento =
	 * seguimiento; }
	 */
	public void setRecomendacionesList(List<Recomendacion> recomendacionesList) {
		this.recomendacionesList = recomendacionesList;
	}

	public List<CedulaNota> getHallazgosList() {
		return hallazgosList;
	}

	public void setHallazgosList(List<CedulaNota> hallazgosList) {
		this.hallazgosList = hallazgosList;
	}

	public List<CedulaNota> getFilteredHallazgosList() {
		return filteredHallazgosList;
	}

	public void setFilteredHallazgosList(List<CedulaNota> filteredHallazgosList) {
		this.filteredHallazgosList = filteredHallazgosList;
	}
}
