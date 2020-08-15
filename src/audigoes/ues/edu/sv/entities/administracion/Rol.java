package audigoes.ues.edu.sv.entities.administracion;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the rol database table.
 * 
 */
@Entity
@NamedQuery(name="Rol.findAll", query="SELECT r FROM Rol r")
public class Rol extends audigoes.ues.edu.sv.entities.SuperEntity implements Serializable {
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
	private List<RolMenu> rolMenus;

	//bi-directional many-to-one association to RolPermiso
	@OneToMany(mappedBy="rol")
	private List<RolPermiso> rolPermisos;

	//bi-directional many-to-one association to UsuarioPermiso
	@OneToMany(mappedBy="rol")
	private List<UsuarioPermiso> usuarioPermisos;

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

	public List<RolMenu> getRolMenus() {
		return this.rolMenus;
	}

	public void setRolMenus(List<RolMenu> rolMenus) {
		this.rolMenus = rolMenus;
	}

	public RolMenu addRolMenus(RolMenu rolMenus) {
		getRolMenus().add(rolMenus);
		rolMenus.setRol(this);

		return rolMenus;
	}

	public RolMenu removeRolMenus(RolMenu rolMenus) {
		getRolMenus().remove(rolMenus);
		rolMenus.setRol(null);

		return rolMenus;
	}

	public List<RolPermiso> getRolPermisos() {
		return this.rolPermisos;
	}

	public void setRolPermisos(List<RolPermiso> rolPermisos) {
		this.rolPermisos = rolPermisos;
	}

	public RolPermiso addRolPermiso(RolPermiso rolPermiso) {
		getRolPermisos().add(rolPermiso);
		rolPermiso.setRol(this);

		return rolPermiso;
	}

	public RolPermiso removeRolPermiso(RolPermiso rolPermiso) {
		getRolPermisos().remove(rolPermiso);
		rolPermiso.setRol(null);

		return rolPermiso;
	}

	public List<UsuarioPermiso> getUsuarioPermisos() {
		return this.usuarioPermisos;
	}

	public void setUsuarioPermisos(List<UsuarioPermiso> usuarioPermisos) {
		this.usuarioPermisos = usuarioPermisos;
	}

	public UsuarioPermiso addUsuarioPermiso(UsuarioPermiso usuarioPermiso) {
		getUsuarioPermisos().add(usuarioPermiso);
		usuarioPermiso.setRol(this);

		return usuarioPermiso;
	}

	public UsuarioPermiso removeUsuarioPermiso(UsuarioPermiso usuarioPermiso) {
		getUsuarioPermisos().remove(usuarioPermiso);
		usuarioPermiso.setRol(null);

		return usuarioPermiso;
	}

}