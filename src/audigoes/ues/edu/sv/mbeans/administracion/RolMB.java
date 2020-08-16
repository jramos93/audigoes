package audigoes.ues.edu.sv.mbeans.administracion;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.administracion.Rol;

@ManagedBean(name = "rolMB")
@ViewScoped
public class RolMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RolMB() {
		super(new Rol());
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
			setListado((List<Rol>) audigoesLocal.findByNamedQuery(Rol.class, "rol.all", new Object[] {}));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* GETS y SETS */

	@Override
	public Rol getRegistro() {
		return (Rol) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Rol> getListado() {
		return (List<Rol>) super.getListado();
	}
}
