package uk.ac.ncl.safecap.xmldata;

import java.util.List;

import uk.ac.ncl.safecap.xdata.base.tablesmodel.TablesModel;

public interface IConceptMap {
	
	/**
	 * Returns the external name of the concept
	 * @param concept short name of a data set concept
	 * @return 
	 */
	String getConceptProvenance(String concept);

	/**
	 * Returns the short concept name given its external name
	 * @param mapped external name (i.e., a column in a table)
	 * @return concept short name
	 */	
	String getProvenanceConcept(String source);

	List<String> getConceptsAll();

	TablesModel getTreeModel();
}
