
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

import org.modeldriven.alf.syntax.units.EnumerationDefinition;

import fUML.Syntax.Classes.Kernel.Classifier;
import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.Classes.Kernel.Enumeration;
import fUML.Syntax.Classes.Kernel.EnumerationLiteral;
import fUML.Syntax.Classes.Kernel.NamedElement;

public class EnumerationDefinitionMapping extends ClassifierDefinitionMapping {

	public EnumerationDefinition getEnumerationDefinition() {
		return (EnumerationDefinition) this.getSource();
	}

    @Override
    public Classifier mapClassifier() {
        return new Enumeration();
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
