package uk.ac.ncl.safecap.gui.infographs.dependencies;

/**
 * Listens to zoom level changes.
 * 
 * @author Eric Bordeau
 */
public interface ZoomListener {

	/**
	 * Called whenever the ZoomManager's zoom level changes.
	 * 
	 * @param zoom the new zoom level.
	 */
	void zoomChanged(double zoom);

}
