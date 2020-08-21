package audigoes.ues.edu.sv.entities.administracion;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the origen_auditoria database table.
 * 
 */
@Entity
@Table(name="origen_auditoria")
@NamedQuery(name="OrigenAuditoria.findAll", query="SELECT o FROM OrigenAuditoria o")
public class OrigenAuditoria extends audigoes.ues.edu.sv.entities.SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "ori_id", schema = "audigoes", table = "contador", pkColumnName = "cnt_nombre", valueColumnName = "cnt_valor", pkColumnValue = "ori_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ori_id")
	@Column(name="ori_id")
	private int oriId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_crea")
	private Date fecCrea;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_modi")
	private Date fecModi;

	@Column(name="ori_descripcion")
	private String oriDescripcion;

	@Column(name="ori_nombre")
	private String oriNombre;

	@Column(name="reg_activo")
	private int regActivo;

	@Column(name="usu_crea")
	private String usuCrea;

	@Column(name="usu_modi")
	private String usuModi;

	public OrigenAuditoria() {
	}

	public int getOriId() {
		return this.oriId;
	}

	public void setOriId(int oriId) {
		this.oriId = oriId;
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

	public String getOriDescripcion() {
		return this.oriDescripcion;
	}

	public void setOriDescripcion(String oriDescripcion) {
		this.oriDescripcion = oriDescripcion;
	}

	public String getOriNombre() {
		return this.oriNombre;
	}

	public void setOriNombre(String oriNombre) {
		this.oriNombre = oriNombre;
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