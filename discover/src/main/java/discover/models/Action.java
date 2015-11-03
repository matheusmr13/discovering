package discover.models;

public class Action {

	private Integer x;
	private Integer y;
	private Long timeSince;
	private ActionType actionType;

	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public ActionType getActionType() {
		return actionType;
	}

	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public Long getTimeSince() {
		return timeSince;
	}

	public void setTimeSince(Long timeSince) {
		this.timeSince = timeSince;
	}
}
