package uk.ac.ncl.safecap.misc.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IResource;

import safecap.Project;
import safecap.model.Route;
import uk.ac.ncl.safecap.common.MD5Checksum;

public class ProjectUtil {
	public static String getFingerprint(Project project) {
		final Object fingerprint = ProjectTransient.getValue(project, SafecapConstants.EXT_PROJECT_FINGERPRINT);
		if (fingerprint != null) {
			return fingerprint.toString();
		}

		final StringBuilder sb = new StringBuilder();
		final List<String> names = new ArrayList<>(project.getRoutes().size());
		for (final Route r : project.getRoutes()) {
			names.add(r.getLabel());
		}

		Collections.sort(names);

		for (final String s : names) {
			sb.append(s);
		}

		final String fp = MD5Checksum.getMD5TextChecksum(sb.toString());
		ProjectTransient.setValue(project, SafecapConstants.EXT_PROJECT_FINGERPRINT, fp);
		return fp;
	}

	public static String getFingerprintName(Project project) {
		final IResource resource = EmfUtil.getPlatformResource(project);
		String name = resource.getName();
		if (name.indexOf("___") > 0) {
			name = name.substring(0, name.indexOf("___"));
		} else if (name.indexOf(".") > 0) {
			name = name.substring(0, name.indexOf("."));
		}
		return name;

	}

}
