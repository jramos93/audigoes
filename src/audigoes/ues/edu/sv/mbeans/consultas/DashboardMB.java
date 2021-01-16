package audigoes.ues.edu.sv.mbeans.consultas;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.chart.DonutChartModel;
import org.primefaces.model.chart.LineChartModel;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;
import audigoes.ues.edu.sv.entities.vistas.StatsAuditoriaEstado;
import audigoes.ues.edu.sv.entities.vistas.StatsHallazgoEstado;
import audigoes.ues.edu.sv.entities.vistas.TiempoPorFaseAuditorias;

@ManagedBean(name = "dashMB")
@ViewScoped
public class DashboardMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* Variables para manejo de fechas */
	private Date fechaInicio;
	private Date fechaFin;

	private String strFechaInicio;
	private String strFechaFin;

	private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	/* Gráficos */

	/* gráfico líneas, cantidad de auditorias por estado */
	private LineChartModel lineCharAuditoriasPorEstado;
	private DonutChartModel donutChartAuditoriasPorEstado;
	private DonutChartModel donulChartHallazgosPoeEstado;
	private List<StatsAuditoriaEstado> statAuditoriaEstadoList;
	private List<StatsHallazgoEstado> statHallazgoEstadoList;
	private List<TiempoPorFaseAuditorias> tiempoPorFaseAuditoriasList;

	public DashboardMB() {
		super(new Auditoria());
	}

	@PostConstruct
	public void init() {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.set(GregorianCalendar.getInstance().get(Calendar.YEAR), Calendar.JANUARY, 1);
			setFechaInicio(calendar.getTime());
			calendar.set(GregorianCalendar.getInstance().get(Calendar.YEAR), Calendar.DECEMBER, 31);
			setFechaFin(calendar.getTime());
			createDonutChartAuditoriasPorEstado();
			createDonutChartHallazgosPorEstado();
			obtenerTiemposPorFaseAuditorias();
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Nos se pudieron cargar los datos del dashboard"));
		}
	}

	@SuppressWarnings("unchecked")
	public void createLineModel() {
		try {
			setFormatter(new SimpleDateFormat("yyyy-MM-dd"));
			setStrFechaInicio(getFormatter().format(getFechaInicio()));
			setStrFechaFin(getFormatter().format(getFechaFin()));
			setStrFechaInicio(getFormatter().format(getFechaInicio()));
			setStrFechaFin(getFormatter().format(getFechaFin()));
			lineCharAuditoriasPorEstado = new LineChartModel();

			// LineChartSeries series = new LineChartSeries();

			setStatAuditoriaEstadoList((List<StatsAuditoriaEstado>) audigoesLocal.findByNamedQuery(
					StatsAuditoriaEstado.class, "consulta.stat.auditoria.estado",
					new Object[] { getObjAppsSession().getUsuario().getInstitucion().getInsId(), getStrFechaInicio(),
							getStrFechaFin() }));

		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Problema al obtener datos para gráfico de auditorias por estado"));
		}
	}

	@SuppressWarnings("unchecked")
	public void createDonutChartAuditoriasPorEstado() {
		setFormatter(new SimpleDateFormat("yyyy-MM-dd"));
		setStrFechaInicio(getFormatter().format(getFechaInicio()));
		setStrFechaFin(getFormatter().format(getFechaFin()));
		donutChartAuditoriasPorEstado = new DonutChartModel();
		Map<String, Number> circle = new LinkedHashMap<String, Number>();
		try {
			/* Obteniendo datos cantidad de auditorías por estado */
			setStatAuditoriaEstadoList((List<StatsAuditoriaEstado>) audigoesLocal.findByNamedQuery(
					StatsAuditoriaEstado.class, "consulta.stat.auditoria.estado",
					new Object[] { getObjAppsSession().getUsuario().getInstitucion().getInsId(), getStrFechaInicio(),
							getStrFechaFin() }));
			if (getStatAuditoriaEstadoList().size() > 0) {
				circle.put("Programada", getStatAuditoriaEstadoList().get(0).getProgramada());
				circle.put("Planificación", getStatAuditoriaEstadoList().get(0).getPlanificacion());
				circle.put("Ejecución", getStatAuditoriaEstadoList().get(0).getEjecucion());
				circle.put("Informe", getStatAuditoriaEstadoList().get(0).getInforme());
				circle.put("Seguimimiento", getStatAuditoriaEstadoList().get(0).getSeguimiento());
				circle.put("Finalizada", getStatAuditoriaEstadoList().get(0).getFinalizada());
			} else {
				circle.put("Programada", 0);
				circle.put("Planificación", 0);
				circle.put("Ejecución", 0);
				circle.put("Informe", 0);
				circle.put("Seguimimiento", 0);
				circle.put("Finalizada", 0);
			}
			donutChartAuditoriasPorEstado.addCircle(circle);
			donutChartAuditoriasPorEstado.setTitle("Auditorías por Estado");
			donutChartAuditoriasPorEstado.setLegendPosition("e");
			donutChartAuditoriasPorEstado.setSliceMargin(3);
			donutChartAuditoriasPorEstado.setDataFormat("value");
			donutChartAuditoriasPorEstado.setShowDataLabels(true);
			donutChartAuditoriasPorEstado.setShadow(true);
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Problema al obtener datos de cantidad de auditorias por estado"));
		}
	}

	@SuppressWarnings("unchecked")
	public void createDonutChartHallazgosPorEstado() {
		setFormatter(new SimpleDateFormat("yyyy-MM-dd"));
		setStrFechaInicio(getFormatter().format(getFechaInicio()));
		setStrFechaFin(getFormatter().format(getFechaFin()));
		donulChartHallazgosPoeEstado = new DonutChartModel();
		Map<String, Number> circle = new LinkedHashMap<String, Number>();
		try {
			/* Obteniendo datos cantidad de auditorías por estado */
			setStatHallazgoEstadoList((List<StatsHallazgoEstado>) audigoesLocal.findByNamedQuery(
					StatsHallazgoEstado.class, "consulta.cantidad.hallazgos.estado",
					new Object[] { getObjAppsSession().getUsuario().getInstitucion().getInsId(), getStrFechaInicio(),
							getStrFechaFin() }));
			if (getStatHallazgoEstadoList().size() > 0) {
				circle.put("Redacción", getStatHallazgoEstadoList().get(0).getRedaccion());
				circle.put("Revisión", getStatHallazgoEstadoList().get(0).getRevision());
				circle.put("Comunicar", getStatHallazgoEstadoList().get(0).getComunicar());
				circle.put("Comunicado", getStatHallazgoEstadoList().get(0).getComunicado());
				circle.put("Análisis", getStatHallazgoEstadoList().get(0).getAnalisis());
				circle.put("Finalizado", getStatHallazgoEstadoList().get(0).getFinalizado());
			} else {
				circle.put("Redacción", 0);
				circle.put("Revisión", 0);
				circle.put("Comunicar", 0);
				circle.put("Comunicado", 0);
				circle.put("Análisis", 0);
				circle.put("Finalizado", 0);
			}
			donulChartHallazgosPoeEstado.addCircle(circle);
			donulChartHallazgosPoeEstado.setTitle("Hallazgos por Estado");
			donulChartHallazgosPoeEstado.setLegendPosition("e");
			donulChartHallazgosPoeEstado.setSliceMargin(3);
			donulChartHallazgosPoeEstado.setDataFormat("value");
			donulChartHallazgosPoeEstado.setShowDataLabels(true);
			donulChartHallazgosPoeEstado.setShadow(true);
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Problema al obtener datos de cantidad de auditorias por estado"));
		}
	}

	@SuppressWarnings("unchecked")
	public void obtenerTiemposPorFaseAuditorias() {
		try {
			setTiempoPorFaseAuditoriasList((List<TiempoPorFaseAuditorias>) audigoesLocal.findByNamedQuery(
					TiempoPorFaseAuditorias.class, "consulta.tiempo.fases.auditoria",
					new Object[] { getObjAppsSession().getUsuario().getInstitucion().getInsId() }));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* GETS y SETS */

	@Override
	public Auditoria getRegistro() {
		return (Auditoria) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Auditoria> getListado() {
		return (List<Auditoria>) super.getListado();
	}

	@Override
	public boolean beforeSaveNew() {
		return super.beforeSaveNew();
	}

	@Override
	public void afterSaveNew() {
		super.afterSaveNew();
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

	public LineChartModel getLineCharAuditoriasPorEstado() {
		return lineCharAuditoriasPorEstado;
	}

	public void setLineCharAuditoriasPorEstado(LineChartModel lineCharAuditoriasPorEstado) {
		this.lineCharAuditoriasPorEstado = lineCharAuditoriasPorEstado;
	}

	public List<StatsAuditoriaEstado> getStatAuditoriaEstadoList() {
		return statAuditoriaEstadoList;
	}

	public void setStatAuditoriaEstadoList(List<StatsAuditoriaEstado> statAuditoriaEstadoList) {
		this.statAuditoriaEstadoList = statAuditoriaEstadoList;
	}

	public DonutChartModel getDonutChartAuditoriasPorEstado() {
		return donutChartAuditoriasPorEstado;
	}

	public void setDonutChartAuditoriasPorEstado(DonutChartModel donutChartAuditoriasPorEstado) {
		this.donutChartAuditoriasPorEstado = donutChartAuditoriasPorEstado;
	}

	public DonutChartModel getDonulChartHallazgosPoeEstado() {
		return donulChartHallazgosPoeEstado;
	}

	public void setDonulChartHallazgosPoeEstado(DonutChartModel donulChartHallazgosPoeEstado) {
		this.donulChartHallazgosPoeEstado = donulChartHallazgosPoeEstado;
	}

	public List<StatsHallazgoEstado> getStatHallazgoEstadoList() {
		return statHallazgoEstadoList;
	}

	public void setStatHallazgoEstadoList(List<StatsHallazgoEstado> statHallazgoEstadoList) {
		this.statHallazgoEstadoList = statHallazgoEstadoList;
	}

	public List<TiempoPorFaseAuditorias> getTiempoPorFaseAuditoriasList() {
		return tiempoPorFaseAuditoriasList;
	}

	public void setTiempoPorFaseAuditoriasList(List<TiempoPorFaseAuditorias> tiempoPorFaseAuditoriasList) {
		this.tiempoPorFaseAuditoriasList = tiempoPorFaseAuditoriasList;
	}

}
