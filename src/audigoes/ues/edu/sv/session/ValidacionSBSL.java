package audigoes.ues.edu.sv.session;

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
	private static final Integer VAL_USUARIO_NO_EXISTE=0;
	private static final Integer VAL_USUARIO_VALIDO=1;
	private static final Integer VAL_USUARIO_DEBE_CAMBIAR_CLAVE=2;
	private static final Integer VAL_ERROR_EN_CLAVE=3;
	//private static final Integer VAL_USUARIO_EXPIRADO=4;
	private static final Integer VAL_USUARIO_DE_BAJA=5;
	//private static final Integer VAL_USUARIO_NO_TIENE_ACCESO=6;
	
	public Integer validar(String usuario, String clave) {
		Integer resp=VAL_USUARIO_NO_EXISTE;
		String claveAES="";
		String claveMD5="";
		
		try {
			Usuario usr = (Usuario) this.audigoesLocal.findByNamedQuery(Usuario.class, "usu.by.usuario", new Object[] {usuario});
			if(usr!=null) {
				if(usr.getRegActivo()==1) {
					claveAES=Utils.getAESString(clave);
					claveMD5=Utils.getMD5String(claveAES);
					if(usr.getUsuContrasenia().equals(claveMD5)) {
						if(!this.isClaveEstandar(usuario, clave)) {
							resp=VAL_USUARIO_VALIDO;
						} else {
							resp=VAL_USUARIO_DEBE_CAMBIAR_CLAVE;
						}
					} else {
						resp=VAL_ERROR_EN_CLAVE;
					}
				} else {
					resp=VAL_USUARIO_DE_BAJA;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	public boolean isClaveEstandar(String usuario, String clave) {
		boolean estandar=false;
		String claveAES="";
		String claveMD5="";
		claveAES=Utils.getAESString(usuario+CLAVE_ESTANDAR);
		claveMD5=Utils.getMD5String(claveAES);
		if(clave.equals(claveMD5)) {
			estandar=true;
		}
		return estandar;
	}
}