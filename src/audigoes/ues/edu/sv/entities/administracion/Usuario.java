package audigoes.ues.edu.sv.entities.administracion;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario extends audigoes.ues.edu.sv.entities.SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="usu_id")
	private int usuId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_crea")
	private Date fecCrea;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_modi")
	private Date fecModi;

	@Column(name="reg_activo")
	private int regActivo;

	@Column(name="usu_apellido")
	private String usuApellido;

	@Column(name="usu_cargo")
	private String usuCargo;

	@Column(name="usu_carnet_empleado")
	private String usuCarnetEmpleado;

	@Lob
	@Column(name="usu_contrasenia")
	private String usuContrasenia;

	@Column(name="usu_correo")
	private String usuCorreo;

	@Column(name="usu_crea")
	private String usuCrea;

	@Column(name="usu_direccion_residencia")
	private String usuDireccionResidencia;

	@Column(name="usu_dui")
	private String usuDui;

	@Temporal(TemporalType.DATE)
	@Column(name="usu_fecha_nacimiento")
	private Date usuFechaNacimiento;

	@Lob
	@Column(name="usu_foto")
	private String usuFoto;

	@Column(name="usu_genero")
	private int usuGenero;

	@Column(name="usu_modi")
	private String usuModi;

	@Column(name="usu_nit")
	private String usuNit;

	@Column(name="usu_nombre")
	private String usuNombre;

	@Column(name="usu_usuario")
	private String usuUsuario;

	//bi-directional many-to-one association to Institucion
	@ManyToOne
	@JoinColumn(name="usu_ins_id")
	private Institucion institucion;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="usu_jefe_id")
	private Usuario usuario;

	//bi-directional many-to-one association to Usuario
	@OneToMany(mappedBy="usuario")
	private List<Usuario> usuarios;

	//bi-directional many-to-one association to UsuarioPermiso
	@OneToMany(mappedBy="usuario")
	private List<UsuarioPermiso> usuarioPermisos;

	//bi-directional many-to-one association to UsuarioUnidad
	@OneToMany(mappedBy="usuario")
	private List<UsuarioUnidad> usuarioUnidads;

	public Usuario() {
	}

	public int getUsuId() {
		return this.usuId;
	}

	public void setUsuId(int usuId) {
		this.usuId = usuId;
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

	public String getUsuApellido() {
		return this.usuApellido;
	}

	public void setUsuApellido(String usuApellido) {
		this.usuApellido = usuApellido;
	}

	public String getUsuCargo() {
		return this.usuCargo;
	}

	public void setUsuCargo(String usuCargo) {
		this.usuCargo = usuCargo;
	}

	public String getUsuCarnetEmpleado() {
		return this.usuCarnetEmpleado;
	}

	public void setUsuCarnetEmpleado(String usuCarnetEmpleado) {
		this.usuCarnetEmpleado = usuCarnetEmpleado;
	}

	public String getUsuContrasenia() {
		return this.usuContrasenia;
	}

	public void setUsuContrasenia(String usuContrasenia) {
		this.usuContrasenia = usuContrasenia;
	}

	public String getUsuCorreo() {
		return this.usuCorreo;
	}

	public void setUsuCorreo(String usuCorreo) {
		this.usuCorreo = usuCorreo;
	}

	public String getUsuCrea() {
		return this.usuCrea;
	}

	public void setUsuCrea(String usuCrea) {
		this.usuCrea = usuCrea;
	}

	public String getUsuDireccionResidencia() {
		return this.usuDireccionResidencia;
	}

	public void setUsuDireccionResidencia(String usuDireccionResidencia) {
		this.usuDireccionResidencia = usuDireccionResidencia;
	}

	public String getUsuDui() {
		return this.usuDui;
	}

	public void setUsuDui(String usuDui) {
		this.usuDui = usuDui;
	}

	public Date getUsuFechaNacimiento() {
		return this.usuFechaNacimiento;
	}

	public void setUsuFechaNacimiento(Date usuFechaNacimiento) {
		this.usuFechaNacimiento = usuFechaNacimiento;
	}

	public String getUsuFoto() {
		return this.usuFoto;
	}

	public void setUsuFoto(String usuFoto) {
		this.usuFoto = usuFoto;
	}

	public int getUsuGenero() {
		return this.usuGenero;
	}

	public void setUsuGenero(int usuGenero) {
		this.usuGenero = usuGenero;
	}

	public String getUsuModi() {
		return this.usuModi;
	}

	public void setUsuModi(String usuModi) {
		this.usuModi = usuModi;
	}

	public String getUsuNit() {
		return this.usuNit;
	}

	public void setUsuNit(String usuNit) {
		this.usuNit = usuNit;
	}

	public String getUsuNombre() {
		return this.usuNombre;
	}

	public void setUsuNombre(String usuNombre) {
		this.usuNombre = usuNombre;
	}

	public String getUsuUsuario() {
		return this.usuUsuario;
	}

	public void setUsuUsuario(String usuUsuario) {
		this.usuUsuario = usuUsuario;
	}

	public Institucion getInstitucion() {
		return this.institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario addUsuario(Usuario usuario) {
		getUsuarios().add(usuario);
		usuario.setUsuario(this);

		return usuario;
	}

	public Usuario removeUsuario(Usuario usuario) {
		getUsuarios().remove(usuario);
		usuario.setUsuario(null);

		return usuario;
	}

	public List<UsuarioPermiso> getUsuarioPermisos() {
		return this.usuarioPermisos;
	}

	public void setUsuarioPermisos(List<UsuarioPermiso> usuarioPermisos) {
		this.usuarioPermisos = usuarioPermisos;
	}

	public UsuarioPermiso addUsuarioPermiso(UsuarioPermiso usuarioPermiso) {
		getUsuarioPermisos().add(usuarioPermiso);
		usuarioPermiso.setUsuario(this);

		return usuarioPermiso;
	}

	public UsuarioPermiso removeUsuarioPermiso(UsuarioPermiso usuarioPermiso) {
		getUsuarioPermisos().remove(usuarioPermiso);
		usuarioPermiso.setUsuario(null);

		return usuarioPermiso;
	}

	public List<UsuarioUnidad> getUsuarioUnidads() {
		return this.usuarioUnidads;
	}

	public void setUsuarioUnidads(List<UsuarioUnidad> usuarioUnidads) {
		this.usuarioUnidads = usuarioUnidads;
	}

	public UsuarioUnidad addUsuarioUnidad(UsuarioUnidad usuarioUnidad) {
		getUsuarioUnidads().add(usuarioUnidad);
		usuarioUnidad.setUsuario(this);

		return usuarioUnidad;
	}

	public UsuarioUnidad removeUsuarioUnidad(UsuarioUnidad usuarioUnidad) {
		getUsuarioUnidads().remove(usuarioUnidad);
		usuarioUnidad.setUsuario(null);

		return usuarioUnidad;
	}

}