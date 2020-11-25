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
import audigoes.ues.edu.sv.entities.informe.ActaLectura;
import audigoes.ues.edu.sv.entities.informe.Convocatoria;
import audigoes.ues.edu.sv.entities.informe.Informe;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;

@ManagedBean(name = "actaLecturaMB")
@ViewScoped
public class ActaLecturaMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<ActaLectura> filteredActaLecturas;
	
	private Informe informe;

	public ActaLecturaMB() {
		super(new ActaLectura());
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
		if(getRegistro().getAclId() > 0 ) {
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
			setListado((List<ActaLectura>) audigoesLocal.findByNamedQuery(ActaLectura.class,"actaLectura.all",
					new Object[] {}));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	public void fillActaLectura() {
		try {
			setListado((List<ActaLectura>) audigoesLocal.findByNamedQuery(ActaLectura.class,
					"actaLectura.by.informe",
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

		ActaLectura actaLectura = (ActaLectura) value;
		return actaLectura.getAclEncabezado().toLowerCase().contains(filterText);
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
			TabView tv = (TabView) FacesContext.getCurrentInstance().getViewRoot().findComponent("frmConvocatoriaActa:tvActa");
			tv.setActiveIndex(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		super.afterCancel();
	}
	
	/* GETS y SETS */

	@Override
	public ActaLectura getRegistro() {
		return (ActaLectura) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ActaLectura> getListado() {
		return (List<ActaLectura>) super.getListado();
	}

	@Override
	public void afterSaveNew() {
		getListado().add(getRegistro());
		super.afterSaveNew();
	}

	public List<ActaLectura> getFilteredInformes() {
		return filteredActaLecturas;
	}

	public void setFilteredActaLecturas(List<ActaLectura> filteredActaLecturas) {
		this.filteredActaLecturas = filteredActaLecturas;
	}

	public Informe getInforme() {
		return informe;
	}

	public void setInforme(Informe informe) {
		this.informe = informe;
	}

}
