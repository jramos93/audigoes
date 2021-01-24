package audigoes.ues.edu.sv.mbeans.informe;

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
import javax.faces.context.FacesContext;

import org.primefaces.component.tabview.TabView;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.source.ByteArrayOutputStream;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.informe.Convocatoria;
import audigoes.ues.edu.sv.entities.informe.Informe;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;
import audigoes.ues.edu.sv.mbeans.administracion.ArchivoMB;

@ManagedBean(name = "convocatoriaMB")
@ViewScoped
public class ConvocatoriaMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Convocatoria> filteredConvocatorias;
	private StreamedContent convocatoria;
	private Informe informe;

	@ManagedProperty(value = "#{arcMB}")
	private ArchivoMB arcMB = new ArchivoMB();

	public ConvocatoriaMB() {
		super(new Convocatoria());
	}

	@PostConstruct
	public void init() {
		try {
			super.init();
			fillListado();
			this.arcMB.fillByConvocatoria(getRegistro());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onSave() {
		if (getRegistro().getCvcId() > 0) {
			setStatus("EDIT");
		} else {
			setStatus("NEW");
		}
		super.onSave();
	}

	@Override
	public boolean beforeSaveNew() {
		getRegistro().setInforme(getInforme());
		getRegistro().setRegActivo(1);
		getRegistro().setUsuCrea(getObjAppsSession().getUsuario().getUsuUsuario());
		getRegistro().setFecCrea(getToday());
		return super.beforeSaveNew();
	}

	@SuppressWarnings("unchecked")
	public void fillListado() {
		try {
			setListado((List<Convocatoria>) audigoesLocal.findByNamedQuery(Convocatoria.class, "convocatoria.all",
					new Object[] {}));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void fillConvocatoria() {
		try {
			setListado((List<Convocatoria>) audigoesLocal.findByNamedQuery(Convocatoria.class,
					"convocatoria.by.informe", new Object[] { getInforme().getInfId() }));
			if (!getListado().isEmpty()) {
				setRegistro(getListado().get(0));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void buscaConvocatoria(Informe informe) {
		try {
			System.out.println("buscar");
			setListado((List<Convocatoria>) audigoesLocal.findByNamedQuery(Convocatoria.class, "convocatoria.by.informe",
					new Object[] { informe.getInfId() }));
			if (getListado() != null && !getListado().isEmpty()) {
				setRegistro(getListado().get(0));
			} else {
				onNew();
				getRegistro().setInforme(informe);
				getRegistro().setFecCrea(getToday());
				getRegistro().setUsuCrea(getObjAppsSession().getUsuario().getUsuUsuario());
				getRegistro().setRegActivo(1);
				;
				audigoesLocal.insert(getRegistro());
				inicializarValores();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void inicializarValores() {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("d 'de' MMM 'de' yyyy");
			String intro = "";

			intro = intro + "<p>San Salvador, " + formatter.format(getToday())
					+ "<br/><br/><strong>Se&ntilde;or (es)<br />\r\n"
					+ "Ministro/Consejo Directivo/Junta Directiva/Consejo Municipal<br />\r\n"
					+ "Presente</strong></p>\r\n" + "\r\n" + "<p>";
			intro = intro
					+ "<p style=\"text-align:justify\">Atentamente hago de su conocimiento que, con la finalidad de cumplir lo dispuesto en "
					+ "el Art. 33 de la Ley de la Corte de Cuentas de la Rep&uacute;blica, esta Unidad de Auditor&iacute;a Interna, "
					+ "ha preparado el Borrador del Informe <strong>"+getRegistro().getInforme().getAuditoria().getAudNombre()+"</strong>, "
					+ "correspondiente al per&iacute;odo del <strong>"
					+formatter.format(getRegistro().getInforme().getAuditoria().getAudFechaInicioProgramado())
					+" al "+formatter.format(getRegistro().getInforme().getAuditoria().getAudFechaFinProgramado())+"</strong>, "
					+ "del cual se remite un ejemplar, adjunto a la presente.</p>\r\n"
					+ "\r\n"
					+ "<p style=\"text-align:justify\">Al respecto, es oportuno mencionar que por tratarse de resultados preliminares, "
					+ "tal informe puede ser modificado como producto de la presentaci&oacute;n de evidencia documental, "
					+ "as&iacute; como del an&aacute;lisis de explicaciones y comentarios adicionales que se presenten "
					+ "por escrito el d&iacute;a de la lectura de dicho informe; para tal efecto, se le convoca a una "
					+ "reuni&oacute;n en <strong>(especificar el lugar)</strong>, a las <strong>x: xx horas del d&iacute;a xx de xx "
					+ "del corriente a&ntilde;o.</strong></p>\r\n"
					+ "\r\n"
					+ "<p style=\"text-align:justify\">Lo anterior se notifica a usted para los efectos legales consiguientes , "
					+ "aprovecho la ocasi&oacute;n para expresarles mis muestras de consideraci&oacute;n y estima.</p>\r\n";

			getRegistro().setCvcCuerpo(intro);
			System.out.println("inici");
		} catch (Exception e) {
		}
	}

	public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
		if (filterText == null || filterText.equals("")) {
			return true;
		}
		int filterInt = getInteger(filterText);

		Convocatoria convocatoria = (Convocatoria) value;
		return convocatoria.getCvcEncabezado().toLowerCase().contains(filterText);
	}

	private int getInteger(String string) {
		try {
			return Integer.valueOf(string);
		} catch (NumberFormatException ex) {
			return 0;
		}
	}

	@Override
	public void afterCancel() {
		try {
			TabView tv = (TabView) FacesContext.getCurrentInstance().getViewRoot()
					.findComponent("frmConvocatoria:tvConvocatoria");
			tv.setActiveIndex(0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		super.afterCancel();
	}

	/* GETS y SETS */

	@Override
	public Convocatoria getRegistro() {
		return (Convocatoria) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Convocatoria> getListado() {
		return (List<Convocatoria>) super.getListado();
	}

	@Override
	public void afterSaveNew() {
		getListado().add(getRegistro());
		super.afterSaveNew();
	}

	public List<Convocatoria> getFilteredInformes() {
		return filteredConvocatorias;
	}

	public void setFilteredConvocatorias(List<Convocatoria> filteredConvocatorias) {
		this.filteredConvocatorias = filteredConvocatorias;
	}

	public Informe getInforme() {
		return informe;
	}

	public void setInforme(Informe informe) {
		this.informe = informe;
	}

	@SuppressWarnings("deprecation")
	public StreamedContent getConvocatoria() {

		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();

			// Para definir los encabezados de pagina y los pie de pagina
			String str = "<html><head>"
					+ "<style>#header{position: running(header);} @page {margin: 100px 100px 100px;@bottom-right @top-center {content: element(header);} }</style>"
					+ "</head><body style='width:500px; font-size:smaller;'>";

			// Defino el texto del encabezado de pagina con el id header que es que se pone
			// arriba running(header)
			str = str + "<div id='header' style='margin-top:20px;'> " + "<strong></strong><br/><strong></strong></div>";
			// Concateno el texto a agregar
			str = str + "<div style='text-align:justify'>" + getRegistro().getCvcEncabezado();
			str = str + "</div></body>";

			str = str + "<div style='text-align:justify'>" + getRegistro().getCvcDestinatario();
			str = str + "</div></body>";

			str = str + "<div style='text-align:justify'>" + getRegistro().getCvcCuerpo();
			str = str + "</div></body></html>";

			HtmlConverter.convertToPdf(str, os);

			InputStream is = new ByteArrayInputStream(os.toByteArray());
			return new DefaultStreamedContent(is, "application/pdf", "convocatoria.pdf");
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage("Advertencia", "Hubo un error al generar el documento de la convocatoria"));
		}
		return convocatoria;
	}

	public void setConvocatoria(StreamedContent convocatoria) {
		this.convocatoria = convocatoria;
	}

	public void handleFileUpload(FileUploadEvent event) {
		try {
			System.out.println(" archivo " + event.getFile().getFileName());
			if (getStatus().equals("NEW")) {
				onSave();
				onEdit();
			}
			this.arcMB.onNew();
			this.arcMB.getRegistro().setConvocatoria(getRegistro());
			this.arcMB.getRegistro().setArcArchivo(event.getFile().getContent());
			this.arcMB.getRegistro().setArcNombre(event.getFile().getFileName());
			this.arcMB.getRegistro().setArcExt(event.getFile().getContentType());
			this.arcMB.onSave();
			// this.arcMB.afterSaveNew();
			this.arcMB.fillByConvocatoria(getRegistro());
			if (!getStatus().equals("NEW")) {
				addWarn(new FacesMessage(SYSTEM_NAME, "Archivo Guardado con Éxito"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Problemas al guardar archivo."));
		}

	}

	public ArchivoMB getArcMB() {
		return arcMB;
	}

	public void setArcMB(ArchivoMB arcMB) {
		this.arcMB = arcMB;
	}

}
