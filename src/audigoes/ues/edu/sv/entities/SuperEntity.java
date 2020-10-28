package audigoes.ues.edu.sv.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class SuperEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Transient
	private boolean selected;
	
	@Transient
	private String status = "NEW";
	
	@Column(name="FEC_CREA")
	private Date fecCrea;
	
	@Column(name="FEC_MODI")
	private Date fecModi;
	
	@Column(name="USU_CREA")
	private String usuCrea;
	
	@Column(name="USU_MODI")
	private String usuModi;
	
	@Column(name="REG_ACTIVO")
	private int regActivo;

	public Date getFecCrea() {
		return fecCrea;
	}

	public void setFecCrea(Date fecCrea) {
		this.fecCrea = fecCrea;
	}

	public Date getFecModi() {
		return fecModi;
	}

	public void setFecModi(Date fecModi) {
		this.fecModi = fecModi;
	}

	public String getUsuCrea() {
		return usuCrea;
	}

	public void setUsuCrea(String usuCrea) {
		this.usuCrea = usuCrea;
	}

	public String getUsuModi() {
		return usuModi;
	}

	public void setUsuModi(String usuModi) {
		this.usuModi = usuModi;
	}

	public int getRegActivo() {
		return regActivo;
	}

	public void setRegActivo(int regActivo) {
		this.regActivo = regActivo;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
