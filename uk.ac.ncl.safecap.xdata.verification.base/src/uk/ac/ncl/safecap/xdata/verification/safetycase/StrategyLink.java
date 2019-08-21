/******************************************************************************
 * Copyright (c) 2014 Oracle
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Shenxue Zhou - initial implementation and ongoing maintenance
 *    Konstantin Komissarchik - miscellaneous improvements
 ******************************************************************************/

package uk.ac.ncl.safecap.xdata.verification.safetycase;

import org.eclipse.sapphire.ElementType;

public interface StrategyLink extends SafetyLink {
	ElementType TYPE = new ElementType(StrategyLink.class);
}
