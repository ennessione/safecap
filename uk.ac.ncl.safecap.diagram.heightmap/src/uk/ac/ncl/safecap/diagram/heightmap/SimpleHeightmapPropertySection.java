package uk.ac.ncl.safecap.diagram.heightmap;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.LayeredPane;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import safecap.diagram.edit.parts.SegmentEditPart;
import safecap.model.Line;

public class SimpleHeightmapPropertySection extends AbstractPropertySection {
	private LightweightSystem _draw2dSystem;
	private Canvas _canvas;
	private EObject _model;
	private LayeredPane _pane;
	private GraphFigure _topLevelFigure;

	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent, aTabbedPropertySheetPage);
		parent.setLayout(new FillLayout());
//        parent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		final ScrolledComposite sc1 = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.NO_FOCUS);

		_canvas = new Canvas(sc1, SWT.DOUBLE_BUFFERED | SWT.NO_FOCUS);
		_canvas.setSize(500, 500);
		sc1.setContent(_canvas);
		_canvas.setBackground(ColorConstants.white);

//        _pane = new LayeredPane();
//        _pane.setOpaque(false);
		_draw2dSystem = new LightweightSystem(_canvas);
		_draw2dSystem.getRootFigure().setOpaque(false);
//        _draw2dSystem.setContents(_pane);
//        _pane.add(new Layer(), "points", 0);

//        Figure root = new Figure();
//        _draw2dSystem.setContents(new Label("blah"));
//        root.add(new Label("blah"));
	}

	@Override
	public void setInput(IWorkbenchPart part, ISelection selection) {
		if (!selection.isEmpty() && selection instanceof IStructuredSelection) {
			final Object firstElement = ((IStructuredSelection) selection).getFirstElement();
			if (firstElement != null) {
				if (firstElement instanceof SegmentEditPart) {
					final SegmentEditPart editPart = (SegmentEditPart) firstElement;
					final Edge edge = (Edge) editPart.getModel();
					_model = edge.getElement();
				} else if (firstElement instanceof Line) {
					_model = (EObject) firstElement;
				}
				if (_topLevelFigure != null) {
					_topLevelFigure.dispose();
				}
				_topLevelFigure = new GraphFigure(_canvas, _model);
				_topLevelFigure.setOpaque(true);
				_draw2dSystem.setContents(_topLevelFigure);

//                topLevelFigure.addPropertyChangeListener(new PropertyChangeListener() {
//                    @Override
//                    public void propertyChange(PropertyChangeEvent arg0)
//                    {
//                        String propName = arg0.getPropertyName();
//                        if (propName.equals("size"))
//                            _canvas.setSize(topLevelFigure.getSize().width, topLevelFigure.getSize().height);
//                    }
//                });

//                Layer newLayer = new Layer();
//                newLayer.setSize(500, 500);
//                newLayer.setBackgroundColor(ColorConstants.black);
//                _pane.add(newLayer, "points", 0);
//                _pane.getLayer("points").add(new Label("Blah" + _segment.getLabel()));
//                _draw2dSystem.getRootFigure().erase();
//                _draw2dSystem.getRootFigure().add(new Label("Blaaaah" + _segment.getLabel()));
//                _draw2dSystem.getRootFigure().add(new Label("AnotherBlaaaah" + _segment.getLabel()));

//                Figure root = new Figure();
//                PointFigure point = new PointFigure(ColorConstants.black);
//                point.setLocation(new Point(100, 100));
//                root.add(point);
//                _draw2dSystem.setContents(root);

//                root.add(new Label("blaaah"));

			}
		}
	}

	@Override
	public void aboutToBeHidden() {
		// TODO Auto-generated method stub
		super.aboutToBeHidden();
	}

	@Override
	public void aboutToBeShown() {
		// TODO Auto-generated method stub
		super.aboutToBeShown();
	}

	@Override
	public void dispose() {
		if (_topLevelFigure != null) {
			_topLevelFigure.stopDrawing();
			_topLevelFigure = null;
		}
		_draw2dSystem = null;
		if (_canvas != null) {
			_canvas.dispose();
			_canvas = null;
		}
		super.dispose();
	}
}
