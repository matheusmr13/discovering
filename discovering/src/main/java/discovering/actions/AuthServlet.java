package discovering.actions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.dextra.security.AuthenticationServlet;
import br.com.dextra.security.Credential;
import br.com.dextra.security.exceptions.AuthenticationFailedException;

public class AuthServlet extends AuthenticationServlet {

	private static final long serialVersionUID = -2146254848098407871L;

	@Override
	protected void sendSuccess(String token, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		super.sendSuccess(token, req, resp);
	}

	@Override
	protected void sendError(AuthenticationFailedException e, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		super.sendError(e, req, resp);
	}

	@Override
	protected Credential authenticate(HttpServletRequest request) throws AuthenticationFailedException {
		return new Credential(request.getParameter("login"), configuration.getMyProvider());
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
	}
}
