package audigoes.ues.edu.sv.entities.administracion;

import java.io.Serializable;
import javax.persistence.*;

import audigoes.ues.edu.sv.entities.SuperEntity;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the institucion database table.
 * 
 */
@Entity
@NamedQuery(name="Institucion.findAll", query="SELECT i FROM Institucion i")
public class Institucion extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "ins_id", schema = "audigoes", table = "contador", pkColumnName = "cnt_nombre", valueColumnName = "cnt_valor", pkColumnValue = "ins_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ins_id")
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

	//bi-directional many-to-one association to Marca
	@OneToMany(mappedBy="institucion")
	private List<Marca> marca;

	//bi-directional many-to-one association to NormativaCedula
	@OneToMany(mappedBy="institucion")
	private List<NormativaCedula> normativaCedula;

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

	public List<Marca> getMarca() {
		return this.marca;
	}

	public void setMarca(List<Marca> marca) {
		this.marca = marca;
	}

	public Marca addMarca(Marca marca) {
		getMarca().add(marca);
		marca.setInstitucion(this);

		return marca;
	}

	public Marca removeMarca(Marca marca) {
		getMarca().remove(marca);
		marca.setInstitucion(null);

		return marca;
	}

	public List<NormativaCedula> getNormativaCedula() {
		return this.normativaCedula;
	}

	public void setNormativaCedula(List<NormativaCedula> normativaCedula) {
		this.normativaCedula = normativaCedula;
	}

	public NormativaCedula addNormativaCedula(NormativaCedula normativaCedula) {
		getNormativaCedula().add(normativaCedula);
		normativaCedula.setInstitucion(this);

		return normativaCedula;
	}

	public NormativaCedula removeNormativaCedula(NormativaCedula normativaCedula) {
		getNormativaCedula().remove(normativaCedula);
		normativaCedula.setInstitucion(null);

		return normativaCedula;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + insId;
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
		Institucion other = (Institucion) obj;
		if (insId != other.insId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Institucion [insId=" + insId + ", fecCrea=" + fecCrea + ", fecModi=" + fecModi + ", insDireccion="
				+ insDireccion + ", insIniciales=" + insIniciales + ", insLogo=" + insLogo + ", insNit=" + insNit
				+ ", insNombre=" + insNombre + ", insSlogan=" + insSlogan + ", insTelefono=" + insTelefono
				+ ", regActivo=" + regActivo + ", usuCrea=" + usuCrea + ", usuModi=" + usuModi + ", configuracion="
				+ configuracion + ", marca=" + marca + ", normativaCedula=" + normativaCedula + ", unidad=" + unidad
				+ ", usuario=" + usuario + "]";
	}

}