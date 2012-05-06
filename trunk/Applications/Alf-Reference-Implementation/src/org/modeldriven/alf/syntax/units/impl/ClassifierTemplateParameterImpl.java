
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

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
    
    private boolean isBound = false;
    private ElementReference boundArgument = null;

	public ClassifierTemplateParameterImpl(ClassifierTemplateParameter self) {
		super(self);
	}

	@Override
	public ClassifierTemplateParameter getSelf() {
		return (ClassifierTemplateParameter) this.self;
	}
	
	public boolean isBound() {
	    return this.isBound;
	}
	
	public ElementReference getBoundArgument() {
	    return this.boundArgument;
	}
	
	/*
	 * Helper Methods.
	 */

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
	
	@Override
	public boolean isCompletelyBound() {
	    return false;
	}
	
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
	
	/*
    @Override
    public Member bind(String name,
            NamespaceDefinition namespace,
            boolean isOwnedMember,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        
        if (this.getReferent().getImpl().isContainedIn(templateParameters)) {
            return null;
        } else {
            return super.bind(name, namespace, isOwnedMember, 
                    templateParameters, templateArguments);
        }
    }
    */
	
	@Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        ElementReference referent = ((Member)base).getImpl().getReferent();
        for (int i = 0; i < templateParameters.size(); i++) {
            if (referent.getImpl().equals(templateParameters.get(i))) {
                this.isBound = true;
                this.boundArgument = i >= templateArguments.size()? null: 
                    templateArguments.get(i);
                break;
            }
        }
	}


} // ClassifierTemplateParameterImpl
