package audigoes.ues.edu.sv.mbeans.planeacion;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.omg.CORBA.Environment;
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.sun.jndi.toolkit.url.Uri;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;
import audigoes.ues.edu.sv.entities.planeacion.PlanAnual;
import audigoes.ues.edu.sv.mbeans.administracion.ArchivoMB;

@ManagedBean(name = "planMB")
@ViewScoped
public class PlaneacionMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<PlanAnual> filteredPlanAnuales;
	private List<Auditoria> auditoriasPlanList;
	private StreamedContent plan;

	@ManagedProperty(value = "#{audMB}")
	private AuditoriaMB audMB = new AuditoriaMB();
	
	@ManagedProperty(value = "#{arcMB}")
	private ArchivoMB arcMB = new ArchivoMB();

	public PlaneacionMB() {
		super(new PlanAnual());
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
			setListado((List<PlanAnual>) audigoesLocal.findByNamedQuery(PlanAnual.class,
					"plananual.get.all.institucion.activos",
					new Object[] { getObjAppsSession().getUsuario().getInstitucion().getInsId() }));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void afterRowSelect() {
		getAudMB().fillAuditoriasPlan();
		getArcMB().setPlanAnual(getRegistro());
		getArcMB().fillAnexosPlanAnual();
		super.afterRowSelect();
	}

	public void showAuditorias() {
		try {
			getAudMB().setPlanSelected(getRegistro());
			getAudMB().fillAuditoriasPlan();
			setStatus("VIEW_AUDITORIAS");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void generarInforme() {
		try {

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

		PlanAnual plan = (PlanAnual) value;
		return plan.getPlaNombre().toLowerCase().contains(filterText)
				|| plan.getPlaDescripcion().toLowerCase().contains(filterText) || plan.getPlaId() == filterInt;
	}

	private int getInteger(String string) {
		try {
			return Integer.valueOf(string);
		} catch (NumberFormatException ex) {
			return 0;
		}
	}
	
	@Override
	public boolean beforeCancel() {
		if(getAudMB().getStatus() != "SEARCH") {
			getAudMB().onCancel();
		}
		return super.beforeCancel();
	}

	@Override
	public void afterCancel() {
		try {
			TabView tv = (TabView) FacesContext.getCurrentInstance().getViewRoot()
					.findComponent("frmNewEdit:tvPlanAnual");
			tv.setActiveIndex(0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		super.afterCancel();
	}
	
	@Override
	protected void afterEdit() {
		getAudMB().setPlanSelected(getRegistro());
		getAudMB().onNew();
		super.afterEdit();
	}
	
	@Override
	public boolean beforeSaveNew() {
		if (getRegistro().getPlaFechaInicio().compareTo(getRegistro().getPlaFechaFin()) < 0) {
			getRegistro().setInstitucion(getObjAppsSession().getUsuario().getInstitucion());
			return super.beforeSaveNew();
		} else {
			addWarn(new FacesMessage(SYSTEM_NAME,
					"La fecha de inicio debe ser menor a la fecha de finalización, favor verificar las fechas ingresadas"));
			return false;
		}
	}
	
	@Override
	public boolean beforeDelete() {
		getAudMB().setPlanSelected(getRegistro());
		getAudMB().fillAuditoriasPlan();
		if(getAudMB().getListado().size() > 0) {
			addWarn(new FacesMessage(SYSTEM_NAME, "El plan ya tiene auditorías asignadas, no puede eliminarse"));
			return false;
		}
		return super.beforeDelete();
	}

	@Override
	public void afterDelete() {
		getListado().remove(getRegistro());
		super.afterDelete();
	}
	
	public void handleFileUpload(FileUploadEvent event) {
		try {
			this.arcMB.onNew();
			this.arcMB.getRegistro().setPlanAnual(getRegistro());
			this.arcMB.getRegistro().setArcArchivo(event.getFile().getContent());
			this.arcMB.getRegistro().setArcNombre(reemplazarTildes(event.getFile().getFileName()));
			this.arcMB.getRegistro().setArcExt(event.getFile().getContentType());
			this.arcMB.onSave();
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Problemas al guardar archivo."));
		}
		
	}
	
	/*public byte[] streamToByteArray(InputStream stream) {
		try {
			return IOUtils.toByteArray(stream);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}*/

	/* ver reporte */
	public void viewReporte() throws SQLException, ClassNotFoundException {
		//FacesContext facesContext = FacesContext.getCurrentInstance();
		//ServletContext servletContext = (ServletContext) facesContext.getExternalContext();
		
		try {
			//Se setea la variable reportId desde la vista con un setProperty
			getReport();
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//<h:commandLink actionListener="#{bean.viewReporte()} target="_blank/>
	}

	/* GETS y SETS */

	@Override
	public PlanAnual getRegistro() {
		return (PlanAnual) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PlanAnual> getListado() {
		return (List<PlanAnual>) super.getListado();
	}

	@Override
	public void afterSaveNew() {
		getListado().add(getRegistro());
		super.afterSaveNew();
	}

	public List<PlanAnual> getFilteredPlanAnuales() {
		return filteredPlanAnuales;
	}

	public void setFilteredPlanAnuales(List<PlanAnual> filteredPlanAnuales) {
		this.filteredPlanAnuales = filteredPlanAnuales;
	}
	
	public List<Auditoria> getAuditoriasPlanList() {
		return auditoriasPlanList;
	}

	public void setAuditoriasPlanList(List<Auditoria> auditoriasPlanList) {
		this.auditoriasPlanList = auditoriasPlanList;
	}

	public AuditoriaMB getAudMB() {
		return audMB;
	}

	public void setAudMB(AuditoriaMB audMB) {
		this.audMB = audMB;
	}

	public ArchivoMB getArcMB() {
		return arcMB;
	}

	public void setArcMB(ArchivoMB arcMB) {
		this.arcMB = arcMB;
	}

	@SuppressWarnings("deprecation")
	public StreamedContent getPlan() {
		
			try {
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				
				// Para definir los encabezados de pagina y los pie de pagina
				String str = "<html><head>"
						+ "<style>#header{position: running(header);} @page {margin: 70px 70px 100px;@bottom-right @top-center {content: element(header);} }</style>"
						+ "</head><body style='width:500px; font-size:smaller;'>";
				
				// Defino el texto del encabezado de pagina con el id header que es que se pone arriba running(header)
				str=str+"<div style='margin-top:20px;'> "
						+ "<strong><br><strong><h2 style='text-align:center'>" +getRegistro().getInstitucion().getInsNombre();
				str=str + "</strong></div></h2>"
						+ "<br><br><br><br><br><br><br>";
				
				str=str+"<div style='margin-top:20px;'> "
								+ "<strong><br><strong><h2 style='text-align:center'>";
				str=str + "</strong></div></h2>"
								+ "<br><br><br><br><br><br><br>";
				// Concateno el texto a agregar
				str=str+"<h1 style='text-align:center'>" +getRegistro().getPlaNombre();
				str=str+"</h1><h3 style='text-align:center'>" +getRegistro().getPlaAnio();
				str=str+ "</h3><br>"
						+ "<br><br><br><br><br><br><br>";
				str=str+"</h1><h3 style='text-align:center'>" +getRegistro().getPlaLugarFecha();
			
				str=str+"<div style= 'page-break-after:always'></div>";
				
				str=str+"<div style='margin-top:20px;'> "
						+ "<h3>INDICE</h3>";
				str=str+"<h5 style='text-align:justify'> I. Introduccion </h5>";
				str=str+"<h5 style='text-align:justify'> II. Vision </h5>";
				str=str+"<h5 style='text-align:justify'> III. Mision </h5>";
				str=str+"<h5 style='text-align:justify'> IV. Principios y Valores </h5>";
				str=str+"<h5 style='text-align:justify'> V. Objetivos generales y especificos </h5>";
				str=str+"<h5 style='text-align:justify'> VI. Riesgos </h5>";
				str=str+"<h5 style='text-align:justify'> VII. Programacion de Auditorias </h5>";
				str=str+"<h5 style='text-align:justify'> VIII. Anexos </h5>";
				
				str=str+"<div style= 'page-break-after:always'></div>";
						
				str=str+"<h3 style='text-align:justify'> I. Introduccion  </h3>";
				str=str+"<div style='text-align:justify'>"+getRegistro().getPlaIntroduccion();
				str=str + "</div>";
				
				str=str+"<h3 style='text-align:justify'> II. Vision </h3>";
				str=str+"<div style='text-align:justify'>"+getRegistro().getPlaVision();
				str=str + "</div>";
				
				str=str+"<h3 style='text-align:justify'> III. Mision  </h3>";
				str=str+"<div style='text-align:justify'>"+getRegistro().getPlaMision();
				str=str + "</div>";
				
				str=str+"<h3 style='text-align:justify'> IV. Principios y Valores</h3>";
				str=str+"<div style='text-align:justify'>"+getRegistro().getPlaPrincipiosValores();
				str=str + "</div>";
				
				str=str+"<h3 style='text-align:justify'>V. Objetivos generales y especificos</h3>";
				str=str+"<div style='text-align:justify'>"+getRegistro().getPlaObjetivos();
				str=str + "</div>";
				
				str=str+"<h3 style='text-align:justify'> VI. Riesgos  </h3>";
				str=str+"<div style='text-align:justify'>"+getRegistro().getPlaRiesgosConsiderados();
				str=str + "</div>";
				
				str=str+"<h3 style='text-align:justify'> VII. Programacion de Auditorias </h3>";
				if (getAudMB().getListado() != null) {
					for(Auditoria item:getAudMB().getListado()) {
						str=str+"<div style='text-align:justify'>"+item.getAudNombre();
						str=str + "</div>";
					}
				} else {
					str=str+"<div>";
					str=str + "</div>";
				}
				
				str=str+"<h3 style='text-align:justify'> VIII. Anexos</h3>";
				str=str+"<div style='text-align:justify'>";
				str=str + "</div>";
				
				str=str+"<div style= 'page-break-after:always'></div>";
				
				HtmlConverter.convertToPdf(str, os);
				
				InputStream is = new ByteArrayInputStream(os.toByteArray());
				return new DefaultStreamedContent(is, "application/pdf", "planAnual.pdf");
				
			} catch (Exception e) {
				e.printStackTrace();
				addWarn(new FacesMessage("Advertencia", "Hubo un error al generar el documento de la narrativa"));
			}
			return plan;
		}

		public void setPlan(StreamedContent plan) {
			this.plan = plan;
		}
			
}
