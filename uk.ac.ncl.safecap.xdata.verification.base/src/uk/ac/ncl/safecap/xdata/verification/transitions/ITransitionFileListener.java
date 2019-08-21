package uk.ac.ncl.safecap.xdata.verification.transitions;

import java.io.File;

public interface ITransitionFileListener {
	void refresh(File file, Object element);
}
