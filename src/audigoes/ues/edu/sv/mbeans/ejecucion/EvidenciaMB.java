package audigoes.ues.edu.sv.mbeans.ejecucion;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.administracion.Archivo;
import audigoes.ues.edu.sv.entities.informe.CedulaNota;
import audigoes.ues.edu.sv.entities.seguimiento.Evidencia;

@ManagedBean(name = "evdMB")
@ViewScoped
public class EvidenciaMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Evidencia> filteredArchivos;
	
	private CedulaNota cedula;

	public EvidenciaMB() {
		super(new Evidencia());
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
	public void fillByCedula() {
		try {
			setListado((List<Evidencia>) audigoesLocal.findByNamedQuery(Evidencia.class, "evidencia.cedula",
					new Object[] { cedula.getCedId() }));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
		if (filterText == null || filterText.equals("")) {
			return true;
		}

		Evidencia arch = (Evidencia) value;
		return arch.getEvdNombre().toLowerCase().contains(filterText);
	}
	
	public void onBorrar(Archivo a) {
		setRegistro(a);
		onDelete();
	}
	
	@Override
	public void afterDelete() {
		getListado().remove(getRegistro());
		super.afterDelete();
	}
	

	/* GETS y SETS */

	@Override
	public Evidencia getRegistro() {
		return (Evidencia) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Evidencia> getListado() {
		return (List<Evidencia>) super.getListado();
	}

	@Override
	public void afterSaveNew() {
		getListado().add(getRegistro());
		super.afterSaveNew();
	}

	public List<Evidencia> getFilteredArchivos() {
		return filteredArchivos;
	}

	public void setFilteredArchivos(List<Evidencia> filteredArchivos) {
		this.filteredArchivos = filteredArchivos;
	}

	public CedulaNota getCedula() {
		return cedula;
	}

	public void setCedula(CedulaNota cedula) {
		this.cedula = cedula;
	}



}
