package audigoes.ues.edu.sv.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("estadoRecomendacion")
public class RecomendacionConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		return value;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		String valor = value.toString();
		if (!valor.isEmpty()) {
			if (valor.equals("1")) {
				valor = "Pendiente";
			} else if (valor.equals("2")) {
				valor = "Implementada";
			} else if (valor.equals("3")) {
				valor = "En proceso";
			}  else {
				valor = "---";
			}
		}
		return valor;
	}

}
