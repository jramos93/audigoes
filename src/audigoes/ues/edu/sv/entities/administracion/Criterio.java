package audigoes.ues.edu.sv.entities.administracion;

import java.io.Serializable;
import javax.persistence.*;

import audigoes.ues.edu.sv.entities.SuperEntity;

import java.util.Date;


/**
 * The persistent class for the criterio database table.
 * 
 */
@Entity
@NamedQuery(name="Criterio.findAll", query="SELECT c FROM Criterio c")
public class Criterio extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="cri_id")
	private int criId;

	@Lob
	@Column(name="cri_descripcion")
	private String criDescripcion;

	@Column(name="cri_nombre")
	private String criNombre;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_crea")
	private Date fecCrea;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_modi")
	private Date fecModi;

	@Column(name="reg_activo")
	private int regActivo;

	@Column(name="usu_crea")
	private String usuCrea;

	@Column(name="usu_modi")
	private String usuModi;

	//bi-directional many-to-one association to NormativaCedula
	@ManyToOne
	@JoinColumn(name="cri_nor_id")
	private NormativaCedula normativaCedula;

	public Criterio() {
	}

	public int getCriId() {
		return this.criId;
	}

	public void setCriId(int criId) {
		this.criId = criId;
	}

	public String getCriDescripcion() {
		return this.criDescripcion;
	}

	public void setCriDescripcion(String criDescripcion) {
		this.criDescripcion = criDescripcion;
	}

	public String getCriNombre() {
		return this.criNombre;
	}

	public void setCriNombre(String criNombre) {
		this.criNombre = criNombre;
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

	public NormativaCedula getNormativaCedula() {
		return this.normativaCedula;
	}

	public void setNormativaCedula(NormativaCedula normativaCedula) {
		this.normativaCedula = normativaCedula;
	}

}