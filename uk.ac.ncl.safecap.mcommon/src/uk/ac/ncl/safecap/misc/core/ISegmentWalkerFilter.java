package uk.ac.ncl.safecap.misc.core;

import safecap.schema.Segment;

public interface ISegmentWalkerFilter {
	boolean accept(Segment prev, Segment current);

	public static class AcceptAllSegmentWalkerFilter implements ISegmentWalkerFilter {
		public static ISegmentWalkerFilter INSTANCE = new AcceptAllSegmentWalkerFilter();

		private AcceptAllSegmentWalkerFilter() {
		};

		@Override
		public boolean accept(Segment prev, Segment current) {
			return true;
		}

	}
}
