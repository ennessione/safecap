package uk.ac.ncl.safecap.gui.trainconfig;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;

import safecap.Project;
import servicepattern.Library;
import servicepattern.Pattern;
import servicepattern.ServicepatternPackage;
import traintable.Train;
import traintable.TrainTable;
import traintable.TraintablePackage;

public class TrainConfig {

	public static Train resolveTrain(List<Train> trains, String name) {
		if (name == null) {
			return null;
		}

		for (final Train t : trains) {
			if (name.equals(t.getName())) {
				return t;
			}
		}

		return null;
	}

	/**
	 * 
	 * @return a list of currently defined train types, returns null if
	 *         "Verification" project does not exist
	 */
	public static List<Train> getTrainTypes() {
		final IProject verProject = ResourcesPlugin.getWorkspace().getRoot().getProject("Verification");
		if (verProject.exists()) {
			final IFile file = verProject.getFile("config.traintable");
			if (!file.exists()) {
				try {
					// Create a resource set
					//
					final ResourceSet resourceSet = new ResourceSetImpl();
					// Get the URI of the model file.
					//
					final URI fileURI = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
					// Create a resource for this file.
					//
					final Resource resource = resourceSet.createResource(fileURI);
					// Add the initial model object to the contents.
					//
					final EObject rootObject = TraintablePackage.eINSTANCE.getTraintableFactory().createTrainTable();
					if (rootObject != null) {
						resource.getContents().add(rootObject);
					}

					// Save the contents of the resource to the file system.
					//
					final Map<Object, Object> options = new HashMap<>();
					options.put(XMLResource.OPTION_ENCODING, "UTF-8");
					resource.save(options);
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}

			if (file.exists()) {
				try {
					final ResourceSet resourceSet = new ResourceSetImpl();
					final URI fileURI = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
					final Resource resource = resourceSet.createResource(fileURI);
					final Map<Object, Object> options = new HashMap<>();
					options.put(XMLResource.OPTION_ENCODING, "UTF-8");
					resource.load(options);
					return ((TrainTable) resource.getContents().get(0)).getTrains();
				} catch (final IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static Pattern getServicePatterns(Project project, String trainName) {
		for (final Pattern pattern : getServicePatterns(project)) {
			if (pattern.getTrainName().equals(trainName)) {
				return pattern;
			}
		}

		return null;
	}

	/**
	 * 
	 * @return a list of service patterns, returns null if project is null, project
	 *         is a non-file resource, or the containing Eclipse project does not
	 *         exist
	 */
	public static List<Pattern> getServicePatterns(Project project) {
		if (project.eResource() == null) {
			return null;
		}

		final URI projectUri = project.eResource().getURI();
		if (projectUri.isPlatformResource()) {
			final String filePath = projectUri.toPlatformString(false);
			final IFile projectFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(filePath));
			final IProject verProject = projectFile.getProject();

			final IFile file = verProject.getFile("config.servicepattern");
			if (!file.exists()) {
				try {
					// Create a resource set
					//
					final ResourceSet resourceSet = new ResourceSetImpl();
					// Get the URI of the model file.
					//
					final URI fileURI = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
					// Create a resource for this file.
					//
					final Resource resource = resourceSet.createResource(fileURI);
					// Add the initial model object to the contents.
					//
					final EObject rootObject = ServicepatternPackage.eINSTANCE.getServicepatternFactory().createLibrary();
					if (rootObject != null) {
						resource.getContents().add(rootObject);
					}

					// Save the contents of the resource to the file system.
					//
					final Map<Object, Object> options = new HashMap<>();
					options.put(XMLResource.OPTION_ENCODING, "UTF-8");
					resource.save(options);
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}

			if (file.exists()) {
				try {
					final ResourceSet resourceSet = new ResourceSetImpl();
					final URI fileURI = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
					final Resource resource = resourceSet.createResource(fileURI);
					final Map<Object, Object> options = new HashMap<>();
					options.put(XMLResource.OPTION_ENCODING, "UTF-8");
					resource.load(options);
					return ((Library) resource.getContents().get(0)).getPatterns();
				} catch (final IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
