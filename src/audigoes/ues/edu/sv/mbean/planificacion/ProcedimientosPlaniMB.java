package audigoes.ues.edu.sv.mbean.planificacion;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
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
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;
import audigoes.ues.edu.sv.entities.planificacion.ProcedimientoPlanificacion;
import audigoes.ues.edu.sv.entities.planificacion.ProgramaPlanificacion;
import audigoes.ues.edu.sv.mbeans.administracion.ArchivoMB;

@ManagedBean(name = "proplaMB")
@ViewScoped
public class ProcedimientosPlaniMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<ProcedimientoPlanificacion> filteredProcedimiento;
	private Auditoria auditoria;
	private ProgramaPlanificacion programa;

	private StreamedContent narrativa;

	@ManagedProperty(value = "#{arcMB}")
	private ArchivoMB arcMB = new ArchivoMB();

	public ProcedimientosPlaniMB() {
		super(new ProcedimientoPlanificacion());
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
			setListado(
					(List<ProcedimientoPlanificacion>) audigoesLocal.findByNamedQuery(ProcedimientoPlanificacion.class,
							"procedimientos.planificacion.by.programa", new Object[] { programa.getPrpId() }));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handleFileUpload(FileUploadEvent event) {
		try {
			System.out.println(" archivo " + event.getFile().getFileName());
			if (getStatus().equals("NEW")) {
				onSave();
				onEdit();
			}
			arcMB.onNew();
			arcMB.getRegistro().setProcedimientoPlanificacion(getRegistro());
			arcMB.getRegistro().setArcArchivo(event.getFile().getContent());
			arcMB.getRegistro().setArcNombre(event.getFile().getFileName());
			arcMB.getRegistro().setArcExt(event.getFile().getContentType());
			arcMB.getRegistro().setFecCrea(getToday());
			arcMB.getRegistro().setUsuCrea(getObjAppsSession().getUsuario().getUsuUsuario());
			arcMB.getRegistro().setRegActivo(1);
			audigoesLocal.insert(arcMB.getRegistro());
			// this.arcMB.afterSaveNew();
			// this.arcMB.getListado().add(this.arcMB.getRegistro());
			if (!getStatus().equals("NEW")) {
				addWarn(new FacesMessage(SYSTEM_NAME, "Archivo Guardado con Éxito"));
			}
			arcMB.fillByPlanificacion(getRegistro());
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Problemas al guardar archivo."));
		}

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

		ProcedimientoPlanificacion proc = (ProcedimientoPlanificacion) value;
		return proc.getProNombre().toLowerCase().contains(filterText);
	}

	@Override
	public void afterCancel() {
		super.afterCancel();
	}

	@Override
	public void afterNew() {
		// TODO Auto-generated method stub
		super.afterNew();
	}

	@Override
	protected void afterEdit() {
		// TODO Auto-generated method stub
		super.afterEdit();
		arcMB.fillByPlanificacion(getRegistro());
	}

	@Override
	public boolean beforeSaveNew() {
		getRegistro().setProFechaElaboro(getToday());
		getRegistro().setUsuario1(getObjAppsSession().getUsuario());
		getRegistro().setProgramaPlanificacion(programa);
		if (getRegistro().getProFechaInicio() == null) {
			getRegistro().setProFechaInicio(getToday());
		}
		return super.beforeSaveNew();
	}

	@Override
	public void onDelete() {
		// TODO Auto-generated method stub
		super.onDelete();
	}

	@Override
	public void onShowSelected() {
		// TODO Auto-generated method stub
		super.onShowSelected();
	}

	@Override
	public boolean beforeDelete() {
		try {
			arcMB.fillByPlanificacion(getRegistro());
			if (arcMB.getListado().size() > 0) {
				addWarn(new FacesMessage("Error",
						"El procedimiento posee archivos vinculador, debe eliminarlos primero"));
				return false;
			}
		} catch (Exception e) {
			addWarn(new FacesMessage("Error", "Hubo un problema al momento de eliminar"));
			return false;
		}

		return super.beforeDelete();
	}

	/* GETS y SETS */

	@Override
	public ProcedimientoPlanificacion getRegistro() {
		return (ProcedimientoPlanificacion) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProcedimientoPlanificacion> getListado() {
		return (List<ProcedimientoPlanificacion>) super.getListado();
	}

	@Override
	public void afterSaveNew() {
		getListado().add(getRegistro());
		super.afterSaveNew();
	}

	public List<ProcedimientoPlanificacion> getFilteredProcedimiento() {
		return filteredProcedimiento;
	}

	public void setFilteredProcedimiento(List<ProcedimientoPlanificacion> filteredProcedimiento) {
		this.filteredProcedimiento = filteredProcedimiento;
	}

	public Auditoria getAuditoria() {
		return auditoria;
	}

	public void setAuditoria(Auditoria auditoria) {
		this.auditoria = auditoria;
	}

	public ProgramaPlanificacion getPrograma() {
		return programa;
	}

	public void setPrograma(ProgramaPlanificacion programa) {
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
			SimpleDateFormat  formatter = new SimpleDateFormat("dd/MM/yyyy");
			ByteArrayOutputStream os = new ByteArrayOutputStream();

			// Para definir los encabezados de pagina y los pie de pagina
			String str = "<html><head>"
					+ "<style>#header{position: running(header);} @page {margin: 150px 70px 100px;@bottom-right {content: \"Página \" counter(page) \" de \" counter(pages); } @top-center {content: element(header);} }</style>"
					+ "</head><body style='width:500px; font-size:smaller;'>";

			// Defino el texto del encabezado de pagina con el id header que es que se pone
			// arriba running(header)
			str = str + "<div id='header' style='margin-top:20px;'> "
					+ "<strong>Unidad de Auditoría Interna</strong><br/><strong>" + getRegistro().getProgramaPlanificacion()
							.getAuditoria().getPlanAnual().getInstitucion().getInsNombre()
					+ "</strong><br/><strong>Narrativa de Procedimiento de Planificación</strong></div>";
			// Concateno el texto a agregar
			str = str +"<p><strong>Procedimiento</strong>: "+getRegistro().getProNombre()+"</p>\r\n"
					+ "\r\n"
					+ "<p><strong>Referencia: </strong>"+getRegistro().getProReferencia()+"</p>\r\n"
					+ "\r\n"
					+ "<p><strong>Elaborado Por: &nbsp;</strong>"+getRegistro().getUsuario1().getUsuNombre()+"</p>\r\n"
					+ "\r\n"
					+ "<p><strong>Fecha Elaboraci&oacute;n: &nbsp;</strong>&nbsp;"+formatter.format(getRegistro().getProFechaElaboro())+"</p>\r\n"
					+ "\r\n"
					+ "<p>&nbsp;</p>\r\n"
					+ "\r\n"
					+ "<p><strong>Narrativa:</strong></p>";
			str = str + "<div style='text-align:justify'>" + getRegistro().getProNarrativa();
			str = str + "</div></body></html>";

			HtmlConverter.convertToPdf(str, os);

			InputStream is = new ByteArrayInputStream(os.toByteArray());
			return new DefaultStreamedContent(is, "application/pdf", "narrativa-planificacion.pdf");
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
