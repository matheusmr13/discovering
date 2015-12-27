package discovering.utils;

import discovering.exceptions.ValidationException;
import discovering.user.User;

public class LoggedUserUtils {

		public static final ThreadLocal<User> LOGGED_USER = new ThreadLocal<User>();

		public static User getUsuarioLogado() {
			if (LOGGED_USER.get() != null)
				return LOGGED_USER.get();

			throw new ValidationException("Currently user not found!");
		}

}
