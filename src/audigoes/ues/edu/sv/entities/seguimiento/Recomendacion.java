package audigoes.ues.edu.sv.entities.seguimiento;

import java.io.Serializable;
import javax.persistence.*;

import audigoes.ues.edu.sv.entities.SuperEntity;
import audigoes.ues.edu.sv.entities.informe.CedulaNota;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;

import java.util.Date;
import java.util.List;

/**
 * The persistent class for the recomendacion database table.
 * 
 */
@Entity
@Table(name="recomendacion")
@NamedQuery(name = "Recomendacion.findAll", query = "SELECT r FROM Recomendacion r")
public class Recomendacion extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "rec_id", schema = "audigoes", table = "contador", pkColumnName = "cnt_nombre", valueColumnName = "cnt_valor", pkColumnValue = "rec_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "rec_id")
	@Column(name = "rec_id")
	private int recId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fec_crea")
	private Date fecCrea;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fec_modi")
	private Date fecModi;

	@Column(name = "rec_estado")
	private int recEstado;

	@Column(name = "rec_recomendacion")
	private String recRecomendacion;

	@Column(name = "rec_situacion")
	private String recSituacion;

	@Column(name = "rec_titulo")
	private String recTitulo;

	@Column(name = "reg_activo")
	private int regActivo;

	@Column(name = "usu_crea")
	private String usuCrea;

	@Column(name = "usu_modi")
	private String usuModi;

	// bi-directional many-to-one association to Comentario
	@OneToMany(mappedBy = "recomendacion")
	private List<Comentario> comentario;

	// bi-directional many-to-one association to CedulaNota
	@ManyToOne
	@JoinColumn(name = "rec_ced_id")
	private CedulaNota cedulaNota;

	// bi-directional many-to-one association to Seguimiento
	@ManyToOne
	@JoinColumn(name = "rec_seg_id")
	private Seguimiento seguimiento;

	// bi-directional many-to-one association to Auditoria
	@ManyToOne
	@JoinColumn(name = "rec_aud_id")
	private Auditoria auditoria;

	public Recomendacion() {
	}

	public int getRecId() {
		return this.recId;
	}

	public void setRecId(int recId) {
		this.recId = recId;
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

	public int getRecEstado() {
		return this.recEstado;
	}

	public void setRecEstado(int recEstado) {
		this.recEstado = recEstado;
	}

	public String getRecRecomendacion() {
		return this.recRecomendacion;
	}

	public void setRecRecomendacion(String recRecomendacion) {
		this.recRecomendacion = recRecomendacion;
	}

	public String getRecSituacion() {
		return this.recSituacion;
	}

	public void setRecSituacion(String recSituacion) {
		this.recSituacion = recSituacion;
	}

	public String getRecTitulo() {
		return this.recTitulo;
	}

	public void setRecTitulo(String recTitulo) {
		this.recTitulo = recTitulo;
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

	public List<Comentario> getComentario() {
		return this.comentario;
	}

	public void setComentario(List<Comentario> comentario) {
		this.comentario = comentario;
	}

	public Comentario addComentario(Comentario comentario) {
		getComentario().add(comentario);
		comentario.setRecomendacion(this);

		return comentario;
	}

	public Comentario removeComentario(Comentario comentario) {
		getComentario().remove(comentario);
		comentario.setRecomendacion(null);

		return comentario;
	}

	public CedulaNota getCedulaNota() {
		return this.cedulaNota;
	}

	public void setCedulaNota(CedulaNota cedulaNota) {
		this.cedulaNota = cedulaNota;
	}

	public Seguimiento getSeguimiento() {
		return this.seguimiento;
	}

	public void setSeguimiento(Seguimiento seguimiento) {
		this.seguimiento = seguimiento;
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
		result = prime * result + recId;
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
		Recomendacion other = (Recomendacion) obj;
		if (recId != other.recId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Recomendacion [recId=" + recId + ", fecCrea=" + fecCrea + ", fecModi=" + fecModi + ", recEstado="
				+ recEstado + ", recRecomendacion=" + recRecomendacion + ", recSituacion=" + recSituacion
				+ ", recTitulo=" + recTitulo + ", regActivo=" + regActivo + ", usuCrea=" + usuCrea + ", usuModi="
				+ usuModi + ", comentario=" + comentario + ", cedulaNota=" + cedulaNota + ", seguimiento=" + seguimiento
				+ ", auditoria=" + auditoria + "]";
	}

}