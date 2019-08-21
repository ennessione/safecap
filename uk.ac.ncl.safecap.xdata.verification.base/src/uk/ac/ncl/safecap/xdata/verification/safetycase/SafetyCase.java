/******************************************************************************
 * Copyright (c) 2014 Oracle and Accenture
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Konstantin Komissarchik - initial implementation
 *    Kamesh Sampath - [355751] General improvement of XML root binding API
 *    Konstantin Komissarchik - miscellaneous improvements
 ******************************************************************************/

package uk.ac.ncl.safecap.xdata.verification.safetycase;

import org.eclipse.sapphire.Element;
import org.eclipse.sapphire.ElementList;
import org.eclipse.sapphire.ElementType;
import org.eclipse.sapphire.ListProperty;
import org.eclipse.sapphire.Type;
import org.eclipse.sapphire.modeling.xml.annotations.XmlBinding;
import org.eclipse.sapphire.modeling.xml.annotations.XmlListBinding;

@XmlBinding(path = "safetycase")
public interface SafetyCase extends Element {
	ElementType TYPE = new ElementType(SafetyCase.class);

	@Type(base = SafetyStatement.class, possible = { SafetyGoal.class, SafetySolution.class, SafetyStrategy.class })
	@XmlListBinding(mappings = @XmlListBinding.Mapping(element = "statement", type = SafetyStatement.class))
	ListProperty PROP_STATEMENTS = new ListProperty(TYPE, "Statements");

	ElementList<SafetyStatement> getStatements();

	@Type(base = SafetyContext.class)
	@XmlListBinding(mappings = @XmlListBinding.Mapping(element = "context", type = SafetyContext.class))
	ListProperty PROP_CONTEXTS = new ListProperty(TYPE, "Contexts");

	ElementList<SafetyContext> getContexts();

	@Type(base = SafetyLink.class)
	@XmlListBinding(mappings = @XmlListBinding.Mapping(element = "link", type = SafetyLink.class))
	ListProperty PROP_LINKS = new ListProperty(TYPE, "Links");

	ElementList<SafetyLink> getLinks();

	@Type(base = ContextLink.class)
	@XmlListBinding(mappings = @XmlListBinding.Mapping(element = "ref", type = ContextLink.class))
	ListProperty PROP_REFS = new ListProperty(TYPE, "Refs");

	ElementList<ContextLink> getRefs();

}
