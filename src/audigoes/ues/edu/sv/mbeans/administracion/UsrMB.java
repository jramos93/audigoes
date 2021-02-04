package audigoes.ues.edu.sv.mbeans.administracion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.administracion.Unidad;
import audigoes.ues.edu.sv.entities.administracion.Usuario;
import audigoes.ues.edu.sv.entities.administracion.UsuarioUnidad;
import audigoes.ues.edu.sv.util.Utils;

@ManagedBean(name = "usrMB")
@ViewScoped
public class UsrMB extends AudigoesController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Usuario> filteredUsuarios;

	@ManagedProperty(value = "#{uprMB}")
	private UsuarioPermisoMB uprMB = new UsuarioPermisoMB();

	private boolean auditor = false;
	private boolean auditorCreate;
	private boolean auditorRead;
	private boolean auditorUpdate;
	private boolean auditorDelete;
	private boolean generalLogin;

	private List<Unidad> unidadList;
	private List<Unidad> unidadesSelectedList;

	public UsrMB() {
		super(new Usuario());
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
					setListado((List<Usuario>) audigoesLocal.findByNamedQuery(Usuario.class, "usuarios.by.institucion",
							new Object[] { getObjAppsSession().getUsuario().getInstitucion().getInsId() }));
				}
			} else {
				setListado(
						(List<Usuario>) audigoesLocal.findByNamedQuery(Usuario.class, "usuarios.all", new Object[] {}));
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

		Usuario usuario = (Usuario) value;
		return usuario.getUsuNombre().toLowerCase().contains(filterText)
				|| usuario.getUsuCorreo().toLowerCase().contains(filterText) || usuario.getUsuId() == filterInt;
	}

	@Override
	public void onEditSelected() {
		System.out.println(" onEdit ");
		super.onEditSelected();
		System.out.println(" onEdit " + getRegistro().getUsuarioUnidad().size());

		unidadesSelectedList = new ArrayList<Unidad>();
		for (UsuarioUnidad usu : getRegistro().getUsuarioUnidad()) {
			unidadesSelectedList.add(usu.getUnidad());
		}
	}

	private int getInteger(String string) {
		try {
			return Integer.valueOf(string);
		} catch (NumberFormatException ex) {
			return 0;
		}
	}

	public void onSaveUser() {
		if (getStatus().equals("NEW")) {
			onCreateUser();
			uprMB.setUsuario(getRegistro());
			uprMB.onSaveUser();
			guardarUnidades();
		} else if (getStatus().equals("EDIT")) {
			onEditUser();
			onSaveEdit();
			guardarUnidadesEdit();
		}
	}

	public void onCreateUser() {
		try {
			getRegistro().setFecCrea(getToday());
			getRegistro().setUsuCrea(getObjAppsSession().getUsuario().getUsuUsuario());
			getRegistro().setUsuContrasenia(onGeneratePassword(getRegistro()));
			getRegistro().setInstitucion(getObjAppsSession().getUsuario().getInstitucion());
			getRegistro().setRegActivo(1);
			audigoesLocal.insert(getRegistro());
			if (getRegistro() != null && getRegistro().getUsuId() > 0) {
				// uprMB.addPermiso(Utils.rolGeneral, Utils.perLogin, getRegistro());
				// addDelPermisos();
				getListado().add(getRegistro());
				addInfo(new FacesMessage("Confirmación", "Usuario creado correctamente"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage("Error", "Consulte con el administrador"));
		}
	}

	public void onEditUser() {
		try {
			if (getRegistro() != null && getRegistro().getUsuId() > 0) {
				// addDelPermisos();
				addInfo(new FacesMessage("Confirmación", "Usuario guardado correctamente"));
//				uprMB.setUsuario(getRegistro());
//				uprMB.fillListado();
			}
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage("Error", "Consulte con el administrador"));
		}
	}

	public String onGeneratePassword(Usuario u) {
		try {
			return Utils.getShaString(u.getUsuUsuario() + Utils.claveEstandar);
		} catch (Exception e) {
			e.printStackTrace();
			return "XXX";
		}
	}

	@Override
	public void onNew() {
		fillUnidadList();
		super.onNew();
	}

	@Override
	public boolean beforeEdit() {
		fillUnidadList();
		return super.beforeEdit();
	}

	public void fillPermisos() {
//		if (getRegistro() != null) {
//			try {
//				setAuditor(uprMB.searchPermiso(Utils.rolAuditor, Utils.perGeneral, getRegistro()).getUspId() > 0);
//				setAuditorCreate(uprMB.searchPermiso(Utils.rolAuditor, Utils.perCreate, getRegistro()).getUspId() > 0);
//				setAuditorRead(uprMB.searchPermiso(Utils.rolAuditor, Utils.perRead, getRegistro()).getUspId() > 0);
//				setAuditorUpdate(uprMB.searchPermiso(Utils.rolAuditor, Utils.perUpdate, getRegistro()).getUspId() > 0);
//				setAuditorDelete(uprMB.searchPermiso(Utils.rolAuditor, Utils.perDelete, getRegistro()).getUspId() > 0);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//		}
	}

	@SuppressWarnings("unchecked")
	public void fillUnidadList() {
		try {
			setUnidadList((List<Unidad>) audigoesLocal.findByNamedQuery(Unidad.class, "unidad.get.all.institucion",
					new Object[] { getObjAppsSession().getUsuario().getInstitucion().getInsId() }));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void eliminarUnidadAuditada(Unidad unidad) {
		if (getStatus().equals("NEW")) {
			getUnidadesSelectedList().remove(unidad);
			getUnidadList().add(unidad);
		}
	}

	public void addDelPermisos() {
		try {
			if (isAuditor()) {
				uprMB.addPermiso(Utils.rolAuditor, Utils.perGeneral, getRegistro());
				if (isAuditorCreate()) {
					uprMB.addPermiso(Utils.rolAuditor, Utils.perCreate, getRegistro());
				} else {
					uprMB.delPermiso(Utils.rolAuditor, Utils.perCreate, getRegistro());
				}

				if (isAuditorRead()) {
					uprMB.addPermiso(Utils.rolAuditor, Utils.perRead, getRegistro());
				} else {
					uprMB.delPermiso(Utils.rolAuditor, Utils.perRead, getRegistro());
				}

				if (isAuditorUpdate()) {
					uprMB.addPermiso(Utils.rolAuditor, Utils.perUpdate, getRegistro());
				} else {
					uprMB.delPermiso(Utils.rolAuditor, Utils.perUpdate, getRegistro());
				}

				if (isAuditorDelete()) {
					uprMB.addPermiso(Utils.rolAuditor, Utils.perDelete, getRegistro());
				} else {
					uprMB.delPermiso(Utils.rolAuditor, Utils.perDelete, getRegistro());
				}
			} else {
				uprMB.delPermiso(Utils.rolAuditor, Utils.perGeneral, getRegistro());
				uprMB.delPermiso(Utils.rolAuditor, Utils.perCreate, getRegistro());
				uprMB.delPermiso(Utils.rolAuditor, Utils.perRead, getRegistro());
				uprMB.delPermiso(Utils.rolAuditor, Utils.perUpdate, getRegistro());
				uprMB.delPermiso(Utils.rolAuditor, Utils.perDelete, getRegistro());
			}

			if (isGeneralLogin()) {
				uprMB.addPermiso(Utils.rolGeneral, Utils.perLogin, getRegistro());
			} else {
				uprMB.delPermiso(Utils.rolGeneral, Utils.perLogin, getRegistro());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void afterEdit() {
		fillPermisos();
		uprMB.setUsuario(getRegistro());
		uprMB.fillListado();
		uprMB.getRolMB().fillListado();
		uprMB.onEdit();
		super.afterEdit();
	}

	@Override
	public void afterNew() {
		setAuditor(false);
		setAuditorCreate(false);
		setAuditorRead(false);
		setAuditorUpdate(false);
		setAuditorDelete(false);

		setGeneralLogin(false);

//		setCoordinador(false);
//		setCoordinadorCreate(false);
//		setCoordinadorRead(false);
//		setCoordinadorUpdate(false);
//		setCoordinadorDelete(false);
//
//		setJefe(false);
//		setJefeCreate(false);
//		setJefeRead(false);
//		setJefeUpdate(false);
//		setJefeDelete(false);
//
//		setConsulta(false);
//		setConsultaExport(false);
//		setConsultaPrint(false);
//
//		setUnidad(false);
//		setUnidadCreate(false);
//		setUnidadRead(false);
//		setUnidadUpdate(false);
//		setUnidadDelete(false);
//
//		setExterno(false);
//		setExternoRead(false);

		uprMB.getRolMB().fillListado();
		super.afterNew();
	}

	/* GETS y SETS */

	@Override
	public Usuario getRegistro() {
		return (Usuario) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> getListado() {
		return (List<Usuario>) super.getListado();
	}

	@Override
	public void afterSaveNew() {
		getListado().add(getRegistro());
		// guardarUnidades();
		super.afterSaveNew();
	}

	public void guardarUnidades() {
		try {
			System.out.println("after");
			UsuarioUnidad usuUnidad;
			for (Unidad u : getUnidadesSelectedList()) {
				System.out.println("after " + u.getUniId());
				usuUnidad = new UsuarioUnidad();
				usuUnidad.setUsuario(getRegistro());
				usuUnidad.setUnidad(u);
				usuUnidad.setUsuCrea(getObjAppsSession().getUsuario().getUsuUsuario());
				usuUnidad.setFecCrea(getToday());
				usuUnidad.setRegActivo(1);

				audigoesLocal.insert(usuUnidad);
			}
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage("Error al registrar las unidades del usuario"));
		}
	}

	public void guardarUnidadesEdit() {
		try {
			System.out.println("after");
			UsuarioUnidad usuUnidad;

			for (UsuarioUnidad usu : getRegistro().getUsuarioUnidad()) {
				audigoesLocal.delete(usu);
			}

			for (Unidad u : getUnidadesSelectedList()) {
				System.out.println("after " + u.getUniId());

				usuUnidad = new UsuarioUnidad();
				usuUnidad.setUsuario(getRegistro());
				usuUnidad.setUnidad(u);
				usuUnidad.setUsuCrea(getObjAppsSession().getUsuario().getUsuUsuario());
				usuUnidad.setFecCrea(getToday());
				usuUnidad.setRegActivo(1);

				audigoesLocal.insert(usuUnidad);

			}
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage("Error al registrar las unidades del usuario"));
		}
	}

	public List<Usuario> getFilteredUsuarios() {
		return filteredUsuarios;
	}

	public void setFilteredUsuarios(List<Usuario> filteredUsuarios) {
		this.filteredUsuarios = filteredUsuarios;
	}

	public boolean isAuditor() {
		return auditor;
	}

	public void setAuditor(boolean auditor) {
		this.auditor = auditor;
	}

	public boolean isAuditorCreate() {
		return auditorCreate;
	}

	public void setAuditorCreate(boolean auditorCreate) {
		this.auditorCreate = auditorCreate;
	}

	public boolean isAuditorRead() {
		return auditorRead;
	}

	public void setAuditorRead(boolean auditorRead) {
		this.auditorRead = auditorRead;
	}

	public boolean isAuditorUpdate() {
		return auditorUpdate;
	}

	public void setAuditorUpdate(boolean auditorUpdate) {
		this.auditorUpdate = auditorUpdate;
	}

	public boolean isAuditorDelete() {
		return auditorDelete;
	}

	public void setAuditorDelete(boolean auditorDelete) {
		this.auditorDelete = auditorDelete;
	}

	public UsuarioPermisoMB getUprMB() {
		return uprMB;
	}

	public void setUprMB(UsuarioPermisoMB uprMB) {
		this.uprMB = uprMB;
	}

	public boolean isGeneralLogin() {
		return generalLogin;
	}

	public void setGeneralLogin(boolean generalLogin) {
		this.generalLogin = generalLogin;
	}

	public List<Unidad> getUnidadList() {
		return unidadList;
	}

	public void setUnidadList(List<Unidad> unidadList) {
		this.unidadList = unidadList;
	}

	public List<Unidad> getUnidadesSelectedList() {
		return unidadesSelectedList;
	}

	public void setUnidadesSelectedList(List<Unidad> unidadesSelectedList) {
		this.unidadesSelectedList = unidadesSelectedList;
	}
}
