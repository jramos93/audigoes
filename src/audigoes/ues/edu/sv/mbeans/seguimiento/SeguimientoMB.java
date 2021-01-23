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
import javax.mail.Address;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.administracion.Usuario;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;
import audigoes.ues.edu.sv.entities.seguimiento.Comentario;
import audigoes.ues.edu.sv.entities.seguimiento.Recomendacion;
import audigoes.ues.edu.sv.entities.seguimiento.Seguimiento;
import audigoes.ues.edu.sv.util.SendMailAttach;

@ManagedBean(name = "segMB")
@ViewScoped
public class SeguimientoMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Usuario> auditoresList;
	private Usuario coordinador;
	private Auditoria auditoria;
	private String textoCorreo = "";

	private List<Seguimiento> filteredSeguimientos;
	
	private Seguimiento seguimientoSelected;
	private List<Recomendacion> recomendacionesSeguimientoList;
	private Comentario comentarioRecomendacion;

	@ManagedProperty(value = "#{recMB}")
	private RecomendacionMB recMB = new RecomendacionMB();

	public SeguimientoMB() {
		super(new Seguimiento());
	}

	@PostConstruct
	public void init() {
		try {
			configBean();
			Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			setAuditoria((Auditoria) sessionMap.get("auditoria"));

			obtenerRecomendaciones();
			obtenerCoordinador();
			obtenerSeguimiento();
			

			//super.init();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void obtenerRecomendaciones() {
		if (getAuditoria() != null) {
			getRecMB().setAuditoria(getAuditoria());
			getRecMB().fillRecomendacionesAuditoria();
		}
	}

	@SuppressWarnings("unchecked")
	public void obtenerCoordinador() {
		try {
			if (getAuditoria() != null) {
				setAuditoresList((List<Usuario>) audigoesLocal.findByNamedQuery(Usuario.class,
						"usuario.get.coordinador", new Object[] { getAuditoria().getAudId() }));
				System.out.println(getAuditoresList().size());
				if (getAuditoresList().isEmpty()) {
					setCoordinador(getAuditoresList().get(0));
					System.out.println(getAuditoresList().get(0).getUsuNombre());
				} else {
					setCoordinador(new Usuario());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void obtenerSeguimiento() {
		try {
			if (getAuditoria() != null) {
				setListado((List<Seguimiento>) audigoesLocal.findByNamedQuery(Seguimiento.class,
						"seguimiento.get.by.auditoria", new Object[] { getAuditoria().getAudId() }));
				if (!getListado().isEmpty()) {
					setRegistro(getListado().get(getListado().size() - 1));
					onEdit();
					if (getRegistro().getSegFecFin() != null) {
						onNew();
					}
				} else {
					onNew();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void onRowSeguimientoSelected() {
		seguimientoSelected.setSelected(true);
	}

	@SuppressWarnings("unchecked")
	public void obtenerRecomendacionesSeguimiento() {
		/* Mostrar recomendaciones de seguimiento seleccionado */
		setStatus("RECOMENDACIONES_SEGUIMIENTO");
		try {
			setRecomendacionesSeguimientoList((List<Recomendacion>) audigoesLocal.findByNamedQuery(Recomendacion.class,
					"seguimiento.recomendaciones.seguimiento.selected", new Object[] { seguimientoSelected.getSegId() }));
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME,"Problemas al obtener recomendaciones del seguimiento seleccionado"));
		}
	}

	public void obtenerComentariosSeguimiento() {
		/* Mostrar comentarios de seguimiento seleccionado */
	}
	
	public void mostrarComentarios() {
		recMB.mostrarComentarios();
		recMB.setSeguimiento(getRegistro());
	}
	
	public void mostrarHistorialComentario() {
		
	}

	public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
		if (filterText == null || filterText.equals("")) {
			return true;
		}
		int filterInt = getInteger(filterText);

		Auditoria auditoria = (Auditoria) value;
		return auditoria.getAudNombre().toLowerCase().contains(filterText)
				|| auditoria.getAudDescripcion().toLowerCase().contains(filterText)
				|| auditoria.getAudId() == filterInt;
	}

	private int getInteger(String string) {
		try {
			return Integer.valueOf(string);
		} catch (NumberFormatException ex) {
			return 0;
		}
	}

	public void iniciarSeguimiento() {
		try {
			if (getStatus().equals("NEW")) {
				getRegistro().setAuditoria(getAuditoria());
				getRegistro().setSegFecInicio(getToday());
				onSaveNew();
				getListado().add(getRegistro());
				onEdit();
			} else if (getStatus().equals("EDIT")) {
				getRegistro().setSegFecInicio(getToday());
				onSaveEdit();
				onEdit();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void finalizarSeguimiento() {
		try {
			getRegistro().setSegFecFin(getToday());
			getListado().get(getListado().size() - 1).setSegFecFin(getToday());
			onSaveEdit();
			onNew();
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage(SYSTEM_NAME, "Problemas al finalizar seguimiento"));
		}
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

	public Usuario getCoordinador() {
		return coordinador;
	}

	public void setCoordinador(Usuario coordinador) {
		this.coordinador = coordinador;
	}

	public List<Usuario> getAuditoresList() {
		return auditoresList;
	}

	public void setAuditoresList(List<Usuario> auditoresList) {
		this.auditoresList = auditoresList;
	}

	public Auditoria getAuditoria() {
		return auditoria;
	}

	public void setAuditoria(Auditoria auditoria) {
		this.auditoria = auditoria;
	}

	public RecomendacionMB getRecMB() {
		return recMB;
	}

	public void setRecMB(RecomendacionMB recMB) {
		this.recMB = recMB;
	}

	public void prepararCorreo() {
		textoCorreo = "<p><strong>AUDIGOES LE INFORMA:</strong></p>"
				+ "<p>Se le notifica que la fase de seguimiento a iniciado"
				+ "correspondiente a la auditor&iacute;a <strong>"
				+ getRegistro().getAuditoria().getTipoAuditoria().getTpaAcronimo() + "-"
				+ getRegistro().getAuditoria().getAudAnio() + "-" + getRegistro().getAuditoria().getAudCorrelativo()
				+ "</strong> por lo que se le pide ingresar al sistema para revisarlo.</p>\r\n" + "<p>Atte.-</p>";
	}

	public void onEnviarRevision() {
		correoRevision(textoCorreo);
	}

	public void correoRevision(String texto) {
		String from;
		String cc;
		String to;
		String subject;
		String text;
		String attach;
		String logo;
		String body;
		Address[] toList;
		Address[] toCc;

		try {
			from = "audigoes.ues@gmail.com";
			cc = "wilmer.grijalva@gmail.com";
			to = "wilmer.grijalva@gmail.com";
			subject = "Correo de Prueba";

			body = texto;
			logo = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/images/logo-azul.png");

			SendMailAttach mail = new SendMailAttach(from, cc, to, subject, body, null, logo);
			mail.send();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public String getTextoCorreo() {
		return textoCorreo;
	}

	public void setTextoCorreo(String textoCorreo) {
		this.textoCorreo = textoCorreo;
	}

	public List<Seguimiento> getFilteredSeguimientos() {
		return filteredSeguimientos;
	}

	public void setFilteredSeguimientos(List<Seguimiento> filteredSeguimientos) {
		this.filteredSeguimientos = filteredSeguimientos;
	}

	public Seguimiento getSeguimientoSelected() {
		return seguimientoSelected;
	}

	public void setSeguimientoSelected(Seguimiento seguimientoSelected) {
		this.seguimientoSelected = seguimientoSelected;
	}

	public List<Recomendacion> getRecomendacionesSeguimientoList() {
		return recomendacionesSeguimientoList;
	}

	public Comentario getComentarioRecomendacion() {
		return comentarioRecomendacion;
	}

	public void setRecomendacionesSeguimientoList(List<Recomendacion> recomendacionesSeguimientoList) {
		this.recomendacionesSeguimientoList = recomendacionesSeguimientoList;
	}

	public void setComentarioRecomendacion(Comentario comentarioRecomendacion) {
		this.comentarioRecomendacion = comentarioRecomendacion;
	}
	
	@Override
	protected void configBean() {
		// TODO Auto-generated method stub
		super.configBean();
	}
}
