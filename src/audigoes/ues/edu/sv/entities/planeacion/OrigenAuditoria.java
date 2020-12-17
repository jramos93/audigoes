package audigoes.ues.edu.sv.entities.planeacion;

import java.io.Serializable;
import javax.persistence.*;

import audigoes.ues.edu.sv.entities.SuperEntity;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the origen_auditoria database table.
 * 
 */
@Entity
@Table(name="origen_auditoria")
@NamedQuery(name="OrigenAuditoria.findAll", query="SELECT o FROM OrigenAuditoria o")
public class OrigenAuditoria extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "ori_id", schema = "audigoes", table = "contador", pkColumnName = "cnt_nombre", valueColumnName = "cnt_valor", pkColumnValue = "ori_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ori_id")
	@Column(name="ori_id")
	private int oriId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_crea")
	private Date fecCrea;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_modi")
	private Date fecModi;

	@Column(name="ori_descripcion")
	private String oriDescripcion;

	@Column(name="ori_nombre")
	private String oriNombre;

	@Column(name="reg_activo")
	private int regActivo;

	@Column(name="usu_crea")
	private String usuCrea;

	@Column(name="usu_modi")
	private String usuModi;

	//bi-directional many-to-one association to Auditoria
	@OneToMany(mappedBy="origenAuditoria", fetch=FetchType.EAGER)
	private List<Auditoria> auditoria;

	public OrigenAuditoria() {
	}

	public int getOriId() {
		return this.oriId;
	}

	public void setOriId(int oriId) {
		this.oriId = oriId;
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

	public String getOriDescripcion() {
		return this.oriDescripcion;
	}

	public void setOriDescripcion(String oriDescripcion) {
		this.oriDescripcion = oriDescripcion;
	}

	public String getOriNombre() {
		return this.oriNombre;
	}

	public void setOriNombre(String oriNombre) {
		this.oriNombre = oriNombre;
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

	public List<Auditoria> getAuditoria() {
		return this.auditoria;
	}

	public void setAuditoria(List<Auditoria> auditoria) {
		this.auditoria = auditoria;
	}

	public Auditoria addAuditoria(Auditoria auditoria) {
		getAuditoria().add(auditoria);
		auditoria.setOrigenAuditoria(this);

		return auditoria;
	}

	public Auditoria removeAuditoria(Auditoria auditoria) {
		getAuditoria().remove(auditoria);
		auditoria.setOrigenAuditoria(null);

		return auditoria;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + oriId;
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
		OrigenAuditoria other = (OrigenAuditoria) obj;
		if (oriId != other.oriId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OrigenAuditoria [oriId=" + oriId + ", fecCrea=" + fecCrea + ", fecModi=" + fecModi + ", oriDescripcion="
				+ oriDescripcion + ", oriNombre=" + oriNombre + ", regActivo=" + regActivo + ", usuCrea=" + usuCrea
				+ ", usuModi=" + usuModi + ", auditoria=" + auditoria + "]";
	}

}