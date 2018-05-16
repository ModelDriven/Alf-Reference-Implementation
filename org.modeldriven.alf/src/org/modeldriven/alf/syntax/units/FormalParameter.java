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
import java.util.Collection;
import org.modeldriven.alf.syntax.units.impl.FormalParameterImpl;

/**
 * A typed element definition for the formal parameter of an activity or
 * operation.
 **/

public class FormalParameter extends TypedElementDefinition {

	public FormalParameter() {
		this.impl = new FormalParameterImpl(this);
	}

	public FormalParameter(Parser parser) {
		this();
		this.init(parser);
	}

	public FormalParameter(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
    public FormalParameterImpl getImpl() {
		return (FormalParameterImpl) this.impl;
	}

	public String getDirection() {
		return this.getImpl().getDirection();
	}

	public void setDirection(String direction) {
		this.getImpl().setDirection(direction);
	}

    /**
     * If a formal parameter has direction "out" and a multiplicity lower bound
     * greater than 0, and its owning activity or operation definition has an
     * effective body, then there must be an assignment for the formal parameter
     * after the effective body that has a multiplicity greater than 0.
     */
    public boolean formalParameterAssignmentAfterBody() {
        return this.getImpl().formalParameterAssignmentAfterBody();
    }

        /**
	 * Returns true if the annotation is for a stereotype that has a metaclass
	 * consistent with Parameter.
	 **/
	@Override
    public Boolean annotationAllowed(StereotypeAnnotation annotation) {
		return this.getImpl().annotationAllowed(annotation);
	}

	/**
	 * Return true if the given member is a FormalParameter.
	 **/
	@Override
    public Boolean isSameKindAs(Member member) {
		return this.getImpl().isSameKindAs(member);
	}

	@Override
    public void _deriveAll() {
		super._deriveAll();
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
        if (!this.formalParameterAssignmentAfterBody()) {
            violations.add(new ConstraintViolation(
                    "formalParameterAssignmentAfterBody", this));
        }
	}

	@Override
    public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		s.append(" direction:");
		s.append(this.getDirection());
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
	}
} // FormalParameter
