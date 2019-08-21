package uk.ac.ncl.safecap.xdata.dataframe;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import uk.ac.ncl.safecap.xmldata.IConceptMap;
import uk.ac.ncl.safecap.xmldata.IXFunction;
import uk.ac.ncl.safecap.xmldata.base.ISDADataProvider;
import uk.ac.ncl.safecap.xmldata.types.XEnumType;
import uk.ac.ncl.safecap.xmldata.types.XFunctionBasic;

public class RowDataProvider implements ISDADataProvider {
	private Map<String, XFunctionBasic> functions;
	
	private SDADataFrame frame;
	private DataFrameRow row;
	
	public RowDataProvider(SDADataFrame frame, DataFrameRow row) {
		super();
		this.frame = frame;
		this.row = row;
		functions = new HashMap<>();
		populateFunctions();
	}

	private void populateFunctions() {
		for(int i = 1; i < frame.getColumnNames().size(); i++) {
			String s = frame.getColumnNames().get(i);
			XFunctionBasic f = (XFunctionBasic) frame.getContext().getFunction(s);
			if (f != null) {
				XFunctionBasic c = f.cloneEmpty();
				List<Object> values = SDADataFrame.getValue(row.getDomain(), f);
				if (values != null)
					c.setMapping(row.getDomain(), values);
				functions.put(s, c);
			}
		}
	}

	@Override
	public String getName() {
		return "row<" + row.getDomain().toString() +">";
	}

	@Override
	public Set<String> getEnumMembers(XEnumType type) {
		return Collections.emptySet();
	}

	@Override
	public Set<String> getEnumMembers(String type) {
		throw new IllegalArgumentException();
	}

	@Override
	public XEnumType getEnum(String id) {
		return null;
	}

	@Override
	public Set<String> getEnums() {
		return Collections.emptySet();
	}

	@Override
	public Collection<String> getFunctionIds() {
		return functions.keySet();
	}

	@Override
	public IXFunction getFunction(String name) {
		return functions.get(name);
	}

	@Override
	public String getConceptMapSource() {
		return null;
	}

	@Override
	public IConceptMap getConceptMap() {
		return null;
	}

	@Override
	public Collection<String> getExternalMap(String element) {
		return Collections.emptySet();
	}

	@Override
	public Collection<String> getExternalFileLocations() {
		return Collections.emptySet();
	}
	
	
}
