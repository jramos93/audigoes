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
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.mail.Address;
import javax.mail.internet.InternetAddress;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.source.ByteArrayOutputStream;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.administracion.Archivo;
import audigoes.ues.edu.sv.entities.administracion.BitacoraActividades;
import audigoes.ues.edu.sv.entities.administracion.Usuario;
import audigoes.ues.edu.sv.entities.ejecucion.ProcedimientoEjecucion;
import audigoes.ues.edu.sv.entities.ejecucion.ProgramaEjecucion;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;
import audigoes.ues.edu.sv.entities.planeacion.AuditoriaResponsable;
import audigoes.ues.edu.sv.mbeans.administracion.ArchivoMB;
import audigoes.ues.edu.sv.util.SendMailAttach;

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

	private String textoCorreo = "";
	private String textoCorreoObs = "";
	private String textoCorreoFin = "";

	@ManagedProperty(value = "#{arcMB}")
	private ArchivoMB arcMB = new ArchivoMB();
	
	@ManagedProperty(value = "#{bitaMB}")
	private BitacoraActividadMB bitaMB = new BitacoraActividadMB();

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
		if (getRegistro().getPejEstado() >= 1 ) {
			revisarPermisosDesarrollo();
		}
	}

	public void showAuditorias() {
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean beforeDelete() {
		try {
			arcMB.fillByEjecucion(getRegistro());
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

	@Override
	public void onEditSelected() {
		super.onEditSelected();
	}

	public void handleFileUpload(FileUploadEvent event) {
		try {
			System.out.println(" archivo " + event.getFile().getFileName());
			if (getStatus().equals("NEW")) {
				onSave();
				onEdit();
			}
			arcMB.onNew();
			arcMB.getRegistro().setProcedimientoEjecucion(getRegistro());
			arcMB.getRegistro().setArcArchivo(event.getFile().getContent());
			arcMB.getRegistro().setArcNombre(event.getFile().getFileName());
			arcMB.getRegistro().setArcExt(event.getFile().getContentType());
			arcMB.getRegistro().setFecCrea(getToday());
			arcMB.getRegistro().setUsuCrea(getObjAppsSession().getUsuario().getUsuUsuario());
			arcMB.getRegistro().setRegActivo(1);
			audigoesLocal.insert(arcMB.getRegistro());
			// this.arcMB.afterSaveNew();
			// this.arcMB.getListado().add(this.arcMB.getRegistro());
			this.arcMB.fillByEjecucion(getRegistro());
			if (!getStatus().equals("NEW")) {
				addWarn(new FacesMessage(SYSTEM_NAME, "Archivo Guardado con Éxito"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Problemas al guardar archivo."));
		}

	}

	public void prepararCorreo() {
		textoCorreo = "<p><strong>AUDIGOES LE INFORMA:</strong></p>"
				+ "<p>Se ha enviado para su revisi&oacute;n el procedimiento de ejecución: <strong> "
				+ getRegistro().getPejNombre() + " </strong> correspondiente a la auditor&iacute;a <strong>"
				+ getAuditoria().getTipoAuditoria().getTpaAcronimo() + "-" + getAuditoria().getAudAnio() + "-"
				+ getAuditoria().getAudCorrelativo()
				+ "</strong> por lo que se le pide ingresar al sistema para realizarlo.</p>\r\n" + "<p>Atte.-</p>";
	}

	public void prepararCorreoObs() {
		textoCorreoObs = "<p><strong>AUDIGOES LE INFORMA:</strong></p>"
				+ "<p>Se han enviado las observaciones del procedimiento de ejecución: <strong> "
				+ getRegistro().getPejNombre() + "</strong> correspondiente a la auditor&iacute;a <strong>"
				+ getAuditoria().getTipoAuditoria().getTpaAcronimo() + "-" + getAuditoria().getAudAnio() + "-"
				+ getAuditoria().getAudCorrelativo()
				+ "</strong> por lo que se le pide ingresar al sistema para solventarlas.</p>\r\n" + "<p>Atte.-</p>";
	}

	public void prepararCorreoFin() {
		textoCorreoFin = "<p><strong>AUDIGOES LE INFORMA:</strong></p>"
				+ "<p>Se ha finalizado la revisión del procedimiento de ejecución: <strong> "
				+ getRegistro().getPejNombre() + " </strong> correspondiente a la auditor&iacute;a <strong>"
				+ getAuditoria().getTipoAuditoria().getTpaAcronimo() + "-" + getAuditoria().getAudAnio() + "-"
				+ getAuditoria().getAudCorrelativo()
				+ "</strong> por lo que puede proceder con el Memorando de Planificación</p>\r\n" + "<p>Atte.-</p>";
	}

	public void onEnviarRevision() {
		Usuario usr = buscarCoordinador(getAuditoria());
		System.out.println("enviar");
		if (usr != null) {
			System.out.println("if enviar");
			try {
				getRegistro().setPejEstado(2);
				onSave();
				if (!correoRevision(textoCorreo, usr)) {
					addWarn(new FacesMessage("Error en el envio del correo"));
				}
				revisarPermisosDesarrollo();
			} catch (Exception e) {
				e.printStackTrace();
				addWarn(new FacesMessage("Error al enviar a revisión"));
			}
		}
	}

	public void onEnviarObservacion() {

		try {
			getRegistro().setPejEstado(1);
			getRegistro().setUsuario2(getObjAppsSession().getUsuario());
			getRegistro().setPejFechaReviso(getToday());
			onSave();
			if (!correoObservacion(textoCorreoObs, getObjAppsSession().getUsuario())) {
				addWarn(new FacesMessage("Error en el envio del correo"));
			}
			revisarPermisosDesarrollo();
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage("Error al enviar las observaciones"));
		}

	}

	public void onFinalizar() {

		try {
			getRegistro().setPejEstado(3);
			getRegistro().setUsuario2(getObjAppsSession().getUsuario());
			getRegistro().setPejFechaReviso(getToday());
			onSave();
			revisarPermisosDesarrollo();
			if (!correoFin(textoCorreoFin, getObjAppsSession().getUsuario())) {
				addWarn(new FacesMessage("Error en el envio del correo"));
			} else {
				int contador =0;
				for(ProcedimientoEjecucion p: getListado()) {
					if(p.getPejEstado()!=3) {
						contador=contador+1;
					}
				}
				
				if(contador==0) {
					bitaMB.finalizarActividad(12, getAuditoria(), getObjAppsSession().getUsuario());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage("Error al finalizar las observaciones"));
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
			subject = "Solicitud de revisión procedimiento de ejecución";
			// cc = auditor.getUsuCorreo();

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
			subject = "Observaciones a procedimiento de ejecución";
			// to = auditor.getUsuCorreo();

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
			subject = "Finalización de revisión a procedimiento de ejecución";
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

	public BitacoraActividadMB getBitaMB() {
		return bitaMB;
	}

	public void setBitaMB(BitacoraActividadMB bitaMB) {
		this.bitaMB = bitaMB;
	}

	public StreamedContent getNarrativa() {

		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			ByteArrayOutputStream os = new ByteArrayOutputStream();

			// Para definir los encabezados de pagina y los pie de pagina
			String str = "<html><head>"
					+ "<style>#header{position: running(header);} @page {margin: 150px 70px 100px;@bottom-right {content: \"Página \" counter(page) \" de \" counter(pages); } @top-center {content: element(header);} }</style>"
					+ "</head><body style='width:500px; font-size:smaller;'>";

			// Defino el texto del encabezado de pagina con el id header que es que se pone
			// arriba running(header)
			str = str + "<div id='header' style='margin-top:20px;'> "
					+ "<strong>Unidad de Auditoría Interna</strong><br/><strong>"
					+ getRegistro().getProgramaEjecucion().getAuditoria().getPlanAnual().getInstitucion().getInsNombre()
					+ "</strong><br/><strong>Cédula Narrativa de Procedimiento de Ejecución</strong></div>";
			// Concateno el texto a agregar
			str = str + "<p><strong>Procedimiento</strong>: " + getRegistro().getPejNombre() + "</p>\r\n" + "\r\n"
					+ "<p><strong>Referencia: </strong>" + getRegistro().getPejReferencia() + "</p>";

			str = str
					+ "<table align=\"left\" border=\"0\" cellpadding=\"1\" cellspacing=\"1\" style=\"width:600px\">\r\n"
					+ "	<tbody>\r\n" + "		<tr>\r\n"
					+ "			<td style=\"height:50px; width:300px\"><strong>Elaborador Por: </strong>"
					+ getRegistro().getUsuario1().getUsuNombre() + "</td>\r\n"
					+ "			<td style=\"width:300px\"><strong>Supervisado Por: </strong>";
			if (getRegistro().getUsuario2() != null) {
				str = str + getRegistro().getUsuario2().getUsuNombre();
			}
			str = str + "</td>\r\n" + "		</tr>\r\n" + "		<tr>\r\n"
					+ "			<td style=\"height:50px; width:300px\"><strong>Fecha Elaboraci&oacute;n: </strong>"
					+ formatter.format(getRegistro().getPejFechaElaboro()) + "</td>\r\n"
					+ "			<td style=\"height:50px; width:300px\"><strong>Fecha Supervisi&oacute;n: </strong>";
			if (getRegistro().getPejFechaReviso() != null) {
				str = str + formatter.format(getRegistro().getPejFechaReviso());
			}
			str = str + "</tr>\r\n" + "	</tbody>\r\n" + "</table>";
			str = str + "<br/><br/><p><strong><u>Narrativa:</u></strong></p><div style='text-align:justify'>"
					+ getRegistro().getPejNarrativa();
			str = str + "</div></body></html>";

			HtmlConverter.convertToPdf(str, os);

			InputStream is = new ByteArrayInputStream(os.toByteArray());
			return new DefaultStreamedContent(is, "application/pdf", "narrativa-ejecucion.pdf");
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage("Advertencia", "Hubo un error al generar el documento de la narrativa"));
		}
		return narrativa;
	}

	public void setNarrativa(StreamedContent narrativa) {
		this.narrativa = narrativa;
	}

	public void revisarPermisosDesarrollo() {
		super.configBean();
		
		if (getAuditoria() != null) {
			setPerEdit(false);

			setRolCoordinadorAuditoria(getObjAppsSession().isCoordinador(getObjAppsSession().getUsuario().getUsuId(),
					getAuditoria().getAuditoriaResponsable()));
			switch (getRegistro().getPejEstado()) {
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
				if (isRolAuditor()) {
					setPerEdit(true);
				}
				break;
			}
		}
	}

}
