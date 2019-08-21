package uk.ac.ncl.safecap.xdata.verification.configsave;

import java.io.Serializable;

public class PropertyReview implements Serializable {
	public enum STATE {
		NONE, ACCEPTED, REJECTED, REVIEW
	};

	private static final long serialVersionUID = -3436561852089780846L;
	private STATE state = STATE.NONE;
	private String commentary = "";

	public PropertyReview(STATE state, String commentary) {
		this.state = state;
		this.commentary = commentary;
	}

	public STATE getState() {
		return state;
	}

	public void setState(STATE state) {
		this.state = state;
	}

	public String getCommentary() {
		return commentary;
	}

	public void setCommentary(String commentary) {
		this.commentary = commentary;
	}
}
