package uk.ac.ncl.safecap.misc.core;

import java.util.Comparator;

import safecap.trackside.Equipment;

public class EquipmentPosition {
	public Equipment equipment;
	public double distance;

	public EquipmentPosition(Equipment equipment, double distance) {
		this.equipment = equipment;
		this.distance = distance;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public double getDistance() {
		return distance;
	}

	@Override
	public String toString() {
		return equipment.toString() + "@" + Delta.round2(distance);
	}

	public static Comparator<EquipmentPosition> getDistanceComparator() {
		return new Comparator<EquipmentPosition>() {
			@Override
			public int compare(EquipmentPosition o1, EquipmentPosition o2) {
				if (o1.distance < o2.distance) {
					return -1;
				} else if (o1.distance > o2.distance) {
					return 1;
				}
				return 0;
			}
		};
	}
}
