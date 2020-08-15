package audigoes.ues.edu.sv.entities.administracion;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the unidad database table.
 * 
 */
@Entity
@NamedQuery(name="Unidad.findAll", query="SELECT u FROM Unidad u")
public class Unidad extends audigoes.ues.edu.sv.entities.SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="uni_id")
	private int uniId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_crea")
	private Date fecCrea;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_modi")
	private Date fecModi;

	@Column(name="reg_activo")
	private int regActivo;

	@Column(name="uni_descripcion")
	private String uniDescripcion;

	@Column(name="uni_iniciales")
	private String uniIniciales;

	@Column(name="uni_nombre")
	private String uniNombre;

	@Column(name="uni_ubicacion")
	private String uniUbicacion;

	@Column(name="usu_crea")
	private String usuCrea;

	@Column(name="usu_modi")
	private String usuModi;

	//bi-directional many-to-one association to Institucion
	@ManyToOne
	@JoinColumn(name="uni_ins_id")
	private Institucion institucion;

	//bi-directional many-to-one association to UsuarioUnidad
	@OneToMany(mappedBy="unidad")
	private List<UsuarioUnidad> usuarioUnidads;

	public Unidad() {
	}

	public int getUniId() {
		return this.uniId;
	}

	public void setUniId(int uniId) {
		this.uniId = uniId;
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

	public String getUniDescripcion() {
		return this.uniDescripcion;
	}

	public void setUniDescripcion(String uniDescripcion) {
		this.uniDescripcion = uniDescripcion;
	}

	public String getUniIniciales() {
		return this.uniIniciales;
	}

	public void setUniIniciales(String uniIniciales) {
		this.uniIniciales = uniIniciales;
	}

	public String getUniNombre() {
		return this.uniNombre;
	}

	public void setUniNombre(String uniNombre) {
		this.uniNombre = uniNombre;
	}

	public String getUniUbicacion() {
		return this.uniUbicacion;
	}

	public void setUniUbicacion(String uniUbicacion) {
		this.uniUbicacion = uniUbicacion;
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

	public Institucion getInstitucion() {
		return this.institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public List<UsuarioUnidad> getUsuarioUnidads() {
		return this.usuarioUnidads;
	}

	public void setUsuarioUnidads(List<UsuarioUnidad> usuarioUnidads) {
		this.usuarioUnidads = usuarioUnidads;
	}

	public UsuarioUnidad addUsuarioUnidad(UsuarioUnidad usuarioUnidad) {
		getUsuarioUnidads().add(usuarioUnidad);
		usuarioUnidad.setUnidad(this);

		return usuarioUnidad;
	}

	public UsuarioUnidad removeUsuarioUnidad(UsuarioUnidad usuarioUnidad) {
		getUsuarioUnidads().remove(usuarioUnidad);
		usuarioUnidad.setUnidad(null);

		return usuarioUnidad;
	}

}