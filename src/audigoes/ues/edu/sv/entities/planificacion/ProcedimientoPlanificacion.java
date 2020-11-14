package audigoes.ues.edu.sv.entities.planificacion;

import java.io.Serializable;
import javax.persistence.*;

import audigoes.ues.edu.sv.entities.SuperEntity;
import audigoes.ues.edu.sv.entities.administracion.Usuario;
import audigoes.ues.edu.sv.entities.informe.CedulaNota;

import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the procedimiento_planificacion database table.
 * 
 */
@Entity
@Table(name="procedimiento_planificacion")
@NamedQuery(name="ProcedimientoPlanificacion.findAll", query="SELECT p FROM ProcedimientoPlanificacion p")
public class ProcedimientoPlanificacion extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "pro_id", schema = "audigoes", table = "contador", pkColumnName = "cnt_nombre", valueColumnName = "cnt_valor", pkColumnValue = "pro_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "pro_id")
	@Column(name="pro_id")
	private int proId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_crea")
	private Date fecCrea;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_modi")
	private Date fecModi;

	@Temporal(TemporalType.DATE)
	@Column(name="pro_fecha_inicio")
	private Date proFechaInicio;
	
	@Temporal(TemporalType.DATE)
	@Column(name="pro_fecha_fin")
	private Date proFechaFin;

	@Temporal(TemporalType.DATE)
	@Column(name="pro_fecha_elaboro")
	private Date proFechaElaboro;

	@Temporal(TemporalType.DATE)
	@Column(name="pro_fecha_reviso")
	private Date proFechaReviso;

	@Lob
	@Column(name="pro_narrativa")
	private String proNarrativa;

	@Column(name="pro_nombre")
	private String proNombre;

	@Column(name="pro_referencia")
	private String proReferencia;

	@Column(name="reg_activo")
	private int regActivo;

	@Column(name="usu_crea")
	private String usuCrea;

	@Column(name="usu_modi")
	private String usuModi;

	//bi-directional many-to-one association to CedulaNota
	@OneToMany(mappedBy="procedimientoPlanificacion", fetch=FetchType.EAGER)
	private Set<CedulaNota> cedulaNotas;

	//bi-directional many-to-one association to DocumentosPlanificacion
	@OneToMany(mappedBy="procedimientoPlanificacion", fetch=FetchType.EAGER)
	private Set<DocumentosPlanificacion> documentosPlanificacion;

	//bi-directional many-to-one association to ProgramaPlanificacion
	@ManyToOne
	@JoinColumn(name="pro_prp_id")
	private ProgramaPlanificacion programaPlanificacion;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="pro_usu_id")
	private Usuario usuario1;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="pro_usu_usu_id")
	private Usuario usuario2;

	public ProcedimientoPlanificacion() {
	}

	public int getProId() {
		return this.proId;
	}

	public void setProId(int proId) {
		this.proId = proId;
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

	public Date getProFechaElaboro() {
		return this.proFechaElaboro;
	}

	public void setProFechaElaboro(Date proFechaElaboro) {
		this.proFechaElaboro = proFechaElaboro;
	}

	public Date getProFechaReviso() {
		return this.proFechaReviso;
	}

	public void setProFechaReviso(Date proFechaReviso) {
		this.proFechaReviso = proFechaReviso;
	}

	public String getProNarrativa() {
		return this.proNarrativa;
	}

	public void setProNarrativa(String proNarrativa) {
		this.proNarrativa = proNarrativa;
	}

	public String getProNombre() {
		return this.proNombre;
	}

	public void setProNombre(String proNombre) {
		this.proNombre = proNombre;
	}

	public String getProReferencia() {
		return this.proReferencia;
	}

	public void setProReferencia(String proReferencia) {
		this.proReferencia = proReferencia;
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

	public Set<CedulaNota> getCedulaNotas() {
		return this.cedulaNotas;
	}

	public void setCedulaNotas(Set<CedulaNota> cedulaNotas) {
		this.cedulaNotas = cedulaNotas;
	}

	public CedulaNota addCedulaNota(CedulaNota cedulaNota) {
		getCedulaNotas().add(cedulaNota);
		cedulaNota.setProcedimientoPlanificacion(this);

		return cedulaNota;
	}

	public CedulaNota removeCedulaNota(CedulaNota cedulaNota) {
		getCedulaNotas().remove(cedulaNota);
		cedulaNota.setProcedimientoPlanificacion(null);

		return cedulaNota;
	}

	public Set<DocumentosPlanificacion> getDocumentosPlanificacion() {
		return this.documentosPlanificacion;
	}

	public void setDocumentosPlanificacion(Set<DocumentosPlanificacion> documentosPlanificacion) {
		this.documentosPlanificacion = documentosPlanificacion;
	}

	public DocumentosPlanificacion addDocumentosPlanificacion(DocumentosPlanificacion documentosPlanificacion) {
		getDocumentosPlanificacion().add(documentosPlanificacion);
		documentosPlanificacion.setProcedimientoPlanificacion(this);

		return documentosPlanificacion;
	}

	public DocumentosPlanificacion removeDocumentosPlanificacion(DocumentosPlanificacion documentosPlanificacion) {
		getDocumentosPlanificacion().remove(documentosPlanificacion);
		documentosPlanificacion.setProcedimientoPlanificacion(null);

		return documentosPlanificacion;
	}

	public ProgramaPlanificacion getProgramaPlanificacion() {
		return this.programaPlanificacion;
	}

	public void setProgramaPlanificacion(ProgramaPlanificacion programaPlanificacion) {
		this.programaPlanificacion = programaPlanificacion;
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
	

	public Date getProFechaInicio() {
		return proFechaInicio;
	}

	public void setProFechaInicio(Date proFechaInicio) {
		this.proFechaInicio = proFechaInicio;
	}

	public Date getProFechaFin() {
		return proFechaFin;
	}

	public void setProFechaFin(Date proFechaFin) {
		this.proFechaFin = proFechaFin;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + proId;
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
		ProcedimientoPlanificacion other = (ProcedimientoPlanificacion) obj;
		if (proId != other.proId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProcedimientoPlanificacion [proId=" + proId + ", fecCrea=" + fecCrea + ", fecModi=" + fecModi
				+ ", proFecha=" + proFechaInicio + ", proFechaElaboro=" + proFechaElaboro + ", proFechaReviso="
				+ proFechaReviso + ", proNarrativa=" + proNarrativa + ", proNombre=" + proNombre + ", proReferencia="
				+ proReferencia + ", regActivo=" + regActivo + ", usuCrea=" + usuCrea + ", usuModi=" + usuModi
				+ ", cedulaNotas=" + cedulaNotas + ", documentosPlanificacion=" + documentosPlanificacion
				+ ", programaPlanificacion=" + programaPlanificacion + ", usuario1=" + usuario1 + ", usuario2="
				+ usuario2 + "]";
	}

}