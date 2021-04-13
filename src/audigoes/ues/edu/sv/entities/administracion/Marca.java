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
@Table(name="marca")
@NamedQuery(name="Marca.findAll", query="SELECT m FROM Marca m")
public class Marca extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "mar_id", schema = "audigoes", table = "contador", pkColumnName = "cnt_nombre", valueColumnName = "cnt_valor", pkColumnValue = "mar_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "mar_id")
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
	
	@Lob
	@Column(name="mar_arc_archivo")
	private byte[] marArcArchivo;

	@Column(name="mar_arc_ext")
	private String marArcExt;

	@Column(name="mar_arc_nombre")
	private String marArcNombre;

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
	
	public byte[] getMarArcArchivo() {
		return marArcArchivo;
	}

	public void setMarArcArchivo(byte[] marArcArchivo) {
		this.marArcArchivo = marArcArchivo;
	}

	public String getMarArcExt() {
		return marArcExt;
	}

	public void setMarArcExt(String marArcExt) {
		this.marArcExt = marArcExt;
	}

	public String getMarArcNombre() {
		return marArcNombre;
	}

	public void setMarArcNombre(String marArcNombre) {
		this.marArcNombre = marArcNombre;
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
				+ marDescripcion + ", marNombre=" + marNombre 
				+ ", marArcNombre=" + marArcNombre + ", marArcArchivo=" + marArcArchivo + ", marArcExt=" + marArcExt
				+ ", regActivo=" + regActivo + ", usuCrea=" + usuCrea + ", usuModi=" + usuModi + ", institucion="
				+ institucion + "]";
	}

}