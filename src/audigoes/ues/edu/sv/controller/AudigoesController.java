
package audigoes.ues.edu.sv.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.context.PrimeFacesContext;

import audigoes.ues.edu.sv.entities.SuperEntity;
import audigoes.ues.edu.sv.entities.administracion.Usuario;
import audigoes.ues.edu.sv.security.ObjAppsSession;
import audigoes.ues.edu.sv.session.audigoesSBSLLocal;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

public class AudigoesController {

	public static final String SYSTEM_NAME = "AUDIGOES";
	public static final String REPORT_PATH = "/WEB-INF/reportes/";

	private boolean rolAuditor;
	private boolean rolCoordinadorAuditoria;
	private boolean rolCoordinador;
	private boolean rolJefe;
	private boolean rolAuditado;
	private boolean rolAuditorExterno;
	private boolean rolAdministrador;

	private boolean perNew;
	private boolean perEdit;
	private boolean perRead;
	private boolean perDelete;
	private boolean perPrint;
	private boolean perRoot;
	private boolean perAutorizar;
	private boolean perAprobar;
	private boolean perEnviar;
	private boolean perDescargar;
	private boolean perSubir;

	@EJB(beanName = "audigoesSBSL")
	protected audigoesSBSLLocal audigoesLocal;

	public audigoesSBSLLocal getAudigoesLocal() {
		return audigoesLocal;
	}

	public void setAudigoesLocal(audigoesSBSLLocal audigoesLocal) {
		this.audigoesLocal = audigoesLocal;
	}

	public AudigoesController() {
		super();
	}

	public AudigoesController(SuperEntity registro) {
		this.setRegistro(registro);
		if (this.getRegistro() instanceof SuperEntity) {
			((SuperEntity) registro).setRegActivo(1);
		}
	}

	@PostConstruct
	public void init() {
		//System.out.println("init");
		configBean();
	}

	private boolean error = false;
	private String status = "SEARCH"; // NEW, EDIT, SEARCH

	/* propiedades para objetos de entidad */
	private SuperEntity registro;
	private SuperEntity regSelected;

	/* propiedades para listados de objeto de entidad */
	private List<? extends SuperEntity> listado;
	private List<? extends SuperEntity> filteredListado;
	private List<? extends SuperEntity> selectedListado;

	/* propiedades de utilerìa básica */
	private List<SelectItem> siNoList;
	private List<SelectItem> regActivoList;
	private List<SelectItem> generoList;
	private List<SelectItem> faseAuditoriaList;

	/* Listado de usuarios de una institución - usuario seleccionado */
	private List<Usuario> usuariosInstitucionList;
	private Usuario usuarioSelected;

	/* propiedades para los reportes */
	private String pathReporte;
	private String reportId;

	protected ObjAppsSession objAppsSession;
	protected String outcome;

	public void addInfo(FacesMessage mensaje) {
		mensaje.setSeverity(FacesMessage.SEVERITY_INFO);
		if (mensaje.getSummary() == null) {
			mensaje.setSummary("INFORMACIÓN");
		}
		PrimeFacesContext.getCurrentInstance().addMessage(null, mensaje);
	}

	public void addWarn(FacesMessage mensaje) {
		mensaje.setSeverity(FacesMessage.SEVERITY_WARN);
		if (mensaje.getSummary() == null) {
			mensaje.setSummary("ADVERTENCIA");
		}
		PrimeFacesContext.getCurrentInstance().addMessage(null, mensaje);
	}

	public void addError(FacesMessage mensaje) {
		mensaje.setSeverity(FacesMessage.SEVERITY_ERROR);
		if (mensaje.getSummary() == null) {
			mensaje.setSummary("ERROR");
		}
		PrimeFacesContext.getCurrentInstance().addMessage(null, mensaje);
	}

	public void addFatal(FacesMessage mensaje) {
		mensaje.setSeverity(FacesMessage.SEVERITY_FATAL);
		if (mensaje.getSummary() == null) {
			mensaje.setSummary("FATAL");
		}
		PrimeFacesContext.getCurrentInstance().addMessage(null, mensaje);
	}

	public Date getToday() {
		TimeZone timeZone = TimeZone.getTimeZone("UTC-6");
		return Calendar.getInstance(timeZone).getTime();
	}

	/* Acciones al crear nuevo objeto */
	public boolean beforeNew() {
		return true;
	}

	public void onNew() {
		if (beforeNew()) {
			if (getRegistro() != null) {
				try {
					setRegistro((SuperEntity) getRegistro().getClass().newInstance());
					getRegistro().setRegActivo(1);
					getRegistro().setFecCrea(getToday());
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			setStatus("NEW");
			afterNew();
		}
	}

	public void afterNew() {
	}

	/* Acciones al editar objeto */
	public boolean beforeEdit() {
		return true;
	}

	public void onEdit() {
		if (beforeEdit()) {
			setStatus("EDIT");
			afterEdit();
		}
	}

	protected void afterEdit() {
	}

	/* Acciones al visualizar objeto */
	public boolean beforeShow() {
		return true;
	}

	public void onShow() {
		if (beforeShow()) {
			setStatus("SHOW");
			afterShow();
		}
	}

	protected void afterShow() {
	}

	/* Eliminar registro */

	public boolean beforeDelete() {
		return true;
	}

	public void onDelete() {
		try {
			if (beforeDelete()) {
				audigoesLocal.delete(getRegistro());
				this.addInfo(new FacesMessage("Confirmación", "Se eliminó el registro seleccionado"));
				afterDelete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void afterDelete() {

	}

	/* Acciones al cancelar */
	public boolean beforeCancel() {
		return true;
	}

	public void onCancel() {
		try {
			if (beforeCancel()) {
				if (getRegistro() != null) {
					// setRegistro(null);
					setRegistro((SuperEntity) getRegistro().getClass().newInstance());
					setStatus("SEARCH");
				}
			}
			afterCancel();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void afterCancel() {
	}

	/* Funciones para guardar */
	public boolean beforeSave() {
		return true;
	}

	public void onSave() {
		try {
			if (beforeSave()) {
				if (getStatus().equals("NEW")) {
					onSaveNew();
				}
				if (getStatus().equals("EDIT")) {
					onSaveEdit();
				}
				if (!isError()) {
					afterSave();
				} else if (getRegistro() == null) {
					try {
						setRegistro((SuperEntity) getRegistro().getClass().newInstance());
					} catch (InstantiationException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void afterSave() {
		setStatus("SEARCH");
	}

	/* Funciones para guardar nuevo */
	public boolean beforeSaveNew() {
		return true;
	}

	public void onSaveNew() {
		try {
			if (beforeSaveNew()) {
				this.saveNewAudit();
				audigoesLocal.insert(getRegistro());
				this.addInfo(new FacesMessage("Confirmación", "Registro Guardado con Éxito"));
				afterSaveNew();
			} else {
				setError(true); // para no ejecutar afterSave en el método onSave()
			}
		} catch (Exception e) {
			this.addWarn(new FacesMessage("Error!", "Consulte con el Administrador"));
			setError(true);
			e.printStackTrace();
		}
	}

	public void saveNewAudit() {
		if (this.getRegistro() instanceof SuperEntity) {
			this.getRegistro().setUsuCrea(getObjAppsSession().getUsuario().getUsuUsuario());
			this.getRegistro().setFecCrea(Calendar.getInstance(Locale.getDefault()).getTime());
		}
	}

	public void afterSaveNew() {
	}

	/* Funciones para guardar edicion */
	public boolean beforeSaveEdit() {
		return true;
	}

	public void onSaveEdit() {
		try {
			if (beforeSaveEdit()) {
				this.saveEditAudit();
				audigoesLocal.update(getRegistro());
				this.addInfo(new FacesMessage("Confirmación", "Registro Guardado con Éxito"));
				afterSaveEdit();
			} else {
				setError(true);
			}
		} catch (Exception e) {
			setError(true);
			this.addWarn(new FacesMessage("Error!", "Consulte con el Administrador"));
			e.printStackTrace();
		}
	}

	public void saveEditAudit() {
		if (this.getRegistro() instanceof SuperEntity) {
			this.getRegistro().setUsuModi(getObjAppsSession().getUsuario().getUsuUsuario());
			this.getRegistro().setFecModi(Calendar.getInstance(Locale.getDefault()).getTime());
		}
	}

	public void afterSaveEdit() {
	}

	/* Funciones para seleccion del registro */
	protected boolean beforeRowSelect() {
		return true;
	}

	public void onRowSelect() {
		if (this.beforeRowSelect()) {
			this.setRegistro(this.getRegSelected());
			this.getRegistro().setSelected(true);
			this.afterRowSelect();
		}
	}

	protected void afterRowSelect() {

	}

	public void onShowSelected() {
		this.onRowSelect();
		this.onShow();
	}

	public void onEditSelected() {
		this.onRowSelect();
		this.onEdit();
	}

	/* Generar Reporte */

	@SuppressWarnings({ "rawtypes", "deprecation", "null", "unchecked" })
	public void getReport() throws ClassNotFoundException, InstantiationException {
		/* Parámetros reporte */
		Map params = new HashMap();
		try {
			setReportId("rpt_plan.jasper");
			File archivo = new File(
					FacesContext.getCurrentInstance().getExternalContext().getRealPath(REPORT_PATH + getReportId()));

			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance()
					.getExternalContext().getResponse();
			httpServletResponse.setContentType("application/pdf");
			httpServletResponse.addHeader("Content-Type", "application/pdf");

			JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(archivo.getPath());
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params);

			JRExporter jrExporter = null;
			jrExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			jrExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, httpServletResponse);

			if (jrExporter != null) {
				try {
					jrExporter.exportReport();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void fillUsuariosInstitucionList() {
		try {
			setUsuariosInstitucionList(
					(List<Usuario>) audigoesLocal.findByNamedQuery(Usuario.class, "usuario.get.all.institucion",
							new Object[] { getObjAppsSession().getUsuario().getInstitucion().getInsId() }));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String reemplazarTildes(String text) {
		return text.replace("á", "&aacute;").replace("Á", "&Aacute;").replace("é", "&aacute;").replace("É", "&Aacute;")
				.replace("í", "&aacute;").replace("Í", "&Aacute;").replace("ó", "&aacute;").replace("Ó", "&Aacute;")
				.replace("ú", "&aacute;").replace("Ú", "&Aacute;").replace("ñ", "&ntilde;").replace("Ñ", "&Nacute;");
	}

	/* GET Y SET */

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public SuperEntity getRegistro() {
		return this.registro;
	}

	public void setRegistro(SuperEntity registro) {
		this.registro = registro;
	}

	public SuperEntity getRegSelected() {
		return this.regSelected;
	}

	public void setRegSelected(SuperEntity regSelected) {
		this.regSelected = regSelected;
	}

	public List<? extends SuperEntity> getListado() {
		return listado;
	}

	public void setListado(List<? extends SuperEntity> listado) {
		this.listado = listado;
	}

	public List<? extends SuperEntity> getFilteredListado() {
		return filteredListado;
	}

	public void setFilteredListado(List<? extends SuperEntity> filteredListado) {
		this.filteredListado = filteredListado;
	}

	public List<? extends SuperEntity> getSelectedListado() {
		return selectedListado;
	}

	public void setSelectedListado(List<? extends SuperEntity> selectedListado) {
		this.selectedListado = selectedListado;
	}

	public List<SelectItem> getSiNoList() {
		return siNoList;
	}

	public void setSiNoList(List<SelectItem> siNoList) {
		this.siNoList = siNoList;
	}

	public List<SelectItem> getRegActivoList() {
		if (this.regActivoList == null) {
			this.regActivoList = new ArrayList<>();
			this.regActivoList.add(new SelectItem(1, "ACTIVO"));
			this.regActivoList.add(new SelectItem(0, "INACTIVO"));
		}
		return regActivoList;
	}

	public void setRegActivoList(List<SelectItem> regActivoList) {
		this.regActivoList = regActivoList;
	}

	public List<SelectItem> getFaseAuditoriaList() {
		if (this.faseAuditoriaList == null) {
			this.faseAuditoriaList = new ArrayList<>();
			this.faseAuditoriaList.add(new SelectItem(0, "PROGRAMADA"));
			this.faseAuditoriaList.add(new SelectItem(1, "ASIGNADA"));
			this.faseAuditoriaList.add(new SelectItem(2, "FASE PLANIFICACIÓN"));
			this.faseAuditoriaList.add(new SelectItem(3, "FASE EJECUCIÓN"));
			this.faseAuditoriaList.add(new SelectItem(4, "FASE INFORME"));
			this.faseAuditoriaList.add(new SelectItem(5, "FASE SEGUIMIENTO"));
		}
		return faseAuditoriaList;
	}

	public void setFaseAuditoriaList(List<SelectItem> faseAuditoriaList) {
		this.faseAuditoriaList = faseAuditoriaList;
	}

	public List<Usuario> getUsuariosInstitucionList() {
		return usuariosInstitucionList;
	}

	public void setUsuariosInstitucionList(List<Usuario> usuariosInstitucionList) {
		this.usuariosInstitucionList = usuariosInstitucionList;
	}

	public Usuario getUsuarioSelected() {
		return usuarioSelected;
	}

	public void setUsuarioSelected(Usuario usuarioSelected) {
		this.usuarioSelected = usuarioSelected;
	}

	public ObjAppsSession getObjAppsSession() {
		if (this.objAppsSession == null) {
			this.objAppsSession = (ObjAppsSession) FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().get("audigoes.session");
		}
		return this.objAppsSession;
	}

	public void setObjAppsSession(ObjAppsSession objAppsSession) {
		this.objAppsSession = objAppsSession;
	}

	public String getOutcome() {
		return outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	public List<SelectItem> getGeneroList() {
		if (generoList == null) {
			generoList = new ArrayList<SelectItem>();
			generoList.add(new SelectItem(0, "F"));
			generoList.add(new SelectItem(1, "M"));
		}
		return generoList;
	}

	public void setGeneroList(List<SelectItem> generoList) {
		this.generoList = generoList;
	}

	public String getPathReporte() {
		return pathReporte;
	}

	public void setPathReporte(String pathReporte) {
		this.pathReporte = pathReporte;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public boolean isRolAuditor() {
		return rolAuditor;
	}

	public void setRolAuditor(boolean rolAuditor) {
		this.rolAuditor = rolAuditor;
	}

	public boolean isRolCoordinador() {
		return rolCoordinador;
	}

	public void setRolCoordinador(boolean rolCoordinador) {
		this.rolCoordinador = rolCoordinador;
	}

	public boolean isRolJefe() {
		return rolJefe;
	}

	public void setRolJefe(boolean rolJefe) {
		this.rolJefe = rolJefe;
	}

	public boolean isRolAuditado() {
		return rolAuditado;
	}

	public void setRolAuditado(boolean rolAuditado) {
		this.rolAuditado = rolAuditado;
	}

	public boolean isRolAuditorExterno() {
		return rolAuditorExterno;
	}

	public void setRolAuditorExterno(boolean rolAuditorExterno) {
		this.rolAuditorExterno = rolAuditorExterno;
	}

	public boolean isRolAdministrador() {
		return rolAdministrador;
	}

	public void setRolAdministrador(boolean rolAdministrador) {
		this.rolAdministrador = rolAdministrador;
	}

	public boolean isPerNew() {
		return perNew;
	}

	public void setPerNew(boolean perNew) {
		this.perNew = perNew;
	}

	public boolean isPerEdit() {
		return perEdit;
	}

	public void setPerEdit(boolean perEdit) {
		this.perEdit = perEdit;
	}

	public boolean isPerRead() {
		return perRead;
	}

	public void setPerRead(boolean perRead) {
		this.perRead = perRead;
	}

	public boolean isPerDelete() {
		return perDelete;
	}

	public void setPerDelete(boolean perDelete) {
		this.perDelete = perDelete;
	}

	public boolean isPerPrint() {
		return perPrint;
	}

	public void setPerPrint(boolean perPrint) {
		this.perPrint = perPrint;
	}

	public boolean isPerRoot() {
		return perRoot;
	}

	public void setPerRoot(boolean perRoot) {
		this.perRoot = perRoot;
	}

	public boolean isPerAutorizar() {
		return perAutorizar;
	}

	public void setPerAutorizar(boolean perAutorizar) {
		this.perAutorizar = perAutorizar;
	}

	public boolean isPerAprobar() {
		return perAprobar;
	}

	public void setPerAprobar(boolean perAprobar) {
		this.perAprobar = perAprobar;
	}

	public boolean isPerEnviar() {
		return perEnviar;
	}

	public void setPerEnviar(boolean perEnviar) {
		this.perEnviar = perEnviar;
	}

	public boolean isPerDescargar() {
		return perDescargar;
	}

	public void setPerDescargar(boolean perDescargar) {
		this.perDescargar = perDescargar;
	}

	public boolean isPerSubir() {
		return perSubir;
	}

	public void setPerSubir(boolean perSubir) {
		this.perSubir = perSubir;
	}

	public boolean isRolCoordinadorAuditoria() {
		return rolCoordinadorAuditoria;
	}

	public void setRolCoordinadorAuditoria(boolean rolCoordinadorAuditoria) {
		this.rolCoordinadorAuditoria = rolCoordinadorAuditoria;
	}

	protected void configBean() {
		//System.out.println("controller");
		if (getObjAppsSession() != null) {
			setPerRoot(getObjAppsSession().isRolValido("ROOT"));
			setRolAdministrador(getObjAppsSession().isRolValido("ROOT"));
			setRolAuditor(getObjAppsSession().isRolValido("AUDITOR"));
			setRolCoordinador(getObjAppsSession().isRolValido("COORDINADOR"));
			setRolJefe(getObjAppsSession().isRolValido("JEFE"));
			setRolAuditado(getObjAppsSession().isRolValido("AUDITADO"));
			setRolAuditorExterno(getObjAppsSession().isRolValido("EXTERNO"));
			setRolCoordinadorAuditoria(false);
			
			setPerEnviar(getObjAppsSession().isPermisoValido("ENVIAR"));
			setPerAutorizar(getObjAppsSession().isPermisoValido("AUTORIZAR"));
			setPerAprobar(getObjAppsSession().isPermisoValido("APROBAR"));
			
			//System.out.println(" Auditor: "+isRolAuditor());
			//System.out.println(" Coordinador: "+isRolCoordinador());
			//System.out.println(" Jefe: "+isRolJefe());
		}

		if (isPerRoot()) {
			
			setPerNew(true);
			setPerEdit(true);
			setPerRead(true);
			setPerDelete(true);
			setPerAutorizar(true);
			setPerAprobar(true);
			setPerEnviar(true);
			setPerDescargar(true);
			setPerPrint(true);
			setPerSubir(true);
		}
	}

}
