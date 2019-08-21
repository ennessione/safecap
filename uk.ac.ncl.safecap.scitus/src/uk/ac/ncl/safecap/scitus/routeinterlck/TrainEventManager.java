package uk.ac.ncl.safecap.scitus.routeinterlck;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import uk.ac.ncl.safecap.scitus.base.S1TrainActor;
import uk.ac.ncl.safecap.scitus.base.TrainEvent;

public class TrainEventManager implements Comparator<TrainEvent> {
	private final List<ITrainEventSource> event_sources;

	public TrainEventManager() {
		event_sources = new ArrayList<>();
	}

	public void addEventSource(ITrainEventSource source) {
		assert source != null;
		event_sources.add(source);
	}

//	public TrainEvent getResolvedTrainEvent(TrainActor train)
//	{
//		System.out.println("** " + train.toString());
//
//		List<TrainEvent> events = new ArrayList<TrainEvent>(event_sources.size() + 1);
//
//		TrainEvent resolved = TrainEvent.UNLIMITED;
//		for(ITrainEventSource source: event_sources) {
//			TrainEvent event = source.getTrainEventFor(train);
//			events.add(event);
//			if (event != TrainEvent.UNLIMITED)
//				System.out.println("\t\t" + event.toString() +
//						" :: " + Delta.round2(event.getDecelerationMetric(train)) +
//						" :: " + Delta.round2(event.getLinearDeceleration(train))
//						);
//			if (event.isLess(train, resolved))
//				resolved = event;
//		}
//
//		train.mrt = resolved;
//		train.mrt_list = events;
//
//		return resolved;
//	}

	public List<TrainEvent> getEvents(S1TrainActor train) {
		final List<TrainEvent> events = new ArrayList<>(event_sources.size() + 1);
		for (final ITrainEventSource source : event_sources) {
			final TrainEvent te = source.getTrainEventFor(train);
			if (te != TrainEvent.UNLIMITED) {
				events.add(te);
			}
		}

		return events;
	}

	public TrainEvent getResolvedTrainEvent(S1TrainActor train) {
		final List<TrainEvent> events = getEvents(train);
//		System.out.println("** " + train.toString());
//		for(TrainEvent e: events) {
//			System.out.println("\t\t " + e.toString());
//		}

		TrainEvent resolved;
		if (events.size() > 0) {
			resolved = resolutionByDec(events);
		} else {
			resolved = TrainEvent.UNLIMITED;
		}

		train.mrt = resolved;
		train.mrt_list = events;

		return resolved;
	}

	/**
	 * A resolution technique returning the nearest target
	 * 
	 * @param events
	 * @return
	 */
	private TrainEvent resolutionByDec(List<TrainEvent> events) {

		java.util.Collections.<TrainEvent>sort(events, this);

		if (events.get(0).getSpeedAtEnd() > 0) {
			return events.get(0);
		}

		boolean isStop = true;

		for (final TrainEvent te : events) {
			if (te.getSpeedAtEnd() > 0) {
				isStop = false;
				break;
			}
		}

		if (isStop) {
			return events.get(0);
		}

		int skipZeroes = 0;
		for (final TrainEvent te : events) {
			if (te.getDistance() == 0) {
				skipZeroes++;
			} else {
				break;
			}
		}

		assert skipZeroes < events.size() - 1;

		return events.get(skipZeroes);
	}

	// distance-based target sorting
	@Override
	public int compare(TrainEvent o1, TrainEvent o2) {
		if (o1.getDistance() < o2.getDistance()) {
			return -1;
		} else if (o1.getDistance() == o2.getDistance()) {
			return 0;
		} else {
			return 1;
		}
	}
}
