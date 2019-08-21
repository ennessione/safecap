package uk.ac.ncl.safecap.diagram.misc.visual;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

public class TrackColours {
	public static final Color TerminalNode;
	public static final Color BoundaryNode;
	public static final Color NormalNode;
	public static final Color InvalidNode;
	public static final Color PointNode;
	public static final Color SegmentBase;
	public static final Color SegmentJunction;
	public static final Color SegmentCrossoverA;
	public static final Color SegmentCrossoverB;
	public static final Color LightLightGray;

	public static final Color[] interlocking;

	static {
		final Display display = getDisplay();

		TerminalNode = new Color(display, 125, 125, 245);
		BoundaryNode = new Color(display, 45, 195, 56);
		NormalNode = new Color(display, 0, 0, 0);
		InvalidNode = new Color(display, 255, 50, 50);
		PointNode = new Color(display, 255, 255, 255);
		LightLightGray = new Color(display, 225, 225, 225);
		SegmentBase = new Color(display, 120, 120, 120);
		SegmentJunction = new Color(display, 50, 50, 50);
		SegmentCrossoverA = new Color(display, 50, 100, 50);
		SegmentCrossoverB = new Color(display, 50, 50, 100);

		interlocking = new Color[] { new Color(display, 220, 0, 0), new Color(display, 0, 180, 0), new Color(display, 0, 0, 180),
				new Color(display, 90, 90, 0), new Color(display, 90, 0, 90), new Color(display, 0, 30, 190),
				new Color(display, 110, 0, 100) };

	}

	public static Display getDisplay() {
		if (PlatformUI.isWorkbenchRunning()) {
			return PlatformUI.getWorkbench().getDisplay();
		} else {
			return Display.findDisplay(Thread.currentThread());
		}
	}
}
