package uk.ac.ncl.safecap.common.resources;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class ImageRegister {
	public static final String ICON_SECTION = "icon_section";
	public static final String ICON_JUNCTION = "icon_junction";
	public static final String ICON_LINE = "icon_line";
	public static final String ICON_ROUTE = "icon_route";
	public static final String ICON_ROUTE_LEFT = "icon_route_left";
	public static final String ICON_ROUTE_RIGHT = "icon_route_right";
	public static final String ICON_AMBIT = "icon_ambit";
	public static final String ICON_POINT = "icon_point";
	public static final String ICON_ROUTES = "icon_routes";
	public static final String ICON_LINES = "icon_lines";
	public static final String ICON_MODEL = "icon_model";
	public static final String ICON_SUBROUTE = "icon_subroute";
	public static final String ICON_SUBOVERLAP = "icon_suboverlap";

	public static final String ICON_COLLECTION = "icon_collection";
	public static final String ICON_PROJECT = "icon_project";
	public static final String ICON_SCHEMA = "icon_schema";
	public static final String ICON_VCATALOG = "icon_vcatalog";
	public static final String ICON_XMLDATA = "icon_xmldata";
	public static final String ICON_TRAIN = "icon_train";
	public static final String ICON_METRO = "icon_metro";
	public static final String ICON_CATEGORY = "icon_category";
	public static final String ICON_STATUS = "icon_status";
	public static final String ICON_PENDING = "icon_peding";
	public static final String ICON_ACTION_CHECK = "icon_action_check";
	public static final String ICON_ACTION_CHECK_PROB = "icon_action_check_prob";
	public static final String ICON_ACTION_CHECK_NAT = "icon_action_check_nat";
	public static final String ICON_ACTION_CE = "icon_action_ce";
	public static final String ICON_ACTION_EVAL = "icon_action_eval";
	public static final String ICON_AST = "icon_ast";
	public static final String ICON_VERIFICATION_WORKING = "icon_verification_working";
	public static final String ICON_VERIFICATION_SUCCESS = "icon_verification_success";
	public static final String ICON_VERIFICATION_FAILED = "icon_verification_failed";
	public static final String ICON_VERIFICATION_UNKNOWN = "icon_verification_unknown";
	public static final String ICON_BULLET_BLACK = "icon_bullet_black";
	public static final String ICON_BULLET_BLUE = "icon_bullet_blue";
	public static final String ICON_GRAPH = "icon_graph";

	public static final String ICON_TABLE = "icon_table";
	public static final String ICON_COLUMN = "icon_column";
	public static final String ICON_TEST = "icon_test";

	public static final String ICON_RIGHT_ARROW = "icon_ra";
	public static final String ICON_DOUBLE_RIGHT_ARROW = "icon_dra";
	public static final String ICON_DB_BLACK = "icon_db_black";
	public static final String ICON_REDUCE = "icon_reduce";

	public static void initializeImageRegistry(final ImageRegistry registry) {
		registerImage(registry, ICON_RIGHT_ARROW, "icons/change.png");
		registerImage(registry, ICON_DOUBLE_RIGHT_ARROW, "icons/produce.png");
		registerImage(registry, ICON_DB_BLACK, "icons/database_black.png");
		registerImage(registry, ICON_REDUCE, "icons/reduce.png");

		registerImage(registry, ICON_SECTION, "icons/icon_section.gif");
		registerImage(registry, ICON_JUNCTION, "icons/icon_junction.gif");
		registerImage(registry, ICON_LINE, "icons/icon_line.gif");
		registerImage(registry, ICON_ROUTE, "icons/icon_route.gif");
		registerImage(registry, ICON_ROUTE_LEFT, "icons/icon_route_left.gif");
		registerImage(registry, ICON_ROUTE_RIGHT, "icons/icon_route_right.gif");
		registerImage(registry, ICON_AMBIT, "icons/icon_ambit.gif");
		registerImage(registry, ICON_POINT, "icons/icon_point.gif");
		registerImage(registry, ICON_ROUTES, "icons/icon_routes.gif");
		registerImage(registry, ICON_LINES, "icons/icon_lines.gif");

		registerImage(registry, ICON_SUBOVERLAP, "icons/icon_subroute.gif");
		registerImage(registry, ICON_SUBROUTE, "icons/icon_suboverlap.gif");

		registerImage(registry, ICON_MODEL, "icons/ComponentLeaf.png");
		registerImage(registry, ICON_COLLECTION, "icons/icon_collection.gif");
		registerImage(registry, ICON_PROJECT, "icons/project.gif");
		registerImage(registry, ICON_SCHEMA, "icons/schema.gif");
		registerImage(registry, ICON_VCATALOG, "icons/accept_document.png");
		registerImage(registry, ICON_XMLDATA, "icons/database.png");
		registerImage(registry, ICON_TRAIN, "icons/train.png");
		registerImage(registry, ICON_METRO, "icons/metro.png");
		registerImage(registry, ICON_CATEGORY, "icons/Components.png");
		registerImage(registry, ICON_STATUS, "icons/service_status.png");
		registerImage(registry, ICON_PENDING, "icons/pending.gif");
		registerImage(registry, ICON_ACTION_CHECK, "icons/check_16.png");
		registerImage(registry, ICON_ACTION_CHECK_PROB, "icons/check_b1.png");
		registerImage(registry, ICON_ACTION_CHECK_NAT, "icons/check_b2.png");
		registerImage(registry, ICON_ACTION_CE, "icons/error_checking_16.png");
		registerImage(registry, ICON_ACTION_EVAL, "icons/evaluate_formula_16.png");
		registerImage(registry, ICON_VERIFICATION_WORKING, "icons/working.gif");
		registerImage(registry, ICON_VERIFICATION_SUCCESS, "icons/success.gif");
		registerImage(registry, ICON_VERIFICATION_FAILED, "icons/failed.gif");
		registerImage(registry, ICON_VERIFICATION_UNKNOWN, "icons/possibly.gif");
		registerImage(registry, ICON_AST, "icons/tree_list_16.png");
		registerImage(registry, ICON_BULLET_BLACK, "icons/bullet_black.png");
		registerImage(registry, ICON_BULLET_BLUE, "icons/bullet_blue.png");
		registerImage(registry, ICON_TABLE, "icons/table.png");
		registerImage(registry, ICON_COLUMN, "icons/column.png");
		registerImage(registry, ICON_TEST, "icons/test.png");
		registerImage(registry, ICON_GRAPH, "icons/graph16.png");
	}

	private static void registerImage(final ImageRegistry registry, final String key, final String path) {
		final ImageDescriptor desc = getImageDescriptor(path);
		registry.put(key, desc);
	}

	public static ImageDescriptor getImageDescriptor(final String path) {
		return getImageDescriptor(CommonPlugin.PLUGIN_ID, path);
	}

	private static ImageDescriptor getImageDescriptor(final String pluginID, final String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin(pluginID, path);
	}

	public static Image getImage(final String key) {
		final ImageRegistry registry = CommonPlugin.getDefault().getImageRegistry();
		return registry.get(key);
	}

	public static ImageDescriptor getDescriptor(final String key) {
		final ImageRegistry registry = CommonPlugin.getDefault().getImageRegistry();
		return registry.getDescriptor(key);
	}

}
