package audigoes.ues.edu.sv.mbeans.informe;

import java.io.ByteArrayInputStream;
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
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpSession;

import org.primefaces.component.tabview.TabView;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.source.ByteArrayOutputStream;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.administracion.BitacoraActividades;
import audigoes.ues.edu.sv.entities.administracion.Usuario;
import audigoes.ues.edu.sv.entities.ejecucion.ComentarioHallazgo;
import audigoes.ues.edu.sv.entities.ejecucion.ProcedimientoEjecucion;
import audigoes.ues.edu.sv.entities.informe.ActaLectura;
import audigoes.ues.edu.sv.entities.informe.CedulaNota;
import audigoes.ues.edu.sv.entities.informe.Convocatoria;
import audigoes.ues.edu.sv.entities.informe.Informe;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;
import audigoes.ues.edu.sv.entities.planeacion.AuditoriaResponsable;
import audigoes.ues.edu.sv.entities.planificacion.ProcedimientoPlanificacion;
import audigoes.ues.edu.sv.entities.seguimiento.Recomendacion;
import audigoes.ues.edu.sv.mbean.planificacion.BitacoraActividadMB;
import audigoes.ues.edu.sv.mbeans.administracion.ArchivoMB;
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
	private String textoCorreo = "";
	private String textoCorreoObs = "";
	private String textoCorreoFin = "";

	@ManagedProperty(value = "#{actaLecturaMB}")
	private ActaLecturaMB actLecMB = new ActaLecturaMB();

	@ManagedProperty(value = "#{cartaGerenciaMB}")
	private CartaGerenciaMB cartaGMB = new CartaGerenciaMB();

	@ManagedProperty(value = "#{convocatoriaMB}")
	private ConvocatoriaMB convMB = new ConvocatoriaMB();

	@ManagedProperty(value = "#{arcMB}")
	private ArchivoMB arcMB = new ArchivoMB();

	@ManagedProperty(value = "#{bitaMB}")
	private BitacoraActividadMB bitaMB = new BitacoraActividadMB();

	private Auditoria auditoria;
	private int versionActual;

	public InformeMB() {
		super(new Informe());
	}

	@PostConstruct
	public void init() {
		try {
//			Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
//			setAuditoria((Auditoria) sessionMap.get("auditoria"));
//			setInforme();
			// System.out.println(getAuditoria().getAudId());
			super.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onSave() {
		if (getRegistro().getInfId() > 0) {
			setStatus("EDIT");
		} else {
			setStatus("NEW");
		}
		super.onSave();
	}

	public void setInforme() {
		try {

			setRegistro((Informe) audigoesLocal
					.findByNamedQuery(Informe.class, "informe.by.auditoria", new Object[] { getAuditoria().getAudId() })
					.get(0));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void buscaInforme(Auditoria auditoria) {
		try {
			setListado((List<Informe>) audigoesLocal.findByNamedQuery(Informe.class, "informe.by.auditoria",
					new Object[] { auditoria.getAudId() }));
			if (getListado() != null && !getListado().isEmpty()) {
				setRegistro(getListado().get(0));
			} else {
				onNew();
				getRegistro().setInfTitulo("INFORME DE AUDITORIA " + auditoria.getAudNombre().toUpperCase());
				getRegistro().setAuditoria(auditoria);
				getRegistro().setFecCrea(getToday());
				getRegistro().setUsuCrea(getObjAppsSession().getUsuario().getUsuUsuario());
				getRegistro().setRegActivo(1);
				getRegistro().setInfEstado(1);
				getRegistro().setInfVersion(1);
				audigoesLocal.insert(getRegistro());
				inicializarValores();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onInforme() {
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		setAuditoria(((Auditoria) sessionMap.get("auditoria")));

		buscaInforme(getAuditoria());
		onEdit();
		this.arcMB.fillByInforme(getRegistro());

		BitacoraActividades a = bitaMB.buscarActividad(15, getAuditoria());
		if (a == null) {
			bitaMB.iniciarActividad(15, "Elaboración Borrador de Informe", getAuditoria(),
					getObjAppsSession().getUsuario());
		}

		if (getRegistro().getInfId() > 0) {
			setVersionActual(getRegistro().getInfVersion());
		} else {
			setVersionActual(1);
			getRegistro().setInfVersion(getVersionActual());
		}
		setStatus("INFORME");

		revisarPermisos();
	}

	@Override
	public boolean beforeSaveNew() {
//		getRegistro().setAuditoria(getAuditoria());
//		getRegistro().setRegActivo(1);
//		getRegistro().setUsuCrea(getObjAppsSession().getUsuario().getUsuUsuario());
//		getRegistro().setFecCrea(getToday());
		return super.beforeSaveNew();
	}

	@SuppressWarnings("unchecked")
	public void fillListado() {
		try {
			setListado((List<Informe>) audigoesLocal.findByNamedQuery(Informe.class, "informe.all", new Object[] {}));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void fillInforme() {
		try {
			setListado((List<Informe>) audigoesLocal.findByNamedQuery(Informe.class, "informe.by.auditoria",
					new Object[] { getAuditoria().getAudId() }));
			if (!getListado().isEmpty()) {
				setRegistro(getListado().get(0));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void fillActaLectura() {
		try {
			setListado((List<ActaLectura>) audigoesLocal.findByNamedQuery(ActaLectura.class, "actaLectura.by.informe",
					new Object[] { getRegistro().getInfId() }));
			if (!getListado().isEmpty()) {
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
					"convocatoria.by.informe", new Object[] { getRegistro().getInfId() }));
			if (!getListado().isEmpty()) {
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
		return informe.getInfTitulo().toLowerCase().contains(filterText) || informe.getInfVersion() == filterInt
				|| informe.getInfId() == filterInt;
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
					.findComponent("frmInforme:tvInforme");
			tv.setActiveIndex(0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		super.afterCancel();
	}

	@Override
	public void afterSave() {
		super.afterSave();
		setStatus("INFORME");
	}

	public void showInforme() {
		fillInforme();
		if (getRegistro().getInfId() > 0) {
			setVersionActual(getRegistro().getInfVersion());
		} else {
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
		// fillActaLectura();
		// fillConvocatoria();
		setStatus("ACTA_LECTURA");
	}
	
	public void onConvocatoria() {
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		setAuditoria(((Auditoria) sessionMap.get("auditoria")));
		onInforme();
		convMB.buscaConvocatoria(getRegistro());
		
		//this.arcMB.fillByInforme(getRegistro());

		BitacoraActividades a = bitaMB.buscarActividad(15, getAuditoria());
		if (a == null) {
			bitaMB.iniciarActividad(15, "Elaboración Borrador de Informe", getAuditoria(),
					getObjAppsSession().getUsuario());
		}

		setStatus("ACTA_LECTURA");

		revisarPermisos();
	}

	public void showCarta() {
		// fillCartaGerencia();
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

	public void inicializarValores() {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

			String portada = "";

			portada = portada + "<div>&nbsp;\r\n" + "<h1 style=\"text-align:center\"><strong>"
					+ getRegistro().getAuditoria().getPlanAnual().getInstitucion().getInsNombre().toUpperCase()
					+ "</strong></h1>\r\n" + "\r\n"
					+ "<h1 style=\"text-align:center\"><strong>UNIDAD DE AUDITORIA INTERNA</strong></h1>\r\n"
					+ "</div>\r\n" + "\r\n" + "<p>&nbsp;</p>\r\n" + "\r\n" + "<p>&nbsp;</p>\r\n" + "\r\n"
					+ "<p>&nbsp;</p>\r\n" + "\r\n" + "<p>&nbsp;</p>\r\n" + "\r\n" + "<p>&nbsp;</p>\r\n" + "\r\n"
					+ "<p>&nbsp;</p>\r\n" + "\r\n" + "<p>&nbsp;</p>\r\n" + "\r\n" + "<p>&nbsp;</p>\r\n" + "\r\n"
					+ "<h1 style=\"text-align:center\"><strong>Informe de "
					+ getRegistro().getAuditoria().getAudNombre()
					+ "</strong><h3 style=\"text-align:center\"><strong> PERIODO DEL "
					+ formatter.format(getRegistro().getAuditoria().getAudFechaInicioProgramado()) + "   AL "
					+ formatter.format(getRegistro().getAuditoria().getAudFechaFinProgramado()) + "</strong></h3>\r\n"
					+ "\r\n" + "<p>&nbsp;</p>\r\n" + "\r\n" + "<p>&nbsp;</p>\r\n" + "\r\n" + "<p>&nbsp;</p>\r\n"
					+ "\r\n" + "<p>&nbsp;</p>\r\n" + "\r\n" + "<p>&nbsp;</p>\r\n" + "\r\n" + "<p>&nbsp;</p>\r\n"
					+ "\r\n" + "<p>&nbsp;</p>\r\n" + "\r\n" + "<h3 style=\"text-align:right\"><br />\r\n" + "<br />\r\n"
					+ "<strong>SAN SALVADOR, ENERO 2020</strong></h3>\r\n";

			String indice = "";

			indice = indice + "<div>\r\n"
					+ "<h2 style=\"text-align:center\"><strong>INDICE</strong></h2><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p>\r\n"
					+ "\r\n" + "<h2 style=\"text-align:justify\"><strong>I. Introducción</strong></h2>\r\n" + "\r\n"
					+ "<h2 style=\"text-align:justify\"><strong>II. Objetivos de la Auditoría</strong></h2>\r\n"
					+ "\r\n" + "<h2 style=\"text-align:justify\"><strong>III. Alcance de la Auditoria</strong></h2>\r\n"
					+ "\r\n"
					+ "<h2 style=\"text-align:justify\"><strong>IV. Procedimientos de Auditoria Aplicados</strong></h2>\r\n"
					+ "\r\n"
					+ "<h2 style=\"text-align:justify\"><strong>V. Resultados de la Auditoria</strong></h2>\r\n"
					+ "\r\n"
					+ "<h2 style=\"text-align:justify\"><strong>VI. Seguimiento a las recomendaciones de auditorias anteriores</strong></h2>\r\n"
					+ "\r\n"
					+ "<h2 style=\"text-align:justify\"><strong>VII. Recomendacioes de la Auditoria</strong></h2>\r\n"
					+ "\r\n" + "<h2 style=\"text-align:justify\"><strong>VIII. Conclusion</strong></h2>\r\n" + "\r\n"
					+ "<h2 style=\"text-align:justify\"><strong>IX. Parrafo Aclaratorio</strong></h2>\r\n"
					+ "</div>\r\n";

			String intro = "";

			intro = intro + "<p><strong>Se&ntilde;or (es)<br />\r\n"
					+ "Ministro/Consejo Directivo/Junta Directiva/Consejo Municipal<br />\r\n"
					+ "Presente</strong></p>\r\n" + "\r\n" + "<p>&nbsp;</p>\r\n" + "\r\n"
					+ "<p>El presente informe contiene los resultados de la auditor&iacute;a "
					+ getRegistro().getAuditoria().getAudNombre() + ", por el per&iacute;odo del "
					+ formatter.format(getRegistro().getAuditoria().getAudFechaInicioProgramado()) + "   al "
					+ formatter.format(getRegistro().getAuditoria().getAudFechaFinProgramado())
					+ ". La auditor&iacute;a fue realizada en cumplimiento a los Arts. 30 y 31 de la Ley de la Corte de Cuentas de la Rep&uacute;blica y a las Normas de Auditor&iacute;a Interna del Sector Gubernamental, emitidas por la Corte de Cuentas de la Rep&uacute;blica.</p>\r\n";

			String obj = "";

			obj = obj + "<h3><strong>II. Objetivos de la auditor&iacute;a</strong></h3>\r\n" + "\r\n"
					+ "<h4><strong>Objetivos Generales y Especificos</strong></h4>\r\n" + "\r\n"
					+ "<p style=\"text-align:justify\">" + getRegistro().getAuditoria().getAudObjetivos() + "</p>";

			String alcance = "";

			alcance = alcance
					+ "<h3><strong>III. Alcance de la auditor&iacute;a</strong></h3><p style=\"text-align:justify\">"
					+ getRegistro().getAuditoria().getAudAlcances() + "</p>";

			String procs = "";

			procs = procs
					+ "<h3><strong>IV. Procedimientos de auditor&iacute;a aplicados</strong></h3><p style=\"text-align:justify\">"
					+ "<h4><strong>Procedimientos de Planificaci&oacute;n</strong></h4><ol>";

			// procedimientos.planificacion.by.auditoria

			@SuppressWarnings("unchecked")
			List<ProcedimientoPlanificacion> plani = (List<ProcedimientoPlanificacion>) audigoesLocal.findByNamedQuery(
					ProcedimientoPlanificacion.class, "procedimientos.planificacion.by.auditoria",
					new Object[] { getRegistro().getAuditoria().getAudId() });
			System.out.println(" " + plani.size() + " id " + getRegistro().getAuditoria().getAudId());
			if (plani != null && !plani.isEmpty()) {
				for (ProcedimientoPlanificacion p : plani) {
					System.out.println(" - " + p.getProNombre());
					procs = procs + "<li>" + p.getProNombre() + "</li>";
				}
			}

			procs = procs + "</ol></p>";

			procs = procs + "<p style=\"text-align:justify\">"
					+ "<h4><strong>Procedimientos de Ejecuci&oacute;n</strong></h4><ol>";

			// procedimientos.planificacion.by.auditoria

			@SuppressWarnings("unchecked")
			List<ProcedimientoEjecucion> ejec = (List<ProcedimientoEjecucion>) audigoesLocal.findByNamedQuery(
					ProcedimientoEjecucion.class, "procedimientos.ejecucion.by.auditoria",
					new Object[] { getRegistro().getAuditoria().getAudId() });
			System.out.println(" " + ejec.size() + " id " + getRegistro().getAuditoria().getAudId());
			if (ejec != null && !ejec.isEmpty()) {
				for (ProcedimientoEjecucion p : ejec) {
					System.out.println(" - " + p.getPejNombre());
					procs = procs + "<li>" + p.getPejNombre() + "</li>";
				}
			}

			procs = procs + "</ol></p>";

			String resultados = "";

			resultados = resultados
					+ "<h3><strong>V. Resultados de la auditor&iacute;a </strong></h3><p style=\"text-align:justify\">"
					+ "<ol>";

			@SuppressWarnings("unchecked")
			List<CedulaNota> hallazgos = (List<CedulaNota>) audigoesLocal.findByNamedQuery(CedulaNota.class,
					"hallazgos.all.by.auditoria", new Object[] { getRegistro().getAuditoria().getAudId() });
			System.out
					.println(" hallazgos size " + hallazgos.size() + " id " + getRegistro().getAuditoria().getAudId());
			if (hallazgos != null && !hallazgos.isEmpty()) {
				for (CedulaNota c : hallazgos) {
					System.out.println(" - " + c.getCedTitulo());
					resultados = resultados + "<li><strong>" + c.getCedTitulo().toUpperCase() + "</strong><br/><br/>"
							+ "<strong> Condición: </strong>" + c.getCedCondicion() + "<br/><br/>"
							+ "<strong> Criterio: </strong>" + c.getCedCriterio() + "<br/><br/>"
							+ "<strong> Causa: </strong>" + c.getCedCausa() + "<br/><br/>"
							+ "<strong> Efecto: </strong>" + c.getCedEfecto() + "<br/><br/>"
							+ "<strong> Recomendaciones: </strong><br/><br/>";
					@SuppressWarnings("unchecked")
					List<Recomendacion> recomendaciones = (List<Recomendacion>) audigoesLocal.findByNamedQuery(
							Recomendacion.class, "recomendaciones.all.by.cedula", new Object[] { c.getCedId() });
					if (!recomendaciones.isEmpty()) {
						resultados = resultados + "<ul>";
						for (Recomendacion r : recomendaciones) {
							resultados = resultados + "<li>" + r.getRecRecomendacion() + "</li><br/>";
						}
						resultados = resultados + "</ul>";
					} else {
						resultados = resultados + "Sin Recomendaciones<br/><br/>";
					}

					resultados = resultados + "<strong> Comentarios de la Administración: </strong> <br/><br/>";

					if (c.getComentarioHallazgos() != null && !c.getComentarioHallazgos().isEmpty()) {
						resultados = resultados + "<ul>";
						for (ComentarioHallazgo ch : c.getComentarioHallazgos()) {
							resultados = resultados + "<li><strong>" + ch.getUsuario().getUsuNombre() + ": </strong>"
									+ ch.getComeComentario() + "</li><br/>";
						}
						resultados = resultados + "</ul>";
					} else {
						resultados = resultados + "Sin Comentarios<br/><br/>";
					}

					resultados = resultados + "<strong> Comentarios de Auditoría: </strong>"
							+ c.getCedComentarioAuditor() + "<br/><br/>";

					String estado = "";

					switch (c.getCedValorizacion()) {
					case 1:
						estado = "En Proceso";
						break;
					case 2:
						estado = "Implementada";
						break;
					case 3:
						estado = "Asuntos Menores";
						break;
					case 4:
						estado = "No Implementada";
						break;

					default:
						estado = "En Proceso";
						break;
					}
					resultados = resultados + "<strong> Valorización: </strong>" + estado + "<br/>";

					resultados = resultados + "</li><br/>";
				}
			}

			resultados = resultados + "</ol></p>";

			String seguimientos = "";

			seguimientos = seguimientos
					+ "<h3><strong>VI. Seguimiento a las recomendaciones de auditorias anteriores </strong></h3><p style=\"text-align:justify\"></p>";

			String recomendaciones = "";

			recomendaciones = recomendaciones
					+ "<h3><strong>VII. Recomendaciones de auditoría </strong></h3><p style=\"text-align:justify\"></p>";

			String conclusiones = "";

			conclusiones = conclusiones
					+ "<h3><strong>VIII. Conclusiones </strong></h3><p style=\"text-align:justify\"></p>";

			String parrafo = "";

			parrafo = parrafo
					+ "<h3><strong>IX. Parrafo Aclaratorio </strong></h3><p style=\"text-align:justify\"><br/><br/><br/><br/><strong>San Salvador, Enero 2020<br/>DIOS UNION LIBERTAD</strong></p>";

			getRegistro().setInfPortada(portada);
			getRegistro().setInfIndice(indice);
			getRegistro().setInfIntroduccion(intro);
			getRegistro().setInfObjetivos(obj);
			getRegistro().setInfAlcance(alcance);
			getRegistro().setInfProcedimientos(procs);
			getRegistro().setInfResultados(resultados);
			getRegistro().setInfSeguimiento(seguimientos);
			getRegistro().setInfRecomendaciones(recomendaciones);
			getRegistro().setInfConclusion(conclusiones);
			getRegistro().setInfAclaracion(parrafo);

		} catch (Exception e) {
			e.printStackTrace();
		}
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

			// Defino el texto del encabezado de pagina con el id header que es que se pone
			// arriba running(header)
			str = str + getRegistro().getInfPortada();
			str = str + "<div style= 'page-break-after:always'></div>";

			str = str + getRegistro().getInfIndice();
			str = str + "<div style= 'page-break-after:always'></div>";

			str = str + getRegistro().getInfIntroduccion();
			str = str + getRegistro().getInfObjetivos();
			str = str + getRegistro().getInfAlcance();
			str = str + getRegistro().getInfProcedimientos();
			str = str + getRegistro().getInfResultados();
			str = str + getRegistro().getInfSeguimiento();
			str = str + getRegistro().getInfRecomendaciones();
			str = str + getRegistro().getInfConclusion();
			str = str + getRegistro().getInfAclaracion();
			str = str + "<div style= 'page-break-after:always'></div>";

			HtmlConverter.convertToPdf(str, os);

			InputStream is = new ByteArrayInputStream(os.toByteArray());
			return new DefaultStreamedContent(is, "application/pdf", "informe.pdf");

		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage("Advertencia", "Hubo un error al generar el documento de la narrativa"));
		}
		return informe;
	}

	@SuppressWarnings("deprecation")
	public byte[] getInformePDF() {

		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

			ByteArrayOutputStream os = new ByteArrayOutputStream();

			// Para definir los encabezados de pagina y los pie de pagina
			String str = "<html><head>"
					+ "<style>#header{position: running(header);} @page {margin: 70px 70px 100px;@bottom-right @top-center {content: element(header);} }</style>"
					+ "</head><body style='width:500px; font-size:smaller;'>";

			// Defino el texto del encabezado de pagina con el id header que es que se pone
			// arriba running(header)
			str = str + getRegistro().getInfPortada();
			str = str + "<div style= 'page-break-after:always'></div>";

			str = str + getRegistro().getInfIndice();
			str = str + "<div style= 'page-break-after:always'></div>";

			str = str + getRegistro().getInfIntroduccion();
			str = str + getRegistro().getInfObjetivos();
			str = str + getRegistro().getInfAlcance();
			str = str + getRegistro().getInfProcedimientos();
			str = str + getRegistro().getInfResultados();
			str = str + getRegistro().getInfSeguimiento();
			str = str + getRegistro().getInfRecomendaciones();
			str = str + getRegistro().getInfConclusion();
			str = str + getRegistro().getInfAclaracion();
			str = str + "<div style= 'page-break-after:always'></div>";

			HtmlConverter.convertToPdf(str, os);

			return os.toByteArray();

		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage("Advertencia", "Hubo un error al generar el documento de la narrativa"));
		}
		return null;
	}

	public void onGenerarInforme() {
		onpdf(getInformePDF());
	}

	public void onpdf(byte[] pdfBytesArray) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

		session.setAttribute("pdfBytesArray", pdfBytesArray);
	}

	public void setInforme(StreamedContent informe) {
		this.informe = informe;
	}

	@SuppressWarnings("unchecked")
	public Usuario buscarCoordinador(Auditoria auditoria) {
		Usuario usuario = null;
		try {
			List<AuditoriaResponsable> responsables = (List<AuditoriaResponsable>) audigoesLocal.findByNamedQuery(
					AuditoriaResponsable.class, "find.coordinador.by.auditoria", new Object[] { auditoria.getAudId() });
			if (!responsables.isEmpty()) {
				usuario = responsables.get(0).getUsuario();
				return usuario;
			} else {
				addWarn(new FacesMessage("Error, la auditoria no cuenta con un coordinador asignado"));
				return usuario;
			}
		} catch (Exception e) {
			addWarn(new FacesMessage("Error al identificar  el coordinador de la auditoria"));
			return usuario;
		}
	}

	public void prepararCorreo() {
		textoCorreo = "<p><strong>AUDIGOES LE INFORMA:</strong></p>"
				+ "<p>Se ha enviado para su revisi&oacute;n el borrador del informe "
				+ "correspondiente a la auditor&iacute;a <strong>"
				+ getRegistro().getAuditoria().getTipoAuditoria().getTpaAcronimo() + "-"
				+ getRegistro().getAuditoria().getAudAnio() + "-" + getRegistro().getAuditoria().getAudCorrelativo()
				+ "</strong> por lo que se le pide ingresar al sistema para revisarlo.</p>\r\n" + "<p>Atte.-</p>";
	}
	
	public void prepararCorreoObs() {
		textoCorreoObs = "<p><strong>AUDIGOES LE INFORMA:</strong></p>"
				+ "<p>Se han enviado las observaciones del memorando de planificaci&oacute;n "
				+ "correspondiente a la auditor&iacute;a <strong>"
				+ getRegistro().getAuditoria().getTipoAuditoria().getTpaAcronimo() + "-"
				+ getRegistro().getAuditoria().getAudAnio() + "-" + getRegistro().getAuditoria().getAudCorrelativo()
				+ "</strong> por lo que se le pide ingresar al sistema para solventarlas.</p>\r\n" + "<p>Atte.-</p>";
	}

	public void prepararCorreoFin() {
		textoCorreoFin = "<p><strong>AUDIGOES LE INFORMA:</strong></p>"
				+ "<p>Se ha finalizado la revisión del memorando de planificaci&oacute;n "
				+ "correspondiente a la auditor&iacute;a <strong>"
				+ getRegistro().getAuditoria().getTipoAuditoria().getTpaAcronimo() + "-"
				+ getRegistro().getAuditoria().getAudAnio() + "-" + getRegistro().getAuditoria().getAudCorrelativo()
				+ "</strong> por lo que puede proceder con el Memorando de Planificación</p>\r\n" + "<p>Atte.-</p>";
	}

	public void onEnviarRevision() {
		Usuario usr = buscarCoordinador(getRegistro().getAuditoria());
		if (usr != null) {
			try {
				getRegistro().setInfEstado(2);
				getRegistro().setInfFecElaboro(getToday());
				getRegistro().setUsuarioElaboro(getObjAppsSession().getUsuario());
				System.out.println("status");
				onSave();
				if (correoRevision(textoCorreo, usr)) {

					BitacoraActividades a = bitaMB.buscarActividad(15, getRegistro().getAuditoria());
					if (a != null) {
						bitaMB.finalizarActividad(15, getRegistro().getAuditoria(), getObjAppsSession().getUsuario());
					}

					bitaMB.iniciarActividad(16, "Revisión del Borrador de Informe", getRegistro().getAuditoria(),
							getObjAppsSession().getUsuario());
				}
				revisarPermisos();
			} catch (Exception e) {
				e.printStackTrace();
				addWarn(new FacesMessage("Error al enviar a revisión"));
			}
		}
	}
	
	public void onEnviarObservacion() {
		Usuario usr = buscarCoordinador(getRegistro().getAuditoria());
		if (usr != null) {
			try {
				getRegistro().setInfEstado(1);
				getRegistro().setUsuarioReviso(getObjAppsSession().getUsuario());
				getRegistro().setInfFecReviso(getToday());
				System.out.println("status");
				onSave();
				if (correoObservacion(textoCorreoObs, getObjAppsSession().getUsuario())) {
					BitacoraActividades a = bitaMB.buscarActividad(16, getRegistro().getAuditoria());
					if (a != null) {
						bitaMB.finalizarActividad(16, getRegistro().getAuditoria(), getObjAppsSession().getUsuario());
					}

					bitaMB.iniciarActividad(15, "Elaboración del Borrador de Informe", getRegistro().getAuditoria(),
							getObjAppsSession().getUsuario());
				}
				revisarPermisos();
			} catch (Exception e) {
				e.printStackTrace();
				addWarn(new FacesMessage("Error al enviar las observaciones"));
			}
		}
	}

	public void onFinalizar() {
		Usuario usr = buscarCoordinador(getRegistro().getAuditoria());
		if (usr != null) {
			try {
				getRegistro().setInfEstado(3);
				getRegistro().setUsuarioReviso(getObjAppsSession().getUsuario());
				getRegistro().setInfFecReviso(getToday());
				System.out.println("status");
				onSave();
				if (correoFin(textoCorreoFin, getObjAppsSession().getUsuario())) {

					BitacoraActividades a = bitaMB.buscarActividad(16, getRegistro().getAuditoria());
					if (a != null) {
						bitaMB.finalizarActividad(16, getRegistro().getAuditoria(), getObjAppsSession().getUsuario());
					}

				}
				revisarPermisos();
			} catch (Exception e) {
				e.printStackTrace();
				addWarn(new FacesMessage("Error al finalizar las observaciones"));
			}
		}
	}

	public boolean correoRevision(String texto, Usuario coordinador) {
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
			to = coordinador.getUsuCorreo();
			subject = "Solicitud de revisión borrador de informe";
			String toccs = "";

			for (AuditoriaResponsable r : auditoria.getAuditoriaResponsable()) {
				toccs = toccs + r.getUsuario().getUsuCorreo() + ",";
			}
			toCc = InternetAddress.parse(toccs);

			body = texto;
			logo = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/images/logo-azul.png");

			SendMailAttach mail = new SendMailAttach(from, to, toCc, subject, body, null, logo);
			mail.sendManyCc();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean correoObservacion(String texto, Usuario coordinador) {
		String from;
		String cc;
		String to;
		String subject;
		String attach;
		String logo;
		String body;
		Address[] toList;
		Address[] toCc;

		try {
			from = "audigoes.ues@gmail.com";
			cc = coordinador.getUsuCorreo();
			subject = "Observaciones al borrador de informe";
			String tolists = "";

			for (AuditoriaResponsable r : auditoria.getAuditoriaResponsable()) {
				tolists = tolists + r.getUsuario().getUsuCorreo() + ",";
			}
			toList = InternetAddress.parse(tolists);

			body = texto;
			logo = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/images/logo-azul.png");

			SendMailAttach mail = new SendMailAttach(from, toList, cc, subject, body, null, logo);
			mail.sendManyTo();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean correoFin(String texto, Usuario coordinador) {
		String from;
		String cc;
		String to;
		String subject;
		String attach;
		String logo;
		String body;
		Address[] toList;
		Address[] toCc;

		try {
			from = "audigoes.ues@gmail.com";
			cc = coordinador.getUsuCorreo();
			subject = "Finalización de revisión al borrador de informe";
			String tolists = "";

			for (AuditoriaResponsable r : auditoria.getAuditoriaResponsable()) {
				tolists = tolists + r.getUsuario().getUsuCorreo() + ",";
			}
			toList = InternetAddress.parse(tolists);
			body = texto;
			logo = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/images/logo-azul.png");

			SendMailAttach mail = new SendMailAttach(from, toList, cc, subject, body, null, logo);
			mail.sendManyTo();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void handleFileUpload(FileUploadEvent event) {
		try {
			System.out.println(" archivo " + event.getFile().getFileName());
			if (getStatus().equals("NEW")) {
				onSave();
				onEdit();
			}
			this.arcMB.onNew();
			this.arcMB.getRegistro().setInforme(getRegistro());
			this.arcMB.getRegistro().setArcArchivo(event.getFile().getContent());
			this.arcMB.getRegistro().setArcNombre(event.getFile().getFileName());
			this.arcMB.getRegistro().setArcExt(event.getFile().getContentType());
			// this.arcMB.onSave();
			audigoesLocal.insert(this.arcMB.getRegistro());
			// this.arcMB.afterSaveNew();
			this.arcMB.fillByInforme(getRegistro());
			addWarn(new FacesMessage(SYSTEM_NAME, "Archivo Guardado con Éxito"));

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

	public BitacoraActividadMB getBitaMB() {
		return bitaMB;
	}

	public void setBitaMB(BitacoraActividadMB bitaMB) {
		this.bitaMB = bitaMB;
	}

	public String getTextoCorreoObs() {
		return textoCorreoObs;
	}

	public void setTextoCorreoObs(String textoCorreoObs) {
		this.textoCorreoObs = textoCorreoObs;
	}

	public String getTextoCorreoFin() {
		return textoCorreoFin;
	}

	public void setTextoCorreoFin(String textoCorreoFin) {
		this.textoCorreoFin = textoCorreoFin;
	}

	public void revisarPermisos() {
		super.configBean();
		if (getAuditoria() != null) {
			setPerEdit(false);

			setRolCoordinadorAuditoria(getObjAppsSession().isCoordinador(getObjAppsSession().getUsuario().getUsuId(),
					getAuditoria().getAuditoriaResponsable()));
			switch (getRegistro().getInfEstado()) {
			case 1:
				if (isRolAuditor()) {
					setPerEdit(true);
				} else {
					setPerEnviar(false);
				}
				setPerAutorizar(false);
				break;
			case 2:
				if (isRolCoordinadorAuditoria()) {
					//setPerEdit(true);
					if (isPerAutorizar()) {
						setPerAutorizar(true);
					}
				} else {
					setPerAutorizar(false);
				}
				setPerEnviar(false);
				break;
			case 3:
				break;
			case 4:
				break;
			case 5:
				break;
			default:
				break;
			}
		}
	}

	@Override
	protected void configBean() {

		super.configBean();

	}

}