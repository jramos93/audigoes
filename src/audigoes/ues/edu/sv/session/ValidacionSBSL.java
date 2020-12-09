package audigoes.ues.edu.sv.session;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import audigoes.ues.edu.sv.entities.administracion.Usuario;
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
							if (!this.isClaveEstandar(usuario, clave)) {
								resp = VAL_USUARIO_VALIDO;
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
}