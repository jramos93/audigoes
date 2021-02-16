package audigoes.ues.edu.sv.mbeans.seguimiento;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.informe.CedulaNota;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;
import audigoes.ues.edu.sv.entities.seguimiento.Comentario;
import audigoes.ues.edu.sv.entities.seguimiento.Recomendacion;
import audigoes.ues.edu.sv.entities.seguimiento.Seguimiento;

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
	private Seguimiento seguimiento;
	private CedulaNota cedula;
	private String textoCorreo = "";

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
			if (getCedula() != null && getSeguimiento() != null) {
				List<Comentario> com = (List<Comentario>) audigoesLocal.findByNamedQuery(Comentario.class,
						"comentario.by.hallazgo.seguimiento", new Object[] {getSeguimiento().getSegId(), getCedula().getCedId()});
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
	
	public void preparaCorreoComunicarAuditoria() {
		setTextoCorreo("<p><strong>AUDIGOES LE INFORMA:</strong></p>"
				+ "<p>Se notifica que se ha dado por iniciado el seguimiento "
				+ "correspondiente a la auditor&iacute;a <strong>"
				+ getAuditoria().getTipoAuditoria().getTpaAcronimo() + "-"
				+ getAuditoria().getAudAnio() + "-" + getAuditoria().getAudCorrelativo()
				+ "</strong> denominado <strong>" + getAuditoria().getAudNombre()
				+ " </strong> por lo que se le pide ingresar al sistema para realizar los comentarios correspondientes.</p>\r\n" + "<p>Atte.-</p>");
	}

	public void setComentarioComunicarAuditoria() {
		try {
			//Enviar comentario a revisión del coordinador
			onEdit();
			getRegistro().setComEstado(2);
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Problema enviar comentario a revisión"));
		}
		
	}
	public void preparaCorreoEnviarRevision() {
		setTextoCorreo("<p><strong>AUDIGOES LE INFORMA:</strong></p>"
				+ "<p>Se notifica que se ha a enviado para su revión el comentario "
				+ "correspondiente a la auditor&iacute;a <strong>"
				+ getAuditoria().getTipoAuditoria().getTpaAcronimo() + "-"
				+ getAuditoria().getAudAnio() + "-" + getAuditoria().getAudCorrelativo()
				+ "</strong> denominado <strong>" + getAuditoria().getAudNombre()
				+ " </strong> por lo que se le pide ingresar al sistema para realizar la revisión correspondientes.</p>\r\n" + "<p>Atte.-</p>");
	}
	
	public void setComentarioRevision() {
		try {
			//Enviar comentario a revisión del coordinador
			onEdit();
			getRegistro().setComEstado(4);
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Problema enviar comentario a revisión"));
		}
		
	}
	
	public void preparaCorreoComentarioObservado() {
		setTextoCorreo("<p><strong>AUDIGOES LE INFORMA:</strong></p>"
				+ "<p>Se notifica que el comentario "
				+ "correspondiente a la auditor&iacute;a <strong>"
				+ getAuditoria().getTipoAuditoria().getTpaAcronimo() + "-"
				+ getAuditoria().getAudAnio() + "-" + getAuditoria().getAudCorrelativo()
				+ "</strong> denominado <strong>" + getAuditoria().getAudNombre()
				+ " </strong> por lo que se le pide ingresar al sistema para realizar la revisión correspondientes.</p>\r\n" + "<p>Atte.-</p>");
	}
	
	public void setComentarioObservado() { 
		try {
			//Enviar comentario a unidad auditada
			onEdit();
			getRegistro().setComEstado(5);
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Problema enviar comentario a revisión"));
		}
	}
	
	public void preparaCorreoAComunicar() {
		setTextoCorreo("<p><strong>AUDIGOES LE INFORMA:</strong></p>"
				+ "<p>Se notifica que se realizado la respectiva revisi&oacute;n al comentario "
				+ "correspondiente a la auditor&iacute;a <strong>"
				+ getAuditoria().getTipoAuditoria().getTpaAcronimo() + "-"
				+ getAuditoria().getAudAnio() + "-" + getAuditoria().getAudCorrelativo()
				+ "</strong> denominada <strong>" + getAuditoria().getAudNombre()
				+ " </strong> por lo que se le pide ingresar al sistema para continuar con el proceso.</p>\r\n" + "<p>Atte.-</p>");
	}
	
	public void setComentarioAComunicar() {
		try {
			//Enviar comentario a auditor para que lo comunique a unidad auditada
			onEdit();
			getRegistro().setComEstado(6);
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Problema enviar comentario a revisión"));
		}
	}
	
	public void preparaCorreoComunicado() {
		setTextoCorreo("<p><strong>AUDIGOES LE INFORMA:</strong></p>"
				+ "<p>Se notifica que se ha dado por iniciado el seguimiento "
				+ "correspondiente a la auditor&iacute;a <strong>"
				+ getAuditoria().getTipoAuditoria().getTpaAcronimo() + "-"
				+ getAuditoria().getAudAnio() + "-" + getAuditoria().getAudCorrelativo()
				+ "</strong> denominado <strong>" + getAuditoria().getAudNombre()
				+ " </strong> por lo que se le pide ingresar al sistema para realizar los comentarios correspondientes.</p>\r\n" + "<p>Atte.-</p>");
	}
	
	public void setComentarioComunicado() { 
		try {
			//Enviar comentario a unidad auditada
			onEdit();
			getRegistro().setComEstado(7);
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Problema enviar comentario a revisión"));
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
	public boolean beforeSave() {
		if(isRolAuditor()) {
			if(getRegistro().getComComentarioAuditoria() == null || getRegistro().getComComentarioAuditoria().isEmpty()) {
				addWarn(new FacesMessage(SYSTEM_NAME, "Debe ingresar un comentario para poder guardar"));
				return false;
			} else {
				return super.beforeSaveNew();
			}
			
		}else if (isRolAuditado()) {
			if (getRegistro().getComComentario() == null || getRegistro().getComComentario().isEmpty()) {
				addWarn(new FacesMessage(SYSTEM_NAME, "Debe ingresar un comentario para poder guardar"));
				return false;
			} else {
				return super.beforeSaveNew();
			}
		} else {
			addWarn(new FacesMessage(SYSTEM_NAME, "No tiene permisos para realizar cambios en los comentarios"));
			return false;
		}
	}
	
	@Override
	public boolean beforeSaveNew() {
		getRegistro().setSeguimiento(getSeguimiento());
		getRegistro().setCedulaNota(getCedula());
		return super.beforeSaveNew();
	}

	@Override
	public void afterNew() {
		if(isRolAuditado()) {
			getRegistro().setComEstado(1); // Redacción auditado 
		} else if (isRolAuditor()) {
			getRegistro().setComEstado(3); // Redacción auditoría
		}
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
	
	public Seguimiento getSeguimiento() {
		return seguimiento;
	}

	public void setSeguimiento(Seguimiento seguimiento) {
		this.seguimiento = seguimiento;
	}

	public CedulaNota getCedula() {
		return cedula;
	}

	public void setCedula(CedulaNota cedula) {
		this.cedula = cedula;
	}

	public String getTextoCorreo() {
		return textoCorreo;
	}

	public void setTextoCorreo(String textoCorreo) {
		this.textoCorreo = textoCorreo;
	}

	@Override
	protected void configBean() {
		// TODO Auto-generated method stub
		super.configBean();
	}
	
}
