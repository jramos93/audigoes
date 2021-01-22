package audigoes.ues.edu.sv.mbeans.ejecucion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.ejecucion.ComentarioHallazgo;
import audigoes.ues.edu.sv.entities.informe.CedulaNota;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;
import audigoes.ues.edu.sv.entities.seguimiento.Recomendacion;

@ManagedBean(name = "comeMB")
@ViewScoped
public class ComentarioHallazgoMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Auditoria auditoria;
	private CedulaNota cedula;

	public ComentarioHallazgoMB() {
		super(new ComentarioHallazgo());
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
	public void fillComentariosByCedula() {
		try {
			System.out.println("fill "+cedula.getCedId());
			setListado((List<ComentarioHallazgo>) audigoesLocal.findByNamedQuery(ComentarioHallazgo.class,
					"comentarios.by.cedula", new Object[] { cedula.getCedId()}));
			System.out.println("fill "+getListado().size());
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage("Advertencia", "No se pudo obtener las auditorias"));
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<ComentarioHallazgo> fillComentarios(CedulaNota c) {
		try {
			System.out.println("fill "+c.getCedId());
			return (List<ComentarioHallazgo>) audigoesLocal.findByNamedQuery(ComentarioHallazgo.class,
					"comentarios.by.cedula", new Object[] { c.getCedId()});
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage("Advertencia", "No se pudo obtener las auditorias"));
			return new ArrayList<ComentarioHallazgo>();
		}
	}

	public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
		if (filterText == null || filterText.equals("")) {
			return true;
		}

		Recomendacion rec = (Recomendacion) value;
		return rec.getRecRecomendacion().toLowerCase().contains(filterText)
				|| rec.getRecTitulo().toLowerCase().contains(filterText);
	}

	private int getInteger(String string) {
		try {
			return Integer.valueOf(string);
		} catch (NumberFormatException ex) {
			return 0;
		}
	}

	@Override
	public void afterSaveNew() {
		getListado().add(getRegistro());
		super.afterSaveNew();
	}
	
	@Override
	public boolean beforeSaveNew() {
		return super.beforeSaveNew();
	}

	/* GETS y SETS */

	@Override
	public ComentarioHallazgo getRegistro() {
		return (ComentarioHallazgo) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComentarioHallazgo> getListado() {
		return (List<ComentarioHallazgo>) super.getListado();
	}

	public Auditoria getAuditoria() {
		return auditoria;
	}

	public void setAuditoria(Auditoria auditoria) {
		this.auditoria = auditoria;
	}

	public CedulaNota getCedula() {
		return cedula;
	}

	public void setCedula(CedulaNota cedula) {
		this.cedula = cedula;
	}

}
