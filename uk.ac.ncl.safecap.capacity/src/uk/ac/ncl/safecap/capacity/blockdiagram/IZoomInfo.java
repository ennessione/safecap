package uk.ac.ncl.safecap.capacity.blockdiagram;

public interface IZoomInfo {
	double getXRatio();

	double getYRatio();

	int convertToGraphX(double x);

	int convertToGraphY(double y);
}
