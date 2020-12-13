package audigoes.ues.edu.sv.mbeans.ejecucion;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.administracion.Archivo;
import audigoes.ues.edu.sv.entities.informe.CedulaNota;
import audigoes.ues.edu.sv.entities.seguimiento.Evidencia;

@ManagedBean(name = "evdMB")
@ViewScoped
public class EvidenciaMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Evidencia> filteredArchivos;

	private CedulaNota cedula;

	private StreamedContent pt;

	public EvidenciaMB() {
		super(new Evidencia());
	}

	@PostConstruct
	public void init() {
		try {
			super.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void fillByCedula() {
		try {
			setListado((List<Evidencia>) audigoesLocal.findByNamedQuery(Evidencia.class, "evidencia.cedula",
					new Object[] { cedula.getCedId() }));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
		if (filterText == null || filterText.equals("")) {
			return true;
		}

		Evidencia arch = (Evidencia) value;
		return arch.getEvdNombre().toLowerCase().contains(filterText);
	}

	public void onBorrar(Evidencia a) {
		setRegistro(a);
		onDelete();
	}

	@Override
	public void afterDelete() {
		getListado().remove(getRegistro());
		super.afterDelete();
	}

	public void handleFileUpload(FileUploadEvent event) {
		try {
			this.onNew();
			// this.getRegistro().setProcedimientoEjecucion(getRegistro());
			this.getRegistro().setEvdArchivo(event.getFile().getContent());
			this.getRegistro().setCedula(cedula);
			this.getRegistro().setEvdNombre(event.getFile().getFileName());
			this.getRegistro().setEvdExtension(event.getFile().getContentType());
			this.getRegistro().setFecCrea(getToday());
			this.getRegistro().setUsuCrea(getObjAppsSession().getUsuario().getUsuUsuario());
			this.getRegistro().setRegActivo(1);
			audigoesLocal.insert(this.getRegistro());
			// this.arcMB.afterSaveNew();
			this.getListado().add(this.getRegistro());
			if (!getStatus().equals("NEW")) {
				addWarn(new FacesMessage(SYSTEM_NAME, "Archivo Guardado con Éxito"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Problemas al guardar archivo."));
		}

	}

	public void downloadFile(ActionEvent event) {
		Evidencia ev = (Evidencia) event.getComponent().getAttributes().get("pt");
		InputStream bis = new ByteArrayInputStream(ev.getEvdArchivo());
		pt = new DefaultStreamedContent(bis);
		pt = new DefaultStreamedContent(bis, ev.getEvdExtension(), ev.getEvdNombre());
	}

	/* GETS y SETS */

	@Override
	public Evidencia getRegistro() {
		return (Evidencia) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Evidencia> getListado() {
		return (List<Evidencia>) super.getListado();
	}

	@Override
	public void afterSaveNew() {
		getListado().add(getRegistro());
		super.afterSaveNew();
	}

	public List<Evidencia> getFilteredArchivos() {
		return filteredArchivos;
	}

	public void setFilteredArchivos(List<Evidencia> filteredArchivos) {
		this.filteredArchivos = filteredArchivos;
	}

	public CedulaNota getCedula() {
		return cedula;
	}

	public void setCedula(CedulaNota cedula) {
		this.cedula = cedula;
	}

	public StreamedContent getPt() {
		return pt;
	}

	public void setPt(StreamedContent pt) {
		this.pt = pt;
	}

}
