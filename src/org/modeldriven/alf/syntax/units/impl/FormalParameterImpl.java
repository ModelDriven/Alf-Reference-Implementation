
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.units.impl;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.SyntaxElement;
import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.units.*;

import java.util.List;

/**
 * A typed element definition for the formal parameter of an activity or
 * operation.
 **/

public class FormalParameterImpl extends TypedElementDefinitionImpl {

    private String direction = "";

	public FormalParameterImpl(FormalParameter self) {
		super(self);
	}

	public FormalParameter getSelf() {
		return (FormalParameter) this.self;
	}
	
    public String getDirection() {
        return this.direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

	/*
	 * Helper Methods
	 */

	/**
	 * Returns true if the annotation is for a stereotype that has a metaclass
	 * consistent with Parameter.
	 **/
	public Boolean annotationAllowed(StereotypeAnnotation annotation) {
	    // TODO: Allow stereotypes consistent with formal parameters.
		return false;
	} // annotationAllowed

	/**
	 * Return true if the given member is a FormalParameter.
	 **/
	public Boolean isSameKindAs(Member member) {
		return member instanceof FormalParameter;
	} // isSameKindAs
	
    /**
     * Check if one list of formal parameters equals another, element by 
     * element, in order.
     **/
    public static boolean equals(List<FormalParameter> formalParameters1,
            List<FormalParameter> formalParameters2) {
        if (formalParameters1.size() != formalParameters2.size()) {
            return false;
        } else {
            for (int i=0; i<formalParameters1.size(); i++) {
                if (!formalParameters1.get(i).getImpl().equals(formalParameters2.get(i))){
                    return false;
                }
            }
            return true;
        }
    }
    
    /**
     * Check if one list of formal parameters matches another, element by 
     * element, in order.
     **/
    public static boolean match(List<FormalParameter> formalParameters1,
            List<FormalParameter> formalParameters2) {
        if (formalParameters1.size() != formalParameters2.size()) {
            return false;
        } else {
            for (int i=0; i<formalParameters1.size(); i++) {
                if (!formalParameters1.get(i).getImpl().matches(formalParameters2.get(i))){
                    return false;
                }
            }
            return true;
        }
    }
    
	/**
     * Two formal parameters are equal if they have the same direction, name, 
     * multiplicity bounds, ordering, uniqueness and type reference.
    **/
	@Override
	public boolean equals(Object object) {
	    if (!(object instanceof FormalParameter || 
	            object instanceof FormalParameterImpl)) {
	        return false;
	    } else {
	        FormalParameter self = this.getSelf();
	        ElementReference myType = self.getType();
	        
	        FormalParameter other = object instanceof FormalParameter?
	                (FormalParameter)object: ((FormalParameterImpl)object).getSelf();
            ElementReference otherType = other.getType();
            
            return  self.getDirection().equals(other.getDirection()) &&
                    self.getName().equals(other.getName()) &&
                    self.getLower().equals(other.getLower()) &&
                    self.getUpper().equals(other.getUpper()) &&
                    self.getIsOrdered().equals(other.getIsOrdered()) &&
                    self.getIsNonunique().equals(other.getIsNonunique()) &&
                    (myType != null && myType.getImpl().equals(otherType) ||
                            myType == null && otherType == null);
	    }
	}

    /**
     * Two formal parameters "match" if they have the same name and type reference.
    **/
    public boolean matches(Object object) {
        if (!(object instanceof FormalParameter || 
                object instanceof FormalParameterImpl)) {
            return false;
        } else {
            FormalParameter self = this.getSelf();
            String myName = self.getName();
            ElementReference myType = self.getType();
            
            FormalParameter other = object instanceof FormalParameter?
                (FormalParameter)object: ((FormalParameterImpl)object).getSelf();
            String otherName = other.getName();
            ElementReference otherType = other.getType();
            
            return (myName == null && otherName == null ||
                        myName != null && myName.equals(otherName)) &&
                   (myType == null && otherType == null ||
                        myType != null && myType.getImpl().equals(otherType));
        }
    }

    /**
     * Check if this parameter is assignable from the given expression.
     */
    public boolean isAssignableFrom(Expression expression) {
        return new AssignableTypedElementImpl(this).isAssignableFrom(expression);
    }
    
    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof FormalParameter) {
            this.getSelf().setDirection(((FormalParameter)base).getDirection());
        }
    }

} // FormalParameterImpl
