package audigoes.ues.edu.sv.entities.seguimiento;

import java.io.Serializable;
import javax.persistence.*;

import audigoes.ues.edu.sv.entities.SuperEntity;

import java.util.Date;


/**
 * The persistent class for the evidencia database table.
 * 
 */
@Entity
@NamedQuery(name="Evidencia.findAll", query="SELECT e FROM Evidencia e")
public class Evidencia extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "evd_id", schema = "audigoes", table = "contador", pkColumnName = "cnt_nombre", valueColumnName = "cnt_valor", pkColumnValue = "evd_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "evd_id")
	@Column(name="evd_id")
	private int evdId;

	@Column(name="evd_extension")
	private String evdExtension;

	@Column(name="evd_nombre")
	private String evdNombre;

	@Column(name="evd_referencia")
	private String evdReferencia;

	@Column(name="evd_ruta")
	private String evdRuta;

	@Temporal(TemporalType.DATE)
	@Column(name="fec_crea")
	private Date fecCrea;

	@Temporal(TemporalType.DATE)
	@Column(name="fec_modi")
	private Date fecModi;

	@Column(name="reg_activo")
	private int regActivo;

	@Column(name="usu_crea")
	private String usuCrea;

	@Column(name="usu_modi")
	private String usuModi;

	//bi-directional many-to-one association to Comentario
	@ManyToOne
	@JoinColumn(name="evd_com_id")
	private Comentario comentario;

	public Evidencia() {
	}

	public int getEvdId() {
		return this.evdId;
	}

	public void setEvdId(int evdId) {
		this.evdId = evdId;
	}

	public String getEvdExtension() {
		return this.evdExtension;
	}

	public void setEvdExtension(String evdExtension) {
		this.evdExtension = evdExtension;
	}

	public String getEvdNombre() {
		return this.evdNombre;
	}

	public void setEvdNombre(String evdNombre) {
		this.evdNombre = evdNombre;
	}

	public String getEvdReferencia() {
		return this.evdReferencia;
	}

	public void setEvdReferencia(String evdReferencia) {
		this.evdReferencia = evdReferencia;
	}

	public String getEvdRuta() {
		return this.evdRuta;
	}

	public void setEvdRuta(String evdRuta) {
		this.evdRuta = evdRuta;
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

	public Comentario getComentario() {
		return this.comentario;
	}

	public void setComentario(Comentario comentario) {
		this.comentario = comentario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + evdId;
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
		Evidencia other = (Evidencia) obj;
		if (evdId != other.evdId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Evidencia [evdId=" + evdId + ", evdExtension=" + evdExtension + ", evdNombre=" + evdNombre
				+ ", evdReferencia=" + evdReferencia + ", evdRuta=" + evdRuta + ", fecCrea=" + fecCrea + ", fecModi="
				+ fecModi + ", regActivo=" + regActivo + ", usuCrea=" + usuCrea + ", usuModi=" + usuModi
				+ ", comentario=" + comentario + "]";
	}

}