package audigoes.ues.edu.sv.mbeans.administracion;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.administracion.Criterio;
import audigoes.ues.edu.sv.entities.administracion.NormativaCedula;

@ManagedBean(name = "criMB")
@ViewScoped
public class CriterioMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Criterio> filteredCriterios;

	private List<NormativaCedula> normativasList;

	public CriterioMB() {
		super(new Criterio());
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
			setListado(
					(List<Criterio>) audigoesLocal.findByNamedQuery(Criterio.class, "criterio.all", new Object[] {}));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void fillNormativasList() {
		try {
			setNormativasList((List<NormativaCedula>) audigoesLocal.findByNamedQuery(NormativaCedula.class,
					"normativacedula.all", new Object[] {}));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean beforeNew() {
		fillNormativasList();
		return super.beforeNew();
	}

	@Override
	public boolean beforeEdit() {
		fillNormativasList();
		return super.beforeEdit();
	}

	@Override
	public boolean beforeShow() {
		fillNormativasList();
		return super.beforeShow();
	}

	public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
		if (filterText == null || filterText.equals("")) {
			return true;
		}
		int filterInt = getInteger(filterText);

		Criterio criterio = (Criterio) value;
		return criterio.getCriNombre().toLowerCase().contains(filterText)
				|| criterio.getCriDescripcion().toLowerCase().contains(filterText) || criterio.getCriId() == filterInt;
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
	public Criterio getRegistro() {
		return (Criterio) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Criterio> getListado() {
		return (List<Criterio>) super.getListado();
	}

	@Override
	public void afterSaveNew() {
		getListado().add(getRegistro());
		super.afterSaveNew();
	}

	public List<Criterio> getFilteredCriterios() {
		return filteredCriterios;
	}

	public void setFilteredCriterios(List<Criterio> filteredCriterios) {
		this.filteredCriterios = filteredCriterios;
	}

	public List<NormativaCedula> getNormativasList() {
		return normativasList;
	}

	public void setNormativasList(List<NormativaCedula> normativasList) {
		this.normativasList = normativasList;
	}

}
