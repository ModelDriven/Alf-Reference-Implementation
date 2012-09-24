
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.mapping.fuml.units;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.units.ClassifierDefinitionMapping;

import org.modeldriven.alf.syntax.units.EnumerationDefinition;

import org.modeldriven.alf.uml.Classifier;
import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.Enumeration;
import org.modeldriven.alf.uml.EnumerationLiteral;
import org.modeldriven.alf.uml.NamedElement;

public class EnumerationDefinitionMapping extends ClassifierDefinitionMapping {
    
    /**
     * 1. An enumeration definition maps to an enumeration.
     * 
     * 2. An enumeration literal name maps to an enumeration literal that is an
     * owned literal of the enumeration and has the given unqualified name.
     */
    
    // Visibility is handled in MemberMapping.
    // See also EnumerationLiteralNameMapping.

	public EnumerationDefinition getEnumerationDefinition() {
		return (EnumerationDefinition) this.getSource();
	}

    @Override
    public Classifier mapClassifier() {
        return this.create(Enumeration.class);
    }

    @Override
    public void addMemberTo(Element element, NamedElement namespace) throws MappingError {
        if (element instanceof EnumerationLiteral) {
            ((Enumeration)namespace).addOwnedLiteral((EnumerationLiteral)element);
          } else {
            this.throwError("Only enumeration literals allowed: " + element);
          }
    }

} // EnumerationDefinitionMapping
