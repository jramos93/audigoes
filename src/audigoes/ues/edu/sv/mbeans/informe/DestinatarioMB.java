package audigoes.ues.edu.sv.mbeans.informe;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.tabview.TabView;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.informe.Destinatario;

@ManagedBean(name = "destinatarioMB")
@ViewScoped
public class DestinatarioMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Destinatario> filteredDestinatarios;

	public DestinatarioMB() {
		super(new Destinatario());
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
			setListado((List<Destinatario>) audigoesLocal.findByNamedQuery(Destinatario.class,"destinatario.all",
					new Object[] {}));
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

		Destinatario destinatario = (Destinatario) value;
		return destinatario.getDstCorreo().toLowerCase().contains(filterText);
	}

	private int getInteger(String string) {
		try {
			return Integer.valueOf(string);
		} catch (NumberFormatException ex) {
			return 0;
		}
	}
	
	@Override
	public void afterCancel() {
		try {
			TabView tv = (TabView) FacesContext.getCurrentInstance().getViewRoot().findComponent("frmNewEdit4:tvDestinatario");
			tv.setActiveIndex(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		super.afterCancel();
	}
	
	/* GETS y SETS */

	@Override
	public Destinatario getRegistro() {
		return (Destinatario) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Destinatario> getListado() {
		return (List<Destinatario>) super.getListado();
	}

	@Override
	public void afterSaveNew() {
		getListado().add(getRegistro());
		super.afterSaveNew();
	}

	public List<Destinatario> getFilteredDestinatarios() {
		return filteredDestinatarios;
	}

	public void setFilteredDestinatarios(List<Destinatario> filteredDestinatarios) {
		this.filteredDestinatarios = filteredDestinatarios;
	}

}
