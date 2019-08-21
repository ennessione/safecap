package uk.ac.ncl.safecap.misc.core;

import java.util.HashMap;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;

import safecap.Project;
import safecap.TransientValues;

public class ProjectTransient {
	public static final String SEGMENT_AMBIT_MAP = "segment.ambit,map";
	public static final String SUBROUTE_GRAPH = "subroute.graph";
	public static final String SUBROUTE_SET = "subroute.set";
	public static final String AMBIT_COMPOSED_MAP = "ambit.composed.map";
	public static final String SUBROUTE_RENAMED_MAP = "subroute.renamed.map";

	public static boolean getSchemaFlag(Project schema, String flag) {
		final Object z = getValue(schema, SafecapConstants.EXT_SCHEMA_FLAG + "/" + flag);
		if (z instanceof Boolean) {
			return (Boolean) z;
		} else {
			return false;
		}
	}

	public static void setSchemaFlag(Project schema, String flag, boolean value) {
		setValueX(schema, SafecapConstants.EXT_SCHEMA_FLAG + "/" + flag, value);
	}

	public static void resetProjectLevel(Project root) {
		if (root.getRuntimeAttributes() != null) {
			root.getRuntimeAttributes().clear();
		}
	}

	public static synchronized void setValue(final TransientValues root, String key, Object value) {
		if (root.getRuntimeAttributes() == null) {

			try {
				final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(root);
				if (domain instanceof InternalTransactionalEditingDomain) {
					final InternalTransactionalEditingDomain idomain = (InternalTransactionalEditingDomain) domain;
					if (idomain.getActiveTransaction() != null) {
						root.setRuntimeAttributes(new HashMap<String, Object>(20));
					}
				}
				if (!domain.isReadOnly(root.eResource())) {
					domain.getCommandStack().execute(new RecordingCommand(domain) {

						@Override
						protected void doExecute() {
							root.setRuntimeAttributes(new HashMap<String, Object>(20));
						}
					});
				}
			} catch (final Throwable e) {
				// e.printStackTrace();
			}
		}

		if (root.getRuntimeAttributes() != null) {
			root.getRuntimeAttributes().put(key, value);
		}
	}

	public static synchronized void setValueX(final TransientValues root, String key, Object value) {
		if (root.getRuntimeAttributes() == null) {
			root.setRuntimeAttributes(new HashMap<String, Object>(20));
		}

		root.getRuntimeAttributes().put(key, value);
	}

	public static Object getValue(TransientValues object, String key) {
		if (object != null && object.getRuntimeAttributes() != null) {
			return object.getRuntimeAttributes().get(key);
		} else {
			return null;
		}

	}

	public static void init(Project root) {
		AmbitUtil.getSegmentAmbitMap(root);
		SubRouteUtil.getSubRouteGraph(root);
		SubRouteUtil.getSubRoutes(root);
		AmbitUtil.getAmbitToComposedMap(root);
		SubRouteUtil.getSubRouteRenameMap(root);
	}

}
