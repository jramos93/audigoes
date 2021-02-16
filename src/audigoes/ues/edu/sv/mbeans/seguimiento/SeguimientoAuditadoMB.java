package audigoes.ues.edu.sv.mbeans.seguimiento;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.informe.CedulaNota;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;
import audigoes.ues.edu.sv.entities.seguimiento.Recomendacion;
import audigoes.ues.edu.sv.entities.seguimiento.Seguimiento;

@ManagedBean(name = "segaudMB")
@ViewScoped
public class SeguimientoAuditadoMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Auditoria auditoria;
	private List<CedulaNota> cedulaNotaList;
	private List<CedulaNota> filteredCedula;
	private CedulaNota cedulaSelected;

	private List<Recomendacion> recomendacionList;
	private List<Recomendacion> filteredRecomendaciones;
	private Recomendacion recomendacionSelected;

	@ManagedProperty(value = "#{comMB}")
	private ComentarioMB comMB;

	public SeguimientoAuditadoMB() {
		super(new Seguimiento());
	}

	@PostConstruct
	public void init() {
		try {
			configBean();
			Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			setAuditoria((Auditoria) sessionMap.get("auditoria"));
			obtenerCedulaNotaList();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void obtenerCedulaNotaList() {
		try {
			setCedulaNotaList((List<CedulaNota>) audigoesLocal.findByNamedQuery(CedulaNota.class,
					"seguimiento.hallazgo.auditoria.auditado", new Object[] {getObjAppsSession().getUsuario().getInstitucion().getInsId()}));
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Problema al obtener listado de hallazgos en seguimiento"));
		}
	}

	@SuppressWarnings("unchecked")
	public void obtenerRecomendacionesHallazgo() {
		try {
			setRecomendacionList((List<Recomendacion>) audigoesLocal.findByNamedQuery(Recomendacion.class,
					"seguimiento.recomendaciones.por.seguimiento", new Object[] {getCedulaSelected().getCedId()}));
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Problemas al obtener listado de recomendaciones"));
		}
	}

	public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
		if (filterText == null || filterText.equals("")) {
			return true;
		}

		CedulaNota ced = (CedulaNota) value;
		return ced.getCedTitulo().toLowerCase().contains(filterText)
				|| ced.getCedCondicion().toLowerCase().contains(filterText)
				|| ced.getCedCriterio().toLowerCase().contains(filterText)
				|| ced.getCedCausa().toLowerCase().contains(filterText);
	}
	
	public boolean globalFilterFunctionRecomendaciones(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
		if (filterText == null || filterText.equals("")) {
			return true;
		}

		Recomendacion rec = (Recomendacion) value;
		return rec.getRecRecomendacion().toLowerCase().contains(filterText)
				|| rec.getRecTitulo().toLowerCase().contains(filterText);
	}

	public void onRowCedulaSelect() {
		getCedulaSelected().setSelected(true);
	}

	public void obtenerComentarios() {
		setStatus("SEGUIMIENTO_COMENTARIOS");
		obtenerRecomendacionesHallazgo();
		//this.comMB.setSeguimiento(getRegistro());
		this.comMB.setCedula(getCedulaSelected());
		this.comMB.obtenerComentario();
	}
	
	public void guardarComentario() {
		this.comMB.onSave();
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

	public Auditoria getAuditoria() {
		return auditoria;
	}

	public List<CedulaNota> getCedulaNotaList() {
		return cedulaNotaList;
	}

	public void setCedulaNotaList(List<CedulaNota> cedulaNotaList) {
		this.cedulaNotaList = cedulaNotaList;
	}

	public CedulaNota getCedulaSelected() {
		return cedulaSelected;
	}

	public void setCedulaSelected(CedulaNota cedulaSelected) {
		this.cedulaSelected = cedulaSelected;
	}

	public List<CedulaNota> getFilteredCedula() {
		return filteredCedula;
	}

	public void setFilteredCedula(List<CedulaNota> filteredCedula) {
		this.filteredCedula = filteredCedula;
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

	public List<Recomendacion> getRecomendacionList() {
		return recomendacionList;
	}

	public void setRecomendacionList(List<Recomendacion> recomendacionList) {
		this.recomendacionList = recomendacionList;
	}

	public List<Recomendacion> getFilteredRecomendaciones() {
		return filteredRecomendaciones;
	}

	public void setFilteredRecomendaciones(List<Recomendacion> filteredRecomendaciones) {
		this.filteredRecomendaciones = filteredRecomendaciones;
	}

	public Recomendacion getRecomendacionSelected() {
		return recomendacionSelected;
	}

	public void setRecomendacionSelected(Recomendacion recomendacionSelected) {
		this.recomendacionSelected = recomendacionSelected;
	}

	@Override
	protected void configBean() {
		// TODO Auto-generated method stub
		super.configBean();
	}
}
