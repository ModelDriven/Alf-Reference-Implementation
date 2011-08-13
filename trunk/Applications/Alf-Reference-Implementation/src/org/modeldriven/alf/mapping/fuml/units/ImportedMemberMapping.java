
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.units;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.units.MemberMapping;

import org.modeldriven.alf.syntax.units.ImportedMember;

import fUML.Syntax.Classes.Kernel.Element;

import java.util.List;

public class ImportedMemberMapping extends MemberMapping {

	public ImportedMemberMapping() {
		this.setErrorMessage("No mapping for ImportedMember.");
	}

	public List<Element> getModelElements() throws MappingError {
        throw new MappingError(this, this.getErrorMessage());
	}

	public ImportedMember getImportedMember() {
		return (ImportedMember) this.getSource();
	}

    @Override
    public Element getElement() {
        return null;
    }

} // ImportedMemberMapping
