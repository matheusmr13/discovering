package discovering.user;

import discovering.exceptions.ValidationException;
import discovering.utils.CriptoUtils;
import io.yawp.repository.hooks.Hook;

public class UserHook extends Hook<User> {
	public void beforeSave(User user) {
		if (user.getId() == null) {
			User userSameLogin = yawp(User.class).where("login", "=", user.getLogin()).first();
			if (userSameLogin != null) {
				throw new ValidationException("An user with this login already exists!");
			}

			user.setPassword(CriptoUtils.sha256(user.getPassword()));
		}
	}

}
