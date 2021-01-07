package audigoes.ues.edu.sv.mbeans.seguimiento;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.informe.CedulaNota;
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
	private CedulaNota cedula;

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
			System.out.println(getListado().size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void fillRecomendaciones() {
		try {

			setListado((List<Recomendacion>) audigoesLocal.findByNamedQuery(Recomendacion.class,
					"recomedaciones.by.nota", new Object[] { cedula.getCedId(), auditoria.getAudId() }));
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage("Advertencia", "No se pudo obtener las auditorias"));
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
	public void afterNew() {
		super.afterNew();
	}

	@Override
	public void afterSaveNew() {
		getListado().add(getRegistro());
		super.afterSaveNew();
	}
	
	@Override
	public boolean beforeSaveNew() {
		getRegistro().setAuditoria(auditoria);
		getRegistro().setCedulaNota(cedula);
		getRegistro().setRecEstado(1);
		return super.beforeSaveNew();
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

	public CedulaNota getCedula() {
		return cedula;
	}

	public void setCedula(CedulaNota cedula) {
		this.cedula = cedula;
	}

}
