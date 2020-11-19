package audigoes.ues.edu.sv.mbeans.planeacion;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.informe.Informe;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;
import audigoes.ues.edu.sv.entities.planeacion.PlanAnual;
import audigoes.ues.edu.sv.entities.planeacion.TipoAuditoria;
import audigoes.ues.edu.sv.entities.planificacion.ProgramaPlanificacion;

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

	private List<TipoAuditoria> tipoAuditoriaList;
	private TipoAuditoria tipoAuditoriaSelected;
		
	private Informe informe;
	
	@ManagedProperty(value = "#{pplaMB}")
	private ProgramaPlanificacionMB pplaMB = new ProgramaPlanificacionMB();

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

	public void informeAud(){
		try {
			Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			sessionMap.put("auditoria",getRegistro());
			FacesContext.getCurrentInstance().getExternalContext().redirect("/audigoes/page/informe/informe.xhtml");
			System.out.println(getRegistro().getAudId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void fillListado() {
		try {
			setListado(
					(List<Auditoria>) audigoesLocal.findByNamedQuery(Auditoria.class, "auditoria.get.all.institucion",
							new Object[] { getObjAppsSession().getUsuario().getInstitucion().getInsId() }));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void onPrograma() {
		pplaMB.fillPrograma();
		pplaMB.onEdit();
	}

	@SuppressWarnings("unchecked")
	public void fillPlanAnualList() {
		try {
			setPlanAnualList((List<PlanAnual>) audigoesLocal.findByNamedQuery(PlanAnual.class,
					"plananual.get.all.institucion.activos",
					new Object[] { getObjAppsSession().getUsuario().getInstitucion().getInsId() }));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void fillTipoAuditoriaList() {
		try {
			setTipoAuditoriaList((List<TipoAuditoria>) audigoesLocal.findByNamedQuery(TipoAuditoria.class,
					"tipoauditoria.get.all.active.institucion",
					new Object[] { getObjAppsSession().getUsuario().getInstitucion().getInsId() }));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void fillAuditoriasPlan() {
		try {
			if (getPlanSelected() != null) {
				setListado((List<Auditoria>) audigoesLocal.findByNamedQuery(Auditoria.class,
						"auditoria.get.all.institucion.plan",
						new Object[] { getObjAppsSession().getUsuario().getInstitucion().getInsId(),
								getPlanSelected().getPlaId() }));
			} else {
				fillListado();
			}

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
				|| auditoria.getAudDescripcion().toLowerCase().contains(filterText)
				|| auditoria.getAudId() == filterInt;
	}

	private int getInteger(String string) {
		try {
			return Integer.valueOf(string);
		} catch (NumberFormatException ex) {
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	public int getCorrelativoAuditoria() {
		List<Auditoria> maxIdList;
		try {
			maxIdList = (List<Auditoria>) audigoesLocal.findByNamedQuery(Auditoria.class, "auditoria.get.max.id", new Object [] {
					getObjAppsSession().getUsuario().getInstitucion().getInsId()});
			if(maxIdList.size()>0) {
				System.out.println(maxIdList.get(0).getAudCorrelativo()+1);
				return maxIdList.get(0).getAudCorrelativo()+1;
			}else {
				System.out.println("1");
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public void createCodAuditoria() {
		
	}
	
	@Override
	public boolean beforeNew() {
		fillTipoAuditoriaList();
		fillPlanAnualList();
		createCodAuditoria();
		return super.beforeNew();
	}
	
	@Override
	public boolean beforeEdit() {
		fillTipoAuditoriaList();
		fillPlanAnualList();
		setTipoAuditoriaSelected(getRegistro().getTipoAuditoria());
		setPlanSelected(getRegistro().getPlanAnual());
		return super.beforeEdit();
	}

	@Override
	public boolean beforeSave() {
		// validando fecha de auditoría: fechaInicio < fechaFin
		boolean fechaValida = getRegistro().getAudFechaInicioProgramado()
				.compareTo(getRegistro().getAudFechaFinProgramado()) < 0;
		if (fechaValida) {
			/* 
			 * validando fecha de auditoria: fecha de la nueva auditoría debe estar dentro
			 * del rango de fecha del plan seleccionado 
			*/
			fechaValida = getRegistro().getAudFechaInicioProgramado()
					.compareTo(getPlanSelected().getPlaFechaInicio()) >= 0
					&& getRegistro().getAudFechaFinProgramado().compareTo(getPlanSelected().getPlaFechaFin()) <= 0;
			if (fechaValida) {
				getRegistro().setAudCorrelativo(getCorrelativoAuditoria());
				getRegistro().setPlanAnual(getPlanSelected());
				getRegistro().setTipoAuditoria(getTipoAuditoriaSelected());
				return super.beforeSave();
			} else {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				addWarn(new FacesMessage(SYSTEM_NAME,
						"La fecha de la auditoría se sale del rango programado para el plan seleccionado, DATOS DEL PLAN: "
						+ " Fecha Inicio " + dateFormat.format(getPlanSelected().getPlaFechaInicio()) 
						+ " Fecha Fin "	+ dateFormat.format(getPlanSelected().getPlaFechaFin())));
				return false;
			}

		} else {
			addWarn(new FacesMessage(SYSTEM_NAME,
					"La fecha de inicio debe ser menor a la fecha de finalización, favor verificar las fechas ingresadas"));
			return false;
		}

	}

	@Override
	public void afterSaveNew() {
		getListado().add(getRegistro());
		onNew();
		super.afterSaveNew();
	}
	
	@Override
	public boolean beforeDelete() {
		if(getRegistro().getActividad().size() > 0 ) {
			addWarn(new FacesMessage(SYSTEM_NAME, "La auditoría ya tiene actividades asignadas, no puede eliminarse"));
			return false;
		}
		return super.beforeDelete();
	}
	
	@Override
	public void afterDelete() {
		getListado().remove(getRegistro());
		super.afterDelete();
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
	
	public List<TipoAuditoria> getTipoAuditoriaList() {
		return tipoAuditoriaList;
	}

	public void setTipoAuditoriaList(List<TipoAuditoria> tipoAuditoriaList) {
		this.tipoAuditoriaList = tipoAuditoriaList;
	}

	public TipoAuditoria getTipoAuditoriaSelected() {
		return tipoAuditoriaSelected;
	}

	public void setTipoAuditoriaSelected(TipoAuditoria tipoAuditoriaSelected) {
		this.tipoAuditoriaSelected = tipoAuditoriaSelected;
	}

	public ProgramaPlanificacionMB getPplaMB() {
		return pplaMB;
	}

	public void setPplaMB(ProgramaPlanificacionMB pplaMB) {
		this.pplaMB = pplaMB;
	}

	public Informe getInforme() {
		return informe;
	}

	public void setInforme(Informe informe) {
		this.informe = informe;
	}
	
}
