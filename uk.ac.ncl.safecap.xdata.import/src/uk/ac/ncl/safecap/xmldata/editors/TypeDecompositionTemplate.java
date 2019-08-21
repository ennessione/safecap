package uk.ac.ncl.safecap.xmldata.editors;

import uk.ac.ncl.safecap.xmldata.DataContext;

public class TypeDecompositionTemplate {
	public DataContext context;
	public String type;

	public TypeDecompositionTemplate(DataContext context, String type) {
		this.context = context;
		this.type = type;
	}
}