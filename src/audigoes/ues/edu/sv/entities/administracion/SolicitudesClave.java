package audigoes.ues.edu.sv.entities.administracion;

import java.io.Serializable;
import javax.persistence.*;

import audigoes.ues.edu.sv.entities.SuperEntity;

import java.util.Date;


/**
 * The persistent class for the solicitudes_clave database table.
 * 
 */
@Entity
@Table(name="solicitudes_clave")
@NamedQuery(name="SolicitudesClave.findAll", query="SELECT s FROM SolicitudesClave s")
public class SolicitudesClave extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "sc_id", schema = "audigoes", table = "contador", pkColumnName = "cnt_nombre", valueColumnName = "cnt_valor", pkColumnValue = "sc_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sc_id")
	@Column(name="sc_id")
	private int scId;

	@Temporal(TemporalType.DATE)
	@Column(name="fec_crea")
	private Date fecCrea;

	@Temporal(TemporalType.DATE)
	@Column(name="fec_modi")
	private Date fecModi;

	@Column(name="reg_activo")
	private int regActivo;

	@Column(name="sc_host")
	private String scHost;

	@Column(name="sc_ip")
	private String scIp;

	@Column(name="usu_crea")
	private String usuCrea;

	@Column(name="usu_modi")
	private String usuModi;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="sc_usu_id")
	private Usuario usuario;

	public SolicitudesClave() {
	}

	public int getScId() {
		return this.scId;
	}

	public void setScId(int scId) {
		this.scId = scId;
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

	public String getScHost() {
		return this.scHost;
	}

	public void setScHost(String scHost) {
		this.scHost = scHost;
	}

	public String getScIp() {
		return this.scIp;
	}

	public void setScIp(String scIp) {
		this.scIp = scIp;
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

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}