package audigoes.ues.edu.sv.entities.administracion;

import java.io.Serializable;
import javax.persistence.*;

import audigoes.ues.edu.sv.entities.SuperEntity;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the rol database table.
 * 
 */
@Entity
@NamedQuery(name="Rol.findAll", query="SELECT r FROM Rol r")
public class Rol extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="rol_id")
	private int rolId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_crea")
	private Date fecCrea;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_modi")
	private Date fecModi;

	@Column(name="reg_activo")
	private int regActivo;

	@Column(name="rol_descripcion")
	private String rolDescripcion;

	@Column(name="rol_nombre")
	private String rolNombre;

	@Column(name="usu_crea")
	private String usuCrea;

	@Column(name="usu_modi")
	private String usuModi;

	//bi-directional many-to-one association to RolMenu
	@OneToMany(mappedBy="rol")
	private List<RolMenu> rolMenu;

	//bi-directional many-to-one association to RolPermiso
	@OneToMany(mappedBy="rol")
	private List<RolPermiso> rolPermiso;

	//bi-directional many-to-one association to UsuarioPermiso
	@OneToMany(mappedBy="rol")
	private List<UsuarioPermiso> usuarioPermiso;

	public Rol() {
	}

	public int getRolId() {
		return this.rolId;
	}

	public void setRolId(int rolId) {
		this.rolId = rolId;
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

	public String getRolDescripcion() {
		return this.rolDescripcion;
	}

	public void setRolDescripcion(String rolDescripcion) {
		this.rolDescripcion = rolDescripcion;
	}

	public String getRolNombre() {
		return this.rolNombre;
	}

	public void setRolNombre(String rolNombre) {
		this.rolNombre = rolNombre;
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

	public List<RolMenu> getRolMenu() {
		return this.rolMenu;
	}

	public void setRolMenu(List<RolMenu> rolMenu) {
		this.rolMenu = rolMenu;
	}

	public RolMenu addRolMenu(RolMenu rolMenu) {
		getRolMenu().add(rolMenu);
		rolMenu.setRol(this);

		return rolMenu;
	}

	public RolMenu removeRolMenu(RolMenu rolMenu) {
		getRolMenu().remove(rolMenu);
		rolMenu.setRol(null);

		return rolMenu;
	}

	public List<RolPermiso> getRolPermiso() {
		return this.rolPermiso;
	}

	public void setRolPermiso(List<RolPermiso> rolPermiso) {
		this.rolPermiso = rolPermiso;
	}

	public RolPermiso addRolPermiso(RolPermiso rolPermiso) {
		getRolPermiso().add(rolPermiso);
		rolPermiso.setRol(this);

		return rolPermiso;
	}

	public RolPermiso removeRolPermiso(RolPermiso rolPermiso) {
		getRolPermiso().remove(rolPermiso);
		rolPermiso.setRol(null);

		return rolPermiso;
	}

	public List<UsuarioPermiso> getUsuarioPermiso() {
		return this.usuarioPermiso;
	}

	public void setUsuarioPermiso(List<UsuarioPermiso> usuarioPermiso) {
		this.usuarioPermiso = usuarioPermiso;
	}

	public UsuarioPermiso addUsuarioPermiso(UsuarioPermiso usuarioPermiso) {
		getUsuarioPermiso().add(usuarioPermiso);
		usuarioPermiso.setRol(this);

		return usuarioPermiso;
	}

	public UsuarioPermiso removeUsuarioPermiso(UsuarioPermiso usuarioPermiso) {
		getUsuarioPermiso().remove(usuarioPermiso);
		usuarioPermiso.setRol(null);

		return usuarioPermiso;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + rolId;
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
		Rol other = (Rol) obj;
		if (rolId != other.rolId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Rol [rolId=" + rolId + ", fecCrea=" + fecCrea + ", fecModi=" + fecModi + ", regActivo=" + regActivo
				+ ", rolDescripcion=" + rolDescripcion + ", rolNombre=" + rolNombre + ", usuCrea=" + usuCrea
				+ ", usuModi=" + usuModi + ", rolMenu=" + rolMenu + ", rolPermiso=" + rolPermiso + ", usuarioPermiso="
				+ usuarioPermiso + "]";
	}

}