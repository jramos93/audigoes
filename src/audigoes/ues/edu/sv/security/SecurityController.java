package audigoes.ues.edu.sv.security;

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
import javax.servlet.http.HttpServletRequest;

import org.primefaces.model.menu.MenuModel;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.administracion.Institucion;
import audigoes.ues.edu.sv.entities.administracion.Sesiones;
import audigoes.ues.edu.sv.entities.administracion.Usuario;
import audigoes.ues.edu.sv.entities.administracion.UsuarioPermiso;
import audigoes.ues.edu.sv.session.ValidacionSBSLLocal;
import audigoes.ues.edu.sv.util.Test;
import audigoes.ues.edu.sv.util.Utils;

public class SecurityController extends AudigoesController {

	@EJB(beanName = "ValidacionSBSL")
	protected ValidacionSBSLLocal validacionSBSL;

	private MenuModel menu;
	private String usuario;
	private String clave;
	private Institucion institucionSelected;
	private String claveNueva;
	private String claveConfirmada;
	private String changePassOutcome;
	private Date fechaSistema;
	private boolean runLogout = true;
	private boolean loggedIn = false;
	private boolean produccion = true;
	private Integer msgCodigo = 0;

	public SecurityController() {
		TimeZone tz = TimeZone.getTimeZone("America/El_Salvador");
		this.fechaSistema = Calendar.getInstance(tz, new Locale("es", "SV")).getTime();
	}

	@PostConstruct
	public void init() {
	}

	private List<Institucion> institucionList;

	@SuppressWarnings("unchecked")
	public String onLogin() {
		this.loggedIn = false;
		FacesMessage message = new FacesMessage();
		// System.out.println("institucion "+institucionSelected.getInsNombre());
		try {
			FacesContext ctx = FacesContext.getCurrentInstance();
			if (this.beforeLogin()) {
				if (this.usuario != null && this.clave != null && this.institucionSelected != null) {
					this.msgCodigo = this.validacionSBSL.validar(this.usuario, this.clave,
							this.institucionSelected.getInsId());
					if (this.msgCodigo.equals(ValidacionSBSLLocal.VAL_USUARIO_VALIDO)) {
						ObjAppsSession objAppsSession = new ObjAppsSession();
						Sesiones sesion = null;
						List<Usuario> usrs = (List<Usuario>) this.audigoesLocal.findByNamedQuery(Usuario.class,
								"usuario.findByUsuario", new Object[] { usuario, institucionSelected.getInsId() });
						Usuario usr = usrs.get(0);
						if (usr == null) {
							message.setDetail("Error en login");
							this.addWarn(message);
							return null;
						}

						objAppsSession.setUsuario(usr);
						sesion = this.getInitSesion(usr);
						List<UsuarioPermiso> permisos = (List<UsuarioPermiso>) this.audigoesLocal.findByNamedQuery(
								UsuarioPermiso.class, "permisos.findByUsuario", new Object[] { usr.getUsuId() });
						if (sesion == null) {
							message.setDetail("Error en el registro de la sesion");
							this.addWarn(message);
							return null;
						} else {
							objAppsSession.setPermisos(permisos);
							objAppsSession.setHost(sesion.getSesHostname());
							objAppsSession.setIp(sesion.getSesIp());
							ctx.getExternalContext().getSessionMap().put("audigoes.user.name", this.usuario);
							ctx.getExternalContext().getSessionMap().put("audigoes.session", objAppsSession);
							this.setObjAppsSession(objAppsSession);
							this.loggedIn = true;
							this.afterLogin();
							return this.outcome;
						}

					} else {
						message.setDetail(
								"Error! Usuario y/o clave no valido o no pertenece a la institución seleccionada");
						this.addWarn(message);
						return null;
					}
				} else {
					message.setDetail("Error! ingrese el usuario y clave");
					this.addWarn(message);
					return null;
				}
			} else {
				message.setDetail("Error en el registro de la sesion");
				this.addWarn(message);
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			message.setDetail("Error en el registro de la sesion");
			this.addWarn(message);
			return null;
		}
	}

	private Sesiones getInitSesion(Usuario usuario) {
		Sesiones sesion = null;
		try {
			FacesContext ctx = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
			Date hoy = new Date(System.currentTimeMillis());
			sesion = new Sesiones();
			sesion.setUsuario(usuario);
			sesion.setRegActivo(1);
			sesion.setSesHostname(request.getRemoteHost());
			sesion.setSesIp(request.getRemoteAddr());
			sesion.setSesInicio(hoy);
			sesion.setUsuCrea(usuario.getUsuUsuario());
			sesion.setFecCrea(hoy);
			return (Sesiones) this.audigoesLocal.insert(sesion);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public void fillInstitucionList() {
		try {
			setInstitucionList((List<Institucion>) audigoesLocal.findByNamedQuery(Institucion.class,
					"institucion.login", new Object[] {}));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected boolean beforeLogin() {
		return true;
	}

	protected boolean beforeLogout() {
		// this.setOutcome("/index.xhtml");
		return true;
	}

	protected void afterLogin() {
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public MenuModel getMenu() {
		return menu;
	}

	public void setMenu(MenuModel menu) {
		this.menu = menu;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getClaveNueva() {
		return claveNueva;
	}

	public void setClaveNueva(String claveNueva) {
		this.claveNueva = claveNueva;
	}

	public String getClaveConfirmada() {
		return claveConfirmada;
	}

	public void setClaveConfirmada(String claveConfirmada) {
		this.claveConfirmada = claveConfirmada;
	}

	public String getChangePassOutcome() {
		return changePassOutcome;
	}

	public void setChangePassOutcome(String changePassOutcome) {
		this.changePassOutcome = changePassOutcome;
	}

	public Date getFechaSistema() {
		return fechaSistema;
	}

	public void setFechaSistema(Date fechaSistema) {
		this.fechaSistema = fechaSistema;
	}

	public boolean isRunLogout() {
		return runLogout;
	}

	public void setRunLogout(boolean runLogout) {
		this.runLogout = runLogout;
	}

	public boolean isProduccion() {
		return produccion;
	}

	public void setProduccion(boolean produccion) {
		this.produccion = produccion;
	}

	public Integer getMsgCodigo() {
		return msgCodigo;
	}

	public void setMsgCodigo(Integer msgCodigo) {
		this.msgCodigo = msgCodigo;
	}

	public List<Institucion> getInstitucionList() {
		return institucionList;
	}

	public void setInstitucionList(List<Institucion> institucionList) {
		this.institucionList = institucionList;
	}

	public Institucion getInstitucionSelected() {
		return institucionSelected;
	}

	public void setInstitucionSelected(Institucion institucionSelected) {
		this.institucionSelected = institucionSelected;
	}

}
