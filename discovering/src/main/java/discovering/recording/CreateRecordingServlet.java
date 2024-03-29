package discovering.recording;

import io.yawp.commons.utils.JsonUtils;
import io.yawp.repository.EndpointScanner;
import io.yawp.repository.Repository;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;

public class CreateRecordingServlet extends HttpServlet {

	private static final long serialVersionUID = 463643468198227006L;
	private static Repository yawp = Repository.r().setFeatures(new EndpointScanner("").enableHooks(true).scan());

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setHeader("Access-Control-Allow-Origin", "http://gae-example-990.appspot.com");//TODO Change to only registered servers
		res.setHeader("Access-Control-Allow-Methods", "POST,GET");
		res.setHeader("Access-Control-Allow-Headers", "Content-Type");
		res.setHeader("Access-Control-Max-Age", "86400");

		//TODO Verifications
		Recording recording = JsonUtils.from(yawp, req.getParameter("recording"), Recording.class);
		yawp.save(recording);

		res.setStatus(HttpStatus.SC_OK);
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.getWriter().write("asdasdasdas");// FIXME
		res.setStatus(HttpStatus.SC_OK);
	}
}
