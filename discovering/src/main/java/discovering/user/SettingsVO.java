package discovering.user;


public class SettingsVO {

	private  String script;
	public SettingsVO(User usuarioLogado) {
		this.script = "<script>window.myToken=\"huehue\";</script><script src=\"localhost:8080/js/cdn/discovery.js\"></script>";
	}
	public String getScript() {
		return script;
	}
	public void setScript(String script) {
		this.script = script;
	}
}
