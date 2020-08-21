package audigoes.ues.edu.sv.entities.planificacion;

import java.io.Serializable;
import javax.persistence.*;

import audigoes.ues.edu.sv.entities.SuperEntity;
import audigoes.ues.edu.sv.entities.administracion.Usuario;

import java.util.Date;


/**
 * The persistent class for the usuario_actividad database table.
 * 
 */
@Entity
@Table(name="usuario_actividad")
@NamedQuery(name="UsuarioActividad.findAll", query="SELECT u FROM UsuarioActividad u")
public class UsuarioActividad extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="uac_id")
	private int uacId;

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

	//bi-directional many-to-one association to Actividad
	@ManyToOne
	@JoinColumn(name="uac_act_id")
	private Actividad actividad;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="uac_usu_id")
	private Usuario usuario;

	public UsuarioActividad() {
	}

	public int getUacId() {
		return this.uacId;
	}

	public void setUacId(int uacId) {
		this.uacId = uacId;
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

	public Actividad getActividad() {
		return this.actividad;
	}

	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + uacId;
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
		UsuarioActividad other = (UsuarioActividad) obj;
		if (uacId != other.uacId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UsuarioActividad [uacId=" + uacId + ", fecCrea=" + fecCrea + ", fecModi=" + fecModi + ", regActivo="
				+ regActivo + ", usuCrea=" + usuCrea + ", usuModi=" + usuModi + ", actividad=" + actividad
				+ ", usuario=" + usuario + "]";
	}

}