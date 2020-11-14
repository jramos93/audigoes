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
import audigoes.ues.edu.sv.entities.administracion.Usuario;
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

	private boolean auditor=false;
	private boolean auditorCreate;
	private boolean auditorRead;
	private boolean auditorUpdate;
	private boolean auditorDelete;
	private boolean generalLogin;

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
			setListado((List<Usuario>) audigoesLocal.findByNamedQuery(Usuario.class, "usuarios.all", new Object[] {}));
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
		} else if (getStatus().equals("EDIT")) {
			onEditUser();
			onSaveEdit();
		}
	}

	public void onCreateUser() {
		try {
			getRegistro().setFecCrea(getToday());
			getRegistro().setUsuCrea(getObjAppsSession().getUsuario().getUsuUsuario());
			getRegistro().setUsuContrasenia(onGeneratePassword(getRegistro()));
			getRegistro().setRegActivo(1);
			audigoesLocal.insert(getRegistro());
			if (getRegistro() != null && getRegistro().getUsuId() > 0) {
				//uprMB.addPermiso(Utils.rolGeneral, Utils.perLogin, getRegistro());
				//addDelPermisos();
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
				//addDelPermisos();
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
			return Utils.getShaString(u.getUsuUsuario()+Utils.claveEstandar);
		} catch (Exception e) {
			e.printStackTrace();
			return "XXX";
		}
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
			
			if(isGeneralLogin()) {
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
		super.afterSaveNew();
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
}
