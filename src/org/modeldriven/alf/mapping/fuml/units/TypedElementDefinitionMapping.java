
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
import org.modeldriven.alf.syntax.units.TypedElementDefinition;

import fUML.Syntax.Classes.Kernel.Classifier;
import fUML.Syntax.Classes.Kernel.MultiplicityElement;
import fUML.Syntax.Classes.Kernel.Type;
import fUML.Syntax.Classes.Kernel.TypedElement;

public abstract class TypedElementDefinitionMapping extends MemberMapping {

    public void mapTo(
            TypedElement typedElement, 
            MultiplicityElement multiplicityElement
            ) throws MappingError {
        super.mapTo(typedElement);

        TypedElementDefinition definition = this.getTypedElementDefinition();

        multiplicityElement.setLower(definition.getLower());
        multiplicityElement.setUpper(definition.getUpper());
        multiplicityElement.setIsOrdered(definition.getIsOrdered());
        multiplicityElement.setIsUnique(!definition.getIsNonunique());

        ElementReference type = definition.getType();
        if (type != null ) {
            FumlMapping mapping = this.fumlMap(type);
            if (mapping instanceof ElementReferenceMapping) {
                mapping = ((ElementReferenceMapping)mapping).getMapping();
            }
            if (!(mapping instanceof ClassifierDefinitionMapping)) {
                this.throwError("Error mapping type: " + type);
            } else {
                // NOTE: If this typed element is within an operation, then the
                // use of getClassifierOnly here avoids problems with mapping
                // the classifier before the mapping of the operation is
                // complete, if the type is the class that owns the operation.
                Classifier classifier = 
                    ((ClassifierDefinitionMapping)mapping).getClassifierOnly();
                typedElement.setType(classifier);                   
            }
        }
    }
    
	public TypedElementDefinition getTypedElementDefinition() {
		return (TypedElementDefinition) this.getSource();
	}
	
	public abstract MultiplicityElement getMultiplicityElement();
	public abstract TypedElement getTypedElement();
	
	@Override
	public String toString() {
	    MultiplicityElement multiplicityElement = this.getMultiplicityElement();
	    return super.toString() + 
	        " lower:" + multiplicityElement.lower +
	        " upper:" + multiplicityElement.upper.naturalValue +
	        " isOrdered:" + multiplicityElement.isOrdered +
	        " isNonUnique:" + multiplicityElement.isUnique;
	}
	
	@Override
	public void print(String prefix) {
	    super.print(prefix);
	    
	    Type type = this.getTypedElement().type;
	    if (type != null) {
	        System.out.println(prefix + " type: " + type);
	    }
	}

} // TypedElementDefinitionMapping
