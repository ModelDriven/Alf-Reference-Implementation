
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.statements;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.Profile;
import org.modeldriven.alf.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.statements.impl.LoopVariableDefinitionImpl;

/**
 * The definition of a loop variable in a for statement.
 **/

public class LoopVariableDefinition extends SyntaxElement {

	public LoopVariableDefinition() {
		this.impl = new LoopVariableDefinitionImpl(this);
	}

	public LoopVariableDefinitionImpl getImpl() {
		return (LoopVariableDefinitionImpl) this.impl;
	}

	public String getVariable() {
		return this.getImpl().getVariable();
	}

	public void setVariable(String variable) {
		this.getImpl().setVariable(variable);
	}

	public Expression getExpression1() {
		return this.getImpl().getExpression1();
	}

	public void setExpression1(Expression expression1) {
		this.getImpl().setExpression1(expression1);
	}

	public Expression getExpression2() {
		return this.getImpl().getExpression2();
	}

	public void setExpression2(Expression expression2) {
		this.getImpl().setExpression2(expression2);
	}

	public QualifiedName getTypeName() {
		return this.getImpl().getTypeName();
	}

	public void setTypeName(QualifiedName typeName) {
		this.getImpl().setTypeName(typeName);
	}

	public Boolean getTypeIsInferred() {
		return this.getImpl().getTypeIsInferred();
	}

	public void setTypeIsInferred(Boolean typeIsInferred) {
		this.getImpl().setTypeIsInferred(typeIsInferred);
	}

	public Boolean getIsCollectionConversion() {
		return this.getImpl().getIsCollectionConversion();
	}

	public void setIsCollectionConversion(Boolean isCollectionConversion) {
		this.getImpl().setIsCollectionConversion(isCollectionConversion);
	}

	public ElementReference getType() {
		return this.getImpl().getType();
	}

	public void setType(ElementReference type) {
		this.getImpl().setType(type);
	}

	public Boolean getIsFirst() {
		return this.getImpl().getIsFirst();
	}

	public void setIsFirst(Boolean isFirst) {
		this.getImpl().setIsFirst(isFirst);
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

	/**
	 * The assignments after a loop variable definition include the assignments
	 * after the expression (or expressions) of the definition plus a new
	 * assigned source for the loop variable itself. The assigned source for the
	 * loop variable is the loop variable definition. The multiplicity upper
	 * bound for the variable is 1. The multiplicity lower bound is 1 if the
	 * loop variable definition is the first in a for statement and 0 otherwise.
	 * If collection conversion is not required, then the variable has the
	 * inferred or declared type from the definition. If collection conversion
	 * is required, then the variable has the argument type of the collection
	 * class.
	 **/
	public boolean loopVariableDefinitionAssignmentAfterDerivation() {
		return this.getImpl().loopVariableDefinitionAssignmentAfterDerivation();
	}

	/**
	 * The assignments before the expressions of a loop variable definition are
	 * the assignments before the loop variable definition.
	 **/
	public boolean loopVariableDefinitionAssignmentsBefore() {
		return this.getImpl().loopVariableDefinitionAssignmentsBefore();
	}

	/**
	 * If a loop variable definition has two expressions, then both expressions
	 * must have type Integer and a multiplicity upper bound of 1, and no name
	 * may be newly assigned or reassigned in more than one of the expressions.
	 **/
	public boolean loopVariableDefinitionRangeExpressions() {
		return this.getImpl().loopVariableDefinitionRangeExpressions();
	}

	/**
	 * If a loop variable definition has a type name, then this name must
	 * resolve to a non-template classifier.
	 **/
	public boolean loopVariableDefinitionTypeName() {
		return this.getImpl().loopVariableDefinitionTypeName();
	}

	/**
	 * If the type of a loop variable is not inferred, then the variable has the
	 * type denoted by the type name, if it is not empty, and is untyped
	 * otherwise. If the type is inferred, them the variable has the same as the
	 * type of the expression in its definition.
	 **/
	public boolean loopVariableDefinitionTypeDerivation() {
		return this.getImpl().loopVariableDefinitionTypeDerivation();
	}

	/**
	 * If the type of a loop variable definition is not inferred, then the first
	 * expression of the definition must have a type that conforms to the
	 * declared type.
	 **/
	public boolean loopVariableDefinitionDeclaredType() {
		return this.getImpl().loopVariableDefinitionDeclaredType();
	}

	/**
	 * Collection conversion is required for a loop variable definition if the
	 * type for the definition is the instantiation of a collection class and
	 * the multiplicity upper bound of the first expression is no greater than
	 * 1.
	 **/
	public boolean loopVariableDefinitionIsCollectionConversionDerivation() {
		return this.getImpl()
				.loopVariableDefinitionIsCollectionConversionDerivation();
	}

	/**
	 * The variable name given in a loop variable definition must be unassigned
	 * after the expression or expressions in the definition.
	 **/
	public boolean loopVariableDefinitionVariable() {
		return this.getImpl().loopVariableDefinitionVariable();
	}

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.loopVariableDefinitionAssignmentAfterDerivation()) {
			violations.add(new ConstraintViolation(
					"loopVariableDefinitionAssignmentAfterDerivation", this));
		}
		if (!this.loopVariableDefinitionAssignmentsBefore()) {
			violations.add(new ConstraintViolation(
					"loopVariableDefinitionAssignmentsBefore", this));
		}
		if (!this.loopVariableDefinitionRangeExpressions()) {
			violations.add(new ConstraintViolation(
					"loopVariableDefinitionRangeExpressions", this));
		}
		if (!this.loopVariableDefinitionTypeName()) {
			violations.add(new ConstraintViolation(
					"loopVariableDefinitionTypeName", this));
		}
		if (!this.loopVariableDefinitionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"loopVariableDefinitionTypeDerivation", this));
		}
		if (!this.loopVariableDefinitionDeclaredType()) {
			violations.add(new ConstraintViolation(
					"loopVariableDefinitionDeclaredType", this));
		}
		if (!this.loopVariableDefinitionIsCollectionConversionDerivation()) {
			violations.add(new ConstraintViolation(
					"loopVariableDefinitionIsCollectionConversionDerivation",
					this));
		}
		if (!this.loopVariableDefinitionVariable()) {
			violations.add(new ConstraintViolation(
					"loopVariableDefinitionVariable", this));
		}
		Expression expression1 = this.getExpression1();
		if (expression1 != null) {
			expression1.checkConstraints(violations);
		}
		Expression expression2 = this.getExpression2();
		if (expression2 != null) {
			expression2.checkConstraints(violations);
		}
		QualifiedName typeName = this.getTypeName();
		if (typeName != null) {
			typeName.checkConstraints(violations);
		}
	}

	public String toString() {
		return "(" + this.hashCode() + ")" + this.getImpl().toString();
	}

	public String _toString() {
		StringBuffer s = new StringBuffer(super._toString());
		s.append(" variable:");
		s.append(this.getVariable());
		s.append(" typeIsInferred:");
		s.append(this.getTypeIsInferred());
		Boolean isCollectionConversion = this.getIsCollectionConversion();
		if (isCollectionConversion != null) {
			s.append(" /isCollectionConversion:");
			s.append(isCollectionConversion);
		}
		Boolean isFirst = this.getIsFirst();
		if (isFirst != null) {
			s.append(" /isFirst:");
			s.append(isFirst);
		}
		return s.toString();
	}

	public void print() {
		this.print("");
	}

	public void print(String prefix) {
		super.print(prefix);
		Expression expression1 = this.getExpression1();
		if (expression1 != null) {
			System.out.println(prefix + " expression1:");
			expression1.print(prefix + "  ");
		}
		Expression expression2 = this.getExpression2();
		if (expression2 != null) {
			System.out.println(prefix + " expression2:");
			expression2.print(prefix + "  ");
		}
		QualifiedName typeName = this.getTypeName();
		if (typeName != null) {
			System.out.println(prefix + " typeName:");
			typeName.print(prefix + "  ");
		}
		ElementReference type = this.getType();
		if (type != null) {
			System.out.println(prefix + " /type:" + type);
		}
		Collection<AssignedSource> assignmentBefore = this
				.getAssignmentBefore();
		if (assignmentBefore != null) {
			if (assignmentBefore.size() > 0) {
				System.out.println(prefix + " /assignmentBefore:");
			}
			for (Object _object : assignmentBefore.toArray()) {
				AssignedSource _assignmentBefore = (AssignedSource) _object;
				System.out.println(prefix + "  " + _assignmentBefore);
			}
		}
		Collection<AssignedSource> assignmentAfter = this.getAssignmentAfter();
		if (assignmentAfter != null) {
			if (assignmentAfter.size() > 0) {
				System.out.println(prefix + " /assignmentAfter:");
			}
			for (Object _object : assignmentAfter.toArray()) {
				AssignedSource _assignmentAfter = (AssignedSource) _object;
				System.out.println(prefix + "  " + _assignmentAfter);
			}
		}
	}
} // LoopVariableDefinition
