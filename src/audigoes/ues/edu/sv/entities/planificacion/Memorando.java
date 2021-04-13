package audigoes.ues.edu.sv.entities.planificacion;

import java.io.Serializable;
import javax.persistence.*;

import audigoes.ues.edu.sv.entities.SuperEntity;
import audigoes.ues.edu.sv.entities.administracion.Usuario;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;

import java.util.Date;


/**
 * The persistent class for the memorando database table.
 * 
 */
@Entity
@Table(name="memorando")
@NamedQuery(name="Memorando.findAll", query="SELECT m FROM Memorando m")
public class Memorando extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "mem_id", schema = "audigoes", table = "contador", pkColumnName = "cnt_nombre", valueColumnName = "cnt_valor", pkColumnValue = "mem_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "mem_id")
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
	@Column(name="mem_observaciones")
	private String memObservaciones;

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
	
	@Column(name="mem_estado")
	private int memEstado;

	//bi-directional many-to-one association to Auditoria
	@ManyToOne
	@JoinColumn(name="mem_aud_id")
	private Auditoria auditoria;
	
	// bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name = "mem_usu_id")
	private Usuario usuario1;

	// bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name = "mem_usu_usu_id")
	private Usuario usuario2;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "mem_fecha_elaboro")
	private Date memFechaElaboro;

	@Temporal(TemporalType.DATE)
	@Column(name = "mem_fecha_reviso")
	private Date memFechaReviso;

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

	public int getMemEstado() {
		return memEstado;
	}

	public void setMemEstado(int memEstado) {
		this.memEstado = memEstado;
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

	public String getMemObservaciones() {
		return memObservaciones;
	}

	public void setMemObservaciones(String memObservaciones) {
		this.memObservaciones = memObservaciones;
	}

	public Usuario getUsuario1() {
		return usuario1;
	}

	public void setUsuario1(Usuario usuario1) {
		this.usuario1 = usuario1;
	}

	public Usuario getUsuario2() {
		return usuario2;
	}

	public void setUsuario2(Usuario usuario2) {
		this.usuario2 = usuario2;
	}

	

	public Date getMemFechaElaboro() {
		return memFechaElaboro;
	}

	public void setMemFechaElaboro(Date memFechaElaboro) {
		this.memFechaElaboro = memFechaElaboro;
	}

	public Date getMemFechaReviso() {
		return memFechaReviso;
	}

	public void setMemFechaReviso(Date memFechaReviso) {
		this.memFechaReviso = memFechaReviso;
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