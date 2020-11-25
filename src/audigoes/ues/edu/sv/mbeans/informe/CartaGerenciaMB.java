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
import audigoes.ues.edu.sv.entities.informe.Convocatoria;
import audigoes.ues.edu.sv.entities.informe.Destinatario;
import audigoes.ues.edu.sv.entities.informe.Informe;


@ManagedBean(name = "cartaGerenciaMB")
@ViewScoped
public class CartaGerenciaMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<CartaGerencia> filteredCartaGerencias;
	
	private Informe informe;
	
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
	@Override
	public void onSave() {
		if(getRegistro().getCtgId() > 0 ) {
			setStatus("EDIT");
		} else {
			setStatus("NEW");
		}
		super.onSave();
	}
	
	@Override
	public boolean beforeSaveNew() {
		getRegistro().setInforme(getInforme());
		getRegistro().setRegActivo(1);
		getRegistro().setUsuCrea(getObjAppsSession().getUsuario().getUsuUsuario());
		getRegistro().setFecCrea(getToday());
		return super.beforeSaveNew();
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
	@SuppressWarnings("unchecked")
	public void fillCartaGerencia() {
		try {
			setListado((List<CartaGerencia>) audigoesLocal.findByNamedQuery(CartaGerencia.class,
					"cartaGerencia.get.all.auditoria.informe",
					new Object[] { getInforme().getInfId()}));
			System.out.println("registro "+getInforme().getInfId());
			if(!getListado().isEmpty()) {
				System.out.println("registro "+getRegistro().getCtgId());
				setRegistro(getListado().get(0));
			} else {
				System.out.println("NO ENTRA A LA CONSULTA");
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
			TabView tv = (TabView) FacesContext.getCurrentInstance().getViewRoot().findComponent("frmCarta:tvCarta");
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

	public List<CartaGerencia> getFilteredCartaGerencias() {
		return filteredCartaGerencias;
	}

	public void setFilteredCartaGerencias(List<CartaGerencia> filteredCartaGerencias) {
		this.filteredCartaGerencias = filteredCartaGerencias;
	}

	public Informe getInforme() {
		return informe;
	}

	public void setInforme(Informe informe) {
		this.informe = informe;
	}

}
