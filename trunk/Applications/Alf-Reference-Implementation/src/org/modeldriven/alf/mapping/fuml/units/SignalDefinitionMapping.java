
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

import org.modeldriven.alf.syntax.units.SignalDefinition;

import fUML.Syntax.Classes.Kernel.Classifier;
import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.Classes.Kernel.NamedElement;
import fUML.Syntax.Classes.Kernel.Property;
import fUML.Syntax.CommonBehaviors.Communications.Signal;

public class SignalDefinitionMapping extends ClassifierDefinitionMapping {

	public SignalDefinition getSignalDefinition() {
		return (SignalDefinition) this.getSource();
	}

    @Override
    public Classifier mapClassifier() {
        return new Signal();
    }

    @Override
    public void addMemberTo(Element element, NamedElement namespace) throws MappingError {
        if (element instanceof Property) {
            ((Signal)namespace).addOwnedAttribute((Property)element);
          } else {
            this.throwError("Member not allowed for a signal: " + element);
          }
    }

    public Signal getSignal() throws MappingError {
        return (Signal)this.getClassifier();
    }

} // SignalDefinitionMapping
