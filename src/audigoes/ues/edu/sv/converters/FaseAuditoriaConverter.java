package audigoes.ues.edu.sv.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("faseAuditoriaConverter")
public class FaseAuditoriaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		return value;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		String valor = value.toString();
		if (!valor.isEmpty()) {
			if (valor.equals("0")) {
				valor = "PROGRAMADA";
			} else if (valor.equals("1")) {
				valor = "FASE PLANIFICACIÓN";
			} else if (valor.equals("2")) {
				valor = "FASE EJECUCIÓN";
			} else if (valor.equals("3")) {
				valor = "FASE INFORME";
			} else if (valor.equals("4")) {
				valor = "FASE SEGUIMIENTO";
			} else if (valor.equals("5")) {
				valor = "FINALIZADA";
			} else {
				valor = "---";
			}
		}
		return valor;
	}

}
