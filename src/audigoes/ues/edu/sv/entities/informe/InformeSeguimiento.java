package audigoes.ues.edu.sv.entities.informe;

import java.io.Serializable;
import javax.persistence.*;

import audigoes.ues.edu.sv.entities.SuperEntity;
import audigoes.ues.edu.sv.entities.administracion.Usuario;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;

import java.util.Date;


/**
 * The persistent class for the informe_seguimiento database table.
 * 
 */
@Entity
@Table(name="informe_seguimiento")
@NamedQuery(name="InformeSeguimiento.findAll", query="SELECT i FROM InformeSeguimiento i")
public class InformeSeguimiento extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "infs_id", schema = "audigoes", table = "contador", pkColumnName = "cnt_nombre", valueColumnName = "cnt_valor", pkColumnValue = "infs_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "infs_id")
	@Column(name = "infs_id")
	private int infsId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_crea")
	private Date fecCrea;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_modi")
	private Date fecModi;

	@Lob
	@Column(name="infs_aclaracion")
	private String infsAclaracion;

	@Lob
	@Column(name="infs_alcance")
	private String infsAlcance;

	@Lob
	@Column(name="infs_conclusion")
	private String infsConclusion;

	@Lob
	@Column(name="infs_destinatario")
	private String infsDestinatario;

	@Lob
	@Column(name="infs_encabezado")
	private String infsEncabezado;

	@Column(name="infs_estado")
	private int infsEstado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="infs_fec_aprobo")
	private Date infsFecAprobo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="infs_fec_elaboro")
	private Date infsFecElaboro;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="infs_fec_reviso")
	private Date infsFecReviso;

	@Lob
	@Column(name="infs_indice")
	private String infsIndice;

	@Lob
	@Column(name="infs_introduccion")
	private String infsIntroduccion;

	@Lob
	@Column(name="infs_logros")
	private String infsLogros;

	@Lob
	@Column(name="infs_objetivos")
	private String infsObjetivos;

	@Lob
	@Column(name="infs_observaciones")
	private String infsObservaciones;

	@Lob
	@Column(name="infs_observaciones_jefe")
	private String infsObservacionesJefe;

	@Lob
	@Column(name="infs_pie_pagina")
	private String infsPiePagina;

	@Lob
	@Column(name="infs_portada")
	private String infsPortada;

	@Lob
	@Column(name="infs_procedimientos")
	private String infsProcedimientos;

	@Lob
	@Column(name="infs_recomendaciones")
	private String infsRecomendaciones;

	@Lob
	@Column(name="infs_resultados")
	private String infsResultados;

	@Lob
	@Column(name="infs_seguimiento")
	private String infsSeguimiento;

	@Column(name="infs_titulo")
	private String infsTitulo;

	@Column(name="infs_version")
	private int infsVersion;

	@Column(name="reg_activo")
	private int regActivo;

	@Column(name="usu_crea")
	private String usuCrea;

	@Column(name="usu_modi")
	private String usuModi;
	
	// bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name = "infs_usu_elaboro")
	private Usuario usuarioElaboro;

	// bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name = "infs_usu_reviso")
	private Usuario usuarioReviso;

	// bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name = "infs_usu_aprobo")
	private Usuario usuarioAprobo;
	
	// bi-directional many-to-one association to Auditoria
	@ManyToOne
	@JoinColumn(name = "infs_aud_id")
	private Auditoria auditoria;

	public InformeSeguimiento() {
	}

	public int getInfsId() {
		return this.infsId;
	}

	public void setInfsId(int infsId) {
		this.infsId = infsId;
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

	public String getInfsAclaracion() {
		return this.infsAclaracion;
	}

	public void setInfsAclaracion(String infsAclaracion) {
		this.infsAclaracion = infsAclaracion;
	}

	public String getInfsAlcance() {
		return this.infsAlcance;
	}

	public void setInfsAlcance(String infsAlcance) {
		this.infsAlcance = infsAlcance;
	}

	public String getInfsConclusion() {
		return this.infsConclusion;
	}

	public void setInfsConclusion(String infsConclusion) {
		this.infsConclusion = infsConclusion;
	}

	public String getInfsDestinatario() {
		return this.infsDestinatario;
	}

	public void setInfsDestinatario(String infsDestinatario) {
		this.infsDestinatario = infsDestinatario;
	}

	public String getInfsEncabezado() {
		return this.infsEncabezado;
	}

	public void setInfsEncabezado(String infsEncabezado) {
		this.infsEncabezado = infsEncabezado;
	}

	public int getInfsEstado() {
		return this.infsEstado;
	}

	public void setInfsEstado(int infsEstado) {
		this.infsEstado = infsEstado;
	}

	public Date getInfsFecAprobo() {
		return this.infsFecAprobo;
	}

	public void setInfsFecAprobo(Date infsFecAprobo) {
		this.infsFecAprobo = infsFecAprobo;
	}

	public Date getInfsFecElaboro() {
		return this.infsFecElaboro;
	}

	public void setInfsFecElaboro(Date infsFecElaboro) {
		this.infsFecElaboro = infsFecElaboro;
	}

	public Date getInfsFecReviso() {
		return this.infsFecReviso;
	}

	public void setInfsFecReviso(Date infsFecReviso) {
		this.infsFecReviso = infsFecReviso;
	}

	public String getInfsIndice() {
		return this.infsIndice;
	}

	public void setInfsIndice(String infsIndice) {
		this.infsIndice = infsIndice;
	}

	public String getInfsIntroduccion() {
		return this.infsIntroduccion;
	}

	public void setInfsIntroduccion(String infsIntroduccion) {
		this.infsIntroduccion = infsIntroduccion;
	}

	public String getInfsLogros() {
		return this.infsLogros;
	}

	public void setInfsLogros(String infsLogros) {
		this.infsLogros = infsLogros;
	}

	public String getInfsObjetivos() {
		return this.infsObjetivos;
	}

	public void setInfsObjetivos(String infsObjetivos) {
		this.infsObjetivos = infsObjetivos;
	}

	public String getInfsObservaciones() {
		return this.infsObservaciones;
	}

	public void setInfsObservaciones(String infsObservaciones) {
		this.infsObservaciones = infsObservaciones;
	}

	public String getInfsObservacionesJefe() {
		return this.infsObservacionesJefe;
	}

	public void setInfsObservacionesJefe(String infsObservacionesJefe) {
		this.infsObservacionesJefe = infsObservacionesJefe;
	}

	public String getInfsPiePagina() {
		return this.infsPiePagina;
	}

	public void setInfsPiePagina(String infsPiePagina) {
		this.infsPiePagina = infsPiePagina;
	}

	public String getInfsPortada() {
		return this.infsPortada;
	}

	public void setInfsPortada(String infsPortada) {
		this.infsPortada = infsPortada;
	}

	public String getInfsProcedimientos() {
		return this.infsProcedimientos;
	}

	public void setInfsProcedimientos(String infsProcedimientos) {
		this.infsProcedimientos = infsProcedimientos;
	}

	public String getInfsRecomendaciones() {
		return this.infsRecomendaciones;
	}

	public void setInfsRecomendaciones(String infsRecomendaciones) {
		this.infsRecomendaciones = infsRecomendaciones;
	}

	public String getInfsResultados() {
		return this.infsResultados;
	}

	public void setInfsResultados(String infsResultados) {
		this.infsResultados = infsResultados;
	}

	public String getInfsSeguimiento() {
		return this.infsSeguimiento;
	}

	public void setInfsSeguimiento(String infsSeguimiento) {
		this.infsSeguimiento = infsSeguimiento;
	}

	public String getInfsTitulo() {
		return this.infsTitulo;
	}

	public void setInfsTitulo(String infsTitulo) {
		this.infsTitulo = infsTitulo;
	}

	public int getInfsVersion() {
		return this.infsVersion;
	}

	public void setInfsVersion(int infsVersion) {
		this.infsVersion = infsVersion;
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

	public Usuario getUsuarioElaboro() {
		return usuarioElaboro;
	}

	public void setUsuarioElaboro(Usuario usuarioElaboro) {
		this.usuarioElaboro = usuarioElaboro;
	}

	public Usuario getUsuarioReviso() {
		return usuarioReviso;
	}

	public void setUsuarioReviso(Usuario usuarioReviso) {
		this.usuarioReviso = usuarioReviso;
	}

	public Usuario getUsuarioAprobo() {
		return usuarioAprobo;
	}

	public void setUsuarioAprobo(Usuario usuarioAprobo) {
		this.usuarioAprobo = usuarioAprobo;
	}

	public Auditoria getAuditoria() {
		return auditoria;
	}

	public void setAuditoria(Auditoria auditoria) {
		this.auditoria = auditoria;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + infsId;
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
		InformeSeguimiento other = (InformeSeguimiento) obj;
		if (infsId != other.infsId)
			return false;
		return true;
	}

}