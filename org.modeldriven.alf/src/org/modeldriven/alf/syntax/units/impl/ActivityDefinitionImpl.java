/*******************************************************************************
 * Copyright 2011-2017 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.units.impl;

import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.SyntaxElement;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.statements.Block;
import org.modeldriven.alf.syntax.statements.QualifiedNameList;
import org.modeldriven.alf.syntax.units.*;

/**
 * The definition of an activity, with any formal parameters defined as owned
 * members.
 **/

public class ActivityDefinitionImpl extends ClassifierDefinitionImpl {

    private Block body = null;
    private Block effectiveBody = null; // DERIVED

	public ActivityDefinitionImpl(ActivityDefinition self) {
		super(self);
	}

    @Override
	public ActivityDefinition getSelf() {
		return (ActivityDefinition) this.self;
	}
    
    public Block getBody() {
        return this.body;
    }

    public void setBody(Block body) {
        this.body = body;
        if (body != null) {
            body.getImpl().setCurrentScope(this.getSelf());
        }
    }
    
    public Block getEffectiveBody() {
        if (this.effectiveBody == null) {
            this.setEffectiveBody(this.deriveEffectiveBody());
        }
        return this.effectiveBody;
    }
    
    public void setEffectiveBody(Block effectiveBody) {
        this.effectiveBody = effectiveBody;
    }
    
    /**
     * If an activity definition is a stub, then its effective body is the body
     * of the corresponding subunit. Otherwise, the effective body is the same
     * as the body of the activity definition.
     */
    public Block deriveEffectiveBody() {
        ActivityDefinition self = this.getSelf();
        UnitDefinition subunit = self.getSubunit();
        if (subunit == null) {
            return self.getBody();
        } else {
            NamespaceDefinition definition = subunit.getDefinition();
            return definition instanceof ActivityDefinition?
                        ((ActivityDefinition)definition).getBody():
                        null;
        }
    }
        
    /*
     * Derivations
     */

    public boolean activityDefinitionEffectiveBodyDerivation() {
        this.getSelf().getEffectiveBody();
        return true;
    }

    /*
	 * Constraints
	 */

	/**
	 * An activity definition may not have a specialization list.
	 **/
	public boolean activityDefinitionSpecialization() {
		return this.getSelf().getSpecialization() == null;
	}

	/**
	 * If an activity definition is primitive, then it must have a body that is
	 * empty.
	 **/
	public boolean activityDefinitionPrimitive() {
	    ActivityDefinition self = this.getSelf();
		return !self.getIsPrimitive() || self.getBody() == null || 
		        self.getBody().getStatement().isEmpty();
	}
	
    /**
     * The assignments before the effective body of an activity definition
     * include an assignment for each "in" or "inout" formal parameter of the
     * activity definition, with the formal parameter as the assigned source.
     */
    public boolean activityDefinitionEffectiveBodyAssignmentsBefore() {
        // This handled by BlockImpl::deriveAssignmentsAfter.
        return true;
    }
    
    /**
     * If an activity definition is not primitive and has a return parameter
     * with a multiplicity lower bound greater than 0, then the effective body
     * of the activity definition must have a return value.
     */
    public boolean activityDefinitionReturn() {
        ActivityDefinition self = this.getSelf();
        ElementReference returnParameter = this.getReturnParameter();
        Block body = self.getEffectiveBody();
        return self.getIsPrimitive() || returnParameter == null || 
               returnParameter.getImpl().getLower() == 0 ||
               body == null || body.hasReturnValue();
    }

	/*
	 * Helper Methods
	 */

	/**
	 * In addition to the annotations allowed for classifiers in general, an
	 * activity definition allows @primitive annotations and any stereotype
	 * whose metaclass is consistent with Activity.
	 **/
    @Override
	public Boolean annotationAllowed(StereotypeAnnotation annotation) {
		return super.annotationAllowed(annotation) || 
		    annotation.getStereotypeName().getPathName().equals("primitive");
	}
    
    @Override
    public Class<?> getUMLMetaclass() {
        return org.modeldriven.alf.uml.Activity.class;
    }

	/**
	 * Returns true if the given unit definition matches this activity
	 * definition considered as a classifier definition and the subunit is for
	 * an activity definition. In addition, the subunit definition must have
	 * formal parameters that match each of the formal parameters of the stub
	 * definition, in order. Two formal parameters match if they have the same
	 * direction, name, multiplicity bounds, ordering, uniqueness and type
	 * reference.
	 **/
	public Boolean matchForStub(UnitDefinition unit) {
	    NamespaceDefinition definition = unit.getDefinition();
		return definition instanceof ActivityDefinition && 
		    super.matchForStub(unit) &&
		    FormalParameterImpl.equal(this.getParameters(), 
		            ((ActivityDefinition)definition).getImpl().getParameters());
	} // matchForStub

    /**
	 * Return true if the given member is either an ActivityDefinition or an
	 * imported member whose referent is an ActivityDefinition or an Activity.
	 **/
	@Override
	public Boolean isSameKindAs(Member member) {
	    return member.getImpl().getReferent().getImpl().isActivity();
	} // isSameKindAs

	/*
	 * Helper Methods
	 */

    public ElementReference getType() {
        ElementReference returnParameter = this.getReturnParameter();
        return returnParameter == null? any: returnParameter.getImpl().getType();
    }

    public int getLower() {
        ElementReference returnParameter = this.getReturnParameter();
        return returnParameter == null? 0: returnParameter.getImpl().getLower();
    }
    
    public int getUpper() {
        ElementReference returnParameter = this.getReturnParameter();
        return returnParameter == null? 0: returnParameter.getImpl().getUpper();
    }
    
    public boolean isClassifierBehavior() {
        Member stub = this.getStub();
        return isClassifierBehavior(stub != null? stub: this.getSelf());
    }
    
    private static boolean isClassifierBehavior(Member member) {
        ElementReference namespace = member.getImpl().getNamespaceReference();
        return namespace != null && 
                member.getImpl().getReferent().getImpl().
                    equals(namespace.getImpl().getClassifierBehavior());
    }
    
    @Override
    public ElementReference getContext() {
        Member stub = this.getStub();
        return stub != null? stub.getImpl().getContext():
            isClassifierBehavior(this.getSelf())? this.getNamespaceReference(): 
            this.getReferent();
    }
    
    public String getPrimitiveBehaviorPrototypeName() {
        for (StereotypeAnnotation annotation: this.getSelf().getAnnotation()) {
            if (annotation.getStereotypeName().getPathName().equals("primitive")) {
                QualifiedNameList nameList = annotation.getNames();
                if (nameList != null) {
                    Collection<QualifiedName> names = nameList.getName();
                    if (!names.isEmpty()) {
                        return ((QualifiedName)names.toArray()[0]).getPathName();
                    }
                } else {
                    TaggedValueList taggedValues = annotation.getTaggedValues();
                    if (taggedValues != null) {
                        String value = taggedValues.getImpl().getValue("implementation");
                        if (value != null && value.length() > 2 && value.charAt(0) == '"') {
                            value = value.substring(1, value.length() - 1);
                        }
                        return value;
                    }
                }
            }
        }
        return null;
     }
    
    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof ActivityDefinition) {
            Block body = ((ActivityDefinition)base).getImpl().getEffectiveBody();
            if (body != null) {
                this.getSelf().setBody((Block)body.getImpl().
                        bind(templateParameters, templateArguments));
            }
        }
    }

} // ActivityDefinitionImpl
