package audigoes.ues.edu.sv.entities.seguimiento;

import java.io.Serializable;
import javax.persistence.*;

import audigoes.ues.edu.sv.entities.SuperEntity;
import audigoes.ues.edu.sv.entities.administracion.Usuario;

import java.util.Date;


/**
 * The persistent class for the responsable_recomendacion database table.
 * 
 */
@Entity
@Table(name="responsable_recomendacion")
@NamedQuery(name="ResponsableRecomendacion.findAll", query="SELECT r FROM ResponsableRecomendacion r")
public class ResponsableRecomendacion extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="rrc_id")
	private int rrcId;

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

	//bi-directional many-to-one association to Comentario
	@ManyToOne
	@JoinColumn(name="rrc_com_id")
	private Comentario comentario;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="rrc_usu_id")
	private Usuario usuario;

	public ResponsableRecomendacion() {
	}

	public int getRrcId() {
		return this.rrcId;
	}

	public void setRrcId(int rrcId) {
		this.rrcId = rrcId;
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

	public Comentario getComentario() {
		return this.comentario;
	}

	public void setComentario(Comentario comentario) {
		this.comentario = comentario;
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
		result = prime * result + rrcId;
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
		ResponsableRecomendacion other = (ResponsableRecomendacion) obj;
		if (rrcId != other.rrcId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ResponsableRecomendacion [rrcId=" + rrcId + ", fecCrea=" + fecCrea + ", fecModi=" + fecModi
				+ ", regActivo=" + regActivo + ", usuCrea=" + usuCrea + ", usuModi=" + usuModi + ", comentario="
				+ comentario + ", usuario=" + usuario + "]";
	}

}