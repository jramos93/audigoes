package audigoes.ues.edu.sv.entities.administracion;

import java.io.Serializable;
import javax.persistence.*;

import audigoes.ues.edu.sv.entities.SuperEntity;

import java.util.Date;

/**
 * The persistent class for the configuracion database table.
 * 
 */
@Entity
@Table(name="configuracion")
@NamedQuery(name = "Configuracion.findAll", query = "SELECT c FROM Configuracion c")
public class Configuracion extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "con_id", schema = "audigoes", table = "contador", pkColumnName = "cnt_nombre", valueColumnName = "cnt_valor", pkColumnValue = "con_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "con_id")
	@Column(name = "con_id")
	private int conId;

	@Column(name = "con_descripcion")
	private String conDescripcion;

	@Column(name = "con_valor")
	private String conValor;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fec_crea")
	private Date fecCrea;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fec_modi")
	private Date fecModi;

	@Column(name = "reg_activo")
	private int regActivo;

	@Column(name = "usu_crea")
	private String usuCrea;

	@Column(name = "usu_modi")
	private String usuModi;

	// bi-directional many-to-one association to Institucion
	@ManyToOne
	@JoinColumn(name = "con_ins_id")
	private Institucion institucion;

	// bi-directional many-to-one association to VariablesSistema
	@ManyToOne
	@JoinColumn(name = "con_var_id")
	private VariablesSistema variablesSistema;

	public Configuracion() {
	}

	public int getConId() {
		return this.conId;
	}

	public void setConId(int conId) {
		this.conId = conId;
	}

	public String getConDescripcion() {
		return this.conDescripcion;
	}

	public void setConDescripcion(String conDescripcion) {
		this.conDescripcion = conDescripcion;
	}

	public String getConValor() {
		return this.conValor;
	}

	public void setConValor(String conValor) {
		this.conValor = conValor;
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

	public Institucion getInstitucion() {
		return this.institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public VariablesSistema getVariablesSistema() {
		return variablesSistema;
	}

	public void setVariablesSistema(VariablesSistema variablesSistema) {
		this.variablesSistema = variablesSistema;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + conId;
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
		Configuracion other = (Configuracion) obj;
		if (conId != other.conId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Configuracion [conId=" + conId + ", conDescripcion=" + conDescripcion + ", conValor=" + conValor
				+ ", fecCrea=" + fecCrea + ", fecModi=" + fecModi + ", regActivo=" + regActivo + ", usuCrea=" + usuCrea
				+ ", usuModi=" + usuModi + ", institucion=" + institucion + ", variablesSistema=" + variablesSistema
				+ "]";
	}
}