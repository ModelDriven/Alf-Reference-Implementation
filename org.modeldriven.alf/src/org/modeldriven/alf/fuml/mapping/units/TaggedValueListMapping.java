
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.units;

import org.modeldriven.alf.fuml.mapping.common.SyntaxElementMapping;
import org.modeldriven.alf.mapping.MappingError;

import org.modeldriven.alf.syntax.units.TaggedValueList;

import org.modeldriven.alf.uml.Element;

import java.util.List;

public class TaggedValueListMapping extends SyntaxElementMapping {

	public TaggedValueListMapping() {
		this.setErrorMessage("No mapping for TaggedValueList.");
	}

    @Override
    public Element getElement() {
        return null;
    }

	public List<Element> getModelElements() throws MappingError {
        throw new MappingError(this, this.getErrorMessage());
	}

	public TaggedValueList getTaggedValueList() {
		return (TaggedValueList) this.getSource();
	}

} // TaggedValueListMapping
