package safecap.diagram.providers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.model.IClientSelector;
import org.eclipse.emf.validation.service.IBatchValidator;
import org.eclipse.emf.validation.service.ITraversalStrategy;
import org.eclipse.gmf.runtime.emf.core.util.EMFCoreUtil;
import org.eclipse.gmf.runtime.notation.View;

import safecap.Extensible;
import safecap.Labeled;
import safecap.Project;
import safecap.derived.MergedPoint;
import safecap.diagram.edit.parts.ProjectEditPart;
import safecap.diagram.edit.parts.SegmentEditPart;
import safecap.diagram.part.SafecapDiagramEditorPlugin;
import safecap.diagram.part.SafecapVisualIDRegistry;
import safecap.model.Ambit;
import safecap.model.Point;
import safecap.schema.Node;
import safecap.schema.NodeKind;
import safecap.schema.PointRole;
import safecap.schema.Segment;
import safecap.trackside.Equipment;
import safecap.trackside.LeftSide;
import safecap.trackside.LeftSignal;
import safecap.trackside.RightSide;
import safecap.trackside.RightSignal;
import safecap.trackside.Signal;
import safecap.trackside.SignalType;
import safecap.trackside.Wire;
import uk.ac.ncl.safecap.misc.core.AmbitUtil;
import uk.ac.ncl.safecap.misc.core.DiagramError;
import uk.ac.ncl.safecap.misc.core.EmfUtil;
import uk.ac.ncl.safecap.misc.core.NodeUtil;
import uk.ac.ncl.safecap.misc.core.ScopeB;
import uk.ac.ncl.safecap.misc.core.ScopeC;
import uk.ac.ncl.safecap.misc.core.SegmentUtil;
import uk.ac.ncl.safecap.misc.core.SignalUtil;

/**
 * @generated
 */
public class SafecapValidationProvider {

	/**
	 * @generated
	 */
	private static boolean constraintsActive = false;

	/**
	 * @generated
	 */
	public static boolean shouldConstraintsBePrivate() {
		return false;
	}

	/**
	 * @generated
	 */
	public static void runWithConstraints(TransactionalEditingDomain editingDomain, Runnable operation) {
		final Runnable op = operation;
		final Runnable task = new Runnable() {
			@Override
			public void run() {
				try {
					constraintsActive = true;
					op.run();
				} finally {
					constraintsActive = false;
				}
			}
		};
		if (editingDomain != null) {
			try {
				editingDomain.runExclusive(task);
			} catch (final Exception e) {
				SafecapDiagramEditorPlugin.getInstance().logError("Validation failed", e); //$NON-NLS-1$
			}
		} else {
			task.run();
		}
	}

	/**
	 * @generated
	 */
	static boolean isInDefaultEditorContext(Object object) {
		if (shouldConstraintsBePrivate() && !constraintsActive) {
			return false;
		}
		if (object instanceof View) {
			return constraintsActive && ProjectEditPart.MODEL_ID.equals(SafecapVisualIDRegistry.getModelID((View) object));
		}
		return true;
	}

	/**
	 * @generated
	 */
	public static class DefaultCtx implements IClientSelector {

		/**
		 * @generated
		 */
		@Override
		public boolean selects(Object object) {
			return isInDefaultEditorContext(object);
		}
	}

	/**
	 * @generated
	 */
	public static class Ctx_4001 implements IClientSelector {

		/**
		 * @generated
		 */
		@Override
		public boolean selects(Object object) {
			if (isInDefaultEditorContext(object) && object instanceof View) {
				final int id = SafecapVisualIDRegistry.getVisualID((View) object);
				boolean result = false;
				result = result || id == SegmentEditPart.VISUAL_ID;
				return result;
			}
			return false;
		}
	}

	/**
	 * @generated
	 */
	public static ITraversalStrategy getNotationTraversalStrategy(IBatchValidator validator) {
		return new CtxSwitchStrategy(validator);
	}

	/**
	 * @generated
	 */
	private static class CtxSwitchStrategy implements ITraversalStrategy {

		/**
		 * @generated
		 */
		private final ITraversalStrategy defaultStrategy;

		/**
		 * @generated
		 */
		private int currentSemanticCtxId = -1;

		/**
		 * @generated
		 */
		private boolean ctxChanged = true;

		/**
		 * @generated
		 */
		private EObject currentTarget;

		/**
		 * @generated
		 */
		private EObject preFetchedNextTarget;

		/**
		 * @generated
		 */
		private final int[] contextSwitchingIdentifiers;

		/**
		 * @generated
		 */
		CtxSwitchStrategy(IBatchValidator validator) {
			defaultStrategy = validator.getDefaultTraversalStrategy();
			contextSwitchingIdentifiers = new int[] { SegmentEditPart.VISUAL_ID };
			Arrays.sort(contextSwitchingIdentifiers);
		}

		/**
		 * @generated
		 */
		@Override
		public void elementValidated(EObject element, IStatus status) {
			defaultStrategy.elementValidated(element, status);
		}

		/**
		 * @generated
		 */
		@Override
		public boolean hasNext() {
			return defaultStrategy.hasNext();
		}

		/**
		 * @generated
		 */
		@Override
		public boolean isClientContextChanged() {
			if (preFetchedNextTarget == null) {
				preFetchedNextTarget = next();
				prepareNextClientContext(preFetchedNextTarget);
			}
			return ctxChanged;
		}

		/**
		 * @generated
		 */
		@Override
		public EObject next() {
			EObject nextTarget = preFetchedNextTarget;
			if (nextTarget == null) {
				nextTarget = defaultStrategy.next();
			}
			preFetchedNextTarget = null;
			return currentTarget = nextTarget;
		}

		/**
		 * @generated
		 */
		@Override
		public void startTraversal(Collection traversalRoots, IProgressMonitor monitor) {
			defaultStrategy.startTraversal(traversalRoots, monitor);
		}

		/**
		 * @generated
		 */
		private void prepareNextClientContext(EObject nextTarget) {
			if (nextTarget != null && currentTarget != null) {
				if (nextTarget instanceof View) {
					final int id = SafecapVisualIDRegistry.getVisualID((View) nextTarget);
					final int nextSemanticId = id != -1 && Arrays.binarySearch(contextSwitchingIdentifiers, id) >= 0 ? id : -1;
					if (currentSemanticCtxId != -1 && currentSemanticCtxId != nextSemanticId
							|| nextSemanticId != -1 && nextSemanticId != currentSemanticCtxId) {
						ctxChanged = true;
					}
					currentSemanticCtxId = nextSemanticId;
				} else {
					// context of domain model
					ctxChanged = currentSemanticCtxId != -1;
					currentSemanticCtxId = -1;
				}
			} else {
				ctxChanged = false;
			}
		}
	}

	/**
	 * InvalidNode
	 * 
	 * @generated NOT
	 */
	public static class Adapter1 extends AbstractModelConstraint {

		/**
		 * @generated NOT
		 */
		@Override
		public IStatus validate(IValidationContext ctx) {
			final Node context = (Node) ctx.getTarget();
			if (context.getKind() == NodeKind.INVALID) {
				return ctx.createFailureStatus();
			} else {
				return ctx.createSuccessStatus();
			}
		}
	}

	/**
	 * NoSegmentLabel
	 * 
	 * @generated NOT
	 */
	public static class Adapter2 extends AbstractModelConstraint {

		/**
		 * @generated NOT
		 */
		@Override
		public IStatus validate(IValidationContext ctx) {
			final Segment context = (Segment) ctx.getTarget();
			if (context.getLabel() == null || context.getLabel().length() == 0) {
				return ctx.createFailureStatus();
			} else {
				return ctx.createSuccessStatus();
			}
		}
	}

	/**
	 * LabelNotUnqiue
	 * 
	 * @generated NOT
	 */
	public static class Adapter3 extends AbstractModelConstraint {

		/**
		 * @generated NOT
		 */
		@Override
		public IStatus validate(IValidationContext ctx) {
			final Labeled context = (Labeled) ctx.getTarget();

			if (context.getLabel() == null || context.getLabel().length() == 0) {
				return ctx.createSuccessStatus();
			}

			if (context instanceof Node || context instanceof Signal) {
				if (!ScopeB.isUniqueLabel(context, context.getLabel())) {
					return ctx.createFailureStatus();
				}
			} else if (context instanceof Segment) {
				final Segment segment = (Segment) context;
				if (SegmentUtil.needsLabel(segment)) {
					if (!ScopeC.isUniqueLabel(segment, context.getLabel())) {
						return ctx.createFailureStatus();
					}
				}
			}

			return ctx.createSuccessStatus();
		}
	}

	/**
	 * InvalidOrientation
	 * 
	 * @generated NOT
	 */
	public static class Adapter4 extends AbstractModelConstraint {

		/**
		 * @generated NOT
		 */
		@Override
		public IStatus validate(IValidationContext ctx) {
			return ctx.createSuccessStatus();
			/*
			 * Edge edge = (Edge) ctx.getTarget();
			 * 
			 * if (!(edge.getElement() instanceof Segment)) return
			 * ctx.createSuccessStatus();
			 * 
			 * Segment track = (Segment) edge.getElement();
			 * 
			 * View view = edge.getDiagram();
			 * 
			 * try { if (SafecapDiagramEditorUtil.openDiagram(view.eResource())) {
			 * IEditorPart editorPart =
			 * PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
			 * .getActiveEditor(); if (editorPart instanceof IDiagramWorkbenchPart) {
			 * DiagramEditPart part = ((IDiagramWorkbenchPart)
			 * editorPart).getDiagramEditPart(); IDiagramGraphicalViewer dviewer =
			 * (IDiagramGraphicalViewer) part.getViewer();
			 * 
			 * // look for the edit part of the element String id =
			 * EMFCoreUtil.getProxyID(track); List<?> parts =
			 * dviewer.findEditPartsForElement(id, SegmentEditPart.class);
			 * 
			 * // should be a unique one if (parts.size() == 1) { SegmentEditPart track_part
			 * = (SegmentEditPart) parts.get(0); IFigure figure = track_part.getFigure();
			 * 
			 * if (figure instanceof FSegment) { FSegment tfig = (FSegment) figure; if
			 * (tfig.wrongOrientation()) { //
			 * System.out.println("Invalid orientation for segment " + track.getLabel());
			 * return ctx.createFailureStatus(track.getLabel()); } } }
			 * 
			 * } } } catch (PartInitException e) { e.printStackTrace(); return
			 * ctx.createSuccessStatus(); }
			 * 
			 * return ctx.createSuccessStatus();
			 */

		}
	}

	/**
	 * SignalInJunction
	 * 
	 * @generated NOT
	 */
	public static class Adapter5 extends AbstractModelConstraint {

		/**
		 * @generated NOT
		 */
		@Override
		public IStatus validate(IValidationContext ctx) {
			return ctx.createSuccessStatus();
			/*
			 * Signal context = (Signal) ctx.getTarget(); Project project =
			 * EmfUtil.getProject(context); Segment segment = null;
			 * 
			 * for (Wire wire : project.getWires()) { if (wire.getFrom() == context) {
			 * segment = wire.getTo(); if (SegmentUtil.isJunctionPart(segment)) { Equipment
			 * eqp = wire.getFrom(); if (eqp instanceof Signal) { Signal sig = (Signal) eqp;
			 * if (sig.getType() == SignalType.AUTOMATIC || sig.getType() ==
			 * SignalType.CONTROLLED || sig.getType() == SignalType.SHUNT || sig.getType()
			 * == SignalType.SEMI_AUTOMATIC) return ctx.createFailureStatus(); } } } }
			 * 
			 * return ctx.createSuccessStatus();
			 */
		}
	}

	/**
	 * NoSignalLabel
	 * 
	 * @generated NOT
	 */
	public static class Adapter6 extends AbstractModelConstraint {

		/**
		 * @generated NOT
		 */
		@Override
		public IStatus validate(IValidationContext ctx) {
			final Signal context = (Signal) ctx.getTarget();

			if ((context instanceof LeftSignal || context instanceof RightSignal)
					&& (context.getLabel() == null || context.getLabel().length() == 0)) {
				return ctx.createFailureStatus();
			} else {
				return ctx.createSuccessStatus();
			}
		}
	}

	/**
	 * MultiSignals
	 * 
	 * @generated NOT
	 */
	public static class Adapter7 extends AbstractModelConstraint {

		/**
		 * @generated NOT
		 */
		@Override
		public IStatus validate(IValidationContext ctx) {
			final Signal signal = (Signal) ctx.getTarget();
			final Segment segment = SignalUtil.getSignalSegment(signal);
			if (segment == null) {
				return ctx.createSuccessStatus();
			}
			final Project root = EmfUtil.getProject(segment);

			if (root == null) {
				return ctx.createSuccessStatus();
			}

			final Ambit ambit = AmbitUtil.getSegmentAmbitMap(root).get(segment);
			if (ambit == null) {
				return ctx.createSuccessStatus();
			}
			
			if (AmbitUtil.isComposed(ambit) || AmbitUtil.isDisjoint(ambit)) {
				return ctx.createSuccessStatus();
			}

			final List<Signal> signals = new ArrayList<>();
			for (final Wire wire : root.getWires()) {
				final Equipment eqp = wire.getFrom();
				if (eqp instanceof Signal && sameSide((Signal) eqp, signal) && ambit.getSegments().contains(wire.getTo())) {
					final Signal sig = (Signal) eqp;
					if (sig.getType() == SignalType.AUTOMATIC || sig.getType() == SignalType.CONTROLLED || sig.getType() == SignalType.SHUNT
							|| sig.getType() == SignalType.SEMI_AUTOMATIC) {
						signals.add((Signal) eqp);
					}
				}
			}

			if (signals.size() > 1) {
				return ctx.createFailureStatus();
			} else {
				return ctx.createSuccessStatus();
			}
		}

		private boolean sameSide(Signal a, Signal b) {
			return a instanceof LeftSide && b instanceof LeftSide || a instanceof RightSide && b instanceof RightSide;
		}

	}

	/**
	 * MultiWires
	 * 
	 * @generated NOT
	 */
	public static class Adapter8 extends AbstractModelConstraint {

		/**
		 * @generated NOT
		 */
		@Override
		public IStatus validate(IValidationContext ctx) {
			final Wire wire = (Wire) ctx.getTarget();
			final Project root = EmfUtil.getProject(wire);

			for (final Wire w : root.getWires()) {
				if (w != wire && w.getFrom() == wire.getFrom()) {
					return ctx.createFailureStatus();
				}
			}

			return ctx.createSuccessStatus();
		}
	}

	/**
	 * LabelIsMissing
	 * 
	 * @generated NOT
	 */
	public static class Adapter9 extends AbstractModelConstraint {

		/**
		 * @generated NOT
		 */
		@Override
		public IStatus validate(IValidationContext ctx) {
			final Labeled context = (Labeled) ctx.getTarget();

			if (context.getLabel() == null || context.getLabel().length() == 0) {
				if (context instanceof Node) {
					if (NodeUtil.isPointNode((Node) context)) {
						return ctx.createFailureStatus();
					}
				}
			}

			return ctx.createSuccessStatus();
		}
	}

	/**
	 * InvalidPointRole
	 * 
	 * @generated NOT
	 */
	public static class Adapter10 extends AbstractModelConstraint {

		/**
		 * @generated NOT
		 */
		@Override
		public IStatus validate(IValidationContext ctx) {
			final Segment s = (Segment) ctx.getTarget();
			Node point = null;

			if (NodeUtil.isPointNode(s.getFrom())) {
				point = s.getFrom();
			} else if (NodeUtil.isPointNode(s.getFrom())) {
				point = s.getTo();
			}

			if (point == null) {
				return ctx.createSuccessStatus();
			}

			Segment none = null;
			Segment normal = null;
			Segment reverse = null;

			for (final Segment sg : NodeUtil.getIncomingSegments(point)) {
				if (sg.getPointrole() == PointRole.NORMAL) {
					if (normal != null) {
						return ctx.createFailureStatus();
					}
					normal = sg;
				} else if (sg.getPointrole() == PointRole.NONE) {
					if (none != null) {
						return ctx.createFailureStatus();
					}
					none = sg;
				} else if (sg.getPointrole() == PointRole.REVERSE) {
					if (reverse != null) {
						return ctx.createFailureStatus();
					}
					reverse = sg;
				}
			}

			for (final Segment sg : NodeUtil.getOutgoingSegments(point)) {
				if (sg.getPointrole() == PointRole.NORMAL) {
					if (normal != null) {
						return ctx.createFailureStatus();
					}
					normal = sg;
				} else if (sg.getPointrole() == PointRole.NONE) {
					if (none != null) {
						return ctx.createFailureStatus();
					}
					none = sg;
				} else if (sg.getPointrole() == PointRole.REVERSE) {
					if (reverse != null) {
						return ctx.createFailureStatus();
					}
					reverse = sg;
				}
			}

			if (none == null || normal == null || reverse == null) {
				return ctx.createFailureStatus();
			}

			return ctx.createSuccessStatus();
		}
	}

	/**
	 * Generic (warning)
	 * 
	 * @generated NOT
	 */
	public static class Adapter11 extends AbstractModelConstraint {

		/**
		 * @generated NOT
		 */
		@Override
		public IStatus validate(IValidationContext ctx) {
			final Extensible context = (Extensible) ctx.getTarget();
			final Collection<String> errors = DiagramError.getValidationRecords(context, "warning");

			if (errors == null || errors.size() == 0) {
				return ctx.createSuccessStatus();
			}

			if (context instanceof Ambit) {
				final Ambit ambit = (Ambit) context;
				ctx.addResults(ambit.getSegments());
			} else if (context instanceof MergedPoint) {
				final MergedPoint point = (MergedPoint) context;
				for (final Point p : point.getPoints()) {
					ctx.addResult(p.getNode());
				}
			} else if (context instanceof Point) {
				final Point point = (Point) context;
				ctx.addResult(point.getNode());
			}

			return ctx.createFailureStatus(errors.iterator().next());
		}
	}

	/**
	 * LabelWrong
	 * 
	 * @generated NOT
	 */
	public static class Adapter12 extends AbstractModelConstraint {

		/**
		 * @generated NOT
		 */
		@Override
		public IStatus validate(IValidationContext ctx) {
			return ctx.createSuccessStatus();

			/*
			 * Labeled label = (Labeled) ctx.getTarget();
			 * 
			 * String newText = label.getLabel(); if (newText == null || newText.length() ==
			 * 0) return ctx.createSuccessStatus();
			 * 
			 * if (!Character.isLetter(newText.charAt(0)) &&
			 * !Character.isDigit(newText.charAt(0))) return
			 * ctx.createFailureStatus("invalid lead character '" + newText.charAt(0) +
			 * "'");
			 * 
			 * char prev = 'x'; for (char c : newText.toCharArray()) { if
			 * (!Character.isLetter(c) && !Character.isDigit(c) && c != ' ' && c != '-' && c
			 * != '.' && c != '(' && c != ')' && c != '/' && c != '&') { if (c == '_') { if
			 * (prev == '_') return ctx.createFailureStatus("invalid character '" + c +
			 * "' in " + label); } else { return
			 * ctx.createFailureStatus("invalid character '" + c + "' in " + label); } }
			 * prev = c; }
			 * 
			 * return ctx.createSuccessStatus();
			 */
		}
	}

	/**
	 * No Ambit Label
	 * 
	 * @generated NOT
	 */
	public static class Adapter13 extends AbstractModelConstraint {

		/**
		 * @generated NOT
		 */
		@Override
		public IStatus validate(IValidationContext ctx) {
			final Segment context = (Segment) ctx.getTarget();
			final Ambit ambit = SegmentUtil.getSegmentAmbit(context);
			if (ambit != null && (ambit.getLabel() == null || ambit.getLabel().length() == 0)) {
				return ctx.createFailureStatus();
			} else {
				return ctx.createSuccessStatus();
			}
		}
	}

	/**
	 * No Ambit
	 * 
	 * @generated NOT
	 */
	public static class Adapter14 extends AbstractModelConstraint {

		/**
		 * @generated NOT
		 */
		@Override
		public IStatus validate(IValidationContext ctx) {
			final Segment context = (Segment) ctx.getTarget();
			final Ambit ambit = SegmentUtil.getSegmentAmbit(context);
			if (ambit == null) {
				return ctx.createFailureStatus();
			} else {
				return ctx.createSuccessStatus();
			}
		}
	}

	/**
	 * Ambit name mismatch
	 * 
	 * @generated NOT
	 */
	public static class Adapter15 extends AbstractModelConstraint {

		/**
		 * @generated NOT
		 */
		@Override
		public IStatus validate(IValidationContext ctx) {
			final Segment context = (Segment) ctx.getTarget();
			final Ambit ambit = SegmentUtil.getSegmentAmbit(context);
			if (ambit == null || ambit.getLabel() == null) {
				return ctx.createSuccessStatus();
			}

			String name = null;
			boolean same = true;
			for (final Segment s : ambit.getSegments()) {
				if (name == null) {
					name = s.getLabel();
				} else if (s.getLabel() != null) {
					same = same && s.getLabel().equals(name);
				}
			}

			if (same && !ambit.getLabel().equals(name)) {
				return ctx.createFailureStatus();
			}

			return ctx.createSuccessStatus();
		}
	}

	/**
	 * Generic extensible
	 * 
	 * @generated NOT
	 */
	public static class Adapter16 extends AbstractModelConstraint {

		/**
		 * @generated NOT
		 */
		@Override
		public IStatus validate(IValidationContext ctx) {
			final Extensible context = (Extensible) ctx.getTarget();
			final Collection<String> errors = DiagramError.getValidationRecords(context, "error");

			if (errors == null || errors.size() == 0) {
				return ctx.createSuccessStatus();
			}

			if (context instanceof Ambit) {
				final Ambit ambit = (Ambit) context;
				ctx.addResults(ambit.getSegments());
			} else if (context instanceof MergedPoint) {
				final MergedPoint point = (MergedPoint) context;
				for (final Point p : point.getPoints()) {
					ctx.addResult(p.getNode());
				}
			} else if (context instanceof Point) {
				final Point point = (Point) context;
				ctx.addResult(point.getNode());
			}

			return ctx.createFailureStatus(errors.iterator().next());

		}
	}

	/**
	 * @generated
	 */
	static String formatElement(EObject object) {
		return EMFCoreUtil.getQualifiedName(object, true);
	}

}
