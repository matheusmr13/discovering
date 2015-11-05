package discovering.models;

public enum ActionType {
	MD("Mouse Drag"), MM("Mouse Move"), MC("Mouse Click"), CP("Copy"), IF("Input Focus"), IK("Input Key");

	private String name;

	private ActionType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
