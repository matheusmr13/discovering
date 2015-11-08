package discovering.actions;

import io.yawp.commons.http.annotation.GET;
import io.yawp.repository.actions.Action;
import discovering.models.Usuario;

public class UsuarioAction extends Action<Usuario> {

	@GET("me")
	public UsuarioVO me() {
		return new UsuarioVO(UsuarioLogadoUtils.getUsuarioLogado());
	}
}
