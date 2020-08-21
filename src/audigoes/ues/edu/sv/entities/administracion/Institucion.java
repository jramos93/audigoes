package audigoes.ues.edu.sv.entities.administracion;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the institucion database table.
 * 
 */
@Entity
@NamedQuery(name="Institucion.findAll", query="SELECT i FROM Institucion i")
public class Institucion extends audigoes.ues.edu.sv.entities.SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="ins_id")
	private int insId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_crea")
	private Date fecCrea;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_modi")
	private Date fecModi;

	@Column(name="ins_direccion")
	private String insDireccion;

	@Column(name="ins_iniciales")
	private String insIniciales;

	@Lob
	@Column(name="ins_logo")
	private String insLogo;

	@Column(name="ins_nit")
	private String insNit;

	@Column(name="ins_nombre")
	private String insNombre;

	@Column(name="ins_slogan")
	private String insSlogan;

	@Column(name="ins_telefono")
	private String insTelefono;

	@Column(name="reg_activo")
	private int regActivo;

	@Column(name="usu_crea")
	private String usuCrea;

	@Column(name="usu_modi")
	private String usuModi;

	//bi-directional many-to-one association to Configuracion
	@OneToMany(mappedBy="institucion")
	private List<Configuracion> configuracion;

	//bi-directional many-to-one association to Unidad
	@OneToMany(mappedBy="institucion")
	private List<Unidad> unidad;

	//bi-directional many-to-one association to Usuario
	@OneToMany(mappedBy="institucion")
	private List<Usuario> usuario;

	public Institucion() {
	}

	public int getInsId() {
		return this.insId;
	}

	public void setInsId(int insId) {
		this.insId = insId;
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

	public String getInsDireccion() {
		return this.insDireccion;
	}

	public void setInsDireccion(String insDireccion) {
		this.insDireccion = insDireccion;
	}

	public String getInsIniciales() {
		return this.insIniciales;
	}

	public void setInsIniciales(String insIniciales) {
		this.insIniciales = insIniciales;
	}

	public String getInsLogo() {
		return this.insLogo;
	}

	public void setInsLogo(String insLogo) {
		this.insLogo = insLogo;
	}

	public String getInsNit() {
		return this.insNit;
	}

	public void setInsNit(String insNit) {
		this.insNit = insNit;
	}

	public String getInsNombre() {
		return this.insNombre;
	}

	public void setInsNombre(String insNombre) {
		this.insNombre = insNombre;
	}

	public String getInsSlogan() {
		return this.insSlogan;
	}

	public void setInsSlogan(String insSlogan) {
		this.insSlogan = insSlogan;
	}

	public String getInsTelefono() {
		return this.insTelefono;
	}

	public void setInsTelefono(String insTelefono) {
		this.insTelefono = insTelefono;
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

	public List<Configuracion> getConfiguracion() {
		return this.configuracion;
	}

	public void setConfiguracion(List<Configuracion> configuracion) {
		this.configuracion = configuracion;
	}

	public Configuracion addConfiguracion(Configuracion configuracion) {
		getConfiguracion().add(configuracion);
		configuracion.setInstitucion(this);

		return configuracion;
	}

	public Configuracion removeConfiguracion(Configuracion configuracion) {
		getConfiguracion().remove(configuracion);
		configuracion.setInstitucion(null);

		return configuracion;
	}

	public List<Unidad> getUnidad() {
		return this.unidad;
	}

	public void setUnidad(List<Unidad> unidad) {
		this.unidad = unidad;
	}

	public Unidad addUnidad(Unidad unidad) {
		getUnidad().add(unidad);
		unidad.setInstitucion(this);

		return unidad;
	}

	public Unidad removeUnidad(Unidad unidad) {
		getUnidad().remove(unidad);
		unidad.setInstitucion(null);

		return unidad;
	}

	public List<Usuario> getUsuario() {
		return this.usuario;
	}

	public void setUsuario(List<Usuario> usuario) {
		this.usuario = usuario;
	}

	public Usuario addUsuario(Usuario usuario) {
		getUsuario().add(usuario);
		usuario.setInstitucion(this);

		return usuario;
	}

	public Usuario removeUsuario(Usuario usuario) {
		getUsuario().remove(usuario);
		usuario.setInstitucion(null);

		return usuario;
	}

}