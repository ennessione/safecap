package uk.ac.ncl.safecap.scitus.stat;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SimHistoryRecord implements ITrainRun {
	public static final String DISTANCE_ID = "Distance";
	public static final String SPEED_ID = "Speed";
	public static final String TIME_ID = "Time";
	private final Run2DRecord distanceRecord;
	private final Run2DRecord speedRecord;
	private final Run2DRecord speedRecord2;
	private final Run2DRecord timeRecord;

	private final Map<String, Run2DRecord> records;

	public SimHistoryRecord() {
		records = new HashMap<>();
		distanceRecord = setupRecord(Run2DRecord.DIST, DISTANCE_ID);
		speedRecord = setupRecord(Run2DRecord.MAIN, SPEED_ID);
		speedRecord2 = setupRecord(Run2DRecord.MAINT, SPEED_ID + "(t)");
		timeRecord = setupRecord("", TIME_ID);
		timeRecord.setShow(false);
		distanceRecord.setShow(true);
		distanceRecord.setXAxis(Run2DRecord.XAXIS.TIME);
		speedRecord.setXAxis(Run2DRecord.XAXIS.DISTANCE);
		speedRecord2.setXAxis(Run2DRecord.XAXIS.TIME);
	}

	public Run2DRecord setupRecord(String kind, String name) {
		final Run2DRecord record = new Run2DRecord(kind, name);
		records.put(name, record);
		return record;
	}

	public Run2DRecord setupRecord(String kind, String name, int lineWidth) {
		final Run2DRecord record = new Run2DRecord(kind, name);
		record.setLineWidth(lineWidth);
		records.put(name, record);
		return record;
	}

	public void timestep(double value) {
		timeRecord.insert(value);
	}

	public void insert(String name, double value) {
		final Run2DRecord record = records.get(name);
		if (record != null) {
			record.insert(value);
		}
	}

	public Set<String> getRecords() {
		return records.keySet();
	}

	public Run2DRecord getRecord(String name) {
		return records.get(name);
	}

	public void insertPositionSpeed(double position, double speed) {
		distanceRecord.insert(position);
		speedRecord.insert(speed);
		speedRecord2.insert(speed);
	}

	@Override
	public SimHistoryRecord truncate() {
		for (final Run2DRecord record : records.values()) {
			record.truncate();
		}

		return this;
	}

	@Override
	public int getDistancePoints() {
		return distanceRecord.getCursor();
	}

	@Override
	public double[] getPosition() {
		return distanceRecord.getData();
	}

	@Override
	public double[] getSpeed() {
		return speedRecord.getData();
	}

	@Override
	public double getIntegralTime(double start_position, double end_position) {
		// TODO
		return 0;
	}

	public void setStartTime(double time) {

	}

	@Override
	public double[] getTime() {
		return timeRecord.getData();
	}

	@Override
	public int timeToIndex(double time) {
		int i;
		for (i = 1; i < timeRecord.getData().length && timeRecord.getData()[i] < time; i++) {
			;
		}
		return i - 1;
	}
}
