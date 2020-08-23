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

import audigoes.ues.edu.sv.entities.SuperEntity;

/**
 * The persistent class for the rol_menu database table.
 * 
 */
@Entity
@Table(name = "rol_menu")
@NamedQuery(name = "RolMenu.findAll", query = "SELECT r FROM RolMenu r")
public class RolMenu extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "rmn_id", schema = "audigoes", table = "contador", pkColumnName = "cnt_nombre", valueColumnName = "cnt_valor", pkColumnValue = "rmn_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "rmn_id")
	@Column(name = "rmn_id")
	private int rmnId;

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

	// bi-directional many-to-one association to Menu
	@ManyToOne
	@JoinColumn(name = "rmn_mnu_id")
	private Menu menu;

	// bi-directional many-to-one association to Rol
	@ManyToOne
	@JoinColumn(name = "rmn_rol_id")
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
				+ ", usuCrea=" + usuCrea + ", usuModi=" + usuModi + ", menu=" + menu + ", rol=" + rol + "]";
	}

}