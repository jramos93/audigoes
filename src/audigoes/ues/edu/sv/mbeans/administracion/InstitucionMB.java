package audigoes.ues.edu.sv.mbeans.administracion;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.administracion.Institucion;

@ManagedBean(name = "insMB")
@ViewScoped
public class InstitucionMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Institucion> filteredInstituciones;

	public InstitucionMB() {
		super(new Institucion());
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
			setListado((List<Institucion>) audigoesLocal.findByNamedQuery(Institucion.class, "institucion.all", new Object[] {}));
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

		Institucion institucion = (Institucion) value;
		return institucion.getInsNombre().toLowerCase().contains(filterText)
				||institucion.getInsIniciales().toLowerCase().contains(filterText)
				|| institucion.getInsId() == filterInt;
	}

	private int getInteger(String string) {
		try {
			return Integer.valueOf(string);
		} catch (NumberFormatException ex) {
			return 0;
		}
	}
	
	public void handleFileUpload(FileUploadEvent event) {
		try {
			System.out.println(" archivo " + event.getFile().getFileName());
			getRegistro().setInsLogo(event.getFile().getContent());
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Problemas al guardar archivo."));
		}

	}

	/* GETS y SETS */

	@Override
	public Institucion getRegistro() {
		return (Institucion) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Institucion> getListado() {
		return (List<Institucion>) super.getListado();
	}

	@Override
	public void afterSaveNew() {
		getListado().add(getRegistro());
		PrimeFaces.current().executeScript("PF('wvConfirmacion').show();)");
		PrimeFaces.current().ajax().update(":frmConfirmacion");
		super.afterSaveNew();
	}

	public List<Institucion> getFilteredInstituciones() {
		return filteredInstituciones;
	}

	public void setFilteredInstituciones(List<Institucion> filteredInstituciones) {
		this.filteredInstituciones = filteredInstituciones;
	}
}
