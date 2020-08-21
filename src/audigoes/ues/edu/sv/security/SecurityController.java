package audigoes.ues.edu.sv.security;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.model.menu.MenuModel;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.session.ValidacionSBSLLocal;

public class SecurityController extends AudigoesController{
	
	@EJB(beanName = "ValidacionSBSL")
	protected ValidacionSBSLLocal validacionSBSL;
	
	private MenuModel menu;
	private String usuario;
	private String clave;
	private String claveNueva;
	private String claveConfirmada;
	private String changePassOutcome;
	private Date fechaSistema;
	private boolean runLogout=true;
	private boolean loggedIn=false;
	private boolean produccion=true;
	private Integer msgCodigo=0;
	
	public SecurityController() {
		TimeZone tz = TimeZone.getTimeZone("America/El_Salvador");
		this.fechaSistema=Calendar.getInstance(tz, new Locale("es","SV")).getTime();
	}
	
	@PostConstruct
	public void init() {}
	
	public String onLogin() {
		this.loggedIn=false;
		FacesMessage message = new FacesMessage();
		try {
			FacesContext ctx = FacesContext.getCurrentInstance();
			if(this.beforeLogin()) {
				if(this.usuario!=null && this.clave!=null) {
					this.msgCodigo=this.validacionSBSL.validar(this.usuario, this.clave);
					
					if(this.msgCodigo.equals(ValidacionSBSLLocal.VAL_USUARIO_VALIDO)) {
						System.out.println("inicio");
					} else {
						System.out.println("no inicio");
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	protected boolean beforeLogin() {
		return true;
	}
	
	protected boolean beforeLogout() {
		//this.setOutcome("/index.xhtml");
		return true;
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
	
	
}
