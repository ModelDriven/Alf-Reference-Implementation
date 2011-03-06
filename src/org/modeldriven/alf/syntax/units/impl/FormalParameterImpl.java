
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units.impl;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.units.*;
import org.omg.uml.Classifier;
import org.omg.uml.Parameter;

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
            List<?> formalParameters2) {
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
            List<?> formalParameters2) {
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
	            object instanceof FormalParameterImpl) ||
	            object instanceof Parameter) {
	        return false;
	    } else {
	        FormalParameter self = this.getSelf();
	        if (object instanceof Parameter) {
	            Parameter other = (Parameter)object;
	            String otherDirection = other.getDirection().toString();
	            if (otherDirection.equals("return_")) {
	                otherDirection = "return";
	            }
	            return  self.getDirection().equals(otherDirection) &&
	                    self.getName().equals(other.getName()) &&
	                    self.getLower() == other.getLower() &&
	                    self.getUpper() == other.getUpper() &&
	                    self.getIsOrdered() == other.getIsOrdered() &&
	                    self.getIsNonunique() == !other.getIsUnique() &&
	                    self.getType().getImpl().equals(other.getType());
	        } else {
    	        FormalParameter other = object instanceof FormalParameter?
    	            (FormalParameter)object: ((FormalParameterImpl)object).getSelf();
    	        return  self.getDirection().equals(other.getDirection()) &&
    	                self.getName().equals(other.getName()) &&
    	                self.getLower() == other.getLower() &&
    	                self.getUpper() == other.getUpper() &&
    	                self.getIsOrdered() == other.getIsOrdered() &&
    	                self.getIsNonunique() == other.getIsNonunique() &&
    	                self.getType().getImpl().equals(other.getType());
	        }
	    }
	}

    /**
     * Two formal parameters "match" if they have the same name and type reference.
    **/
    public boolean matches(Object object) {
        if (!(object instanceof FormalParameter || 
                object instanceof FormalParameterImpl) ||
                object instanceof Parameter) {
            return false;
        } else {
            FormalParameter self = this.getSelf();
            String myName = self.getName();
            ElementReference myType = self.getType();
            if (object instanceof Parameter) {
                Parameter other = (Parameter)object;
                String otherName = other.getName();
                Classifier otherType = other.getType();
                return (myName == null && otherName == null ||
                            myName.equals(otherName)) &&
                       (myType == null && otherType == null ||
                            myType.getImpl().equals(otherType));
            } else {
                FormalParameter other = object instanceof FormalParameter?
                    (FormalParameter)object: ((FormalParameterImpl)object).getSelf();
                String otherName = other.getName();
                ElementReference otherType = other.getType();
                return (myName == null && otherName == null ||
                            myName.equals(otherName)) &&
                       (myType == null && otherType == null ||
                            myType.getImpl().equals(otherType));
            }
        }
    }

} // FormalParameterImpl
