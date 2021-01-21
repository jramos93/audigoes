package audigoes.ues.edu.sv.mbeans.consultas;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DonutChartModel;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.hbar.HorizontalBarChartDataSet;
import org.primefaces.model.charts.optionconfig.title.Title;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;
import audigoes.ues.edu.sv.entities.planeacion.PlanAnual;
import audigoes.ues.edu.sv.entities.vistas.DuracionAuditorias;
import audigoes.ues.edu.sv.entities.vistas.StatsAuditoriaEstado;
import audigoes.ues.edu.sv.entities.vistas.StatsHallazgoEstado;
import audigoes.ues.edu.sv.entities.vistas.TiempoPlanificadoVsTiempoEjecutado;
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

	private List<PlanAnual> planList;

	/* Gráficos */

	private LineChartModel lineCharAuditoriasPorEstado;
	private DonutChartModel donutChartAuditoriasPorEstado;
	private DonutChartModel donulChartHallazgosPoeEstado;
	private CartesianChartModel chartModelCompTiempos;
	private HorizontalBarChartModel barChartTotalVsFinalizadas;
	private HorizontalBarChartModel barChartTotalDuracionAuditorias;
	private List<StatsAuditoriaEstado> statAuditoriaEstadoList;
	private List<StatsHallazgoEstado> statHallazgoEstadoList;
	private List<TiempoPorFaseAuditorias> tiempoPorFaseAuditoriasList;
	private List<DuracionAuditorias> duracionList;

	/* renderer gráficos */
	private boolean renderAuditoriasPorEstado;
	private boolean renderHallazgosPorEstado;
	private boolean renderCompTiempos;
	private boolean renderTotalVsFinalizadas;
	private boolean renderTotalDuracionAuditorias;

	private int totalAuditoriasPlanificadas;
	private int totalAuditoriasFinalizadas;
	private int totalHallazgos;

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
			crearGraficos();
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Nos se pudieron carsgar los datos del dashboard"));
		}
	}

	public void crearGraficos() {
		createDonutChartAuditoriasPorEstado();
		createDonutChartHallazgosPorEstado();
		createChartModelCompTiempos();
		createBarChartTotalVsFinalizadas();
		createBarChartDuracionAuditorias();
		obtenerTiemposPorFaseAuditorias();

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
				setTotalAuditoriasFinalizadas(getStatAuditoriaEstadoList().get(0).getFinalizada().intValue());
				setTotalAuditoriasPlanificadas(getStatAuditoriaEstadoList().get(0).getProgramada()
						.add(getStatAuditoriaEstadoList().get(0).getAsignada())
						.add(getStatAuditoriaEstadoList().get(0).getPlanificacion())
						.add(getStatAuditoriaEstadoList().get(0).getEjecucion())
						.add(getStatAuditoriaEstadoList().get(0).getInforme())
						.add(getStatAuditoriaEstadoList().get(0).getSeguimiento())
						.add(getStatAuditoriaEstadoList().get(0).getFinalizada()).intValue());
				if (getTotalAuditoriasPlanificadas() == 0) {
					setRenderAuditoriasPorEstado(false);
				} else {
					setRenderAuditoriasPorEstado(true);

					circle.put("Programada", getStatAuditoriaEstadoList().get(0).getProgramada());
					circle.put("Asignada", getStatAuditoriaEstadoList().get(0).getAsignada());
					circle.put("Planificación", getStatAuditoriaEstadoList().get(0).getPlanificacion());
					circle.put("Ejecución", getStatAuditoriaEstadoList().get(0).getEjecucion());
					circle.put("Informe", getStatAuditoriaEstadoList().get(0).getInforme());
					circle.put("Seguimimiento", getStatAuditoriaEstadoList().get(0).getSeguimiento());
					circle.put("Finalizada", getStatAuditoriaEstadoList().get(0).getFinalizada());

					donutChartAuditoriasPorEstado.addCircle(circle);
					donutChartAuditoriasPorEstado.setTitle("Auditorías por Estado");
					donutChartAuditoriasPorEstado.setLegendPosition("e");
					donutChartAuditoriasPorEstado.setSliceMargin(3);
					donutChartAuditoriasPorEstado.setDataFormat("value");
					donutChartAuditoriasPorEstado.setShowDataLabels(true);
					donutChartAuditoriasPorEstado.setShadow(true);
				}
			} else {
				circle.put("Programada", 0);
				circle.put("Planificación", 0);
				circle.put("Ejecución", 0);
				circle.put("Informe", 0);
				circle.put("Seguimimiento", 0);
				circle.put("Finalizada", 0);
			}

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
				setTotalHallazgos(getStatHallazgoEstadoList().get(0).getRedaccion()
						.add(getStatHallazgoEstadoList().get(0).getRevision())
						.add(getStatHallazgoEstadoList().get(0).getComunicar())
						.add(getStatHallazgoEstadoList().get(0).getComunicado())
						.add(getStatHallazgoEstadoList().get(0).getAnalisis())
						.add(getStatHallazgoEstadoList().get(0).getFinalizado()).intValue());
				if (getTotalHallazgos() == 0) {
					setRenderHallazgosPorEstado(false);
				} else {
					setRenderHallazgosPorEstado(true);
					circle.put("Redacción", getStatHallazgoEstadoList().get(0).getRedaccion());
					circle.put("Revisión", getStatHallazgoEstadoList().get(0).getRevision());
					circle.put("Comunicar", getStatHallazgoEstadoList().get(0).getComunicar());
					circle.put("Comunicado", getStatHallazgoEstadoList().get(0).getComunicado());
					circle.put("Análisis", getStatHallazgoEstadoList().get(0).getAnalisis());
					circle.put("Finalizado", getStatHallazgoEstadoList().get(0).getFinalizado());

					donulChartHallazgosPoeEstado.addCircle(circle);
					donulChartHallazgosPoeEstado.setTitle("Hallazgos por Estado");
					donulChartHallazgosPoeEstado.setLegendPosition("e");
					donulChartHallazgosPoeEstado.setSliceMargin(3);
					donulChartHallazgosPoeEstado.setDataFormat("value");
					donulChartHallazgosPoeEstado.setShowDataLabels(true);
					donulChartHallazgosPoeEstado.setShadow(true);
				}

			} else {
				circle.put("Redacción", 0);
				circle.put("Revisión", 0);
				circle.put("Comunicar", 0);
				circle.put("Comunicado", 0);
				circle.put("Análisis", 0);
				circle.put("Finalizado", 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Problema al obtener datos de cantidad de hallazgos por estado"));
		}
	}

	public void createBarChartTotalVsFinalizadas() {
		try {
			int minAxis = 0;
			int maxAxis = getTotalAuditoriasPlanificadas();

			setBarChartTotalVsFinalizadas(new HorizontalBarChartModel());
			ChartSeries totalAuditorias = new ChartSeries();

			totalAuditorias.setLabel("Duración Auditorías");

			totalAuditorias.set("Finalizadas", getTotalAuditoriasFinalizadas());
			totalAuditorias.set("Planificadas", getTotalAuditoriasPlanificadas());
			if (getTotalAuditoriasPlanificadas() > 0) {
				setRenderTotalVsFinalizadas(true);
			} else {
				setRenderTotalVsFinalizadas(false);
			}

			getBarChartTotalVsFinalizadas().addSeries(totalAuditorias);
			getBarChartTotalVsFinalizadas().setTitle("Auditorias Planificadas vs Auditorias Finalizadas");
			getBarChartTotalVsFinalizadas().setLegendPosition("e");

			getBarChartTotalVsFinalizadas().setStacked(true);

			Axis xAxis = getBarChartTotalVsFinalizadas().getAxis(AxisType.X);
			xAxis.setLabel("Total Auditorias");
			xAxis.setMin(minAxis);
			xAxis.setMax(maxAxis + 10);

			Axis yAxis = getBarChartTotalVsFinalizadas().getAxis(AxisType.Y);
			yAxis.setLabel("Auditorías");
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Problema al obtener datos para gráfico Duración de Auditorías"));
		}

	}

	@SuppressWarnings("unchecked")
	public void createBarChartDuracionAuditorias() {
		setFormatter(new SimpleDateFormat("yyyy-MM-dd"));
		setStrFechaInicio(getFormatter().format(getFechaInicio()));
		setStrFechaFin(getFormatter().format(getFechaFin()));
		try {
			int minAxis = 0;
			int maxAxis = 0;

			setBarChartTotalDuracionAuditorias(new HorizontalBarChartModel());
			ChartSeries auditoriasSeries = new ChartSeries();

			setDuracionList((List<DuracionAuditorias>) audigoesLocal.findByNamedQuery(DuracionAuditorias.class,
					"consulta.duracion.auditorias.horas",
					new Object[] { getObjAppsSession().getUsuario().getInstitucion().getInsId(), 6, getStrFechaInicio(),
							getStrFechaFin(), getObjAppsSession().getUsuario().getInstitucion().getInsId(), 6,
							getStrFechaInicio(), getStrFechaFin() }));
			if (!getDuracionList().isEmpty()) {
				setRenderTotalDuracionAuditorias(true);
				for (DuracionAuditorias item : getDuracionList()) {
					auditoriasSeries.set(item.getAudCodigo(), item.getTiempoEjecutado());
					if (item.getTiempoEjecutado().intValue() > maxAxis) {
						maxAxis = item.getTiempoEjecutado().intValue();
					}
				}
			} else {
				setRenderTotalDuracionAuditorias(false);
			}

			getBarChartTotalDuracionAuditorias().addSeries(auditoriasSeries);
			getBarChartTotalDuracionAuditorias().setTitle("Duración auditorías");
			getBarChartTotalDuracionAuditorias().setLegendPosition("e");

			getBarChartTotalDuracionAuditorias().setStacked(true);

			Axis xAxis = getBarChartTotalDuracionAuditorias().getAxis(AxisType.X);
			xAxis.setLabel("Total Auditorias");
			xAxis.setMin(minAxis);
			xAxis.setMax(maxAxis + 10);

			Axis yAxis = getBarChartTotalVsFinalizadas().getAxis(AxisType.Y);
			yAxis.setLabel("Auditorías");
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Problemas al obtener datos para gráfico Duración Auditorías"));
		}

	}

	@SuppressWarnings("unchecked")
	public void createChartModelCompTiempos() {
		setFormatter(new SimpleDateFormat("yyyy-MM-dd"));
		setStrFechaInicio(getFormatter().format(getFechaInicio()));
		setStrFechaFin(getFormatter().format(getFechaFin()));

		chartModelCompTiempos = new BarChartModel();
		BarChartSeries barTiempoSerie = new BarChartSeries();
		LineChartSeries lineTiempoSerie = new LineChartSeries();

		barTiempoSerie.setLabel("Tiempo Ejecutado");
		lineTiempoSerie.setLabel("Tiempo Planificado");

		int minAxis = 0;
		int maxAxis = 0;

		try {
			List<TiempoPlanificadoVsTiempoEjecutado> diferenciaTiempos;
			diferenciaTiempos = (List<TiempoPlanificadoVsTiempoEjecutado>) audigoesLocal.findByNamedQuery(
					TiempoPlanificadoVsTiempoEjecutado.class, "consulta.tiempo.planificado.vs.ejecutado",
					new Object[] { getObjAppsSession().getUsuario().getInstitucion().getInsId(), getStrFechaInicio(),
							getStrFechaFin() });
			if (!diferenciaTiempos.isEmpty()) {
				setRenderCompTiempos(true);
				for (TiempoPlanificadoVsTiempoEjecutado row : diferenciaTiempos) {
					barTiempoSerie.set(row.getAudCodigo(), row.getTiempoEjecutado());
					lineTiempoSerie.set(row.getAudCodigo(), row.getTiempoProgramado());
					if (row.getTiempoEjecutado().intValue() > maxAxis) {
						maxAxis = row.getTiempoEjecutado().intValue();
					}
					if (row.getTiempoProgramado().intValue() > maxAxis) {
						maxAxis = row.getTiempoProgramado().intValue();
					}
				}
			} else {
				setRenderCompTiempos(false);
			}

			chartModelCompTiempos.addSeries(barTiempoSerie);
			chartModelCompTiempos.addSeries(lineTiempoSerie);

			chartModelCompTiempos.setTitle("Tiempo Planificado vs Tiempo Ejecutado");

			chartModelCompTiempos.setLegendPosition("ne");
			chartModelCompTiempos.setMouseoverHighlight(false);
			chartModelCompTiempos.setShowDatatip(false);
			chartModelCompTiempos.setShowPointLabels(true);

			Axis xAxis = chartModelCompTiempos.getAxis(AxisType.X);
			xAxis.setLabel("Auditorías (código auditoria)");
			xAxis.setTickAngle(-90);

			Axis yAxis = chartModelCompTiempos.getAxis(AxisType.Y);
			yAxis.setMin(minAxis);
			yAxis.setMax(maxAxis + 30);
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME,
					"Problema al cargar datos de gráfico Tiempo Planificado vs Tiempo Ejecutado"));
		}

	}

	@SuppressWarnings("unchecked")
	public void obtenerTiemposPorFaseAuditorias() {
		setFormatter(new SimpleDateFormat("yyyy-MM-dd"));
		setStrFechaInicio(getFormatter().format(getFechaInicio()));
		setStrFechaFin(getFormatter().format(getFechaFin()));
		try {
			setTiempoPorFaseAuditoriasList((List<TiempoPorFaseAuditorias>) audigoesLocal.findByNamedQuery(
					TiempoPorFaseAuditorias.class, "consulta.tiempo.fases.auditoria",
					new Object[] { getObjAppsSession().getUsuario().getInstitucion().getInsId(), getStrFechaInicio(),
							getStrFechaFin() }));
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Problemas al obtener datos tiempo por auditoria"));
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

	public List<PlanAnual> getPlanList() {
		return planList;
	}

	public void setPlanList(List<PlanAnual> planList) {
		this.planList = planList;
	}

	public int getTotalAuditoriasPlanificadas() {
		return totalAuditoriasPlanificadas;
	}

	public int getTotalAuditoriasFinalizadas() {
		return totalAuditoriasFinalizadas;
	}

	public void setTotalAuditoriasPlanificadas(int totalAuditoriasPlanificadas) {
		this.totalAuditoriasPlanificadas = totalAuditoriasPlanificadas;
	}

	public void setTotalAuditoriasFinalizadas(int totalAuditoriasFinalizadas) {
		this.totalAuditoriasFinalizadas = totalAuditoriasFinalizadas;
	}

	public int getTotalHallazgos() {
		return totalHallazgos;
	}

	public void setTotalHallazgos(int totalHallazgos) {
		this.totalHallazgos = totalHallazgos;
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

	public CartesianChartModel getChartModelCompTiempos() {
		return chartModelCompTiempos;
	}

	public void setChartModelCompTiempos(CartesianChartModel chartModelCompTiempos) {
		this.chartModelCompTiempos = chartModelCompTiempos;
	}

	public HorizontalBarChartModel getBarChartTotalVsFinalizadas() {
		return barChartTotalVsFinalizadas;
	}

	public void setBarChartTotalVsFinalizadas(HorizontalBarChartModel barChartTotalVsFinalizadas) {
		this.barChartTotalVsFinalizadas = barChartTotalVsFinalizadas;
	}

	public HorizontalBarChartModel getBarChartTotalDuracionAuditorias() {
		return barChartTotalDuracionAuditorias;
	}

	public void setBarChartTotalDuracionAuditorias(HorizontalBarChartModel barChartTotalDuracionAuditorias) {
		this.barChartTotalDuracionAuditorias = barChartTotalDuracionAuditorias;
	}

	public List<DuracionAuditorias> getDuracionList() {
		return duracionList;
	}

	public void setDuracionList(List<DuracionAuditorias> duracionList) {
		this.duracionList = duracionList;
	}

	public boolean isRenderAuditoriasPorEstado() {
		return renderAuditoriasPorEstado;
	}

	public boolean isRenderHallazgosPorEstado() {
		return renderHallazgosPorEstado;
	}

	public boolean isRenderCompTiempos() {
		return renderCompTiempos;
	}

	public boolean isRenderTotalVsFinalizadas() {
		return renderTotalVsFinalizadas;
	}

	public boolean isRenderTotalDuracionAuditorias() {
		return renderTotalDuracionAuditorias;
	}

	public void setRenderAuditoriasPorEstado(boolean renderAuditoriasPorEstado) {
		this.renderAuditoriasPorEstado = renderAuditoriasPorEstado;
	}

	public void setRenderHallazgosPorEstado(boolean renderHallazgosPorEstado) {
		this.renderHallazgosPorEstado = renderHallazgosPorEstado;
	}

	public void setRenderCompTiempos(boolean renderCompTiempos) {
		this.renderCompTiempos = renderCompTiempos;
	}

	public void setRenderTotalVsFinalizadas(boolean renderTotalVsFinalizadas) {
		this.renderTotalVsFinalizadas = renderTotalVsFinalizadas;
	}

	public void setRenderTotalDuracionAuditorias(boolean renderTotalDuracionAuditorias) {
		this.renderTotalDuracionAuditorias = renderTotalDuracionAuditorias;
	}

}
