package audigoes.ues.edu.sv.entities.administracion;

import java.io.Serializable;
import javax.persistence.*;

import audigoes.ues.edu.sv.entities.SuperEntity;
import audigoes.ues.edu.sv.entities.ejecucion.ProcedimientoEjecucion;
import audigoes.ues.edu.sv.entities.informe.ActaLectura;
import audigoes.ues.edu.sv.entities.informe.CartaGerencia;
import audigoes.ues.edu.sv.entities.informe.Convocatoria;
import audigoes.ues.edu.sv.entities.informe.Informe;
import audigoes.ues.edu.sv.entities.planeacion.PlanAnual;
import audigoes.ues.edu.sv.entities.planificacion.ProcedimientoPlanificacion;

import java.util.Arrays;
import java.util.Date;


/**
 * The persistent class for the archivo database table.
 * 
 */
@Entity
@NamedQuery(name="Archivo.findAll", query="SELECT a FROM Archivo a")
public class Archivo extends SuperEntity implements Serializable  {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "arc_id", schema = "audigoes", table = "contador", pkColumnName = "cnt_nombre", valueColumnName = "cnt_valor", pkColumnValue = "mar_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "arc_id")
	@Column(name="arc_id")
	private int arcId;

	@Lob
	@Column(name="arc_archivo")
	private byte[] arcArchivo;

	@Column(name="arc_ext")
	private String arcExt;

	@Column(name="arc_nombre")
	private String arcNombre;

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

	//bi-directional many-to-one association to PlanAnual
	@ManyToOne
	@JoinColumn(name="arc_pla_id")
	private PlanAnual planAnual;

	//bi-directional many-to-one association to ProcedimientoEjecucion
	@ManyToOne
	@JoinColumn(name="arc_pej_id")
	private ProcedimientoEjecucion procedimientoEjecucion;
	
	//bi-directional many-to-one association to ProcedimientoPlanificacion
	@ManyToOne
	@JoinColumn(name="arc_pro_id")
	private ProcedimientoPlanificacion procedimientoPlanificacion;

	//bi-directional many-to-one association to Informe
	@ManyToOne
	@JoinColumn(name="arc_inf_id")
	private Informe informe;
	
	//bi-directional many-to-one association to Convocatoria
	@ManyToOne
	@JoinColumn(name="arc_cvc_id")
	private Convocatoria convocatoria;
	
	//bi-directional many-to-one association to Carta a la Gerencia
	@ManyToOne
	@JoinColumn(name="arc_ctg_id")
	private CartaGerencia cartaGerencia;
	
	//bi-directional many-to-one association to Acta Lectura
	@ManyToOne
	@JoinColumn(name="arc_acl_id")
	private ActaLectura actaLectura;
	
	public Archivo() {
	}

	public int getArcId() {
		return this.arcId;
	}

	public void setArcId(int arcId) {
		this.arcId = arcId;
	}

	public byte[] getArcArchivo() {
		return this.arcArchivo;
	}

	public void setArcArchivo(byte[] arcArchivo) {
		this.arcArchivo = arcArchivo;
	}

	public String getArcExt() {
		return this.arcExt;
	}

	public void setArcExt(String arcExt) {
		this.arcExt = arcExt;
	}

	public String getArcNombre() {
		return this.arcNombre;
	}

	public void setArcNombre(String arcNombre) {
		this.arcNombre = arcNombre;
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

	public PlanAnual getPlanAnual() {
		return this.planAnual;
	}

	public void setPlanAnual(PlanAnual planAnual) {
		this.planAnual = planAnual;
	}

	public ProcedimientoEjecucion getProcedimientoEjecucion() {
		return this.procedimientoEjecucion;
	}

	public void setProcedimientoEjecucion(ProcedimientoEjecucion procedimientoEjecucion) {
		this.procedimientoEjecucion = procedimientoEjecucion;
	}

	public ProcedimientoPlanificacion getProcedimientoPlanificacion() {
		return this.procedimientoPlanificacion;
	}

	public void setProcedimientoPlanificacion(ProcedimientoPlanificacion procedimientoPlanificacion) {
		this.procedimientoPlanificacion = procedimientoPlanificacion;
	}
	
	public Informe getInforme() {
		return informe;
	}

	public void setInforme(Informe informe) {
		this.informe = informe;
	}

	public Convocatoria getConvocatoria() {
		return convocatoria;
	}

	public void setConvocatoria(Convocatoria convocatoria) {
		this.convocatoria = convocatoria;
	}

	public CartaGerencia getCartaGerencia() {
		return cartaGerencia;
	}

	public void setCartaGerencia(CartaGerencia cartaGerencia) {
		this.cartaGerencia = cartaGerencia;
	}

	public ActaLectura getActaLectura() {
		return actaLectura;
	}

	public void setActaLectura(ActaLectura actaLectura) {
		this.actaLectura = actaLectura;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + arcId;
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
		Archivo other = (Archivo) obj;
		if (arcId != other.arcId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Archivo [arcId=" + arcId + ", arcArchivo=" + Arrays.toString(arcArchivo) + ", arcExt=" + arcExt
				+ ", arcNombre=" + arcNombre + ", fecCrea=" + fecCrea + ", fecModi=" + fecModi + ", regActivo="
				+ regActivo + ", usuCrea=" + usuCrea + ", usuModi=" + usuModi + ", planAnual=" + planAnual
				+ ", procedimientoEjecucion=" + procedimientoEjecucion + ", procedimientoPlanificacion="
				+ procedimientoPlanificacion + ", informe=" + informe
				+ ", cartaGerencia=" + cartaGerencia + ", actaLectura=" + actaLectura 
				+ ", convocatoria=" + convocatoria +"]";
	}
	

}