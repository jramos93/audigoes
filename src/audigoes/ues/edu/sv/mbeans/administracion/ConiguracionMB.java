package audigoes.ues.edu.sv.mbeans.administracion;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.administracion.Configuracion;

@ManagedBean(name = "confMB")
@ViewScoped
public class ConiguracionMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConiguracionMB() {
		super(new Configuracion());
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
			setListado((List<Configuracion>) audigoesLocal.findByNamedQuery(Configuracion.class, "configuracion.all",
					new Object[] { getObjAppsSession().getUsuario().getInstitucion().getInsId() }));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void onRowEdit(RowEditEvent<Configuracion> event) {
		setRegistro(new Configuracion());
		onEdit();
		setRegistro(event.getObject());
		onSave();
    }
     
    public void onRowCancel(RowEditEvent<Configuracion> event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", event.getObject().getVariablesSistema().getVarNombre());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

	/* GETS y SETS */

	@Override
	public Configuracion getRegistro() {
		return (Configuracion) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Configuracion> getListado() {
		return (List<Configuracion>) super.getListado();
	}

	@Override
	public void afterSaveNew() {
		getListado().add(getRegistro());
		super.afterSaveNew();
	}

}
