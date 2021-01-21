package audigoes.ues.edu.sv.mbeans.seguimiento;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;
import audigoes.ues.edu.sv.entities.seguimiento.Comentario;
import audigoes.ues.edu.sv.entities.seguimiento.Recomendacion;

@ManagedBean(name = "comMB")
@ViewScoped
public class ComentarioMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Comentario> filteredComentarios;

	private Auditoria auditoria;
	private Recomendacion recomendacion;

	public ComentarioMB() {
		super(new Comentario());
	}

	@PostConstruct
	public void init() {
		try {
			configBean();
			super.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void fillRecomendacionesAuditoria() {
		try {
			setListado((List<Comentario>) audigoesLocal.findByNamedQuery(Comentario.class,
					"recomendacion.get.all.auditoria", new Object[] { getAuditoria().getAudId() }));
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

		Auditoria auditoria = 
				(Auditoria) value;
		return auditoria.getAudNombre().toLowerCase().contains(filterText)
				|| auditoria.getAudDescripcion().toLowerCase().contains(filterText)
				|| auditoria.getAudId() == filterInt;
	}

	@SuppressWarnings("unchecked")
	public void obtenerComentario() {
		try {
			if (getRecomendacion() != null) {
				List<Comentario> com = (List<Comentario>) audigoesLocal.findByNamedQuery(Comentario.class,
						"comentario.by.recomendacion", new Object[] {getRecomendacion().getRecId()});
				if(!com.isEmpty()) {
					setRegistro(com.get(0));
					onEdit();
				} else {
					onNew();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int getInteger(String string) {
		try {
			return Integer.valueOf(string);
		} catch (NumberFormatException ex) {
			return 0;
		}
	}
	
	@Override
	public boolean beforeSaveNew() {
		getRegistro().setRecomendacion(getRecomendacion());
		return super.beforeSaveNew();
	}

	@Override
	public void afterNew() {
		super.afterNew();
	}
	
	@Override
	public void afterSave() {
		onEdit();
		super.afterSave();
	}

	/* GETS y SETS */

	@Override
	public Comentario getRegistro() {
		return (Comentario) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comentario> getListado() {
		return (List<Comentario>) super.getListado();
	}

	public List<Comentario> getFilteredComentarios() {
		return filteredComentarios;
	}

	public void setFilteredComentarios(List<Comentario> filteredComentarios) {
		this.filteredComentarios = filteredComentarios;
	}

	public Auditoria getAuditoria() {
		return auditoria;
	}

	public void setAuditoria(Auditoria auditoria) {
		this.auditoria = auditoria;
	}

	public Recomendacion getRecomendacion() {
		return recomendacion;
	}

	public void setRecomendacion(Recomendacion recomendacion) {
		this.recomendacion = recomendacion;
	}
	
	@Override
	protected void configBean() {
		// TODO Auto-generated method stub
		super.configBean();
	}
	
}
