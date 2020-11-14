package audigoes.ues.edu.sv.mbean.planificacion;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.administracion.Unidad;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;

@ManagedBean(name = "audSMB")
@ViewScoped
public class AuditoriaSSSSMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Auditoria> filteredAuditoria;

	public AuditoriaSSSSMB() {
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
			setListado((List<Auditoria>) audigoesLocal.findByNamedQuery(Auditoria.class, "auditoria.all", new Object[] {}));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
		if (filterText == null || filterText.equals("")) {
			return true;
		}
		//int filterInt = getInteger(filterText);

		Auditoria auditoria = (Auditoria) value;
		return auditoria.getAudNombre().toLowerCase().contains(filterText)
				||auditoria.getAudCodigo().toLowerCase().contains(filterText)
				||auditoria.getAudDescripcion().toLowerCase().contains(filterText);
	}

//	private int getInteger(String string) {
//		try {
//			return Integer.valueOf(string);
//		} catch (NumberFormatException ex) {
//			return 0;
//		}
//	}

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
	public boolean beforeSaveNew() {
		
		return super.beforeSaveNew();
	}

	@Override
	public void afterSaveNew() {
		getListado().add(getRegistro());
		super.afterSaveNew();
	}

	public List<Auditoria> getFilteredAuditoria() {
		return filteredAuditoria;
	}

	public void setFilteredAuditoria(List<Auditoria> filteredAuditoria) {
		this.filteredAuditoria = filteredAuditoria;
	}

	
}
