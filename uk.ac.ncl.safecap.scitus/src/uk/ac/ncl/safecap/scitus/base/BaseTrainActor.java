package uk.ac.ncl.safecap.scitus.base;

import safecap.schema.Segment;
import uk.ac.ncl.safecap.common.TrainDescriptor;
import uk.ac.ncl.safecap.misc.core.Delta;
import uk.ac.ncl.safecap.misc.core.TrainLine;

public class BaseTrainActor implements ITrain {
	protected TrainDescriptor descr;
	protected TrainLine line;

	protected double head; // position of the train head, as offset from line start, meters
	protected double speed; // current train speed, m/s
	protected double acceleration; // current acceleration

	protected Segment tail_segment;
	protected Segment head_segment;
	protected int tail_segment_index;
	protected int head_segment_index;

	public BaseTrainActor(TrainLine line, TrainDescriptor descr) {
		this.line = line;
		this.descr = descr;
		tail_segment = _getTailSegment();
		head_segment = _getHeadSegment();

		tail_segment_index = _getTailSegmentIndex();
		head_segment_index = _getHeadSegmentIndex();
	}

	@Override
	public boolean isCompleted() {
		final double pos = getTail();
		final double linelen = getLine().getLineLength();
		if (pos >= linelen) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public TrainDescriptor getDescriptor() {
		return descr;
	}

	@Override
	public double getSpeed() {
		return speed;
	}

	@Override
	public double getHead() {
		return head;
	}

	public double getTail() {
		return head - descr.getLength();
	}

	@Override
	public TrainLine getLine() {
		return line;
	}

	public void move(double distance) {
		if (distance != 0d) {
			head += distance;
			head = Delta.roundToDelta(head); // round position
			head_segment_index = _getHeadSegmentIndex();
			tail_segment_index = _getTailSegmentIndex();
			head_segment = _getHeadSegment();
			tail_segment = _getTailSegment();
		}
	}

	private Segment _getHeadSegment() {
		return line.getPath().get(head_segment_index);
	}

	private Segment _getTailSegment() {
		return line.getPath().get(tail_segment_index);
	}

	private int _getHeadSegmentIndex() {
		return line.getSegmentIndex(head);
	}

	private int _getTailSegmentIndex() {
		int si = line.getSegmentIndex(getTail());
		final Segment s = line.getPath().get(si);

		final double distToEnd = line.getSegmentOffset(s) + s.getLength() - getTail();
		if (distToEnd == 0) {
			if (si + 1 < line.getPath().size()) {
				si++;
				// System.out.println("Tail shift");
			}
		}

		return si;
	}

	@Override
	public Segment getHeadSegment() {
		return head_segment;
	}

	@Override
	public Segment getTailSegment() {
		return tail_segment;
	}

	@Override
	public int getHeadSegmentIndex() {
		return head_segment_index;
	}

	@Override
	public int getTailSegmentIndex() {
		return tail_segment_index;
	}

	public double getAcceleration() {
		return acceleration;
	}

	@Override
	public String getName() {
		return descr.toString() + "@" + line.getSchemaLine().getLabel();
	}

	@Override
	public String toString() {
		return getName() + " " + Delta.round2(getTail()) + "m(" + tail_segment.getLabel() + ")/" + Delta.round2(getHead()) + "m("
				+ head_segment.getLabel() + "):" + Delta.round2(speed) + "/" + Delta.round2(acceleration);
	}

	@Override
	public double getTimeExtent() {
		throw new RuntimeException("Unimplemented");
	}

}
