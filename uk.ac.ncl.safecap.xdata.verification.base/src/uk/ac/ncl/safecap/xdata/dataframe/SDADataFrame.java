package uk.ac.ncl.safecap.xdata.dataframe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.ncl.safecap.misc.core.LocatedValue;
import uk.ac.ncl.safecap.xdata.verification.core.SDAContext;
import uk.ac.ncl.safecap.xmldata.IXFunction;
import uk.ac.ncl.safecap.xmldata.ValueList;
import uk.ac.ncl.safecap.xmldata.base.ISDADataProvider;
import uk.ac.ncl.safecap.xmldata.types.XPowType;
import uk.ac.ncl.safecap.xmldata.types.XRelationType;
import uk.ac.ncl.safecap.xmldata.types.XSeqType;
import uk.ac.ncl.safecap.xmldata.types.XType;

public class SDADataFrame extends DataFrame {
	private int maxClusterSize = 50;
	private SDAContext context;
	private XType domainType;

	public SDADataFrame(SDAContext context, String domainType) {
		super();
		this.context = context;
		this.domainType = context.getEnum(domainType);
		if (domainType != null)
			populate();
	}

	public int getMaxClusterSize() {
		return maxClusterSize;
	}

	public void setMaxClusterSize(int maxClusterSize) {
		this.maxClusterSize = maxClusterSize;
	}

	public SDAContext getContext() {
		return context;
	}

	public XType getDomainType() {
		return domainType;
	}

	private void populate() {
		// collect all functions and initialise column clusters
		Map<IXFunction, ColumnCluster> parts = new HashMap<>();
		for (String fn : context.getFunctionIds()) {
			IXFunction f = context.getFunction(fn);
			if (isApplicableFunction(f)) {
				XRelationType rt = (XRelationType) f.getFunctionType();
				if (rt.getDomain() != null && rt.getRange() != null && rt.getDomain().equals(domainType))
					parts.put(f, new ColumnCluster(f.getName()));
			}
		}

		// compute column clusters sizes
		for (String d : context.getEnumMembers(domainType.toString())) {
			for (IXFunction f : parts.keySet()) {
				List<Object> list = f.get(d);
				if (list != null) {
					int rangeSize = getRangeSize(list);
					ColumnCluster cc = parts.get(f);
					cc.extras = Math.min(maxClusterSize, Math.max(cc.extras, rangeSize - 1));
				}
			}
		}		
		
		// sort column clusters
		List<ColumnCluster> preColumns = new ArrayList<>(parts.values());
		preColumns.sort(new Comparator<ColumnCluster>() {
			@Override
			public int compare(ColumnCluster o1, ColumnCluster o2) {
				return o1.name.compareTo(o2.name);
			}
		});

		// define columns
		int offset = 0;
		for (ColumnCluster cc : preColumns) {
			cc.setOffset(offset);
			offset += cc.extras + 1;

			if (cc.extras > 0)
				addColumn(cc.name + ":0");
			else
				addColumn(cc.name);
			for (int i = 0; i < cc.extras; i++)
				addColumn(cc.name + ":" + (i + 1));
		}

		// populate row data
		for (String d : context.getEnumMembers(domainType.toString())) {
			List<Object> row = new ArrayList<>(getColumnNames().size() - 1);
			for(int i = 1; i < getColumnNames().size(); i++)
				row.add("");
			for (IXFunction f : parts.keySet())
				process(d, f, parts.get(f), row);
			super.addRow(new DataFrameRow(d, row));
		}

	}

	private boolean isApplicableFunction(IXFunction f) {
		if (f.getFunctionType() instanceof XRelationType) {
			XRelationType rt = (XRelationType) f.getFunctionType();
			if (rt.getDomain() == null || rt.getRange() == null)
				return false;
			if (f.getName().startsWith("/"))
				return false;
			if (f.getName().contains(".Raw."))
				return false;
			final ISDADataProvider origin = context.getFunctionOrigin(f.getName());
			if (origin != null && origin.getName().startsWith("schema"))
				return false;
			
			return true;
		}
		
		return false;
	}

	private int getRangeSize(List<Object> list) {
		int size = 0;
		for(Object o: list)
			size += getRangeSize(o);
		return size;
	}
	
	private int getRangeSize(Object value) {
		if (isPackedList(value)) {
			ValueList vl = (ValueList) value;
			return vl.size();
		}
		return 1;
	}	
	
	private boolean isPackedList(Object value) {
		if (value instanceof ValueList) {
			ValueList vl = (ValueList) value;
			if (vl.getType() != null && (vl.getType() instanceof XPowType || vl.getType() instanceof XSeqType))
				return true;
			else
				return false;
		}
		return false;
	}		

	private void process(String domain, IXFunction f, ColumnCluster columnCluster, List<Object> row) {
		List<Object> values = getValue(domain, f);
		List<Object> inserted = new ArrayList<>();
		if (values != null) {
			for(int i = 0; i < values.size() && i < columnCluster.extras + 1; i++) {
				Object v = normalise(values.get(i));
				if (isPackedList(v)) {
					ValueList vl = (ValueList) v;
					for(int j = 0; j < vl.size(); j++) 
						inserted.add(normalise(vl.get(i)));
				} else {
					inserted.add(v);
				}
			}
			
			// sort lexicographically 
			Collections.sort(inserted, new Comparator<Object>() {
				@Override
				public int compare(Object o1, Object o2) {
					return o1.toString().compareTo(o2.toString());
				}
			});
			
			for(int i = 0; i < inserted.size(); i++)
				row.set(columnCluster.offset + i, inserted.get(i));
		}
	}

	public static List<Object> getValue(Object domain, IXFunction f) {
		List<Object> values = f.get(domain);
		if (values == null)
			values = f.get(new LocatedValue(domain, 0, 0, 0, 0));
		return values;
	}

	private Object normalise(Object z) {
		if (z instanceof ValueList) {
			ValueList vl = (ValueList) z;
			if (vl.size() == 2 && vl.get(0) instanceof Integer) {
				return vl.get(1);
			} else {
				return vl;
			}
		}
		return z;
	}

	private class ColumnCluster {
		private String name;
		private int extras;
		private int offset;

		ColumnCluster(String name) {
			this.name = name;
			extras = 0;
		}


		public void setOffset(int offset) {
			this.offset = offset;
		}
	}
}
