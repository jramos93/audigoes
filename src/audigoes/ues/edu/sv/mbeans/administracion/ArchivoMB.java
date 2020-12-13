package audigoes.ues.edu.sv.mbeans.administracion;

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
import audigoes.ues.edu.sv.entities.ejecucion.ProcedimientoEjecucion;
import audigoes.ues.edu.sv.entities.informe.ActaLectura;
import audigoes.ues.edu.sv.entities.informe.CartaGerencia;
import audigoes.ues.edu.sv.entities.informe.CedulaNota;
import audigoes.ues.edu.sv.entities.informe.Convocatoria;
import audigoes.ues.edu.sv.entities.informe.Informe;
import audigoes.ues.edu.sv.entities.planeacion.PlanAnual;
import audigoes.ues.edu.sv.entities.planificacion.ProcedimientoPlanificacion;
import audigoes.ues.edu.sv.entities.seguimiento.Evidencia;

@ManagedBean(name = "arcMB")
@ViewScoped
public class ArchivoMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Archivo> filteredArchivos;

	private PlanAnual planAnual;

	private CedulaNota cedula;

	private StreamedContent pt;

	public ArchivoMB() {
		super(new Archivo());
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
	public void fillListado() {
		try {
			setListado((List<Archivo>) audigoesLocal.findByNamedQuery(Archivo.class, "configuracion.all",
					new Object[] { getObjAppsSession().getUsuario().getInstitucion().getInsId() }));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void fillByPlanificacion(ProcedimientoPlanificacion p) {
		try {
			setListado((List<Archivo>) audigoesLocal.findByNamedQuery(Archivo.class, "archivos.planificacion",
					new Object[] { p.getProId() }));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void fillByEjecucion(ProcedimientoEjecucion p) {
		try {
			setListado((List<Archivo>) audigoesLocal.findByNamedQuery(Archivo.class, "archivos.ejecucion",
					new Object[] { p.getPejId() }));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void fillByInforme(Informe i) {
		try {
			setListado((List<Archivo>) audigoesLocal.findByNamedQuery(Archivo.class, "archivos.informe",
					new Object[] { i.getInfId() }));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void fillByCarta(CartaGerencia c) {
		try {
			setListado((List<Archivo>) audigoesLocal.findByNamedQuery(Archivo.class, "archivos.carta",
					new Object[] { c.getCtgId() }));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void fillByConvocatoria(Convocatoria c) {
		try {
			setListado((List<Archivo>) audigoesLocal.findByNamedQuery(Archivo.class, "archivos.convocatoria",
					new Object[] { c.getCvcId() }));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void fillByActa(ActaLectura a) {
		try {
			setListado((List<Archivo>) audigoesLocal.findByNamedQuery(Archivo.class, "archivos.acta",
					new Object[] { a.getAclId() }));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void fillByCedula(CedulaNota c) {
		try {
			setListado((List<Archivo>) audigoesLocal.findByNamedQuery(Archivo.class, "archivos.cedula",
					new Object[] { c.getCedId() }));
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

		Archivo arch = (Archivo) value;
		return arch.getArcNombre().toLowerCase().contains(filterText) || arch.getArcId() == filterInt;
	}

	private int getInteger(String string) {
		try {
			return Integer.valueOf(string);
		} catch (NumberFormatException ex) {
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	public void fillAnexosPlanAnual() {
		try {
			setListado((List<Archivo>) audigoesLocal.findByNamedQuery(Archivo.class, "archivo.plan.anual",
					new Object[] { getPlanAnual().getPlaId() }));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onBorrar(Archivo a) {
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
			this.getRegistro().setArcArchivo(event.getFile().getContent());
			this.getRegistro().setCedula(cedula);
			this.getRegistro().setArcNombre(event.getFile().getFileName());
			this.getRegistro().setArcExt(event.getFile().getContentType());
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
		Archivo ev = (Archivo) event.getComponent().getAttributes().get("pt");
		InputStream bis = new ByteArrayInputStream(ev.getArcArchivo());
		pt = new DefaultStreamedContent(bis);
		pt = new DefaultStreamedContent(bis, ev.getArcExt(), ev.getArcNombre());
	}

	/* GETS y SETS */

	@Override
	public Archivo getRegistro() {
		return (Archivo) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Archivo> getListado() {
		return (List<Archivo>) super.getListado();
	}

	@Override
	public void afterSaveNew() {
		getListado().add(getRegistro());
		super.afterSaveNew();
	}

	public List<Archivo> getFilteredArchivos() {
		return filteredArchivos;
	}

	public void setFilteredArchivos(List<Archivo> filteredArchivos) {
		this.filteredArchivos = filteredArchivos;
	}

	public PlanAnual getPlanAnual() {
		return planAnual;
	}

	public void setPlanAnual(PlanAnual planAnual) {
		this.planAnual = planAnual;
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
