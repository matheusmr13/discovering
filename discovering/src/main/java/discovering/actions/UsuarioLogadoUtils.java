package discovering.actions;

import discovering.models.User;

public class UsuarioLogadoUtils {

		public static final ThreadLocal<User> USUARIO_LOGADO = new ThreadLocal<User>();

		public static User getUsuarioLogado() {
			if (USUARIO_LOGADO.get() != null)
				return USUARIO_LOGADO.get();

			throw new ValidacaoException("Usuario-nao-logado");
		}

}
