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
import audigoes.ues.edu.sv.entities.informe.Convocatoria;
import audigoes.ues.edu.sv.entities.informe.Informe;

@ManagedBean(name = "convocatoriaMB")
@ViewScoped
public class ConvocatoriaMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Convocatoria> filteredConvocatorias;
	
	private Informe informe;

	public ConvocatoriaMB() {
		super(new Convocatoria());
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
		if(getRegistro().getCvcId() > 0 ) {
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
			setListado((List<Convocatoria>) audigoesLocal.findByNamedQuery(Convocatoria.class,"convocatoria.all",
					new Object[] {}));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void fillConvocatoria() {
		try {
			setListado((List<Convocatoria>) audigoesLocal.findByNamedQuery(Convocatoria.class,
					"convocatoria.by.informe",
					new Object[] {getInforme().getInfId()}));
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

		Convocatoria convocatoria = (Convocatoria) value;
		return convocatoria.getCvcEncabezado().toLowerCase().contains(filterText);
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
			TabView tv = (TabView) FacesContext.getCurrentInstance().getViewRoot().findComponent("frmConvocatoria:tvConvocatoria");
			tv.setActiveIndex(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		super.afterCancel();
	}
	
	/* GETS y SETS */

	@Override
	public Convocatoria getRegistro() {
		return (Convocatoria) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Convocatoria> getListado() {
		return (List<Convocatoria>) super.getListado();
	}

	@Override
	public void afterSaveNew() {
		getListado().add(getRegistro());
		super.afterSaveNew();
	}

	public List<Convocatoria> getFilteredInformes() {
		return filteredConvocatorias;
	}

	public void setFilteredConvocatorias(List<Convocatoria> filteredConvocatorias) {
		this.filteredConvocatorias = filteredConvocatorias;
	}

	public Informe getInforme() {
		return informe;
	}

	public void setInforme(Informe informe) {
		this.informe = informe;
	}

}
