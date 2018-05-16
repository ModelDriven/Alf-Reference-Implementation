/*******************************************************************************
 * Copyright 2011, 2017 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.units;

import org.modeldriven.alf.parser.ParsedElement;
import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.statements.*;
import java.util.Collection;
import org.modeldriven.alf.syntax.units.impl.ActivityDefinitionImpl;

/**
 * The definition of an activity, with any formal parameters defined as owned
 * members.
 **/

public class ActivityDefinition extends ClassifierDefinition {

	public ActivityDefinition() {
		this.impl = new ActivityDefinitionImpl(this);
	}

	public ActivityDefinition(Parser parser) {
		this();
		this.init(parser);
	}

	public ActivityDefinition(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
    public ActivityDefinitionImpl getImpl() {
		return (ActivityDefinitionImpl) this.impl;
	}

	public Block getBody() {
		return this.getImpl().getBody();
	}

	public void setBody(Block body) {
		this.getImpl().setBody(body);
	}
	
	public Block getEffectiveBody() {
	    return this.getImpl().getEffectiveBody();
	}
	
    public void setEffectiveBody(Block effectiveBody) {
        this.getImpl().setEffectiveBody(effectiveBody);
    }

	/**
	 * An activity definition may not have a specialization list.
	 **/
	public boolean activityDefinitionSpecialization() {
		return this.getImpl().activityDefinitionSpecialization();
	}

	/**
	 * If an activity definition is primitive, then it must have a body that is
	 * empty.
	 **/
	public boolean activityDefinitionPrimitive() {
		return this.getImpl().activityDefinitionPrimitive();
	}

    /**
     * The assignments before the effective body of an activity definition
     * include an assignment for each "in" or "inout" formal parameter of the
     * activity definition, with the formal parameter as the assigned source.
     */
    public boolean activityDefinitionEffectiveBodyAssignmentsBefore() {
        return this.getImpl().activityDefinitionEffectiveBodyAssignmentsBefore();
    }

    /**
     * If an activity definition has a return parameter with a multiplicity
     * lower bound greater than 0, then the effective body of the activity
     * definition must have a return value.
     */
    public boolean activityDefinitionReturn() {
        return this.getImpl().activityDefinitionReturn();
    }
    
    /**
     * If an activity definition is a stub, then its effective body is the body
     * of the corresponding subunit. Otherwise, the effective body is the same
     * as the body of the activity definition.
     */
    public boolean activityDefinitionEffectiveBodyDerivation() {
        return this.getImpl().activityDefinitionEffectiveBodyDerivation();
    }

	/**
	 * In addition to the annotations allowed for classifiers in general, an
	 * activity definition allows @primitive annotations and any stereotype
	 * whose metaclass is consistent with Activity.
	 **/
	@Override
    public Boolean annotationAllowed(StereotypeAnnotation annotation) {
		return this.getImpl().annotationAllowed(annotation);
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
	@Override
    public Boolean matchForStub(UnitDefinition unit) {
		return this.getImpl().matchForStub(unit);
	}

	@Override
    public Boolean isSameKindAs(Member member) {
		return this.getImpl().isSameKindAs(member);
	}

    @Override
    public void _addExternalReferences(Collection<ExternalElementReference> references) {
        super._addExternalReferences(references);
        addExternalReferencesFor(references, this.getBody());
    }

	@Override
    public void _deriveAll() {
        this.getEffectiveBody();
		super._deriveAll();
        Block body = this.getBody();
        if (body != null) {
            body.deriveAll();
        }
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.activityDefinitionSpecialization()) {
			violations.add(new ConstraintViolation(
					"activityDefinitionSpecialization", this));
		}
        if (!this.activityDefinitionPrimitive()) {
            violations.add(new ConstraintViolation(
                    "activityDefinitionPrimitive", this));
        }
        if (!this.activityDefinitionEffectiveBodyAssignmentsBefore()) {
            violations.add(new ConstraintViolation(
                    "activityDefinitionEffectiveBodyAssignmentsBefore", this));
        }
        if (!this.activityDefinitionReturn()) {
            violations.add(new ConstraintViolation(
                    "activityDefinitionReturn", this));
        }
        if (!this.activityDefinitionEffectiveBodyDerivation()) {
            violations.add(new ConstraintViolation(
                    "activityDefinitionEffectiveBodyDerivation", this));
        }
		Block body = this.getBody();
		if (body != null) {
			body.checkConstraints(violations);
		}
	}

	@Override
    public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		return s.toString();
	}

	@Override
    public void print() {
		this.print("", false);
	}

	@Override
    public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	@Override
    public void print(String prefix, boolean includeDerived) {
		super.print(prefix, includeDerived);
		Block body = this.getBody();
		if (body != null) {
			System.out.println(prefix + " body:");
			body.print(prefix + "  ", includeDerived);
		}
	}
} // ActivityDefinition
