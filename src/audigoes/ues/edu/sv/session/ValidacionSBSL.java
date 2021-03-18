package audigoes.ues.edu.sv.session;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import audigoes.ues.edu.sv.entities.administracion.Usuario;
import audigoes.ues.edu.sv.util.SendMailAttach;
import audigoes.ues.edu.sv.util.Utils;

@Stateless
@LocalBean
public class ValidacionSBSL implements ValidacionSBSLLocal {
	@EJB(beanName = "audigoesSBSL")
	protected audigoesSBSLLocal audigoesLocal;

	private static final String CLAVE_ESTANDAR = "Cambiame1";
	private static final Integer VAL_USUARIO_NO_EXISTE = 0;
	private static final Integer VAL_USUARIO_VALIDO = 1;
	private static final Integer VAL_USUARIO_DEBE_CAMBIAR_CLAVE = 2;
	private static final Integer VAL_ERROR_EN_CLAVE = 3;
	// private static final Integer VAL_USUARIO_EXPIRADO=4;
	private static final Integer VAL_USUARIO_DE_BAJA = 5;
	// private static final Integer VAL_USUARIO_NO_TIENE_ACCESO=6;

	@SuppressWarnings("unchecked")
	public Integer validar(String usuario, String clave, int institucion) {
		Integer resp = VAL_USUARIO_NO_EXISTE;
		// String claveAES="";
		String claveSha384 = "";

		try {
			List<Usuario> usrs = (List<Usuario>) this.audigoesLocal.findByNamedQuery(Usuario.class,
					"usuario.findByUsuario", new Object[] { usuario, institucion });
			if (!usrs.isEmpty()) {
				Usuario usr = usrs.get(0);
				if (usr != null) {
					if (usr.getRegActivo() == 1) {
						
							claveSha384 = Utils.getShaString(usuario + clave);
							if (usr.getUsuContrasenia().equals(claveSha384)) {
								if (usr.getUsuExpirado() == 0) {
									if (!this.isClaveEstandar(usuario, clave)) {
										resp = VAL_USUARIO_VALIDO;
									} else {
										resp = VAL_USUARIO_DEBE_CAMBIAR_CLAVE;
									}
								} else {
									resp = VAL_USUARIO_DEBE_CAMBIAR_CLAVE;
								}
								
							} else {
								resp = VAL_ERROR_EN_CLAVE;
							}
						
					} else {
						resp = VAL_USUARIO_DE_BAJA;
					}
				}
			} else {
				resp = VAL_ERROR_EN_CLAVE;
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp = VAL_ERROR_EN_CLAVE;
		}
		return resp;
	}

	public boolean isClaveEstandar(String usuario, String clave) {
		boolean estandar = false;
		String claveSha384 = "";
		claveSha384 = Utils.getShaString(usuario + CLAVE_ESTANDAR);
		if (clave.equals(claveSha384)) {
			estandar = true;
		}
		return estandar;
	}

	@SuppressWarnings("unchecked")
	public Integer generarClave(String usuario, int insId) {
		Integer resp = 32;
		String pass = "";

		System.out.println("Integer generarClave");
		try {
			List<Usuario> user = (List<Usuario>) audigoesLocal.findByCondition(Usuario.class,
					"o.usuUsuario='" + usuario + "' and o.institucion.insId=" + insId, null);
			if (user != null && !user.isEmpty()) {
				Usuario u = user.get(0);
				if (u.getUsuCorreo() != null) {
					pass = Utils.getPassword(8);
					//String correo = u.getUsuCorreo();
					System.out.println("pass "+pass);
					u.setUsuContrasenia(Utils.getHashPassword(u.getUsuUsuario()+ pass));
					System.out.println("pass2 "+u.getUsuContrasenia());
					u.setUsuExpirado(1);
					u.setUsuFechaCambioClave(new Date());
					if (audigoesLocal.update(u) != null) {
						if(notificaClave(u.getUsuCorreo(), pass)==1) {
							resp=20;
						} else {
							resp=30;
						}
					}

				}
			}
		} catch (Exception e) {
			System.out.println("F");
			e.printStackTrace();
		}

		return resp;
	}
	
	@SuppressWarnings("unused")
	private int notificaClave(String correo, String clave) {
		String subject = "AUDIGOES - Cambio de Clave";
		String text = "<br/> Se le informa que se ha solicitado la generación de una nueva clave temporal de acceso para el sistema.<br/><br/>";
		text = text + " Su clave temporal de acceso es: <b> " + clave + "</b><br/><br/>";
		text = text + " El sistema solicitará el cambio automaticamente al ingresar<br/><br/>";
		text = text
				+ " Si usted no ha solicitado el cambio de clave, comuniquese con el administrador del sistema <br/><br/>";
		//String logo = FacesContext.getCurrentInstance().getExternalContext()
//				.getRealPath("resources/images/logo-azul.png");

		SendMailAttach sendMailAttach = new SendMailAttach("audigoes.ues@gmail.com", "", correo, subject, text, null,
				null);
		return sendMailAttach.send();
	}

	public Integer cambiarClave(String usuario, String clave, String nuevaClave, int institucion) {
		Integer resp = 22;

		if (!clave.equals(nuevaClave)) {
			Integer valClave = this.validarClave(nuevaClave);
			if (valClave == 20) {
				if (actualizarClave(usuario, nuevaClave, institucion)) {
					resp = 20;
				} else {
					resp = 32;
				}
			}
		}

		return resp;
	}

	public Integer validarClave(String clave) {
		return 20;
	}

	@SuppressWarnings("unchecked")
	public boolean actualizarClave(String usuario, String nuevaClave, int institucion) {
		boolean resp = false;

		try {
			List<Usuario> user = (List<Usuario>) audigoesLocal.findByCondition(Usuario.class,
					"o.usuUsuario='" + usuario + "' and o.institucion.insId=" + institucion, null);
			if (user != null && !user.isEmpty()) {
				Usuario u = user.get(0);
				u.setUsuContrasenia(Utils.getHashPassword(u.getUsuUsuario() + nuevaClave));
				u.setUsuFechaCambioClave(new Date());
				u.setUsuExpirado(0);
				if (this.audigoesLocal.update(u) != null) {
					resp = true;
				}
			}

			return resp;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}