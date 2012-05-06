
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.mapping.fuml.units;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.units.MemberMapping;

import org.modeldriven.alf.syntax.units.ImportedMember;

import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.Classes.Kernel.NamedElement;

import java.util.List;

public class ImportedMemberMapping extends MemberMapping {

	public ImportedMemberMapping() {
		this.setErrorMessage("No mapping for ImportedMember.");
	}

	public List<Element> getModelElements() throws MappingError {
        throw new MappingError(this, this.getErrorMessage());
	}
	
	@Override
	public NamedElement getNamedElement() {
	    return null;
	}

	public ImportedMember getImportedMember() {
		return (ImportedMember) this.getSource();
	}

    @Override
    public Element getElement() {
        return null;
    }

} // ImportedMemberMapping
