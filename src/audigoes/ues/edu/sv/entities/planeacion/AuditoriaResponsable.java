package audigoes.ues.edu.sv.entities.planeacion;

import java.io.Serializable;
import javax.persistence.*;

import audigoes.ues.edu.sv.entities.SuperEntity;
import audigoes.ues.edu.sv.entities.administracion.Usuario;

import java.util.Date;


/**
 * The persistent class for the auditoria_responsable database table.
 * 
 */
@Entity
@Table(name="auditoria_responsable")
@NamedQuery(name="AuditoriaResponsable.findAll", query="SELECT a FROM AuditoriaResponsable a")
public class AuditoriaResponsable extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "aur_id", schema = "audigoes", table = "contador", pkColumnName = "cnt_nombre", valueColumnName = "cnt_valor", pkColumnValue = "aur_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "aur_id")
	@Column(name="aur_id")
	private int aurId;

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

	//bi-directional many-to-one association to Auditoria
	@ManyToOne
	@JoinColumn(name="aur_aud_id")
	private Auditoria auditoria;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="aur_usu_id")
	private Usuario usuario;

	public AuditoriaResponsable() {
	}

	public int getAurId() {
		return this.aurId;
	}

	public void setAurId(int aurId) {
		this.aurId = aurId;
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

	public Auditoria getAuditoria() {
		return this.auditoria;
	}

	public void setAuditoria(Auditoria auditoria) {
		this.auditoria = auditoria;
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
		result = prime * result + aurId;
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
		AuditoriaResponsable other = (AuditoriaResponsable) obj;
		if (aurId != other.aurId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AuditoriaResponsable [aurId=" + aurId + ", fecCrea=" + fecCrea + ", fecModi=" + fecModi + ", regActivo="
				+ regActivo + ", usuCrea=" + usuCrea + ", usuModi=" + usuModi + ", auditoria=" + auditoria
				+ ", usuario=" + usuario + "]";
	}

}