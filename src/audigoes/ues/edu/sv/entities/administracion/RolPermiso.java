package audigoes.ues.edu.sv.entities.administracion;

import java.io.Serializable;
import javax.persistence.*;

import audigoes.ues.edu.sv.entities.SuperEntity;

import java.util.Date;


/**
 * The persistent class for the rol_permiso database table.
 * 
 */
@Entity
@Table(name="rol_permiso")
@NamedQuery(name="RolPermiso.findAll", query="SELECT r FROM RolPermiso r")
public class RolPermiso extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "rlp_id", schema = "audigoes", table = "contador", pkColumnName = "cnt_nombre", valueColumnName = "cnt_valor", pkColumnValue = "rlp_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "rlp_id")
	@Column(name="rlp_id")
	private int rlpId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_crea")
	private Date fecCrea;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_modi")
	private Date fecModi;

	@Column(name="reg_activo")
	private int regActivo;
	
	@Column(name="rlp_selected")
	private int rlpSelected;

	@Column(name="usu_crea")
	private String usuCrea;

	@Column(name="usu_modi")
	private String usuModi;

	//bi-directional many-to-one association to Permiso
	@ManyToOne
	@JoinColumn(name="rlp_per_id")
	private Permiso permiso;

	//bi-directional many-to-one association to Rol
	@ManyToOne
	@JoinColumn(name="rlp_rol_id")
	private Rol rol;

	public RolPermiso() {
	}

	public int getRlpId() {
		return this.rlpId;
	}

	public void setRlpId(int rlpId) {
		this.rlpId = rlpId;
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

	public int getRlpSelected() {
		return rlpSelected;
	}

	public void setRlpSelected(int rlpSelected) {
		this.rlpSelected = rlpSelected;
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

	public Permiso getPermiso() {
		return this.permiso;
	}

	public void setPermiso(Permiso permiso) {
		this.permiso = permiso;
	}

	public Rol getRol() {
		return this.rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + rlpId;
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
		RolPermiso other = (RolPermiso) obj;
		if (rlpId != other.rlpId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RolPermiso [rlpId=" + rlpId + ", fecCrea=" + fecCrea + ", fecModi=" + fecModi + ", regActivo="
				+ regActivo + ", rlpSelected=" + rlpSelected + ", usuCrea=" + usuCrea + ", usuModi=" + usuModi
				+ ", permiso=" + permiso + ", rol=" + rol + "]";
	}

}