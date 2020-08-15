package audigoes.ues.edu.sv.entities.administracion;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the contador database table.
 * 
 */
@Entity
@NamedQuery(name="Contador.findAll", query="SELECT c FROM Contador c")
public class Contador implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ContadorPK id;

	@Column(name="cnt_anio")
	private int cntAnio;

	public Contador() {
	}

	public ContadorPK getId() {
		return this.id;
	}

	public void setId(ContadorPK id) {
		this.id = id;
	}

	public int getCntAnio() {
		return this.cntAnio;
	}

	public void setCntAnio(int cntAnio) {
		this.cntAnio = cntAnio;
	}

}