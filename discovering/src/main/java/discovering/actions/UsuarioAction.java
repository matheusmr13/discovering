package discovering.actions;

import io.yawp.commons.http.annotation.GET;
import io.yawp.repository.actions.Action;
import discovering.models.User;

public class UsuarioAction extends Action<User> {

	@GET("me")
	public UsuarioVO me() {
		return new UsuarioVO(UsuarioLogadoUtils.getUsuarioLogado());
	}
}
