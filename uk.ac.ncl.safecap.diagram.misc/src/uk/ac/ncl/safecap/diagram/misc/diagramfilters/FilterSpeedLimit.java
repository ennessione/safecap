package uk.ac.ncl.safecap.diagram.misc.diagramfilters;

import uk.ac.ncl.safecap.misc.core.ExtensionUtil;

public class FilterSpeedLimit extends FilterBase {

	@Override
	public String getFlagId() {
		return ExtensionUtil.FILTER_SPEEDLIMIT;
	}

}
