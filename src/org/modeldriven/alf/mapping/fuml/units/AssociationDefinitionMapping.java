
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.units;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.units.ClassifierDefinitionMapping;

import org.modeldriven.alf.syntax.units.AssociationDefinition;

import fUML.Syntax.Classes.Kernel.Association;
import fUML.Syntax.Classes.Kernel.Classifier;
import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.Classes.Kernel.NamedElement;
import fUML.Syntax.Classes.Kernel.Property;

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
        return new Association();
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
