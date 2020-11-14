package audigoes.ues.edu.sv.mbeans.informe;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.tabview.TabView;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.informe.Informe;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;

@ManagedBean(name = "informeMB")
@ViewScoped
public class InformeMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Informe> filteredInformes;
	
	@ManagedProperty(value = "#{actaLecturaMB}")
	private ActaLecturaMB actLecMB = new ActaLecturaMB();
	
	@ManagedProperty(value = "#{cartaGerenciaMB}")
	private CartaGerenciaMB cartaGMB = new CartaGerenciaMB();
	
	@ManagedProperty(value= "#{convocatoriaMB}")
	private ConvocatoriaMB convMB = new ConvocatoriaMB();
	
	private Auditoria auditoria;

	public InformeMB() {
		super(new Informe());
	}

	@PostConstruct
	public void init() {
		try {
			Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			setAuditoria((Auditoria) sessionMap.get("auditoria"));
			super.init();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@SuppressWarnings("unchecked")
	public void fillListado() {
		try {
			setListado((List<Informe>) audigoesLocal.findByNamedQuery(Informe.class,"informe.all",
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

		Informe informe = (Informe) value;
		return informe.getInfTitulo().toLowerCase().contains(filterText)
				|| informe.getInfVersion() == filterInt || informe.getInfId() == filterInt;
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
			TabView tv = (TabView) FacesContext.getCurrentInstance().getViewRoot().findComponent("frmNewEdit:tvInforme");
			tv.setActiveIndex(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		super.afterCancel();
	}
	
	public void showInforme() {
		setStatus("INFORME");
	}
	
	public void showActaLectura() {
		setStatus("ACTA_LECTURA");
	}
	
	public void showCarta() {
		setStatus("CARTA_GERENCIA");
	}
	
	/* GETS y SETS */

	@Override
	public Informe getRegistro() {
		return (Informe) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Informe> getListado() {
		return (List<Informe>) super.getListado();
	}

	@Override
	public void afterSaveNew() {
		getListado().add(getRegistro());
		super.afterSaveNew();
	}

	public List<Informe> getFilteredInformes() {
		return filteredInformes;
	}

	public void setFilteredInformes(List<Informe> filteredInformes) {
		this.filteredInformes = filteredInformes;
	}

	public ActaLecturaMB getActLecMB() {
		return actLecMB;
	}

	public void setActLecMB(ActaLecturaMB actLecMB) {
		this.actLecMB = actLecMB;
	}

	public CartaGerenciaMB getCartaGMB() {
		return cartaGMB;
	}

	public void setCartaGMB(CartaGerenciaMB cartaGMB) {
		this.cartaGMB = cartaGMB;
	}

	public ConvocatoriaMB getConvMB() {
		return convMB;
	}

	public void setConvMB(ConvocatoriaMB convMB) {
		this.convMB = convMB;
	}

	public Auditoria getAuditoria() {
		return auditoria;
	}

	public void setAuditoria(Auditoria auditoria) {
		this.auditoria = auditoria;
	}

}