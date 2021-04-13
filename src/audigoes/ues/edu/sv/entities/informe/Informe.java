package audigoes.ues.edu.sv.entities.informe;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import audigoes.ues.edu.sv.entities.SuperEntity;
import audigoes.ues.edu.sv.entities.administracion.Archivo;
import audigoes.ues.edu.sv.entities.administracion.Usuario;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;
import audigoes.ues.edu.sv.entities.planificacion.Actividad;

/**
 * The persistent class for the informe database table.
 * 
 */
@Entity
@Table(name="informe")
@NamedQuery(name = "Informe.findAll", query = "SELECT i FROM Informe i")
public class Informe extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "inf_id", schema = "audigoes", table = "contador", pkColumnName = "cnt_nombre", valueColumnName = "cnt_valor", pkColumnValue = "inf_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "inf_id")
	@Column(name = "inf_id")
	private int infId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fec_crea")
	private Date fecCrea;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fec_modi")
	private Date fecModi;

	@Lob
	@Column(name = "inf_aclaracion")
	private String infAclaracion;

	@Lob
	@Column(name = "inf_conclusion")
	private String infConclusion;

	@Lob
	@Column(name = "inf_encabezado")
	private String infEncabezado;

	@Lob
	@Column(name = "inf_introduccion")
	private String infIntroduccion;

	@Lob
	@Column(name = "inf_logros")
	private String infLogros;

	@Lob
	@Column(name = "inf_pie_pagina")
	private String infPiePagina;

	@Lob
	@Column(name = "inf_portada")
	private String infPortada;

	@Lob
	@Column(name = "inf_indice")
	private String infIndice;

	@Lob
	@Column(name = "inf_destinatario")
	private String infDestinatario;

	@Lob
	@Column(name = "inf_objetivos")
	private String infObjetivos;

	@Lob
	@Column(name = "inf_alcance")
	private String infAlcance;

	@Lob
	@Column(name = "inf_procedimientos")
	private String infProcedimientos;

	@Lob
	@Column(name = "inf_recomendaciones")
	private String infRecomendaciones;

	@Lob
	@Column(name = "inf_resultados")
	private String infResultados;

	@Lob
	@Column(name = "inf_observaciones")
	private String infObservaciones;
	
	@Lob
	@Column(name = "inf_observaciones_jefe")
	private String infObservacionesJefe;

	@Column(name = "inf_estado")
	private int infEstado;

	@Lob
	@Column(name = "inf_seguimiento")
	private String infSeguimiento;

	@Column(name = "inf_titulo")
	private String infTitulo;

	@Column(name = "inf_version")
	private int infVersion;

	@Column(name = "reg_activo")
	private int regActivo;

	@Column(name = "usu_crea")
	private String usuCrea;

	@Column(name = "usu_modi")
	private String usuModi;

	// bi-directional many-to-one association to ActaLectura
	@OneToMany(mappedBy = "informe")
	private List<ActaLectura> actaLectura;

	// bi-directional many-to-one association to ActaLectura
	@OneToMany(mappedBy = "informe")
	private List<CartaGerencia> cartaGerencia;

	// bi-directional many-to-one association to ActaLectura
	@OneToMany(mappedBy = "informe")
	private List<Convocatoria> convocatoria;

	// bi-directional many-to-one association to CedulaNota
	@OneToMany(mappedBy = "informe")
	private List<CedulaNota> cedulaNotas;

	// bi-directional many-to-one association to Actividad
	@ManyToOne
	@JoinColumn(name = "inf_act_id")
	private Actividad actividad;

	// bi-directional many-to-one association to Auditoria
	@ManyToOne
	@JoinColumn(name = "inf_aud_id")
	private Auditoria auditoria;

	// bi-directional many-to-one association to Archivo
	@OneToMany(mappedBy = "procedimientoPlanificacion")
	private List<Archivo> archivo;

	// bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name = "inf_usu_elaboro")
	private Usuario usuarioElaboro;

	// bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name = "inf_usu_reviso")
	private Usuario usuarioReviso;

	// bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name = "inf_usu_aprobo")
	private Usuario usuarioAprobo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "inf_fec_elaboro")
	private Date infFecElaboro;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "inf_fec_reviso")
	private Date infFecReviso;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "inf_fec_aprobo")
	private Date infFecAprobo;

	public Informe() {
	}

	public int getInfId() {
		return this.infId;
	}

	public void setInfId(int infId) {
		this.infId = infId;
	}

	public Date getFecCrea() {
		return this.fecCrea;
	}

	public void setFecCrea(Date fecCrea) {
		this.fecCrea = fecCrea;
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

	public Date getInfFecElaboro() {
		return infFecElaboro;
	}

	public void setInfFecElaboro(Date infFecElaboro) {
		this.infFecElaboro = infFecElaboro;
	}

	public Date getInfFecReviso() {
		return infFecReviso;
	}

	public void setInfFecReviso(Date infFecReviso) {
		this.infFecReviso = infFecReviso;
	}

	public Date getInfFecAprobo() {
		return infFecAprobo;
	}

	public void setInfFecAprobo(Date infFecAprobo) {
		this.infFecAprobo = infFecAprobo;
	}

	public Date getFecModi() {
		return this.fecModi;
	}

	public void setFecModi(Date fecModi) {
		this.fecModi = fecModi;
	}

	public String getInfAclaracion() {
		return this.infAclaracion;
	}

	public void setInfAclaracion(String infAclaracion) {
		this.infAclaracion = infAclaracion;
	}

	public String getInfConclusion() {
		return this.infConclusion;
	}

	public void setInfConclusion(String infConclusion) {
		this.infConclusion = infConclusion;
	}

	public String getInfEncabezado() {
		return this.infEncabezado;
	}

	public void setInfEncabezado(String infEncabezado) {
		this.infEncabezado = infEncabezado;
	}

	public String getInfIntroduccion() {
		return this.infIntroduccion;
	}

	public void setInfIntroduccion(String infIntroduccion) {
		this.infIntroduccion = infIntroduccion;
	}

	public String getInfLogros() {
		return this.infLogros;
	}

	public void setInfLogros(String infLogros) {
		this.infLogros = infLogros;
	}

	public String getInfPiePagina() {
		return this.infPiePagina;
	}

	public void setInfPiePagina(String infPiePagina) {
		this.infPiePagina = infPiePagina;
	}

	public String getInfPortada() {
		return this.infPortada;
	}

	public void setInfPortada(String infPortada) {
		this.infPortada = infPortada;
	}

	public String getInfDestinatario() {
		return this.infDestinatario;
	}

	public void setInfDestinatario(String infDestinatario) {
		this.infDestinatario = infDestinatario;
	}

	public String getInfProcedimientos() {
		return this.infProcedimientos;
	}

	public void setInfProcedimientos(String infProcedimientos) {
		this.infProcedimientos = infProcedimientos;
	}

	public String getInfRecomendaciones() {
		return this.infRecomendaciones;
	}

	public void setInfRecomendaciones(String infRecomendaciones) {
		this.infRecomendaciones = infRecomendaciones;
	}

	public String getInfResultados() {
		return this.infResultados;
	}

	public void setInfResultados(String infResultados) {
		this.infResultados = infResultados;
	}

	public String getInfSeguimiento() {
		return this.infSeguimiento;
	}

	public void setInfSeguimiento(String infSeguimiento) {
		this.infSeguimiento = infSeguimiento;
	}

	public String getInfTitulo() {
		return this.infTitulo;
	}

	public void setInfTitulo(String infTitulo) {
		this.infTitulo = infTitulo;
	}

	public String getInfObservaciones() {
		return this.infObservaciones;
	}

	public void setInfObservaciones(String infObservaciones) {
		this.infObservaciones = infObservaciones;
	}

	public int getInfVersion() {
		return this.infVersion;
	}

	public void setInfVersion(int infVersion) {
		this.infVersion = infVersion;
	}

	public int getRegActivo() {
		return this.regActivo;
	}

	public void setRegActivo(int regActivo) {
		this.regActivo = regActivo;
	}

	public int getInfEstado() {
		return infEstado;
	}

	public void setInfEstado(int infEstado) {
		this.infEstado = infEstado;
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

	public List<ActaLectura> getActaLectura() {
		return this.actaLectura;
	}

	public void setActaLectura(List<ActaLectura> actaLectura) {
		this.actaLectura = actaLectura;
	}

	public ActaLectura addActaLectura(ActaLectura actaLectura) {
		getActaLectura().add(actaLectura);
		actaLectura.setInforme(this);

		return actaLectura;
	}

	public ActaLectura removeActaLectura(ActaLectura actaLectura) {
		getActaLectura().remove(actaLectura);
		actaLectura.setInforme(null);

		return actaLectura;
	}

	public List<CedulaNota> getCedulaNotas() {
		return this.cedulaNotas;
	}

	public void setCedulaNotas(List<CedulaNota> cedulaNotas) {
		this.cedulaNotas = cedulaNotas;
	}

	public CedulaNota addCedulaNota(CedulaNota cedulaNota) {
		getCedulaNotas().add(cedulaNota);
		cedulaNota.setInforme(this);

		return cedulaNota;
	}

	public CedulaNota removeCedulaNota(CedulaNota cedulaNota) {
		getCedulaNotas().remove(cedulaNota);
		cedulaNota.setInforme(null);

		return cedulaNota;
	}

	public List<CartaGerencia> getCartaGerencia() {
		return cartaGerencia;
	}

	public void setCartaGerencia(List<CartaGerencia> cartaGerencia) {
		this.cartaGerencia = cartaGerencia;
	}

	public CartaGerencia addCartaGerencia(CartaGerencia cartaGerencia) {
		getCartaGerencia().add(cartaGerencia);
		cartaGerencia.setInforme(this);

		return cartaGerencia;
	}

	public CartaGerencia removeCartaGerencia(CartaGerencia cartaGerencia) {
		getCartaGerencia().remove(cartaGerencia);
		cartaGerencia.setInforme(null);

		return cartaGerencia;
	}

	public List<Convocatoria> getConvocatoria() {
		return convocatoria;
	}

	public void setConvocatoria(List<Convocatoria> convocatoria) {
		this.convocatoria = convocatoria;
	}

	public Convocatoria addConvocatoria(Convocatoria convocatoria) {
		getConvocatoria().add(convocatoria);
		convocatoria.setInforme(this);

		return convocatoria;
	}

	public Convocatoria removeConvocatoria(Convocatoria convocatoria) {
		getConvocatoria().remove(convocatoria);
		convocatoria.setInforme(null);

		return convocatoria;
	}

	public Actividad getActividad() {
		return this.actividad;
	}

	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}

	public Auditoria getAuditoria() {
		return this.auditoria;
	}

	public void setAuditoria(Auditoria auditoria) {
		this.auditoria = auditoria;
	}

	public String getInfObjetivos() {
		return infObjetivos;
	}

	public void setInfObjetivos(String infObjetivos) {
		this.infObjetivos = infObjetivos;
	}

	public String getInfAlcance() {
		return infAlcance;
	}

	public void setInfAlcance(String infAlcance) {
		this.infAlcance = infAlcance;
	}

	public String getInfObservacionesJefe() {
		return infObservacionesJefe;
	}

	public void setInfObservacionesJefe(String infObservacionesJefe) {
		this.infObservacionesJefe = infObservacionesJefe;
	}

	public List<Archivo> getArchivo() {
		return this.archivo;
	}

	public void setArchivo(List<Archivo> archivo) {
		this.archivo = archivo;
	}

	public String getInfIndice() {
		return infIndice;
	}

	public void setInfIndice(String infIndice) {
		this.infIndice = infIndice;
	}

	public Archivo addArchivo(Archivo archivo) {
		getArchivo().add(archivo);
		archivo.setInforme(this);

		return archivo;
	}

	public Archivo removeArchivo(Archivo archivo) {
		getArchivo().remove(archivo);
		archivo.setInforme(null);

		return archivo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + infId;
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
		Informe other = (Informe) obj;
		if (infId != other.infId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Informe [infId=" + infId + ", fecCrea=" + fecCrea + ", fecModi=" + fecModi + ", infAclaracion="
				+ infAclaracion + ", infConclusion=" + infConclusion + ", infEncabezado=" + infEncabezado
				+ ", infIntroduccion=" + infIntroduccion + ", infLogros=" + infLogros + ", infPiePagina=" + infPiePagina
				+ ", infPortada=" + infPortada + ", infDestinatario=" + infDestinatario + ", infProcedimientos="
				+ infProcedimientos + ", infRecomendaciones=" + infRecomendaciones + ", infObservaciones="
				+ infObservaciones + ", infResultados=" + infResultados + ", infSeguimiento=" + infSeguimiento
				+ ", infTitulo=" + infTitulo + ", infVersion=" + infVersion + ", infEstado=" + infEstado
				+ ", regActivo=" + regActivo + ", usuCrea=" + usuCrea + ", usuModi=" + usuModi + ", actaLectura="
				+ actaLectura + ", cartaGerencia=" + cartaGerencia + ", convocatoria=" + convocatoria + ", cedulaNotas="
				+ cedulaNotas + ", actividad=" + actividad + ", archivo=" + archivo + "]";
	}

}