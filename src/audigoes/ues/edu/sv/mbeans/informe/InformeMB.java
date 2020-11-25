package audigoes.ues.edu.sv.mbeans.informe;

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

import org.primefaces.component.tabview.TabView;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.SuperEntity;
import audigoes.ues.edu.sv.entities.informe.ActaLectura;
import audigoes.ues.edu.sv.entities.informe.CartaGerencia;
import audigoes.ues.edu.sv.entities.informe.Convocatoria;
import audigoes.ues.edu.sv.entities.informe.Informe;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;
import audigoes.ues.edu.sv.entities.planificacion.ProgramaPlanificacion;
import audigoes.ues.edu.sv.mbeans.planeacion.AuditoriaMB;

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
	private int versionActual;

	public InformeMB() {
		super(new Informe());
	}

	@PostConstruct
	public void init() {
		try {
			Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			setAuditoria((Auditoria) sessionMap.get("auditoria"));
			setInforme();
			System.out.println(getAuditoria().getAudId());
			super.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onSave() {
		if(getRegistro().getInfId() > 0 ) {
			setStatus("EDIT");
		} else {
			setStatus("NEW");
		}
		super.onSave();
	}
	
	public void setInforme() {
		try {
			setRegistro((Informe)audigoesLocal.findByNamedQuery(Informe.class,"informe.by.auditoria",
					new Object[] {getAuditoria().getAudId()}).get(0));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean beforeSaveNew() {
		getRegistro().setAuditoria(getAuditoria());
		getRegistro().setRegActivo(1);
		getRegistro().setUsuCrea(getObjAppsSession().getUsuario().getUsuUsuario());
		getRegistro().setFecCrea(getToday());
		return super.beforeSaveNew();
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
	@SuppressWarnings("unchecked")
	public void fillInforme() {
		try {
			setListado((List<Informe>) audigoesLocal.findByNamedQuery(Informe.class,
					"informe.by.auditoria",
					new Object[] {getAuditoria().getAudId()}));
			if(!getListado().isEmpty()) {
				setRegistro(getListado().get(0));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void fillActaLectura() {
		try {
			setListado((List<ActaLectura>) audigoesLocal.findByNamedQuery(ActaLectura.class,
					"actaLectura.by.informe",
					new Object[] {getRegistro().getInfId()}));
			if(!getListado().isEmpty()) {
				setRegistro(getListado().get(0));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void fillConvocatoria() {
		try {
			setListado((List<Convocatoria>) audigoesLocal.findByNamedQuery(Convocatoria.class,
					"convocatoria.by.informe",
					new Object[] {getRegistro().getInfId()}));
			if(!getListado().isEmpty()) {
				setRegistro(getListado().get(0));
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
			TabView tv = (TabView) FacesContext.getCurrentInstance().getViewRoot().findComponent("frmInforme:tvInforme");
			tv.setActiveIndex(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		super.afterCancel();
	}
	
	public void showInforme() {
		fillInforme();
		if (getRegistro().getInfId() > 0) {
			setVersionActual(getRegistro().getInfVersion());
		}else {
			setVersionActual(1);
			getRegistro().setInfVersion(getVersionActual());
		}
		setStatus("INFORME");
	}
	
	public void showActaLectura() {
		this.getConvMB().setInforme(getRegistro());
		this.getConvMB().fillConvocatoria();
		this.getActLecMB().setInforme(getRegistro());
		this.getActLecMB().fillActaLectura();
		//fillActaLectura();
		//fillConvocatoria();
		setStatus("ACTA_LECTURA");
	}
	
	public void showCarta() {
		//fillCartaGerencia();
		this.getCartaGMB().setInforme(getRegistro());
		this.getCartaGMB().fillCartaGerencia();
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

	public int getVersionActual() {
		return versionActual;
	}

	public void setVersionActual(int versionActual) {
		this.versionActual = versionActual;
	}

}