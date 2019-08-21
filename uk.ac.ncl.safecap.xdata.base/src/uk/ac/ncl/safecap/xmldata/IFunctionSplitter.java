package uk.ac.ncl.safecap.xmldata;

import uk.ac.ncl.safecap.xmldata.types.XFunctionBasic;

public interface IFunctionSplitter {
	XFunctionBasic split(XFunctionBasic ff, String suffix);
}
