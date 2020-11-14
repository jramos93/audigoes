package audigoes.ues.edu.sv.entities.informe;

import java.io.Serializable;
import javax.persistence.*;

import audigoes.ues.edu.sv.entities.SuperEntity;

import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the acta_lectura database table.
 * 
 */
@Entity
@Table(name="carta_gerencia")
@NamedQuery(name="CartaGerencia.findAll", query="SELECT a FROM CartaGerencia a")
public class CartaGerencia extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "ctg_id", schema = "audigoes", table = "contador", pkColumnName = "cnt_nombre", valueColumnName = "cnt_valor", pkColumnValue = "ctg_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ctg_id")
	@Column(name="ctg_id")
	private int ctgId;

	@Lob
	@Column(name="ctg_cuerpo")
	private String ctgCuerpo;

	@Column(name="ctg_ref")
	private String ctgRef;

	@Column(name="ctg_encabezado")
	private String ctgEncabezado;
	
	@Column(name="ctg_destinatario")
	private String ctgDestinatario;

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
	@JoinColumn(name="ctg_inf_id")
	private Informe informe;

	//bi-directional many-to-one association to Destinatario
	@OneToMany(mappedBy="cartaGerencia", fetch=FetchType.EAGER)
	private Set<Destinatario> destinatario;

	public CartaGerencia() {
	}

	public int getCtgId() {
		return this.ctgId;
	}

	public void setCtgId(int ctgId) {
		this.ctgId = ctgId;
	}

	public String getCtgCuerpo() {
		return this.ctgCuerpo;
	}

	public void setCtgCuerpo(String ctgCuerpo) {
		this.ctgCuerpo = ctgCuerpo;
	}

	public String getCtgRef() {
		return this.ctgRef;
	}

	public void setCtgRef(String ctgRef) {
		this.ctgRef = ctgRef;
	}

	public String getCtgEncabezado() {
		return this.ctgEncabezado;
	}

	public void setCtgEncabezado(String ctgEncabezado) {
		this.ctgEncabezado = ctgEncabezado;
	}
	
	public String getCtgDestinatario() {
		return this.ctgDestinatario;
	}

	public void setCtgDestinatario(String ctgDestinatario) {
		this.ctgDestinatario = ctgDestinatario;
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
		destinatario.setCartaGerencia(this);

		return destinatario;
	}

	public Destinatario removeDestinatario(Destinatario destinatario) {
		getDestinatario().remove(destinatario);
		destinatario.setConvocatoria(null);

		return destinatario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ctgId;
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
		CartaGerencia other = (CartaGerencia) obj;
		if (ctgId != other.ctgId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Convocatoria [ctgId=" + ctgId + ", ctgCuerpo=" + ctgCuerpo 
				+ ", ctgRef=" + ctgRef + ", ctgEncabezado=" + ctgEncabezado + ", ctgDestinatario=" + ctgDestinatario + ", fecCrea=" + fecCrea + ", fecModi=" + fecModi
				+ ", regActivo=" + regActivo + ", usuCrea=" + usuCrea + ", usuModi=" + usuModi + ", informe=" + informe
				+ ", ctgDestinatario=" + ctgDestinatario + "]";
	}

}