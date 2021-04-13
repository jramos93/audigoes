package audigoes.ues.edu.sv.entities.administracion;

import java.io.Serializable;
import javax.persistence.*;

import audigoes.ues.edu.sv.entities.SuperEntity;
import audigoes.ues.edu.sv.entities.planeacion.AuditoriaUnidad;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the unidad database table.
 * 
 */
@Entity
@Table(name="unidad")
@NamedQuery(name="Unidad.findAll", query="SELECT u FROM Unidad u")
public class Unidad extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "uni_id", schema = "audigoes", table = "contador", pkColumnName = "cnt_nombre", valueColumnName = "cnt_valor", pkColumnValue = "uni_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "uni_id")
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

	//bi-directional many-to-one association to AuditoriaUnidad
	@OneToMany(mappedBy="unidad")
	private List<AuditoriaUnidad> auditoriaUnidad;

	//bi-directional many-to-one association to Institucion
	@ManyToOne
	@JoinColumn(name="uni_ins_id")
	private Institucion institucion;

	//bi-directional many-to-one association to UsuarioUnidad
	@OneToMany(mappedBy="unidad")
	private List<UsuarioUnidad> usuarioUnidad;

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

	public List<AuditoriaUnidad> getAuditoriaUnidad() {
		return this.auditoriaUnidad;
	}

	public void setAuditoriaUnidad(List<AuditoriaUnidad> auditoriaUnidad) {
		this.auditoriaUnidad = auditoriaUnidad;
	}

	public AuditoriaUnidad addAuditoriaUnidad(AuditoriaUnidad auditoriaUnidad) {
		getAuditoriaUnidad().add(auditoriaUnidad);
		auditoriaUnidad.setUnidad(this);

		return auditoriaUnidad;
	}

	public AuditoriaUnidad removeAuditoriaUnidad(AuditoriaUnidad auditoriaUnidad) {
		getAuditoriaUnidad().remove(auditoriaUnidad);
		auditoriaUnidad.setUnidad(null);

		return auditoriaUnidad;
	}

	public Institucion getInstitucion() {
		return this.institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public List<UsuarioUnidad> getUsuarioUnidad() {
		return this.usuarioUnidad;
	}

	public void setUsuarioUnidad(List<UsuarioUnidad> usuarioUnidad) {
		this.usuarioUnidad = usuarioUnidad;
	}

	public UsuarioUnidad addUsuarioUnidad(UsuarioUnidad usuarioUnidad) {
		getUsuarioUnidad().add(usuarioUnidad);
		usuarioUnidad.setUnidad(this);

		return usuarioUnidad;
	}

	public UsuarioUnidad removeUsuarioUnidad(UsuarioUnidad usuarioUnidad) {
		getUsuarioUnidad().remove(usuarioUnidad);
		usuarioUnidad.setUnidad(null);

		return usuarioUnidad;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + uniId;
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
		Unidad other = (Unidad) obj;
		if (uniId != other.uniId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Unidad [uniId=" + uniId + ", fecCrea=" + fecCrea + ", fecModi=" + fecModi + ", regActivo=" + regActivo
				+ ", uniDescripcion=" + uniDescripcion + ", uniIniciales=" + uniIniciales + ", uniNombre=" + uniNombre
				+ ", uniUbicacion=" + uniUbicacion + ", usuCrea=" + usuCrea + ", usuModi=" + usuModi
				+ ", auditoriaUnidad=" + auditoriaUnidad + ", institucion=" + institucion + ", usuarioUnidad="
				+ usuarioUnidad + "]";
	}

}