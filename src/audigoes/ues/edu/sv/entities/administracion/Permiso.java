package audigoes.ues.edu.sv.entities.administracion;

import java.io.Serializable;
import javax.persistence.*;

import audigoes.ues.edu.sv.entities.SuperEntity;

import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the permiso database table.
 * 
 */
@Entity
@NamedQuery(name="Permiso.findAll", query="SELECT p FROM Permiso p")
public class Permiso extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "per_id", schema = "audigoes", table = "contador", pkColumnName = "cnt_nombre", valueColumnName = "cnt_valor", pkColumnValue = "per_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "per_id")
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
	@OneToMany(mappedBy="permiso", fetch=FetchType.EAGER)
	private Set<RolPermiso> rolPermiso;

	//bi-directional many-to-one association to UsuarioPermiso
	@OneToMany(mappedBy="permiso", fetch=FetchType.EAGER)
	private Set<UsuarioPermiso> usuarioPermiso;

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

	public Set<RolPermiso> getRolPermiso() {
		return this.rolPermiso;
	}

	public void setRolPermiso(Set<RolPermiso> rolPermiso) {
		this.rolPermiso = rolPermiso;
	}

	public RolPermiso addRolPermiso(RolPermiso rolPermiso) {
		getRolPermiso().add(rolPermiso);
		rolPermiso.setPermiso(this);

		return rolPermiso;
	}

	public RolPermiso removeRolPermiso(RolPermiso rolPermiso) {
		getRolPermiso().remove(rolPermiso);
		rolPermiso.setPermiso(null);

		return rolPermiso;
	}

	public Set<UsuarioPermiso> getUsuarioPermiso() {
		return this.usuarioPermiso;
	}

	public void setUsuarioPermiso(Set<UsuarioPermiso> usuarioPermiso) {
		this.usuarioPermiso = usuarioPermiso;
	}

	public UsuarioPermiso addUsuarioPermiso(UsuarioPermiso usuarioPermiso) {
		getUsuarioPermiso().add(usuarioPermiso);
		usuarioPermiso.setPermiso(this);

		return usuarioPermiso;
	}

	public UsuarioPermiso removeUsuarioPermiso(UsuarioPermiso usuarioPermiso) {
		getUsuarioPermiso().remove(usuarioPermiso);
		usuarioPermiso.setPermiso(null);

		return usuarioPermiso;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + perId;
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
		Permiso other = (Permiso) obj;
		if (perId != other.perId)
			return false;
		return true;
	}


}