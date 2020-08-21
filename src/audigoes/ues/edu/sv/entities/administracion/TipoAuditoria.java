package audigoes.ues.edu.sv.entities.administracion;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the tipo_auditoria database table.
 * 
 */
@Entity
@Table(name="tipo_auditoria")
@NamedQuery(name="TipoAuditoria.findAll", query="SELECT t FROM TipoAuditoria t")
public class TipoAuditoria extends audigoes.ues.edu.sv.entities.SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "tpa_id", schema = "audigoes", table = "contador", pkColumnName = "cnt_nombre", valueColumnName = "cnt_valor", pkColumnValue = "tpa_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tpa_id")
	@Column(name="tpa_id")
	private int tpaId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_crea")
	private Date fecCrea;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_modi")
	private Date fecModi;

	@Column(name="reg_activo")
	private int regActivo;

	@Column(name="tpa_descripcion")
	private String tpaDescripcion;

	@Column(name="tpa_nombre")
	private String tpaNombre;

	@Column(name="usu_crea")
	private String usuCrea;

	@Column(name="usu_modi")
	private String usuModi;

	public TipoAuditoria() {
	}

	public int getTpaId() {
		return this.tpaId;
	}

	public void setTpaId(int tpaId) {
		this.tpaId = tpaId;
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

	public String getTpaDescripcion() {
		return this.tpaDescripcion;
	}

	public void setTpaDescripcion(String tpaDescripcion) {
		this.tpaDescripcion = tpaDescripcion;
	}

	public String getTpaNombre() {
		return this.tpaNombre;
	}

	public void setTpaNombre(String tpaNombre) {
		this.tpaNombre = tpaNombre;
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