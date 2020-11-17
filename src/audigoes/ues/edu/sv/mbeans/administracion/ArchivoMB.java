package audigoes.ues.edu.sv.mbeans.administracion;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.administracion.Archivo;

@ManagedBean(name = "arcMB")
@ViewScoped
public class ArchivoMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ArchivoMB() {
		super(new Archivo());
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
	public void fillListado() {
		try {
			setListado((List<Archivo>) audigoesLocal.findByNamedQuery(Archivo.class, "configuracion.all",
					new Object[] { getObjAppsSession().getUsuario().getInstitucion().getInsId() }));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* GETS y SETS */

	@Override
	public Archivo getRegistro() {
		return (Archivo) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Archivo> getListado() {
		return (List<Archivo>) super.getListado();
	}

	@Override
	public void afterSaveNew() {
		//getListado().add(getRegistro());
		super.afterSaveNew();
	}

}
