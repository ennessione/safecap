package uk.ac.ncl.safecap.scitus.adaptive;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import safecap.model.Point;

public class AdaptiveControlState implements Serializable {
	private static final long serialVersionUID = -6392671673335383038L;
	private Map<Point, ScenarioPoint> scenarioPoints;

	public AdaptiveControlState() {

	}

	public Map<Point, ScenarioPoint> getScenarioPoints() {
		return scenarioPoints;
	}

	public void setScenarioPoints(Map<Point, ScenarioPoint> scnearioPoints) {
		scenarioPoints = scnearioPoints;
	}

	@Override
	public String toString() {
		return scenarioPoints.toString();
	}

	public AdaptiveControlState copy() {
		final AdaptiveControlState c = new AdaptiveControlState();

		if (scenarioPoints != null) {
			final Map<Point, ScenarioPoint> sc = new HashMap<>(scenarioPoints.size());
//			for(Point p: scenarioPoints.keySet()) {
//
//			}
			c.setScenarioPoints(sc);
		}

		return c;

	}

}
