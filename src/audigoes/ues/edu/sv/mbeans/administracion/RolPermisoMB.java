package audigoes.ues.edu.sv.mbeans.administracion;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.TransferEvent;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.administracion.Permiso;
import audigoes.ues.edu.sv.entities.administracion.Rol;
import audigoes.ues.edu.sv.entities.administracion.RolPermiso;

@ManagedBean(name = "rlpMB")
@ViewScoped
public class RolPermisoMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<RolPermiso> filteredRolPermisos;
	private Rol rol;

	@ManagedProperty(value = "#{perMB}")
	private PermisoMB perMB = new PermisoMB();

	public RolPermisoMB() {
		super(new RolPermiso());
	}

	@PostConstruct
	public void init() {
		try {
			super.init();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public void fillListado() {
		try {
			setListado((List<RolPermiso>) audigoesLocal.findByNamedQuery(RolPermiso.class,
					"rolpermisos.get.permisos.rol", new Object[] { rol.getRolId() }));
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

		RolPermiso rolpermiso = (RolPermiso) value;
		return rolpermiso.getPermiso().getPerNombre().toLowerCase().contains(filterText)
				|| rolpermiso.getPermiso().getPerDescripcion().toLowerCase().contains(filterText)
				|| rolpermiso.getPermiso().getPerId() == filterInt;
	}

	private int getInteger(String string) {
		try {
			return Integer.valueOf(string);
		} catch (NumberFormatException ex) {
			return 0;
		}
	}

	@Override
	public boolean beforeNew() {
		getPerMB().setRol(getRol());
		getPerMB().fillPermisosDualList();
		return super.beforeNew();
	}

	@SuppressWarnings("unchecked")
	public void asignarPermiso(TransferEvent event) {
		try {
			for (Object item : event.getItems()) {
				if (event.isAdd()) {
					try {
						onNew();
						getRegistro().setRol(getRol());
						getRegistro().setPermiso((Permiso) item);
						onSave();
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
				if (event.isRemove()) {
					try {
						List<RolPermiso> rolpermisodelete = (List<RolPermiso>) audigoesLocal.findByNamedQuery(
								RolPermiso.class, "rolpermisos.delete",
								new Object[] { getRol().getRolId(), ((Permiso) item).getPerId() });
						if(rolpermisodelete.size()>0) {
							setRegistro(rolpermisodelete.get(0));
							onDelete();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/* GETS y SETS */

	@Override
	public RolPermiso getRegistro() {
		return (RolPermiso) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RolPermiso> getListado() {
		return (List<RolPermiso>) super.getListado();
	}

	@Override
	public void afterSaveNew() {
		getListado().add(getRegistro());
		super.afterSaveNew();
	}
	
	@Override
	public void afterDelete() {
		getListado().remove(getRegistro());
		super.afterDelete();
	}

	public List<RolPermiso> getFilteredRolPermisos() {
		return filteredRolPermisos;
	}

	public void setFilteredRolPermisos(List<RolPermiso> filteredRolPermisos) {
		this.filteredRolPermisos = filteredRolPermisos;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public PermisoMB getPerMB() {
		return perMB;
	}

	public void setPerMB(PermisoMB perMB) {
		this.perMB = perMB;
	}
}
