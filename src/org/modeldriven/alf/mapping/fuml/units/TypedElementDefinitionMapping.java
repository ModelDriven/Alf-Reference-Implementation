
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

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
    
    /**
     * 1. A typed element definition maps to an element that is both a typed
     * element and a multiplicity element, as given for the specific kind of
     * typed element definition.
     * 
     * Typed Element
     * 
     * 2. The type of the typed element definition maps to the type of the typed
     * element.
     * 
     * Multiplicity Element
     * 
     * 3. The lower attribute of the multiplicity element is a literal integer
     * for the value given by the lower attribute of the typed element
     * definition.
     * 
     * 4. The upper attribute of the multiplicity element is a literal unlimited
     * natural for the value given by the upper attribute of the typed element
     * definition.
     * 
     * 5. The isUnique and isOrdered attributes of the multiplicity element are
     * set according to the isNonUnique (with opposite sense) and isOrdered
     * attributes of the typed element definition
     */

    public void mapTo(
            TypedElement typedElement, 
            MultiplicityElement multiplicityElement) throws MappingError {
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
