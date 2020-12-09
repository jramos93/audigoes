package audigoes.ues.edu.sv.session;

import javax.ejb.Local;

@Local
public interface ValidacionSBSLLocal {
	
	String CLAVE_ESTANDAR = "Cambiame1";
	Integer VAL_USUARIO_NO_EXISTE=0;
	Integer VAL_USUARIO_VALIDO=1;
	Integer VAL_USUARIO_DEBE_CAMBIAR_CLAVE=2;
	Integer VAL_ERROR_EN_CLAVE=3;
	//private static final Integer VAL_USUARIO_EXPIRADO=4;
	Integer VAL_USUARIO_DE_BAJA=5;
	//private static final Integer VAL_USUARIO_NO_TIENE_ACCESO=6;
	Integer validar(String usuario, String clave, int institucion);
	boolean isClaveEstandar(String usuario, String clave);
}
