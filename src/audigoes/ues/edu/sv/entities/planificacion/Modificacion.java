package audigoes.ues.edu.sv.entities.planificacion;

import java.io.Serializable;
import javax.persistence.*;

import audigoes.ues.edu.sv.entities.SuperEntity;

import java.util.Date;


/**
 * The persistent class for the modificacion database table.
 * 
 */
@Entity
@NamedQuery(name="Modificacion.findAll", query="SELECT m FROM Modificacion m")
public class Modificacion extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "mod_id", schema = "audigoes", table = "contador", pkColumnName = "cnt_nombre", valueColumnName = "cnt_valor", pkColumnValue = "mod_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "mod_id")
	@Column(name="mod_id")
	private int modId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_crea")
	private Date fecCrea;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_modi")
	private Date fecModi;

	@Column(name="mod_estado")
	private int modEstado;

	@Temporal(TemporalType.DATE)
	@Column(name="mod_fec_fin_anterior")
	private Date modFecFinAnterior;

	@Temporal(TemporalType.DATE)
	@Column(name="mod_fec_fin_nuevo")
	private Date modFecFinNuevo;

	@Temporal(TemporalType.DATE)
	@Column(name="mod_fec_ini_anterior")
	private Date modFecIniAnterior;

	@Temporal(TemporalType.DATE)
	@Column(name="mod_fec_ini_nuevo")
	private Date modFecIniNuevo;

	@Column(name="reg_activo")
	private int regActivo;

	@Column(name="usu_crea")
	private String usuCrea;

	@Column(name="usu_modi")
	private String usuModi;

	//bi-directional many-to-one association to Actividad
	@ManyToOne
	@JoinColumn(name="mod_act_id")
	private Actividad actividad;

	public Modificacion() {
	}

	public int getModId() {
		return this.modId;
	}

	public void setModId(int modId) {
		this.modId = modId;
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

	public int getModEstado() {
		return this.modEstado;
	}

	public void setModEstado(int modEstado) {
		this.modEstado = modEstado;
	}

	public Date getModFecFinAnterior() {
		return this.modFecFinAnterior;
	}

	public void setModFecFinAnterior(Date modFecFinAnterior) {
		this.modFecFinAnterior = modFecFinAnterior;
	}

	public Date getModFecFinNuevo() {
		return this.modFecFinNuevo;
	}

	public void setModFecFinNuevo(Date modFecFinNuevo) {
		this.modFecFinNuevo = modFecFinNuevo;
	}

	public Date getModFecIniAnterior() {
		return this.modFecIniAnterior;
	}

	public void setModFecIniAnterior(Date modFecIniAnterior) {
		this.modFecIniAnterior = modFecIniAnterior;
	}

	public Date getModFecIniNuevo() {
		return this.modFecIniNuevo;
	}

	public void setModFecIniNuevo(Date modFecIniNuevo) {
		this.modFecIniNuevo = modFecIniNuevo;
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

	public Actividad getActividad() {
		return this.actividad;
	}

	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + modId;
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
		Modificacion other = (Modificacion) obj;
		if (modId != other.modId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Modificacion [modId=" + modId + ", fecCrea=" + fecCrea + ", fecModi=" + fecModi + ", modEstado="
				+ modEstado + ", modFecFinAnterior=" + modFecFinAnterior + ", modFecFinNuevo=" + modFecFinNuevo
				+ ", modFecIniAnterior=" + modFecIniAnterior + ", modFecIniNuevo=" + modFecIniNuevo + ", regActivo="
				+ regActivo + ", usuCrea=" + usuCrea + ", usuModi=" + usuModi + ", actividad=" + actividad + "]";
	}

}