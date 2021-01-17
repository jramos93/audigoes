package audigoes.ues.edu.sv.mbean.planificacion;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;
import audigoes.ues.edu.sv.entities.planeacion.AuditoriaUnidad;

@ManagedBean(name = "audUniMB")
@ViewScoped
public class AuditoriaUnidadMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<AuditoriaUnidad> filteredAuditoriaUnidad;
	private Auditoria auditoria;

	public AuditoriaUnidadMB() {
		super(new AuditoriaUnidad());
	}

	@PostConstruct
	public void init() {
		try {
			//super.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void fillListado() {
		try {
			setListado((List<AuditoriaUnidad>) audigoesLocal.findByNamedQuery(AuditoriaUnidad.class,
					"auditoriaunidad.get.all.unidades", new Object[] { getAuditoria().getAudId() }));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
		if (filterText == null || filterText.equals("")) {
			return true;
		}

		AuditoriaUnidad audunidad = (AuditoriaUnidad) value;
		return audunidad.getUnidad().getUniNombre().toLowerCase().contains(filterText);
	}

	/* GETS y SETS */

	@Override
	public AuditoriaUnidad getRegistro() {
		return (AuditoriaUnidad) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AuditoriaUnidad> getListado() {
		return (List<AuditoriaUnidad>) super.getListado();
	}

	public Auditoria getAuditoria() {
		return auditoria;
	}

	public void setAuditoria(Auditoria auditoria) {
		this.auditoria = auditoria;
	}

	public List<AuditoriaUnidad> getFilteredAuditoriaUnidad() {
		return filteredAuditoriaUnidad;
	}

	public void setFilteredAuditoriaUnidad(List<AuditoriaUnidad> filteredAuditoriaUnidad) {
		this.filteredAuditoriaUnidad = filteredAuditoriaUnidad;
	}
}
