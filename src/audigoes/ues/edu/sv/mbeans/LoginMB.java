package audigoes.ues.edu.sv.mbeans;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.ResourceHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import audigoes.ues.edu.sv.entities.administracion.Institucion;
import audigoes.ues.edu.sv.security.SecurityController;

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

	// M�todo para cerrar sesi�n
	public String logout() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
		if (session != null) {
			session.invalidate(); // Cierre de sesion
			session = (HttpSession) facesContext.getExternalContext().getSession(true);
		}
		return "/inicio.xhtml?faces-redirect=true";// indicas a donde quieres direccionar despu�s de cerrar sesi�n
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
		this.setOutcome("/index.xhtml");
		return true;
	}

	public List<Institucion> getInstitucionList() {
		return institucionList;
	}

	public void setInstitucionList(List<Institucion> institucionList) {
		this.institucionList = institucionList;
	}

	@Override
	protected void configBean() {
		// TODO Auto-generated method stub
		super.configBean();
	}
}
