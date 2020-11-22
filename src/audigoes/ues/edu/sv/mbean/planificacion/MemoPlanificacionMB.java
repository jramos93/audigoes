package audigoes.ues.edu.sv.mbean.planificacion;

import java.io.Serializable;
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
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;
import audigoes.ues.edu.sv.entities.planificacion.Memorando;
import audigoes.ues.edu.sv.entities.planificacion.ProgramaPlanificacion;
import audigoes.ues.edu.sv.mbeans.administracion.UsuarioPermisoMB;

@ManagedBean(name = "memoMB")
@ViewScoped
public class MemoPlanificacionMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Memorando> filteredMemo;
	private Auditoria auditoria;

	public MemoPlanificacionMB() {
		super(new Memorando());
	}

	@PostConstruct
	public void init() {
		try {
			super.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	


	@SuppressWarnings("unchecked")
	public void fillMemo() {
		try {
//			setListado((List<ProgramaPlanificacion>) audigoesLocal.findByNamedQuery(ProgramaPlanificacion.class,
//					"programa.by.auditoria",
//					new Object[] { auditoria.getAudId() }));
			Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			setAuditoria(((Auditoria) sessionMap.get("auditoria")));
			System.out.println("auditoria "+auditoria.getAudId());
			setListado((List<Memorando>) audigoesLocal.findByNamedQuery(Memorando.class,
					"memo.by.auditoria",
					new Object[] { auditoria.getAudId() }));
			if(!getListado().isEmpty()) {
				setRegistro(getListado().get(0));
				
			} else {
				getRegistro().setAuditoria(auditoria);
				getRegistro().setFecCrea(getToday());
				getRegistro().setUsuCrea(getObjAppsSession().getUsuario().getUsuUsuario());
				getRegistro().setRegActivo(1);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage("Advertencia", "Auditoria No cuenta con Programa de Planificación"));
		}
	}
	
	public void showAuditorias() {
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

		Memorando memo = (Memorando) value;
		return memo.getMemNombre().toLowerCase().contains(filterText);
	}
	
	@Override
	public void afterCancel() {
		super.afterCancel();
	}
	
	@Override
	public boolean beforeSaveNew() {
		return super.beforeSaveNew();
	}

	/* GETS y SETS */

	@Override
	public Memorando getRegistro() {
		return (Memorando) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Memorando> getListado() {
		return (List<Memorando>) super.getListado();
	}
	
	@Override
	public void afterSave() {
		super.afterSave();
		onEdit();
	}

	@Override
	public void afterSaveNew() {
		//getListado().add(getRegistro());
		super.afterSaveNew();
	}
	
	public Auditoria getAuditoria() {
		return auditoria;
	}

	public void setAuditoria(Auditoria auditoria) {
		this.auditoria = auditoria;
	}

	public List<Memorando> getFilteredMemo() {
		return filteredMemo;
	}

	public void setFilteredMemo(List<Memorando> filteredMemo) {
		this.filteredMemo = filteredMemo;
	}
}
