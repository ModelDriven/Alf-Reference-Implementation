
/*******************************************************************************
 * Copyright 2011-2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.units;

import org.modeldriven.alf.fuml.mapping.units.MemberMapping;

import org.modeldriven.alf.syntax.units.ImportedMember;

import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.NamedElement;

public class ImportedMemberMapping extends MemberMapping {

	@Override
	public NamedElement getNamedElement() {
	    return (NamedElement)this.getElement();
	}

    @Override
    public Element getElement() {
        return this.getImportedMember().getReferent().getImpl().getUml();
    }
    
	public ImportedMember getImportedMember() {
		return (ImportedMember) this.getSource();
	}

} // ImportedMemberMapping
