package audigoes.ues.edu.sv.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("estadoComentarioConverter")
public class EstadoComentarioConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		return value;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		String valor = value.toString();
		if (!valor.isEmpty()) {
			if (valor.equals("1")) {
				valor = "REDACCIÓN"; //REDACCIÓN UNIDAD AUDITADA
			} else if (valor.equals("2")) {
				valor = "COMUNICADO A AUDITORIA"; //COMUNICADO A AUDITORÍA
			} else if (valor.equals("3")) {
				valor = "REDACCIÓN"; //REDACCIÓN UNIDAD DE AUDITORÍA
			} else if (valor.equals("4")) {
				valor = "REVISIÓN";
			} else if (valor.equals("5")) {
				valor = "OBSERVADO";
			} else if (valor.equals("6")) {
				valor = "A COMUNICAR";
			} else if (valor.equals("7")) {
				valor = "COMUNICADO";
			} else {
				valor = "NO DEFINIDO";
			}
		}
		return valor;
	}

}
