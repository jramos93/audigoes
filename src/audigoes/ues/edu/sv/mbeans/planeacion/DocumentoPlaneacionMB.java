package audigoes.ues.edu.sv.mbeans.planeacion;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;
import audigoes.ues.edu.sv.entities.planeacion.DocumentoPlan;
import audigoes.ues.edu.sv.entities.planeacion.PlanAnual;

@ManagedBean(name = "docPlanMB")
@ViewScoped
public class DocumentoPlaneacionMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<DocumentoPlan> filteredDocumentoPlan;

	public DocumentoPlaneacionMB() {
		super(new DocumentoPlan());
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
			setListado((List<Auditoria>) audigoesLocal.findByNamedQuery(Auditoria.class,
					"auditoria.get.all.institucion",
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

		Auditoria auditoria = (Auditoria) value;
		return auditoria.getAudNombre().toLowerCase().contains(filterText)
				|| auditoria.getAudDescripcion().toLowerCase().contains(filterText) || auditoria.getAudId() == filterInt;
	}

	private int getInteger(String string) {
		try {
			return Integer.valueOf(string);
		} catch (NumberFormatException ex) {
			return 0;
		}
	}
	
	@Override
	public void afterNew() {
		super.afterNew();
	}
	
	@Override
	public void afterSaveNew() {
		getListado().add(getRegistro());
		super.afterSaveNew();
	}

	/* GETS y SETS */

	@Override
	public DocumentoPlan getRegistro() {
		return (DocumentoPlan) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentoPlan> getListado() {
		return (List<DocumentoPlan>) super.getListado();
	}

	public List<DocumentoPlan> getFilteredDocumentoPlan() {
		return filteredDocumentoPlan;
	}

	public void setFilteredDocumentoPlan(List<DocumentoPlan> filteredDocumentoPlan) {
		this.filteredDocumentoPlan = filteredDocumentoPlan;
	}

}
