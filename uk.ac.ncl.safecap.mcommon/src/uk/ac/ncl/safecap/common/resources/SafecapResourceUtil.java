package uk.ac.ncl.safecap.common.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

public class SafecapResourceUtil {

	public static Map<String, List<IFile>> getResource(IProject project, String extension) {
		final Map<String, List<IFile>> models = new HashMap<>();
		try {
			final IResource[] resources = project.members();
			for (final IResource res : resources) {
				if (res instanceof IFile) {
					final IFile file = (IFile) res;
					if (file.exists() && file.getFileExtension() != null && file.getFileExtension().equals(extension)) {
						final String modelName = VersionUtil.getModelName(file.getFullPath().removeFileExtension().lastSegment());
						List<IFile> list = models.get(modelName);
						if (list == null) {
							list = new ArrayList<>();
							models.put(modelName, list);
						}
						list.add(file);
					}
				}
			}
			return models;
		} catch (final CoreException e) {
		}

		return models;
	}
}
