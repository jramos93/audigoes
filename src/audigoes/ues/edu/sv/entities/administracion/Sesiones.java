package audigoes.ues.edu.sv.entities.administracion;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the usuario_permiso database table.
 * 
 */
@Entity
@Table(name="sesiones")
@NamedQuery(name="Sesiones.findAll", query="SELECT u FROM Sesiones u")
public class Sesiones extends audigoes.ues.edu.sv.entities.SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "ses_id", schema = "audigoes", table = "contador", pkColumnName = "cnt_nombre", valueColumnName = "cnt_valor", pkColumnValue = "ses_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ses_id")
	@Column(name="ses_id")
	private int sesId;

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
	
	@Column(name="ses_hostname")
	private String sesHostname;
	
	@Column(name="ses_ip")
	private String sesIp;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ses_inicio")
	private Date sesInicio;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ses_final")
	private Date sesFinal;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="ses_usu_id")
	private Usuario usuario;

	public Sesiones() {
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

	public int getSesId() {
		return sesId;
	}

	public void setSesId(int sesId) {
		this.sesId = sesId;
	}

	public String getSesHostname() {
		return sesHostname;
	}

	public void setSesHostname(String sesHostname) {
		this.sesHostname = sesHostname;
	}

	public String getSesIp() {
		return sesIp;
	}

	public void setSesIp(String sesIp) {
		this.sesIp = sesIp;
	}

	public Date getSesInicio() {
		return sesInicio;
	}

	public void setSesInicio(Date sesInicio) {
		this.sesInicio = sesInicio;
	}

	public Date getSesFinal() {
		return sesFinal;
	}

	public void setSesFinal(Date sesFinal) {
		this.sesFinal = sesFinal;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}