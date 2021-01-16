package audigoes.ues.edu.sv.entities.administracion;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import audigoes.ues.edu.sv.entities.planeacion.Auditoria;

/**
 * The persistent class for the bitacora database table.
 * 
 */
@Entity
@Table(name="bitacora_actividades")
@NamedQuery(name = "BitacoraActividades.findAll", query = "SELECT b FROM BitacoraActividades b")
public class BitacoraActividades extends audigoes.ues.edu.sv.entities.SuperEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@TableGenerator(name = "bita_id", schema = "audigoes", table = "contador", pkColumnName = "cnt_nombre", valueColumnName = "cnt_valor", pkColumnValue = "bita_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "bita_id")
	@Column(name = "bita_id")
	private int bitaId;

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

	@Column(name = "bita_accion")
	private String bitaAccion;
	
	@Column(name = "bita_correlativo")
	private int bitaCorrelativo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "bita_fecha_inicio")
	private Date bitaFechaInicio;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "bita_fecha_fin")
	private Date bitaFechaFin;

	// bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name = "bita_usu_id_inicio")
	private Usuario usuarioInicio;

	// bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name = "bita_usu_id_fin")
	private Usuario usuarioFin;

	// bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name = "bita_aud_id")
	private Auditoria auditoria;
	
	public BitacoraActividades() {}

	public int getBitaId() {
		return bitaId;
	}

	public void setBitaId(int bitaId) {
		this.bitaId = bitaId;
	}

	public Date getFecCrea() {
		return fecCrea;
	}

	public void setFecCrea(Date fecCrea) {
		this.fecCrea = fecCrea;
	}

	public Date getFecModi() {
		return fecModi;
	}

	public void setFecModi(Date fecModi) {
		this.fecModi = fecModi;
	}

	public int getRegActivo() {
		return regActivo;
	}

	public void setRegActivo(int regActivo) {
		this.regActivo = regActivo;
	}

	public String getUsuCrea() {
		return usuCrea;
	}

	public void setUsuCrea(String usuCrea) {
		this.usuCrea = usuCrea;
	}

	public String getUsuModi() {
		return usuModi;
	}

	public void setUsuModi(String usuModi) {
		this.usuModi = usuModi;
	}

	public String getBitaAccion() {
		return bitaAccion;
	}

	public void setBitaAccion(String bitaAccion) {
		this.bitaAccion = bitaAccion;
	}

	public Date getBitaFechaInicio() {
		return bitaFechaInicio;
	}

	public void setBitaFechaInicio(Date bitaFechaInicio) {
		this.bitaFechaInicio = bitaFechaInicio;
	}

	public Date getBitaFechaFin() {
		return bitaFechaFin;
	}

	public void setBitaFechaFin(Date bitaFechaFin) {
		this.bitaFechaFin = bitaFechaFin;
	}

	public Usuario getUsuarioInicio() {
		return usuarioInicio;
	}

	public void setUsuarioInicio(Usuario usuarioInicio) {
		this.usuarioInicio = usuarioInicio;
	}

	public Usuario getUsuarioFin() {
		return usuarioFin;
	}

	public void setUsuarioFin(Usuario usuarioFin) {
		this.usuarioFin = usuarioFin;
	}

	public Auditoria getAuditoria() {
		return auditoria;
	}

	public void setAuditoria(Auditoria auditoria) {
		this.auditoria = auditoria;
	}

	public int getBitaCorrelativo() {
		return bitaCorrelativo;
	}

	public void setBitaCorrelativo(int bitaCorrelativo) {
		this.bitaCorrelativo = bitaCorrelativo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + bitaId;
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
		return true;
	}
	
	
}
