package uk.ac.ncl.safecap.xmldata.editors;

import uk.ac.ncl.safecap.xmldata.DataContext;

class TypeTransformationTemplate {
	DataContext context;
	String type;

	public TypeTransformationTemplate(DataContext context, String type) {
		this.context = context;
		this.type = type;
	}
}