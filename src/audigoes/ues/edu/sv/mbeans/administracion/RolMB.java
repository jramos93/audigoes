package audigoes.ues.edu.sv.mbeans.administracion;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.administracion.Rol;
import audigoes.ues.edu.sv.entities.administracion.RolPermiso;
import audigoes.ues.edu.sv.entities.administracion.UsuarioPermiso;

@ManagedBean(name = "rolMB")
@ViewScoped
public class RolMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Rol> filteredRoles;

	@ManagedProperty(value = "#{rlpMB}")
	private RolPermisoMB rlpMB = new RolPermisoMB();

	public RolMB() {
		super(new Rol());
	}

	@PostConstruct
	public void init() {
		try {
			super.init();
			fillListado();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public void fillListado() {
		try {
			if (getObjAppsSession() != null) {
				if (getObjAppsSession().getUsuario() != null) {
					setListado((List<Rol>) audigoesLocal.findByNamedQuery(Rol.class, "rol.by.institucion",
							new Object[] { getObjAppsSession().getUsuario().getInstitucion().getInsId() }));
				}
			} else {
				setListado((List<Rol>) audigoesLocal.findByNamedQuery(Rol.class, "rol.all", new Object[] {}));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
		if (filterText == null || filterText.equals("")) {
			return true;
		}
		int filterInt = getInteger(filterText);

		Rol rol = (Rol) value;
		return rol.getRolNombre().toLowerCase().contains(filterText)
				|| rol.getRolDescripcion().toLowerCase().contains(filterText) || rol.getRolId() == filterInt;
	}

	private int getInteger(String string) {
		try {
			return Integer.valueOf(string);
		} catch (NumberFormatException ex) {
			return 0;
		}
	}

	public void fillPermisos() {
		getRlpMB().setRol(getRegistro());
		getRlpMB().fillListado();
	}

	@Override
	public boolean beforeSaveNew() {
		getRegistro().setInstitucion(getObjAppsSession().getUsuario().getInstitucion());
		return super.beforeSaveNew();
	}

	/* GETS y SETS */

	@Override
	public Rol getRegistro() {
		return (Rol) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Rol> getListado() {
		return (List<Rol>) super.getListado();
	}

	@Override
	public void afterSaveNew() {
		getListado().add(getRegistro());
		super.afterSaveNew();
	}

	@Override
	public boolean beforeDelete() {
		if (!comprobarDelete(getRegistro())) {
			addWarn(new FacesMessage(SYSTEM_NAME, "El rol tiene permisos o usuarios asignados, no puede ser eliminado"));
			return false;
		}
		return super.beforeDelete();
	}
	
	@SuppressWarnings("unchecked")
	public boolean comprobarDelete(Rol rol) {
		try {
			List<RolPermiso> rolPermisos = (List<RolPermiso>) audigoesLocal.findByNamedQuery(RolPermiso.class, "rolpermiso.by.rol",
					new Object[] { rol.getRolId() });
			if(rolPermisos.isEmpty()) {
				return false;
			}
			
			List<UsuarioPermiso> usuPermisos = (List<UsuarioPermiso>) audigoesLocal.findByNamedQuery(UsuarioPermiso.class, "usuariopermiso.by.rol",
					new Object[] { rol.getRolId() });
			
			if(usuPermisos.isEmpty()) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
			
		}
		return true;
	}

	public List<Rol> getFilteredRoles() {
		return filteredRoles;
	}

	public void setFilteredRoles(List<Rol> filteredRoles) {
		this.filteredRoles = filteredRoles;
	}

	public RolPermisoMB getRlpMB() {
		return rlpMB;
	}

	public void setRlpMB(RolPermisoMB rlpMB) {
		this.rlpMB = rlpMB;
	}
}
