package audigoes.ues.edu.sv.mbeans.administracion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.DualListModel;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.administracion.Permiso;
import audigoes.ues.edu.sv.entities.administracion.Rol;

@ManagedBean(name = "perMB")
@ViewScoped
public class PermisoMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Permiso> filteredPermisos;

	private DualListModel<Permiso> permisosList;

	private List<Permiso> sourcePermisosList;
	private List<Permiso> targetPermisosList;

	private Rol rol;

	public PermisoMB() {
		super(new Permiso());
	}

	@PostConstruct
	public void init() {
		try {
			setSourcePermisosList(new ArrayList<Permiso>());
			setTargetPermisosList(new ArrayList<Permiso>());

			setPermisosList(new DualListModel<Permiso>(sourcePermisosList, targetPermisosList));
			super.init();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public void fillListado() {
		try {
			setListado(
					(List<Permiso>) audigoesLocal.findByNamedQuery(Permiso.class, "permiso.get.all", new Object[] {}));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void fillPermisosDualList() {
		try {
			setSourcePermisosList((List<Permiso>) audigoesLocal.findByNamedQuery(Permiso.class,
					"permiso.get.source.list", new Object[] { getRol().getRolId() }));
			setTargetPermisosList((List<Permiso>) audigoesLocal.findByNamedQuery(Permiso.class,
					"permiso.get.target.list", new Object[] { getRol().getRolId() }));

			setPermisosList(new DualListModel<Permiso>(sourcePermisosList, targetPermisosList));
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

		Permiso permiso = (Permiso) value;
		return permiso.getPerNombre().toLowerCase().contains(filterText)
				|| permiso.getPerDescripcion().toLowerCase().contains(filterText) || permiso.getPerId() == filterInt;
	}

	private int getInteger(String string) {
		try {
			return Integer.valueOf(string);
		} catch (NumberFormatException ex) {
			return 0;
		}
	}

	/* GETS y SETS */

	@Override
	public Permiso getRegistro() {
		return (Permiso) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Permiso> getListado() {
		return (List<Permiso>) super.getListado();
	}

	@Override
	public void afterSaveNew() {
		getListado().add(getRegistro());
		super.afterSaveNew();
	}

	public List<Permiso> getFilteredPermisos() {
		return filteredPermisos;
	}

	public void setFilteredPermisos(List<Permiso> filteredPermisos) {
		this.filteredPermisos = filteredPermisos;
	}

	public DualListModel<Permiso> getPermisosList() {
		return permisosList;
	}

	public void setPermisosList(DualListModel<Permiso> permisosList) {
		this.permisosList = permisosList;
	}

	public List<Permiso> getSourcePermisosList() {
		return sourcePermisosList;
	}

	public void setSourcePermisosList(List<Permiso> sourcePermisosList) {
		this.sourcePermisosList = sourcePermisosList;
	}

	public List<Permiso> getTargetPermisosList() {
		return targetPermisosList;
	}

	public void setTargetPermisosList(List<Permiso> targetPermisosList) {
		this.targetPermisosList = targetPermisosList;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

}
