package uk.ac.ncl.safecap.diagram.heightmap;

import java.io.IOException;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.UpdateManager;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;

import safecap.schema.Node;
import safecap.schema.SchemaPackage;

public class BoundaryPointFigure extends PointFigure {
	public BoundaryPointFigure(Node node) {
		super(node, ColorConstants.darkGreen);
	}

	@Override
	protected void onDragComplete() {
		super.onDragComplete();
		final Node point = (Node) getModel();
		final GraphFigure parent = (GraphFigure) getParent();
		final Point p = getLocation().getCopy();
		final Dimension dims = getSize();
		p.x += dims.width / 2;
		p.y += dims.height / 2;
		final int y = parent.toModelY(p.y);

		final TransactionalEditingDomain dom = TransactionUtil.getEditingDomain(point);
		if (dom != null) {
			dom.getCommandStack().execute(new SetCommand(dom, point, SchemaPackage.eINSTANCE.getNode_Altitude(), y));
			save(point.eResource());
		} else {
			point.setAltitude(y);
			save(point.eResource());
		}
		onModelChanged();
	}

	@Override
	public void onModelChanged() {
		super.onModelChanged();
		final Node point = (Node) getModel();
		final GraphFigure parent = (GraphFigure) getParent();
		final Point p = new Point(parent.getNodeModelPosition(point), point.getAltitude());
		parent.toGraphPosition(p);
		final Dimension dims = getSize();
		p.x -= dims.width / 2;
		p.y -= dims.height / 2;
		setLocation(p);
		final LayoutManager layoutMgr = getParent().getLayoutManager();
		layoutMgr.setConstraint(this, getBounds());
		final UpdateManager updateMgr = getUpdateManager();
		updateMgr.addDirtyRegion(getParent(), getParent().getBounds());
	}

	@Override
	protected void checkBounds(Rectangle bounds) {
		super.checkBounds(bounds);
		final Dimension parentSize = getParent().getSize();
		final Rectangle currentBounds = getBounds();
		bounds.x = currentBounds.x;
		if (bounds.y < 0) {
			bounds.y = 0;
		}
		if (bounds.y + bounds.height > parentSize.height) {
			bounds.y = parentSize.height - bounds.height;
		}
	}

	private static void save(Resource res) {
		try {
			res.save(null);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

//    @Override
//    public Point getModelPosition()
//    {
//        Node node = (Node) getModel();
//        GraphFigure parent = (GraphFigure) getParent();
//        return new Point(parent.getNodePosition(node), node.getAltitude());
//    }
}