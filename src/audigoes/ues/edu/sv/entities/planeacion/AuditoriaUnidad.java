package audigoes.ues.edu.sv.entities.planeacion;

import java.io.Serializable;
import javax.persistence.*;

import audigoes.ues.edu.sv.entities.SuperEntity;
import audigoes.ues.edu.sv.entities.administracion.Unidad;

import java.util.Date;


/**
 * The persistent class for the auditoria_unidad database table.
 * 
 */
@Entity
@Table(name="auditoria_unidad")
@NamedQuery(name="AuditoriaUnidad.findAll", query="SELECT a FROM AuditoriaUnidad a")
public class AuditoriaUnidad extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "auu_id", schema = "audigoes", table = "contador", pkColumnName = "cnt_nombre", valueColumnName = "cnt_valor", pkColumnValue = "auu_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "auu_id")
	@Column(name="auu_id")
	private int auuId;

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

	//bi-directional many-to-one association to Auditoria
	@ManyToOne
	@JoinColumn(name="auu_aud_id")
	private Auditoria auditoria;

	//bi-directional many-to-one association to Unidad
	@ManyToOne
	@JoinColumn(name="auu_uni_id")
	private Unidad unidad;

	public AuditoriaUnidad() {
	}

	public int getAuuId() {
		return this.auuId;
	}

	public void setAuuId(int auuId) {
		this.auuId = auuId;
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

	public Auditoria getAuditoria() {
		return this.auditoria;
	}

	public void setAuditoria(Auditoria auditoria) {
		this.auditoria = auditoria;
	}

	public Unidad getUnidad() {
		return this.unidad;
	}

	public void setUnidad(Unidad unidad) {
		this.unidad = unidad;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + auuId;
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
		AuditoriaUnidad other = (AuditoriaUnidad) obj;
		if (auuId != other.auuId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AuditoriaUnidad [auuId=" + auuId + ", fecCrea=" + fecCrea + ", fecModi=" + fecModi + ", regActivo="
				+ regActivo + ", usuCrea=" + usuCrea + ", usuModi=" + usuModi + ", auditoria=" + auditoria + ", unidad="
				+ unidad + "]";
	}

}