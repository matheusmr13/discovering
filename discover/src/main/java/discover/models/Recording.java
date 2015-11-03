package discover.models;

import java.util.Date;
import java.util.List;

import io.yawp.repository.IdRef;
import io.yawp.repository.annotations.Endpoint;
import io.yawp.repository.annotations.Id;
import io.yawp.repository.annotations.Json;

@Endpoint(path = "/recordings")
public class Recording {

	@Id
	private IdRef<Recording> id;

	private Date startDate;
	private String browser;
	private String version;
	private String majorVersion;
	private String navigatorAppName;
	private String navigatorUserAgent;

	@Json
	private List<Action> actions;

	public IdRef<Recording> getId() {
		return id;
	}

	public void setId(IdRef<Recording> id) {
		this.id = id;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMajorVersion() {
		return majorVersion;
	}

	public void setMajorVersion(String majorVersion) {
		this.majorVersion = majorVersion;
	}

	public String getNavigatorAppName() {
		return navigatorAppName;
	}

	public void setNavigatorAppName(String navigatorAppName) {
		this.navigatorAppName = navigatorAppName;
	}

	public String getNavigatorUserAgent() {
		return navigatorUserAgent;
	}

	public void setNavigatorUserAgent(String navigatorUserAgent) {
		this.navigatorUserAgent = navigatorUserAgent;
	}

	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

}
