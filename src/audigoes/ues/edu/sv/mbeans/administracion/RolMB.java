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
			setListado((List<Rol>) audigoesLocal.findByNamedQuery(Rol.class, "rol.all", new Object[] {}));
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
				|| rol.getRolDescripcion().toLowerCase().contains(filterText)
				|| rol.getRolId() == filterInt;
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
		if(getRegistro().getRolPermiso().size() > 0 ) {
			addWarn(new FacesMessage(SYSTEM_NAME, "El rol tiene permisos asignados, no puede ser eliminado"));
			return false;
		}
		return super.beforeDelete();
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
