package audigoes.ues.edu.sv.entities.administracion;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the permiso database table.
 * 
 */
@Entity
@NamedQuery(name="Permiso.findAll", query="SELECT p FROM Permiso p")
public class Permiso extends audigoes.ues.edu.sv.entities.SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="per_id")
	private int perId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_crea")
	private Date fecCrea;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_modi")
	private Date fecModi;

	@Column(name="per_descripcion")
	private String perDescripcion;

	@Column(name="per_nombre")
	private String perNombre;

	@Column(name="reg_activo")
	private int regActivo;

	@Column(name="usu_crea")
	private String usuCrea;

	@Column(name="usu_modi")
	private String usuModi;

	//bi-directional many-to-one association to RolPermiso
	@OneToMany(mappedBy="permiso")
	private List<RolPermiso> rolPermisos;

	//bi-directional many-to-one association to UsuarioPermiso
	@OneToMany(mappedBy="permiso")
	private List<UsuarioPermiso> usuarioPermisos;

	public Permiso() {
	}

	public int getPerId() {
		return this.perId;
	}

	public void setPerId(int perId) {
		this.perId = perId;
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

	public String getPerDescripcion() {
		return this.perDescripcion;
	}

	public void setPerDescripcion(String perDescripcion) {
		this.perDescripcion = perDescripcion;
	}

	public String getPerNombre() {
		return this.perNombre;
	}

	public void setPerNombre(String perNombre) {
		this.perNombre = perNombre;
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

	public List<RolPermiso> getRolPermisos() {
		return this.rolPermisos;
	}

	public void setRolPermisos(List<RolPermiso> rolPermisos) {
		this.rolPermisos = rolPermisos;
	}

	public RolPermiso addRolPermiso(RolPermiso rolPermiso) {
		getRolPermisos().add(rolPermiso);
		rolPermiso.setPermiso(this);

		return rolPermiso;
	}

	public RolPermiso removeRolPermiso(RolPermiso rolPermiso) {
		getRolPermisos().remove(rolPermiso);
		rolPermiso.setPermiso(null);

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
		usuarioPermiso.setPermiso(this);

		return usuarioPermiso;
	}

	public UsuarioPermiso removeUsuarioPermiso(UsuarioPermiso usuarioPermiso) {
		getUsuarioPermisos().remove(usuarioPermiso);
		usuarioPermiso.setPermiso(null);

		return usuarioPermiso;
	}

}