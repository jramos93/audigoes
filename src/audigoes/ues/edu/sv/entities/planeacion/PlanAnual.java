package audigoes.ues.edu.sv.entities.planeacion;

import java.io.Serializable;
import javax.persistence.*;

import audigoes.ues.edu.sv.entities.SuperEntity;
import audigoes.ues.edu.sv.entities.administracion.Institucion;

import java.util.Date;
import java.util.Set;

/**
 * The persistent class for the plan_anual database table.
 * 
 */
@Entity
@Table(name = "plan_anual")
@NamedQuery(name = "PlanAnual.findAll", query = "SELECT p FROM PlanAnual p")
public class PlanAnual extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "pla_id", schema = "audigoes", table = "contador", pkColumnName = "cnt_nombre", valueColumnName = "cnt_valor", pkColumnValue = "pla_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "pla_id")
	@Column(name = "pla_id")
	private int plaId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fec_crea")
	private Date fecCrea;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fec_modi")
	private Date fecModi;

	@Column(name = "pla_anio")
	private String plaAnio;

	@Column(name = "pla_descripcion")
	private String plaDescripcion;

	@Temporal(TemporalType.DATE)
	@Column(name = "pla_fecha_fin")
	private Date plaFechaFin;

	@Temporal(TemporalType.DATE)
	@Column(name = "pla_fecha_inicio")
	private Date plaFechaInicio;

	@Lob
	@Column(name = "pla_indice")
	private String plaIndice;

	@Lob
	@Column(name = "pla_introduccion")
	private String plaIntroduccion;

	@Column(name = "pla_lugar_fecha")
	private String plaLugarFecha;

	@Lob
	@Column(name = "pla_mision")
	private String plaMision;

	@Column(name = "pla_nombre")
	private String plaNombre;

	@Lob
	@Column(name = "pla_objetivos")
	private String plaObjetivos;

	@Lob
	@Column(name = "pla_principios_valores")
	private String plaPrincipiosValores;

	@Lob
	@Column(name = "pla_riesgos_considerados")
	private String plaRiesgosConsiderados;

	@Lob
	@Column(name = "pla_vision")
	private String plaVision;

	@Column(name = "reg_activo")
	private int regActivo;

	@Column(name = "usu_crea")
	private String usuCrea;

	@Column(name = "usu_modi")
	private String usuModi;

	// bi-directional many-to-one association to Auditoria
	@OneToMany(mappedBy = "planAnual", fetch = FetchType.EAGER)
	private Set<Auditoria> auditoria;

	// bi-directional many-to-one association to DocumentoPlan
	@OneToMany(mappedBy = "planAnual", fetch = FetchType.EAGER)
	private Set<DocumentoPlan> documentoPlan;

	// bi-directional many-to-one association to Institucion
	@ManyToOne
	@JoinColumn(name = "pla_ins_id")
	private Institucion institucion;

	public PlanAnual() {
	}

	public int getPlaId() {
		return this.plaId;
	}

	public void setPlaId(int plaId) {
		this.plaId = plaId;
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

	public String getPlaAnio() {
		return this.plaAnio;
	}

	public void setPlaAnio(String plaAnio) {
		this.plaAnio = plaAnio;
	}

	public String getPlaDescripcion() {
		return this.plaDescripcion;
	}

	public void setPlaDescripcion(String plaDescripcion) {
		this.plaDescripcion = plaDescripcion;
	}

	public Date getPlaFechaFin() {
		return this.plaFechaFin;
	}

	public void setPlaFechaFin(Date plaFechaFin) {
		this.plaFechaFin = plaFechaFin;
	}

	public Date getPlaFechaInicio() {
		return this.plaFechaInicio;
	}

	public void setPlaFechaInicio(Date plaFechaInicio) {
		this.plaFechaInicio = plaFechaInicio;
	}

	public String getPlaIndice() {
		return this.plaIndice;
	}

	public void setPlaIndice(String plaIndice) {
		this.plaIndice = plaIndice;
	}

	public String getPlaIntroduccion() {
		return this.plaIntroduccion;
	}

	public void setPlaIntroduccion(String plaIntroduccion) {
		this.plaIntroduccion = plaIntroduccion;
	}

	public String getPlaLugarFecha() {
		return this.plaLugarFecha;
	}

	public void setPlaLugarFecha(String plaLugarFecha) {
		this.plaLugarFecha = plaLugarFecha;
	}

	public String getPlaMision() {
		return this.plaMision;
	}

	public void setPlaMision(String plaMision) {
		this.plaMision = plaMision;
	}

	public String getPlaNombre() {
		return this.plaNombre;
	}

	public void setPlaNombre(String plaNombre) {
		this.plaNombre = plaNombre;
	}

	public String getPlaObjetivos() {
		return this.plaObjetivos;
	}

	public void setPlaObjetivos(String plaObjetivos) {
		this.plaObjetivos = plaObjetivos;
	}

	public String getPlaPrincipiosValores() {
		return this.plaPrincipiosValores;
	}

	public void setPlaPrincipiosValores(String plaPrincipiosValores) {
		this.plaPrincipiosValores = plaPrincipiosValores;
	}

	public String getPlaRiesgosConsiderados() {
		return this.plaRiesgosConsiderados;
	}

	public void setPlaRiesgosConsiderados(String plaRiesgosConsiderados) {
		this.plaRiesgosConsiderados = plaRiesgosConsiderados;
	}

	public String getPlaVision() {
		return this.plaVision;
	}

	public void setPlaVision(String plaVision) {
		this.plaVision = plaVision;
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

	public Set<Auditoria> getAuditoria() {
		return this.auditoria;
	}

	public void setAuditoria(Set<Auditoria> auditoria) {
		this.auditoria = auditoria;
	}

	public Auditoria addAuditoria(Auditoria auditoria) {
		getAuditoria().add(auditoria);
		auditoria.setPlanAnual(this);

		return auditoria;
	}

	public Auditoria removeAuditoria(Auditoria auditoria) {
		getAuditoria().remove(auditoria);
		auditoria.setPlanAnual(null);

		return auditoria;
	}

	public Set<DocumentoPlan> getDocumentoPlan() {
		return this.documentoPlan;
	}

	public void setDocumentoPlan(Set<DocumentoPlan> documentoPlan) {
		this.documentoPlan = documentoPlan;
	}

	public DocumentoPlan addDocumentoPlan(DocumentoPlan documentoPlan) {
		getDocumentoPlan().add(documentoPlan);
		documentoPlan.setPlanAnual(this);

		return documentoPlan;
	}

	public DocumentoPlan removeDocumentoPlan(DocumentoPlan documentoPlan) {
		getDocumentoPlan().remove(documentoPlan);
		documentoPlan.setPlanAnual(null);

		return documentoPlan;
	}
	
	public Institucion getInstitucion() {
		return this.institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + plaId;
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
		PlanAnual other = (PlanAnual) obj;
		if (plaId != other.plaId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PlanAnual [plaId=" + plaId + ", fecCrea=" + fecCrea + ", fecModi=" + fecModi + ", plaAnio=" + plaAnio
				+ ", plaDescripcion=" + plaDescripcion + ", plaFechaFin=" + plaFechaFin + ", plaFechaInicio="
				+ plaFechaInicio + ", plaIndice=" + plaIndice + ", plaIntroduccion=" + plaIntroduccion
				+ ", plaLugarFecha=" + plaLugarFecha + ", plaMision=" + plaMision + ", plaNombre=" + plaNombre
				+ ", plaObjetivos=" + plaObjetivos + ", plaPrincipiosValores=" + plaPrincipiosValores
				+ ", plaRiesgosConsiderados=" + plaRiesgosConsiderados + ", plaVision=" + plaVision + ", regActivo="
				+ regActivo + ", usuCrea=" + usuCrea + ", usuModi=" + usuModi + ", auditoria=" + auditoria
				+ ", documentoPlan=" + documentoPlan + "]";
	}

}