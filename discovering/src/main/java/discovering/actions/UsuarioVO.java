package discovering.actions;

import io.yawp.repository.IdRef;
import discovering.models.Usuario;

public class UsuarioVO {

	private IdRef<Usuario> id;
	private String login;
	private String email;
	private String nomeCompleto;
	private String telefoneCelular;
	private String telefoneComercial;

	public UsuarioVO(Usuario usuarioLogado) {
		this.login = usuarioLogado.getLogin();
		this.email = usuarioLogado.getEmail();
	}

	public IdRef<Usuario> getId() {
		return id;
	}

	public void setId(IdRef<Usuario> id) {
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
