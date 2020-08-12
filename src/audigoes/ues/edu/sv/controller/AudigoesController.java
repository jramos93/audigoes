package audigoes.ues.edu.sv.controller;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import audigoes.ues.edu.sv.entities.SuperEntity;
import audigoes.ues.edu.sv.session.audigoesSBSLLocal;

public class AudigoesController {

	@EJB(mappedName = "ejb/BusinessSBSL")
	protected audigoesSBSLLocal audigoesLocal;

	private boolean error = false;
	private String status; // NEW, EDIT, SEARCH

	/* propiedades para objetos de entidad */
	private SuperEntity registro;
	private SuperEntity regSelected;

	/* propiedades para listados de objeto de entidad */
	private List<? extends SuperEntity> listado;
	private List<? extends SuperEntity> filteredListado;
	private List<? extends SuperEntity> selectedListado;

	/* propiedades de utilerìa bàsica */
	private List<SelectItem> regActivoList;
	private List<SelectItem> siNoList;

	public void addInfo(FacesMessage mensaje) {
		mensaje.setSeverity(FacesMessage.SEVERITY_INFO);
		if (mensaje.getSummary() == null) {
			mensaje.setSummary("INFORMACIÓN");
		}
		FacesContext.getCurrentInstance().addMessage(null, mensaje);
	}

	public void addWarn(FacesMessage mensaje) {
		mensaje.setSeverity(FacesMessage.SEVERITY_WARN);
		if (mensaje.getSummary() == null) {
			mensaje.setSummary("ADVERTENCIA");
		}
		FacesContext.getCurrentInstance().addMessage(null, mensaje);
	}

	public void addError(FacesMessage mensaje) {
		mensaje.setSeverity(FacesMessage.SEVERITY_ERROR);
		if (mensaje.getSummary() == null) {
			mensaje.setSummary("ERROR");
		}
		FacesContext.getCurrentInstance().addMessage(null, mensaje);
	}

	public void addFatal(FacesMessage mensaje) {
		mensaje.setSeverity(FacesMessage.SEVERITY_FATAL);
		if (mensaje.getSummary() == null) {
			mensaje.setSummary("FATAL");
		}
		FacesContext.getCurrentInstance().addMessage(null, mensaje);
	}

	public Date getToday() {
		return Calendar.getInstance(Locale.getDefault()).getTime();
	}

	/* Acciones al crear nuevo objeto */
	public boolean beforeNew() {
		return true;
	}

	public void onNew() {
		if (beforeNew()) {
			if (getRegistro() != null) {
				try {
					setRegistro((SuperEntity) getRegistro().getClass().newInstance());
					getRegistro().setRegActivo(BigDecimal.ONE);
					getRegistro().setFecCrea(getToday());
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			setStatus("NEW");
			afterNew();
		}
	}

	public void afterNew() {
	}

	/* Acciones al editar objeto */
	public boolean beforeEdit() {
		return true;
	}

	public void onEdit() {
		if (beforeEdit()) {
			setStatus("EDIT");
			afterEdit();
		}
	}

	protected void afterEdit() {
	}

	/* Acciones al cancelar un evento */
	public boolean beforeCancel() {
		return true;
	}

	public void onCancel() {
		try {
			if (beforeCancel()) {
				if (getRegistro() != null) {
					setRegistro(null);
					setRegistro((SuperEntity) getRegistro().getClass().newInstance());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void afterCancel() {
	}

	/* Funciones para guardar*/
	public boolean beforeSave() {
		return true;
	}

	public void onSave() {
		try {
			if (beforeSave()) {
				if (getStatus().equals("NEW")) {
					onSaveNew();
				}
				if (getStatus().equals("EDIT")) {
					onSaveEdit();
				}
				if (!isError()) {
					afterSave();
				} else if (getRegistro() == null) {
					try {
						setRegistro((SuperEntity) getRegistro().getClass().newInstance());
					} catch (InstantiationException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void afterSave() {
	}

	/* Funciones para guardar nuevo*/
	public boolean beforeSaveNew() {
		return true;
	}

	public void onSaveNew() {
		try {
			if (beforeSaveNew()) {
				audigoesLocal.insert(getRegistro());
				afterSaveNew();
			} else {
				// TODO mostrar mensaje de error
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void afterSaveNew() {
	}

	/* Funciones para guardar edicion*/
	public boolean beforeSaveEdit() {
		return true;
	}

	public void onSaveEdit() {
		try {
			if (beforeSaveEdit()) {
				audigoesLocal.update(getRegistro());
				afterSaveNew();
			} else {
				// TODO mostrar mensaje de error
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void afterSaveEdit() {
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public SuperEntity getRegistro() {
		return registro;
	}

	public void setRegistro(SuperEntity registro) {
		this.registro = registro;
	}

	public SuperEntity getRegSelected() {
		return regSelected;
	}

	public void setRegSelected(SuperEntity regSelected) {
		this.regSelected = regSelected;
	}

	public List<? extends SuperEntity> getListado() {
		return listado;
	}

	public void setListado(List<? extends SuperEntity> listado) {
		this.listado = listado;
	}

	public List<? extends SuperEntity> getFilteredListado() {
		return filteredListado;
	}

	public void setFilteredListado(List<? extends SuperEntity> filteredListado) {
		this.filteredListado = filteredListado;
	}

	public List<? extends SuperEntity> getSelectedListado() {
		return selectedListado;
	}

	public void setSelectedListado(List<? extends SuperEntity> selectedListado) {
		this.selectedListado = selectedListado;
	}

	public List<SelectItem> getRegActivoList() {
		return regActivoList;
	}

	public void setRegActivoList(List<SelectItem> regActivoList) {
		this.regActivoList = regActivoList;
	}

	public List<SelectItem> getSiNoList() {
		return siNoList;
	}

	public void setSiNoList(List<SelectItem> siNoList) {
		this.siNoList = siNoList;
	}

}
