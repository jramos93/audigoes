package audigoes.ues.edu.sv.mbeans.informe;

import java.io.Serializable;
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
import audigoes.ues.edu.sv.entities.SuperEntity;
import audigoes.ues.edu.sv.entities.informe.CartaGerencia;
import audigoes.ues.edu.sv.entities.informe.Destinatario;


@ManagedBean(name = "cartaGerenciaMB")
@ViewScoped
public class CartaGerenciaMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<CartaGerencia> filteredCartaGerencias;
	
	public CartaGerenciaMB() {
		super(new CartaGerencia());
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
			setListado((List<CartaGerencia>) audigoesLocal.findByNamedQuery(CartaGerencia.class,"cartaGerencia.all",
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

		CartaGerencia cartaGerencia = (CartaGerencia) value;
		return cartaGerencia.getCtgEncabezado().toLowerCase().contains(filterText);
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
			TabView tv = (TabView) FacesContext.getCurrentInstance().getViewRoot().findComponent("frmNewEdit2:tvCarta");
			tv.setActiveIndex(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		super.afterCancel();
	}
	
	/* GETS y SETS */

	@Override
	public CartaGerencia getRegistro() {
		return (CartaGerencia) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CartaGerencia> getListado() {
		return (List<CartaGerencia>) super.getListado();
	}

	@Override
	public void afterSaveNew() {
		getListado().add(getRegistro());
		super.afterSaveNew();
	}

	public List<CartaGerencia> getFilteredInformes() {
		return filteredCartaGerencias;
	}

	public void setFilteredCartaGerencias(List<CartaGerencia> filteredCartaGerencias) {
		this.filteredCartaGerencias = filteredCartaGerencias;
	}

}
