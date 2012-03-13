
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
import org.modeldriven.alf.mapping.fuml.units.MemberMapping;

import org.modeldriven.alf.syntax.units.EnumerationLiteralName;

import fUML.Syntax.Classes.Kernel.Classifier;
import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.Classes.Kernel.Enumeration;
import fUML.Syntax.Classes.Kernel.EnumerationLiteral;
import fUML.Syntax.Classes.Kernel.NamedElement;

import java.util.ArrayList;
import java.util.List;

public class EnumerationLiteralNameMapping extends MemberMapping {
    
    private EnumerationLiteral enumerationLiteral = null;
    
    public void mapTo(EnumerationLiteral enumerationLiteral) 
        throws MappingError {
        super.mapTo(enumerationLiteral);
        
        EnumerationLiteralName literal = this.getEnumerationLiteralName();
        enumerationLiteral.setName(literal.getName());
        
        FumlMapping mapping = this.fumlMap(literal.getNamespace());
        if (!(mapping instanceof ClassifierDefinitionMapping)) {
            this.throwError("Error mapping enumeration: " +
                    mapping.getErrorMessage());
        } else {
            Classifier classifier = 
                ((ClassifierDefinitionMapping)mapping).getClassifier();
            enumerationLiteral.addClassifier(classifier);
            enumerationLiteral.enumeration = (Enumeration)classifier;
        }
    }

    @Override
    public Element getElement() {
        return this.enumerationLiteral;
    }
    
    @Override
	public List<Element> getModelElements() throws MappingError {
		List<Element> elements = new ArrayList<Element>();
		elements.add(this.getEnumerationLiteral());
		return elements;
	}
	
    @Override
    public NamedElement getNamedElement() throws MappingError {
        return getEnumerationLiteral();
    }

	public EnumerationLiteral getEnumerationLiteral() throws MappingError {
	    if (this.enumerationLiteral == null) {
	        this.enumerationLiteral = new EnumerationLiteral();
	        this.mapTo(enumerationLiteral);
	    }
	    return this.enumerationLiteral;
	}

	public EnumerationLiteralName getEnumerationLiteralName() {
		return (EnumerationLiteralName) this.getSource();
	}
	
	@Override
	public void print(String prefix) {
	    super.print(prefix);
	    
	    if (this.enumerationLiteral != null) {
	        System.out.println(prefix + " enumerationLiteral: " + 
	                enumerationLiteral);
	    }
	}

} // EnumerationLiteralNameMapping
