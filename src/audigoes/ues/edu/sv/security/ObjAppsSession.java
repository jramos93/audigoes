package audigoes.ues.edu.sv.security;

import java.util.List;

import audigoes.ues.edu.sv.entities.administracion.Usuario;
import audigoes.ues.edu.sv.entities.administracion.UsuarioPermiso;

public class ObjAppsSession {

	private Usuario usuario;
	private String ip;
	private String host;
	private List<UsuarioPermiso> permisos;
	
	public ObjAppsSession() {}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public List<UsuarioPermiso> getPermisos() {
		return permisos;
	}

	public void setPermisos(List<UsuarioPermiso> permisos) {
		this.permisos = permisos;
	}
	
	
}
