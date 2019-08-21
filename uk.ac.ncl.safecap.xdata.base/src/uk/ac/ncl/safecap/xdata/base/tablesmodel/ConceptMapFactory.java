package uk.ac.ncl.safecap.xdata.base.tablesmodel;

import uk.ac.ncl.safecap.xmldata.IConceptMap;

public class ConceptMapFactory {

	public static IConceptMap make(String conceptMap) {
		if (conceptMap.equals("UK NR ControlTables")) {
			return new CTConceptMap(TablesModel.buildTablesModel("uknrct.conf"));
		} else if (conceptMap.equals("SafeCap CT")) {
			return new CTConceptMap(TablesModel.buildTablesModel("safecapct.conf"));
		}
		return null;
	}

}
