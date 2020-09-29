package audigoes.ues.edu.sv.entities.administracion;

import java.io.Serializable;
import javax.persistence.*;

import audigoes.ues.edu.sv.entities.SuperEntity;

import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the normativa_cedula database table.
 * 
 */
@Entity
@Table(name="normativa_cedula")
@NamedQuery(name="NormativaCedula.findAll", query="SELECT n FROM NormativaCedula n")
public class NormativaCedula extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "nor_id", schema = "audigoes", table = "contador", pkColumnName = "cnt_nombre", valueColumnName = "cnt_valor", pkColumnValue = "nor_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "nor_id")
	@Column(name="nor_id")
	private int norId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_crea")
	private Date fecCrea;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_modi")
	private Date fecModi;

	@Column(name="nor_anio")
	private String norAnio;

	@Column(name="nor_autor")
	private String norAutor;

	@Column(name="nor_descripcion")
	private String norDescripcion;

	@Column(name="nor_nombre")
	private String norNombre;

	@Column(name="nor_ruta")
	private String norRuta;

	@Column(name="nor_tipo")
	private int norTipo;

	@Column(name="nor_version")
	private String norVersion;

	@Column(name="reg_activo")
	private int regActivo;

	@Column(name="usu_crea")
	private String usuCrea;

	@Column(name="usu_modi")
	private String usuModi;

	//bi-directional many-to-one association to Criterio
	@OneToMany(mappedBy="normativaCedula", fetch=FetchType.EAGER)
	private Set<Criterio> criterio;

	//bi-directional many-to-one association to Institucion
	@ManyToOne
	@JoinColumn(name="nor_ins_id")
	private Institucion institucion;

	public NormativaCedula() {
	}

	public int getNorId() {
		return this.norId;
	}

	public void setNorId(int norId) {
		this.norId = norId;
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

	public String getNorAnio() {
		return this.norAnio;
	}

	public void setNorAnio(String norAnio) {
		this.norAnio = norAnio;
	}

	public String getNorAutor() {
		return this.norAutor;
	}

	public void setNorAutor(String norAutor) {
		this.norAutor = norAutor;
	}

	public String getNorDescripcion() {
		return this.norDescripcion;
	}

	public void setNorDescripcion(String norDescripcion) {
		this.norDescripcion = norDescripcion;
	}

	public String getNorNombre() {
		return this.norNombre;
	}

	public void setNorNombre(String norNombre) {
		this.norNombre = norNombre;
	}

	public String getNorRuta() {
		return this.norRuta;
	}

	public void setNorRuta(String norRuta) {
		this.norRuta = norRuta;
	}

	public int getNorTipo() {
		return this.norTipo;
	}

	public void setNorTipo(int norTipo) {
		this.norTipo = norTipo;
	}

	public String getNorVersion() {
		return this.norVersion;
	}

	public void setNorVersion(String norVersion) {
		this.norVersion = norVersion;
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

	public Set<Criterio> getCriterio() {
		return this.criterio;
	}

	public void setCriterio(Set<Criterio> criterio) {
		this.criterio = criterio;
	}

	public Criterio addCriterio(Criterio criterio) {
		getCriterio().add(criterio);
		criterio.setNormativaCedula(this);

		return criterio;
	}

	public Criterio removeCriterio(Criterio criterio) {
		getCriterio().remove(criterio);
		criterio.setNormativaCedula(null);

		return criterio;
	}

	public Institucion getInstitucion() {
		return this.institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + norId;
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
		NormativaCedula other = (NormativaCedula) obj;
		if (norId != other.norId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NormativaCedula [norId=" + norId + ", fecCrea=" + fecCrea + ", fecModi=" + fecModi + ", norAnio="
				+ norAnio + ", norAutor=" + norAutor + ", norDescripcion=" + norDescripcion + ", norNombre=" + norNombre
				+ ", norRuta=" + norRuta + ", norTipo=" + norTipo + ", norVersion=" + norVersion + ", regActivo="
				+ regActivo + ", usuCrea=" + usuCrea + ", usuModi=" + usuModi + ", criterio=" + criterio
				+ ", institucion=" + institucion + "]";
	}

}