package uk.ac.ncl.safecap.scitus.base;

import safecap.schema.Segment;
import uk.ac.ncl.safecap.common.TrainDescriptor;
import uk.ac.ncl.safecap.misc.core.TrainLine;

public interface ITrain {
	TrainLine getLine();

	TrainDescriptor getDescriptor();

	String getName();

	double getTimeExtent();

	double getSpeed();

	double getHead();

	Segment getHeadSegment();

	Segment getTailSegment();

	int getHeadSegmentIndex();

	int getTailSegmentIndex();

	boolean isCompleted();
}
