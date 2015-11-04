package discover.models;

// Short variable names to save memory on javascript
public class Action {

	// Timestamp that action happened
	private Long s;

	// Mouse
	private Integer x;
	private Integer y;

	// Input
	private String q;
	private Integer k;

	// Copy
	private String c;

	// Action Type
	private ActionType t;

	public Long getS() {
		return s;
	}

	public void setS(Long s) {
		this.s = s;
	}

	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public ActionType getT() {
		return t;
	}

	public void setT(ActionType t) {
		this.t = t;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public Integer getK() {
		return k;
	}

	public void setK(Integer k) {
		this.k = k;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}
}
