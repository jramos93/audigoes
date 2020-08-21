package audigoes.ues.edu.sv.entities.administracion;

import java.io.Serializable;
import javax.persistence.*;

import audigoes.ues.edu.sv.entities.SuperEntity;

import java.util.Date;


/**
 * The persistent class for the propiedades_sistema database table.
 * 
 */
@Entity
@Table(name="propiedades_sistema")
@NamedQuery(name="PropiedadesSistema.findAll", query="SELECT p FROM PropiedadesSistema p")
public class PropiedadesSistema extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="prs_id")
	private int prsId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_crea")
	private Date fecCrea;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_modi")
	private Date fecModi;

	@Column(name="prs_propiedad")
	private String prsPropiedad;

	@Column(name="prs_valor")
	private String prsValor;

	@Column(name="reg_activo")
	private int regActivo;

	@Column(name="usu_crea")
	private String usuCrea;

	@Column(name="usu_modi")
	private String usuModi;

	public PropiedadesSistema() {
	}

	public int getPrsId() {
		return this.prsId;
	}

	public void setPrsId(int prsId) {
		this.prsId = prsId;
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

	public String getPrsPropiedad() {
		return this.prsPropiedad;
	}

	public void setPrsPropiedad(String prsPropiedad) {
		this.prsPropiedad = prsPropiedad;
	}

	public String getPrsValor() {
		return this.prsValor;
	}

	public void setPrsValor(String prsValor) {
		this.prsValor = prsValor;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + prsId;
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
		PropiedadesSistema other = (PropiedadesSistema) obj;
		if (prsId != other.prsId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PropiedadesSistema [prsId=" + prsId + ", fecCrea=" + fecCrea + ", fecModi=" + fecModi
				+ ", prsPropiedad=" + prsPropiedad + ", prsValor=" + prsValor + ", regActivo=" + regActivo
				+ ", usuCrea=" + usuCrea + ", usuModi=" + usuModi + "]";
	}

}