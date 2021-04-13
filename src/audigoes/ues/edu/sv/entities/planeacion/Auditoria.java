package audigoes.ues.edu.sv.entities.planeacion;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import audigoes.ues.edu.sv.entities.SuperEntity;
import audigoes.ues.edu.sv.entities.ejecucion.ComentarioHallazgo;
import audigoes.ues.edu.sv.entities.informe.Informe;
import audigoes.ues.edu.sv.entities.planificacion.Actividad;
import audigoes.ues.edu.sv.entities.planificacion.Memorando;
import audigoes.ues.edu.sv.entities.seguimiento.Recomendacion;
import audigoes.ues.edu.sv.entities.seguimiento.Seguimiento;

/**
 * The persistent class for the auditoria database table.
 * 
 */
@Entity
@Table(name="auditoria")
@NamedQuery(name = "Auditoria.findAll", query = "SELECT a FROM Auditoria a")
public class Auditoria extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "aud_id", schema = "audigoes", table = "contador", pkColumnName = "cnt_nombre", valueColumnName = "cnt_valor", pkColumnValue = "aud_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "aud_id")
	@Column(name = "aud_id")
	private int audId;

	@Column(name = "aud_anio")
	private int audAnio;

	@Column(name = "aud_codigo")
	private String audCodigo;

	@Column(name = "aud_correlativo")
	private int audCorrelativo;

	@Lob
	@Column(name = "aud_descripcion")
	private String audDescripcion;

	@Lob
	@Column(name = "aud_objetivos")
	private String audObjetivos;

	@Lob
	@Column(name = "aud_alcances")
	private String audAlcances;

	@Column(name = "aud_fase")
	private int audFase;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "aud_fecha_fin_programado")
	private Date audFechaFinProgramado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "aud_fecha_fin_real")
	private Date audFechaFinReal;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "aud_fecha_inicio_programado")
	private Date audFechaInicioProgramado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "aud_fecha_inicio_real")
	private Date audFechaInicioReal;

	@Column(name = "aud_nombre")
	private String audNombre;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fec_crea")
	private Date fecCrea;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fec_modi")
	private Date fecModi;

	@Column(name = "reg_activo")
	private int regActivo;

	@Column(name = "usu_crea")
	private String usuCrea;

	@Column(name = "usu_modi")
	private String usuModi;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "aud_fecha_programado")
	private Date audFechaProgramado;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "aud_fecha_asignada")
	private Date audFechaAsignada;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "aud_fecha_planificacion")
	private Date audFechaPlanificacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "aud_fecha_ejecucion")
	private Date audFechaEjecucion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "aud_fecha_informe")
	private Date audFechaInforme;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "aud_fecha_seguimiento")
	private Date audFechaSeguimiento;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "aud_fecha_finalizada")
	private Date audFechaFinalizada;

	// bi-directional many-to-one association to Actividad
	@OneToMany(mappedBy = "auditoria")
	private List<Actividad> actividad;

	// bi-directional many-to-one association to OrigenAuditoria
	@ManyToOne
	@JoinColumn(name = "aud_ori_id")
	private OrigenAuditoria origenAuditoria;

	// bi-directional many-to-one association to PlanAnual
	@ManyToOne
	@JoinColumn(name = "aud_pla_id")
	private PlanAnual planAnual;

	// bi-directional many-to-one association to TipoAuditoria
	@ManyToOne
	@JoinColumn(name = "aud_tpa_id")
	private TipoAuditoria tipoAuditoria;

	// bi-directional many-to-one association to AuditoriaResponsable
	@OneToMany(mappedBy = "auditoria")
	private List<AuditoriaResponsable> auditoriaResponsable;

	// bi-directional many-to-one association to AuditoriaUnidad
	@OneToMany(mappedBy = "auditoria")
	private List<AuditoriaUnidad> auditoriaUnidad;

	// bi-directional many-to-one association to Memorando
	@OneToMany(mappedBy = "auditoria")
	private List<Memorando> memorando;

	// bi-directional many-to-one association to Seguimiento
	@OneToMany(mappedBy = "auditoria")
	private List<Seguimiento> seguimiento;

	// bi-directional many-to-one association to Informe
	@OneToMany(mappedBy = "auditoria")
	private List<Informe> informe;

	// bi-directional many-to-one association to Recomendacion
	@OneToMany(mappedBy = "auditoria")
	private List<Recomendacion> recomendacion;
	
	//bi-directional many-to-one association to ComentarioHallazgo
	@OneToMany(mappedBy="auditoria")
	private List<ComentarioHallazgo> comentarioHallazgos;

	public Auditoria() {
	}

	public int getAudId() {
		return this.audId;
	}

	public void setAudId(int audId) {
		this.audId = audId;
	}

	public int getAudAnio() {
		return audAnio;
	}

	public void setAudAnio(int audAnio) {
		this.audAnio = audAnio;
	}

	public String getAudCodigo() {
		return this.audCodigo;
	}

	public void setAudCodigo(String audCodigo) {
		this.audCodigo = audCodigo;
	}

	public int getAudCorrelativo() {
		return audCorrelativo;
	}

	public void setAudCorrelativo(int audCorrelativo) {
		this.audCorrelativo = audCorrelativo;
	}

	public String getAudDescripcion() {
		return this.audDescripcion;
	}

	public void setAudDescripcion(String audDescripcion) {
		this.audDescripcion = audDescripcion;
	}

	public String getAudObjetivos() {
		return audObjetivos;
	}

	public void setAudObjetivos(String audObjetivos) {
		this.audObjetivos = audObjetivos;
	}

	public String getAudAlcances() {
		return audAlcances;
	}

	public void setAudAlcances(String audAlcances) {
		this.audAlcances = audAlcances;
	}

	public int getAudFase() {
		return this.audFase;
	}

	public void setAudFase(int audFase) {
		this.audFase = audFase;
	}

	public Date getAudFechaFinProgramado() {
		return this.audFechaFinProgramado;
	}

	public void setAudFechaFinProgramado(Date audFechaFinProgramado) {
		this.audFechaFinProgramado = audFechaFinProgramado;
	}

	public Date getAudFechaFinReal() {
		return this.audFechaFinReal;
	}

	public void setAudFechaFinReal(Date audFechaFinReal) {
		this.audFechaFinReal = audFechaFinReal;
	}

	public Date getAudFechaInicioProgramado() {
		return this.audFechaInicioProgramado;
	}

	public void setAudFechaInicioProgramado(Date audFechaInicioProgramado) {
		this.audFechaInicioProgramado = audFechaInicioProgramado;
	}

	public Date getAudFechaInicioReal() {
		return this.audFechaInicioReal;
	}

	public void setAudFechaInicioReal(Date audFechaInicioReal) {
		this.audFechaInicioReal = audFechaInicioReal;
	}

	public String getAudNombre() {
		return this.audNombre;
	}

	public void setAudNombre(String audNombre) {
		this.audNombre = audNombre;
	}

	public Date getFecCrea() {
		return this.fecCrea;
	}

	public void setFecCrea(Date fecCrea) {
		this.fecCrea = fecCrea;
	}

	public Date getFecModi() {
		return this.fecModi;
	}

	public void setFecModi(Date fecModi) {
		this.fecModi = fecModi;
	}

	public int getRegActivo() {
		return this.regActivo;
	}

	public void setRegActivo(int regActivo) {
		this.regActivo = regActivo;
	}

	public String getUsuCrea() {
		return this.usuCrea;
	}

	public void setUsuCrea(String usuCrea) {
		this.usuCrea = usuCrea;
	}

	public String getUsuModi() {
		return this.usuModi;
	}

	public void setUsuModi(String usuModi) {
		this.usuModi = usuModi;
	}

	public Date getAudFechaProgramado() {
		return audFechaProgramado;
	}

	public Date getAudFechaPlanificacion() {
		return audFechaPlanificacion;
	}

	public Date getAudFechaEjecucion() {
		return audFechaEjecucion;
	}

	public Date getAudFechaInforme() {
		return audFechaInforme;
	}

	public Date getAudFechaSeguimiento() {
		return audFechaSeguimiento;
	}

	public Date getAudFechaFinalizada() {
		return audFechaFinalizada;
	}

	public void setAudFechaProgramado(Date audFechaProgramado) {
		this.audFechaProgramado = audFechaProgramado;
	}

	public void setAudFechaPlanificacion(Date audFechaPlanificacion) {
		this.audFechaPlanificacion = audFechaPlanificacion;
	}

	public void setAudFechaEjecucion(Date audFechaEjecucion) {
		this.audFechaEjecucion = audFechaEjecucion;
	}

	public void setAudFechaInforme(Date audFechaInforme) {
		this.audFechaInforme = audFechaInforme;
	}

	public void setAudFechaSeguimiento(Date audFechaSeguimiento) {
		this.audFechaSeguimiento = audFechaSeguimiento;
	}

	public void setAudFechaFinalizada(Date audFechaFinalizada) {
		this.audFechaFinalizada = audFechaFinalizada;
	}

	public List<Actividad> getActividad() {
		return this.actividad;
	}

	public void setActividad(List<Actividad> actividad) {
		this.actividad = actividad;
	}

	public Actividad addActividad(Actividad actividad) {
		getActividad().add(actividad);
		actividad.setAuditoria(this);

		return actividad;
	}

	public Actividad removeActividad(Actividad actividad) {
		getActividad().remove(actividad);
		actividad.setAuditoria(null);

		return actividad;
	}

	public OrigenAuditoria getOrigenAuditoria() {
		return this.origenAuditoria;
	}

	public void setOrigenAuditoria(OrigenAuditoria origenAuditoria) {
		this.origenAuditoria = origenAuditoria;
	}

	public PlanAnual getPlanAnual() {
		return this.planAnual;
	}

	public void setPlanAnual(PlanAnual planAnual) {
		this.planAnual = planAnual;
	}

	public TipoAuditoria getTipoAuditoria() {
		return this.tipoAuditoria;
	}

	public void setTipoAuditoria(TipoAuditoria tipoAuditoria) {
		this.tipoAuditoria = tipoAuditoria;
	}

	public List<AuditoriaResponsable> getAuditoriaResponsable() {
		return this.auditoriaResponsable;
	}

	public void setAuditoriaResponsable(List<AuditoriaResponsable> auditoriaResponsable) {
		this.auditoriaResponsable = auditoriaResponsable;
	}

	public AuditoriaResponsable addAuditoriaResponsable(AuditoriaResponsable auditoriaResponsable) {
		getAuditoriaResponsable().add(auditoriaResponsable);
		auditoriaResponsable.setAuditoria(this);

		return auditoriaResponsable;
	}

	public AuditoriaResponsable removeAuditoriaResponsable(AuditoriaResponsable auditoriaResponsable) {
		getAuditoriaResponsable().remove(auditoriaResponsable);
		auditoriaResponsable.setAuditoria(null);

		return auditoriaResponsable;
	}

	public List<AuditoriaUnidad> getAuditoriaUnidad() {
		return this.auditoriaUnidad;
	}

	public void setAuditoriaUnidad(List<AuditoriaUnidad> auditoriaUnidad) {
		this.auditoriaUnidad = auditoriaUnidad;
	}

	public AuditoriaUnidad addAuditoriaUnidad(AuditoriaUnidad auditoriaUnidad) {
		getAuditoriaUnidad().add(auditoriaUnidad);
		auditoriaUnidad.setAuditoria(this);

		return auditoriaUnidad;
	}

	public AuditoriaUnidad removeAuditoriaUnidad(AuditoriaUnidad auditoriaUnidad) {
		getAuditoriaUnidad().remove(auditoriaUnidad);
		auditoriaUnidad.setAuditoria(null);

		return auditoriaUnidad;
	}

	public List<Memorando> getMemorando() {
		return this.memorando;
	}

	public void setMemorando(List<Memorando> memorando) {
		this.memorando = memorando;
	}

	public Memorando addMemorando(Memorando memorando) {
		getMemorando().add(memorando);
		memorando.setAuditoria(this);

		return memorando;
	}

	public Memorando removeMemorando(Memorando memorando) {
		getMemorando().remove(memorando);
		memorando.setAuditoria(null);

		return memorando;
	}

	public List<Seguimiento> getSeguimiento() {
		return this.seguimiento;
	}

	public void setSeguimiento(List<Seguimiento> seguimiento) {
		this.seguimiento = seguimiento;
	}

	public Seguimiento addSeguimiento(Seguimiento seguimiento) {
		getSeguimiento().add(seguimiento);
		seguimiento.setAuditoria(this);

		return seguimiento;
	}

	public Seguimiento removeSeguimiento(Seguimiento seguimiento) {
		getSeguimiento().remove(seguimiento);
		seguimiento.setAuditoria(null);

		return seguimiento;
	}

	public List<Informe> getInforme() {
		return this.informe;
	}

	public void setInforme(List<Informe> informe) {
		this.informe = informe;
	}

	public Informe addInforme(Informe informe) {
		getInforme().add(informe);
		informe.setAuditoria(this);

		return informe;
	}

	public Informe removeInforme(Informe informe) {
		getInforme().remove(informe);
		informe.setAuditoria(null);

		return informe;
	}

	public List<Recomendacion> getRecomendacion() {
		return this.recomendacion;
	}

	public void setRecomendacion(List<Recomendacion> recomendacion) {
		this.recomendacion = recomendacion;
	}

	public Recomendacion addRecomendacion(Recomendacion recomendacion) {
		getRecomendacion().add(recomendacion);
		recomendacion.setAuditoria(this);

		return recomendacion;
	}

	public Recomendacion removeRecomendacion(Recomendacion recomendacion) {
		getRecomendacion().remove(recomendacion);
		recomendacion.setAuditoria(null);

		return recomendacion;
	}

	public Date getAudFechaAsignada() {
		return audFechaAsignada;
	}

	public void setAudFechaAsignada(Date audFechaAsignada) {
		this.audFechaAsignada = audFechaAsignada;
	}

	public List<ComentarioHallazgo> getComentarioHallazgos() {
		return comentarioHallazgos;
	}

	public void setComentarioHallazgos(List<ComentarioHallazgo> comentarioHallazgos) {
		this.comentarioHallazgos = comentarioHallazgos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + audId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Auditoria other = (Auditoria) obj;
		if (audId != other.audId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Auditoria [audId=" + audId + ", audAnio=" + audAnio + ", audCodigo=" + audCodigo + ", audCorrelativo="
				+ audCorrelativo + ", audDescripcion=" + audDescripcion + ", audObjetivos=" + audObjetivos
				+ ", audAlcances=" + audAlcances + ", audFase=" + audFase + ", audFechaFinProgramado="
				+ audFechaFinProgramado + ", audFechaFinReal=" + audFechaFinReal + ", audFechaInicioProgramado="
				+ audFechaInicioProgramado + ", audFechaInicioReal=" + audFechaInicioReal + ", audNombre=" + audNombre
				+ ", fecCrea=" + fecCrea + ", fecModi=" + fecModi + ", regActivo=" + regActivo + ", usuCrea=" + usuCrea
				+ ", usuModi=" + usuModi + ", audFechaProgramado=" + audFechaProgramado + ", audFechaPlanificacion="
				+ audFechaPlanificacion + ", audFechaEjecucion=" + audFechaEjecucion + ", audFechaInforme="
				+ audFechaInforme + ", audFechaSeguimiento=" + audFechaSeguimiento + ", audFechaFinalizada="
				+ audFechaFinalizada + ", actividad=" + actividad + ", origenAuditoria=" + origenAuditoria
				+ ", planAnual=" + planAnual + ", tipoAuditoria=" + tipoAuditoria + ", auditoriaResponsable="
				+ auditoriaResponsable + ", auditoriaUnidad=" + auditoriaUnidad + ", memorando=" + memorando
				+ ", seguimiento=" + seguimiento + ", informe=" + informe + ", recomendacion=" + recomendacion + "]";
	}

}