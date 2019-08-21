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

import org.eclipse.sapphire.Element;
import org.eclipse.sapphire.ElementReference;
import org.eclipse.sapphire.ElementType;
import org.eclipse.sapphire.ReferenceValue;
import org.eclipse.sapphire.ValueProperty;
import org.eclipse.sapphire.modeling.annotations.Reference;
import org.eclipse.sapphire.modeling.annotations.Required;
import org.eclipse.sapphire.modeling.xml.annotations.XmlBinding;

public interface SafetyLink extends Element {
	ElementType TYPE = new ElementType(SafetyLink.class);

	@Reference(target = SafetyStatement.class)
	@ElementReference(list = "/Statements", key = "Id")
	@XmlBinding(path = "from")
	@Required
	ValueProperty PROP_FROM_STATEMENT = new ValueProperty(TYPE, "FromStatement");

	ReferenceValue<String, SafetyStatement> getFromStatement();

	void setFromStatement(String name);

	@Reference(target = SafetyStatement.class)
	@ElementReference(list = "/Statements", key = "Id")
	@XmlBinding(path = "to")
	@Required
	ValueProperty PROP_TO_STATEMENT = new ValueProperty(TYPE, "ToStatement");

	ReferenceValue<String, SafetyStatement> getToStatement();

	void setToStatement(String name);
}
