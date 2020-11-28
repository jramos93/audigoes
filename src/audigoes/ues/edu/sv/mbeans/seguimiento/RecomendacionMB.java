package audigoes.ues.edu.sv.mbeans.seguimiento;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;
import audigoes.ues.edu.sv.entities.seguimiento.Recomendacion;
import audigoes.ues.edu.sv.entities.seguimiento.Seguimiento;

@ManagedBean(name = "recoMB")
@ViewScoped
public class RecomendacionMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Recomendacion> filteredRecomendaciones;

	public RecomendacionMB() {
		super(new Seguimiento());
	}

	@PostConstruct
	public void init() {
		try {
			super.init();
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

		Auditoria auditoria = (Auditoria) value;
		return auditoria.getAudNombre().toLowerCase().contains(filterText)
				|| auditoria.getAudDescripcion().toLowerCase().contains(filterText) || auditoria.getAudId() == filterInt;
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
	public Seguimiento getRegistro() {
		return (Seguimiento) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Seguimiento> getListado() {
		return (List<Seguimiento>) super.getListado();
	}

	public List<Recomendacion> getFilteredRecomendaciones() {
		return filteredRecomendaciones;
	}

	public void setFilteredRecomendaciones(List<Recomendacion> filteredRecomendaciones) {
		this.filteredRecomendaciones = filteredRecomendaciones;
	}

}
