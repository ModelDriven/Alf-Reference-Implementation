
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.Collection;
import java.util.List;

/**
 * The definition of a classifier template parameter, which acts as a classifier
 * within the definition of the template.
 **/

public class ClassifierTemplateParameterImpl extends ClassifierDefinitionImpl {

	public ClassifierTemplateParameterImpl(ClassifierTemplateParameter self) {
		super(self);
	}

	@Override
	public org.modeldriven.alf.syntax.units.ClassifierTemplateParameter getSelf() {
		return (ClassifierTemplateParameter) this.self;
	}

	/**
	 * Annotations are not allowed on classifier template parameters.
	 **/
	@Override
	public Boolean annotationAllowed(StereotypeAnnotation annotation) {
		return false;
	} // annotationAllowed

	/**
	 * Returns false. (Classifier template parameters cannot be stubs.)
	 **/
	@Override
	public Boolean matchForStub(UnitDefinition unit) {
		return false;
	} // matchForStub

	/**
	 * Return true if the given member is a classifier template parameter.
	 **/
	@Override
	public Boolean isSameKindAs(Member member) {
		return member instanceof ClassifierTemplateParameter;
	} // isSameKindAs
	
	/*
	 * Helper Methods
	 */
	
	/**
	 * Two template parameters match if they have same names and the same 
	 * specialization referents.
	 **/
	@Override
	public boolean equals(Object other) {
	    if (!(other instanceof ClassifierTemplateParameter ||
	            other instanceof ClassifierTemplateParameterImpl)) {
	        return false;
	    } else {
	        ClassifierTemplateParameter otherParameter = 
	            other instanceof ClassifierTemplateParameter?
	                    (ClassifierTemplateParameter)other:
	                    ((ClassifierTemplateParameterImpl)other).getSelf();
	        Collection<ElementReference> otherSpecializations = otherParameter.getSpecializationReferent();
	        
	        ClassifierTemplateParameter self = this.getSelf();
            Collection<ElementReference> mySpecializations = self.getSpecializationReferent();
            
            for (ElementReference specialization: mySpecializations) {
                if (!specialization.getImpl().isContainedIn(otherSpecializations)) {
                    return false;
                }
            }
	        
            return  otherParameter.getName().equals(self.getName()) &&
                    otherSpecializations.size() == mySpecializations.size();
	    }
	}
	
    @Override
    public Member bind(String name,
            NamespaceDefinition namespace,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        return null;
    }


} // ClassifierTemplateParameterImpl
