package audigoes.ues.edu.sv.entities.planificacion;

import java.io.Serializable;
import javax.persistence.*;

import audigoes.ues.edu.sv.entities.SuperEntity;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;

import java.util.Date;


/**
 * The persistent class for the memorando database table.
 * 
 */
@Entity
@NamedQuery(name="Memorando.findAll", query="SELECT m FROM Memorando m")
public class Memorando extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="mem_id")
	private int memId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_crea")
	private Date fecCrea;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_modi")
	private Date fecModi;

	@Lob
	@Column(name="mem_contenido")
	private String memContenido;

	@Lob
	@Column(name="mem_descripcion")
	private String memDescripcion;

	@Lob
	@Column(name="mem_indice")
	private String memIndice;

	@Lob
	@Column(name="mem_lugar_fecha")
	private String memLugarFecha;

	@Column(name="mem_nombre")
	private String memNombre;

	@Column(name="mem_tipo")
	private int memTipo;

	@Column(name="reg_activo")
	private int regActivo;

	@Column(name="usu_crea")
	private String usuCrea;

	@Column(name="usu_modi")
	private String usuModi;

	//bi-directional many-to-one association to Auditoria
	@ManyToOne
	@JoinColumn(name="mem_aud_id")
	private Auditoria auditoria;

	public Memorando() {
	}

	public int getMemId() {
		return this.memId;
	}

	public void setMemId(int memId) {
		this.memId = memId;
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

	public String getMemContenido() {
		return this.memContenido;
	}

	public void setMemContenido(String memContenido) {
		this.memContenido = memContenido;
	}

	public String getMemDescripcion() {
		return this.memDescripcion;
	}

	public void setMemDescripcion(String memDescripcion) {
		this.memDescripcion = memDescripcion;
	}

	public String getMemIndice() {
		return this.memIndice;
	}

	public void setMemIndice(String memIndice) {
		this.memIndice = memIndice;
	}

	public String getMemLugarFecha() {
		return this.memLugarFecha;
	}

	public void setMemLugarFecha(String memLugarFecha) {
		this.memLugarFecha = memLugarFecha;
	}

	public String getMemNombre() {
		return this.memNombre;
	}

	public void setMemNombre(String memNombre) {
		this.memNombre = memNombre;
	}

	public int getMemTipo() {
		return this.memTipo;
	}

	public void setMemTipo(int memTipo) {
		this.memTipo = memTipo;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + memId;
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
		Memorando other = (Memorando) obj;
		if (memId != other.memId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Memorando [memId=" + memId + ", fecCrea=" + fecCrea + ", fecModi=" + fecModi + ", memContenido="
				+ memContenido + ", memDescripcion=" + memDescripcion + ", memIndice=" + memIndice + ", memLugarFecha="
				+ memLugarFecha + ", memNombre=" + memNombre + ", memTipo=" + memTipo + ", regActivo=" + regActivo
				+ ", usuCrea=" + usuCrea + ", usuModi=" + usuModi + ", auditoria=" + auditoria + "]";
	}

}