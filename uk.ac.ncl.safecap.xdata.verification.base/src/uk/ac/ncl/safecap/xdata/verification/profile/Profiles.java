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

package uk.ac.ncl.safecap.xdata.verification.profile;

import org.eclipse.sapphire.ElementList;
import org.eclipse.sapphire.ElementType;
import org.eclipse.sapphire.ListProperty;
import org.eclipse.sapphire.Type;
import org.eclipse.sapphire.modeling.xml.annotations.XmlListBinding;

import uk.ac.ncl.safecap.xdata.verification.ICommented;
import uk.ac.ncl.safecap.xdata.verification.INamed;

public interface Profiles extends ICommented, INamed {
	ElementType TYPE = new ElementType(Profiles.class);

	@Type(base = ProfileDefinition.class)
	@XmlListBinding(mappings = @XmlListBinding.Mapping(element = "profile", type = ProfileDefinition.class))
	ListProperty PROP_PROFILES = new ListProperty(TYPE, "Profiles");

	ElementList<ProfileDefinition> getProfiles();

}
