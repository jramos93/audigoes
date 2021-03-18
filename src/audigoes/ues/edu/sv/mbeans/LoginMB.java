package audigoes.ues.edu.sv.mbeans;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import audigoes.ues.edu.sv.entities.administracion.Institucion;
import audigoes.ues.edu.sv.entities.administracion.Usuario;
import audigoes.ues.edu.sv.security.SecurityController;
import audigoes.ues.edu.sv.util.SendMailAttach;
import audigoes.ues.edu.sv.util.Utils;

@ManagedBean(name = "loginMB")
@SessionScoped
public class LoginMB extends SecurityController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Institucion> institucionList;

	public LoginMB() {
	}

	@PostConstruct
	public void init() {
		fillInstitucionList();
		// Test.main(null);
		super.init();
	}

	protected boolean beforeLogin() {
		this.setOutcome("/page/main.xhtml");
		return true;
	}

	@Override
	protected void afterLogin() {
		configBean();
		super.afterLogin();
	}

	// Método para cerrar sesión
	public String logout() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
		if (session != null) {
			session.invalidate(); // Cierre de sesion
			session = (HttpSession) facesContext.getExternalContext().getSession(true);
		}
		return "/inicio.xhtml?faces-redirect=true";// indicas a donde quieres direccionar después de cerrar sesión
	}

	public void onVerificarLogin(ComponentSystemEvent event) {
		if (this.isLoggedIn()) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			try {
				ctx.getExternalContext().redirect("page/main.xhml");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void olvidoClave() {
		System.out.println("Olvido su clave");
		addInfo(new FacesMessage("Se enviara un correo con las instrucciones para solicitar una nueva clave"));
	}

	@SuppressWarnings({ "unused", "unchecked" })
	public void onNotificar() {
		FacesMessage facesMessage = new FacesMessage();

		try {
//			List<Usuario> user = (List<Usuario>) audigoesLocal.findByCondition(Usuario.class, 
//					"o.usuUsuario='jramos'", null);

			List<Usuario> user = (List<Usuario>) audigoesLocal.findByCondition(Usuario.class, "o.usuUsuario='"
					+ getUsuario() + "' and o.institucion.insId=" + getInstitucionSelected().getInsId(), null);
			if (user != null && !user.isEmpty()) {
				Usuario u = user.get(0);

				if (u.getRegActivo() == 1) {
					if (u.getUsuCorreo() != null) {
						FacesContext ctx = FacesContext.getCurrentInstance();
						long sol = audigoesLocal.registrarSolicitud(u);
						notificaClave(u.getUsuCorreo(), u.getUsuUsuario(), sol);

						facesMessage.setSummary("Correo Enviado");
						facesMessage.setDetail("Se ha enviado un correo con las instrucciones a seguir");
						addWarn(facesMessage);
					} else {
						facesMessage.setSummary("Usuario sin Correo");
						facesMessage.setDetail("Su usuario no posee una cuenta de correo asociada");
						addWarn(facesMessage);
					}
				} else {
					facesMessage.setSummary("Usuario de Baja");
					facesMessage.setDetail("Su usuario se encuentra inactivo");
					addWarn(facesMessage);
				}
			} else {
				facesMessage.setSummary("Usuario no Existe");
				facesMessage.setDetail("Su usuario no se encuentra registrado");
				addWarn(facesMessage);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private int notificaClave(String correo, String usuario, long sol) {
		String user = "";
		String solicitud = "";
		try {
			user = Utils.encode(usuario);
			solicitud = Utils.encode("" + sol);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HttpServletRequest sq = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();

		String subject = "AUDIGOES - Solicitud Cambio de Clave";
		String text = "<br/> Se le informa que se ha solicitado la generación de una nueva clave temporal de acceso para el sistema.<br/><br/>";
		text = text + " Le pedimos que confirme haciendo clic en el siguiente enlace : "
				+ "<strong>&nbsp;<a style='text-decoration=none;' title='Confirmar' href='http://localhost:8080/audigoes/cambioClave?param=" + user + " &param2=" + solicitud
				+ "'<b>Solicitar Clave</b></a></strong>. <br/><br/>";
		text = text
				+ " Si usted no ha solicitado el cambio de clave, comuniquese con el administrador del sistema <br/><br/>";
		String logo = FacesContext.getCurrentInstance().getExternalContext()
				.getRealPath("resources/images/logo-azul.png");

		SendMailAttach sendMailAttach = new SendMailAttach("audigoes.ues@gmail.com", "", correo, subject, text, null,
				logo);
		return sendMailAttach.send();
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

	protected boolean beforeLogout() {
		this.setOutcome("/inicio.xhtml");
		return true;
	}

	public List<Institucion> getInstitucionList() {
		return institucionList;
	}

	public void setInstitucionList(List<Institucion> institucionList) {
		this.institucionList = institucionList;
	}

	public String onChangePasswd() {

		FacesMessage message = new FacesMessage();

		if (this.getClave().equals(this.getClaveNueva())) {
			message.setSummary("Advertencia:");
			message.setDetail("La contraseña no puede ser igual que la anterior");
			addWarn(message);
		} else if (!this.getClaveNueva().equals(this.getClaveConfirmada())) {
			message.setSummary("Advertencia:");
			message.setDetail("La contraseñas son diferentes");
			addWarn(message);
		} else {
			Integer msgCodigo = this.validacionSBSL.cambiarClave(this.getUsuario(), this.getClave(),
					this.getClaveNueva(), this.getInstitucionSelected().getInsId());
			if (msgCodigo != 20) {
				message.setSummary("Advertencia:");
				message.setDetail("Hubo un error al cambiar clave");
				addWarn(message);
			} else {
				message.setSummary("Información:");
				message.setDetail("Se realizo el cambio de clave correctamente");
				addInfo(message);

				if (this.isRunLogout()) {
					return this.onLogout();
				}
			}
		}

		return null;
	}

	@Override
	protected void configBean() {
		// TODO Auto-generated method stub
		super.configBean();
	}
}
