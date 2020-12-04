package audigoes.ues.edu.sv.entities.informe;

import java.io.Serializable;
import javax.persistence.*;

import audigoes.ues.edu.sv.entities.SuperEntity;
import audigoes.ues.edu.sv.entities.administracion.Archivo;

import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the acta_lectura database table.
 * 
 */
@Entity
@Table(name="acta_lectura")
@NamedQuery(name="ActaLectura.findAll", query="SELECT a FROM ActaLectura a")
public class ActaLectura extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "acl_id", schema = "audigoes", table = "contador", pkColumnName = "cnt_nombre", valueColumnName = "cnt_valor", pkColumnValue = "acl_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "acl_id")
	@Column(name="acl_id")
	private int aclId;

	@Lob
	@Column(name="acl_cuerpo")
	private String aclCuerpo;

	@Temporal(TemporalType.DATE)
	@Column(name="acl_fec_enviada")
	private Date aclFecEnviada;

	@Column(name="acl_ref")
	private String aclRef;

	@Column(name="acl_encabezado")
	private String aclEncabezado;

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
	@JoinColumn(name="acl_inf_id")
	private Informe informe;

	//bi-directional many-to-one association to Destinatario
	@OneToMany(mappedBy="actaLectura", fetch=FetchType.EAGER)
	private Set<Destinatario> destinatario;
	
	// bi-directional many-to-one association to Archivo
	@OneToMany(mappedBy = "actaLectura", fetch = FetchType.EAGER)
	private Set<Archivo> archivo;

	public ActaLectura() {
	}

	public int getAclId() {
		return this.aclId;
	}

	public void setAclId(int aclId) {
		this.aclId = aclId;
	}

	public String getAclCuerpo() {
		return this.aclCuerpo;
	}

	public void setAclCuerpo(String aclCuerpo) {
		this.aclCuerpo = aclCuerpo;
	}

	public Date getAclFecEnviada() {
		return this.aclFecEnviada;
	}

	public void setAclFecEnviada(Date aclFecEnviada) {
		this.aclFecEnviada = aclFecEnviada;
	}

	public String getAclRef() {
		return this.aclRef;
	}

	public void setAclRef(String aclRef) {
		this.aclRef = aclRef;
	}

	public String getAclEncabezado() {
		return this.aclEncabezado;
	}

	public void setAclEncabezado(String aclEncabezado) {
		this.aclEncabezado = aclEncabezado;
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

	public Set<Destinatario> getDestinatario() {
		return this.destinatario;
	}

	public void setDestinatario(Set<Destinatario> destinatario) {
		this.destinatario = destinatario;
	}

	public Destinatario addDestinatario(Destinatario destinatario) {
		getDestinatario().add(destinatario);
		destinatario.setActaLectura(this);

		return destinatario;
	}

	public Destinatario removeDestinatario(Destinatario destinatario) {
		getDestinatario().remove(destinatario);
		destinatario.setActaLectura(null);

		return destinatario;
	}
	
	public Set<Archivo> getArchivo() {
		return this.archivo;
	}

	public void setArchivo(Set<Archivo> archivo) {
		this.archivo = archivo;
	}

	public Archivo addArchivo(Archivo archivo) {
		getArchivo().add(archivo);
		archivo.setActaLectura(this);

		return archivo;
	}

	public Archivo removeArchivo(Archivo archivo) {
		getArchivo().remove(archivo);
		archivo.setActaLectura(null);

		return archivo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + aclId;
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
		ActaLectura other = (ActaLectura) obj;
		if (aclId != other.aclId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ActaLectura [aclId=" + aclId + ", aclCuerpo=" + aclCuerpo + ", aclFecEnviada=" + aclFecEnviada
				+ ", aclRef=" + aclRef + ", aclEncabezado=" + aclEncabezado + ", fecCrea=" + fecCrea + ", fecModi=" + fecModi
				+ ", regActivo=" + regActivo + ", usuCrea=" + usuCrea + ", usuModi=" + usuModi + ", informe=" + informe
				+ ", destinatario=" + destinatario + ", archivo=" + archivo
				+ "]";
	}

}