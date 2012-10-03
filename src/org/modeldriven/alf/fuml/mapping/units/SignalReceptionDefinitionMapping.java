
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.units;

import org.modeldriven.alf.fuml.mapping.units.SignalDefinitionMapping;
import org.modeldriven.alf.mapping.MappingError;

import org.modeldriven.alf.syntax.units.SignalReceptionDefinition;

import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.Reception;
import org.modeldriven.alf.uml.Signal;

import java.util.List;

public class SignalReceptionDefinitionMapping extends SignalDefinitionMapping {
    
    private Reception reception = null;
    
    /**
     * A signal reception definition maps to a signal and a reception for the
     * signal. The signal is mapped as if the signal reception definition was a
     * signal definition and the signal becomes a nested classifier of the class
     * mapped from the class definition that is the namespace of the signal
     * reception definition. The reception becomes an owned reception of the
     * same class.
     */
    
    public Reception getReception() throws MappingError {
        if (this.reception == null) {
            Signal signal = (Signal)this.getClassifier();
            this.reception = this.create(Reception.class);
            this.reception.setName(signal.getName());
            this.reception.setSignal(signal);            
        }
        return this.reception;
    }

	public List<Element> getModelElements() throws MappingError {
	    List<Element> modelElements = super.getModelElements();
	    modelElements.add(this.getReception());
	    return modelElements;
	}

	public SignalReceptionDefinition getSignalReceptionDefinition() {
		return (SignalReceptionDefinition) this.getSource();
	}
	
	@Override
	public String toString() {
	    return super.toString() + " signal:" + this.reception.getSignal();
	}
	
	@Override
	public void print(String prefix) {
	    super.print(prefix);
	    System.out.println(prefix + " reception:" + this.reception);
	}

} // SignalReceptionDefinitionMapping
