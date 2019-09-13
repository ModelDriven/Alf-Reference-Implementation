/*******************************************************************************
 * Copyright 2011-2017 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.units.impl;

import org.modeldriven.alf.syntax.common.AssignedSource;
import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.SyntaxElement;
import org.modeldriven.alf.syntax.common.impl.ElementReferenceImpl;
import org.modeldriven.alf.syntax.statements.Block;
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
     * Constraints
     */
    
    /**
     * If a formal parameter has direction "out" and a multiplicity lower bound
     * greater than 0, and its owning activity or operation definition has an
     * effective body, then there must be an assignment for the formal parameter
     * after the effective body that has a multiplicity greater than 0.
     */
    public boolean formalParameterAssignmentAfterBody() {
        FormalParameter self = this.getSelf();
        NamespaceDefinition namespace = self.getNamespace();
        if (namespace != null) {
            Block body = namespace.getImpl().getEffectiveBody();
            if (body != null) {
                if ("out".equals(self.getDirection()) && self.getLower() > 0) {
                    AssignedSource assignment = 
                            body.getImpl().getAssignmentAfter(self.getName());
                    if (assignment == null || assignment.getLower() == 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

	/*
	 * Helper Methods
	 */
    
	/**
	 * Returns true if the annotation is for a stereotype that has a metaclass
	 * consistent with Parameter.
	 **/
    @Override
    public Class<?> getUMLMetaclass() {
        return org.modeldriven.alf.uml.Parameter.class;
    }

	/**
	 * Return true if the given member is a FormalParameter.
	 **/
	public Boolean isSameKindAs(Member member) {
	    // NOTE: This should also account for the possibility of an
	    // external parameter visible from an external outer scope.
		return member.getImpl().getReferent().getImpl().isParameter();
	}
	
    @Override
    public boolean equals(Object object) {
        ElementReference referent = this.getReferent();
        return 
            object instanceof ElementReference? equal(referent, (ElementReference)object):
            object instanceof ElementReferenceImpl? equal(referent, ((ElementReferenceImpl)object).getSelf()):
            object instanceof Member? equal(referent, ((Member)object).getImpl().getReferent()):
            object instanceof MemberImpl? equal(referent, ((MemberImpl)object).getReferent()):
            false;
    }
    
    /**
     * Check if one list of formal parameters equals another, element by 
     * element, in order.
     **/
    public static boolean equal(List<ElementReference> formalParameters1,
            List<ElementReference> formalParameters2) {
        if (formalParameters1.size() != formalParameters2.size()) {
            return false;
        } else {
            for (int i=0; i<formalParameters1.size(); i++) {
                if (!equal(formalParameters1.get(i), formalParameters2.get(i))){
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
    public static boolean match(List<ElementReference> formalParameters1,
            List<ElementReference> formalParameters2) {
        if (formalParameters1.size() != formalParameters2.size()) {
            return false;
        } else {
            for (int i=0; i<formalParameters1.size(); i++) {
                if (!match(formalParameters1.get(i), formalParameters2.get(i))) {
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
	public static boolean equal(ElementReference parameter1, ElementReference parameter2) {
	    if (!(parameter1.getImpl().isParameter() && parameter2.getImpl().isParameter())) {
	        return false;
	    } else {
	        ElementReference type1 = parameter1.getImpl().getType();
	        ElementReference type2 = parameter2.getImpl().getType();
            
            return  parameter1.getImpl().getDirection().equals(parameter2.getImpl().getDirection()) &&
                    parameter1.getImpl().getName().equals(parameter2.getImpl().getName()) &&
                    parameter1.getImpl().getLower().equals(parameter2.getImpl().getLower()) &&
                    parameter1.getImpl().getUpper().equals(parameter2.getImpl().getUpper()) &&
                    parameter1.getImpl().isOrdered() == parameter2.getImpl().isOrdered() &&
                    parameter1.getImpl().isUnique() == parameter2.getImpl().isUnique() &&
                    type1 != null && type1.getImpl().equals(type2);
	    }
	}

    /**
     * Two formal parameters "match" if they have the same name and type reference.
    **/
    public static boolean match(ElementReference parameter1, ElementReference parameter2) {
        if (!(parameter1.getImpl().isParameter() && parameter2.getImpl().isParameter())) {
            return false;
        } else {
            String name1 = parameter1.getImpl().getName();
            ElementReference type1 = 
                    ElementReferenceImpl.effectiveElementFor(parameter1.getImpl().getType());
            
            String name2 = parameter2.getImpl().getName();
            ElementReference type2 = 
                    ElementReferenceImpl.effectiveElementFor(parameter2.getImpl().getType());
            
            return (name1 == null && name2 == null ||
                        name1 != null && name1.equals(name2)) &&
                   type1 != null && type1.getImpl().equals(type2);
        }
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

}
