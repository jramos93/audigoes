package audigoes.ues.edu.sv.mbeans.administracion;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.event.FileUploadEvent;

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
			setListado((List<NormativaCedula>) audigoesLocal.findByNamedQuery(NormativaCedula.class, "normativacedula.all", new Object[] {}));
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
				
				/*
				 * PreparedStatement st =
				 * prepareStatement("INSERT INTO normativa_cedula norRuta VALUES (?)");
				 * st.setBinaryStream(1, file.getInputStream());
				 * 
				 * st.executeUpdate(); close();
				 */
				 
				FacesMessage message = new FacesMessage("Exito",file.getFileName() + "Fue Subido");
				FacesContext.getCurrentInstance().addMessage(null, message);	
			}
		}catch (Exception e) {
			FacesMessage message = new FacesMessage("Error de Conexion");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}
	public void fileUploadListener(FileUploadEvent e){
		// Get uploaded file from the FileUploadEvent
		String ruta = obtenerRuta();
		getRegistro().setNorRuta(ruta + e.getFile().getFileName());
		
		// Print out the information of the file
		System.out.println("Uploaded File Name Is :: "+file.getFileName()+" :: Uploaded File Size :: "+file.getSize());
	}
	
	
	private String obtenerRuta() {
		// TODO Auto-generated method stub
		return "C:/Users/audig/docs/";
	}

	private void close() {
		// TODO Auto-generated method stub
		
	}

	private PreparedStatement prepareStatement(String string) {
		// TODO Auto-generated method stub
		return null;
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
		if(this.tipoList==null) {
			this.tipoList=new ArrayList<>();
			this.tipoList.add(new SelectItem(1, "Normativa"));
			this.tipoList.add(new SelectItem(0, "Cedula"));
		}
		return tipoList;
	}

	public void setTipoList(List<SelectItem> tipoList) {
		this.tipoList = tipoList;
	}
	
}
