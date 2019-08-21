package uk.ac.ncl.safecap.xdata.base.tablesmodel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import uk.ac.ncl.safecap.xmldata.IConceptMap;

public class CTConceptMap implements IConceptMap {
	private final TablesModel model;
	private final Map<String, String> conceptToExternal;
	private final List<String> conceptsAll;

	public CTConceptMap(TablesModel model) {
		this.model = model;
		conceptToExternal = new HashMap<>();
		conceptsAll = new ArrayList<>();
		build();
	}

	@Override
	public List<String> getConceptsAll() {
		return conceptsAll;
	}

	public Collection<String> getMappedConcepts() {
		return conceptToExternal.keySet();
	}

	@Override
	public String getConceptProvenance(String concept) {
		return conceptToExternal.get(concept);
	}

	@Override
	public TablesModel getTreeModel() {
		return model;
	}

	private void build() {
		for (final TableModel tmodel : model.getTables()) {
			final List<String> newpath = new ArrayList<>(5);
			newpath.add(tmodel.getName());
			build(tmodel, newpath);
		}
	}

	private void build(TableModel model, List<String> path) {
		for (final ITablePart part : model.getParts()) {
			if (part instanceof TableModel) {
				final TableModel tmodel = (TableModel) part;
				final List<String> newpath = new ArrayList<>(path);
				newpath.add(tmodel.getName().toUpperCase());
				build(tmodel, newpath);
			} else if (part instanceof ColumnModel) {
				final ColumnModel cmodel = (ColumnModel) part;
				build(cmodel, path);
			}
		}

	}

	private void build(ColumnModel cmodel, List<String> path) {
		final String tpath = pathToString(path) + ">" + cmodel.getName();
		for (final String s : cmodel.getConceptPaths()) {
			String zpath = tpath;
			if (cmodel.getConceptPaths().size() > 1) {
				final int index = s.lastIndexOf('/');
				String suffix = "";
				if (index > 0) {
					suffix = "(" + s.substring(index + 1) + ")";
				}
				zpath = tpath + suffix;
			}
			conceptToExternal.put(s, zpath);

			if (!conceptsAll.contains(zpath)) {
				conceptsAll.add(zpath);
			}
		}
	}

	private String pathToString(List<String> path) {
		final StringBuilder sb = new StringBuilder();

		for (int i = 0; i < path.size(); i++) {
			if (i > 0) {
				sb.append(">");
			}

			sb.append(path.get(i).toUpperCase());
		}

		return sb.toString();
	}

	@Override
	public String getProvenanceConcept(String source) {
		for (final Entry<String, String> entry : conceptToExternal.entrySet()) {
			if (entry.getValue().equals(source)) {
				return entry.getKey();
			}
		}

		conceptToExternal.values();

		return null;
	}
}
