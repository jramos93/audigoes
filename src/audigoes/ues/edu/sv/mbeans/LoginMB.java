package audigoes.ues.edu.sv.mbeans;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import audigoes.ues.edu.sv.security.SecurityController;

@ManagedBean(name = "loginMB")
@SessionScoped
public class LoginMB extends SecurityController{
	
	public LoginMB() {
		
	}
	
	@PostConstruct
	public void init() {}
	
	protected boolean beforeLogin() {
		//this.setOutcome("/page/main.xhtml");
		return true;
	}
	
	public void onVerificarLogin(ComponentSystemEvent event) {
		if(this.isLoggedIn()) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			try {
				ctx.getExternalContext().redirect("page/main.xhml");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	protected boolean beforeLogout() {
		//this.setOutcome("/index.xhtml");
		return true;
	}
}
