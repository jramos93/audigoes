package audigoes.ues.edu.sv.mbeans.planeacion;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.planeacion.PlanAnual;

@ManagedBean(name = "planMB")
@ViewScoped
public class PlaneacionMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<PlanAnual> filteredPlanAnuales;

	public PlaneacionMB() {
		super(new PlanAnual());
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
			setListado((List<PlanAnual>) audigoesLocal.findByNamedQuery(PlanAnual.class,
					"plananual.get.all.institucion.activos",
					new Object[] { getObjAppsSession().getUsuario().getInstitucion().getInsId() }));
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

		PlanAnual plan = (PlanAnual) value;
		return plan.getPlaNombre().toLowerCase().contains(filterText)
				|| plan.getPlaDescripcion().toLowerCase().contains(filterText) || plan.getPlaId() == filterInt;
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
	public PlanAnual getRegistro() {
		return (PlanAnual) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PlanAnual> getListado() {
		return (List<PlanAnual>) super.getListado();
	}

	@Override
	public void afterSaveNew() {
		getListado().add(getRegistro());
		super.afterSaveNew();
	}

	public List<PlanAnual> getFilteredPlanAnuales() {
		return filteredPlanAnuales;
	}

	public void setFilteredPlanAnuales(List<PlanAnual> filteredPlanAnuales) {
		this.filteredPlanAnuales = filteredPlanAnuales;
	}

}
