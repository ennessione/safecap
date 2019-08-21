package uk.ac.ncl.safecap.gui.trainconfig;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

import servicepattern.Library;
import servicepattern.Pattern;

public class ServicePatternUtil {
	/**
	 * Returns a color of a given service pattern
	 * 
	 * @return a non-null RGB object. Returns black color in case the pattern color
	 *         is invalid or not set
	 */
	public static RGB getColor(Pattern pattern) {
		try {
			final String colValue = pattern.getColor();
			if (colValue != null) {
				final String[] rgb = colValue.split(" ");
				if (rgb.length == 3) {
					return new RGB(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2]));
				}
			}
		} catch (final NumberFormatException e) {
		}

		return new RGB(0, 0, 0);
	}

	public static Map<String, Color> getColors(Resource resource) {
		final IFile resourceFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(resource.getURI().toPlatformString(false)));
		final IProject project = resourceFile.getProject();

		if (project.exists()) {
			final IFile file = project.getFile("config.servicepattern");
			if (file.exists()) {
				final HashMap<String, Color> colors = new HashMap<>();
				try {
					// Create a resource set
					//
					final ResourceSet resourceSet = new ResourceSetImpl();
					// Get the URI of the model file.
					//
					final URI fileURI = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
					// Create a resource for this file.
					//
					final Resource res = resourceSet.createResource(fileURI);
					// Add the initial model object to the contents.
					//
					final Map<Object, Object> options = new HashMap<>();
					options.put(XMLResource.OPTION_ENCODING, "UTF-8");
					res.load(options);
					final Library patterns = (Library) res.getContents().get(0);
					for (final Pattern pattern : patterns.getPatterns()) {
						final RGB rgb = getColor(pattern);
						final Color col = new Color(Display.getCurrent(), rgb);
						colors.put(pattern.getTrainName(), col);
					}
					res.unload();

					return colors;
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}
}
