package audigoes.ues.edu.sv.mbeans.administracion;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.administracion.NormativaCedula;

@ManagedBean(name = "norMB")
@ViewScoped
public class NormativaCedulaMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<NormativaCedula> filteredNormativasCedula;

	private List<SelectItem> tipoList;

	private UploadedFile file;

	private StreamedContent pt;

	public NormativaCedulaMB() {
		super(new NormativaCedula());
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
			if (getObjAppsSession() != null) {
				if (getObjAppsSession().getUsuario() != null) {
					setListado((List<NormativaCedula>) audigoesLocal.findByNamedQuery(NormativaCedula.class,
							"normativacedula.by.institucion",
							new Object[] { getObjAppsSession().getUsuario().getInstitucion().getInsId() }));
				}
			} else {
				setListado((List<NormativaCedula>) audigoesLocal.findByNamedQuery(NormativaCedula.class,
						"normativacedula.all", new Object[] {}));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public void upload() {

		try {
			if (file != null) {
				FacesMessage message = new FacesMessage("Exito", file.getFileName() + "Fue Subido");
				FacesContext.getCurrentInstance().addMessage(null, message);
			}
		} catch (Exception e) {
			FacesMessage message = new FacesMessage("Error de Conexion");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

	public void fileUploadListener(FileUploadEvent event) {
		try {
			getRegistro().setNorRuta(event.getFile().getFileName());
			getRegistro().setNorArchivo(event.getFile().getContent());
			getRegistro().setNorExtension(event.getFile().getContentType());
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Problemas al guardar archivo."));
		}

	}

	@Override
	public boolean beforeSaveNew() {
		getRegistro().setInstitucion(getObjAppsSession().getUsuario().getInstitucion());

		if (getRegistro().getNorNombre() == null || getRegistro().getNorVersion() == null
				|| getRegistro().getNorNombre().isEmpty() || getRegistro().getNorVersion().isEmpty()) {
			return false;
		}
		return super.beforeSaveNew();
	}

	@SuppressWarnings("deprecation")
	public void downloadFile(ActionEvent event) {
		NormativaCedula nc = (NormativaCedula) event.getComponent().getAttributes().get("normativa");
		InputStream bis = new ByteArrayInputStream(nc.getNorArchivo());
		pt = new DefaultStreamedContent(bis);
		pt = new DefaultStreamedContent(bis, nc.getNorExtension(), nc.getNorRuta());
	}

	public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
		if (filterText == null || filterText.equals("")) {
			return true;
		}
		int filterInt = getInteger(filterText);

		NormativaCedula normativacedula = (NormativaCedula) value;
		return normativacedula.getNorNombre().toLowerCase().contains(filterText)
				|| normativacedula.getNorDescripcion().toLowerCase().contains(filterText)
				|| normativacedula.getNorId() == filterInt;
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
	public NormativaCedula getRegistro() {
		return (NormativaCedula) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NormativaCedula> getListado() {
		return (List<NormativaCedula>) super.getListado();
	}

	@Override
	public void afterSaveNew() {
		getListado().add(getRegistro());
		super.afterSaveNew();
	}

	public List<NormativaCedula> getFilteredNormativasCedula() {
		return filteredNormativasCedula;
	}

	public void setFilteredNormativasCedula(List<NormativaCedula> filteredNormativasCedula) {
		this.filteredNormativasCedula = filteredNormativasCedula;
	}

	public List<SelectItem> getTipoList() {
		if (this.tipoList == null) {
			this.tipoList = new ArrayList<>();
			this.tipoList.add(new SelectItem(1, "Normativa"));
			this.tipoList.add(new SelectItem(0, "Cedula"));
		}
		return tipoList;
	}

	public void setTipoList(List<SelectItem> tipoList) {
		this.tipoList = tipoList;
	}

	public StreamedContent getPt() {
		return pt;
	}

	public void setPt(StreamedContent pt) {
		this.pt = pt;
	}

}
