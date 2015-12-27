package discovering.authentication;

import io.yawp.repository.EndpointScanner;
import io.yawp.repository.Repository;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import br.com.dextra.security.Credential;
import br.com.dextra.security.CredentialHolder;
import br.com.dextra.security.exceptions.ExpiredAuthTokenException;
import br.com.dextra.security.exceptions.InvalidAuthTokenException;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import discovering.exceptions.ValidationException;
import discovering.user.User;
import discovering.utils.CriptoUtils;
import discovering.utils.LoggedUserUtils;

public class AuthenticationFilter extends br.com.dextra.security.AuthenticationFilter {

	private Logger logger = Logger.getLogger(AuthenticationFilter.class.getCanonicalName());

	private static Repository yawp = Repository.r().setFeatures(new EndpointScanner("").enableHooks(true).scan());

	protected void process(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		if (!loginWithToken(request, response, chain)) {
			loggedWithGoogle(request, response, chain);
		} else if (request.getRequestURI().contains("/auth")) {
			if (request.getRequestURI().contains("/logout")) {
				logout(request, response);
			} else {
				login(request, response, chain);
			}
		}
	}

	private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		getConfiguration().getCookieManager().expireCookies(request, response);
		LoggedUserUtils.LOGGED_USER.remove();
		CredentialHolder.deregister();

		response.getWriter().write("/");
	}

	private boolean loginWithToken(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		String token = extractAuthTokenFrom(request);

		if (StringUtils.isBlank(token)) {
			return false;
		}

		Credential credential;
		try {
			credential = processAndValidate(token);
			logger.info("Received authentication token : " + credential.toString());
		} catch (InvalidAuthTokenException e) {
			logger.log(Level.WARNING, "Invalid authentication token received. " + e.getMessage());
			getConfiguration().getCookieManager().expireCookies(request, response);
			return false;
		} catch (ExpiredAuthTokenException e) {
			logger.log(Level.WARNING, "Expired authentication token received.", e);
			getConfiguration().getCookieManager().expireCookies(request, response);
			return false;
		} catch (Exception e) {
			logger.log(Level.WARNING, "Error while processing the received authentication token : " + token, e);
			return false;
		}

		try {
			if (mustRenew(credential)) {
				getConfiguration().getCookieManager().expireCookies(request, response);
				credential = renew(credential, request, response);
			}
		} catch (Exception e) {
			logger.log(Level.WARNING, "Error while processing the received authentication token : " + token, e);
			return false;
		}

		try {
			String email = token.split("\\|")[0];
			LoggedUserUtils.LOGGED_USER.set(fetchUserByEmail(email));
			CredentialHolder.register(credential);
			chain.doFilter(request, response);
		} finally {
			CredentialHolder.deregister();
		}
		return true;
	}

	@Override
	protected String extractAuthTokenFrom(HttpServletRequest request) {
		return getConfiguration().getCookieManager().extractAuthTokenFromCookie(request);
	}

	private boolean loggedWithGoogle(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		UserService userService = UserServiceFactory.getUserService();
		com.google.appengine.api.users.User googleCurrentUser = userService.getCurrentUser();

		if (googleCurrentUser != null) {
			User systemUser = fetchUserOrCreateNew(userService, googleCurrentUser);
			LoggedUserUtils.LOGGED_USER.set(systemUser);

			chain.doFilter(request, response);
			return true;
		}
		sendError(request, response);
		response.sendRedirect("/signin/");
		return false;
	}

	private void login(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		UserService userService = UserServiceFactory.getUserService();
		com.google.appengine.api.users.User googleCurrentUser = userService.getCurrentUser();

		if (googleCurrentUser == null) {
			loginComEmailESenha(request, response, chain);
		}
	}

	private void loginComEmailESenha(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException {
		String login = request.getParameter("login");
		String password = request.getParameter("password");

		if (login == null && password == null) {
			sendError(request, response);
			return;
		}

		try {
			User user = this.auth(login, password);
			LoggedUserUtils.LOGGED_USER.set(user);
			if (user != null) {
				chain.doFilter(request, response);
			}
		} catch (ValidationException e) {
			sendErrorMessage(response, e.getText());
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Authentication error!", e);
			sendError(request, response);
		}
	}

	private User auth(String email, String password) {
		User usuario = fetchUserByEmail(email);
		if (usuario == null) {
			throw new ValidationException("Invalid login or password!");
		}

		if (!CriptoUtils.sha256(password).equals(usuario.getPassword())) {
			throw new ValidationException("Invalid login or password!");
		}

		return usuario;
	}

	private void sendErrorMessage(HttpServletResponse response, String message) throws IOException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("text/plain");
		response.getWriter().write(message);
		response.getWriter().close();
	}

	private User fetchUserOrCreateNew(UserService userService, com.google.appengine.api.users.User currentUser) {
		User usuarioNoSistema = buscaUsuario(currentUser);

		if (usuarioNoSistema == null) {
			usuarioNoSistema = novoUsuario(userService, currentUser);
		}

		return usuarioNoSistema;
	}

	private User buscaUsuario(com.google.appengine.api.users.User currentUser) {
		return fetchUserByEmail(currentUser.getEmail());
	}

	private User fetchUserByEmail(String login) {
		return yawp.query(User.class).where("login", "=", login).first();
	}

	private User novoUsuario(UserService userService, com.google.appengine.api.users.User currentUser) {
		User usuario = new User();

		usuario.setLogin(currentUser.getEmail());
		usuario.setEmail(currentUser.getEmail());
		usuario.setAdmin(userService.isUserAdmin());

		return yawp.save(usuario);
	}
}