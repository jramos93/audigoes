package audigoes.ues.edu.sv.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class SuperEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name="FEC_CREA")
	private Date fecCrea;
	
	@Column(name="FEC_MODI")
	private Date fecModi;
	
	@Column(name="USU_CREA")
	private String usuCrea;
	
	@Column(name="USU_MODI")
	private String usuModi;
	
	@Column(name="REG_ACTIVO")
	private BigDecimal regActivo;

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

	public BigDecimal getRegActivo() {
		return regActivo;
	}

	public void setRegActivo(BigDecimal regActivo) {
		this.regActivo = regActivo;
	}
	
}
