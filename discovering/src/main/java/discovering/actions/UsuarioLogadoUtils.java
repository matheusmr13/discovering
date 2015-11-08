package discovering.actions;

import discovering.models.Usuario;

public class UsuarioLogadoUtils {

		public static final ThreadLocal<Usuario> USUARIO_LOGADO = new ThreadLocal<Usuario>();

		public static Usuario getUsuarioLogado() {
			if (USUARIO_LOGADO.get() != null)
				return USUARIO_LOGADO.get();

			throw new ValidacaoException("Usuario-nao-logado");
		}

}
