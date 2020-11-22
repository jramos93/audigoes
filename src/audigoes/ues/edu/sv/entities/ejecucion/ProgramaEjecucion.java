package audigoes.ues.edu.sv.entities.ejecucion;

import java.io.Serializable;
import javax.persistence.*;

import audigoes.ues.edu.sv.entities.SuperEntity;
import audigoes.ues.edu.sv.entities.administracion.Usuario;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;
import audigoes.ues.edu.sv.entities.planificacion.Actividad;

import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the programa_ejecucion database table.
 * 
 */
@Entity
@Table(name="programa_ejecucion")
@NamedQuery(name="ProgramaEjecucion.findAll", query="SELECT p FROM ProgramaEjecucion p")
public class ProgramaEjecucion extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "pre_id", schema = "audigoes", table = "contador", pkColumnName = "cnt_nombre", valueColumnName = "cnt_valor", pkColumnValue = "pre_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "pre_id")
	@Column(name="pre_id")
	private int preId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_crea")
	private Date fecCrea;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_modi")
	private Date fecModi;

	@Lob
	@Column(name="pre_narrativa")
	private String preNarrativa;

	@Temporal(TemporalType.DATE)
	@Column(name="pre_fecha_inicio")
	private Date preFechaInicio;
	
	@Temporal(TemporalType.DATE)
	@Column(name="pre_fecha_fin")
	private Date preFechaFin;

	@Temporal(TemporalType.DATE)
	@Column(name="pre_fecha_elaboro")
	private Date preFechaElaboro;

	@Temporal(TemporalType.DATE)
	@Column(name="pre_fecha_reviso")
	private Date preFechaReviso;

	@Lob
	@Column(name="pre_obj_e")
	private String preObjE;

	@Lob
	@Column(name="pre_obj_g")
	private String preObjG;

	@Column(name="reg_activo")
	private int regActivo;

	@Column(name="usu_crea")
	private String usuCrea;

	@Column(name="usu_modi")
	private String usuModi;
	
	@Column(name="pre_nombre")
	private String preNombre;
	
	@Column(name="pre_referencia")
	private String preReferencia;

	//bi-directional many-to-one association to ProcedimientoEjecucion
	@OneToMany(mappedBy="programaEjecucion", fetch=FetchType.EAGER)
	private Set<ProcedimientoEjecucion> procedimientoEjecucion;

	//bi-directional many-to-one association to Actividad
	@ManyToOne
	@JoinColumn(name="pre_act_id")
	private Actividad actividad;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="pre_usu_id")
	private Usuario usuario1;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="pre_usu_usu_id")
	private Usuario usuario2;
	
	// bi-directional many-to-one association to Auditoria
	@ManyToOne
	@JoinColumn(name = "pre_aud_id")
	private Auditoria auditoria;

	public ProgramaEjecucion() {
	}

	public int getPreId() {
		return this.preId;
	}

	public void setPreId(int preId) {
		this.preId = preId;
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

	public String getPreNarrativa() {
		return preNarrativa;
	}

	public void setPreNarrativa(String preNarrativa) {
		this.preNarrativa = preNarrativa;
	}

	public Date getPreFechaInicio() {
		return preFechaInicio;
	}

	public void setPreFechaInicio(Date preFechaInicio) {
		this.preFechaInicio = preFechaInicio;
	}

	public Date getPreFechaFin() {
		return preFechaFin;
	}

	public void setPreFechaFin(Date preFechaFin) {
		this.preFechaFin = preFechaFin;
	}

	public String getPreNombre() {
		return preNombre;
	}

	public void setPreNombre(String preNombre) {
		this.preNombre = preNombre;
	}

	public String getPreReferencia() {
		return preReferencia;
	}

	public void setPreReferencia(String preReferencia) {
		this.preReferencia = preReferencia;
	}

	public Date getPreFechaElaboro() {
		return this.preFechaElaboro;
	}

	public void setPreFechaElaboro(Date preFechaElaboro) {
		this.preFechaElaboro = preFechaElaboro;
	}

	public Date getPreFechaReviso() {
		return this.preFechaReviso;
	}

	public void setPreFechaReviso(Date preFechaReviso) {
		this.preFechaReviso = preFechaReviso;
	}

	public String getPreObjE() {
		return this.preObjE;
	}

	public void setPreObjE(String preObjE) {
		this.preObjE = preObjE;
	}

	public String getPreObjG() {
		return this.preObjG;
	}

	public void setPreObjG(String preObjG) {
		this.preObjG = preObjG;
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

	public Set<ProcedimientoEjecucion> getProcedimientoEjecucion() {
		return this.procedimientoEjecucion;
	}

	public void setProcedimientoEjecucion(Set<ProcedimientoEjecucion> procedimientoEjecucion) {
		this.procedimientoEjecucion = procedimientoEjecucion;
	}

	public ProcedimientoEjecucion addProcedimientoEjecucion(ProcedimientoEjecucion procedimientoEjecucion) {
		getProcedimientoEjecucion().add(procedimientoEjecucion);
		procedimientoEjecucion.setProgramaEjecucion(this);

		return procedimientoEjecucion;
	}

	public ProcedimientoEjecucion removeProcedimientoEjecucion(ProcedimientoEjecucion procedimientoEjecucion) {
		getProcedimientoEjecucion().remove(procedimientoEjecucion);
		procedimientoEjecucion.setProgramaEjecucion(null);

		return procedimientoEjecucion;
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

	public Auditoria getAuditoria() {
		return auditoria;
	}

	public void setAuditoria(Auditoria auditoria) {
		this.auditoria = auditoria;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + preId;
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
		ProgramaEjecucion other = (ProgramaEjecucion) obj;
		if (preId != other.preId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProgramaEjecucion [preId=" + preId + ", fecCrea=" + fecCrea + ", fecModi=" + fecModi
				+ ", preEncabezado=" + preNarrativa + ", preFecha=" + preFechaInicio + ", preFechaElaboro=" + preFechaElaboro
				+ ", preFechaReviso=" + preFechaReviso + ", preObjE=" + preObjE + ", preObjG=" + preObjG
				+ ", regActivo=" + regActivo + ", usuCrea=" + usuCrea + ", usuModi=" + usuModi
				+ ", procedimientoEjecucion=" + procedimientoEjecucion + ", actividad=" + actividad + ", usuario1="
				+ usuario1 + ", usuario2=" + usuario2 + "]";
	}

}