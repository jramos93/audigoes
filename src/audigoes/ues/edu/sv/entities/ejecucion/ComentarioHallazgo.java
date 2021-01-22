package audigoes.ues.edu.sv.entities.ejecucion;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import audigoes.ues.edu.sv.entities.SuperEntity;
import audigoes.ues.edu.sv.entities.administracion.Usuario;
import audigoes.ues.edu.sv.entities.informe.CedulaNota;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;
import audigoes.ues.edu.sv.entities.seguimiento.Evidencia;


/**
 * The persistent class for the comentario_hallazgo database table.
 * 
 */
@Entity
@Table(name="comentario_hallazgo")
@NamedQuery(name="ComentarioHallazgo.findAll", query="SELECT c FROM ComentarioHallazgo c")
public class ComentarioHallazgo extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "come_id", schema = "audigoes", table = "contador", pkColumnName = "cnt_nombre", valueColumnName = "cnt_valor", pkColumnValue = "come_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "come_id")
	@Column(name="come_id")
	private int comeId;

	@Lob
	@Column(name="come_comentario")
	private String comeComentario;

	@Column(name="come_enviado")
	private int comeEnviado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="come_fecha_envio")
	private Date comeFechaEnvio;

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
	@JoinColumn(name="come_aud_id")
	private Auditoria auditoria;

	//bi-directional many-to-one association to CedulaNota
	@ManyToOne
	@JoinColumn(name="come_ced_id")
	private CedulaNota cedulaNota;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="come_usu_id")
	private Usuario usuario;
	
	//bi-directional many-to-one association to Evidencia
	@OneToMany(mappedBy="comentarioHallazgo")
	private List<Evidencia> evidencia;

	public ComentarioHallazgo() {
	}

	public int getComeId() {
		return this.comeId;
	}

	public void setComeId(int comeId) {
		this.comeId = comeId;
	}

	public String getComeComentario() {
		return this.comeComentario;
	}

	public void setComeComentario(String comeComentario) {
		this.comeComentario = comeComentario;
	}

	public int getComeEnviado() {
		return this.comeEnviado;
	}

	public void setComeEnviado(int comeEnviado) {
		this.comeEnviado = comeEnviado;
	}

	public Date getComeFechaEnvio() {
		return this.comeFechaEnvio;
	}

	public void setComeFechaEnvio(Date comeFechaEnvio) {
		this.comeFechaEnvio = comeFechaEnvio;
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

	public CedulaNota getCedulaNota() {
		return this.cedulaNota;
	}

	public void setCedulaNota(CedulaNota cedulaNota) {
		this.cedulaNota = cedulaNota;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Evidencia> getEvidencia() {
		return evidencia;
	}

	public void setEvidencia(List<Evidencia> evidencia) {
		this.evidencia = evidencia;
	}

}