package audigoes.ues.edu.sv.mbeans.administracion;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.administracion.Institucion;

@ManagedBean(name = "insMB")
@ViewScoped
public class InstitucionMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Institucion> filteredInstituciones;

	public InstitucionMB() {
		super(new Institucion());
	}

	@PostConstruct
	public void init() {
		try {
			super.init();
			fillListado();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public void fillListado() {
		try {
			setListado((List<Institucion>) audigoesLocal.findByNamedQuery(Institucion.class, "institucion.all", new Object[] {}));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
		if (filterText == null || filterText.equals("")) {
			return true;
		}
		int filterInt = getInteger(filterText);

		Institucion institucion = (Institucion) value;
		return institucion.getInsNombre().toLowerCase().contains(filterText)
				||institucion.getInsIniciales().toLowerCase().contains(filterText)
				|| institucion.getInsId() == filterInt;
	}

	private int getInteger(String string) {
		try {
			return Integer.valueOf(string);
		} catch (NumberFormatException ex) {
			return 0;
		}
	}

	/* GETS y SETS */

	@Override
	public Institucion getRegistro() {
		return (Institucion) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Institucion> getListado() {
		return (List<Institucion>) super.getListado();
	}

	@Override
	public void afterSaveNew() {
		getListado().add(getRegistro());
		super.afterSaveNew();
	}

	public List<Institucion> getFilteredInstituciones() {
		return filteredInstituciones;
	}

	public void setFilteredInstituciones(List<Institucion> filteredInstituciones) {
		this.filteredInstituciones = filteredInstituciones;
	}
}
