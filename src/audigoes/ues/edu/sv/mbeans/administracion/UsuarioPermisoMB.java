package audigoes.ues.edu.sv.mbeans.administracion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.administracion.Permiso;
import audigoes.ues.edu.sv.entities.administracion.Rol;
import audigoes.ues.edu.sv.entities.administracion.RolPermiso;
import audigoes.ues.edu.sv.entities.administracion.Usuario;
import audigoes.ues.edu.sv.entities.administracion.UsuarioPermiso;

@ManagedBean(name = "uprMB")
@ViewScoped
public class UsuarioPermisoMB extends AudigoesController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Usuario usuario;
	private Rol rol;

	private DualListModel<Permiso> permisosList;
	private List<Permiso> sourcePermisosList;
	private List<Permiso> targetPermisosList;

	@ManagedProperty(value = "#{rolMB}")
	private RolMB rolMB = new RolMB();

	public UsuarioPermisoMB() {
		super(new UsuarioPermiso());
		setSourcePermisosList(new ArrayList<Permiso>());
		setTargetPermisosList(new ArrayList<Permiso>());

		setPermisosList(new DualListModel<Permiso>(sourcePermisosList, targetPermisosList));

	}

	@SuppressWarnings("unchecked")
	public void fillListado() {
		try {
			System.out.println("id " + usuario.getUsuId());
			setListado((List<UsuarioPermiso>) audigoesLocal.findByNamedQuery(UsuarioPermiso.class,
					"permisos.findByUsuario", new Object[] { usuario.getUsuId() }));
			System.out.println("tamaño " + getListado().size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onChangeRol() {
		System.out.println("rol selected: " + rol.getRolId() + " - " + rol.getRolNombre());
		fillPermisosDualList();
	}

	@SuppressWarnings("unchecked")
	public void fillPermisosDualList() {
		try {
			setSourcePermisosList((List<Permiso>) audigoesLocal.findByNamedQuery(Permiso.class,
					"usuario.permiso.get.source.list", new Object[] { rol.getRolId(), usuario.getUsuId() }));
			setTargetPermisosList((List<Permiso>) audigoesLocal.findByNamedQuery(Permiso.class,
					"usuario.permiso.get.target.list", new Object[] { rol.getRolId(), usuario.getUsuId() }));

			setPermisosList(new DualListModel<Permiso>(sourcePermisosList, targetPermisosList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onTransfer(TransferEvent event) {
		try {
			for (Object item : event.getItems()) {
				if (event.isAdd()) {
					System.out.println("Agregado");
					targetPermisosList.add((Permiso) item);
				}
				if (event.isRemove()) {
					System.out.println("Removido");
					targetPermisosList.remove((Permiso) item);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void onSaveUser() {
		try {

			for (Permiso p : getTargetPermisosList()) {
				onNew();
				getRegistro().setPermiso(p);
				getRegistro().setRol(rol);
				getRegistro().setUsuario(usuario);
				getRegistro().setFecCrea(getToday());
				getRegistro().setUsuCrea("ROOT");
				getRegistro().setRegActivo(1);

				audigoesLocal.insert(getRegistro());
				getListado().add(getRegistro());
				addInfo(new FacesMessage("Registros agregados correctamente"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void onSaveUserExt() {

		try {

			for (Permiso p : getTargetPermisosList()) {
				getRegistro().setPermiso(p);
				getRegistro().setRol(rol);
				getRegistro().setFecCrea(getToday());
				getRegistro().setUsuCrea("ROOT");
				getRegistro().setRegActivo(1);
				getListado().add(getRegistro());
				addInfo(new FacesMessage("Registros agregados correctamente"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void onSaveEditUser() {
		System.out.println("edit ");

		List<UsuarioPermiso> rolpermisodelete;
		try {
			rolpermisodelete = (List<UsuarioPermiso>) audigoesLocal.findByNamedQuery(UsuarioPermiso.class,
					"usuario.permiso.delete", new Object[] { usuario.getUsuId() });
			if (rolpermisodelete.size() > 0) {
				for (UsuarioPermiso p : rolpermisodelete) {
					setRegistro(p);
					audigoesLocal.delete(getRegistro());

				}
				getListado().clear();
			}

			System.out.println("getTargetPermisosList " + targetPermisosList.size());
			for (Permiso p : targetPermisosList) {
				System.out.println("new ");
				onNew();
				getRegistro().setPermiso(p);
				getRegistro().setRol(rol);
				getRegistro().setUsuario(usuario);
				getRegistro().setFecCrea(getToday());
				getRegistro().setUsuCrea("ROOTS");
				getRegistro().setRegActivo(1);

				audigoesLocal.insert(getRegistro());
				getListado().add(getRegistro());
			}
			addInfo(new FacesMessage("Registros agregados correctamente"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void addPermiso(String rol, String permiso, Usuario usuario) {
		try {
			if (getObjAppsSession() != null) {
				UsuarioPermiso usuPermiso = searchPermiso(rol, permiso, usuario);

				if (usuPermiso != null && usuPermiso.getUspId() > 0) {
					usuPermiso.setFecModi(getToday());
					usuPermiso.setUsuModi(getObjAppsSession().getUsuario().getUsuUsuario());
					usuPermiso.setRegActivo(1);
					audigoesLocal.update(usuPermiso);
				} else {
					System.out.println("getToday " + getToday());
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

				if (usuPermiso != null && usuPermiso.getUspId() > 0) {
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

	/* GETS y SETS */

	@Override
	public UsuarioPermiso getRegistro() {
		return (UsuarioPermiso) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UsuarioPermiso> getListado() {
		return (List<UsuarioPermiso>) super.getListado();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public RolMB getRolMB() {
		return rolMB;
	}

	public void setRolMB(RolMB rolMB) {
		this.rolMB = rolMB;
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

}
