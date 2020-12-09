package audigoes.ues.edu.sv.mbeans.seguimiento;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.administracion.Usuario;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;
import audigoes.ues.edu.sv.entities.seguimiento.Comentario;
import audigoes.ues.edu.sv.entities.seguimiento.Seguimiento;

@ManagedBean(name = "segMB")
@ViewScoped
public class SeguimientoMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Usuario coordinador;
	private Auditoria auditoria;
	
	@ManagedProperty(value = "#{recMB}")
	private RecomendacionMB recMB = new RecomendacionMB();
	
	@ManagedProperty(value = "#{comMB}")
	private ComentarioMB comMB = new ComentarioMB();

	public SeguimientoMB() {
		super(new Seguimiento());
	}

	@PostConstruct
	public void init() {
		try {
			Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			setAuditoria((Auditoria) sessionMap.get("auditoria"));

			obtenerRecomendaciones();
			obtenerCoordinador();
			obtenerSeguimiento();

			super.init();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void obtenerAuditoria() {
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		setAuditoria((Auditoria) sessionMap.get("auditoria"));
	}
	
	public void obtenerRecomendaciones() {
		if(getAuditoria() != null) {
			getRecMB().setAuditoria(getAuditoria());
			getRecMB().fillRecomendacionesAuditoria();
		}
	}

	@SuppressWarnings("unchecked")
	public void obtenerCoordinador() {
		try {
			if (getAuditoria() != null) {
				List<Usuario> usrs = (List<Usuario>) audigoesLocal.findByNamedQuery(Usuario.class,
						"usuario.get.coordinador", new Object[] { getAuditoria().getAudId() });
				if (!usrs.isEmpty()) {
					setCoordinador(usrs.get(0));
				} else {
					setCoordinador(new Usuario());
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void obtenerSeguimiento() {
		try {
			if (getAuditoria() != null) {
				List<Seguimiento> seg = (List<Seguimiento>) audigoesLocal.findByNamedQuery(Seguimiento.class,
						"seguimiento.get.by.auditoria", new Object[] { getAuditoria().getAudId() });
				if (!seg.isEmpty()) {
					setRegistro(seg.get(0));
					onEdit();
				} else {
					onNew();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void mostrarComentarios() {
		
	}

	public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
		if (filterText == null || filterText.equals("")) {
			return true;
		}
		int filterInt = getInteger(filterText);

		Auditoria auditoria = (Auditoria) value;
		return auditoria.getAudNombre().toLowerCase().contains(filterText)
				|| auditoria.getAudDescripcion().toLowerCase().contains(filterText)
				|| auditoria.getAudId() == filterInt;
	}

	private int getInteger(String string) {
		try {
			return Integer.valueOf(string);
		} catch (NumberFormatException ex) {
			return 0;
		}
	}

	public void iniciarSeguimiento() {
		try {
			if(getStatus().equals("NEW")) {
				getRegistro().setAuditoria(getAuditoria());
				getRegistro().setSegFecInicio(getToday());
				onSaveNew();
				onEdit();
			} else 	if(getStatus().equals("EDIT")) {
				getRegistro().setSegFecInicio(getToday());
				onSaveEdit();
				onEdit();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void finalizarSeguimiento() {
		try {
			getRegistro().setSegFecFin(getToday());
			onSaveEdit();
			onEdit();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/* GETS y SETS */

	@Override
	public Seguimiento getRegistro() {
		return (Seguimiento) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Seguimiento> getListado() {
		return (List<Seguimiento>) super.getListado();
	}

	public Usuario getCoordinador() {
		return coordinador;
	}

	public void setCoordinador(Usuario coordinador) {
		this.coordinador = coordinador;
	}

	public Auditoria getAuditoria() {
		return auditoria;
	}

	public void setAuditoria(Auditoria auditoria) {
		this.auditoria = auditoria;
	}

	public RecomendacionMB getRecMB() {
		return recMB;
	}

	public void setRecMB(RecomendacionMB recMB) {
		this.recMB = recMB;
	}

	public ComentarioMB getComMB() {
		return comMB;
	}

	public void setComMB(ComentarioMB comMB) {
		this.comMB = comMB;
	}

}
