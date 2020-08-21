package audigoes.ues.edu.sv.entities.seguimiento;

import java.io.Serializable;
import javax.persistence.*;

import audigoes.ues.edu.sv.entities.SuperEntity;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the comentario database table.
 * 
 */
@Entity
@NamedQuery(name="Comentario.findAll", query="SELECT c FROM Comentario c")
public class Comentario extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="com_id")
	private int comId;

	@Column(name="com_comentario")
	private String comComentario;

	@Column(name="com_comentario_auditoria")
	private String comComentarioAuditoria;

	@Column(name="com_estado")
	private int comEstado;

	@Temporal(TemporalType.DATE)
	@Column(name="com_fecha_envio_auditado")
	private Date comFechaEnvioAuditado;

	@Temporal(TemporalType.DATE)
	@Column(name="com_fecha_envio_auditoria")
	private Date comFechaEnvioAuditoria;

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

	//bi-directional many-to-one association to Recomendacion
	@ManyToOne
	@JoinColumn(name="com_rec_id")
	private Recomendacion recomendacion;

	//bi-directional many-to-one association to Evidencia
	@OneToMany(mappedBy="comentario")
	private List<Evidencia> evidencia;

	//bi-directional many-to-one association to ResponsableRecomendacion
	@OneToMany(mappedBy="comentario")
	private List<ResponsableRecomendacion> responsableRecomendacion;

	public Comentario() {
	}

	public int getComId() {
		return this.comId;
	}

	public void setComId(int comId) {
		this.comId = comId;
	}

	public String getComComentario() {
		return this.comComentario;
	}

	public void setComComentario(String comComentario) {
		this.comComentario = comComentario;
	}

	public String getComComentarioAuditoria() {
		return this.comComentarioAuditoria;
	}

	public void setComComentarioAuditoria(String comComentarioAuditoria) {
		this.comComentarioAuditoria = comComentarioAuditoria;
	}

	public int getComEstado() {
		return this.comEstado;
	}

	public void setComEstado(int comEstado) {
		this.comEstado = comEstado;
	}

	public Date getComFechaEnvioAuditado() {
		return this.comFechaEnvioAuditado;
	}

	public void setComFechaEnvioAuditado(Date comFechaEnvioAuditado) {
		this.comFechaEnvioAuditado = comFechaEnvioAuditado;
	}

	public Date getComFechaEnvioAuditoria() {
		return this.comFechaEnvioAuditoria;
	}

	public void setComFechaEnvioAuditoria(Date comFechaEnvioAuditoria) {
		this.comFechaEnvioAuditoria = comFechaEnvioAuditoria;
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

	public Recomendacion getRecomendacion() {
		return this.recomendacion;
	}

	public void setRecomendacion(Recomendacion recomendacion) {
		this.recomendacion = recomendacion;
	}

	public List<Evidencia> getEvidencia() {
		return this.evidencia;
	}

	public void setEvidencia(List<Evidencia> evidencia) {
		this.evidencia = evidencia;
	}

	public Evidencia addEvidencia(Evidencia evidencia) {
		getEvidencia().add(evidencia);
		evidencia.setComentario(this);

		return evidencia;
	}

	public Evidencia removeEvidencia(Evidencia evidencia) {
		getEvidencia().remove(evidencia);
		evidencia.setComentario(null);

		return evidencia;
	}

	public List<ResponsableRecomendacion> getResponsableRecomendacion() {
		return this.responsableRecomendacion;
	}

	public void setResponsableRecomendacion(List<ResponsableRecomendacion> responsableRecomendacion) {
		this.responsableRecomendacion = responsableRecomendacion;
	}

	public ResponsableRecomendacion addResponsableRecomendacion(ResponsableRecomendacion responsableRecomendacion) {
		getResponsableRecomendacion().add(responsableRecomendacion);
		responsableRecomendacion.setComentario(this);

		return responsableRecomendacion;
	}

	public ResponsableRecomendacion removeResponsableRecomendacion(ResponsableRecomendacion responsableRecomendacion) {
		getResponsableRecomendacion().remove(responsableRecomendacion);
		responsableRecomendacion.setComentario(null);

		return responsableRecomendacion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + comId;
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
		Comentario other = (Comentario) obj;
		if (comId != other.comId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Comentario [comId=" + comId + ", comComentario=" + comComentario + ", comComentarioAuditoria="
				+ comComentarioAuditoria + ", comEstado=" + comEstado + ", comFechaEnvioAuditado="
				+ comFechaEnvioAuditado + ", comFechaEnvioAuditoria=" + comFechaEnvioAuditoria + ", fecCrea=" + fecCrea
				+ ", fecModi=" + fecModi + ", regActivo=" + regActivo + ", usuCrea=" + usuCrea + ", usuModi=" + usuModi
				+ ", recomendacion=" + recomendacion + ", evidencia=" + evidencia + ", responsableRecomendacion="
				+ responsableRecomendacion + "]";
	}

}