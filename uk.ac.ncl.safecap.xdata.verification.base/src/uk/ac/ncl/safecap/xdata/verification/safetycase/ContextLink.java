/******************************************************************************
 * Copyright (c) 2015 Oracle
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

public interface ContextLink extends Element {
	ElementType TYPE = new ElementType(ContextLink.class);

	@Reference(target = SafetyStatement.class)
	@ElementReference(list = "/Statements", key = "Id")
	@XmlBinding(path = "from")
	@Required
	ValueProperty PROP_FROM_STATEMENT = new ValueProperty(TYPE, "FromStatement");

	ReferenceValue<String, SafetyStatement> getFromStatement();

	void setFromStatement(String name);

	@Reference(target = SafetyContext.class)
	@ElementReference(list = "/Contexts", key = "Id")
	@XmlBinding(path = "to")
	@Required
	ValueProperty PROP_TO_CONTEXT = new ValueProperty(TYPE, "ToContext");

	ReferenceValue<String, SafetyContext> getToContext();

	void setToStatement(String name);

}
