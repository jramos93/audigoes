package audigoes.ues.edu.sv.mbeans.ejecucion;

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

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.administracion.BitacoraActividades;
import audigoes.ues.edu.sv.entities.administracion.Usuario;
import audigoes.ues.edu.sv.entities.ejecucion.ComentarioHallazgo;
import audigoes.ues.edu.sv.entities.ejecucion.ProgramaEjecucion;
import audigoes.ues.edu.sv.entities.informe.CedulaNota;
import audigoes.ues.edu.sv.entities.planeacion.Auditoria;
import audigoes.ues.edu.sv.entities.planeacion.AuditoriaResponsable;
import audigoes.ues.edu.sv.mbean.planificacion.BitacoraActividadMB;
import audigoes.ues.edu.sv.mbeans.administracion.ArchivoMB;
import audigoes.ues.edu.sv.mbeans.seguimiento.RecomendacionMB;
import audigoes.ues.edu.sv.util.SendMailAttach;

@ManagedBean(name = "ceduUsuMB")
@ViewScoped
public class CedulaUsuMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<CedulaNota> filteredCedula;
	private Auditoria auditoria;
	private String textoCorreo = "";

	private int dias;

	private List<ProgramaEjecucion> programasList;

	@ManagedProperty(value = "#{recMB}")
	private RecomendacionMB recMB = new RecomendacionMB();

	@ManagedProperty(value = "#{evdMB}")
	private EvidenciaMB evdMB = new EvidenciaMB();

	@ManagedProperty(value = "#{arcMB}")
	private ArchivoMB arcMB = new ArchivoMB();

	@ManagedProperty(value = "#{bitaMB}")
	private BitacoraActividadMB bitaMB = new BitacoraActividadMB();

	@ManagedProperty(value = "#{comeMB}")
	private ComentarioHallazgoMB comeMB = new ComentarioHallazgoMB();

	public CedulaUsuMB() {
		super(new CedulaNota());
	}

	@PostConstruct
	public void init() {
		try {
			super.init();
			fillNotas();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onRowSelect() {
		super.onRowSelect();
		if (getRegistro().getComentarioHallazgos() != null && !getRegistro().getComentarioHallazgos().isEmpty()) {
			if (getRegistro().getComentarioHallazgos().size() == 1) {
				comeMB.setRegistro(getRegistro().getComentarioHallazgos().get(0));
				evdMB.setComentarioHallazgo(getRegistro().getComentarioHallazgos().get(0));
				evdMB.fillByComentario();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public Usuario buscarCoordinador(Auditoria auditoria) {
		Usuario usuario = null;
		try {
			List<AuditoriaResponsable> responsables = (List<AuditoriaResponsable>) audigoesLocal.findByNamedQuery(
					AuditoriaResponsable.class, "find.coordinador.by.auditoria", new Object[] { auditoria.getAudId() });
			if (!responsables.isEmpty()) {
				usuario = responsables.get(0).getUsuario();
				return usuario;
			} else {
				addWarn(new FacesMessage("Error, la auditoria no cuenta con un coordinador asignado"));
				return usuario;
			}
		} catch (Exception e) {
			addWarn(new FacesMessage("Error al identificar  el coordinador de la auditoria"));
			return usuario;
		}
	}

	public void onCancelHallazgo() {
		setStatus("VIEW");
	}

	@SuppressWarnings("unchecked")
	public void fillNotas() {
		try {
//			setListado((List<ProgramaPlanificacion>) audigoesLocal.findByNamedQuery(ProgramaPlanificacion.class,
//					"programa.by.auditoria",
//					new Object[] { auditoria.getAudId() }));
			Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			setAuditoria(((Auditoria) sessionMap.get("auditoria")));

			setListado((List<CedulaNota>) audigoesLocal.findByNamedQuery(CedulaNota.class, "notas.by.usuario",
					new Object[] { getObjAppsSession().getUsuario().getUsuId() }));
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage("Advertencia", "No se pudo obtener las auditorias"));
		}
	}

	public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
		if (filterText == null || filterText.equals("")) {
			return true;
		}

		CedulaNota ced = (CedulaNota) value;
		return ced.getCedTitulo().toLowerCase().contains(filterText)
				|| ced.getCedCondicion().toLowerCase().contains(filterText)
				|| ced.getCedCriterio().toLowerCase().contains(filterText)
				|| ced.getCedCausa().toLowerCase().contains(filterText);
	}

	@Override
	public void onShowSelected() {
		// TODO Auto-generated method stub
		recMB.setAuditoria(getRegistro().getAuditoria());
		recMB.setCedula(getRegistro());
		recMB.fillRecomendaciones();

		evdMB.setCedula(getRegistro());
		evdMB.fillByCedula();

		arcMB.setCedula(getRegistro());
		arcMB.fillByCedula(getRegistro());
		dias = (int) ((getRegistro().getCedFechaPlazo().getTime() - getToday().getTime()) / 86400000);
		dias = 1;
		System.out.println("dias "+dias);

		super.onShowSelected();
	}

	@Override
	public boolean beforeShow() {
		if (getRegistro().getComentarioHallazgos() != null && !getRegistro().getComentarioHallazgos().isEmpty()) {
			if (getRegistro().getComentarioHallazgos().size() == 1) {
				comeMB.onEdit();
				comeMB.setRegistro(getRegistro().getComentarioHallazgos().get(0));
				evdMB.setComentarioHallazgo(getRegistro().getComentarioHallazgos().get(0));
				evdMB.fillByComentario();
			} else {
				addWarn(new FacesMessage("Advertencia", "Problema al obtener los responsables"));
				return false;
			}
		} else {
			addWarn(new FacesMessage("Advertencia", "Problema al obtener los responsables"));
			return false;
		}
		return super.beforeShow();
	}

	@Override
	public boolean beforeEdit() {
		if (getRegistro().getComentarioHallazgos() != null && !getRegistro().getComentarioHallazgos().isEmpty()) {
			if (getRegistro().getComentarioHallazgos().size() == 1) {
				comeMB.onEdit();
				comeMB.setRegistro(getRegistro().getComentarioHallazgos().get(0));
				evdMB.setComentarioHallazgo(getRegistro().getComentarioHallazgos().get(0));
				evdMB.fillByComentario();
			} else {
				addWarn(new FacesMessage("Advertencia", "Problema al obtener los responsables"));
				return false;
			}
		} else {
			addWarn(new FacesMessage("Advertencia", "Problema al obtener los responsables"));
			return false;
		}
		return super.beforeEdit();
	}

	@Override
	public void onEditSelected() {
		// TODO Auto-generated method stub
		super.onEditSelected();
		recMB.setAuditoria(getRegistro().getAuditoria());
		recMB.setCedula(getRegistro());
		recMB.fillRecomendaciones();

		evdMB.setCedula(getRegistro());
		// evdMB.setComentarioHallazgo(comeMB);

		arcMB.setCedula(getRegistro());
		arcMB.fillByCedula(getRegistro());

		dias = (int) ((getRegistro().getCedFechaPlazo().getTime() - getToday().getTime()) / 86400000);
		dias = 1;
		System.out.println("dias "+dias);

		System.out.println("Hay " + dias + " dias de diferencia");
	}

	@Override
	public void afterCancel() {
		super.afterCancel();
	}

	@Override
	public boolean beforeSaveNew() {
		getRegistro().setAuditoria(auditoria);
		getRegistro().setCedFechaElaboro(getToday());
		getRegistro().setUsuario1(getObjAppsSession().getUsuario());
		getRegistro().setCedEstado(1);
		getRegistro().setCedValorizacion(1);
		return super.beforeSaveNew();
	}

	@SuppressWarnings("unchecked")
	public List<CedulaNota> fillAllNotas(Auditoria auditoria) {
		try {
			return (List<CedulaNota>) audigoesLocal.findByNamedQuery(CedulaNota.class, "notas.by.auditoria",
					new Object[] { auditoria.getAudId() });
		} catch (Exception e) {
			e.printStackTrace();
			addWarn(new FacesMessage("Advertencia", "No se pudo obtener las auditorias"));
			return null;
		}
	}

	public void prepararCorreo() {
		textoCorreo = "<p><strong>AUDIGOES LE INFORMA:</strong></p>"
				+ "<p>Se ha dado respuesta y enviado para su revisi&oacute;n los comentarios al hallazgo "
				+ "correspondiente a la auditor&iacute;a <strong>"
				+ getRegistro().getAuditoria().getTipoAuditoria().getTpaAcronimo() + "-"
				+ getRegistro().getAuditoria().getAudAnio() + "-" + getRegistro().getAuditoria().getAudCorrelativo()
				+ "</strong> denominado <strong>" + getRegistro().getCedTitulo()
				+ " </strong> por lo que se le pide ingresar al sistema para realizarlo.</p>\r\n" + "<p>Atte.-</p>";
	}

	public void onEnviarComentarios() {
		Usuario usr = getRegistro().getUsuario1();
		if (usr != null) {
			try {
				if (correoComentarios(textoCorreo, getObjAppsSession().getUsuario(), usr)) {
					System.out.println("correo");
					comeMB.getRegistro().setComeEnviado(1);
					comeMB.getRegistro().setComeFechaEnvio(getToday());
					audigoesLocal.update(comeMB.getRegistro());

					List<ComentarioHallazgo> comentarios = comeMB.fillComentarios(getRegistro());
					if (comentarios != null) {
						int contador = 0;
						for (ComentarioHallazgo c : comentarios) {
							if (c.getComeEnviado() == 0) {
								contador = contador + 1;
							}
						}

						if (contador == 0) {
							getRegistro().setCedEstado(5);
							getRegistro().setCedFechaRespuesta(getToday());
							onSave();
						}
					}

					List<CedulaNota> notas = fillAllNotas(getRegistro().getAuditoria());
					if (notas != null) {
						int contador = 0;
						for (CedulaNota c : notas) {
							if (c.getCedEstado() < 5) {
								contador = contador + 1;
							}
						}

						if (contador == 0) {
							BitacoraActividades a = bitaMB.buscarActividad(13, getRegistro().getAuditoria());
							if (a == null) {
								bitaMB.iniciarActividad(13, "Comunicación de hallazgos preliminares",
										getRegistro().getAuditoria(), getObjAppsSession().getUsuario());
								bitaMB.finalizarActividad(13, auditoria, getObjAppsSession().getUsuario());
							} else {
								bitaMB.finalizarActividad(13, auditoria, getObjAppsSession().getUsuario());
							}

						}

					}

					BitacoraActividades a2 = bitaMB.buscarActividad(14, getRegistro().getAuditoria());
					if (a2 == null) {
						System.out.println(" 2 ");
						bitaMB.iniciarActividad(14, "Análisis de comentarios de unidad", getRegistro().getAuditoria(),
								getObjAppsSession().getUsuario());
					}
				} else {
					addWarn(new FacesMessage("Error al enviar al auditor"));
				}
			} catch (Exception e) {
				e.printStackTrace();
				addWarn(new FacesMessage("Error al enviar al auditor"));
			}
		}
	}

	public boolean correoComentarios(String texto, Usuario auditado, Usuario auditor) {
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
			to = auditado.getUsuCorreo();
			subject = "Envio de comentarios a Hallazgo Preliminar";
			cc = auditor.getUsuCorreo();
			body = texto;
			logo = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/images/logo-azul.png");

			SendMailAttach mail = new SendMailAttach(from, cc, to, subject, body, null, logo);
			mail.send();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/* GETS y SETS */

	@Override
	public CedulaNota getRegistro() {
		return (CedulaNota) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CedulaNota> getListado() {
		return (List<CedulaNota>) super.getListado();
	}

	@Override
	public void afterSave() {
		super.afterSave();
		try {
			audigoesLocal.update(comeMB.getRegistro());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		comeMB.onEdit();
		onEdit();
	}

	@Override
	public void afterSaveNew() {
		// getListado().add(getRegistro());
		super.afterSaveNew();
	}

	public List<CedulaNota> getFilteredCedula() {
		return filteredCedula;
	}

	public void setFilteredCedula(List<CedulaNota> filteredCedula) {
		this.filteredCedula = filteredCedula;
	}

	public Auditoria getAuditoria() {
		return auditoria;
	}

	public void setAuditoria(Auditoria auditoria) {
		this.auditoria = auditoria;
	}

	public String getTextoCorreo() {
		return textoCorreo;
	}

	public void setTextoCorreo(String textoCorreo) {
		this.textoCorreo = textoCorreo;
	}

	public List<ProgramaEjecucion> getProgramasList() {
		return programasList;
	}

	public void setProgramasList(List<ProgramaEjecucion> programasList) {
		this.programasList = programasList;
	}

	public RecomendacionMB getRecMB() {
		return recMB;
	}

	public void setRecMB(RecomendacionMB recMB) {
		this.recMB = recMB;
	}

	public EvidenciaMB getEvdMB() {
		return evdMB;
	}

	public void setEvdMB(EvidenciaMB evdMB) {
		this.evdMB = evdMB;
	}

	public ArchivoMB getArcMB() {
		return arcMB;
	}

	public void setArcMB(ArchivoMB arcMB) {
		this.arcMB = arcMB;
	}

	public int getDias() {
		return dias;
	}

	public void setDias(int dias) {
		this.dias = dias;
	}

	public BitacoraActividadMB getBitaMB() {
		return bitaMB;
	}

	public void setBitaMB(BitacoraActividadMB bitaMB) {
		this.bitaMB = bitaMB;
	}

	public ComentarioHallazgoMB getComeMB() {
		return comeMB;
	}

	public void setComeMB(ComentarioHallazgoMB comeMB) {
		this.comeMB = comeMB;
	}

}
