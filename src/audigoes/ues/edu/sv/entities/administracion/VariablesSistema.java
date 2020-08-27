package audigoes.ues.edu.sv.entities.administracion;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the variables_sistema database table.
 * 
 */
@Entity
@Table(name="variables_sistema")
@NamedQuery(name="VariablesSistema.findAll", query="SELECT v FROM VariablesSistema v")
public class VariablesSistema implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="var_id")
	private int varId;

	@Column(name="var_descripcion")
	private String varDescripcion;

	@Column(name="var_nombre")
	private String varNombre;

	//bi-directional many-to-one association to Configuracion
	@OneToMany(mappedBy="variablesSistema")
	private List<Configuracion> configuracion;

	public VariablesSistema() {
	}

	public int getVarId() {
		return this.varId;
	}

	public void setVarId(int varId) {
		this.varId = varId;
	}

	public String getVarDescripcion() {
		return this.varDescripcion;
	}

	public void setVarDescripcion(String varDescripcion) {
		this.varDescripcion = varDescripcion;
	}

	public String getVarNombre() {
		return this.varNombre;
	}

	public void setVarNombre(String varNombre) {
		this.varNombre = varNombre;
	}

	public List<Configuracion> getConfiguracion() {
		return this.configuracion;
	}

	public void setConfiguracion(List<Configuracion> configuracion) {
		this.configuracion = configuracion;
	}

	public Configuracion addConfiguracion(Configuracion configuracion) {
		getConfiguracion().add(configuracion);
		configuracion.setVariablesSistema(this);

		return configuracion;
	}

	public Configuracion removeConfiguracion(Configuracion configuracion) {
		getConfiguracion().remove(configuracion);
		configuracion.setVariablesSistema(null);

		return configuracion;
	}

}