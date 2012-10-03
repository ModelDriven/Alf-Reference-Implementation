
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.units;

import org.modeldriven.alf.fuml.mapping.units.ClassifierDefinitionMapping;
import org.modeldriven.alf.mapping.MappingError;

import org.modeldriven.alf.syntax.units.AssociationDefinition;

import org.modeldriven.alf.uml.Association;
import org.modeldriven.alf.uml.Classifier;
import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.NamedElement;
import org.modeldriven.alf.uml.Property;

public class AssociationDefinitionMapping extends ClassifierDefinitionMapping {
    
    /**
     * 1. An association definition maps to an association.
     * 
     * Association Members
     * 
     * 2. A property definition maps to an owned end of the association as All
     * ends are navigable owned ends of the association.
     */
    
    // Visibility is handled in MemberMapping.

    @Override
    public Classifier mapClassifier() {
        return this.create(Association.class);
    }

    @Override
    public void addMemberTo(Element element, NamedElement namespace) throws MappingError {
        if (element instanceof Property) {
            ((Association)namespace).addOwnedEnd((Property)element);
          } else {
            this.throwError("Member not allowed for an association: " + element);
          }
    }

	public AssociationDefinition getAssociationDefinition() {
		return (AssociationDefinition) this.getSource();
	}

} // AssociationDefinitionMapping
