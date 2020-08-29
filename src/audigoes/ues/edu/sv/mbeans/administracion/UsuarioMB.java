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
import javax.faces.model.SelectItem;

import org.primefaces.event.FlowEvent;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.administracion.Rol;
import audigoes.ues.edu.sv.entities.administracion.Usuario;
import audigoes.ues.edu.sv.util.Utils;

@ManagedBean(name = "usuMB")
@ViewScoped
public class UsuarioMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{uprMB}")
	private UsuarioPermisoMB uprMB = new UsuarioPermisoMB();

	private List<Usuario> filteredUsuarios;
	private List<SelectItem> generoList;
	private boolean skip;

	private List<Rol> rolesList;

	/* Variables de los roles */
	private boolean auditor;
	private boolean auditorCreate;
	private boolean auditorRead;
	private boolean auditorUpdate;
	private boolean auditorDelete;

	private boolean coordinador;
	private boolean coordinadorCreate;
	private boolean coordinadorRead;
	private boolean coordinadorUpdate;
	private boolean coordinadorDelete;

	private boolean jefe;
	private boolean jefeCreate;
	private boolean jefeRead;
	private boolean jefeUpdate;
	private boolean jefeDelete;

	private boolean consulta;
	private boolean consultaPrint;
	private boolean consultaExport;

	private boolean externo;
	private boolean externoRead;

	private boolean unidad;
	private boolean unidadCreate;
	private boolean unidadRead;
	private boolean unidadUpdate;
	private boolean unidadDelete;

	public UsuarioMB() {
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

	public String onFlowProcess(FlowEvent event) {
		if (skip) {
			skip = false; // reset in case user goes back
			return "confirm";
		} else {
			return event.getNewStep();
		}
	}

	@Override
	protected void afterEdit() {
		fillPermisos();
		super.afterEdit();
	}

	public void onSaveUser() {
		if (getStatus().equals("NEW")) {
			System.out.println("nuevo");
			onCreateUser();
		} else if (getStatus().equals("EDIT")) {
			System.out.println("edit");
			onEditUser();
		}
	}

	public void onCreateUser() {
		try {
			getRegistro().setFecCrea(getToday());
			getRegistro().setUsuCrea(getObjAppsSession().getUsuario().getUsuUsuario());
			getRegistro().setRegActivo(1);
			audigoesLocal.insert(getRegistro());
			if (getRegistro() != null && getRegistro().getUsuId() > 0) {
				uprMB.addPermiso(Utils.rolGeneral, Utils.perLogin, getRegistro());
				addDelPermisos();
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
				addDelPermisos();
				addInfo(new FacesMessage("Confirmación", "Usuario guardado correctamente"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage("Error", "Consulte con el administrador"));
		}
	}

	public void fillPermisos() {
		if (getRegistro() != null) {
			try {
				setAuditor(uprMB.searchPermiso(Utils.rolAuditor, Utils.perGeneral, getRegistro()).getUspId() > 0);
				setAuditorCreate(uprMB.searchPermiso(Utils.rolAuditor, Utils.perCreate, getRegistro()).getUspId() > 0);
				setAuditorRead(uprMB.searchPermiso(Utils.rolAuditor, Utils.perRead, getRegistro()).getUspId() > 0);
				setAuditorUpdate(uprMB.searchPermiso(Utils.rolAuditor, Utils.perUpdate, getRegistro()).getUspId() > 0);
				setAuditorDelete(uprMB.searchPermiso(Utils.rolAuditor, Utils.perDelete, getRegistro()).getUspId() > 0);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public void afterNew() {
		setAuditor(false);
		setAuditorCreate(false);
		setAuditorRead(false);
		setAuditorUpdate(false);
		setAuditorDelete(false);

		setCoordinador(false);
		setCoordinadorCreate(false);
		setCoordinadorRead(false);
		setCoordinadorUpdate(false);
		setCoordinadorDelete(false);

		setJefe(false);
		setJefeCreate(false);
		setJefeRead(false);
		setJefeUpdate(false);
		setJefeDelete(false);

		setConsulta(false);
		setConsultaExport(false);
		setConsultaPrint(false);

		setUnidad(false);
		setUnidadCreate(false);
		setUnidadRead(false);
		setUnidadUpdate(false);
		setUnidadDelete(false);

		setExterno(false);
		setExternoRead(false);
		super.afterNew();
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
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	public List<SelectItem> getGeneroList() {
		if (generoList == null) {
			generoList = new ArrayList<SelectItem>();
			generoList.add(new SelectItem(0, "F"));
			generoList.add(new SelectItem(1, "M"));
		}
		return generoList;
	}

	public void setGeneroList(List<SelectItem> generoList) {
		this.generoList = generoList;
	}

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	public List<Rol> getRolesList() {
		return rolesList;
	}

	public void setRolesList(List<Rol> rolesList) {
		this.rolesList = rolesList;
	}

	/* Gets y Sets de los roles */

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

	public boolean isCoordinador() {
		return coordinador;
	}

	public void setCoordinador(boolean coordinador) {
		this.coordinador = coordinador;
	}

	public boolean isCoordinadorCreate() {
		return coordinadorCreate;
	}

	public void setCoordinadorCreate(boolean coordinadorCreate) {
		this.coordinadorCreate = coordinadorCreate;
	}

	public boolean isCoordinadorRead() {
		return coordinadorRead;
	}

	public void setCoordinadorRead(boolean coordinadorRead) {
		this.coordinadorRead = coordinadorRead;
	}

	public boolean isCoordinadorUpdate() {
		return coordinadorUpdate;
	}

	public void setCoordinadorUpdate(boolean coordinadorUpdate) {
		this.coordinadorUpdate = coordinadorUpdate;
	}

	public boolean isCoordinadorDelete() {
		return coordinadorDelete;
	}

	public void setCoordinadorDelete(boolean coordinadorDelete) {
		this.coordinadorDelete = coordinadorDelete;
	}

	public boolean isJefe() {
		return jefe;
	}

	public void setJefe(boolean jefe) {
		this.jefe = jefe;
	}

	public boolean isJefeCreate() {
		return jefeCreate;
	}

	public void setJefeCreate(boolean jefeCreate) {
		this.jefeCreate = jefeCreate;
	}

	public boolean isJefeRead() {
		return jefeRead;
	}

	public void setJefeRead(boolean jefeRead) {
		this.jefeRead = jefeRead;
	}

	public boolean isJefeUpdate() {
		return jefeUpdate;
	}

	public void setJefeUpdate(boolean jefeUpdate) {
		this.jefeUpdate = jefeUpdate;
	}

	public boolean isJefeDelete() {
		return jefeDelete;
	}

	public void setJefeDelete(boolean jefeDelete) {
		this.jefeDelete = jefeDelete;
	}

	public boolean isConsulta() {
		return consulta;
	}

	public void setConsulta(boolean consulta) {
		this.consulta = consulta;
	}

	public boolean isConsultaPrint() {
		return consultaPrint;
	}

	public void setConsultaPrint(boolean consultaPrint) {
		this.consultaPrint = consultaPrint;
	}

	public boolean isConsultaExport() {
		return consultaExport;
	}

	public void setConsultaExport(boolean consultaExport) {
		this.consultaExport = consultaExport;
	}

	public boolean isExterno() {
		return externo;
	}

	public void setExterno(boolean externo) {
		this.externo = externo;
	}

	public boolean isExternoRead() {
		return externoRead;
	}

	public void setExternoRead(boolean externoRead) {
		this.externoRead = externoRead;
	}

	public boolean isUnidad() {
		return unidad;
	}

	public void setUnidad(boolean unidad) {
		this.unidad = unidad;
	}

	public boolean isUnidadCreate() {
		return unidadCreate;
	}

	public void setUnidadCreate(boolean unidadCreate) {
		this.unidadCreate = unidadCreate;
	}

	public boolean isUnidadRead() {
		return unidadRead;
	}

	public void setUnidadRead(boolean unidadRead) {
		this.unidadRead = unidadRead;
	}

	public boolean isUnidadUpdate() {
		return unidadUpdate;
	}

	public void setUnidadUpdate(boolean unidadUpdate) {
		this.unidadUpdate = unidadUpdate;
	}

	public boolean isUnidadDelete() {
		return unidadDelete;
	}

	public void setUnidadDelete(boolean unidadDelete) {
		this.unidadDelete = unidadDelete;
	}

	public UsuarioPermisoMB getUprMB() {
		return uprMB;
	}

	public void setUprMB(UsuarioPermisoMB uprMB) {
		this.uprMB = uprMB;
	}

}
