package audigoes.ues.edu.sv.mbean.planificacion;

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

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.source.ByteArrayOutputStream;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.ejecucion.ProcedimientoEjecucion;
import audigoes.ues.edu.sv.entities.ejecucion.ProgramaEjecucion;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;
import audigoes.ues.edu.sv.mbeans.administracion.ArchivoMB;

@ManagedBean(name = "proejeMB")
@ViewScoped
public class ProcedimientosEjeMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<ProcedimientoEjecucion> filteredProcedimiento;
	private Auditoria auditoria;
	private ProgramaEjecucion programa;

	private StreamedContent narrativa;

	@ManagedProperty(value = "#{arcMB}")
	private ArchivoMB arcMB = new ArchivoMB();

	public ProcedimientosEjeMB() {
		super(new ProcedimientoEjecucion());
	}

	@PostConstruct
	public void init() {
		try {
			super.init();
			// fillProcedimientos();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void fillProcedimientos() {
		try {
			setListado((List<ProcedimientoEjecucion>) audigoesLocal.findByNamedQuery(ProcedimientoEjecucion.class,
					"procedimientos.ejecucion.by.programa", new Object[] { programa.getPreId() }));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void afterEdit() {
		// TODO Auto-generated method stub
		super.afterEdit();
		arcMB.fillByEjecucion(getRegistro());
	}

	public void showAuditorias() {
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void afterSave() {
		super.afterSave();
		onEdit();
	}

	public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
		if (filterText == null || filterText.equals("")) {
			return true;
		}

		ProcedimientoEjecucion proc = (ProcedimientoEjecucion) value;
		return proc.getPejNombre().toLowerCase().contains(filterText);
	}
	
	@Override
	public void onRowSelect() {
		super.onRowSelect();
	}

	@Override
	public void afterCancel() {
		super.afterCancel();
	}

	@Override
	public boolean beforeSaveNew() {
		getRegistro().setUsuario1(getObjAppsSession().getUsuario());
		getRegistro().setProgramaEjecucion(programa);
		getRegistro().setPejFechaElaboro(getToday());
		return super.beforeSaveNew();
	}

	public void handleFileUpload(FileUploadEvent event) {
		try {
			System.out.println(" archivo " + event.getFile().getFileName());
			if (getStatus().equals("NEW")) {
				onSave();
				onEdit();
			}
			this.arcMB.onNew();
			this.arcMB.getRegistro().setProcedimientoEjecucion(getRegistro());
			this.arcMB.getRegistro().setArcArchivo(event.getFile().getContent());
			this.arcMB.getRegistro().setArcNombre(event.getFile().getFileName());
			this.arcMB.getRegistro().setArcExt(event.getFile().getContentType());
			this.arcMB.getRegistro().setFecCrea(getToday());
			this.arcMB.getRegistro().setUsuCrea(getObjAppsSession().getUsuario().getUsuUsuario());
			this.arcMB.getRegistro().setRegActivo(1);
			audigoesLocal.insert(this.arcMB.getRegistro());
			// this.arcMB.afterSaveNew();
			this.arcMB.getListado().add(this.arcMB.getRegistro());
			if (!getStatus().equals("NEW")) {
				addWarn(new FacesMessage(SYSTEM_NAME, "Archivo Guardado con Éxito"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Problemas al guardar archivo."));
		}

	}

	/* GETS y SETS */

	@Override
	public ProcedimientoEjecucion getRegistro() {
		return (ProcedimientoEjecucion) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProcedimientoEjecucion> getListado() {
		return (List<ProcedimientoEjecucion>) super.getListado();
	}

	@Override
	public void afterSaveNew() {
		getListado().add(getRegistro());
		super.afterSaveNew();
	}

	public List<ProcedimientoEjecucion> getFilteredProcedimiento() {
		return filteredProcedimiento;
	}

	public void setFilteredProcedimiento(List<ProcedimientoEjecucion> filteredProcedimiento) {
		this.filteredProcedimiento = filteredProcedimiento;
	}

	public Auditoria getAuditoria() {
		return auditoria;
	}

	public void setAuditoria(Auditoria auditoria) {
		this.auditoria = auditoria;
	}

	public ProgramaEjecucion getPrograma() {
		return programa;
	}

	public void setPrograma(ProgramaEjecucion programa) {
		this.programa = programa;
	}

	public ArchivoMB getArcMB() {
		return arcMB;
	}

	public void setArcMB(ArchivoMB arcMB) {
		this.arcMB = arcMB;
	}

	public StreamedContent getNarrativa() {

		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();

			// Para definir los encabezados de pagina y los pie de pagina
			String str = "<html><head>"
					+ "<style>#header{position: running(header);} @page {margin: 150px 70px 100px;@bottom-right {content: \"Página \" counter(page) \" de \" counter(pages); } @top-center {content: element(header);} }</style>"
					+ "</head><body style='width:500px; font-size:smaller;'>";

			// Defino el texto del encabezado de pagina con el id header que es que se pone
			// arriba running(header)
			str = str + "<div id='header' style='margin-top:20px;'> "
					+ "<strong>Unidad de Auditoría Interna</strong><br/><strong>Narrativa de Procedimiento de Planificación</strong></div>";
			// Concateno el texto a agregar
			str = str + "<div style='text-align:justify'>" + getRegistro().getPejNarrativa();
			str = str + "</div></body></html>";

			HtmlConverter.convertToPdf(str, os);

			InputStream is = new ByteArrayInputStream(os.toByteArray());
			return new DefaultStreamedContent(is, "application/pdf", "narrativa.pdf");
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage("Advertencia", "Hubo un error al generar el documento de la narrativa"));
		}
		return narrativa;
	}

	public void setNarrativa(StreamedContent narrativa) {
		this.narrativa = narrativa;
	}

}
