package audigoes.ues.edu.sv.entities.planificacion;

import java.io.Serializable;
import javax.persistence.*;

import audigoes.ues.edu.sv.entities.SuperEntity;

import java.util.Date;


/**
 * The persistent class for the documentos_planificacion database table.
 * 
 */
@Entity
@Table(name="documentos_planificacion")
@NamedQuery(name="DocumentosPlanificacion.findAll", query="SELECT d FROM DocumentosPlanificacion d")
public class DocumentosPlanificacion extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="dop_id")
	private int dopId;

	@Column(name="dop_ext")
	private String dopExt;

	@Column(name="dop_nombre")
	private String dopNombre;

	@Column(name="dop_ref")
	private String dopRef;

	@Column(name="dop_ruta")
	private String dopRuta;

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

	//bi-directional many-to-one association to ProcedimientoPlanificacion
	@ManyToOne
	@JoinColumn(name="dop_pro_id")
	private ProcedimientoPlanificacion procedimientoPlanificacion;

	public DocumentosPlanificacion() {
	}

	public int getDopId() {
		return this.dopId;
	}

	public void setDopId(int dopId) {
		this.dopId = dopId;
	}

	public String getDopExt() {
		return this.dopExt;
	}

	public void setDopExt(String dopExt) {
		this.dopExt = dopExt;
	}

	public String getDopNombre() {
		return this.dopNombre;
	}

	public void setDopNombre(String dopNombre) {
		this.dopNombre = dopNombre;
	}

	public String getDopRef() {
		return this.dopRef;
	}

	public void setDopRef(String dopRef) {
		this.dopRef = dopRef;
	}

	public String getDopRuta() {
		return this.dopRuta;
	}

	public void setDopRuta(String dopRuta) {
		this.dopRuta = dopRuta;
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

	public ProcedimientoPlanificacion getProcedimientoPlanificacion() {
		return this.procedimientoPlanificacion;
	}

	public void setProcedimientoPlanificacion(ProcedimientoPlanificacion procedimientoPlanificacion) {
		this.procedimientoPlanificacion = procedimientoPlanificacion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + dopId;
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
		DocumentosPlanificacion other = (DocumentosPlanificacion) obj;
		if (dopId != other.dopId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DocumentosPlanificacion [dopId=" + dopId + ", dopExt=" + dopExt + ", dopNombre=" + dopNombre
				+ ", dopRef=" + dopRef + ", dopRuta=" + dopRuta + ", fecCrea=" + fecCrea + ", fecModi=" + fecModi
				+ ", regActivo=" + regActivo + ", usuCrea=" + usuCrea + ", usuModi=" + usuModi
				+ ", procedimientoPlanificacion=" + procedimientoPlanificacion + "]";
	}

}