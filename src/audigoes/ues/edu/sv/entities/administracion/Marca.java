package audigoes.ues.edu.sv.entities.administracion;

import java.io.Serializable;
import javax.persistence.*;

import audigoes.ues.edu.sv.entities.SuperEntity;

import java.util.Date;


/**
 * The persistent class for the marca database table.
 * 
 */
@Entity
@NamedQuery(name="Marca.findAll", query="SELECT m FROM Marca m")
public class Marca extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="mar_id")
	private int marId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_crea")
	private Date fecCrea;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_modi")
	private Date fecModi;

	@Column(name="mar_descripcion")
	private String marDescripcion;

	@Column(name="mar_nombre")
	private String marNombre;

	@Column(name="mar_ruta")
	private String marRuta;

	@Column(name="mar_simbolo")
	private String marSimbolo;

	@Column(name="reg_activo")
	private int regActivo;

	@Column(name="usu_crea")
	private String usuCrea;

	@Column(name="usu_modi")
	private String usuModi;

	//bi-directional many-to-one association to Institucion
	@ManyToOne
	@JoinColumn(name="mar_ins_id")
	private Institucion institucion;

	public Marca() {
	}

	public int getMarId() {
		return this.marId;
	}

	public void setMarId(int marId) {
		this.marId = marId;
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

	public String getMarDescripcion() {
		return this.marDescripcion;
	}

	public void setMarDescripcion(String marDescripcion) {
		this.marDescripcion = marDescripcion;
	}

	public String getMarNombre() {
		return this.marNombre;
	}

	public void setMarNombre(String marNombre) {
		this.marNombre = marNombre;
	}

	public String getMarRuta() {
		return this.marRuta;
	}

	public void setMarRuta(String marRuta) {
		this.marRuta = marRuta;
	}

	public String getMarSimbolo() {
		return this.marSimbolo;
	}

	public void setMarSimbolo(String marSimbolo) {
		this.marSimbolo = marSimbolo;
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
		result = prime * result + marId;
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
		Marca other = (Marca) obj;
		if (marId != other.marId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Marca [marId=" + marId + ", fecCrea=" + fecCrea + ", fecModi=" + fecModi + ", marDescripcion="
				+ marDescripcion + ", marNombre=" + marNombre + ", marRuta=" + marRuta + ", marSimbolo=" + marSimbolo
				+ ", regActivo=" + regActivo + ", usuCrea=" + usuCrea + ", usuModi=" + usuModi + ", institucion="
				+ institucion + "]";
	}

}