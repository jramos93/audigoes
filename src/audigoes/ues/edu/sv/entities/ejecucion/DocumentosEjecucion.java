package audigoes.ues.edu.sv.entities.ejecucion;

import java.io.Serializable;
import javax.persistence.*;

import audigoes.ues.edu.sv.entities.SuperEntity;

import java.util.Date;


/**
 * The persistent class for the documentos_ejecucion database table.
 * 
 */
@Entity
@Table(name="documentos_ejecucion")
@NamedQuery(name="DocumentosEjecucion.findAll", query="SELECT d FROM DocumentosEjecucion d")
public class DocumentosEjecucion extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "doe_id", schema = "audigoes", table = "contador", pkColumnName = "cnt_nombre", valueColumnName = "cnt_valor", pkColumnValue = "doe_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "doe_id")
	@Column(name="doe_id")
	private int doeId;

	@Column(name="doe_marca")
	private String doeMarca;

	@Column(name="doe_nombre")
	private String doeNombre;

	@Column(name="doe_ref")
	private String doeRef;

	@Column(name="doe_ruta")
	private String doeRuta;

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

	//bi-directional many-to-one association to ProcedimientoEjecucion
	@ManyToOne
	@JoinColumn(name="doe_pej_id")
	private ProcedimientoEjecucion procedimientoEjecucion;

	public DocumentosEjecucion() {
	}

	public int getDoeId() {
		return this.doeId;
	}

	public void setDoeId(int doeId) {
		this.doeId = doeId;
	}

	public String getDoeMarca() {
		return this.doeMarca;
	}

	public void setDoeMarca(String doeMarca) {
		this.doeMarca = doeMarca;
	}

	public String getDoeNombre() {
		return this.doeNombre;
	}

	public void setDoeNombre(String doeNombre) {
		this.doeNombre = doeNombre;
	}

	public String getDoeRef() {
		return this.doeRef;
	}

	public void setDoeRef(String doeRef) {
		this.doeRef = doeRef;
	}

	public String getDoeRuta() {
		return this.doeRuta;
	}

	public void setDoeRuta(String doeRuta) {
		this.doeRuta = doeRuta;
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

	public ProcedimientoEjecucion getProcedimientoEjecucion() {
		return this.procedimientoEjecucion;
	}

	public void setProcedimientoEjecucion(ProcedimientoEjecucion procedimientoEjecucion) {
		this.procedimientoEjecucion = procedimientoEjecucion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + doeId;
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
		DocumentosEjecucion other = (DocumentosEjecucion) obj;
		if (doeId != other.doeId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DocumentosEjecucion [doeId=" + doeId + ", doeMarca=" + doeMarca + ", doeNombre=" + doeNombre
				+ ", doeRef=" + doeRef + ", doeRuta=" + doeRuta + ", fecCrea=" + fecCrea + ", fecModi=" + fecModi
				+ ", regActivo=" + regActivo + ", usuCrea=" + usuCrea + ", usuModi=" + usuModi
				+ ", procedimientoEjecucion=" + procedimientoEjecucion + "]";
	}

}