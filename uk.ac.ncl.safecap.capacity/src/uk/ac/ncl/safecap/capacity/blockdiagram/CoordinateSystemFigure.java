package uk.ac.ncl.safecap.capacity.blockdiagram;

import org.eclipse.draw2d.RectangleFigure;

public abstract class CoordinateSystemFigure extends RectangleFigure {
	private final IZoomInfo _zoomer;

	public CoordinateSystemFigure(IZoomInfo zoomer) {
		_zoomer = zoomer;
		useLocalCoordinates();
	}

	public int toGraphX(double modelX) {
		// return _zoomer.convertToGraphX(modelX);
		return (int) Math.round(modelX / _zoomer.getXRatio());
	}

	public int toGraphY(double modelY) {
		// return _zoomer.convertToGraphY(modelY);
		return (int) Math.round(modelY / _zoomer.getYRatio());
	}

	protected double getXRatio() {
		return _zoomer.getXRatio();
	}

	protected double getYRatio() {
		return _zoomer.getYRatio();
	}
}
