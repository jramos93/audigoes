package audigoes.ues.edu.sv.mbeans.consultas;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.administracion.Unidad;
import audigoes.ues.edu.sv.entities.informe.CedulaNota;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;
import audigoes.ues.edu.sv.entities.vistas.StatsAuditoriaEstado;

@ManagedBean(name = "consMB")
@ViewScoped
public class ConsultaMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Unidad> filteredUnidades;

	private List<Auditoria> auditoriasList;
	private List<Auditoria> filteredAuditorias;
	private Auditoria auditoria;

	private List<CedulaNota> cedulaNotasList;
	private List<CedulaNota> filteredCedulaNotas;
	private CedulaNota cedulaNota;
	
	private List<StatsAuditoriaEstado> statsList;

	private Date fechaInicio;
	private Date fechaFin;
	
	private String strFechaInicio;
	private String strFechaFin;

	private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	public ConsultaMB() {
		super(new Unidad());
	}

	@PostConstruct
	public void init() {
		try {
			Calendar calendar = Calendar.getInstance();
			Date fecha = new Date();
			calendar.set(GregorianCalendar.getInstance().get(Calendar.YEAR), Calendar.JANUARY, 1);
			setFechaInicio(calendar.getTime());
			calendar.set(GregorianCalendar.getInstance().get(Calendar.YEAR), Calendar.DECEMBER, 31);
			setFechaFin(calendar.getTime());
			
			fillAuditoriasList();
			//fillStatsAuditoriaEstado();
			super.init();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public void fillAuditoriasList() {
		try {
			//setStrFechaInicio(getFormatter().format(getFechaInicio()));
			//setStrFechaFin(getFormatter().format(getFechaFin()));
			setAuditoriasList(
					(List<Auditoria>) audigoesLocal.findByNamedQuery(Auditoria.class, "consulta.auditorias.institucion",
							new Object[] { getObjAppsSession().getUsuario().getInstitucion().getInsId(),
									getFechaInicio(), getFechaFin() }));
			System.out.println(getAuditoriasList().size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void fillStatsAuditoriaEstado() {
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@SuppressWarnings("unchecked")
	public void fillCedulaNotas() {
		try {
			//setStrFechaInicio(getFormatter().format(getFechaInicio()));
			//setStrFechaFin(getFormatter().format(getFechaFin()));
			setCedulaNotasList((List<CedulaNota>) audigoesLocal.findByNamedQuery(CedulaNota.class,
					"consulta.cedulanota.institucion",
					new Object[] { getObjAppsSession().getUsuario().getInstitucion().getInsId(),
							getFechaInicio(), getFechaFin() }));
		} catch (Exception e) {
			e.printStackTrace();
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

	private int getInteger(String string) {
		try {
			return Integer.valueOf(string);
		} catch (NumberFormatException ex) {
			return 0;
		}
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
}
