package discovering.user;

import io.yawp.commons.http.annotation.GET;
import io.yawp.repository.actions.Action;
import discovering.utils.LoggedUserUtils;

public class UserAction extends Action<User> {

	@GET("me")
	public UserVO me() {
		return new UserVO(LoggedUserUtils.getUsuarioLogado());
	}

	@GET("settings")
	public SettingsVO settings() {
		return new SettingsVO(LoggedUserUtils.getUsuarioLogado());
	}
}
