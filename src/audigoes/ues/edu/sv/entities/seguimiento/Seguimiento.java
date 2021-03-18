package audigoes.ues.edu.sv.entities.seguimiento;

import java.io.Serializable;
import javax.persistence.*;

import audigoes.ues.edu.sv.entities.SuperEntity;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the seguimiento database table.
 * 
 */
@Entity
@NamedQuery(name="Seguimiento.findAll", query="SELECT s FROM Seguimiento s")
public class Seguimiento extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "seg_id", schema = "audigoes", table = "contador", pkColumnName = "cnt_nombre", valueColumnName = "cnt_valor", pkColumnValue = "seg_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "seg_id")
	@Column(name="seg_id")
	private int segId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_crea")
	private Date fecCrea;

	@Column(name="seg_estado")
	private int segEstado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_modi")
	private Date fecModi;

	@Column(name="reg_activo")
	private int regActivo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="seg_fec_fin")
	private Date segFecFin;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="seg_fec_inicio")
	private Date segFecInicio;

	@Column(name="seg_observacion")
	private String segObservacion;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="seg_fecha_respuesta")
	private Date segFechaRespuesta;

	@Column(name="usu_crea")
	private String usuCrea;

	@Column(name="usu_modi")
	private String usuModi;

	//bi-directional many-to-one association to Recomendacion
	@OneToMany(mappedBy="seguimiento")
	private List<Recomendacion> recomendacion;
	
	// bi-directional many-to-one association to Comentario
	@OneToMany(mappedBy = "seguimiento")
	private List<Comentario> comentario;

	//bi-directional many-to-one association to Auditoria
	@ManyToOne
	@JoinColumn(name="seg_aud_id")
	private Auditoria auditoria;

	public Seguimiento() {
	}

	public int getSegId() {
		return this.segId;
	}

	public void setSegId(int segId) {
		this.segId = segId;
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

	public int getSegEstado() {
		return segEstado;
	}

	public void setSegEstado(int segEstado) {
		this.segEstado = segEstado;
	}

	public Date getSegFecFin() {
		return this.segFecFin;
	}

	public void setSegFecFin(Date segFecFin) {
		this.segFecFin = segFecFin;
	}

	public Date getSegFecInicio() {
		return this.segFecInicio;
	}

	public void setSegFecInicio(Date segFecInicio) {
		this.segFecInicio = segFecInicio;
	}

	public String getSegObservacion() {
		return this.segObservacion;
	}

	public void setSegObservacion(String segObservacion) {
		this.segObservacion = segObservacion;
	}

	public Date getSegFechaRespuesta() {
		return segFechaRespuesta;
	}

	public void setSegFechaRespuesta(Date segFechaRespuesta) {
		this.segFechaRespuesta = segFechaRespuesta;
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

	public List<Recomendacion> getRecomendacion() {
		return this.recomendacion;
	}

	public void setRecomendacion(List<Recomendacion> recomendacion) {
		this.recomendacion = recomendacion;
	}

	public Recomendacion addRecomendacion(Recomendacion recomendacion) {
		getRecomendacion().add(recomendacion);
		recomendacion.setSeguimiento(this);

		return recomendacion;
	}

	public Recomendacion removeRecomendacion(Recomendacion recomendacion) {
		getRecomendacion().remove(recomendacion);
		recomendacion.setSeguimiento(null);

		return recomendacion;
	}

	public List<Comentario> getComentario() {
		return comentario;
	}

	public void setComentario(List<Comentario> comentario) {
		this.comentario = comentario;
	}
	
	public Comentario addComentario(Comentario comentario) {
		getComentario().add(comentario);
		comentario.setSeguimiento(this);

		return comentario;
	}

	public Comentario removeComentario(Comentario comentario) {
		getComentario().remove(comentario);
		comentario.setSeguimiento(null);

		return comentario;
	}

	public Auditoria getAuditoria() {
		return this.auditoria;
	}

	public void setAuditoria(Auditoria auditoria) {
		this.auditoria = auditoria;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + segId;
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
		Seguimiento other = (Seguimiento) obj;
		if (segId != other.segId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Seguimiento [segId=" + segId + ", fecCrea=" + fecCrea + ", fecModi=" + fecModi + ", regActivo="
				+ regActivo + ", segFecFin=" + segFecFin + ", segFecInicio=" + segFecInicio + ", segObservacion="
				+ segObservacion + ", segFechaRespuesta=" + segFechaRespuesta + ", usuCrea=" + usuCrea + ", usuModi="
				+ usuModi + ", recomendacion=" + recomendacion + ", comentario=" + comentario + ", auditoria="
				+ auditoria + "]";
	}

}