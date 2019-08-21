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

package uk.ac.ncl.safecap.xdata.verification.dictionary;

import org.eclipse.sapphire.ElementList;
import org.eclipse.sapphire.ElementType;
import org.eclipse.sapphire.ListProperty;
import org.eclipse.sapphire.Type;
import org.eclipse.sapphire.modeling.xml.annotations.XmlListBinding;

import uk.ac.ncl.safecap.xdata.verification.ICommented;
import uk.ac.ncl.safecap.xdata.verification.INamed;

public interface DictionarySection extends ICommented, INamed {
	ElementType TYPE = new ElementType(DictionarySection.class);

	@Type(base = TermDefinition.class)
	@XmlListBinding(mappings = @XmlListBinding.Mapping(element = "term", type = TermDefinition.class))
	ListProperty PROP_TERMS = new ListProperty(TYPE, "Terms");

	ElementList<TermDefinition> getTerms();

}
