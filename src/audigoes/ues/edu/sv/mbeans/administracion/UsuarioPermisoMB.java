package audigoes.ues.edu.sv.mbeans.administracion;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.administracion.Permiso;
import audigoes.ues.edu.sv.entities.administracion.Rol;
import audigoes.ues.edu.sv.entities.administracion.Usuario;
import audigoes.ues.edu.sv.entities.administracion.UsuarioPermiso;

@ManagedBean(name = "uprMB")
@ViewScoped
public class UsuarioPermisoMB extends AudigoesController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UsuarioPermisoMB() {
		super(new UsuarioPermiso());
	}

	public void addPermiso(String rol, String permiso, Usuario usuario) {
		try {
			if (getObjAppsSession() != null) {
				UsuarioPermiso usuPermiso = searchPermiso(rol, permiso, usuario);
				
				if(usuPermiso!=null && usuPermiso.getUspId()>0) {
					usuPermiso.setFecModi(getToday());
					usuPermiso.setUsuModi(getObjAppsSession().getUsuario().getUsuUsuario());
					usuPermiso.setRegActivo(1);
					audigoesLocal.update(usuPermiso);
				} else{
					System.out.println("getToday "+getToday());
					usuPermiso.setFecCrea(getToday());
					usuPermiso.setUsuCrea(getObjAppsSession().getUsuario().getUsuUsuario());
					usuPermiso.setRegActivo(1);
					audigoesLocal.insert(usuPermiso);
				}
			} else {
				addWarn(new FacesMessage("Error en el registro de la sesión"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delPermiso(String rol, String permiso, Usuario usuario) {
		try {
			if (getObjAppsSession() != null) {
				UsuarioPermiso usuPermiso = searchPermiso(rol, permiso, usuario);
				
				if(usuPermiso!=null && usuPermiso.getUspId()>0) {
					usuPermiso.setFecModi(getToday());
					usuPermiso.setUsuModi(getObjAppsSession().getUsuario().getUsuUsuario());
					usuPermiso.setRegActivo(0);
					audigoesLocal.update(usuPermiso);
				}
			} else {
				addWarn(new FacesMessage("Error en el registro de la sesión"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public UsuarioPermiso searchPermiso(String rol, String permiso, Usuario usuario) {
		UsuarioPermiso vper = null;
		Rol vrol = (Rol) audigoesLocal.findByPropertyUnique(Rol.class, "rolNombre", rol);
		Permiso vpermiso = (Permiso) audigoesLocal.findByPropertyUnique(Permiso.class, "perNombre", permiso);
		boolean valido = vrol != null && vrol.getRolId() > 0 && vpermiso != null && vpermiso.getPerId() > 0
				&& usuario != null && usuario.getUsuId() > 0;
		if (valido) {
			try {
				List<UsuarioPermiso> lista = (List<UsuarioPermiso>) audigoesLocal.findByNamedQuery(UsuarioPermiso.class,
						"usuario.permiso", new Object[] { vrol.getRolId(), vpermiso.getPerId(), usuario.getUsuId() });
				if (lista != null && lista.size() > 0) {
					vper = lista.get(0);
				} else {
					vper = new UsuarioPermiso();
					vper.setRol(vrol);
					vper.setPermiso(vpermiso);
					vper.setUsuario(usuario);
				}
			} catch (Exception e) {
				addWarn(new FacesMessage("Error al obtener los permisos"));
				e.printStackTrace();
			}
		}
		return vper;
	}
}
