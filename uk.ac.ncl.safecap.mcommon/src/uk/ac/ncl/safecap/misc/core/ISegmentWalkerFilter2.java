package uk.ac.ncl.safecap.misc.core;

import safecap.schema.Segment;

public interface ISegmentWalkerFilter2 {
	boolean accept(SegmentPath path, Segment current);

	public static class AcceptAllSegmentWalkerFilter implements ISegmentWalkerFilter2 {
		public static ISegmentWalkerFilter2 INSTANCE = new AcceptAllSegmentWalkerFilter();

		private AcceptAllSegmentWalkerFilter() {
		};

		@Override
		public boolean accept(SegmentPath path, Segment current) {
			return true;
		}

	}
}
