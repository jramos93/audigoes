package audigoes.ues.edu.sv.mbeans.administracion;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.administracion.Archivo;
import audigoes.ues.edu.sv.entities.administracion.Institucion;
import audigoes.ues.edu.sv.entities.administracion.Marca;

@ManagedBean(name = "marMB")
@ViewScoped
public class MarcaMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Marca> filteredMarcas;

	@ManagedProperty(value = "#{arcMB}")
	private ArchivoMB arcMB = new ArchivoMB();

	private StreamedContent marca;

	private Institucion institucion;

	public MarcaMB() {
		super(new Marca());
	}

	@PostConstruct
	public void init() {
		try {
			fillListado();
			super.init();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public void fillListado() {
		try {
			setListado((List<Marca>) audigoesLocal.findByNamedQuery(Marca.class, "marca.get.all.institucion",
					new Object[] { getObjAppsSession().getUsuario().getInstitucion().getInsId() }));
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

		Marca Marca = (Marca) value;
		return Marca.getMarNombre().toLowerCase().contains(filterText)
				|| Marca.getMarDescripcion().toLowerCase().contains(filterText) || Marca.getMarId() == filterInt;
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
	public Marca getRegistro() {
		return (Marca) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Marca> getListado() {
		return (List<Marca>) super.getListado();
	}

	@Override
	public void afterSaveNew() {
		getListado().add(getRegistro());
		super.afterSaveNew();
	}

	public List<Marca> getFilteredMarcas() {
		return filteredMarcas;
	}

	public void setFilteredMarcas(List<Marca> filteredMarcas) {
		this.filteredMarcas = filteredMarcas;
	}

	@SuppressWarnings("unchecked")
	public void fillByMarca(Marca m) {
		try {
			setListado((List<Archivo>) audigoesLocal.findByNamedQuery(Archivo.class, "archivos.marca",
					new Object[] { m.getMarId() }));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean beforeSaveNew() {
		getRegistro().setInstitucion(getObjAppsSession().getUsuario().getInstitucion());
		return super.beforeSaveNew();
	}

	@Override
	public void afterSave() {
		super.afterSave();
	}

	public void handleFileUpload(FileUploadEvent event) {
		try {
			System.out.println(" archivo " + event.getFile().getFileName());
			this.getRegistro().setMarArcArchivo(event.getFile().getContent());
			this.getRegistro().setMarArcNombre(event.getFile().getFileName());
			this.getRegistro().setMarArcExt(event.getFile().getContentType());
			if (!getStatus().equals("NEW")) {
				addWarn(new FacesMessage(SYSTEM_NAME, "Archivo Guardado con Éxito"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Problemas al guardar archivo."));
		}

	}

	@SuppressWarnings("deprecation")
	public void downloadFile(ActionEvent event) {
		Marca m = (Marca) event.getComponent().getAttributes().get("marca");
		InputStream bis = new ByteArrayInputStream(m.getMarArcArchivo());
		marca = new DefaultStreamedContent(bis);
		marca = new DefaultStreamedContent(bis, m.getMarArcExt(), m.getMarArcNombre());
	}

	public ArchivoMB getArcMB() {
		return arcMB;
	}

	public void setArcMB(ArchivoMB arcMB) {
		this.arcMB = arcMB;
	}

	public Institucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public StreamedContent getMarca() {
		return marca;
	}

	public void setMarca(StreamedContent marca) {
		this.marca = marca;
	}

}
