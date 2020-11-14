package audigoes.ues.edu.sv.mbeans.planeacion;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.tabview.TabView;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;
import audigoes.ues.edu.sv.entities.planeacion.PlanAnual;

@ManagedBean(name = "planMB")
@ViewScoped
public class PlaneacionMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<PlanAnual> filteredPlanAnuales;
	private List<Auditoria> auditoriasPlanList;

	@ManagedProperty(value = "#{audMB}")
	private AuditoriaMB audMB = new AuditoriaMB();

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
	
	@Override
	protected void afterRowSelect() {
		getAudMB().fillAuditoriasPlan();
		super.afterRowSelect();
	}

	public void showAuditorias() {
		try {
			getAudMB().setPlanSelected(getRegistro());
			getAudMB().fillAuditoriasPlan();
			setStatus("VIEW_AUDITORIAS");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void generarInforme() {
		try {

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
	
	@Override
	public boolean beforeCancel() {
		if(getAudMB().getStatus() != "SEARCH") {
			getAudMB().onCancel();
		}
		return super.beforeCancel();
	}

	@Override
	public void afterCancel() {
		try {
			TabView tv = (TabView) FacesContext.getCurrentInstance().getViewRoot()
					.findComponent("frmNewEdit:tvPlanAnual");
			tv.setActiveIndex(0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		super.afterCancel();
	}
	
	@Override
	protected void afterEdit() {
		getAudMB().setPlanSelected(getRegistro());
		getAudMB().onNew();
		super.afterEdit();
	}
	
	@Override
	public boolean beforeSaveNew() {
		if (getRegistro().getPlaFechaInicio().compareTo(getRegistro().getPlaFechaFin()) < 0) {
			getRegistro().setInstitucion(getObjAppsSession().getUsuario().getInstitucion());
			return super.beforeSaveNew();
		} else {
			addWarn(new FacesMessage(SYSTEM_NAME,
					"La fecha de inicio debe ser menor a la fecha de finalización, favor verificar las fechas ingresadas"));
			return false;
		}
	}
	
	@Override
	public boolean beforeDelete() {
		getAudMB().setPlanSelected(getRegistro());
		getAudMB().fillAuditoriasPlan();
		if(getAudMB().getListado().size() > 0) {
			addWarn(new FacesMessage(SYSTEM_NAME, "El plan ya tiene auditorías asignadas, no puede eliminarse"));
			return false;
		}
		return super.beforeDelete();
	}

	@Override
	public void afterDelete() {
		getListado().remove(getRegistro());
		super.afterDelete();
	}

	/* ver reporte */
	public void viewReporte() throws SQLException, ClassNotFoundException {
		//FacesContext facesContext = FacesContext.getCurrentInstance();
		//ServletContext servletContext = (ServletContext) facesContext.getExternalContext();
		
		try {
			//Se setea la variable reportId desde la vista con un setProperty
			getReport();
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//<h:commandLink actionListener="#{bean.viewReporte()} target="_blank/>
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
	
	public List<Auditoria> getAuditoriasPlanList() {
		return auditoriasPlanList;
	}

	public void setAuditoriasPlanList(List<Auditoria> auditoriasPlanList) {
		this.auditoriasPlanList = auditoriasPlanList;
	}

	public AuditoriaMB getAudMB() {
		return audMB;
	}

	public void setAudMB(AuditoriaMB audMB) {
		this.audMB = audMB;
	}
	
}
