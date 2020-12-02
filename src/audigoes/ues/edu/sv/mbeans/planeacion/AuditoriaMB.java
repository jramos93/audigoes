package audigoes.ues.edu.sv.mbeans.planeacion;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import audigoes.ues.edu.sv.entities.administracion.Unidad;
import audigoes.ues.edu.sv.entities.administracion.Usuario;
import audigoes.ues.edu.sv.entities.informe.Informe;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;
import audigoes.ues.edu.sv.entities.planeacion.AuditoriaResponsable;
import audigoes.ues.edu.sv.entities.planeacion.PlanAnual;
import audigoes.ues.edu.sv.entities.planeacion.TipoAuditoria;
import audigoes.ues.edu.sv.mbean.planificacion.AuditoriaUnidadMB;
import audigoes.ues.edu.sv.mbean.planificacion.MemoPlanificacionMB;
import audigoes.ues.edu.sv.mbean.planificacion.ProgramaEjecucionMB;
import audigoes.ues.edu.sv.mbean.planificacion.ProgramaPlanificacionMB;
import audigoes.ues.edu.sv.mbeans.seguimiento.SeguimientoMB;

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

	private List<Unidad> unidadList;
	private List<Unidad> unidadesSelectedList;
	private Unidad unidadSelected;

	private Usuario coordinador;

	private Informe informe;

	@ManagedProperty(value = "#{pplaMB}")
	private ProgramaPlanificacionMB pplaMB = new ProgramaPlanificacionMB();

	@ManagedProperty(value = "#{pejeMB}")
	private ProgramaEjecucionMB pejeMB = new ProgramaEjecucionMB();

	@ManagedProperty(value = "#{memoMB}")
	private MemoPlanificacionMB memoMB = new MemoPlanificacionMB();

	@ManagedProperty(value = "#{respMB}")
	private AuditoriaResponsableMB respMB = new AuditoriaResponsableMB();

	@ManagedProperty(value = "#{audUniMB}")
	private AuditoriaUnidadMB audUniMB = new AuditoriaUnidadMB();

	@ManagedProperty(value = "#{segMB}")
	private SeguimientoMB segMB = new SeguimientoMB();

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

	public void informeAud() {
		try {
			Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			sessionMap.put("auditoria", getRegistro());
			FacesContext.getCurrentInstance().getExternalContext().redirect("/audigoes/page/informe/informe.xhtml");
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

	@SuppressWarnings("unchecked")
	public void fillUnidadList() {
		try {
			setUnidadList((List<Unidad>) audigoesLocal.findByNamedQuery(Unidad.class, "unidad.get.all.institucion",
					new Object[] { getObjAppsSession().getUsuario().getInstitucion().getInsId() }));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void onPrograma() {
		pplaMB.fillPrograma();
		pplaMB.onEdit();
	}

	public void onProgramaEje() {
		pejeMB.fillPrograma();
		pejeMB.onEdit();
	}

	public void onMemo() {
		memoMB.fillMemo();
		memoMB.onEdit();
	}

	public void seguimiento() {
		try {
			Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			sessionMap.put("auditoria", getRegistro());
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("/audigoes/page/seguimiento/seguimiento.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void asignarPersonal() {
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessionMap.put("auditoria", getRegistro());
		respMB.fillListado();

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
			maxIdList = (List<Auditoria>) audigoesLocal.findByNamedQuery(Auditoria.class, "auditoria.get.max.id",
					new Object[] { getObjAppsSession().getUsuario().getInstitucion().getInsId() });
			if (maxIdList.size() > 0) {
				return maxIdList.get(0).getAudCorrelativo() + 1;
			} else {
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public void createCodAuditoria() {
		try {
			getRegistro().setAudCodigo(getTipoAuditoriaSelected().getTpaAcronimo() + "-" + getRegistro().getAudAnio()
					+ "-" + getCorrelativoAuditoria());
		} catch (Exception e) {
			addWarn(new FacesMessage(SYSTEM_NAME, "Problema al asignar código de auditoría"));
			e.printStackTrace();
		}
	}

	public void agregarUnidadAuditada() {
		try {
			if(getStatus().equals("NEW")) {
				if (getUnidadesSelectedList() == null) {
					setUnidadesSelectedList(new ArrayList<Unidad>());
				}
				getUnidadesSelectedList().add(getUnidadSelected());
				getUnidadList().remove(getUnidadSelected());
			} else if (getStatus().equals("EDIT")) {
				this.audUniMB.onNew();
				this.audUniMB.getRegistro().setAuditoria(getRegistro());
				this.audUniMB.getRegistro().setUnidad(getUnidadSelected());
				this.audUniMB.onSave();
				getRegistro().getAuditoriaUnidad().add(this.audUniMB.getRegistro());
				getUnidadList().remove(getUnidadSelected());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Problema al agregar unidad"));
		}
	}

	public void eliminarUnidadAuditada(Unidad unidad) {
		if(getStatus().equals("NEW")) {
			getUnidadesSelectedList().remove(unidad);
			getUnidadList().add(unidad);
		}else if (getStatus().equals("EDIT")) {
			
		}
		
	}

	@Override
	public boolean beforeNew() {
		fillTipoAuditoriaList();
		fillPlanAnualList();
		fillUnidadList();
		fillUsuariosInstitucionList();
		return super.beforeNew();
	}

	@Override
	public boolean beforeEdit() {
		fillTipoAuditoriaList();
		fillPlanAnualList();
		fillUnidadList();
		fillUsuariosInstitucionList();
		setTipoAuditoriaSelected(getRegistro().getTipoAuditoria());
		setPlanSelected(getRegistro().getPlanAnual());
		return super.beforeEdit();
	}
	
	@Override
	protected void afterEdit() {
		getRegistro().getAuditoriaUnidad();
		super.afterEdit();
	}

	private boolean isFechaAuditoriaValida() {
		return getRegistro().getAudFechaInicioProgramado().compareTo(getRegistro().getAudFechaFinProgramado()) < 0;
	}

	private boolean isFechaPlanValido() {
		return getRegistro().getAudFechaInicioProgramado().compareTo(getPlanSelected().getPlaFechaInicio()) >= 0
				&& getRegistro().getAudFechaFinProgramado().compareTo(getPlanSelected().getPlaFechaFin()) <= 0;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void onRowSelect() {
		super.onRowSelect();
		try {
			List<Usuario> coord = (List<Usuario>) audigoesLocal.findByNamedQuery(Usuario.class,
					"usuario.get.coordinador", new Object[] { getRegistro().getAudId() });
			if (!coord.isEmpty()) {
				setCoordinador(coord.get(0));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean beforeSaveNew() {
		if (isFechaAuditoriaValida()) {
			if (isFechaPlanValido()) {
				getRegistro().setAudCorrelativo(getCorrelativoAuditoria());
				getRegistro().setPlanAnual(getPlanSelected());
				getRegistro().setTipoAuditoria(getTipoAuditoriaSelected());
				createCodAuditoria();
				return super.beforeSave();
			} else {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				addWarn(new FacesMessage(SYSTEM_NAME,
						"La fecha de la auditoría se sale del rango programado para el plan seleccionado, DATOS DEL PLAN: "
								+ " Fecha Inicio " + dateFormat.format(getPlanSelected().getPlaFechaInicio())
								+ " Fecha Fin " + dateFormat.format(getPlanSelected().getPlaFechaFin())));
				return false;
			}

		} else {
			addWarn(new FacesMessage(SYSTEM_NAME,
					"La fecha de inicio debe ser menor a la fecha de finalización, favor verificar las fechas ingresadas"));
			return false;
		}
	}

	@Override
	public void afterSaveEdit() {
		boolean valido = false;
		for (AuditoriaResponsable cor : getRegistro().getAuditoriaResponsable()) {
			if (cor.getAurRol() == 0) {
				if (cor.getUsuario().getUsuId() != getCoordinador().getUsuId()) {
					try {
						//inactivar coordinador actual
						this.respMB.setRegSelected(cor);
						this.respMB.onEditSelected();
						this.respMB.getRegistro().setRegActivo(0);
						this.respMB.onSaveEdit();
						
						//ingresar nuevo coordinador
						guardarCoordinador();
					} catch (Exception e) {
						e.printStackTrace();
						addWarn(new FacesMessage(SYSTEM_NAME, "Problema al actualizar el coordinador"));
					}
				}
				valido = true;
				break;
			}
		}
		if(!valido) {
			try {
				guardarCoordinador();
			} catch (Exception e) {
				e.printStackTrace();
				addWarn(new FacesMessage(SYSTEM_NAME, "Problema al ingresar nuevo coordinador"));
			}
			
		}
	}

	public void planificacion() {
		try {
			Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			sessionMap.put("auditoria", getRegistro());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void afterSaveNew() {
		try {
			getListado().add(getRegistro());
			
			guardarCoordinador();
			guardarUnidadesAuditadas();
			
			onNew();
			super.afterSaveNew();
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Problema al guardar coordinador de la auditoría"));
		}

	}
	
	public void guardarCoordinador() {
		this.respMB.onNew();
		this.respMB.getRegistro().setUsuario(getCoordinador());
		this.respMB.getRegistro().setAurRol(0);
		this.respMB.getRegistro().setAuditoria(getRegistro());
		this.respMB.onSaveNew();
	}

	public void guardarUnidadesAuditadas() {
		try {
			for (Unidad reg : getUnidadesSelectedList()) {
				this.audUniMB.onNew();
				this.audUniMB.getRegistro().setAuditoria(getRegistro());
				this.audUniMB.getRegistro().setUnidad(reg);
				this.audUniMB.onSave();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean beforeDelete() {
		if (getRegistro().getActividad().size() > 0) {
			addWarn(new FacesMessage(SYSTEM_NAME, "La auditoría ya tiene actividades asignadas, no puede eliminarse"));
			return false;
		}
		if (getRegistro().getAuditoriaResponsable().size() > 0) {
			addWarn(new FacesMessage(SYSTEM_NAME, "La auditoría tiene responsable asignado. No puede ser eliminada"));
			return false;
		}
		if (getRegistro().getAuditoriaUnidad().size() > 0) {
			addWarn(new FacesMessage(SYSTEM_NAME, "La auditoría tiene unidades asignadas. No puede ser eliminada"));
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

	public ProgramaEjecucionMB getPejeMB() {
		return pejeMB;
	}

	public void setPejeMB(ProgramaEjecucionMB pejeMB) {
		this.pejeMB = pejeMB;
	}

	public MemoPlanificacionMB getMemoMB() {
		return memoMB;
	}

	public void setMemoMB(MemoPlanificacionMB memoMB) {
		this.memoMB = memoMB;
	}

	public AuditoriaResponsableMB getRespMB() {
		return respMB;
	}

	public void setRespMB(AuditoriaResponsableMB respMB) {
		this.respMB = respMB;
	}

	public Usuario getCoordinador() {
		return coordinador;
	}

	public void setCoordinador(Usuario coordinador) {
		this.coordinador = coordinador;
	}

	public AuditoriaUnidadMB getAudUniMB() {
		return audUniMB;
	}

	public void setAudUniMB(AuditoriaUnidadMB audUniMB) {
		this.audUniMB = audUniMB;
	}

	public List<Unidad> getUnidadList() {
		return unidadList;
	}

	public void setUnidadList(List<Unidad> unidadList) {
		this.unidadList = unidadList;
	}

	public List<Unidad> getUnidadesSelectedList() {
		return unidadesSelectedList;
	}

	public void setUnidadesSelectedList(List<Unidad> unidadesSelectedList) {
		this.unidadesSelectedList = unidadesSelectedList;
	}

	public Unidad getUnidadSelected() {
		return unidadSelected;
	}

	public void setUnidadSelected(Unidad unidadSelected) {
		this.unidadSelected = unidadSelected;
	}

	public SeguimientoMB getSegMB() {
		return segMB;
	}

	public void setSegMB(SeguimientoMB segMB) {
		this.segMB = segMB;
	}

}
