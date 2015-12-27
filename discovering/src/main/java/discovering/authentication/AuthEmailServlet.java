package discovering.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;

import com.google.appengine.api.users.UserServiceFactory;

public class AuthEmailServlet extends HttpServlet {

	private static final long serialVersionUID = 6879089993722458500L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		if (StringUtils.isNotBlank(req.getPathInfo()) && req.getPathInfo().contains("loginUrl")) {
			res.getWriter().println(UserServiceFactory.getUserService().createLoginURL("/discovering"));
			res.setStatus(HttpStatus.SC_OK);
			res.setContentType("text");
		} else if (StringUtils.isNotBlank(req.getPathInfo()) && req.getPathInfo().contains("logoutUrl")) {
			res.getWriter().println(UserServiceFactory.getUserService().createLogoutURL("/"));
			res.setStatus(HttpStatus.SC_OK);
			res.setContentType("text");
		} else {
			res.setStatus(HttpStatus.SC_BAD_REQUEST);
		}
	}
}
