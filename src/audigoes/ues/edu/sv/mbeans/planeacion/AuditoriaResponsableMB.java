package audigoes.ues.edu.sv.mbeans.planeacion;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.mail.internet.InternetAddress;

import org.primefaces.component.tabview.TabView;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.administracion.Usuario;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;
import audigoes.ues.edu.sv.entities.planeacion.AuditoriaResponsable;
import audigoes.ues.edu.sv.entities.planeacion.PlanAnual;
import audigoes.ues.edu.sv.mbean.planificacion.BitacoraActividadMB;
import audigoes.ues.edu.sv.util.SendMailAttach;

@ManagedBean(name = "respMB")
@ViewScoped
public class AuditoriaResponsableMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Auditoria auditoria;
	private String[] selectedUsuarios;
	private List<Usuario> usuariosList;
	private List<Usuario> usuariosSelectedList = new ArrayList<Usuario>();;
	private Usuario selectedUsuario;
	private String textoCorreo = "";

	@ManagedProperty(value = "#{bitaMB}")
	private BitacoraActividadMB bitaMB = new BitacoraActividadMB();

	public AuditoriaResponsableMB() {
		super(new AuditoriaResponsable());
	}

	@PostConstruct
	public void init() {
		try {
			selectedUsuarios = new String[] {};
			usuariosList = new ArrayList<Usuario>();
			usuariosSelectedList = new ArrayList<Usuario>();
			//super.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void fillListado() {
		try {
			Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			setAuditoria(((Auditoria) sessionMap.get("auditoria")));
			if (auditoria != null) {
				setListado((List<AuditoriaResponsable>) audigoesLocal.findByNamedQuery(AuditoriaResponsable.class,
						"responsables.auditoria", new Object[] { auditoria.getAudId() }));
				setUsuariosList((List<Usuario>) audigoesLocal.findByNamedQuery(Usuario.class, "usuarios.auditoria",
						new Object[] { getObjAppsSession().getUsuario().getInstitucion().getInsId(),
								auditoria.getAudId() }));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addResponsable() {
		onNew();
		getRegistro().setUsuario(getSelectedUsuario());
		getRegistro().setAuditoria(getAuditoria());
		getRegistro().setFecCrea(getToday());
		getRegistro().setUsuCrea(getObjAppsSession().getUsuario().getUsuUsuario());
		getRegistro().setRegActivo(1);
		getRegistro().setAurRol(1);

		onSave();

		getListado().add(getRegistro());
		fillListado();
	}

	@Override
	protected void afterRowSelect() {
		super.afterRowSelect();
	}

	public void onBorrar(AuditoriaResponsable r) {
		setRegistro(r);
		onDelete();
		getListado().remove(getRegistro());
	}

	public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
		if (filterText == null || filterText.equals("")) {
			return true;
		}
		int filterInt = getInteger(filterText);

		PlanAnual plan = (PlanAnual) value;
		return plan.getPlaNombre().toLowerCase().contains(filterText)
				|| plan.getPlaDescripcion().toLowerCase().contains(filterText) || plan.getPlaId() == filterInt;
	}

	private int getInteger(String string) {
		try {
			return Integer.valueOf(string);
		} catch (NumberFormatException ex) {
			return 0;
		}
	}

	@Override
	public void afterCancel() {
		try {
			TabView tv = (TabView) FacesContext.getCurrentInstance().getViewRoot()
					.findComponent("frmNewEdit:tvPlanAnual");
			tv.setActiveIndex(0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		super.afterCancel();
	}

	@Override
	public boolean beforeDelete() {
		return super.beforeDelete();
	}

	@Override
	public void afterDelete() {
		getListado().remove(getRegistro());
		super.afterDelete();
	}

	public void prepararCorreo() {
		textoCorreo = "<p><strong>AUDIGOES LE INFORMA</strong></p>\r\n" + "\r\n"
				+ "<p>Ha sido asignado como auditor de la siguiente auditor&iacute;a:</p>\r\n" + "\r\n" + "<ul>\r\n"
				+ "	<li><strong>C&oacute;digo:</strong> " + auditoria.getAudCodigo() + "</li>\r\n"
				+ "	<li><strong>Tipo de Auditor&iacute;a:</strong> " + auditoria.getTipoAuditoria().getTpaNombre()
				+ "</li>\r\n" + "	<li><strong>Nombre:</strong> " + auditoria.getAudNombre() + "</li>\r\n"
				+ "</ul>\r\n" + "\r\n" + "<p><strong>Descripci&oacute;n:</strong></p>\r\n" + "\r\n" + "<p>"
				+ auditoria.getAudDescripcion() + "</p>\r\n" + "\r\n" + "<p><strong>Objetivos:</strong></p>\r\n"
				+ "\r\n" + "<p>" + auditoria.getAudObjetivos() + "</p>\r\n" + "\r\n"
				+ "<p><strong>Alcance:</strong></p>" + "<p>" + auditoria.getAudAlcances() + "</p>\r\n"
				+ "<p>Atte.-</p>";
		;
	}

	public void onEnviar() {
		try {
			if (enviarCorreo(textoCorreo, getObjAppsSession().getUsuario())) {
				if (bitaMB.registrarActividad(1, "Asignación de Actividad de Auditoria", auditoria,
						getObjAppsSession().getUsuario())) {
					auditoria.setAudFase(1);
					auditoria.setAudFechaAsignada(getToday());
					audigoesLocal.update(auditoria);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage("Error al enviar de comunicación"));
		}
	}

	public boolean enviarCorreo(String texto, Usuario coordinador) {
		String from;
		String cc;
		String to;
		String subject;
		String attach;
		String logo;
		String body;
		Address[] toList;
		Address[] toCc;

		try {
			from = "audigoes.ues@gmail.com";
			String tos = "";

			for (AuditoriaResponsable r : getListado()) {
				tos = tos + r.getUsuario().getUsuCorreo() + ",";
			}
			//System.out.println(" " + tos);

			toList = InternetAddress.parse(tos);

			subject = "Asignación de Auditoría";
			cc = coordinador.getUsuCorreo();
			body = texto;
			logo = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/images/logo-azul.png");

			SendMailAttach mail = new SendMailAttach(from, toList, cc, subject, body, null, logo);
			mail.sendManyTo();
			return true;
		} catch (Exception e) {
			addWarn(new FacesMessage("Error en el envio del correo"));
			return false;
		}
	}

	/*
	 * public byte[] streamToByteArray(InputStream stream) { try { return
	 * IOUtils.toByteArray(stream); } catch (IOException e) { e.printStackTrace();
	 * return null; } }
	 */

	@Override
	public AuditoriaResponsable getRegistro() {
		return (AuditoriaResponsable) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AuditoriaResponsable> getListado() {
		return (List<AuditoriaResponsable>) super.getListado();
	}

	@Override
	public void afterSaveNew() {
		// getListado().add(getRegistro());
		super.afterSaveNew();
	}

	public Auditoria getAuditoria() {
		return auditoria;
	}

	public void setAuditoria(Auditoria auditoria) {
		this.auditoria = auditoria;
	}

	public String[] getSelectedUsuarios() {
		return selectedUsuarios;
	}

	public void setSelectedUsuarios(String[] selectedUsuarios) {
		this.selectedUsuarios = selectedUsuarios;
	}

	public List<Usuario> getUsuariosList() {
		return usuariosList;
	}

	public void setUsuariosList(List<Usuario> usuariosList) {
		this.usuariosList = usuariosList;
	}

	public List<Usuario> getUsuariosSelectedList() {
		return usuariosSelectedList;
	}

	public void setUsuariosSelectedList(List<Usuario> usuariosSelectedList) {
		this.usuariosSelectedList = usuariosSelectedList;
	}

	public Usuario getSelectedUsuario() {
		return selectedUsuario;
	}

	public void setSelectedUsuario(Usuario selectedUsuario) {
		this.selectedUsuario = selectedUsuario;
	}

	public String getTextoCorreo() {
		return textoCorreo;
	}

	public void setTextoCorreo(String textoCorreo) {
		this.textoCorreo = textoCorreo;
	}

	public BitacoraActividadMB getBitaMB() {
		return bitaMB;
	}

	public void setBitaMB(BitacoraActividadMB bitaMB) {
		this.bitaMB = bitaMB;
	}
}
