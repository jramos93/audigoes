package audigoes.ues.edu.sv.mbeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.HttpSession;

import audigoes.ues.edu.sv.entities.administracion.Institucion;
import audigoes.ues.edu.sv.security.SecurityController;

@ManagedBean(name = "loginMB")
@SessionScoped
public class LoginMB extends SecurityController implements Serializable{

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
		//Test.main(null);
	}

	protected boolean beforeLogin() {
		this.setOutcome("/page/main.xhtml");
		return true;
	}
	
	//Método para cerrar sesión
		public String logout() {
		        FacesContext facesContext = FacesContext.getCurrentInstance();
		        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
		        if (session != null) {
		            session.invalidate(); //Cierre de sesion
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
	
	@SuppressWarnings("unchecked")
	public void fillInstitucionList() {
		try {
			setInstitucionList((List<Institucion>) audigoesLocal.findByNamedQuery(Institucion.class,
					"institucion.login",
					new Object[] {}));
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
}
