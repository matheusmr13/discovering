package discovering.actions;

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

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import discovering.models.Usuario;

public class AuthenticationFilter extends br.com.dextra.security.AuthenticationFilter {

	private Logger logger = Logger.getLogger(AuthenticationFilter.class.getCanonicalName());

	private static Repository yawp = Repository.r().setFeatures(new EndpointScanner("").enableHooks(true).scan());

	protected void process(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		if (request.getRequestURI().contains("/auth")) {
			if (request.getRequestURI().contains("/logout")) {
				deslogar(request, response);
			} else {
				realizaLogin(request, response, chain);
			}
		} else if (possuiTokenValido(request)) {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Methods", "POST,GET");
			response.setHeader("Access-Control-Allow-Headers", "Content-Type");
			response.setHeader("Access-Control-Max-Age", "86400");
			chain.doFilter(request, response);
		} else if (!logouViaEmail(request, response, chain)) {
			logaViaToken(request, response, chain);
		} else {
			sendErrorMessage(response, "erro");
		}
		//TODO rever ordem prioridade
	}

	private boolean possuiTokenValido(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return true;
	}

	private void deslogar(HttpServletRequest request, HttpServletResponse response) throws IOException {
		getConfiguration().getCookieManager().expireCookies(request, response);
		UsuarioLogadoUtils.USUARIO_LOGADO.remove();
		CredentialHolder.deregister();

		response.getWriter().write("/");
	}

	private void logaViaToken(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		String token = extractAuthTokenFrom(request);

		if (StringUtils.isBlank(token)) {
			response.sendRedirect("/#email-invalido");
			return;
		}

		Credential credential;
		try {
			credential = processAndValidate(token);
			logger.info("Received authentication token : " + credential.toString());
		} catch (InvalidAuthTokenException e) {
			logger.log(Level.WARNING, "Invalid authentication token received. " + e.getMessage());
			getConfiguration().getCookieManager().expireCookies(request, response);
			sendError(request, response);
			response.sendRedirect("/#email-invalido");
			return;
		} catch (ExpiredAuthTokenException e) {
			logger.log(Level.WARNING, "Expired authentication token received.", e);
			getConfiguration().getCookieManager().expireCookies(request, response);
			sendExpiryError(request, response, token);
			response.sendRedirect("/#email-invalido");
			return;
		} catch (Exception e) {
			logger.log(Level.WARNING, "Error while processing the received authentication token : " + token, e);
			getConfiguration().getCookieManager().expireCookies(request, response);
			response.sendRedirect("/#email-invalido");
			return;
		}

		try {
			if (mustRenew(credential)) {
				getConfiguration().getCookieManager().expireCookies(request, response);
				credential = renew(credential, request, response);
			}
		} catch (Exception e) {
			logger.log(Level.WARNING, "Error while processing the received authentication token : " + token, e);
			sendError(request, response);
			response.sendRedirect("/#email-invalido");
			return;
		}

		try {
			String email = token.split("\\|")[0];
			UsuarioLogadoUtils.USUARIO_LOGADO.set(buscaUsuario(email));
			CredentialHolder.register(credential);
			chain.doFilter(request, response);
		} finally {
			CredentialHolder.deregister();
		}
	}

	@Override
	protected String extractAuthTokenFrom(HttpServletRequest request) {
		return getConfiguration().getCookieManager().extractAuthTokenFromCookie(request);
	}

	private boolean logouViaEmail(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		UserService userService = UserServiceFactory.getUserService();
		User currentUser = userService.getCurrentUser();

		if (currentUser != null && temDominioValido(currentUser)) {
			Usuario usuarioNoSistema = buscaUsuarioOuCriaNovo(userService, currentUser);
			UsuarioLogadoUtils.USUARIO_LOGADO.set(usuarioNoSistema);

			chain.doFilter(request, response);
			return true;
		}

		return false;
	}

	private void realizaLogin(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		UserService userService = UserServiceFactory.getUserService();
		User currentUser = userService.getCurrentUser();

		if (currentUser == null || !temDominioValido(currentUser)) {
			loginComEmailESenha(request, response, chain);
		}
	}

	private void loginComEmailESenha(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException {
		String login = request.getParameter("email");
		String password = request.getParameter("senha");

		if (login == null && password == null) {
			sendError(request, response);
			return;
		}

		try {
			Usuario usuario = this.autenticacao(login, password);
			UsuarioLogadoUtils.USUARIO_LOGADO.set(usuario);
			if (usuario != null) {
				chain.doFilter(request, response);
			}
		} catch (ValidacaoException e) {
			sendErrorMessage(response, e.getText());
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Erro executanto autenticacao", e);
			sendError(request, response);
		}
	}

	private Usuario autenticacao(String email, String password) {
		Usuario usuario = buscaUsuario(email);
		if (usuario == null) {
			throw new ValidacaoException("E-mail ou senha incorretos");
		}
		String pass = password;
//		if (usuario.getPerfil() != Perfil.COMISSAO)
//			pass = CriptoUtils.senhaCriptografada(password);

		if (!pass.equals(usuario.getSenha())) {
			throw new ValidacaoException("E-mail ou senha incorretos");
		}

		return usuario;
	}

	private void sendErrorMessage(HttpServletResponse response, String message) throws IOException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("text/plain");
		response.getWriter().write(message);
		response.getWriter().close();
	}

	private Usuario buscaUsuarioOuCriaNovo(UserService userService, User currentUser) {
		Usuario usuarioNoSistema = buscaUsuario(currentUser);

		if (usuarioNoSistema == null) {
			usuarioNoSistema = novoUsuario(userService, currentUser);
		}

		return usuarioNoSistema;
	}

	private Usuario buscaUsuario(User currentUser) {
		return buscaUsuario(currentUser.getEmail());
	}

	private Usuario buscaUsuario(String email) {
		return yawp.query(Usuario.class).where("login", "=", email).first();
	}

	private Usuario novoUsuario(UserService userService, User currentUser) {
		Usuario usuario = new Usuario();

		usuario.setLogin(currentUser.getEmail());
		usuario.setEmail(currentUser.getEmail());
		usuario.setAdmin(userService.isUserAdmin());

//		if (usuario.getAdmin()) {
//			usuario.setPerfil(Perfil.ADMINISTRADOR);
//		} else {
//			usuario.setPerfil(Perfil.INDEFINIDO);
//		}

		return yawp.save(usuario);
	}

	private boolean temDominioValido(User currentUser) {
		String email = currentUser.getEmail();
		if (StringUtils.isBlank(email) || email.indexOf("@") == -1) {
			return false;
		}

		String dominio = email.split("@")[1];
		return dominio.contains("b2agencia") || dominio.contains("dextra-sw");
	}

}