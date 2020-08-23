package audigoes.ues.edu.sv.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthenticationFilter implements Filter{
	private FilterConfig config;
	
	public void init(FilterConfig cfg) throws ServletException {
		this.config=cfg;
		
	}

	public void destroy() {
		this.config = null;
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest) request).getSession();
		String authKey = (String) session.getAttribute("audigoes.user.name");
		
		if(authKey==null || authKey.isEmpty()) {
			HttpServletRequest reqt = (HttpServletRequest) request;
			HttpServletResponse resp = (HttpServletResponse) response;
			resp.sendRedirect(reqt.getContextPath());
		}
		
		chain.doFilter(request, response);
	}
}
