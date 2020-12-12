package audigoes.ues.edu.sv.mbeans.informe;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.mail.Address;

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
import audigoes.ues.edu.sv.entities.SuperEntity;
import audigoes.ues.edu.sv.entities.informe.ActaLectura;
import audigoes.ues.edu.sv.entities.informe.CartaGerencia;
import audigoes.ues.edu.sv.entities.informe.Convocatoria;
import audigoes.ues.edu.sv.entities.informe.Informe;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;
import audigoes.ues.edu.sv.entities.planificacion.ProgramaPlanificacion;
import audigoes.ues.edu.sv.mbeans.administracion.ArchivoMB;
import audigoes.ues.edu.sv.mbeans.planeacion.AuditoriaMB;
import audigoes.ues.edu.sv.util.SendMailAttach;

@ManagedBean(name = "informeMB")
@ViewScoped
public class InformeMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Informe> filteredInformes;
	private StreamedContent informe;
	private String textoCorreo="";
	
	@ManagedProperty(value = "#{actaLecturaMB}")
	private ActaLecturaMB actLecMB = new ActaLecturaMB();
	
	@ManagedProperty(value = "#{cartaGerenciaMB}")
	private CartaGerenciaMB cartaGMB = new CartaGerenciaMB();
	
	@ManagedProperty(value= "#{convocatoriaMB}")
	private ConvocatoriaMB convMB = new ConvocatoriaMB();
	
	@ManagedProperty(value = "#{arcMB}")
	private ArchivoMB arcMB = new ArchivoMB();
	
	private Auditoria auditoria;
	private int versionActual;

	public InformeMB() {
		super(new Informe());
	}

	@PostConstruct
	public void init() {
		try {
			Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			setAuditoria((Auditoria) sessionMap.get("auditoria"));
			setInforme();
			this.arcMB.fillByInforme(getRegistro());
			System.out.println(getAuditoria().getAudId());
			super.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onSave() {
		if(getRegistro().getInfId() > 0 ) {
			setStatus("EDIT");
		} else {
			setStatus("NEW");
		}
		super.onSave();
	}
	
	public void setInforme() {
		try {
			setRegistro((Informe)audigoesLocal.findByNamedQuery(Informe.class,"informe.by.auditoria",
					new Object[] {getAuditoria().getAudId()}).get(0));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean beforeSaveNew() {
		getRegistro().setAuditoria(getAuditoria());
		getRegistro().setRegActivo(1);
		getRegistro().setUsuCrea(getObjAppsSession().getUsuario().getUsuUsuario());
		getRegistro().setFecCrea(getToday());
		return super.beforeSaveNew();
	}
	
	@SuppressWarnings("unchecked")
	public void fillListado() {
		try {
			setListado((List<Informe>) audigoesLocal.findByNamedQuery(Informe.class,"informe.all",
					new Object[] {}));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	public void fillInforme() {
		try {
			setListado((List<Informe>) audigoesLocal.findByNamedQuery(Informe.class,
					"informe.by.auditoria",
					new Object[] {getAuditoria().getAudId()}));
			if(!getListado().isEmpty()) {
				setRegistro(getListado().get(0));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void fillActaLectura() {
		try {
			setListado((List<ActaLectura>) audigoesLocal.findByNamedQuery(ActaLectura.class,
					"actaLectura.by.informe",
					new Object[] {getRegistro().getInfId()}));
			if(!getListado().isEmpty()) {
				setRegistro(getListado().get(0));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void fillConvocatoria() {
		try {
			setListado((List<Convocatoria>) audigoesLocal.findByNamedQuery(Convocatoria.class,
					"convocatoria.by.informe",
					new Object[] {getRegistro().getInfId()}));
			if(!getListado().isEmpty()) {
				setRegistro(getListado().get(0));
			}
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

		Informe informe = (Informe) value;
		return informe.getInfTitulo().toLowerCase().contains(filterText)
				|| informe.getInfVersion() == filterInt || informe.getInfId() == filterInt;
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
			TabView tv = (TabView) FacesContext.getCurrentInstance().getViewRoot().findComponent("frmInforme:tvInforme");
			tv.setActiveIndex(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		super.afterCancel();
	}
	
	public void showInforme() {
		fillInforme();
		if (getRegistro().getInfId() > 0) {
			setVersionActual(getRegistro().getInfVersion());
		}else {
			setVersionActual(1);
			getRegistro().setInfVersion(getVersionActual());
		}
		setStatus("INFORME");
	}
	
	public void showActaLectura() {
		this.getConvMB().setInforme(getRegistro());
		this.getConvMB().fillConvocatoria();
		this.getActLecMB().setInforme(getRegistro());
		this.getActLecMB().fillActaLectura();
		//fillActaLectura();
		//fillConvocatoria();
		setStatus("ACTA_LECTURA");
	}
	
	public void showCarta() {
		//fillCartaGerencia();
		this.getCartaGMB().setInforme(getRegistro());
		this.getCartaGMB().fillCartaGerencia();
		setStatus("CARTA_GERENCIA");
	}
	
	/* GETS y SETS */

	@Override
	public Informe getRegistro() {
		return (Informe) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Informe> getListado() {
		return (List<Informe>) super.getListado();
	}

	public List<Informe> getFilteredInformes() {
		return filteredInformes;
	}

	public void setFilteredInformes(List<Informe> filteredInformes) {
		this.filteredInformes = filteredInformes;
	}

	public ActaLecturaMB getActLecMB() {
		return actLecMB;
	}

	public void setActLecMB(ActaLecturaMB actLecMB) {
		this.actLecMB = actLecMB;
	}

	public CartaGerenciaMB getCartaGMB() {
		return cartaGMB;
	}

	public void setCartaGMB(CartaGerenciaMB cartaGMB) {
		this.cartaGMB = cartaGMB;
	}

	public ConvocatoriaMB getConvMB() {
		return convMB;
	}

	public void setConvMB(ConvocatoriaMB convMB) {
		this.convMB = convMB;
	}

	public Auditoria getAuditoria() {
		return auditoria;
	}

	public void setAuditoria(Auditoria auditoria) {
		this.auditoria = auditoria;
	}

	public int getVersionActual() {
		return versionActual;
	}

	public void setVersionActual(int versionActual) {
		this.versionActual = versionActual;
	}
	
@SuppressWarnings("deprecation")
public StreamedContent getInforme() {
	
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			
			// Para definir los encabezados de pagina y los pie de pagina
			String str = "<html><head>"
					+ "<style>#header{position: running(header);} @page {margin: 70px 70px 100px;@bottom-right @top-center {content: element(header);} }</style>"
					+ "</head><body style='width:500px; font-size:smaller;'>";
			
			// Defino el texto del encabezado de pagina con el id header que es que se pone arriba running(header)
			str=str+"<div style='margin-top:20px;'> "
					+ "<strong><br><strong><h2 style='text-align:center'>" +getRegistro().getInfPortada();
			str=str + "</strong></div></h2>"
					+ "<br><br><br><br><br><br><br> <br><br><br><br><br><br>";
			// Concateno el texto a agregar
			str=str+"<h1 style='text-align:center'> Informe de " +getRegistro().getAuditoria().getAudNombre();
			str=str+"</h1><h3 style='text-align:center'>PERIODO DEL " +formatter.format(getRegistro().getAuditoria().getAudFechaInicioProgramado());
			str=str+"   AL "+formatter.format(getRegistro().getAuditoria().getAudFechaFinProgramado());
			str=str+ "</h3><br>";
		
			str=str+"<div style= 'page-break-after:always'></div>";
			
			str=str+"<div style='margin-top:20px;'> "
					+ "<h3>INDICE</h3>";
			str=str+"<h5 style='text-align:justify'> I. Objetivos de la Auditoria </h5>";
			str=str+"<h5 style='text-align:justify'> II. Alcance de la Auditoria </h5>";
			str=str+"<h5 style='text-align:justify'> III. Procedimientos de Auditoria Aplicados </h5>";
			str=str+"<h5 style='text-align:justify'> IV. Resultados de la Auditoria </h5>";
			str=str+"<h5 style='text-align:justify'> V. Seguimiento a las recomendaciones de auditorias anteriores </h5>";
			str=str+"<h5 style='text-align:justify'> VI. Recomendacioes de la Auditoria </h5>";
			str=str+"<h5 style='text-align:justify'> VII. Conclusion </h5>";
			str=str+"<h5 style='text-align:justify'> VIII. Parrafo Aclaratorio</h5>";
			
			str=str+"<div style= 'page-break-after:always'></div>";
			
			str=str+"<div> "
					+ "<strong>Señor(es)</strong><strong>" +getRegistro().getInfDestinatario();
			str=str + "</strong></div>"
					+ "<br>";
			
			str=str+ "<div style='text-align:justify'> El presente informe contiene los resultados de " +getRegistro().getAuditoria().getAudNombre();
			str=str+ ", por el periodo del " + getRegistro().getAuditoria().getAudFechaInicioReal();
			str=str+" al " +getRegistro().getAuditoria().getAudFechaFinReal();
			str=str+". La auditoría fue realizada en cumplimiento a los Arts. 30 y 31 de la Ley de la Corte de Cuentas de la República, a las Normas de Auditoría\r\n" + 
					"Interna del Sector Gubernamental, emitidas por la Corte de Cuentas de la República.</div>";
			
			str=str+"<h3 style='text-align:justify'> I. Objetivos de la Auditoria </h3>";
			str=str+"<div>"+getRegistro().getAuditoria().getAudObjetivos();
			str=str + "</div>";
			
			str=str+"<h3 style='text-align:justify'> II. Alcance de la Auditoria </h3>";
			str=str+"<div>"+getRegistro().getAuditoria().getAudAlcances();
			str=str + "</div>";
			
			str=str+"<h3 style='text-align:justify'> III. Procedimientos de Auditoria Aplicados </h3>";
			str=str+"<div>"+getRegistro().getInfProcedimientos();
			str=str + "</div>";
			
			str=str+"<h3 style='text-align:justify'> IIV. Resultados de la Auditoria</h3>";
			str=str+"<div>"+getRegistro().getInfResultados();
			str=str + "</div>";
			
			str=str+"<h3 style='text-align:justify'>V. Seguimiento a las recomendaciones de auditorias anteriores</h3>";
			str=str+"<div>"+getRegistro().getInfSeguimiento();
			str=str + "</div>";
			
			str=str+"<h3 style='text-align:justify'> VI. Recomendacioes de la Auditoria </h3>";
			str=str+"<div>"+getRegistro().getInfRecomendaciones();
			str=str + "</div>";
			
			str=str+"<h3 style='text-align:justify'> VII. Conclusion </h3>";
			str=str+"<div>"+getRegistro().getInfConclusion();
			str=str + "</div>";
			
			str=str+"<h3 style='text-align:justify'> VIII. Parrafo Aclaratorio </h3>";
			str=str+"<div>"+getRegistro().getInfAclaracion();
			str=str + "</div>";
			
			str=str+"<div style= 'page-break-after:always'></div>";
			
			HtmlConverter.convertToPdf(str, os);
			
			InputStream is = new ByteArrayInputStream(os.toByteArray());
			return new DefaultStreamedContent(is, "application/pdf", "informe.pdf");
			
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage("Advertencia", "Hubo un error al generar el documento de la narrativa"));
		}
		return informe;
	}

	public void setInforme(StreamedContent informe) {
		this.informe = informe;
	}
	
	public void prepararCorreo() {
		textoCorreo="<p><strong>AUDIGOES LE INFORMA:</strong></p>" + 
				"<p>Se ha enviado para su revisi&oacute;n el informe "
				+ "correspondiente a la auditor&iacute;a <strong>"
				+getRegistro().getAuditoria().getTipoAuditoria().getTpaAcronimo()+"-"+getRegistro().getAuditoria().getAudAnio()+
				"-"+getRegistro().getAuditoria().getAudCorrelativo()
				+"</strong> por lo que se le pide ingresar al sistema para revisarlo.</p>\r\n"
				+"<p>Atte.-</p>";
	}

	public void onEnviarRevision() {
		correoRevision(textoCorreo);
	}

	public void correoRevision(String texto) {
		String from;
		String cc;
		String to;
		String subject;
		String text;
		String attach;
		String logo;
		String body;
		Address[] toList;
		Address[] toCc;
		
		try {
			from = "audigoes.ues@gmail.com";
			cc = "bren9414@gmail.com";
			to = "bren9414@gmail.com";
			subject = "Correo de Prueba";
			
			body = texto;
			logo = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/images/logo-azul.png");
			
			SendMailAttach mail = new SendMailAttach(from, cc, to, subject, body, null, logo);
			mail.send();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void handleFileUpload(FileUploadEvent event) {
		try {
			System.out.println(" archivo "+event.getFile().getFileName());
			if (getStatus().equals("NEW")) {
				onSave();
				onEdit();
			} 
			this.arcMB.onNew();
			this.arcMB.getRegistro().setInforme(getRegistro());
			this.arcMB.getRegistro().setArcArchivo(event.getFile().getContent());
			this.arcMB.getRegistro().setArcNombre(event.getFile().getFileName());
			this.arcMB.getRegistro().setArcExt(event.getFile().getContentType());
			this.arcMB.onSave();
			//this.arcMB.afterSaveNew();
			this.arcMB.fillByInforme(getRegistro());
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

	public String getTextoCorreo() {
		return textoCorreo;
	}

	public void setTextoCorreo(String textoCorreo) {
		this.textoCorreo = textoCorreo;
	}


}