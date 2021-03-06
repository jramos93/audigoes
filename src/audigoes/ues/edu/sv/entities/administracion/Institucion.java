package audigoes.ues.edu.sv.entities.administracion;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import audigoes.ues.edu.sv.entities.SuperEntity;
import audigoes.ues.edu.sv.entities.planeacion.PlanAnual;
import audigoes.ues.edu.sv.entities.planeacion.TipoAuditoria;

/**
 * The persistent class for the institucion database table.
 * 
 */
@Entity
@NamedQuery(name = "Institucion.findAll", query = "SELECT i FROM Institucion i")
public class Institucion extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "ins_id", schema = "audigoes", table = "contador", pkColumnName = "cnt_nombre", valueColumnName = "cnt_valor", pkColumnValue = "ins_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ins_id")
	@Column(name = "ins_id")
	private int insId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fec_crea")
	private Date fecCrea;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fec_modi")
	private Date fecModi;

	@Column(name = "ins_direccion")
	private String insDireccion;

	@Column(name = "ins_iniciales")
	private String insIniciales;

	@Lob
	@Column(name = "ins_logo")
	private String insLogo;

	@Column(name = "ins_nit")
	private String insNit;

	@Column(name = "ins_nombre")
	private String insNombre;

	@Column(name = "ins_slogan")
	private String insSlogan;

	@Column(name = "ins_telefono")
	private String insTelefono;

	@Column(name = "reg_activo")
	private int regActivo;

	@Column(name = "usu_crea")
	private String usuCrea;

	@Column(name = "usu_modi")
	private String usuModi;

	// bi-directional many-to-one association to Configuracion
	@OneToMany(mappedBy = "institucion", fetch = FetchType.EAGER)
	private Set<Configuracion> configuracion;

	// bi-directional many-to-one association to Marca
	@OneToMany(mappedBy = "institucion", fetch = FetchType.EAGER)
	private Set<Marca> marca;

	// bi-directional many-to-one association to NormativaCedula
	@OneToMany(mappedBy = "institucion", fetch = FetchType.EAGER)
	private Set<NormativaCedula> normativaCedula;

	// bi-directional many-to-one association to Unidad
	@OneToMany(mappedBy = "institucion", fetch = FetchType.EAGER)
	private Set<Unidad> unidad;

	// bi-directional many-to-one association to Usuario
	@OneToMany(mappedBy = "institucion", fetch = FetchType.EAGER)
	private Set<Usuario> usuario;

	// bi-directional many-to-one association to PlanAnual
	@OneToMany(mappedBy = "institucion", fetch = FetchType.EAGER)
	private Set<PlanAnual> planAnual;

	// bi-directional many-to-one association to TipoAuditoria
	@OneToMany(mappedBy = "institucion")
	private List<TipoAuditoria> tipoAuditoria;

	// bi-directional many-to-one association to Rol
	@OneToMany(mappedBy = "institucion")
	private Set<Rol> rol;

	public Institucion() {
	}

	public int getInsId() {
		return this.insId;
	}

	public void setInsId(int insId) {
		this.insId = insId;
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

	public String getInsDireccion() {
		return this.insDireccion;
	}

	public void setInsDireccion(String insDireccion) {
		this.insDireccion = insDireccion;
	}

	public String getInsIniciales() {
		return this.insIniciales;
	}

	public void setInsIniciales(String insIniciales) {
		this.insIniciales = insIniciales;
	}

	public String getInsLogo() {
		return this.insLogo;
	}

	public void setInsLogo(String insLogo) {
		this.insLogo = insLogo;
	}

	public String getInsNit() {
		return this.insNit;
	}

	public void setInsNit(String insNit) {
		this.insNit = insNit;
	}

	public String getInsNombre() {
		return this.insNombre;
	}

	public void setInsNombre(String insNombre) {
		this.insNombre = insNombre;
	}

	public String getInsSlogan() {
		return this.insSlogan;
	}

	public void setInsSlogan(String insSlogan) {
		this.insSlogan = insSlogan;
	}

	public String getInsTelefono() {
		return this.insTelefono;
	}

	public void setInsTelefono(String insTelefono) {
		this.insTelefono = insTelefono;
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

	public Set<Configuracion> getConfiguracion() {
		return this.configuracion;
	}

	public void setConfiguracion(Set<Configuracion> configuracion) {
		this.configuracion = configuracion;
	}

	public Configuracion addConfiguracion(Configuracion configuracion) {
		getConfiguracion().add(configuracion);
		configuracion.setInstitucion(this);

		return configuracion;
	}

	public Configuracion removeConfiguracion(Configuracion configuracion) {
		getConfiguracion().remove(configuracion);
		configuracion.setInstitucion(null);

		return configuracion;
	}

	public Set<Marca> getMarca() {
		return this.marca;
	}

	public void setMarca(Set<Marca> marca) {
		this.marca = marca;
	}

	public Marca addMarca(Marca marca) {
		getMarca().add(marca);
		marca.setInstitucion(this);

		return marca;
	}

	public Marca removeMarca(Marca marca) {
		getMarca().remove(marca);
		marca.setInstitucion(null);

		return marca;
	}

	public Set<NormativaCedula> getNormativaCedula() {
		return this.normativaCedula;
	}

	public void setNormativaCedula(Set<NormativaCedula> normativaCedula) {
		this.normativaCedula = normativaCedula;
	}

	public NormativaCedula addNormativaCedula(NormativaCedula normativaCedula) {
		getNormativaCedula().add(normativaCedula);
		normativaCedula.setInstitucion(this);

		return normativaCedula;
	}

	public NormativaCedula removeNormativaCedula(NormativaCedula normativaCedula) {
		getNormativaCedula().remove(normativaCedula);
		normativaCedula.setInstitucion(null);

		return normativaCedula;
	}

	public Set<Unidad> getUnidad() {
		return this.unidad;
	}

	public void setUnidad(Set<Unidad> unidad) {
		this.unidad = unidad;
	}

	public Unidad addUnidad(Unidad unidad) {
		getUnidad().add(unidad);
		unidad.setInstitucion(this);

		return unidad;
	}

	public Unidad removeUnidad(Unidad unidad) {
		getUnidad().remove(unidad);
		unidad.setInstitucion(null);

		return unidad;
	}

	public Set<Usuario> getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Set<Usuario> usuario) {
		this.usuario = usuario;
	}

	public Usuario addUsuario(Usuario usuario) {
		getUsuario().add(usuario);
		usuario.setInstitucion(this);

		return usuario;
	}

	public Usuario removeUsuario(Usuario usuario) {
		getUsuario().remove(usuario);
		usuario.setInstitucion(null);

		return usuario;
	}

	public Set<PlanAnual> getPlanAnual() {
		return this.planAnual;
	}

	public void setPlanAnual(Set<PlanAnual> planAnual) {
		this.planAnual = planAnual;
	}

	public PlanAnual addPlanAnual(PlanAnual planAnual) {
		getPlanAnual().add(planAnual);
		planAnual.setInstitucion(this);

		return planAnual;
	}

	public PlanAnual removePlanAnual(PlanAnual planAnual) {
		getPlanAnual().remove(planAnual);
		planAnual.setInstitucion(null);

		return planAnual;
	}

	public List<TipoAuditoria> getTipoAuditoria() {
		return this.tipoAuditoria;
	}

	public void setTipoAuditoria(List<TipoAuditoria> tipoAuditoria) {
		this.tipoAuditoria = tipoAuditoria;
	}

	public TipoAuditoria addTipoAuditoria(TipoAuditoria tipoAuditoria) {
		getTipoAuditoria().add(tipoAuditoria);
		tipoAuditoria.setInstitucion(this);

		return tipoAuditoria;
	}

	public TipoAuditoria removeTipoAuditoria(TipoAuditoria tipoAuditoria) {
		getTipoAuditoria().remove(tipoAuditoria);
		tipoAuditoria.setInstitucion(null);

		return tipoAuditoria;
	}
	
	public Set<Rol> getRol() {
		return this.rol;
	}

	public void setRol(Set<Rol> rol) {
		this.rol = rol;
	}

	public Rol addRol(Rol rol) {
		getRol().add(rol);
		rol.setInstitucion(this);

		return rol;
	}

	public Rol removeRol(Rol rol) {
		getRol().remove(rol);
		rol.setInstitucion(null);

		return rol;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + insId;
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
		Institucion other = (Institucion) obj;
		if (insId != other.insId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Institucion [insId=" + insId + ", fecCrea=" + fecCrea + ", fecModi=" + fecModi + ", insDireccion="
				+ insDireccion + ", insIniciales=" + insIniciales + ", insLogo=" + insLogo + ", insNit=" + insNit
				+ ", insNombre=" + insNombre + ", insSlogan=" + insSlogan + ", insTelefono=" + insTelefono
				+ ", regActivo=" + regActivo + ", usuCrea=" + usuCrea + ", usuModi=" + usuModi + ", configuracion="
				+ configuracion + ", marca=" + marca + ", normativaCedula=" + normativaCedula + ", unidad=" + unidad
				+ ", usuario=" + usuario + ", planAnual=" + planAnual + ", tipoAuditoria=" + tipoAuditoria + ", rol="
				+ rol + "]";
	}

}