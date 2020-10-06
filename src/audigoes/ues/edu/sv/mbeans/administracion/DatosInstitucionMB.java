package audigoes.ues.edu.sv.mbeans.administracion;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.PrimeFaces;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.administracion.Institucion;

@ManagedBean(name = "dtinsMB")
@ViewScoped
public class DatosInstitucionMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DatosInstitucionMB() {
		super(new Institucion());
	}

	@PostConstruct
	public void init() {
		try {
			setRegistro(getObjAppsSession().getUsuario().getInstitucion());
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/* GETS y SETS */

	@Override
	public Institucion getRegistro() {
		return (Institucion) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Institucion> getListado() {
		return (List<Institucion>) super.getListado();
	}
	
	@Override
	public void afterSaveEdit() {
		//PrimeFaces.current().ajax().update("frm");
		super.afterSaveEdit();
	}

}
