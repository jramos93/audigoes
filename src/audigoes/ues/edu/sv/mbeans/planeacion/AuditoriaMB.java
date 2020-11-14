package audigoes.ues.edu.sv.mbeans.planeacion;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;
import audigoes.ues.edu.sv.entities.planeacion.PlanAnual;

@ManagedBean(name = "audMB")
@ViewScoped
public class AuditoriaMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Auditoria> filteredAuditorias;
	private List<PlanAnual> planAnualList;
	private PlanAnual planSelected;

	public AuditoriaMB() {
		super(new Auditoria());
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
			System.out.println("getObjAppsSession().getUsuario().getInstitucion().getInsId()  "+getObjAppsSession().getUsuario().getInstitucion().getInsId() );
			setListado((List<Auditoria>) audigoesLocal.findByNamedQuery(Auditoria.class,
					"auditoria.get.all.institucion",
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

		Auditoria auditoria = (Auditoria) value;
		return auditoria.getAudNombre().toLowerCase().contains(filterText)
				|| auditoria.getAudDescripcion().toLowerCase().contains(filterText) || auditoria.getAudId() == filterInt;
	}

	private int getInteger(String string) {
		try {
			return Integer.valueOf(string);
		} catch (NumberFormatException ex) {
			return 0;
		}
	}
	
	@Override
	public void afterNew() {
		super.afterNew();
	}

	/* GETS y SETS */

	@Override
	public Auditoria getRegistro() {
		return (Auditoria) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Auditoria> getListado() {
		return (List<Auditoria>) super.getListado();
	}

	@Override
	public void afterSaveNew() {
		getListado().add(getRegistro());
		super.afterSaveNew();
	}

	public List<Auditoria> getFilteredAuditorias() {
		return filteredAuditorias;
	}

	public void setFilteredAuditorias(List<Auditoria> filteredAuditorias) {
		this.filteredAuditorias = filteredAuditorias;
	}

	public List<PlanAnual> getPlanAnualList() {
		return planAnualList;
	}

	public void setPlanAnualList(List<PlanAnual> planAnualList) {
		this.planAnualList = planAnualList;
	}

	public PlanAnual getPlanSelected() {
		return planSelected;
	}

	public void setPlanSelected(PlanAnual planSelected) {
		this.planSelected = planSelected;
	}

}
