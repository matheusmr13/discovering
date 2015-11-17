package discovering.actions;

import discovering.models.User;

public class UsuarioLogadoUtils {

		public static final ThreadLocal<User> LOGGED_USER = new ThreadLocal<User>();

		public static User getUsuarioLogado() {
			if (LOGGED_USER.get() != null)
				return LOGGED_USER.get();

			throw new ValidationException("Currently user not found!");
		}

}
