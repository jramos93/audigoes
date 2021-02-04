package audigoes.ues.edu.sv.entities.informe;

import java.io.Serializable;
import javax.persistence.*;

import audigoes.ues.edu.sv.entities.SuperEntity;
import audigoes.ues.edu.sv.entities.administracion.Archivo;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the acta_lectura database table.
 * 
 */
@Entity
@Table(name="convocatoria")
@NamedQuery(name="Convocatoria.findAll", query="SELECT a FROM Convocatoria a")
public class Convocatoria extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "cvc_id", schema = "audigoes", table = "contador", pkColumnName = "cnt_nombre", valueColumnName = "cnt_valor", pkColumnValue = "cvc_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "cvc_id")
	@Column(name="cvc_id")
	private int cvcId;

	@Lob
	@Column(name="cvc_cuerpo")
	private String cvcCuerpo;

	@Column(name="cvc_ref")
	private String cvcRef;

	@Column(name="cvc_encabezado")
	private String cvcEncabezado;
	
	@Column(name="cvc_destinatario")
	private String cvcDestinatario;

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

	//bi-directional many-to-one association to Informe
	@ManyToOne
	@JoinColumn(name="cvc_inf_id")
	private Informe informe;

	//bi-directional many-to-one association to Destinatario
	@OneToMany(mappedBy="actaLectura")
	private List<Destinatario> destinatario;
	
	// bi-directional many-to-one association to Archivo
	@OneToMany(mappedBy = "convocatoria")
	private List<Archivo> archivo;
	
	@Column(name = "cvc_estado")
	private int cvcEstado;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="cvc_fecha_envio")
	private Date cvcFechaEnvio;

	public Convocatoria() {
	}

	public int getCvcId() {
		return this.cvcId;
	}

	public void setCvcId(int cvcId) {
		this.cvcId = cvcId;
	}

	public String getCvcCuerpo() {
		return this.cvcCuerpo;
	}

	public void setCvcCuerpo(String cvcCuerpo) {
		this.cvcCuerpo = cvcCuerpo;
	}

	public String getCvcRef() {
		return this.cvcRef;
	}

	public void setCvcRef(String cvcRef) {
		this.cvcRef = cvcRef;
	}

	public String getCvcEncabezado() {
		return this.cvcEncabezado;
	}

	public void setCvcEncabezado(String cvcEncabezado) {
		this.cvcEncabezado = cvcEncabezado;
	}
	
	public String getCvcDestinatario() {
		return this.cvcDestinatario;
	}

	public void setCvcDestinatario(String cvcDestinatario) {
		this.cvcDestinatario = cvcDestinatario;
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

	public Informe getInforme() {
		return this.informe;
	}

	public void setInforme(Informe informe) {
		this.informe = informe;
	}

	public Date getCvcFechaEnvio() {
		return cvcFechaEnvio;
	}

	public void setCvcFechaEnvio(Date cvcFechaEnvio) {
		this.cvcFechaEnvio = cvcFechaEnvio;
	}

	public List<Destinatario> getDestinatario() {
		return this.destinatario;
	}

	public void setDestinatario(List<Destinatario> destinatario) {
		this.destinatario = destinatario;
	}

	public Destinatario addDestinatario(Destinatario destinatario) {
		getDestinatario().add(destinatario);
		destinatario.setConvocatoria(this);

		return destinatario;
	}

	public Destinatario removeDestinatario(Destinatario destinatario) {
		getDestinatario().remove(destinatario);
		destinatario.setConvocatoria(null);

		return destinatario;
	}
	
	public List<Archivo> getArchivo() {
		return this.archivo;
	}

	public void setArchivo(List<Archivo> archivo) {
		this.archivo = archivo;
	}

	public Archivo addArchivo(Archivo archivo) {
		getArchivo().add(archivo);
		archivo.setConvocatoria(this);

		return archivo;
	}

	public int getCvcEstado() {
		return cvcEstado;
	}

	public void setCvcEstado(int cvcEstado) {
		this.cvcEstado = cvcEstado;
	}

	public Archivo removeArchivo(Archivo archivo) {
		getArchivo().remove(archivo);
		archivo.setConvocatoria(null);

		return archivo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cvcId;
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
		Convocatoria other = (Convocatoria) obj;
		if (cvcId != other.cvcId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Convocatoria [cvcId=" + cvcId + ", cvcCuerpo=" + cvcCuerpo 
				+ ", cvcRef=" + cvcRef + ", cvcEncabezado=" + cvcEncabezado + ", cvcDestinatario=" + cvcDestinatario + ", fecCrea=" + fecCrea + ", fecModi=" + fecModi
				+ ", regActivo=" + regActivo + ", usuCrea=" + usuCrea + ", usuModi=" + usuModi + ", informe=" + informe
				+ ", destinatario=" + destinatario + ", archivo=" + archivo +"]";
	}

}