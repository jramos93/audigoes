package audigoes.ues.edu.sv.mbean.planificacion;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.administracion.BitacoraActividades;
import audigoes.ues.edu.sv.entities.administracion.Usuario;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;

@ManagedBean(name = "bitaMB")
@ViewScoped
public class BitacoraActividadMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Auditoria auditoria;

	public BitacoraActividadMB() {
		super(new BitacoraActividades());
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
			setListado((List<BitacoraActividades>) audigoesLocal.findByNamedQuery(BitacoraActividades.class,
					"bitacora.by.auditoria", new Object[] { auditoria.getAudId() }));
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage("Error al obtener el listado"));
		}
	}

	public void iniciarActividad(int correlativo, String accion, Auditoria auditoria, Usuario usuarioInicio) {
		try {
			onNew();

			getRegistro().setBitaCorrelativo(correlativo);
			getRegistro().setAuditoria(auditoria);
			getRegistro().setBitaAccion(accion);
			getRegistro().setUsuarioInicio(usuarioInicio);
			getRegistro().setBitaFechaInicio(getToday());

			audigoesLocal.insert(getRegistro());
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage("Error al registrar actividad en la bitacora"));
		}
	}

	@SuppressWarnings("unchecked")
	public void finalizarActividad(int correlativo, Auditoria auditoria, Usuario usuarioFin) {
		try {
			onEdit();

			setListado((List<BitacoraActividades>) audigoesLocal.findByNamedQuery(BitacoraActividades.class,
					"actividad.by.correlativo", new Object[] { correlativo, auditoria.getAudId() }));

			if (getListado() != null && !getListado().isEmpty()) {
				getRegistro().setUsuarioFin(usuarioFin);
				getRegistro().setBitaFechaFin(getToday());
				getRegistro().setUsuModi(getObjAppsSession().getUsuario().getUsuUsuario());
				getRegistro().setFecModi(getToday());

				audigoesLocal.update(getRegistro());

			} else {
				addWarn(new FacesMessage("Error no se encontrada actividad relacionada en la bitacora"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage("Error al registrar actividad en la bitacora"));
		}
	}

	public boolean registrarActividad(int correlativo, String accion, Auditoria auditoria, Usuario usuario) {
		try {
			onNew();

			getRegistro().setBitaCorrelativo(correlativo);
			getRegistro().setAuditoria(auditoria);
			getRegistro().setBitaAccion(accion);
			getRegistro().setUsuarioInicio(usuario);
			getRegistro().setBitaFechaInicio(getToday());
			getRegistro().setUsuarioFin(usuario);
			getRegistro().setBitaFechaFin(getToday());
			getRegistro().setUsuModi(getObjAppsSession().getUsuario().getUsuUsuario());
			getRegistro().setFecModi(getToday());
			getRegistro().setUsuCrea(getObjAppsSession().getUsuario().getUsuUsuario());
			getRegistro().setFecCrea(getToday());
			getRegistro().setRegActivo(1);

			audigoesLocal.insert(getRegistro());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage("Error al registrar actividad en la bitacora"));
			return false;
		}
	}

	/* GETS y SETS */

	@Override
	public BitacoraActividades getRegistro() {
		return (BitacoraActividades) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BitacoraActividades> getListado() {
		return (List<BitacoraActividades>) super.getListado();
	}

	@Override
	public void afterSaveNew() {
		getListado().add(getRegistro());
		super.afterSaveNew();
	}

	public Auditoria getAuditoria() {
		return auditoria;
	}

	public void setAuditoria(Auditoria auditoria) {
		this.auditoria = auditoria;
	}

}
