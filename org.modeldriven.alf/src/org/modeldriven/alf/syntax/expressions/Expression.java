/*******************************************************************************
 * Copyright 2011, 2016 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.parser.Token;

import org.modeldriven.alf.syntax.common.*;
import java.util.Collection;
import org.modeldriven.alf.syntax.expressions.impl.ExpressionImpl;

/**
 * A model of the common properties derived for any Alf expression.
 * 
 * NOTE: The derivations for all properties of Expression except
 * AssignmentsAfter are specific to its various subclasses.
 **/

public abstract class Expression extends SyntaxElement {

	public Expression() {
	}

	public Expression(Parser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public Expression(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public ExpressionImpl getImpl() {
		return (ExpressionImpl) this.impl;
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

	public Integer getUpper() {
		return this.getImpl().getUpper();
	}

	public void setUpper(Integer upper) {
		this.getImpl().setUpper(upper);
	}

	public Integer getLower() {
		return this.getImpl().getLower();
	}

	public void setLower(Integer lower) {
		this.getImpl().setLower(lower);
	}

	public ElementReference getType() {
		return this.getImpl().getType();
	}

	public void setType(ElementReference type) {
		this.getImpl().setType(type);
	}

	/**
	 * The assignments after an expression are given by the result of the
	 * updateAssignments helper operation.
	 **/
	public boolean expressionAssignmentAfterDerivation() {
		return this.getImpl().expressionAssignmentAfterDerivation();
	}

	/**
	 * No name may be assigned more than once before or after an expression.
	 **/
	public boolean expressionUniqueAssignments() {
		return this.getImpl().expressionUniqueAssignments();
	}
	
	/**
     * Return the type of the expression, based on the originally declared  
     * types of names in the expression. By default, this is the expression
     * type.
     */
	public ElementReference declaredType() {
	    return this.getImpl().declaredType();
	}

	/**
	 * Returns the assignments from before this expression updated for any
	 * assignments made in the expression. By default, this is the same set as
	 * the assignments before the expression. This operation is redefined only
	 * in subclasses of Expression for kinds of expressions that make
	 * assignments.
	 **/
	public Collection<AssignedSource> updateAssignments() {
		return this.getImpl().updateAssignments();
	}
	
    /**
     * Returns the given assignments, adjusted for known nulls, known non-nulls,
     * and best known types, based on the given truth condition. By default, no
     * changes are made. (This operation is overridden for conditional logical,
     * binary unary, equality, behavior invocation, sequence operation and
     * classification expressions that may be used to form checks for null and
     * non-null values and type classification.)
     */
    public Collection<AssignedSource> adjustAssignments(
            Collection<AssignedSource> assignments, boolean condition) {
        return this.getImpl().adjustAssignments(assignments, condition);
    }

    /**
     * Returns the given assignments, adjusted for known nulls and non-nulls,
     * based on the given truth condition. By default, no changes are made.
     * (This operation is overridden for name and assignment expressions that
     * may be used to provide the names that are checked for type classification.)
     */
    public Collection<AssignedSource> adjustMultiplicity(
            Collection<AssignedSource> assignments, boolean condition) {
        return this.getImpl().adjustMultiplicity(assignments, condition);
    }

    /**
     * Returns the given assignments, adjusted for the given best-known subtype.
     * By default, no changes are made. (This operation is overridden by name
     * and assignment expressions that may be used to provide the names that are
     * checked for being null or non-null.)
     */
    public Collection<AssignedSource> adjustType(
            Collection<AssignedSource> assignments, ElementReference subtype) {
        return this.getImpl().adjustType(assignments, subtype);
    }

	public void _deriveAll() {
		this.getAssignmentBefore();
		this.getAssignmentAfter();
		this.getUpper();
		this.getLower();
		this.getType();
		super._deriveAll();
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.expressionAssignmentAfterDerivation()) {
			violations.add(new ConstraintViolation(
					"expressionAssignmentAfterDerivation", this));
		}
		if (!this.expressionUniqueAssignments()) {
			violations.add(new ConstraintViolation(
					"expressionUniqueAssignments", this));
		}
	}

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		if (includeDerived) {
			s.append(" /upper:");
			s.append(this.getUpper());
		}
		if (includeDerived) {
			s.append(" /lower:");
			s.append(this.getLower());
		}
		return s.toString();
	}

	public void print() {
		this.print("", false);
	}

	public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

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
		if (includeDerived) {
			ElementReference type = this.getType();
			if (type != null) {
				System.out.println(prefix + " /type:"
						+ type.toString(includeDerived));
			}
		}
	}
} // Expression
