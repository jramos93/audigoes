package audigoes.ues.edu.sv.entities.informe;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import audigoes.ues.edu.sv.entities.administracion.Usuario;
import audigoes.ues.edu.sv.entities.ejecucion.ProcedimientoEjecucion;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;
import audigoes.ues.edu.sv.entities.planificacion.ProcedimientoPlanificacion;
import audigoes.ues.edu.sv.entities.seguimiento.Recomendacion;


/**
 * The persistent class for the cedula_notas database table.
 * 
 */
@Entity
@Table(name="cedula_notas")
@NamedQuery(name="CedulaNota.findAll", query="SELECT c FROM CedulaNota c")
public class CedulaNota extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "ced_id", schema = "audigoes", table = "contador", pkColumnName = "cnt_nombre", valueColumnName = "cnt_valor", pkColumnValue = "ced_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ced_id")
	@Column(name="ced_id")
	private int cedId;

	@Lob
	@Column(name="ced_causa")
	private String cedCausa;

	@Lob
	@Column(name="ced_condicion")
	private String cedCondicion;

	@Lob
	@Column(name="ced_criterio")
	private String cedCriterio;

	@Lob
	@Column(name="ced_efecto")
	private String cedEfecto;

	@Column(name="ced_estado")
	private int cedEstado;

	@Temporal(TemporalType.DATE)
	@Column(name="ced_fecha_elaboro")
	private Date cedFechaElaboro;

	@Temporal(TemporalType.DATE)
	@Column(name="ced_fecha_reviso")
	private Date cedFechaReviso;

	@Column(name="ced_observacion")
	private String cedObservacion;

	@Column(name="ced_referencia")
	private String cedReferencia;

	@Column(name="ced_titulo")
	private String cedTitulo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_crea")
	private Date fecCrea;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_modi")
	private Date fecModi;

	@Column(name="reg_activo")
	private int regActivo;

	@Column(name="usu_crea")
	private String usuCrea;

	@Column(name="usu_modi")
	private String usuModi;
	
	@ManyToOne
	@JoinColumn(name="ced_aud_id")
	private Auditoria auditoria;

	//bi-directional many-to-one association to Informe
	@ManyToOne
	@JoinColumn(name="ced_inf_id")
	private Informe informe;

	//bi-directional many-to-one association to ProcedimientoEjecucion
	@ManyToOne
	@JoinColumn(name="ced_pej_id")
	private ProcedimientoEjecucion procedimientoEjecucion;

	//bi-directional many-to-one association to ProcedimientoPlanificacion
	@ManyToOne
	@JoinColumn(name="ced_pro_id")
	private ProcedimientoPlanificacion procedimientoPlanificacion;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="ced_usu_id")
	private Usuario usuario1;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="ced_usu_usu_id")
	private Usuario usuario2;

	//bi-directional many-to-one association to Recomendacion
	@OneToMany(mappedBy="cedulaNota", fetch=FetchType.EAGER)
	private Set<Recomendacion> recomendacion;

	public CedulaNota() {
	}

	public int getCedId() {
		return this.cedId;
	}

	public void setCedId(int cedId) {
		this.cedId = cedId;
	}

	public String getCedCausa() {
		return this.cedCausa;
	}

	public void setCedCausa(String cedCausa) {
		this.cedCausa = cedCausa;
	}

	public String getCedCondicion() {
		return this.cedCondicion;
	}

	public void setCedCondicion(String cedCondicion) {
		this.cedCondicion = cedCondicion;
	}

	public String getCedCriterio() {
		return this.cedCriterio;
	}

	public void setCedCriterio(String cedCriterio) {
		this.cedCriterio = cedCriterio;
	}

	public String getCedEfecto() {
		return this.cedEfecto;
	}

	public void setCedEfecto(String cedEfecto) {
		this.cedEfecto = cedEfecto;
	}

	public int getCedEstado() {
		return this.cedEstado;
	}

	public void setCedEstado(int cedEstado) {
		this.cedEstado = cedEstado;
	}

	public Date getCedFechaElaboro() {
		return this.cedFechaElaboro;
	}

	public void setCedFechaElaboro(Date cedFechaElaboro) {
		this.cedFechaElaboro = cedFechaElaboro;
	}

	public Date getCedFechaReviso() {
		return this.cedFechaReviso;
	}

	public void setCedFechaReviso(Date cedFechaReviso) {
		this.cedFechaReviso = cedFechaReviso;
	}

	public String getCedObservacion() {
		return this.cedObservacion;
	}

	public void setCedObservacion(String cedObservacion) {
		this.cedObservacion = cedObservacion;
	}

	public String getCedReferencia() {
		return this.cedReferencia;
	}

	public void setCedReferencia(String cedReferencia) {
		this.cedReferencia = cedReferencia;
	}

	public String getCedTitulo() {
		return this.cedTitulo;
	}

	public void setCedTitulo(String cedTitulo) {
		this.cedTitulo = cedTitulo;
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

	public Informe getInforme() {
		return this.informe;
	}

	public void setInforme(Informe informe) {
		this.informe = informe;
	}

	public ProcedimientoEjecucion getProcedimientoEjecucion() {
		return this.procedimientoEjecucion;
	}

	public void setProcedimientoEjecucion(ProcedimientoEjecucion procedimientoEjecucion) {
		this.procedimientoEjecucion = procedimientoEjecucion;
	}

	public ProcedimientoPlanificacion getProcedimientoPlanificacion() {
		return this.procedimientoPlanificacion;
	}

	public void setProcedimientoPlanificacion(ProcedimientoPlanificacion procedimientoPlanificacion) {
		this.procedimientoPlanificacion = procedimientoPlanificacion;
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

	public Set<Recomendacion> getRecomendacion() {
		return this.recomendacion;
	}

	public void setRecomendacion(Set<Recomendacion> recomendacion) {
		this.recomendacion = recomendacion;
	}

	public Recomendacion addRecomendacion(Recomendacion recomendacion) {
		getRecomendacion().add(recomendacion);
		recomendacion.setCedulaNota(this);

		return recomendacion;
	}

	public Recomendacion removeRecomendacion(Recomendacion recomendacion) {
		getRecomendacion().remove(recomendacion);
		recomendacion.setCedulaNota(null);

		return recomendacion;
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
		result = prime * result + cedId;
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
		CedulaNota other = (CedulaNota) obj;
		if (cedId != other.cedId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CedulaNota [cedId=" + cedId + ", cedCausa=" + cedCausa + ", cedCondicion=" + cedCondicion
				+ ", cedCriterio=" + cedCriterio + ", cedEfecto=" + cedEfecto + ", cedEstado=" + cedEstado
				+ ", cedFechaElaboro=" + cedFechaElaboro + ", cedFechaReviso=" + cedFechaReviso + ", cedObservacion="
				+ cedObservacion + ", cedReferencia=" + cedReferencia + ", cedTitulo=" + cedTitulo + ", fecCrea="
				+ fecCrea + ", fecModi=" + fecModi + ", regActivo=" + regActivo + ", usuCrea=" + usuCrea + ", usuModi="
				+ usuModi + ", informe=" + informe + ", procedimientoEjecucion=" + procedimientoEjecucion
				+ ", procedimientoPlanificacion=" + procedimientoPlanificacion + ", usuario1=" + usuario1
				+ ", usuario2=" + usuario2 + ", recomendacion=" + recomendacion + "]";
	}

}