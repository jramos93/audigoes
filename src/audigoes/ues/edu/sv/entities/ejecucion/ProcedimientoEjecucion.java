package audigoes.ues.edu.sv.entities.ejecucion;

import java.io.Serializable;
import javax.persistence.*;

import audigoes.ues.edu.sv.entities.SuperEntity;
import audigoes.ues.edu.sv.entities.administracion.Usuario;
import audigoes.ues.edu.sv.entities.informe.CedulaNota;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the procedimiento_ejecucion database table.
 * 
 */
@Entity
@Table(name="procedimiento_ejecucion")
@NamedQuery(name="ProcedimientoEjecucion.findAll", query="SELECT p FROM ProcedimientoEjecucion p")
public class ProcedimientoEjecucion extends SuperEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "pej_id", schema = "audigoes", table = "contador", pkColumnName = "cnt_nombre", valueColumnName = "cnt_valor", pkColumnValue = "pej_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "pej_id")
	@Column(name="pej_id")
	private int pejId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_crea")
	private Date fecCrea;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_modi")
	private Date fecModi;

	@Lob
	@Column(name="pej_descripcion")
	private String pejDescripcion;

	@Column(name="pej_nombre")
	private String pejNombre;

	@Column(name="reg_activo")
	private int regActivo;

	@Column(name="usu_crea")
	private String usuCrea;

	@Column(name="usu_modi")
	private String usuModi;

	//bi-directional many-to-one association to CedulaNota
	@OneToMany(mappedBy="procedimientoEjecucion")
	private List<CedulaNota> cedulaNotas;

	//bi-directional many-to-one association to DocumentosEjecucion
	@OneToMany(mappedBy="procedimientoEjecucion")
	private List<DocumentosEjecucion> documentosEjecucion;

	//bi-directional many-to-one association to ProgramaEjecucion
	@ManyToOne
	@JoinColumn(name="pej_pre_id")
	private ProgramaEjecucion programaEjecucion;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="pej_usu_id")
	private Usuario usuario1;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="pej_usu_usu_id")
	private Usuario usuario2;

	public ProcedimientoEjecucion() {
	}

	public int getPejId() {
		return this.pejId;
	}

	public void setPejId(int pejId) {
		this.pejId = pejId;
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

	public String getPejDescripcion() {
		return this.pejDescripcion;
	}

	public void setPejDescripcion(String pejDescripcion) {
		this.pejDescripcion = pejDescripcion;
	}

	public String getPejNombre() {
		return this.pejNombre;
	}

	public void setPejNombre(String pejNombre) {
		this.pejNombre = pejNombre;
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

	public List<CedulaNota> getCedulaNotas() {
		return this.cedulaNotas;
	}

	public void setCedulaNotas(List<CedulaNota> cedulaNotas) {
		this.cedulaNotas = cedulaNotas;
	}

	public CedulaNota addCedulaNota(CedulaNota cedulaNota) {
		getCedulaNotas().add(cedulaNota);
		cedulaNota.setProcedimientoEjecucion(this);

		return cedulaNota;
	}

	public CedulaNota removeCedulaNota(CedulaNota cedulaNota) {
		getCedulaNotas().remove(cedulaNota);
		cedulaNota.setProcedimientoEjecucion(null);

		return cedulaNota;
	}

	public List<DocumentosEjecucion> getDocumentosEjecucion() {
		return this.documentosEjecucion;
	}

	public void setDocumentosEjecucion(List<DocumentosEjecucion> documentosEjecucion) {
		this.documentosEjecucion = documentosEjecucion;
	}

	public DocumentosEjecucion addDocumentosEjecucion(DocumentosEjecucion documentosEjecucion) {
		getDocumentosEjecucion().add(documentosEjecucion);
		documentosEjecucion.setProcedimientoEjecucion(this);

		return documentosEjecucion;
	}

	public DocumentosEjecucion removeDocumentosEjecucion(DocumentosEjecucion documentosEjecucion) {
		getDocumentosEjecucion().remove(documentosEjecucion);
		documentosEjecucion.setProcedimientoEjecucion(null);

		return documentosEjecucion;
	}

	public ProgramaEjecucion getProgramaEjecucion() {
		return this.programaEjecucion;
	}

	public void setProgramaEjecucion(ProgramaEjecucion programaEjecucion) {
		this.programaEjecucion = programaEjecucion;
	}

	public Usuario getUsuario1() {
		return this.usuario1;
	}

	public void setUsuario1(Usuario usuario1) {
		this.usuario1 = usuario1;
	}

	public Usuario getUsuario2() {
		return this.usuario2;
	}

	public void setUsuario2(Usuario usuario2) {
		this.usuario2 = usuario2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + pejId;
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
		ProcedimientoEjecucion other = (ProcedimientoEjecucion) obj;
		if (pejId != other.pejId)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ProcedimientoEjecucion [pejId=" + pejId + ", fecCrea=" + fecCrea + ", fecModi=" + fecModi
				+ ", pejDescripcion=" + pejDescripcion + ", pejNombre=" + pejNombre + ", regActivo=" + regActivo
				+ ", usuCrea=" + usuCrea + ", usuModi=" + usuModi + ", cedulaNotas=" + cedulaNotas
				+ ", documentosEjecucion=" + documentosEjecucion + ", programaEjecucion=" + programaEjecucion
				+ ", usuario1=" + usuario1 + ", usuario2=" + usuario2 + "]";
	}
}