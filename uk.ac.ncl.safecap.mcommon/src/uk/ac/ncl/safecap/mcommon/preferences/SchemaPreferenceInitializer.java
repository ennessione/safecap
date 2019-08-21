package uk.ac.ncl.safecap.mcommon.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import uk.ac.ncl.safecap.common.resources.CommonPlugin;

public class SchemaPreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
	 * initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		final IPreferenceStore store = CommonPlugin.getDefault().getPreferenceStore();
		store.setDefault(SchemaPreferenceConstants.P_F_POG_DEP, false);
		store.setDefault(SchemaPreferenceConstants.P_F_OVERLAP_CUSTOM, true);
		store.setDefault(SchemaPreferenceConstants.P_F_OVERLAP_FULL, true);
		store.setDefault(SchemaPreferenceConstants.P_F_OVERLAP_LENGTH, false);
		store.setDefault(SchemaPreferenceConstants.P_F_OVERLAP_REDUCED, true);
		store.setDefault(SchemaPreferenceConstants.P_F_OVERLAP_STEP_OVER, true);

	}

}
