package audigoes.ues.edu.sv.mbeans.consultas;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.administracion.Unidad;
import audigoes.ues.edu.sv.entities.informe.CedulaNota;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;

@ManagedBean(name = "uniMB")
@ViewScoped
public class ConsultaMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Unidad> filteredUnidades;
	
	private List<Auditoria> auditoriasList;
	private List<Auditoria> filteredAuditorias;
	private Auditoria auditoria;
	
	private List<CedulaNota> cedulaNotasList;
	private List<CedulaNota> filteredCedulaNotas;
	private CedulaNota cedulaNota;
	

	public ConsultaMB() {
		super(new Unidad());
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
			setListado((List<Unidad>) audigoesLocal.findByNamedQuery(Unidad.class, "unidad.all", new Object[] {}));
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

		Unidad unidad = (Unidad) value;
		return unidad.getUniNombre().toLowerCase().contains(filterText)
				||unidad.getUniIniciales().toLowerCase().contains(filterText)
				|| unidad.getUniId() == filterInt;
	}

	private int getInteger(String string) {
		try {
			return Integer.valueOf(string);
		} catch (NumberFormatException ex) {
			return 0;
		}
	}

	/* GETS y SETS */

	@Override
	public Unidad getRegistro() {
		return (Unidad) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Unidad> getListado() {
		return (List<Unidad>) super.getListado();
	}
	
	@Override
	public boolean beforeSaveNew() {
		getRegistro().setInstitucion(getObjAppsSession().getUsuario().getInstitucion());
		return super.beforeSaveNew();
	}

	@Override
	public void afterSaveNew() {
		getListado().add(getRegistro());
		super.afterSaveNew();
	}

	public List<Unidad> getFilteredUnidades() {
		return filteredUnidades;
	}

	public void setFilteredUnidades(List<Unidad> filteredUnidades) {
		this.filteredUnidades = filteredUnidades;
	}
}
