package audigoes.ues.edu.sv.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("estadoSeguimiento")
public class EstadoSeguimientoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		return value;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		String valor = value.toString();
		if (!valor.isEmpty()) {
			if (valor.equals("0")) {
				valor = "Seguimiento Sin Iniciar";
			} else if (valor.equals("1")) {
				valor = "Seguimiento Iniciado";
			} else if (valor.equals("2")) {
				valor = "Seguimiento Finalizado";
			}  else {
				valor = "---";
			}
		}
		return valor;
	}

}
