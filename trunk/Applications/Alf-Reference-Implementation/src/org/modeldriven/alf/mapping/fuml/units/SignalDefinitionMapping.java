
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

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
    
    /**
     * 1. A signal definition maps to a signal.
     * 
     * Signal Members
     * 
     * 2. A property definition maps to an owned attribute of the signal.
     * 
     */
    
    // For signal reception definition mapping, see the
    // SignalReceptionDefinitionMapping subclass.
    // Visibility is handled in MemberMapping.

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
