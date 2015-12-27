package discovering.authentication;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.dextra.security.configuration.DefaultCookieManager;

public class CookieManager extends DefaultCookieManager {

	public static final String COOKIE_NAME = "DISCOVERING-auth";

	@Override
	public void expireCookies(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(COOKIE_NAME)) {
					cookie.setMaxAge(0);
					cookie.setValue(null);
					cookie.setPath(generateCookiePath(request));
					response.addCookie(cookie);
				}
			}
		}
	}

	@Override
	public String extractAuthTokenFromCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(COOKIE_NAME)) {
					return decode(cookie.getValue());
				}
			}
		}

		return null;
	}

	@Override
	public String generateCookieName() {
		return COOKIE_NAME;
	}

	protected String generateCookiePath(HttpServletRequest req) {
		return "/";
	}
}
