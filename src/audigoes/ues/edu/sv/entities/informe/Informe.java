package audigoes.ues.edu.sv.entities.informe;

import java.io.Serializable;
import javax.persistence.*;

import audigoes.ues.edu.sv.entities.SuperEntity;
import audigoes.ues.edu.sv.entities.planificacion.Actividad;

import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the informe database table.
 * 
 */
@Entity
@NamedQuery(name="Informe.findAll", query="SELECT i FROM Informe i")
public class Informe extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "inf_id", schema = "audigoes", table = "contador", pkColumnName = "cnt_nombre", valueColumnName = "cnt_valor", pkColumnValue = "inf_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "inf_id")
	@Column(name="inf_id")
	private int infId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_crea")
	private Date fecCrea;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_modi")
	private Date fecModi;

	@Lob
	@Column(name="inf_aclaracion")
	private String infAclaracion;

	@Lob
	@Column(name="inf_conclusion")
	private String infConclusion;
	
	@Lob
	@Column(name="inf_encabezado")
	private String infEncabezado;

	@Lob
	@Column(name="inf_introduccion")
	private String infIntroduccion;

	@Lob
	@Column(name="inf_logros")
	private String infLogros;

	@Lob
	@Column(name="inf_pie_pagina")
	private String infPiePagina;

	@Lob
	@Column(name="inf_portada")
	private String infPortada;

	@Lob
	@Column(name="inf_procedimientos")
	private String infProcedimientos;

	@Lob
	@Column(name="inf_recomendaciones")
	private String infRecomendaciones;

	@Lob
	@Column(name="inf_resultados")
	private String infResultados;

	@Lob
	@Column(name="inf_seguimiento")
	private String infSeguimiento;

	@Column(name="inf_titulo")
	private String infTitulo;

	@Column(name="inf_version")
	private int infVersion;

	@Column(name="reg_activo")
	private int regActivo;

	@Column(name="usu_crea")
	private String usuCrea;

	@Column(name="usu_modi")
	private String usuModi;

	//bi-directional many-to-one association to ActaLectura
	@OneToMany(mappedBy="informe", fetch=FetchType.EAGER)
	private Set<ActaLectura> actaLectura;

	//bi-directional many-to-one association to CedulaNota
	@OneToMany(mappedBy="informe", fetch=FetchType.EAGER)
	private Set<CedulaNota> cedulaNotas;

	//bi-directional many-to-one association to Actividad
	@ManyToOne
	@JoinColumn(name="inf_act_id")
	private Actividad actividad;

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

	public Set<ActaLectura> getActaLectura() {
		return this.actaLectura;
	}

	public void setActaLectura(Set<ActaLectura> actaLectura) {
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

	public Set<CedulaNota> getCedulaNotas() {
		return this.cedulaNotas;
	}

	public void setCedulaNotas(Set<CedulaNota> cedulaNotas) {
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

	public Actividad getActividad() {
		return this.actividad;
	}

	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
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
		return "Informe [infId=" + infId + ", fecCrea=" + fecCrea + ", fecModi=" + fecModi 
				+ ", infAclaracion=" + infAclaracion + ", infConclusion=" + infConclusion + ", infEncabezado="
				+ infEncabezado + ", infIntroduccion=" + infIntroduccion + ", infLogros=" + infLogros
				+ ", infPiePagina=" + infPiePagina + ", infPortada=" + infPortada  + ", infProcedimientos="
				+ infProcedimientos + ", infRecomendaciones=" + infRecomendaciones + ", infResultados=" + infResultados
				+ ", infSeguimiento=" + infSeguimiento + ", infTitulo=" + infTitulo + ", infVersion=" + infVersion
				+ ", regActivo=" + regActivo + ", usuCrea=" + usuCrea + ", usuModi=" + usuModi + ", actaLectura="
				+ actaLectura + ", cedulaNotas=" + cedulaNotas + ", actividad=" + actividad + "]";
	}

}