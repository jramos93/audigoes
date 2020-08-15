package audigoes.ues.edu.sv.entities.administracion;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the contador database table.
 * 
 */
@Embeddable
public class ContadorPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="cnt_id")
	private int cntId;

	@Column(name="cnt_nombre")
	private String cntNombre;

	@Column(name="cnt_valor")
	private int cntValor;

	public ContadorPK() {
	}
	public int getCntId() {
		return this.cntId;
	}
	public void setCntId(int cntId) {
		this.cntId = cntId;
	}
	public String getCntNombre() {
		return this.cntNombre;
	}
	public void setCntNombre(String cntNombre) {
		this.cntNombre = cntNombre;
	}
	public int getCntValor() {
		return this.cntValor;
	}
	public void setCntValor(int cntValor) {
		this.cntValor = cntValor;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ContadorPK)) {
			return false;
		}
		ContadorPK castOther = (ContadorPK)other;
		return 
			(this.cntId == castOther.cntId)
			&& this.cntNombre.equals(castOther.cntNombre)
			&& (this.cntValor == castOther.cntValor);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.cntId;
		hash = hash * prime + this.cntNombre.hashCode();
		hash = hash * prime + this.cntValor;
		
		return hash;
	}
}