package uk.ac.ncl.safecap.controltable.views;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Image;

import safecap.Project;
import safecap.model.Rule;
import uk.ac.ncl.safecap.misc.core.EmfUtil;

public class VerificationProvider {
	public static final String KIND = "controltable";

	public static void invalidateVerificationStatus(Rule rule) {
		if (rule == null || rule.eContainer() == null) {
			return;
		}

		final EObject container = rule.eContainer();

		final Project project = EmfUtil.getProject(container);
		if (project == null) {
			return;
		}

//		VerificationLog log = VerificationUtils.getVerificationLog(project);
//		if (log == null)
//			return;

//		String uuid = EmfUtil.getElementDescriptor(container);
//
//		List<VerEntry> list = VerificationUtils.findEntriesByKind(log, KIND, uuid);
//		if (list == null || list.size() == 0)
//			return;
//
//		log.getEntries().removeAll(list);
//		VerificationUtils.saveLog(log);
	}

	public static Image getVerificationStatusImage(EObject object) {
//		List<VerEntry> list = VerificationUtils.getVerificationInfoByKind(object, KIND);
//
//		if (list == null || list.size() == 0) {
//			return ImageRegister.getImage(ImageRegister.ICON_VERIFICATION_UNKNOWN);
//		} else {
//			int min_confidence = 1000;
//			for(VerEntry i: list) {
//				if (i.getConfidence() < min_confidence)
//					min_confidence = i.getConfidence();
//			}
//
//			if (min_confidence > 900) {
//				return ImageRegister.getImage(ImageRegister.ICON_VERIFICATION_SUCCESS);
//			} else {
//				return ImageRegister.getImage(ImageRegister.ICON_VERIFICATION_FAILED);
//			}
//		}

		return null;
	}

	public static void onVerificationClick(EObject object) {
//		if (object instanceof Point)
//		{
//			Point point = (Point) object;
//			MessageDialog.openInformation(null, "Verification", "Verification script not available");
//		}
//		else if (object instanceof Route)
//		{
//			Route route = (Route) object;
//			try {
//				CTVerificationAction.execute(route, "route");
//			} catch (ExecutionException e) {
//				MessageDialog.openError(null, "Verification error", "Verification tool failed: " + e.getMessage());
//				e.printStackTrace();
//			}
//
//		}
	}
}
