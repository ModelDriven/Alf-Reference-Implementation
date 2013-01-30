
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

import org.modeldriven.alf.syntax.units.SignalDefinition;

import org.modeldriven.alf.uml.Classifier;
import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.NamedElement;
import org.modeldriven.alf.uml.Property;
import org.modeldriven.alf.uml.Signal;

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
        return this.create(Signal.class);
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
