package audigoes.ues.edu.sv.security;

import java.util.Iterator;
import java.util.List;

import audigoes.ues.edu.sv.entities.administracion.Usuario;
import audigoes.ues.edu.sv.entities.administracion.UsuarioPermiso;

public class ObjAppsSession {

	private Usuario usuario;
	private String rol;
	private String ip;
	private String host;
	private List<UsuarioPermiso> permisos;

	public ObjAppsSession() {
	}

	public boolean isPermisoValido(String rol) {
		boolean valido = false;

		if (this.permisos != null) {
			Iterator var = this.permisos.iterator();

			while (var.hasNext()) {
				UsuarioPermiso reg = (UsuarioPermiso) var.next();
				String comparar = rol.trim().toUpperCase();
				String registro = reg.getRol().getRolIdentificador().trim().toUpperCase();

				if (registro.equals(comparar) && reg.getRegActivo() == 1) {
					return true;
				}
			}
		}
		return valido;
	}

	public boolean isPermisoValido(String rol, String permiso) {
		boolean valido = false;
		if (this.permisos != null) {
			Iterator var = this.permisos.iterator();

			while (var.hasNext()) {
				UsuarioPermiso reg = (UsuarioPermiso) var.next();
				String comparar = rol.trim().toUpperCase() + ":" + permiso.trim().toUpperCase();
				String registro = reg.getRol().getRolIdentificador().trim().toUpperCase() + ":"
						+ reg.getPermiso().getPerIdentificador().trim().toUpperCase();

				if (registro.equals(comparar) && reg.getRegActivo() == 1) {
					return true;
				}
			}
		}
		return valido;
	}

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

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

}
