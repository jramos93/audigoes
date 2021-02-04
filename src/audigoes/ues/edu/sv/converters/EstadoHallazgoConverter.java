package audigoes.ues.edu.sv.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("estadoHallazgo")
public class EstadoHallazgoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		return value;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		String valor = value.toString();
		if (!valor.isEmpty()) {
			if (valor.equals("1")) {
				valor = "REDACCI�N";
			} else if (valor.equals("2")) {
				valor = "REVISI�N";
			} else if (valor.equals("3")) {
				valor = "A COMUNICAR";
			} else if (valor.equals("4")) {
				valor = "COMUNICADO";
			} else if (valor.equals("5")) {
				valor = "AN�LISIS";
			} else if (valor.equals("6")) {
				valor = "SUPERVISI�N";
			} else if (valor.equals("7")) {
				valor = "FINALIZADO";
			} else if (valor.equals("8")) {
				valor = "FINALIZADO";
			} else {
				valor = "---";
			}
		}
		return valor;
	}

}
