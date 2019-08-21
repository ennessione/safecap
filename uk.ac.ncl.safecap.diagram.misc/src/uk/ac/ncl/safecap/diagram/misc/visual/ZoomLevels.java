package uk.ac.ncl.safecap.diagram.misc.visual;

import org.eclipse.gef.editparts.ZoomManager;

public class ZoomLevels {
	public static double OUTLINE = 0.4;
	public static double COARSE = 0.6;
	public static double FINE = 1.1; // 1.25;
	public static double ULTRA_FINE = 2;

	public static boolean isOutline(ZoomManager manager) {
		return manager != null && manager.getZoom() <= OUTLINE;
	}

	public static boolean isCoarse(ZoomManager manager) {
		return manager != null && manager.getZoom() <= COARSE;
	}

	public static boolean isNormal(ZoomManager manager) {
		return manager == null || manager.getZoom() > COARSE;
	}

	public static boolean isFine(ZoomManager manager) {
		return manager != null && manager.getZoom() >= FINE;
	}

	public static boolean isUltraFine(ZoomManager manager) {
		return manager != null && manager.getZoom() >= ULTRA_FINE;
	}

}
