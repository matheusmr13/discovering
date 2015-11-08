package discovering.actions;

import io.yawp.repository.EndpointScanner;
import io.yawp.repository.Repository;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationFilter implements Filter {

	private static Repository yawp = Repository.r().setFeatures(new EndpointScanner("").enableHooks(true).scan());

	protected void process(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		if (request.getRequestURI().contains("/auth")) {
			if (request.getRequestURI().contains("/logout")) {
				deslogar(request, response);
			}
		} else if (possuiTokenValido(request)) {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Methods", "POST,GET");
			response.setHeader("Access-Control-Allow-Headers", "Content-Type");
			response.setHeader("Access-Control-Max-Age", "86400");
			chain.doFilter(request, response);
		} else {
			sendErrorMessage(response, "erro");
		}
	}

	private void deslogar(HttpServletRequest request, HttpServletResponse response) throws IOException {
		UsuarioLogadoUtils.USUARIO_LOGADO.remove();
		response.getWriter().write("/");
	}

	private boolean possuiTokenValido(HttpServletRequest request) throws IOException, ServletException {
		return true; // TODO Validar token chamada
		// return false;
	}

	private void sendErrorMessage(HttpServletResponse response, String message) throws IOException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("text/plain");
		response.getWriter().write(message);
		response.getWriter().close();
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		process((HttpServletRequest) request, (HttpServletResponse) response, chain);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
