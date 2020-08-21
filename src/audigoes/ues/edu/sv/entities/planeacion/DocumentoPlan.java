package audigoes.ues.edu.sv.entities.planeacion;

import java.io.Serializable;
import javax.persistence.*;

import audigoes.ues.edu.sv.entities.SuperEntity;

import java.util.Date;


/**
 * The persistent class for the documento_plan database table.
 * 
 */
@Entity
@Table(name="documento_plan")
@NamedQuery(name="DocumentoPlan.findAll", query="SELECT d FROM DocumentoPlan d")
public class DocumentoPlan extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "dpl_id", schema = "audigoes", table = "contador", pkColumnName = "cnt_nombre", valueColumnName = "cnt_valor", pkColumnValue = "dpl_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "dpl_id")
	@Column(name="dpl_id")
	private int dplId;

	@Column(name="dpl_ext")
	private String dplExt;

	@Column(name="dpl_nombre")
	private String dplNombre;

	@Column(name="dpl_ruta")
	private String dplRuta;

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

	//bi-directional many-to-one association to PlanAnual
	@ManyToOne
	@JoinColumn(name="dpl_pla_id")
	private PlanAnual planAnual;

	public DocumentoPlan() {
	}

	public int getDplId() {
		return this.dplId;
	}

	public void setDplId(int dplId) {
		this.dplId = dplId;
	}

	public String getDplExt() {
		return this.dplExt;
	}

	public void setDplExt(String dplExt) {
		this.dplExt = dplExt;
	}

	public String getDplNombre() {
		return this.dplNombre;
	}

	public void setDplNombre(String dplNombre) {
		this.dplNombre = dplNombre;
	}

	public String getDplRuta() {
		return this.dplRuta;
	}

	public void setDplRuta(String dplRuta) {
		this.dplRuta = dplRuta;
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

	public PlanAnual getPlanAnual() {
		return this.planAnual;
	}

	public void setPlanAnual(PlanAnual planAnual) {
		this.planAnual = planAnual;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + dplId;
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
		DocumentoPlan other = (DocumentoPlan) obj;
		if (dplId != other.dplId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DocumentoPlan [dplId=" + dplId + ", dplExt=" + dplExt + ", dplNombre=" + dplNombre + ", dplRuta="
				+ dplRuta + ", fecCrea=" + fecCrea + ", fecModi=" + fecModi + ", regActivo=" + regActivo + ", usuCrea="
				+ usuCrea + ", usuModi=" + usuModi + ", planAnual=" + planAnual + "]";
	}

}