package uk.ac.ncl.safecap.xdata.verification.safetycase;

import org.eclipse.sapphire.ElementType;

public interface SafetyStrategy extends SafetyStatement {
	ElementType TYPE = new ElementType(SafetyStrategy.class);
}
