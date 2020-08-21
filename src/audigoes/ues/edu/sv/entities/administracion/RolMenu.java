package audigoes.ues.edu.sv.entities.administracion;

import java.io.Serializable;
import javax.persistence.*;

import audigoes.ues.edu.sv.entities.SuperEntity;

import java.util.Date;


/**
 * The persistent class for the rol_menu database table.
 * 
 */
@Entity
@Table(name="rol_menu")
@NamedQuery(name="RolMenu.findAll", query="SELECT r FROM RolMenu r")
public class RolMenu extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="rmn_id")
	private int rmnId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_crea")
	private Date fecCrea;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_modi")
	private Date fecModi;

	@Column(name="reg_activo")
	private int regActivo;

	@Column(name="rmn_mnu_id")
	private int rmnMnuId;

	@Column(name="usu_crea")
	private String usuCrea;

	@Column(name="usu_modi")
	private String usuModi;

	//bi-directional many-to-one association to Menu
	@ManyToOne
	@JoinColumn(name="rmn_rol_id")
	private Menu menu;

	//bi-directional many-to-one association to Rol
	@ManyToOne
	@JoinColumn(name="rmn_rol_id")
	private Rol rol;

	public RolMenu() {
	}

	public int getRmnId() {
		return this.rmnId;
	}

	public void setRmnId(int rmnId) {
		this.rmnId = rmnId;
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

	public int getRmnMnuId() {
		return this.rmnMnuId;
	}

	public void setRmnMnuId(int rmnMnuId) {
		this.rmnMnuId = rmnMnuId;
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

	public Menu getMenu() {
		return this.menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
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
		result = prime * result + rmnId;
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
		RolMenu other = (RolMenu) obj;
		if (rmnId != other.rmnId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RolMenu [rmnId=" + rmnId + ", fecCrea=" + fecCrea + ", fecModi=" + fecModi + ", regActivo=" + regActivo
				+ ", rmnMnuId=" + rmnMnuId + ", usuCrea=" + usuCrea + ", usuModi=" + usuModi + ", menu=" + menu
				+ ", rol=" + rol + "]";
	}

}