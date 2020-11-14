package audigoes.ues.edu.sv.entities.planificacion;

import java.io.Serializable;
import javax.persistence.*;

import audigoes.ues.edu.sv.entities.SuperEntity;
import audigoes.ues.edu.sv.entities.administracion.Usuario;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;

import java.util.Date;
import java.util.Set;

/**
 * The persistent class for the programa_planificacion database table.
 * 
 */
@Entity
@Table(name = "programa_planificacion")
@NamedQuery(name = "ProgramaPlanificacion.findAll", query = "SELECT p FROM ProgramaPlanificacion p")
public class ProgramaPlanificacion extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "prp_id", schema = "audigoes", table = "contador", pkColumnName = "cnt_nombre", valueColumnName = "cnt_valor", pkColumnValue = "prp_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "prp_id")
	@Column(name = "prp_id")
	private int prpId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fec_crea")
	private Date fecCrea;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fec_modi")
	private Date fecModi;

	@Column(name = "prp_alcance")
	private String prpAlcance;

	@Temporal(TemporalType.DATE)
	@Column(name = "prp_fecha_inicio")
	private Date prpFechaInicio;

	@Temporal(TemporalType.DATE)
	@Column(name = "prp_fecha_fin")
	private Date prpFechaFin;

	@Temporal(TemporalType.DATE)
	@Column(name = "prp_fecha_elaboro")
	private Date prpFechaElaboro;

	@Temporal(TemporalType.DATE)
	@Column(name = "prp_fecha_reviso")
	private Date prpFechaReviso;

	@Lob
	@Column(name = "prp_obj_e")
	private String prpObjE;

	@Lob
	@Column(name = "prp_obj_g")
	private String prpObjG;

	@Column(name = "reg_activo")
	private int regActivo;

	@Column(name = "usu_crea")
	private String usuCrea;

	@Column(name = "usu_modi")
	private String usuModi;

	// bi-directional many-to-one association to ProcedimientoPlanificacion
	@OneToMany(mappedBy = "programaPlanificacion", fetch = FetchType.EAGER)
	private Set<ProcedimientoPlanificacion> procedimientoPlanificacion;

	// bi-directional many-to-one association to Actividad
	@ManyToOne
	@JoinColumn(name = "prp_act_id")
	private Actividad actividad;

	// bi-directional many-to-one association to Auditoria
	@ManyToOne
	@JoinColumn(name = "prp_aud_id")
	private Auditoria auditoria;

	// bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name = "prp_usu_id")
	private Usuario usuario1;

	// bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name = "prp_usu_usu_id")
	private Usuario usuario2;

	public ProgramaPlanificacion() {
	}

	public int getPrpId() {
		return this.prpId;
	}

	public void setPrpId(int prpId) {
		this.prpId = prpId;
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

	public String getPrpAlcance() {
		return this.prpAlcance;
	}

	public void setPrpAlcance(String prpAlcance) {
		this.prpAlcance = prpAlcance;
	}
	

	public Date getPrpFechaInicio() {
		return prpFechaInicio;
	}

	public void setPrpFechaInicio(Date prpFechaInicio) {
		this.prpFechaInicio = prpFechaInicio;
	}

	public Date getPrpFechaFin() {
		return prpFechaFin;
	}

	public void setPrpFechaFin(Date prpFechaFin) {
		this.prpFechaFin = prpFechaFin;
	}

	public Auditoria getAuditoria() {
		return auditoria;
	}

	public void setAuditoria(Auditoria auditoria) {
		this.auditoria = auditoria;
	}

	public Date getPrpFechaElaboro() {
		return this.prpFechaElaboro;
	}

	public void setPrpFechaElaboro(Date prpFechaElaboro) {
		this.prpFechaElaboro = prpFechaElaboro;
	}

	public Date getPrpFechaReviso() {
		return this.prpFechaReviso;
	}

	public void setPrpFechaReviso(Date prpFechaReviso) {
		this.prpFechaReviso = prpFechaReviso;
	}

	public String getPrpObjE() {
		return this.prpObjE;
	}

	public void setPrpObjE(String prpObjE) {
		this.prpObjE = prpObjE;
	}

	public String getPrpObjG() {
		return this.prpObjG;
	}

	public void setPrpObjG(String prpObjG) {
		this.prpObjG = prpObjG;
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

	public Set<ProcedimientoPlanificacion> getProcedimientoPlanificacion() {
		return this.procedimientoPlanificacion;
	}

	public void setProcedimientoPlanificacion(Set<ProcedimientoPlanificacion> procedimientoPlanificacion) {
		this.procedimientoPlanificacion = procedimientoPlanificacion;
	}

	public ProcedimientoPlanificacion addProcedimientoPlanificacion(
			ProcedimientoPlanificacion procedimientoPlanificacion) {
		getProcedimientoPlanificacion().add(procedimientoPlanificacion);
		procedimientoPlanificacion.setProgramaPlanificacion(this);

		return procedimientoPlanificacion;
	}

	public ProcedimientoPlanificacion removeProcedimientoPlanificacion(
			ProcedimientoPlanificacion procedimientoPlanificacion) {
		getProcedimientoPlanificacion().remove(procedimientoPlanificacion);
		procedimientoPlanificacion.setProgramaPlanificacion(null);

		return procedimientoPlanificacion;
	}

	public Actividad getActividad() {
		return this.actividad;
	}

	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}

	public Usuario getUsuario1() {
		return this.usuario1;
	}

	public void setUsuario1(Usuario usuario1) {
		this.usuario1 = usuario1;
	}

	public Usuario getUsuario2() {
		return this.usuario2;
	}

	public void setUsuario2(Usuario usuario2) {
		this.usuario2 = usuario2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + prpId;
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
		ProgramaPlanificacion other = (ProgramaPlanificacion) obj;
		if (prpId != other.prpId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProgramaPlanificacion [prpId=" + prpId + ", fecCrea=" + fecCrea + ", fecModi=" + fecModi
				+ ", prpAlcance=" + prpAlcance + ", prpFecha=" + prpFechaInicio + ", prpFechaElaboro=" + prpFechaElaboro
				+ ", prpFechaReviso=" + prpFechaReviso + ", prpObjE=" + prpObjE + ", prpObjG=" + prpObjG
				+ ", regActivo=" + regActivo + ", usuCrea=" + usuCrea + ", usuModi=" + usuModi
				+ ", procedimientoPlanificacion=" + procedimientoPlanificacion + ", actividad=" + actividad
				+ ", usuario1=" + usuario1 + ", usuario2=" + usuario2 + "]";
	}

}