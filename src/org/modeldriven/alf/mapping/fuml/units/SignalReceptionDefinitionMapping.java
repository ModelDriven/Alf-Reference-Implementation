
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.units;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.units.SignalDefinitionMapping;

import org.modeldriven.alf.syntax.units.SignalReceptionDefinition;

import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.CommonBehaviors.Communications.Reception;
import fUML.Syntax.CommonBehaviors.Communications.Signal;

import java.util.List;

public class SignalReceptionDefinitionMapping extends SignalDefinitionMapping {
    
    private Reception reception = null;
    
    public Reception getReception() throws MappingError {
        if (this.reception == null) {
            Signal signal = (Signal)this.getClassifier();
            this.reception = new Reception();
            this.reception.setName(signal.name);
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
	    return super.toString() + " signal:" + this.reception.signal;
	}
	
	@Override
	public void print(String prefix) {
	    super.print(prefix);
	    System.out.println(prefix + " reception:" + this.reception);
	}

} // SignalReceptionDefinitionMapping
