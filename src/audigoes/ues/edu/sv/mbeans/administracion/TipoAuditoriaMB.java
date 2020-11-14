package audigoes.ues.edu.sv.mbeans.administracion;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.planeacion.TipoAuditoria;


@ManagedBean(name = "tpaMB")
@ViewScoped
public class TipoAuditoriaMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<TipoAuditoria> filteredTiposAuditoria;

	public TipoAuditoriaMB() {
		super(new TipoAuditoria());
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
			setListado((List<TipoAuditoria>) audigoesLocal.findByNamedQuery(TipoAuditoria.class, "tipoauditoria.all", new Object[] {}));
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

		TipoAuditoria tipoauditoria = (TipoAuditoria) value;
		return tipoauditoria.getTpaNombre().toLowerCase().contains(filterText)
				|| tipoauditoria.getTpaDescripcion().toLowerCase().contains(filterText)
				|| tipoauditoria.getTpaId() == filterInt;
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
	public TipoAuditoria getRegistro() {
		return (TipoAuditoria) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TipoAuditoria> getListado() {
		return (List<TipoAuditoria>) super.getListado();
	}

	@Override
	public boolean beforeSaveNew() {
		try {
			getRegistro().setInstitucion(getObjAppsSession().getUsuario().getInstitucion());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return super.beforeSaveNew();
	}
	
	@Override
	public void afterSaveNew() {
		getListado().add(getRegistro());
		super.afterSaveNew();
	}
	@Override
	public void afterDelete() {
		getListado().remove(getRegistro());
		super.afterDelete();
	}

	public List<TipoAuditoria> getFilteredTiposAuditoria() {
		return filteredTiposAuditoria;
	}

	public void setFilteredTiposAuditoria(List<TipoAuditoria> filteredTiposAuditoria) {
		this.filteredTiposAuditoria = filteredTiposAuditoria;
	}

}
