package audigoes.ues.edu.sv.converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import audigoes.ues.edu.sv.entities.planeacion.PlanAnual;
import audigoes.ues.edu.sv.security.SecurityController;

@FacesConverter(value = "planAnualConverter")
public class PlanAnualConverter implements Converter {

	private SecurityController securityMB;

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		try {
			int id = 0;
			PlanAnual registro;
			if (arg2.trim().equals("")) {
				registro = new PlanAnual();
				return registro;
			} else {
				id = Integer.parseInt(arg2);
			}
			
			setSecurityMB((SecurityController) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginMB"));
			registro = (PlanAnual) getSecurityMB().getAudigoesLocal().findByPk(PlanAnual.class, id);
			if (registro == null) {
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en conversión",
						"No se pudo convertir el objeto"));
			}
			return registro;

		} catch (Exception e) {
			e.printStackTrace();
			throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en conversión",
					"No se pudo convertir el objeto"));
		}
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		try {
			if (arg2 != null && arg2.getClass().equals(PlanAnual.class)) {
				return String.valueOf(((PlanAnual) arg2).getPlaId());
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public SecurityController getSecurityMB() {
		return securityMB;
	}

	public void setSecurityMB(SecurityController securityMB) {
		this.securityMB = securityMB;
	}

}
