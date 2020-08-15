package audigoes.ues.edu.sv.entities.administracion;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the bitacora database table.
 * 
 */
@Entity
@NamedQuery(name="Bitacora.findAll", query="SELECT b FROM Bitacora b")
public class Bitacora extends audigoes.ues.edu.sv.entities.SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="bit_id")
	private int bitId;

	@Column(name="bit_accion")
	private int bitAccion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="bit_fecha")
	private Date bitFecha;

	@Column(name="bit_ip")
	private String bitIp;

	@Column(name="bit_nombre_tabla")
	private String bitNombreTabla;

	@Column(name="bit_usuario")
	private String bitUsuario;

	@Lob
	@Column(name="bit_valor_actual")
	private String bitValorActual;

	@Lob
	@Column(name="bit_valor_anterior")
	private String bitValorAnterior;

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

	public Bitacora() {
	}

	public int getBitId() {
		return this.bitId;
	}

	public void setBitId(int bitId) {
		this.bitId = bitId;
	}

	public int getBitAccion() {
		return this.bitAccion;
	}

	public void setBitAccion(int bitAccion) {
		this.bitAccion = bitAccion;
	}

	public Date getBitFecha() {
		return this.bitFecha;
	}

	public void setBitFecha(Date bitFecha) {
		this.bitFecha = bitFecha;
	}

	public String getBitIp() {
		return this.bitIp;
	}

	public void setBitIp(String bitIp) {
		this.bitIp = bitIp;
	}

	public String getBitNombreTabla() {
		return this.bitNombreTabla;
	}

	public void setBitNombreTabla(String bitNombreTabla) {
		this.bitNombreTabla = bitNombreTabla;
	}

	public String getBitUsuario() {
		return this.bitUsuario;
	}

	public void setBitUsuario(String bitUsuario) {
		this.bitUsuario = bitUsuario;
	}

	public String getBitValorActual() {
		return this.bitValorActual;
	}

	public void setBitValorActual(String bitValorActual) {
		this.bitValorActual = bitValorActual;
	}

	public String getBitValorAnterior() {
		return this.bitValorAnterior;
	}

	public void setBitValorAnterior(String bitValorAnterior) {
		this.bitValorAnterior = bitValorAnterior;
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

}