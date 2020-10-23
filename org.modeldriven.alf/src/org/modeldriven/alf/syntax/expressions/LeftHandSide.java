/*******************************************************************************
 * Copyright 2011, 2017, 2020 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions;

import java.util.Collection;

import org.modeldriven.alf.syntax.common.AssignedSource;
import org.modeldriven.alf.syntax.common.ConstraintViolation;
import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.ExternalElementReference;
import org.modeldriven.alf.syntax.common.SyntaxElement;
import org.modeldriven.alf.syntax.expressions.impl.LeftHandSideImpl;

/**
 * The left-hand side of an assignment expression.
 * 
 * NOTE: The derivations for the derived properties of LeftHandSide are specific
 * to its various subclasses.
 **/

public abstract class LeftHandSide extends SyntaxElement {

	@Override
    public LeftHandSideImpl getImpl() {
		return (LeftHandSideImpl) this.impl;
	}

	public Collection<AssignedSource> getAssignmentBefore() {
		return this.getImpl().getAssignmentBefore();
	}

	public void setAssignmentBefore(Collection<AssignedSource> assignmentBefore) {
		this.getImpl().setAssignmentBefore(assignmentBefore);
	}

	public void addAssignmentBefore(AssignedSource assignmentBefore) {
		this.getImpl().addAssignmentBefore(assignmentBefore);
	}

	public Collection<AssignedSource> getAssignmentAfter() {
		return this.getImpl().getAssignmentAfter();
	}

	public void setAssignmentAfter(Collection<AssignedSource> assignmentAfter) {
		this.getImpl().setAssignmentAfter(assignmentAfter);
	}

	public void addAssignmentAfter(AssignedSource assignmentAfter) {
		this.getImpl().addAssignmentAfter(assignmentAfter);
	}

	public Expression getIndex() {
		return this.getImpl().getIndex();
	}

	public void setIndex(Expression index) {
		this.getImpl().setIndex(index);
	}

	public ElementReference getReferent() {
		return this.getImpl().getReferent();
	}

	public void setReferent(ElementReference referent) {
		this.getImpl().setReferent(referent);
	}

	public ElementReference getType() {
		return this.getImpl().getType();
	}

	public void setType(ElementReference type) {
		this.getImpl().setType(type);
	}

	public Integer getLower() {
		return this.getImpl().getLower();
	}

	public void setLower(Integer lower) {
		this.getImpl().setLower(lower);
	}

	public Integer getUpper() {
		return this.getImpl().getUpper();
	}

	public void setUpper(Integer upper) {
		this.getImpl().setUpper(upper);
	}

	/**
	 * If a left-hand side has an index, then the index expression must have a
	 * multiplicity upper bound no greater than 1.
	 **/
	public boolean leftHandSideIndexExpression() {
		return this.getImpl().leftHandSideIndexExpression();
	}

	/**
	 * If a left-hand side is for a feature reference whose expression is a data type,
	 * then it must be a legal data value update.
	 */
	public boolean leftHandSideDataValueUpdateLegality() {
        return this.getImpl().leftHandSideDataValueUpdateLegality();
	}
	
    @Override
    public void _addExternalReferences(Collection<ExternalElementReference> references) {
        super._addExternalReferences(references);
        addExternalReferencesFor(references, this.getIndex());
    }

	@Override
    public void _deriveAll() {
		this.getAssignmentBefore();
		this.getAssignmentAfter();
		this.getReferent();
		this.getType();
		this.getLower();
		this.getUpper();
		super._deriveAll();
		Expression index = this.getIndex();
		if (index != null) {
			index.deriveAll();
		}
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.leftHandSideIndexExpression()) {
			violations.add(new ConstraintViolation(
					"leftHandSideIndexExpression", this));
		}
		if (!this.leftHandSideDataValueUpdateLegality()) {
			violations.add(new ConstraintViolation(
					"leftHandSideDataValueUpdateLegality", this));
		}
		Expression index = this.getIndex();
		if (index != null) {
			index.checkConstraints(violations);
		}
	}

	@Override
    public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		if (includeDerived) {
			s.append(" /lower:");
			s.append(this.getLower());
		}
		if (includeDerived) {
			s.append(" /upper:");
			s.append(this.getUpper());
		}
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
		if (includeDerived) {
			Collection<AssignedSource> assignmentBefore = this
					.getAssignmentBefore();
			if (assignmentBefore != null && assignmentBefore.size() > 0) {
				System.out.println(prefix + " /assignmentBefore:");
				for (Object _object : assignmentBefore.toArray()) {
					AssignedSource _assignmentBefore = (AssignedSource) _object;
					System.out.println(prefix + "  "
							+ _assignmentBefore.toString(includeDerived));
				}
			}
		}
		if (includeDerived) {
			Collection<AssignedSource> assignmentAfter = this
					.getAssignmentAfter();
			if (assignmentAfter != null && assignmentAfter.size() > 0) {
				System.out.println(prefix + " /assignmentAfter:");
				for (Object _object : assignmentAfter.toArray()) {
					AssignedSource _assignmentAfter = (AssignedSource) _object;
					System.out.println(prefix + "  "
							+ _assignmentAfter.toString(includeDerived));
				}
			}
		}
		Expression index = this.getIndex();
		if (index != null) {
			System.out.println(prefix + " index:");
			index.print(prefix + "  ", includeDerived);
		}
		if (includeDerived) {
			ElementReference referent = this.getReferent();
			if (referent != null) {
				System.out.println(prefix + " /referent:"
						+ referent.toString(includeDerived));
			}
		}
		if (includeDerived) {
			ElementReference type = this.getType();
			if (type != null) {
				System.out.println(prefix + " /type:"
						+ type.toString(includeDerived));
			}
		}
	}
} // LeftHandSide
