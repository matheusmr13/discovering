package discovering.actions;

import io.yawp.repository.EndpointScanner;
import io.yawp.repository.Repository;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;

import discovering.models.User;

public class CreateUserServlet extends HttpServlet {

	private static final long serialVersionUID = 463643468198227006L;
	private static Repository yawp = Repository.r().setFeatures(new EndpointScanner("").enableHooks(true).scan());

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		User user = new User();
		user.setEmail(req.getParameter("email"));
		user.setLogin(req.getParameter("login"));
		user.setPassword(req.getParameter("password"));
		yawp.saveWithHooks(user);

		res.setStatus(HttpStatus.SC_OK);
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setStatus(HttpStatus.SC_OK);
	}
}
