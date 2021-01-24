package audigoes.ues.edu.sv.servlets;

import java.io.Closeable;
import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import audigoes.ues.edu.sv.session.audigoesSBSLLocal;

@WebServlet("/report.pdf")
public class PdfReportServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private static final int DEFAULT_BUFFER_SIZE = 10240;

	@EJB(beanName = "audigoesSBSL")
	protected audigoesSBSLLocal audigoesLocal;

	public PdfReportServlet() {
		super();
	}
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        byte[] content = (byte[]) request.getSession().getAttribute("pdfBytesArray");
        response.setContentType("application/pdf");
        response.setContentLength(content.length);
        response.getOutputStream().write(content);
    }

//	protected void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		String id = request.getParameter("id");
//		byte[] image = null;
//		try {
//			if (!StringUtils.isEmpty(id)) {
//				System.out.println("id "+id);
//				if (audigoesLocal!= null) {
//					Marca m = (Marca) audigoesLocal.findByPk(Marca.class, Integer.parseInt(id));
//					if ((m != null) && (m.getMarArcArchivo() != null)) {
//						image = m.getMarArcArchivo();
//					} else {
//						File file = new File(
//								request.getSession().getServletContext().getRealPath("/resources/images/no-photo.png"));
//						image = IOUtils.toByteArray(new FileInputStream(file));
//					}
//					response.reset();
//					response.setBufferSize(DEFAULT_BUFFER_SIZE);
//					if ((m != null) && (m.getMarArcArchivo() != null)) {
//						// response.setContentType(m.getMarArcExt());
//						response.setContentType("image/png");
//					} else {
//						response.setContentType("image/png");
//					}
//
//					response.setHeader("Content-Disposition", "inline; filename=\"" + "marArcArchivo" + "\"");
//					response.setContentLength(image.length);
//					BufferedOutputStream output = null;
//					try {
//						output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);
//						output.write(image);
//					} finally {
//						close(output);
//					}
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

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
