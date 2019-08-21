package uk.ac.ncl.safecap.replay;

import java.text.DecimalFormat;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

import safecap.HighlightedInterval;
import safecap.Orientation;
import safecap.Project;
import safecap.SafecapFactory;
import safecap.Style;
import safecap.VisualMarker;
import safecap.schema.Segment;
import safecap.trackside.Equipment;
import safecap.trackside.Signal;
import uk.ac.ncl.safecap.capacity.experiment.SignalRun;
import uk.ac.ncl.safecap.capacity.experiment.SimulationExperiment;
import uk.ac.ncl.safecap.diagram.misc.visual.TrackConstants;
import uk.ac.ncl.safecap.misc.core.TrainLine;
import uk.ac.ncl.safecap.scitus.base.Evolution;
import uk.ac.ncl.safecap.scitus.base.ITrain;
import uk.ac.ncl.safecap.scitus.base.Progression;
import uk.ac.ncl.safecap.scitus.base.S1TrainActor;
import uk.ac.ncl.safecap.scitus.base.TrainEvent;
import uk.ac.ncl.safecap.scitus.base.TrainProgression;
import uk.ac.ncl.safecap.scitus.base.World;
import uk.ac.ncl.safecap.scitus.stat.ITrainRun;

public class HighlightTrains {

	private final Project root;
	private final TransactionalEditingDomain _domain;
	private final SimulationExperiment _exp;
	private final double _time;

	public HighlightTrains(Project project, double time, SimulationExperiment exp) {
		_time = time;
		root = project;
		_domain = TransactionUtil.getEditingDomain(root);
		_exp = exp;
	}

	public static void highlight(Project project, double time, SimulationExperiment exp) {
		final HighlightTrains cmd = new HighlightTrains(project, time, exp);
		if (cmd.root != null && cmd._domain != null) {
			cmd.run();
		}
	}

	public void run() {
		try {
			// OperationHistoryFactory.getOperationHistory().execute(getHighlightSegmentsCommand(),
			// null, null);
			getHighlightTrainsCommand().execute(null, null);
		} catch (final Throwable e) {
			e.printStackTrace();
		}
	}

	private AbstractTransactionalCommand getHighlightTrainsCommand() {
		final AbstractTransactionalCommand command = new AbstractTransactionalCommand(_domain, "HighlightTrains", null) {
			private final DecimalFormat format = new DecimalFormat("#.#");

			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
				// clear all segments
				for (final Segment s : root.getSegments()) {
					s.getIntervals().clear();
					s.getMarkers().clear();
				}

				for (final ITrain actor : _exp.getTrains()) {
					final double len = actor.getDescriptor().getLength();
					final ITrainRun data = _exp.getRunData(actor);
					final double[] pos = data.getPosition();
					final double[] speed = data.getSpeed();
					// double dt = data.getDeltaT();
					// int index = (int) Math.round(_time / dt);
					int index = data.timeToIndex(_time);
					if (index >= pos.length) {
						index = pos.length - 1;
					}
					if (index < 0) {
						index = 0;
					}
					final double headPosition = pos[index];
					final double tailPosition = headPosition - len;

					final double speedValue = speed[index];
					// Segment seg = actor.getLine().getSegment(headPosition);
					final String label = format.format(speedValue);
//					String label = format.format(headPosition - actor.getLine().getSegmentOffset(seg));

					final int voffset = _exp.getVOffset(actor);

					highlightInterval(actor.getLine(), headPosition, tailPosition, actor, 1, _exp.getTrainDeviceColor(actor),
							TrackConstants.TRACK_WIDTH + 4, SWT.LINE_SOLID, label, voffset);

					final double brakingDistance = actor.getDescriptor().getSafeBrakingDistance(speedValue);
					if (brakingDistance > 0) {
						highlightInterval(actor.getLine(), headPosition + brakingDistance, headPosition, actor, 2,
								_exp.getTrainDeviceColor(actor), TrackConstants.TRACK_WIDTH, SWT.LINE_DOT, null, voffset);
						highlightMarker(actor.getLine(), headPosition + brakingDistance, actor, 2, _exp.getTrainDeviceColor(actor),
								TrackConstants.TRACK_WIDTH, "BD");
					}
				}

				if (_exp.getWorld() instanceof World) {
					highligh_style_1();
				}

				return CommandResult.newOKCommandResult();
			}
		};

		return command;
	}

	private void highligh_style_1() {
		final Evolution evolution = _exp.getEvolution(_time);
		for (final Progression progression : evolution.getProgressions()) {
			if (progression instanceof TrainProgression) {
				final TrainProgression trainProgression = (TrainProgression) progression;
				final S1TrainActor actor = trainProgression.getTrain();
				final TrainEvent ev = trainProgression.getMRT();
				// TrainLine line = actor.getLine();
				final String label = ev.getCause().toString();

				final ITrainRun data = _exp.getRunData(actor);
				final double[] pos = data.getPosition();
				// double[] speed = data.getSpeed();
				// double dt = data.getDeltaT();
				// int index = (int) Math.round(_time / dt);
				int index = data.timeToIndex(_time);
				if (index >= pos.length) {
					index = pos.length - 1;
				}
				if (index < 0) {
					index = 0;
					// double headPosition = pos[index];
				}

				// Segment seg = line.getSegment(headPosition);
				if (ev.isForHead()) {
					final double distance = ev.getDistance() + trainProgression.getHeadPosition();
					highlightMarker(actor.getLine(), distance, actor, 2, _exp.getTrainDeviceColor(actor), TrackConstants.TRACK_WIDTH + 5,
							"H:" + label);
				} else {
					final double distance = ev.getDistance() + trainProgression.getHeadPosition() + actor.getDescriptor().getLength();
					highlightMarker(actor.getLine(), distance, actor, 2, _exp.getTrainDeviceColor(actor), TrackConstants.TRACK_WIDTH + 5,
							"T");
				}
			}
		}

		for (final Equipment eq : root.getEquipment()) {
			if (eq instanceof Signal) {
				final Signal signal = (Signal) eq;
				final SignalRun data = _exp.getSignalData(signal);
				final int state = data.getState(_time);
				final int aspects = data.getMaxAspects(_time);
				Style style;
				if (signal.getStyle() == null) {
					style = SafecapFactory.eINSTANCE.createStyle();
					signal.setStyle(style);
				} else {
					style = signal.getStyle();
				}
				if (!data.getRoutes().isEmpty()) {
					style.setBackground(getColor(state, aspects));
				} else {
					style.setBackground(ColorConstants.red);
				}
			}
		}
	}

	private void highlightInterval(TrainLine line, double headPosition, double tailPosition, Object owner, int ownerIndex,
			Color foregroundColor, int linewidth, int style, String label, int voffset) {
		if (tailPosition < 0) {
			tailPosition = 0;
		}

		final int headIndex = line.getSegmentIndex(headPosition);
		final int tailIndex = line.getSegmentIndex(tailPosition);

		double headRatio = line.getPositionInSegment(headPosition) / line.getPath().get(headIndex).getLength();
		double tailRatio = line.getPositionInSegment(tailPosition) / line.getPath().get(tailIndex).getLength();
		final boolean leftToRight = line.getSchemaLine().getOrientation() == Orientation.LEFT;
		if (!leftToRight) {
			headRatio = 1 - headRatio;
			tailRatio = 1 - tailRatio;
		}

		if (headRatio < 0) {
			headRatio = 0;
		}
		if (headRatio > 1) {
			headRatio = 1;
		}
		if (tailRatio < 0) {
			tailRatio = 0;
		}
		if (tailRatio > 1) {
			tailRatio = 1;
		}

		for (int curIndex = tailIndex; curIndex <= headIndex; curIndex++) {
			double startRatio = 0, endRatio = 1;

			if (leftToRight) {
				if (curIndex == tailIndex) {
					startRatio = tailRatio;
				}
				if (curIndex == headIndex) {
					endRatio = headRatio;
				}
			} else {
				if (curIndex == tailIndex) {
					endRatio = tailRatio;
				}
				if (curIndex == headIndex) {
					startRatio = headRatio;
				}
			}

			final Segment curSegment = line.getPath().get(curIndex);
			final EList<HighlightedInterval> intervals = curSegment.getIntervals();
			final HighlightedInterval interval = getInterval(intervals, owner, ownerIndex);
			Style s;
			if (interval.getStyle() != null) {
				s = interval.getStyle();
			} else {
				s = SafecapFactory.eINSTANCE.createStyle();
				interval.setStyle(s);
			}
			interval.setFrom(startRatio);
			interval.setTo(endRatio);
			interval.setVoffset(voffset);
			if (label != null) {
				if (curIndex == headIndex) {
					interval.setLabel(label + (!leftToRight ? "_" : ""));
				}
			}
			s.setForeground(foregroundColor);
			s.setLinewidth(linewidth);
			s.setLinestyle(style);
		}
	}

	private void highlightMarker(TrainLine line, double position, Object owner, int ownerIndex, Color foregroundColor, int linewidth,
			String label) {
		if (position < 0) {
			position = 0;
		}

		final int index = line.getSegmentIndex(position);
		double ratio = line.getPositionInSegment(position) / line.getPath().get(index).getLength();
		final boolean leftToRight = line.getSchemaLine().getOrientation() == Orientation.LEFT;
		if (!leftToRight) {
			ratio = 1 - ratio;
		}

		if (ratio < 0) {
			ratio = 0;
		}
		if (ratio > 1) {
			ratio = 1;
		}

		final Segment curSegment = line.getPath().get(index);
		final EList<VisualMarker> markers = curSegment.getMarkers();
		final VisualMarker marker = getMarker(markers, owner, ownerIndex);
		Style s;
		if (marker.getStyle() != null) {
			s = marker.getStyle();
		} else {
			s = SafecapFactory.eINSTANCE.createStyle();
			marker.setStyle(s);
		}
		marker.setPosition(ratio);
		marker.setLabel(label);
		s.setForeground(foregroundColor);
		s.setLinewidth(linewidth);
		s.setLinestyle(SWT.LINE_SOLID);
	}

	private Color getColor(int aspect, int aspects) {
		if (aspect == -1) {
			return ColorConstants.gray;
		} else if (aspect == 0) {
			return ColorConstants.red;
		} else if (aspect == aspects - 1) {
			return ColorConstants.lightGreen;
		} else {
			return ColorConstants.yellow;
		}
	}

	private static HighlightedInterval getInterval(EList<HighlightedInterval> intervals, Object owner, int ownerIndex) {
		for (final HighlightedInterval interval : intervals) {
			if (interval.getOwner() == owner && interval.getIndex() == ownerIndex) {
				return interval;
			}
		}
		final HighlightedInterval interval = SafecapFactory.eINSTANCE.createHighlightedInterval();
		interval.setOwner(owner);
		interval.setIndex(ownerIndex);
		intervals.add(interval);
		return interval;
	}

	private static VisualMarker getMarker(EList<VisualMarker> markers, Object owner, int ownerIndex) {
		for (final VisualMarker marker : markers) {
			if (marker.getOwner() == owner && marker.getIndex() == ownerIndex) {
				return marker;
			}
		}
		final VisualMarker interval = SafecapFactory.eINSTANCE.createVisualMarker();
		interval.setOwner(owner);
		interval.setIndex(ownerIndex);
		markers.add(interval);
		return interval;
	}
}
