package uk.ac.ncl.safecap.capacity.blockdiagram;

import java.util.Map;

import org.eclipse.swt.widgets.Label;

public class Reporter {
	private double _time;
	private Label _timeLabel, _lineCoord, _ambit, _route;
	private Map<String, Label> _trainPositions;

	public Reporter() {
	}

	public void setGui(Label time, Label lineCoord, Label lblRoute, Label lblAmbit, Map<String, Label> trainPositions) {
		_timeLabel = time;
		_lineCoord = lineCoord;
		_route = lblRoute;
		_ambit = lblAmbit;
		_trainPositions = trainPositions;
	}

	public void reportTime(double time) {
		_time = time;
		if (time >= 0) {
			_timeLabel.setText(Math.round(time) + " sec");
		} else {
			_timeLabel.setText("");
		}
	}

	public void reportLineCoord(double pos) {
		if (pos >= 0) {
			_lineCoord.setText(Math.round(pos) + " m");
		} else {
			_lineCoord.setText("");
		}
	}

	public void reportAmbit(String ambit) {
		if (ambit != null) {
			_ambit.setText(ambit);
		} else {
			_ambit.setText("");
		}
	}

	public void reportRoute(String route) {
		if (route != null) {
			_route.setText(route);
		} else {
			_route.setText("");
		}
	}

	public void reportTrain() {

	}
}
