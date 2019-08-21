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
import org.eclipse.sapphire.Type;
import org.eclipse.sapphire.Value;
import org.eclipse.sapphire.ValueProperty;
import org.eclipse.sapphire.modeling.annotations.DefaultValue;
import org.eclipse.sapphire.modeling.annotations.LongString;
import org.eclipse.sapphire.modeling.annotations.Required;
import org.eclipse.sapphire.modeling.xml.annotations.XmlBinding;

import uk.ac.ncl.safecap.xdata.verification.ICommented;
import uk.ac.ncl.safecap.xdata.verification.INamed;

public interface SafetyStatement extends INamed, ICommented {
	ElementType TYPE = new ElementType(SafetyStatement.class);

	@XmlBinding(path = "text")
	@Required
	@LongString
	ValueProperty PROP_TEXT = new ValueProperty(TYPE, "Text");

	Value<String> getText();

	void setText(String name);

	@XmlBinding(path = "kind")
	@Type(base = ElementClass.class)
	@DefaultValue(text = "IMPLEMENTATION")
	@Required
	ValueProperty PROP_KIND = new ValueProperty(TYPE, "Kind");

	Value<ElementClass> getKind();

	void setKind(ElementClass value);

}
