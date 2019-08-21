package uk.ac.ncl.safecap.xdata.record;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import safecap.Project;
import uk.ac.ncl.safecap.misc.core.ProjectUtil;
import uk.ac.ncl.safecap.xdata.verification.RootCatalog;
import uk.ac.ncl.safecap.xdata.verification.transitions.TransitionContainer;

public class RecordQueries {
	public static List<VerificationRun> getRecordsFor(VerificationRecord record, Project project, TransitionContainer tc) {
		final String schemaId = ProjectUtil.getFingerprint(project);
		final String ssiId = tc.getFingerprint();

		return filterRuns(record, schemaId, ssiId);
	}

	public static List<VerificationRun> getRecordsFor(VerificationRecord record, Project project, RootCatalog catalog) {
		final String schemaId = ProjectUtil.getFingerprint(project);
		if (catalog.getContext().empty()) {
			return Collections.emptyList();
		}
		final String ctId = catalog.getContext().content().getFingerprint();

		return filterRuns(record, schemaId, ctId);
	}

	private static List<VerificationRun> filterRuns(VerificationRecord record, String schemaId, String ssiId) {
		final List<VerificationRun> result = new ArrayList<>();
		for (final VerificationRun vr : record.getEntries()) {
			if (vr.getSchemaId().equals(schemaId) && vr.getArtefactId().equals(ssiId)) {
				result.add(vr);
			}
		}

		return result;
	}
}
