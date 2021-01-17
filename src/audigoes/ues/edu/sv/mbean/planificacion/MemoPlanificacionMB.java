package audigoes.ues.edu.sv.mbean.planificacion;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.source.ByteArrayOutputStream;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.administracion.BitacoraActividades;
import audigoes.ues.edu.sv.entities.administracion.Usuario;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;
import audigoes.ues.edu.sv.entities.planeacion.AuditoriaResponsable;
import audigoes.ues.edu.sv.entities.planificacion.Actividad;
import audigoes.ues.edu.sv.entities.planificacion.Memorando;
import audigoes.ues.edu.sv.util.SendMailAttach;

@ManagedBean(name = "memoMB")
@ViewScoped
public class MemoPlanificacionMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Memorando> filteredMemo;
	private Auditoria auditoria;
	private String textoCorreo = "";
	private String textoCorreoObs = "";
	private String textoCorreoFin = "";
	private StreamedContent memorando;

	@ManagedProperty(value = "#{bitaMB}")
	private BitacoraActividadMB bitaMB = new BitacoraActividadMB();

	public MemoPlanificacionMB() {
		super(new Memorando());
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
	public void fillMemo() {
		try {
//			setListado((List<ProgramaPlanificacion>) audigoesLocal.findByNamedQuery(ProgramaPlanificacion.class,
//					"programa.by.auditoria",
//					new Object[] { auditoria.getAudId() }));
			Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			setAuditoria(((Auditoria) sessionMap.get("auditoria")));
			System.out.println("auditoria " + auditoria.getAudId());
			setListado((List<Memorando>) audigoesLocal.findByNamedQuery(Memorando.class, "memo.by.auditoria",
					new Object[] { auditoria.getAudId() }));
			if (!getListado().isEmpty()) {
				setRegistro(getListado().get(0));

			} else {
				valoresIniciales();
				getRegistro().setAuditoria(auditoria);
				getRegistro().setFecCrea(getToday());
				getRegistro().setUsuCrea(getObjAppsSession().getUsuario().getUsuUsuario());
				getRegistro().setRegActivo(1);
				getRegistro().setUsuario1(getObjAppsSession().getUsuario());
				getRegistro().setMemFechaElaboro(getToday());
				getRegistro().setMemEstado(1);
				audigoesLocal.insert(getRegistro());
			}
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage("Advertencia", "Auditoria No cuenta con Programa de Planificación"));
		}
	}

	public void showAuditorias() {
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void prepararCorreo() {
		textoCorreo = "<p><strong>AUDIGOES LE INFORMA:</strong></p>"
				+ "<p>Se ha enviado para su revisi&oacute;n el memorando de planificaci&oacute;n "
				+ "correspondiente a la auditor&iacute;a <strong>"
				+ getRegistro().getAuditoria().getTipoAuditoria().getTpaAcronimo() + "-"
				+ getRegistro().getAuditoria().getAudAnio() + "-" + getRegistro().getAuditoria().getAudCorrelativo()
				+ "</strong> por lo que se le pide ingresar al sistema para realizarlo.</p>\r\n" + "<p>Atte.-</p>";
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
				getRegistro().setMemEstado(2);
				System.out.println("status");
				onSave();
				if (correoRevision(textoCorreo, usr)) {

					BitacoraActividades a = bitaMB.buscarActividad(8, getRegistro().getAuditoria());
					if (a != null) {
						bitaMB.finalizarActividad(8, getRegistro().getAuditoria(), getObjAppsSession().getUsuario());
					}

					bitaMB.iniciarActividad(7, "Revisión del Memorando de Planificación", getRegistro().getAuditoria(),
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
				getRegistro().setMemEstado(1);
				getRegistro().setUsuario2(getObjAppsSession().getUsuario());
				getRegistro().setMemFechaReviso(getToday());
				System.out.println("status");
				onSave();
				if (correoObservacion(textoCorreoObs, getObjAppsSession().getUsuario())) {
					bitaMB.finalizarActividad(7, getRegistro().getAuditoria(), getObjAppsSession().getUsuario());

					bitaMB.iniciarActividad(8, "Corrección a observaciones del Memorando de Planificación",
							getRegistro().getAuditoria(), getObjAppsSession().getUsuario());
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
				getRegistro().setMemEstado(3);
				getRegistro().setUsuario2(getObjAppsSession().getUsuario());
				getRegistro().setMemFechaReviso(getToday());
				System.out.println("status");
				onSave();
				if (correoFin(textoCorreoFin, getObjAppsSession().getUsuario())) {

					bitaMB.finalizarActividad(7, getRegistro().getAuditoria(), getObjAppsSession().getUsuario());
					bitaMB.finalizarActividad(6, getRegistro().getAuditoria(), getObjAppsSession().getUsuario());
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
		String attach;
		String logo;
		String body;
		Address[] toList;
		Address[] toCc;

		try {
			from = "audigoes.ues@gmail.com";
			to = coordinador.getUsuCorreo();
			subject = "Solicitud de revisión memorando de planificación";
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
			subject = "Observaciones al memorando de planificación";
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
			subject = "Finalización de revisión al memorando de planificación";
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

	public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
		if (filterText == null || filterText.equals("")) {
			return true;
		}

		Memorando memo = (Memorando) value;
		return memo.getMemNombre().toLowerCase().contains(filterText);
	}

	@Override
	public void afterCancel() {
		super.afterCancel();
	}

	@Override
	public boolean beforeSaveNew() {
		return super.beforeSaveNew();
	}

	/* GETS y SETS */

	@Override
	public Memorando getRegistro() {
		return (Memorando) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Memorando> getListado() {
		return (List<Memorando>) super.getListado();
	}

	@Override
	public void afterSave() {
		super.afterSave();
		if (getRegistro().getMemEstado() == 1 || getRegistro().getMemEstado() == 2) {
			onEdit();
		}
	}

	@SuppressWarnings("unchecked")
	public void valoresIniciales() {
		//Encabezado
		
		String encabezado = "<p style=\"text-align:center\"><strong>INDICE</strong></p>\r\n"
				+ "\r\n"
				+ "<p style=\"text-align:justify\">&nbsp;</p>\r\n"
				+ "\r\n"
				+ "<p style=\"text-align:justify\"><strong>CONTENIDO</strong></p>\r\n"
				+ "\r\n"
				+ "<ol>\r\n"
				+ "	<li style=\"text-align:justify\"><strong>INFORMACI&Oacute;N B&Aacute;SICA</strong>\r\n"
				+ "\r\n"
				+ "	<ol>\r\n"
				+ "		<li><strong>Antecedentes de la auditoria</strong></li>\r\n"
				+ "		<li><strong>Conocimiento y comprensi&oacute;n del &aacute;rea</strong></li>\r\n"
				+ "		<li><strong>Objetivos de la unidad organizativa a examinar</strong></li>\r\n"
				+ "		<li><strong>Entrevistas con el personal</strong></li>\r\n"
				+ "		<li><strong>Diagnostico del &aacute;rea</strong></li>\r\n"
				+ "		<li><strong>Identificaci&oacute;n de riesgos significativos</strong></li>\r\n"
				+ "	</ol>\r\n"
				+ "	</li>\r\n"
				+ "	<li style=\"text-align:justify\"><strong>ESTRATEGIA&nbsp;DE LA AUDITORIA</strong>\r\n"
				+ "	<ol>\r\n"
				+ "		<li><strong>Objetivos Generales y Especificos</strong></li>\r\n"
				+ "		<li><strong>Alcance</strong></li>\r\n"
				+ "		<li><strong>Determinaci&oacute;n del riesgo de la auditor&iacute;a</strong></li>\r\n"
				+ "		<li><strong>Determinaci&oacute;n de &aacute;reas a examinar</strong></li>\r\n"
				+ "		<li><strong>Enfoque de auditor&iacute;a</strong></li>\r\n"
				+ "	</ol>\r\n"
				+ "	</li>\r\n"
				+ "	<li style=\"text-align:justify\"><strong>ADMINISTRACI&Oacute;N DEL TRABAJO</strong>\r\n"
				+ "	<ol>\r\n"
				+ "		<li style=\"text-align:justify\"><strong>Equipo de Trabajo</strong></li>\r\n"
				+ "		<li style=\"text-align:justify\"><strong>Fechas claves</strong></li>\r\n"
				+ "	</ol>\r\n"
				+ "	</li>\r\n"
				+ "</ol>\r\n"
				+ "\r\n"
				+ "<p style=\"text-align:justify\">&nbsp;</p>\r\n"
				+ "\r\n"
				+ "<p style=\"text-align:justify\">&nbsp;</p>\r\n"
				+ "\r\n"
				+ "<ol>\r\n"
				+ "</ol>\r\n";
		
		// Contenido
		String contenido = 
				"	<li style=\"text-align:justify\"><strong>INFORMACI&Oacute;N B&Aacute;SICA</strong>\r\n"
				+ "\r\n"
				+ "	<ol>\r\n"
				+ "		<li><strong>Antecedentes de la auditoria</strong></li>\r\n"
				+ "		<li><strong>Conocimiento y comprensi&oacute;n del &aacute;rea</strong></li>\r\n"
				+ "		<li><strong>Objetivos de la unidad organizativa a examinar</strong></li>\r\n"
				+ "		<li><strong>Entrevistas con el personal</strong></li>\r\n"
				+ "		<li><strong>Diagnostico del &aacute;rea</strong></li>\r\n"
				+ "		<li><strong>Identificaci&oacute;n de riesgos significativos</strong></li>\r\n"
				+ "	</ol>\r\n"
				+ "	</li>\r\n"
				+ "	<li style=\"text-align:justify\"><strong>ESTRATEGIA&nbsp;DE LA AUDITORIA</strong>\r\n"
				+ "	<ol>\r\n"
				+ "		<li><strong>Objetivos Generales y Especificos</strong>\r\n"
				+"<br/>"+auditoria.getAudObjetivos()+"</li>"
				+ "		<li><strong>Alcance</strong>\r\n"
				+"<br/>"+auditoria.getAudAlcances()+"</li>"
				+ "		<li><strong>Determinaci&oacute;n del riesgo de la auditor&iacute;a</strong></li>\r\n"
				+ "		<li><strong>Determinaci&oacute;n de &aacute;reas a examinar</strong></li>\r\n"
				+ "		<li><strong>Enfoque de auditor&iacute;a</strong></li>\r\n"
				+ "	</ol>\r\n"
				+ "	</li>\r\n"
				+ "	<li style=\"text-align:justify\"><strong>ADMINISTRACI&Oacute;N DEL TRABAJO</strong>\r\n"
				+ "	<ol>\r\n"
				+ "		<li><strong>Integrantes del equipo auditor</strong>\r\n"
				+ "		<table border=\"1\" cellpadding=\"1\" cellspacing=\"1\" style=\"width:250px\">\r\n"
				+ "			<thead>\r\n"
				+ "				<tr>\r\n"
				+ "					<th scope=\"col\">Cargo</th>\r\n"
				+ "					<th scope=\"col\">Nombre</th>\r\n"
				+ "				</tr>\r\n"
				+ "			</thead>\r\n"
				+ "			<tbody>\r\n";
				for(AuditoriaResponsable a: auditoria.getAuditoriaResponsable()) {
					if(a.getAurRol()==0) {
						contenido=contenido+"<tr>\r\n <td> Coordinador </td>";
					} else {
						contenido=contenido+"<tr>\r\n <td> Auditor </td>";
					}
					contenido=contenido+"<td>"+a.getUsuario().getUsuNombre()+"</td></tr>";
				}
				contenido=contenido
				+ "			</tbody>"
				+ "		</table></li>\r\n"
				+ "		<li><strong>Fechas claves</strong>\r\n"
				+ "		<table border=\"1\" cellpadding=\"1\" cellspacing=\"1\" style=\"width:450px\">\r\n"
				+ "			<thead>\r\n"
				+ "				<tr>\r\n"
				+ "					<th scope=\"col\">Actividad</th>\r\n"
				+ "					<th scope=\"col\">Fecha de Inicio</th>\r\n"
				+ "					<th scope=\"col\">Fecha de Fin</th>\r\n"
				+ "				</tr>\r\n"
				+ "			</thead>\r\n"
				+ "			<tbody>\r\n";
				SimpleDateFormat  formatter = new SimpleDateFormat("dd/MM/yyyy");
				
				List<Actividad> acts = null;
				try {
					acts = ((List<Actividad>) audigoesLocal.findByNamedQuery(Actividad.class, "actividades.by.auditoria",
							new Object[] { auditoria.getAudId() }));
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				if(acts!=null && !acts.isEmpty()) {
					contenido=contenido+"<tr>";
					for(Actividad a: acts) {
						contenido=contenido+"<td>"+a.getActNombre()+"</td>";
						contenido=contenido+"<td>"+formatter.format(a.getActFecIni())+"</td>";
						contenido=contenido+"<td>"+formatter.format(a.getActFecIni())+"</td></tr>";
					}
				}
				contenido=contenido
				+ "			</tbody>"
				+ "		</table></li>"
				+ "	</ol>\r\n"
				+ "	</li>\r\n"
				+ "</ol>\r\n"
				+ "\r\n"
				+ "<p style=\"text-align:justify\">&nbsp;</p>\r\n"
				+ "\r\n"
				+ "<p style=\"text-align:justify\">&nbsp;</p>\r\n"
				+ "\r\n"
				+ "<ol>\r\n"
				+ "</ol>\r\n";
		getRegistro().setMemIndice(encabezado);
		getRegistro().setMemContenido(contenido);
	}

	@Override
	public void afterSaveNew() {
		// getListado().add(getRegistro());
		super.afterSaveNew();
	}

	public Auditoria getAuditoria() {
		return auditoria;
	}

	public void setAuditoria(Auditoria auditoria) {
		this.auditoria = auditoria;
	}

	public List<Memorando> getFilteredMemo() {
		return filteredMemo;
	}

	public void setFilteredMemo(List<Memorando> filteredMemo) {
		this.filteredMemo = filteredMemo;
	}

	public String getTextoCorreo() {
		return textoCorreo;
	}

	public void setTextoCorreo(String textoCorreo) {
		this.textoCorreo = textoCorreo;
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

	@SuppressWarnings("deprecation")
	public StreamedContent getMemorando() {

		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

			ByteArrayOutputStream os = new ByteArrayOutputStream();

			// Para definir los encabezados de pagina y los pie de pagina
			String str = "<html><head>"
					+ "<style>#header{position: running(header);} @page {margin: 70px 70px 100px;@bottom-right @top-center {content: element(header);} }</style>"
					+ "</head><body style='width:500px; font-size:smaller;'>";

			// Defino el texto del encabezado de pagina con el id header que es que se pone
			// arriba running(header)
			str = str + "<div style='margin-top:20px;'> " + "<strong><br><strong><h2 style='text-align:center'>"
					+ getRegistro().getAuditoria().getPlanAnual().getInstitucion().getInsNombre();
			str = str + "</strong></div></h2>" + "<br><br><br><br><br><br><br> <br><br><br><br><br><br>";
			// Concateno el texto a agregar
			str = str + "<h1 style='text-align:center'> Memorando de Planificacion ";
			str = str + "<h1 style='text-align:center'> " + getRegistro().getAuditoria().getAudNombre();
			str = str + "</h1><h3 style='text-align:center'>PERIODO DEL "
					+ formatter.format(getRegistro().getAuditoria().getAudFechaInicioProgramado());
			str = str + "   AL " + formatter.format(getRegistro().getAuditoria().getAudFechaFinProgramado());
			str = str + "</h3><br>";

			str = str + "<div style= 'page-break-after:always'></div>";

			str = str + "<div style='margin-top:20px;'> ";
			str = str + "<h3 style='text-align:justify'>" + getRegistro().getMemIndice();
			str = str + "</h3>";

			str = str + "<div style= 'page-break-after:always'></div>";

			str = str + "<div style='text-align:justify'>" + getRegistro().getMemContenido();
			str = str + "</strong></div>" + "<br>";

			str = str + "<div style= 'page-break-after:always'></div>";

			HtmlConverter.convertToPdf(str, os);

			InputStream is = new ByteArrayInputStream(os.toByteArray());
			return new DefaultStreamedContent(is, "application/pdf", "memorando.pdf");

		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage("Advertencia", "Hubo un error al generar el memorando"));
		}
		return memorando;
	}

	public void setMemorando(StreamedContent memorando) {
		this.memorando = memorando;
	}

	public BitacoraActividadMB getBitaMB() {
		return bitaMB;
	}

	public void setBitaMB(BitacoraActividadMB bitaMB) {
		this.bitaMB = bitaMB;
	}

	public void revisarPermisos() {
		if (getAuditoria() != null) {
			setPerEdit(false);

			setRolCoordinadorAuditoria(getObjAppsSession().isCoordinador(getObjAppsSession().getUsuario().getUsuId(),
					getAuditoria().getAuditoriaResponsable()));
			switch (getRegistro().getMemEstado()) {
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
					setPerEdit(true);
					if (isPerAutorizar()) {
						setPerAutorizar(true);
					}
				} else {
					setPerAutorizar(false);
				}
				setPerEnviar(false);
				break;
			case 3:
				setPerEnviar(false);
				setPerEdit(false);
				setPerAutorizar(false);
				break;
			default:
				break;
			}
		}
	}
}
