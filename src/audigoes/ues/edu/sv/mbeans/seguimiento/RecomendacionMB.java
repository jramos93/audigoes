package audigoes.ues.edu.sv.mbeans.seguimiento;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;
import audigoes.ues.edu.sv.entities.seguimiento.Recomendacion;

@ManagedBean(name = "recMB")
@ViewScoped
public class RecomendacionMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Recomendacion> filteredRecomendaciones;

	private Auditoria auditoria;
	
	@ManagedProperty(value = "#{comMB}")
	private ComentarioMB comMB = new ComentarioMB();

	public RecomendacionMB() {
		super(new Recomendacion());
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
	public void fillRecomendacionesAuditoria() {
		try {
			setListado((List<Recomendacion>) audigoesLocal.findByNamedQuery(Recomendacion.class,
					"recomendacion.get.all.auditoria", new Object[] { getAuditoria().getAudId() }));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void mostrarComentarios() {
		comMB.setRecomendacion(getRegistro());
		comMB.obtenerComentario();
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

	@Override
	public void afterNew() {
		super.afterNew();
	}

	@Override
	public void afterSaveNew() {
		getListado().add(getRegistro());
		super.afterSaveNew();
	}

	/* GETS y SETS */

	@Override
	public Recomendacion getRegistro() {
		return (Recomendacion) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Recomendacion> getListado() {
		return (List<Recomendacion>) super.getListado();
	}

	public List<Recomendacion> getFilteredRecomendaciones() {
		return filteredRecomendaciones;
	}

	public void setFilteredRecomendaciones(List<Recomendacion> filteredRecomendaciones) {
		this.filteredRecomendaciones = filteredRecomendaciones;
	}

	public Auditoria getAuditoria() {
		return auditoria;
	}

	public void setAuditoria(Auditoria auditoria) {
		this.auditoria = auditoria;
	}

	public ComentarioMB getComMB() {
		return comMB;
	}

	public void setComMB(ComentarioMB comMB) {
		this.comMB = comMB;
	}

}
