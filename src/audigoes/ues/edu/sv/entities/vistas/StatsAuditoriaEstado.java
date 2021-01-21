package audigoes.ues.edu.sv.entities.vistas;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;


/**
 * The persistent class for the vw_stats_auditorias_estados database table.
 * 
 */
@Entity
@SqlResultSetMapping(name = "StatsAuditoriaEstado", entities = {@EntityResult(entityClass = StatsAuditoriaEstado.class, fields = {
		@FieldResult(name = "total", column = "TOTAL"), @FieldResult(name = "programada", column = "PROGRAMADA"),
		@FieldResult(name = "asignada", column = "ASIGNADA"), @FieldResult(name = "planificacion", column = "PLANIFICACION"), 
		@FieldResult(name = "ejecucion", column = "EJECUCION"), @FieldResult(name = "informe", column = "INFORME"), 
		@FieldResult(name = "seguimiento", column = "SEGUIMIENTO"), @FieldResult(name = "finalizada", column = "FINALIZADA")
})})
public class StatsAuditoriaEstado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="total")
	private String total;

	@Column(name="programada")
	private BigInteger programada;
	
	@Column(name="asignada")
	private BigInteger asignada;
	
	@Column(name="planificacion")
	private BigInteger planificacion;
	
	@Column(name="ejecucion")
	private BigInteger ejecucion;
	
	@Column(name="informe")
	private BigInteger informe;
	
	@Column(name="seguimiento")
	private BigInteger seguimiento;
	
	@Column(name="finalizada")
	private BigInteger finalizada;

	public StatsAuditoriaEstado() {
	}

	public String getTotal() {
		return this.total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public BigInteger getProgramada() {
		return programada;
	}

	public void setProgramada(BigInteger programada) {
		this.programada = programada;
	}

	public BigInteger getAsignada() {
		return asignada;
	}

	public void setAsignada(BigInteger asignada) {
		this.asignada = asignada;
	}

	public BigInteger getPlanificacion() {
		return planificacion;
	}

	public void setPlanificacion(BigInteger planificacion) {
		this.planificacion = planificacion;
	}

	public BigInteger getEjecucion() {
		return ejecucion;
	}

	public void setEjecucion(BigInteger ejecucion) {
		this.ejecucion = ejecucion;
	}

	public BigInteger getInforme() {
		return informe;
	}

	public void setInforme(BigInteger informe) {
		this.informe = informe;
	}

	public BigInteger getSeguimiento() {
		return seguimiento;
	}

	public void setSeguimiento(BigInteger seguimiento) {
		this.seguimiento = seguimiento;
	}

	public BigInteger getFinalizada() {
		return finalizada;
	}

	public void setFinalizada(BigInteger finalizada) {
		this.finalizada = finalizada;
	}

}