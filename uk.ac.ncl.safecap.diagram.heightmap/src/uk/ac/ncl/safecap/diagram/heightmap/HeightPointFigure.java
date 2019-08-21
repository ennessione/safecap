package uk.ac.ncl.safecap.diagram.heightmap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.UpdateManager;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;

import safecap.schema.HeightPoint;
import safecap.schema.SchemaPackage;
import safecap.schema.Segment;

public class HeightPointFigure extends PointFigure {
	public HeightPointFigure(HeightPoint point) {
		super(point, ColorConstants.darkBlue);
	}

	@Override
	protected void onDragComplete() {
		super.onDragComplete();
		final HeightPoint point = (HeightPoint) getModel();
		final GraphFigure parent = (GraphFigure) getParent();
		final Point p = getLocation().getCopy();
		final Dimension dims = getSize();
		p.x += dims.width / 2;
		p.y += dims.height / 2;
		parent.toModelPosition(p);

		final Segment segment = (Segment) point.eContainer().eContainer();
		p.x -= parent.getNodeModelPosition(segment.getFrom());

		final TransactionalEditingDomain dom = TransactionUtil.getEditingDomain(point);
		if (dom != null) {
			final List<Command> list = new ArrayList<>();
			list.add(new SetCommand(dom, point, SchemaPackage.eINSTANCE.getHeightPoint_Position(), p.x));
			list.add(new SetCommand(dom, point, SchemaPackage.eINSTANCE.getHeightPoint_Altitude(), p.y));
			dom.getCommandStack().execute(new CompoundCommand(list));
			save(point.eResource());
		} else {
			point.setPosition(p.x);
			point.setAltitude(p.y);
			save(point.eResource());
		}
		;
		onModelChanged();
	}

	@Override
	public void onModelChanged() {
		super.onModelChanged();
		final HeightPoint point = (HeightPoint) getModel();
		final GraphFigure parent = (GraphFigure) getParent();
		final Point p = new Point(point.getPosition(), point.getAltitude());

		final Segment segment = (Segment) point.eContainer().eContainer();
		p.x += parent.getNodeModelPosition(segment.getFrom());

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
		final HeightPoint point = (HeightPoint) getModel();
		final Segment segment = (Segment) point.eContainer().eContainer();
		final GraphFigure parent = (GraphFigure) getParent();
		final PointFigure nodeFigLeft = parent.getFigure(segment.getFrom()), nodeFigRight = parent.getFigure(segment.getTo());

		if (bounds.x < nodeFigLeft.getBounds().x) {
			bounds.x = nodeFigLeft.getBounds().x;
		}
		if (bounds.x + bounds.width > nodeFigRight.getBounds().right()) {
			bounds.x = nodeFigRight.getBounds().right() - bounds.width;
		}
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
//        HeightPoint point = (HeightPoint) getModel();
//        return new Point(point.getPosition(), point.getAltitude());
//    }
}
