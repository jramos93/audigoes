package audigoes.ues.edu.sv.entities.vistas;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;


/**
 * The persistent class for the vw_stats_auditorias_estados database table.
 * 
 */
@Entity
@SqlResultSetMapping(name = "StatsHallazgoEstado", entities = {@EntityResult(entityClass = StatsHallazgoEstado.class, fields = {
		@FieldResult(name = "total", column = "TOTAL"), @FieldResult(name = "redaccion", column = "REDACCION"),
		@FieldResult(name = "revision", column = "REVISION"), @FieldResult(name = "comunicar", column = "COMUNICAR"),
		@FieldResult(name = "comunicado", column = "COMUNICADO"), @FieldResult(name = "analisis", column = "ANALISIS"),
		@FieldResult(name = "finalizado", column = "FINALIZADO")
})})
public class StatsHallazgoEstado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="total")
	private String total;

	@Column(name="redaccion")
	private BigInteger redaccion;
	
	@Column(name="revision")
	private BigInteger revision;
	
	@Column(name="comunicar")
	private BigInteger comunicar;
	
	@Column(name="comunicado")
	private BigInteger comunicado;
	
	@Column(name="analisis")
	private BigInteger analisis;
	
	@Column(name="finalizado")
	private BigInteger finalizado;

	public StatsHallazgoEstado() {
	}

	public String getTotal() {
		return this.total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public BigInteger getRedaccion() {
		return redaccion;
	}

	public BigInteger getRevision() {
		return revision;
	}

	public BigInteger getComunicar() {
		return comunicar;
	}

	public BigInteger getComunicado() {
		return comunicado;
	}

	public BigInteger getAnalisis() {
		return analisis;
	}

	public BigInteger getFinalizado() {
		return finalizado;
	}

	public void setRedaccion(BigInteger redaccion) {
		this.redaccion = redaccion;
	}

	public void setRevision(BigInteger revision) {
		this.revision = revision;
	}

	public void setComunicar(BigInteger comunicar) {
		this.comunicar = comunicar;
	}

	public void setComunicado(BigInteger comunicado) {
		this.comunicado = comunicado;
	}

	public void setAnalisis(BigInteger analisis) {
		this.analisis = analisis;
	}

	public void setFinalizado(BigInteger finalizado) {
		this.finalizado = finalizado;
	}
	
}