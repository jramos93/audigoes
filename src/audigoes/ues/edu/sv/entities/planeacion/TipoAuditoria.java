package audigoes.ues.edu.sv.entities.planeacion;

import java.io.Serializable;
import javax.persistence.*;

import audigoes.ues.edu.sv.entities.SuperEntity;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the tipo_auditoria database table.
 * 
 */
@Entity
@Table(name="tipo_auditoria")
@NamedQuery(name="TipoAuditoria.findAll", query="SELECT t FROM TipoAuditoria t")
public class TipoAuditoria extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="tpa_id")
	private int tpaId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_crea")
	private Date fecCrea;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_modi")
	private Date fecModi;

	@Column(name="reg_activo")
	private int regActivo;

	@Column(name="tpa_descripcion")
	private String tpaDescripcion;

	@Column(name="tpa_nombre")
	private String tpaNombre;

	@Column(name="usu_crea")
	private String usuCrea;

	@Column(name="usu_modi")
	private String usuModi;

	//bi-directional many-to-one association to Auditoria
	@OneToMany(mappedBy="tipoAuditoria")
	private List<Auditoria> auditoria;

	public TipoAuditoria() {
	}

	public int getTpaId() {
		return this.tpaId;
	}

	public void setTpaId(int tpaId) {
		this.tpaId = tpaId;
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

	public String getTpaDescripcion() {
		return this.tpaDescripcion;
	}

	public void setTpaDescripcion(String tpaDescripcion) {
		this.tpaDescripcion = tpaDescripcion;
	}

	public String getTpaNombre() {
		return this.tpaNombre;
	}

	public void setTpaNombre(String tpaNombre) {
		this.tpaNombre = tpaNombre;
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

	public List<Auditoria> getAuditoria() {
		return this.auditoria;
	}

	public void setAuditoria(List<Auditoria> auditoria) {
		this.auditoria = auditoria;
	}

	public Auditoria addAuditoria(Auditoria auditoria) {
		getAuditoria().add(auditoria);
		auditoria.setTipoAuditoria(this);

		return auditoria;
	}

	public Auditoria removeAuditoria(Auditoria auditoria) {
		getAuditoria().remove(auditoria);
		auditoria.setTipoAuditoria(null);

		return auditoria;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + tpaId;
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
		TipoAuditoria other = (TipoAuditoria) obj;
		if (tpaId != other.tpaId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TipoAuditoria [tpaId=" + tpaId + ", fecCrea=" + fecCrea + ", fecModi=" + fecModi + ", regActivo="
				+ regActivo + ", tpaDescripcion=" + tpaDescripcion + ", tpaNombre=" + tpaNombre + ", usuCrea=" + usuCrea
				+ ", usuModi=" + usuModi + ", auditoria=" + auditoria + "]";
	}

}