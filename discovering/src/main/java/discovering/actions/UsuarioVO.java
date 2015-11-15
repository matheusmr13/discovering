package discovering.actions;

import io.yawp.repository.IdRef;
import discovering.models.User;

public class UsuarioVO {

	private IdRef<User> id;
	private String login;
	private String email;
	private String nomeCompleto;
	private String telefoneCelular;
	private String telefoneComercial;

	public UsuarioVO(User usuarioLogado) {
		this.login = usuarioLogado.getLogin();
		this.email = usuarioLogado.getEmail();
	}

	public IdRef<User> getId() {
		return id;
	}

	public void setId(IdRef<User> id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public String getTelefoneCelular() {
		return telefoneCelular;
	}

	public void setTelefoneCelular(String telefoneCelular) {
		this.telefoneCelular = telefoneCelular;
	}

	public String getTelefoneComercial() {
		return telefoneComercial;
	}

	public void setTelefoneComercial(String telefoneComercial) {
		this.telefoneComercial = telefoneComercial;
	}
}
