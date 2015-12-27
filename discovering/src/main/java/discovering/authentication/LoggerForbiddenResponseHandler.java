package discovering.authentication;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.dextra.security.configuration.ForbiddenResponseHandler;

public class LoggerForbiddenResponseHandler extends ForbiddenResponseHandler {

	private static final Logger logger = Logger
			.getLogger(LoggerForbiddenResponseHandler.class.getCanonicalName());

	@Override
	public void sendResponse(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		logger.log(Level.WARNING,
				"Forbidden access to resource " + request.getRequestURI()
						+ " from IP " + getIpAddress(request) + ".");

		super.sendResponse(request, response);
	}

	public static String getIpAddress(HttpServletRequest request) {
		String ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null) {
			ipAddress = request.getHeader("X_FORWARDED_FOR");
			if (ipAddress == null) {
				ipAddress = request.getRemoteAddr();
			}
		}
		return ipAddress;
	}
}
