package audigoes.ues.edu.sv.mbeans.administracion;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.administracion.Marca;


@ManagedBean(name = "marMB")
@ViewScoped
public class MarcaMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Marca> filteredMarcas;

	public MarcaMB() {
		super(new Marca());
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
			setListado((List<Marca>) audigoesLocal.findByNamedQuery(Marca.class, "origenauditoria.all", new Object[] {}));
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

		Marca Marca = (Marca) value;
		return Marca.getMarNombre().toLowerCase().contains(filterText)
				|| Marca.getMarDescripcion().toLowerCase().contains(filterText)
				|| Marca.getMarId() == filterInt;
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
	public Marca getRegistro() {
		return (Marca) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Marca> getListado() {
		return (List<Marca>) super.getListado();
	}

	@Override
	public void afterSaveNew() {
		getListado().add(getRegistro());
		super.afterSaveNew();
	}

	public List<Marca> getFilteredMarcas() {
		return filteredMarcas;
	}

	public void setFilteredMarcas(List<Marca> filteredMarcas) {
		this.filteredMarcas = filteredMarcas;
	}


}
