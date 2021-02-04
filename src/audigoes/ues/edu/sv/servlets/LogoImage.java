package audigoes.ues.edu.sv.servlets;

import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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
import audigoes.ues.edu.sv.security.SecurityController;
import audigoes.ues.edu.sv.session.audigoesSBSLLocal;

@WebServlet("/logoImage")
public class LogoImage extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_BUFFER_SIZE = 10240;

	@EJB(beanName = "audigoesSBSL")
	protected audigoesSBSLLocal audigoesLocal;

	public LogoImage() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		byte[] image = null;
		try {
			if (!StringUtils.isEmpty(id)) {
				System.out.println("id "+id);
				if (audigoesLocal!= null) {
					Institucion m = (Institucion) audigoesLocal.findByPk(Institucion.class, Integer.parseInt(id));
					if ((m != null) && (m.getInsLogo() != null)) {
						image = m.getInsLogo();
					} else {
						File file = new File(
								request.getSession().getServletContext().getRealPath("/resources/images/no-photo.png"));
						image = IOUtils.toByteArray(new FileInputStream(file));
					}
					response.reset();
					response.setBufferSize(DEFAULT_BUFFER_SIZE);
					if ((m != null) && (m.getInsLogo() != null)) {
						// response.setContentType(m.getMarArcExt());
						response.setContentType("image/png");
					} else {
						response.setContentType("image/png");
					}

					response.setHeader("Content-Disposition", "inline; filename=\"" + "insLogo" + "\"");
					response.setContentLength(image.length);
					BufferedOutputStream output = null;
					try {
						output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);
						output.write(image);
					} finally {
						close(output);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void close(Closeable resource) {
		if (resource != null) {
			try {
				resource.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public audigoesSBSLLocal getAudigoesLocal() {
		return audigoesLocal;
	}

	public void setAudigoesLocal(audigoesSBSLLocal audigoesLocal) {
		this.audigoesLocal = audigoesLocal;
	}



}
