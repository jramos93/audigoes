package audigoes.ues.edu.sv.entities.vistas;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;


/**
 * The persistent class for the vw_stats_auditorias_estados database table.
 * 
 */
@Entity
@SqlResultSetMapping(name = "TiempoPlanificadoVsTiempoEjecutado", entities = {@EntityResult(entityClass = TiempoPlanificadoVsTiempoEjecutado.class, fields = {
		@FieldResult(name = "aud_id", column = "aud_id"), @FieldResult(name = "ins_id", column = "INS_ID"),
		@FieldResult(name = "ins_nombre", column = "INS_NOMBRE"), @FieldResult(name = "aud_codigo", column = "AUD_CODIGO"),
		@FieldResult(name = "aud_nombre", column = "AUD_NOMBRE"), @FieldResult(name = "tiempo_programado", column = "TIEMPO_PROGRAMADO"),
		@FieldResult(name = "tiempo_ejecutado", column = "TIEMPO_EJECUTADO")
})})
public class TiempoPlanificadoVsTiempoEjecutado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="aud_id")
	private BigInteger audId;

	@Column(name="ins_id")
	private BigInteger insId;
	
	@Column(name="ins_nombre")
	private String insNombre;
	
	@Column(name="aud_codigo")
	private String audCodigo;
	
	@Column(name="aud_nombre ")
	private String audNombre;
	
	@Column(name="tiempo_programado")
	private BigInteger tiempoProgramado;
	
	@Column(name="tiempo_ejecutado")
	private BigInteger tiempoEjecutado;

	public TiempoPlanificadoVsTiempoEjecutado() {
	}

	public BigInteger getAudId() {
		return audId;
	}

	public BigInteger getInsId() {
		return insId;
	}

	public String getInsNombre() {
		return insNombre;
	}

	public String getAudCodigo() {
		return audCodigo;
	}

	public String getAudNombre() {
		return audNombre;
	}

	public BigInteger getTiempoProgramado() {
		return tiempoProgramado;
	}

	public void setAudId(BigInteger audId) {
		this.audId = audId;
	}

	public void setInsId(BigInteger insId) {
		this.insId = insId;
	}

	public void setInsNombre(String insNombre) {
		this.insNombre = insNombre;
	}

	public void setAudCodigo(String audCodigo) {
		this.audCodigo = audCodigo;
	}

	public void setAudNombre(String audNombre) {
		this.audNombre = audNombre;
	}

	public void setTiempoProgramado(BigInteger tiempoProgramado) {
		this.tiempoProgramado = tiempoProgramado;
	}

	public BigInteger getTiempoEjecutado() {
		return tiempoEjecutado;
	}

	public void setTiempoEjecutado(BigInteger tiempoEjecutado) {
		this.tiempoEjecutado = tiempoEjecutado;
	}

}