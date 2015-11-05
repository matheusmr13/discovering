package discovering.models;

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
	private String operatingSystem;
	private String language;
	private String ip;
	private String fullUrl;

	private Integer screenWidth;
	private Integer screenHeight;
	private Integer browserWidth;
	private Integer browserHeight;

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

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getOperatingSystem() {
		return operatingSystem;
	}

	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	public Integer getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(Integer screenWidth) {
		this.screenWidth = screenWidth;
	}

	public Integer getScreenHeight() {
		return screenHeight;
	}

	public void setScreenHeight(Integer screenHeight) {
		this.screenHeight = screenHeight;
	}

	public Integer getBrowserWidth() {
		return browserWidth;
	}

	public void setBrowserWidth(Integer browserWidth) {
		this.browserWidth = browserWidth;
	}

	public Integer getBrowserHeight() {
		return browserHeight;
	}

	public void setBrowserHeight(Integer browserHeight) {
		this.browserHeight = browserHeight;
	}

	public String getFullUrl() {
		return fullUrl;
	}

	public void setFullUrl(String fullUrl) {
		this.fullUrl = fullUrl;
	}

}
