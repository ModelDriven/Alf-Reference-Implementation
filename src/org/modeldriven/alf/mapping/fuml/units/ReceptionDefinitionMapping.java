
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.units;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.mapping.fuml.common.ElementReferenceMapping;
import org.modeldriven.alf.mapping.fuml.units.MemberMapping;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.units.ReceptionDefinition;

import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.Classes.Kernel.NamedElement;
import fUML.Syntax.CommonBehaviors.Communications.Reception;
import fUML.Syntax.CommonBehaviors.Communications.Signal;

import java.util.ArrayList;
import java.util.List;

public class ReceptionDefinitionMapping extends MemberMapping {

    private Reception reception = null;
    
    public void mapTo(Reception reception) throws MappingError {
        super.mapTo(reception);

        ReceptionDefinition definition = this.getReceptionDefinition();
        ElementReference signalReference = definition.getSignal();

        FumlMapping mapping = this.fumlMap(signalReference);
        if (mapping instanceof ElementReferenceMapping) {
            mapping = ((ElementReferenceMapping)mapping).getMapping();
            if (mapping instanceof SignalDefinitionMapping) {
                Signal signal = 
                    (Signal)((SignalDefinitionMapping)mapping).getClassifier();
                reception.setSignal(signal);
            }
        }
    }
    
    @Override
    public NamedElement getNamedElement() throws MappingError {
        return this.getReception();
    }
    
    public Reception getReception() throws MappingError {
        if (this.reception == null) {
            this.reception = new Reception();
            this.mapTo(this.reception);
        }

        return this.reception;
    }
    
	public ReceptionDefinition getReceptionDefinition() {
		return (ReceptionDefinition) this.getSource();
	}

    @Override
    public Element getElement() {
        return this.reception;
    }

    @Override
	public List<Element> getModelElements() throws MappingError {
	    ArrayList<Element> elements = new ArrayList<Element>();
	    elements.add(this.getReception());
	    return elements;
	}
    
    @Override
    public String toString() {
        return super.toString() + " signal:" + this.reception.signal;
    }
    
    @Override
    public void print(String prefix) {
        super.print(prefix);
        System.out.println(prefix + " reception:" + reception);
    }

} // ReceptionDefinitionMapping
