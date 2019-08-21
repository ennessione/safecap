package uk.ac.ncl.safecap.xdata.verification.configsave;

import java.util.Date;

public class PackagedPropertyReview {
	private final Date date;
	private final PropertyReview propertyReview;

	public PackagedPropertyReview(Date date, PropertyReview propertyReview) {
		super();
		this.date = date;
		this.propertyReview = propertyReview;
	}

	public Date getDate() {
		return date;
	}

	public PropertyReview getPropertyReview() {
		return propertyReview;
	}
}
