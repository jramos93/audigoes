package audigoes.ues.edu.sv.entities.informe;

import java.io.Serializable;
import javax.persistence.*;

import audigoes.ues.edu.sv.entities.SuperEntity;

import java.util.Date;


/**
 * The persistent class for the destinatario database table.
 * 
 */
@Entity
@NamedQuery(name="Destinatario.findAll", query="SELECT d FROM Destinatario d")
public class Destinatario extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "dst_id", schema = "audigoes", table = "contador", pkColumnName = "cnt_nombre", valueColumnName = "cnt_valor", pkColumnValue = "dst_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "dst_id")
	@Column(name="dst_id")
	private int dstId;

	@Column(name="dst_correo")
	private String dstCorreo;

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

	//bi-directional many-to-one association to ActaLectura
	@ManyToOne
	@JoinColumn(name="dst_acl_id")
	private ActaLectura actaLectura;
	
	//bi-directional many-to-one association to ActaLectura
	@ManyToOne
	@JoinColumn(name="dst_cvc_id")
	private Convocatoria convocatoria;
	
	//bi-directional many-to-one association to ActaLectura
	@ManyToOne
	@JoinColumn(name="dst_ctg_id")
	private CartaGerencia cartaGerencia;

	public Destinatario() {
	}

	public int getDstId() {
		return this.dstId;
	}

	public void setDstId(int dstId) {
		this.dstId = dstId;
	}

	public String getDstCorreo() {
		return this.dstCorreo;
	}

	public void setDstCorreo(String dstCorreo) {
		this.dstCorreo = dstCorreo;
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

	public ActaLectura getActaLectura() {
		return this.actaLectura;
	}

	public void setActaLectura(ActaLectura actaLectura) {
		this.actaLectura = actaLectura;
	}
	public Convocatoria getConvocatoria() {
		return this.convocatoria;
	}

	public void setConvocatoria(Convocatoria convocatoria) {
		this.convocatoria = convocatoria;
	}
	
	public CartaGerencia getCartaGerencia() {
		return this.cartaGerencia;
	}

	public void setCartaGerencia(CartaGerencia cartaGerencia) {
		this.cartaGerencia = cartaGerencia;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + dstId;
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
		Destinatario other = (Destinatario) obj;
		if (dstId != other.dstId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Destinatario [dstId=" + dstId + ", dstCorreo=" + dstCorreo + ", fecCrea=" + fecCrea + ", fecModi="
				+ fecModi + ", regActivo=" + regActivo + ", usuCrea=" + usuCrea + ", usuModi=" + usuModi
				+ ", convocatoria=" + convocatoria + ", cartaGerencia=" + cartaGerencia +", actaLectura=" + actaLectura + "]";
	}

}