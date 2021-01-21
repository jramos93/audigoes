package audigoes.ues.edu.sv.entities.vistas;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;


/**
 * The persistent class for the vw_stats_auditorias_estados database table.
 * 
 */
@Entity
@SqlResultSetMapping(name = "DuracionAuditorias", entities = {@EntityResult(entityClass = DuracionAuditorias.class, 
fields = {
		@FieldResult(name = "aud_id", column = "aud_id"),
		@FieldResult(name = "aud_codigo", column = "AUD_CODIGO"),
		@FieldResult(name = "tiempo_ejecutado", column = "TIEMPO_EJECUTADO"),
})})
public class DuracionAuditorias implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="aud_id")
	private BigInteger audId;
	
	@Column(name="aud_codigo")
	private String audCodigo;
	
	@Column(name="tiempo_ejecutado")
	private BigInteger tiempoEjecutado;

	public DuracionAuditorias() {
	}

	public BigInteger getAudId() {
		return audId;
	}

	public void setAudId(BigInteger audId) {
		this.audId = audId;
	}

	public String getAudCodigo() {
		return audCodigo;
	}

	public BigInteger getTiempoEjecutado() {
		return tiempoEjecutado;
	}

	public void setAudCodigo(String audCodigo) {
		this.audCodigo = audCodigo;
	}

	public void setTiempoEjecutado(BigInteger tiempoEjecutado) {
		this.tiempoEjecutado = tiempoEjecutado;
	}

}