package audigoes.ues.edu.sv.mbeans.administracion;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.planeacion.OrigenAuditoria;


@ManagedBean(name = "oriMB")
@ViewScoped
public class OrigenAuditoriaMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<OrigenAuditoria> filteredOrigenesAuditoria;

	public OrigenAuditoriaMB() {
		super(new OrigenAuditoria());
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
			setListado((List<OrigenAuditoria>) audigoesLocal.findByNamedQuery(OrigenAuditoria.class, "origenauditoria.all", new Object[] {}));
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

		OrigenAuditoria OrigenAuditoria = (OrigenAuditoria) value;
		return OrigenAuditoria.getOriNombre().toLowerCase().contains(filterText)
				|| OrigenAuditoria.getOriDescripcion().toLowerCase().contains(filterText)
				|| OrigenAuditoria.getOriId() == filterInt;
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
	public OrigenAuditoria getRegistro() {
		return (OrigenAuditoria) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrigenAuditoria> getListado() {
		return (List<OrigenAuditoria>) super.getListado();
	}

	@Override
	public void afterSaveNew() {
		getListado().add(getRegistro());
		super.afterSaveNew();
	}

	public List<OrigenAuditoria> getFilteredOrigenesAuditoria() {
		return filteredOrigenesAuditoria;
	}

	public void setFilteredOrigenesAuditoria(List<OrigenAuditoria> filteredOrigenesAuditoria) {
		this.filteredOrigenesAuditoria = filteredOrigenesAuditoria;
	}


}
