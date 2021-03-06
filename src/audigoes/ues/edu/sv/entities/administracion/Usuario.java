package audigoes.ues.edu.sv.entities.administracion;

import java.io.Serializable;
import javax.persistence.*;

import audigoes.ues.edu.sv.entities.SuperEntity;
import audigoes.ues.edu.sv.entities.ejecucion.ProcedimientoEjecucion;
import audigoes.ues.edu.sv.entities.ejecucion.ProgramaEjecucion;
import audigoes.ues.edu.sv.entities.informe.CedulaNota;
import audigoes.ues.edu.sv.entities.planeacion.AuditoriaResponsable;
import audigoes.ues.edu.sv.entities.planificacion.ProcedimientoPlanificacion;
import audigoes.ues.edu.sv.entities.planificacion.ProgramaPlanificacion;
import audigoes.ues.edu.sv.entities.planificacion.UsuarioActividad;
import audigoes.ues.edu.sv.entities.seguimiento.ResponsableRecomendacion;

import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "usu_id", schema = "audigoes", table = "contador", pkColumnName = "cnt_nombre", valueColumnName = "cnt_valor", pkColumnValue = "usu_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "usu_id")
	@Column(name="usu_id")
	private int usuId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_crea")
	private Date fecCrea;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_modi")
	private Date fecModi;

	@Column(name="reg_activo")
	private int regActivo;

	@Column(name="usu_apellido")
	private String usuApellido;

	@Column(name="usu_cargo")
	private String usuCargo;

	@Column(name="usu_carnet_empleado")
	private String usuCarnetEmpleado;

	@Lob
	@Column(name="usu_contrasenia")
	private String usuContrasenia;

	@Column(name="usu_correo")
	private String usuCorreo;

	@Column(name="usu_crea")
	private String usuCrea;

	@Column(name="usu_direccion_residencia")
	private String usuDireccionResidencia;

	@Column(name="usu_dui")
	private String usuDui;
	
	@Column(name="usu_rol")
	private int usuRol;

	@Temporal(TemporalType.DATE)
	@Column(name="usu_fecha_nacimiento")
	private Date usuFechaNacimiento;

	@Lob
	@Column(name="usu_foto")
	private String usuFoto;

	@Column(name="usu_genero")
	private int usuGenero;

	@Column(name="usu_modi")
	private String usuModi;

	@Column(name="usu_nit")
	private String usuNit;

	@Column(name="usu_nombre")
	private String usuNombre;

	@Column(name="usu_usuario")
	private String usuUsuario;

	//bi-directional many-to-one association to AuditoriaResponsable
	@OneToMany(mappedBy="usuario", fetch=FetchType.EAGER)
	private Set<AuditoriaResponsable> auditoriaResponsable;

	//bi-directional many-to-one association to CedulaNota
	@OneToMany(mappedBy="usuario1", fetch=FetchType.EAGER)
	private Set<CedulaNota> cedulaNotas1;

	//bi-directional many-to-one association to CedulaNota
	@OneToMany(mappedBy="usuario2", fetch=FetchType.EAGER)
	private Set<CedulaNota> cedulaNotas2;

	//bi-directional many-to-one association to ProcedimientoEjecucion
	@OneToMany(mappedBy="usuario1", fetch=FetchType.EAGER)
	private Set<ProcedimientoEjecucion> procedimientoEjecucion1;

	//bi-directional many-to-one association to ProcedimientoEjecucion
	@OneToMany(mappedBy="usuario2", fetch=FetchType.EAGER)
	private Set<ProcedimientoEjecucion> procedimientoEjecucion2;

	//bi-directional many-to-one association to ProcedimientoPlanificacion
	@OneToMany(mappedBy="usuario1", fetch=FetchType.EAGER)
	private Set<ProcedimientoPlanificacion> procedimientoPlanificacion1;

	//bi-directional many-to-one association to ProcedimientoPlanificacion
	@OneToMany(mappedBy="usuario2", fetch=FetchType.EAGER)
	private Set<ProcedimientoPlanificacion> procedimientoPlanificacion2;

	//bi-directional many-to-one association to ProgramaEjecucion
	@OneToMany(mappedBy="usuario1", fetch=FetchType.EAGER)
	private Set<ProgramaEjecucion> programaEjecucion1;

	//bi-directional many-to-one association to ProgramaEjecucion
	@OneToMany(mappedBy="usuario2", fetch=FetchType.EAGER)
	private Set<ProgramaEjecucion> programaEjecucion2;

	//bi-directional many-to-one association to ProgramaPlanificacion
	@OneToMany(mappedBy="usuario1", fetch=FetchType.EAGER)
	private Set<ProgramaPlanificacion> programaPlanificacion1;

	//bi-directional many-to-one association to ProgramaPlanificacion
	@OneToMany(mappedBy="usuario2", fetch=FetchType.EAGER)
	private Set<ProgramaPlanificacion> programaPlanificacion2;

	//bi-directional many-to-one association to ResponsableRecomendacion
	@OneToMany(mappedBy="usuario", fetch=FetchType.EAGER)
	private Set<ResponsableRecomendacion> responsableRecomendacion;

	//bi-directional many-to-one association to Institucion
	@ManyToOne
	@JoinColumn(name="usu_ins_id")
	private Institucion institucion;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="usu_jefe_id")
	private Usuario usuario;

	//bi-directional many-to-one association to Usuario
	@OneToMany(mappedBy="usuario", fetch=FetchType.EAGER)
	private Set<Usuario> usuarios;

	//bi-directional many-to-one association to UsuarioActividad
	@OneToMany(mappedBy="usuario", fetch=FetchType.EAGER)
	private Set<UsuarioActividad> usuarioActividad;

	//bi-directional many-to-one association to UsuarioPermiso
	@OneToMany(mappedBy="usuario", fetch=FetchType.EAGER)
	private Set<UsuarioPermiso> usuarioPermiso;

	//bi-directional many-to-one association to UsuarioUnidad
	@OneToMany(mappedBy="usuario", fetch=FetchType.EAGER)
	private Set<UsuarioUnidad> usuarioUnidad;

	public Usuario() {
	}

	public int getUsuId() {
		return this.usuId;
	}

	public void setUsuId(int usuId) {
		this.usuId = usuId;
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

	public String getUsuApellido() {
		return this.usuApellido;
	}

	public void setUsuApellido(String usuApellido) {
		this.usuApellido = usuApellido;
	}

	public String getUsuCargo() {
		return this.usuCargo;
	}

	public void setUsuCargo(String usuCargo) {
		this.usuCargo = usuCargo;
	}

	public String getUsuCarnetEmpleado() {
		return this.usuCarnetEmpleado;
	}

	public void setUsuCarnetEmpleado(String usuCarnetEmpleado) {
		this.usuCarnetEmpleado = usuCarnetEmpleado;
	}

	public String getUsuContrasenia() {
		return this.usuContrasenia;
	}

	public void setUsuContrasenia(String usuContrasenia) {
		this.usuContrasenia = usuContrasenia;
	}

	public String getUsuCorreo() {
		return this.usuCorreo;
	}

	public void setUsuCorreo(String usuCorreo) {
		this.usuCorreo = usuCorreo;
	}

	public String getUsuCrea() {
		return this.usuCrea;
	}

	public void setUsuCrea(String usuCrea) {
		this.usuCrea = usuCrea;
	}

	public String getUsuDireccionResidencia() {
		return this.usuDireccionResidencia;
	}

	public void setUsuDireccionResidencia(String usuDireccionResidencia) {
		this.usuDireccionResidencia = usuDireccionResidencia;
	}

	public String getUsuDui() {
		return this.usuDui;
	}

	public void setUsuDui(String usuDui) {
		this.usuDui = usuDui;
	}

	public Date getUsuFechaNacimiento() {
		return this.usuFechaNacimiento;
	}

	public void setUsuFechaNacimiento(Date usuFechaNacimiento) {
		this.usuFechaNacimiento = usuFechaNacimiento;
	}

	public String getUsuFoto() {
		return this.usuFoto;
	}

	public void setUsuFoto(String usuFoto) {
		this.usuFoto = usuFoto;
	}

	public int getUsuGenero() {
		return this.usuGenero;
	}

	public void setUsuGenero(int usuGenero) {
		this.usuGenero = usuGenero;
	}

	public String getUsuModi() {
		return this.usuModi;
	}

	public void setUsuModi(String usuModi) {
		this.usuModi = usuModi;
	}

	public String getUsuNit() {
		return this.usuNit;
	}

	public void setUsuNit(String usuNit) {
		this.usuNit = usuNit;
	}

	public String getUsuNombre() {
		return this.usuNombre;
	}

	public void setUsuNombre(String usuNombre) {
		this.usuNombre = usuNombre;
	}

	public String getUsuUsuario() {
		return this.usuUsuario;
	}

	public void setUsuUsuario(String usuUsuario) {
		this.usuUsuario = usuUsuario;
	}

	public Set<AuditoriaResponsable> getAuditoriaResponsable() {
		return this.auditoriaResponsable;
	}

	public void setAuditoriaResponsable(Set<AuditoriaResponsable> auditoriaResponsable) {
		this.auditoriaResponsable = auditoriaResponsable;
	}

	public AuditoriaResponsable addAuditoriaResponsable(AuditoriaResponsable auditoriaResponsable) {
		getAuditoriaResponsable().add(auditoriaResponsable);
		auditoriaResponsable.setUsuario(this);

		return auditoriaResponsable;
	}

	public AuditoriaResponsable removeAuditoriaResponsable(AuditoriaResponsable auditoriaResponsable) {
		getAuditoriaResponsable().remove(auditoriaResponsable);
		auditoriaResponsable.setUsuario(null);

		return auditoriaResponsable;
	}

	public Set<CedulaNota> getCedulaNotas1() {
		return this.cedulaNotas1;
	}

	public void setCedulaNotas1(Set<CedulaNota> cedulaNotas1) {
		this.cedulaNotas1 = cedulaNotas1;
	}

	public CedulaNota addCedulaNotas1(CedulaNota cedulaNotas1) {
		getCedulaNotas1().add(cedulaNotas1);
		cedulaNotas1.setUsuario1(this);

		return cedulaNotas1;
	}

	public CedulaNota removeCedulaNotas1(CedulaNota cedulaNotas1) {
		getCedulaNotas1().remove(cedulaNotas1);
		cedulaNotas1.setUsuario1(null);

		return cedulaNotas1;
	}

	public Set<CedulaNota> getCedulaNotas2() {
		return this.cedulaNotas2;
	}

	public void setCedulaNotas2(Set<CedulaNota> cedulaNotas2) {
		this.cedulaNotas2 = cedulaNotas2;
	}

	public CedulaNota addCedulaNotas2(CedulaNota cedulaNotas2) {
		getCedulaNotas2().add(cedulaNotas2);
		cedulaNotas2.setUsuario2(this);

		return cedulaNotas2;
	}

	public CedulaNota removeCedulaNotas2(CedulaNota cedulaNotas2) {
		getCedulaNotas2().remove(cedulaNotas2);
		cedulaNotas2.setUsuario2(null);

		return cedulaNotas2;
	}

	public Set<ProcedimientoEjecucion> getProcedimientoEjecucion1() {
		return this.procedimientoEjecucion1;
	}

	public void setProcedimientoEjecucion1(Set<ProcedimientoEjecucion> procedimientoEjecucion1) {
		this.procedimientoEjecucion1 = procedimientoEjecucion1;
	}

	public ProcedimientoEjecucion addProcedimientoEjecucion1(ProcedimientoEjecucion procedimientoEjecucion1) {
		getProcedimientoEjecucion1().add(procedimientoEjecucion1);
		procedimientoEjecucion1.setUsuario1(this);

		return procedimientoEjecucion1;
	}

	public ProcedimientoEjecucion removeProcedimientoEjecucion1(ProcedimientoEjecucion procedimientoEjecucion1) {
		getProcedimientoEjecucion1().remove(procedimientoEjecucion1);
		procedimientoEjecucion1.setUsuario1(null);

		return procedimientoEjecucion1;
	}

	public Set<ProcedimientoEjecucion> getProcedimientoEjecucion2() {
		return this.procedimientoEjecucion2;
	}

	public void setProcedimientoEjecucion2(Set<ProcedimientoEjecucion> procedimientoEjecucion2) {
		this.procedimientoEjecucion2 = procedimientoEjecucion2;
	}

	public ProcedimientoEjecucion addProcedimientoEjecucion2(ProcedimientoEjecucion procedimientoEjecucion2) {
		getProcedimientoEjecucion2().add(procedimientoEjecucion2);
		procedimientoEjecucion2.setUsuario2(this);

		return procedimientoEjecucion2;
	}

	public ProcedimientoEjecucion removeProcedimientoEjecucion2(ProcedimientoEjecucion procedimientoEjecucion2) {
		getProcedimientoEjecucion2().remove(procedimientoEjecucion2);
		procedimientoEjecucion2.setUsuario2(null);

		return procedimientoEjecucion2;
	}

	public Set<ProcedimientoPlanificacion> getProcedimientoPlanificacion1() {
		return this.procedimientoPlanificacion1;
	}

	public void setProcedimientoPlanificacion1(Set<ProcedimientoPlanificacion> procedimientoPlanificacion1) {
		this.procedimientoPlanificacion1 = procedimientoPlanificacion1;
	}

	public ProcedimientoPlanificacion addProcedimientoPlanificacion1(ProcedimientoPlanificacion procedimientoPlanificacion1) {
		getProcedimientoPlanificacion1().add(procedimientoPlanificacion1);
		procedimientoPlanificacion1.setUsuario1(this);

		return procedimientoPlanificacion1;
	}

	public ProcedimientoPlanificacion removeProcedimientoPlanificacion1(ProcedimientoPlanificacion procedimientoPlanificacion1) {
		getProcedimientoPlanificacion1().remove(procedimientoPlanificacion1);
		procedimientoPlanificacion1.setUsuario1(null);

		return procedimientoPlanificacion1;
	}

	public Set<ProcedimientoPlanificacion> getProcedimientoPlanificacion2() {
		return this.procedimientoPlanificacion2;
	}

	public void setProcedimientoPlanificacion2(Set<ProcedimientoPlanificacion> procedimientoPlanificacion2) {
		this.procedimientoPlanificacion2 = procedimientoPlanificacion2;
	}

	public ProcedimientoPlanificacion addProcedimientoPlanificacion2(ProcedimientoPlanificacion procedimientoPlanificacion2) {
		getProcedimientoPlanificacion2().add(procedimientoPlanificacion2);
		procedimientoPlanificacion2.setUsuario2(this);

		return procedimientoPlanificacion2;
	}

	public ProcedimientoPlanificacion removeProcedimientoPlanificacion2(ProcedimientoPlanificacion procedimientoPlanificacion2) {
		getProcedimientoPlanificacion2().remove(procedimientoPlanificacion2);
		procedimientoPlanificacion2.setUsuario2(null);

		return procedimientoPlanificacion2;
	}

	public Set<ProgramaEjecucion> getProgramaEjecucion1() {
		return this.programaEjecucion1;
	}

	public void setProgramaEjecucion1(Set<ProgramaEjecucion> programaEjecucion1) {
		this.programaEjecucion1 = programaEjecucion1;
	}

	public ProgramaEjecucion addProgramaEjecucion1(ProgramaEjecucion programaEjecucion1) {
		getProgramaEjecucion1().add(programaEjecucion1);
		programaEjecucion1.setUsuario1(this);

		return programaEjecucion1;
	}

	public ProgramaEjecucion removeProgramaEjecucion1(ProgramaEjecucion programaEjecucion1) {
		getProgramaEjecucion1().remove(programaEjecucion1);
		programaEjecucion1.setUsuario1(null);

		return programaEjecucion1;
	}

	public Set<ProgramaEjecucion> getProgramaEjecucion2() {
		return this.programaEjecucion2;
	}

	public void setProgramaEjecucion2(Set<ProgramaEjecucion> programaEjecucion2) {
		this.programaEjecucion2 = programaEjecucion2;
	}

	public ProgramaEjecucion addProgramaEjecucion2(ProgramaEjecucion programaEjecucion2) {
		getProgramaEjecucion2().add(programaEjecucion2);
		programaEjecucion2.setUsuario2(this);

		return programaEjecucion2;
	}

	public ProgramaEjecucion removeProgramaEjecucion2(ProgramaEjecucion programaEjecucion2) {
		getProgramaEjecucion2().remove(programaEjecucion2);
		programaEjecucion2.setUsuario2(null);

		return programaEjecucion2;
	}

	public Set<ProgramaPlanificacion> getProgramaPlanificacion1() {
		return this.programaPlanificacion1;
	}

	public void setProgramaPlanificacion1(Set<ProgramaPlanificacion> programaPlanificacion1) {
		this.programaPlanificacion1 = programaPlanificacion1;
	}

	public ProgramaPlanificacion addProgramaPlanificacion1(ProgramaPlanificacion programaPlanificacion1) {
		getProgramaPlanificacion1().add(programaPlanificacion1);
		programaPlanificacion1.setUsuario1(this);

		return programaPlanificacion1;
	}

	public ProgramaPlanificacion removeProgramaPlanificacion1(ProgramaPlanificacion programaPlanificacion1) {
		getProgramaPlanificacion1().remove(programaPlanificacion1);
		programaPlanificacion1.setUsuario1(null);

		return programaPlanificacion1;
	}

	public Set<ProgramaPlanificacion> getProgramaPlanificacion2() {
		return this.programaPlanificacion2;
	}

	public void setProgramaPlanificacion2(Set<ProgramaPlanificacion> programaPlanificacion2) {
		this.programaPlanificacion2 = programaPlanificacion2;
	}

	public ProgramaPlanificacion addProgramaPlanificacion2(ProgramaPlanificacion programaPlanificacion2) {
		getProgramaPlanificacion2().add(programaPlanificacion2);
		programaPlanificacion2.setUsuario2(this);

		return programaPlanificacion2;
	}

	public ProgramaPlanificacion removeProgramaPlanificacion2(ProgramaPlanificacion programaPlanificacion2) {
		getProgramaPlanificacion2().remove(programaPlanificacion2);
		programaPlanificacion2.setUsuario2(null);

		return programaPlanificacion2;
	}

	public Set<ResponsableRecomendacion> getResponsableRecomendacion() {
		return this.responsableRecomendacion;
	}

	public void setResponsableRecomendacion(Set<ResponsableRecomendacion> responsableRecomendacion) {
		this.responsableRecomendacion = responsableRecomendacion;
	}

	public ResponsableRecomendacion addResponsableRecomendacion(ResponsableRecomendacion responsableRecomendacion) {
		getResponsableRecomendacion().add(responsableRecomendacion);
		responsableRecomendacion.setUsuario(this);

		return responsableRecomendacion;
	}

	public ResponsableRecomendacion removeResponsableRecomendacion(ResponsableRecomendacion responsableRecomendacion) {
		getResponsableRecomendacion().remove(responsableRecomendacion);
		responsableRecomendacion.setUsuario(null);

		return responsableRecomendacion;
	}

	public Institucion getInstitucion() {
		return this.institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Set<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(Set<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario addUsuario(Usuario usuario) {
		getUsuarios().add(usuario);
		usuario.setUsuario(this);

		return usuario;
	}

	public Usuario removeUsuario(Usuario usuario) {
		getUsuarios().remove(usuario);
		usuario.setUsuario(null);

		return usuario;
	}

	public Set<UsuarioActividad> getUsuarioActividad() {
		return this.usuarioActividad;
	}

	public void setUsuarioActividad(Set<UsuarioActividad> usuarioActividad) {
		this.usuarioActividad = usuarioActividad;
	}

	public UsuarioActividad addUsuarioActividad(UsuarioActividad usuarioActividad) {
		getUsuarioActividad().add(usuarioActividad);
		usuarioActividad.setUsuario(this);

		return usuarioActividad;
	}

	public UsuarioActividad removeUsuarioActividad(UsuarioActividad usuarioActividad) {
		getUsuarioActividad().remove(usuarioActividad);
		usuarioActividad.setUsuario(null);

		return usuarioActividad;
	}

	public Set<UsuarioPermiso> getUsuarioPermiso() {
		return this.usuarioPermiso;
	}

	public void setUsuarioPermiso(Set<UsuarioPermiso> usuarioPermiso) {
		this.usuarioPermiso = usuarioPermiso;
	}

	public UsuarioPermiso addUsuarioPermiso(UsuarioPermiso usuarioPermiso) {
		getUsuarioPermiso().add(usuarioPermiso);
		usuarioPermiso.setUsuario(this);

		return usuarioPermiso;
	}

	public UsuarioPermiso removeUsuarioPermiso(UsuarioPermiso usuarioPermiso) {
		getUsuarioPermiso().remove(usuarioPermiso);
		usuarioPermiso.setUsuario(null);

		return usuarioPermiso;
	}

	public Set<UsuarioUnidad> getUsuarioUnidad() {
		return this.usuarioUnidad;
	}

	public void setUsuarioUnidad(Set<UsuarioUnidad> usuarioUnidad) {
		this.usuarioUnidad = usuarioUnidad;
	}

	public UsuarioUnidad addUsuarioUnidad(UsuarioUnidad usuarioUnidad) {
		getUsuarioUnidad().add(usuarioUnidad);
		usuarioUnidad.setUsuario(this);

		return usuarioUnidad;
	}

	public UsuarioUnidad removeUsuarioUnidad(UsuarioUnidad usuarioUnidad) {
		getUsuarioUnidad().remove(usuarioUnidad);
		usuarioUnidad.setUsuario(null);

		return usuarioUnidad;
	}

	public int getUsuRol() {
		return usuRol;
	}

	public void setUsuRol(int usuRol) {
		this.usuRol = usuRol;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + usuId;
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
		Usuario other = (Usuario) obj;
		if (usuId != other.usuId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [usuId=" + usuId + ", fecCrea=" + fecCrea + ", fecModi=" + fecModi + ", regActivo=" + regActivo
				+ ", usuApellido=" + usuApellido + ", usuCargo=" + usuCargo + ", usuCarnetEmpleado=" + usuCarnetEmpleado
				+ ", usuContrasenia=" + usuContrasenia + ", usuCorreo=" + usuCorreo + ", usuCrea=" + usuCrea
				+ ", usuDireccionResidencia=" + usuDireccionResidencia + ", usuDui=" + usuDui + ", usuFechaNacimiento="
				+ usuFechaNacimiento + ", usuFoto=" + usuFoto + ", usuGenero=" + usuGenero + ", usuModi=" + usuModi
				+ ", usuNit=" + usuNit + ", usuNombre=" + usuNombre + ", usuUsuario=" + usuUsuario
				+ ", auditoriaResponsable=" + auditoriaResponsable + ", cedulaNotas1=" + cedulaNotas1
				+ ", cedulaNotas2=" + cedulaNotas2 + ", procedimientoEjecucion1=" + procedimientoEjecucion1
				+ ", procedimientoEjecucion2=" + procedimientoEjecucion2 + ", procedimientoPlanificacion1="
				+ procedimientoPlanificacion1 + ", procedimientoPlanificacion2=" + procedimientoPlanificacion2
				+ ", programaEjecucion1=" + programaEjecucion1 + ", programaEjecucion2=" + programaEjecucion2
				+ ", programaPlanificacion1=" + programaPlanificacion1 + ", programaPlanificacion2="
				+ programaPlanificacion2 + ", responsableRecomendacion=" + responsableRecomendacion + ", institucion="
				+ institucion + ", usuario=" + usuario + ", usuarios=" + usuarios + ", usuarioActividad="
				+ usuarioActividad + ", usuarioPermiso=" + usuarioPermiso + ", usuarioUnidad=" + usuarioUnidad + "]";
	}

}