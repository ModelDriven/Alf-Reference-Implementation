
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units.impl;

import java.util.List;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.SyntaxElement;
import org.modeldriven.alf.syntax.statements.Block;
import org.modeldriven.alf.syntax.units.*;

/**
 * The definition of an activity, with any formal parameters defined as owned
 * members.
 **/

public class ActivityDefinitionImpl extends ClassifierDefinitionImpl {

    private Block body = null;

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
	    // TODO: Allow activity stereotypes.
		return super.annotationAllowed(annotation) || 
		    annotation.getStereotypeName().getPathName().equals("primitive");
	} // annotationAllowed

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
		    FormalParameterImpl.equals(this.getFormalParameters(), 
		            ((ActivityDefinition)definition).getImpl().getFormalParameters());
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

    public Block getEffectiveBody() {
        return this.getEffectiveBody(this.getSelf().getSubunit());
    }
    
    private Block getEffectiveBody(UnitDefinition subunit) {
        ActivityDefinition self = this.getSelf();
        if (subunit == null) {
            return self.getBody();
        } else {
            NamespaceDefinition definition = subunit.getDefinition();
            return definition instanceof ActivityDefinition?
                        ((ActivityDefinition)definition).getBody():
                        null;
        }
    }
    
    public ElementReference getType() {
        FormalParameter returnParameter = this.getReturnParameter();
        return returnParameter == null? null: returnParameter.getType();
    }

    public int getLower() {
        FormalParameter returnParameter = this.getReturnParameter();
        return returnParameter == null? 0: returnParameter.getLower();
    }
    
    public int getUpper() {
        FormalParameter returnParameter = this.getReturnParameter();
        return returnParameter == null? 0: returnParameter.getUpper();
    }
    
    public boolean isClassifierBehavior() {
        ElementReference namespace =  this.getNamespaceReference();
        return namespace != null && 
            this.getReferent().getImpl().
                equals(namespace.getImpl().getClassifierBehavior());
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
