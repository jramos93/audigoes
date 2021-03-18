package audigoes.ues.edu.sv.servlets;

import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import audigoes.ues.edu.sv.entities.administracion.Institucion;
import audigoes.ues.edu.sv.entities.administracion.Marca;
import audigoes.ues.edu.sv.entities.administracion.SolicitudesClave;
import audigoes.ues.edu.sv.mbeans.LoginMB;
import audigoes.ues.edu.sv.security.SecurityController;
import audigoes.ues.edu.sv.session.audigoesSBSLLocal;
import audigoes.ues.edu.sv.util.Utils;

@WebServlet("/cambioClave")
public class CambioClave extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB(beanName = "audigoesSBSL")
	protected audigoesSBSLLocal audigoesLocal;

	public CambioClave() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String usuario = Utils.decode(request.getParameter("param"));
		String sol = Utils.decode(request.getParameter("param2"));
		SolicitudesClave s = null;
		System.out.println("id "+sol);
		try {
			s = (SolicitudesClave) audigoesLocal.findByPk(SolicitudesClave.class, Integer.parseInt(sol));

			if (s != null) {
				if (s.getRegActivo() == 1L) {
					

					s.setUsuModi("ROOT");
					s.setFecModi(new Date());
					s.setRegActivo(0);

					audigoesLocal.update(s);

					LoginMB bean = (LoginMB) request.getSession().getAttribute("loginMB");
					System.out.println("bean");
					if (bean == null) {
						System.out.println("null");
						bean = new LoginMB();
						getServletContext().setAttribute("loginMB", bean);
					}
					
					System.out.println("usuario "+usuario);
					bean.setUsuario(usuario);
					Institucion i = new Institucion();
					i.setInsId(2);
					bean.setInstitucionSelected(i);
					bean.onGeneraClave();
					
					response.setContentType("text/html");
					request.getRequestDispatcher("info.xhtml").forward(request, response);
				} else {
					response.setContentType("text/html");
					request.getRequestDispatcher("expirada.xhtml").forward(request, response);
					;
				}
			} else {
				response.setContentType("text/html");
				request.getRequestDispatcher("error.xhtml").forward(request, response);
				;
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setContentType("text/html");
			request.getRequestDispatcher("error.xhtml").forward(request, response);
		}

	}

	public audigoesSBSLLocal getAudigoesLocal() {
		return audigoesLocal;
	}

	public void setAudigoesLocal(audigoesSBSLLocal audigoesLocal) {
		this.audigoesLocal = audigoesLocal;
	}

}
