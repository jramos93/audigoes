package audigoes.ues.edu.sv.entities.planificacion;

import java.io.Serializable;
import javax.persistence.*;

import audigoes.ues.edu.sv.entities.SuperEntity;
import audigoes.ues.edu.sv.entities.ejecucion.ProgramaEjecucion;
import audigoes.ues.edu.sv.entities.informe.Informe;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the actividad database table.
 * 
 */
@Entity
@NamedQuery(name="Actividad.findAll", query="SELECT a FROM Actividad a")
public class Actividad extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="act_id")
	private int actId;

	@Column(name="act_descripcion")
	private String actDescripcion;

	@Temporal(TemporalType.DATE)
	@Column(name="act_fec_fin")
	private Date actFecFin;

	@Temporal(TemporalType.DATE)
	@Column(name="act_fec_ini")
	private Date actFecIni;

	@Column(name="act_nombre")
	private String actNombre;

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
	@JoinColumn(name="act_aud_id")
	private Auditoria auditoria;

	//bi-directional many-to-one association to Informe
	@OneToMany(mappedBy="actividad")
	private List<Informe> informe;

	//bi-directional many-to-one association to Modificacion
	@OneToMany(mappedBy="actividad")
	private List<Modificacion> modificacion;

	//bi-directional many-to-one association to ProgramaEjecucion
	@OneToMany(mappedBy="actividad")
	private List<ProgramaEjecucion> programaEjecucion;

	//bi-directional many-to-one association to ProgramaPlanificacion
	@OneToMany(mappedBy="actividad")
	private List<ProgramaPlanificacion> programaPlanificacion;

	//bi-directional many-to-one association to UsuarioActividad
	@OneToMany(mappedBy="actividad")
	private List<UsuarioActividad> usuarioActividad;

	public Actividad() {
	}

	public int getActId() {
		return this.actId;
	}

	public void setActId(int actId) {
		this.actId = actId;
	}

	public String getActDescripcion() {
		return this.actDescripcion;
	}

	public void setActDescripcion(String actDescripcion) {
		this.actDescripcion = actDescripcion;
	}

	public Date getActFecFin() {
		return this.actFecFin;
	}

	public void setActFecFin(Date actFecFin) {
		this.actFecFin = actFecFin;
	}

	public Date getActFecIni() {
		return this.actFecIni;
	}

	public void setActFecIni(Date actFecIni) {
		this.actFecIni = actFecIni;
	}

	public String getActNombre() {
		return this.actNombre;
	}

	public void setActNombre(String actNombre) {
		this.actNombre = actNombre;
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

	public List<Informe> getInforme() {
		return this.informe;
	}

	public void setInforme(List<Informe> informe) {
		this.informe = informe;
	}

	public Informe addInforme(Informe informe) {
		getInforme().add(informe);
		informe.setActividad(this);

		return informe;
	}

	public Informe removeInforme(Informe informe) {
		getInforme().remove(informe);
		informe.setActividad(null);

		return informe;
	}

	public List<Modificacion> getModificacion() {
		return this.modificacion;
	}

	public void setModificacion(List<Modificacion> modificacion) {
		this.modificacion = modificacion;
	}

	public Modificacion addModificacion(Modificacion modificacion) {
		getModificacion().add(modificacion);
		modificacion.setActividad(this);

		return modificacion;
	}

	public Modificacion removeModificacion(Modificacion modificacion) {
		getModificacion().remove(modificacion);
		modificacion.setActividad(null);

		return modificacion;
	}

	public List<ProgramaEjecucion> getProgramaEjecucion() {
		return this.programaEjecucion;
	}

	public void setProgramaEjecucion(List<ProgramaEjecucion> programaEjecucion) {
		this.programaEjecucion = programaEjecucion;
	}

	public ProgramaEjecucion addProgramaEjecucion(ProgramaEjecucion programaEjecucion) {
		getProgramaEjecucion().add(programaEjecucion);
		programaEjecucion.setActividad(this);

		return programaEjecucion;
	}

	public ProgramaEjecucion removeProgramaEjecucion(ProgramaEjecucion programaEjecucion) {
		getProgramaEjecucion().remove(programaEjecucion);
		programaEjecucion.setActividad(null);

		return programaEjecucion;
	}

	public List<ProgramaPlanificacion> getProgramaPlanificacion() {
		return this.programaPlanificacion;
	}

	public void setProgramaPlanificacion(List<ProgramaPlanificacion> programaPlanificacion) {
		this.programaPlanificacion = programaPlanificacion;
	}

	public ProgramaPlanificacion addProgramaPlanificacion(ProgramaPlanificacion programaPlanificacion) {
		getProgramaPlanificacion().add(programaPlanificacion);
		programaPlanificacion.setActividad(this);

		return programaPlanificacion;
	}

	public ProgramaPlanificacion removeProgramaPlanificacion(ProgramaPlanificacion programaPlanificacion) {
		getProgramaPlanificacion().remove(programaPlanificacion);
		programaPlanificacion.setActividad(null);

		return programaPlanificacion;
	}

	public List<UsuarioActividad> getUsuarioActividad() {
		return this.usuarioActividad;
	}

	public void setUsuarioActividad(List<UsuarioActividad> usuarioActividad) {
		this.usuarioActividad = usuarioActividad;
	}

	public UsuarioActividad addUsuarioActividad(UsuarioActividad usuarioActividad) {
		getUsuarioActividad().add(usuarioActividad);
		usuarioActividad.setActividad(this);

		return usuarioActividad;
	}

	public UsuarioActividad removeUsuarioActividad(UsuarioActividad usuarioActividad) {
		getUsuarioActividad().remove(usuarioActividad);
		usuarioActividad.setActividad(null);

		return usuarioActividad;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + actId;
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
		Actividad other = (Actividad) obj;
		if (actId != other.actId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Actividad [actId=" + actId + ", actDescripcion=" + actDescripcion + ", actFecFin=" + actFecFin
				+ ", actFecIni=" + actFecIni + ", actNombre=" + actNombre + ", fecCrea=" + fecCrea + ", fecModi="
				+ fecModi + ", regActivo=" + regActivo + ", usuCrea=" + usuCrea + ", usuModi=" + usuModi
				+ ", auditoria=" + auditoria + ", informe=" + informe + ", modificacion=" + modificacion
				+ ", programaEjecucion=" + programaEjecucion + ", programaPlanificacion=" + programaPlanificacion
				+ ", usuarioActividad=" + usuarioActividad + "]";
	}

}