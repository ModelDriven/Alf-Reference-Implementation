
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.omg.uml.Element;
import org.omg.uml.Profile;
import org.omg.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.expressions.impl.InvocationExpressionImpl;

/**
 * An expression denoting the invocation of a behavior or operation, or the
 * sending of a signal.
 **/

public abstract class InvocationExpression extends Expression {

	public InvocationExpressionImpl getImpl() {
		return (InvocationExpressionImpl) this.impl;
	}

	public Boolean getIsBehavior() {
		return this.getImpl().getIsBehavior();
	}

	public void setIsBehavior(Boolean isBehavior) {
		this.getImpl().setIsBehavior(isBehavior);
	}

	public Boolean getIsAssociationEnd() {
		return this.getImpl().getIsAssociationEnd();
	}

	public void setIsAssociationEnd(Boolean isAssociationEnd) {
		this.getImpl().setIsAssociationEnd(isAssociationEnd);
	}

	public FeatureReference getFeature() {
		return this.getImpl().getFeature();
	}

	public void setFeature(FeatureReference feature) {
		this.getImpl().setFeature(feature);
	}

	public Tuple getTuple() {
		return this.getImpl().getTuple();
	}

	public void setTuple(Tuple tuple) {
		this.getImpl().setTuple(tuple);
	}

	public Boolean getIsOperation() {
		return this.getImpl().getIsOperation();
	}

	public void setIsOperation(Boolean isOperation) {
		this.getImpl().setIsOperation(isOperation);
	}

	public Boolean getIsDestructor() {
		return this.getImpl().getIsDestructor();
	}

	public void setIsDestructor(Boolean isDestructor) {
		this.getImpl().setIsDestructor(isDestructor);
	}

	public Boolean getIsImplicit() {
		return this.getImpl().getIsImplicit();
	}

	public void setIsImplicit(Boolean isImplicit) {
		this.getImpl().setIsImplicit(isImplicit);
	}

	public ElementReference getReferent() {
		return this.getImpl().getReferent();
	}

	public void setReferent(ElementReference referent) {
		this.getImpl().setReferent(referent);
	}

	public List<ElementReference> getParameter() {
		return this.getImpl().getParameter();
	}

	public void setParameter(List<ElementReference> parameter) {
		this.getImpl().setParameter(parameter);
	}

	public void addParameter(ElementReference parameter) {
		this.getImpl().addParameter(parameter);
	}

	public Boolean getIsSignal() {
		return this.getImpl().getIsSignal();
	}

	public void setIsSignal(Boolean isSignal) {
		this.getImpl().setIsSignal(isSignal);
	}

	/**
	 * An invocation expression is a behavior invocation if its referent is a
	 * behavior.
	 **/
	public boolean invocationExpressionIsBehaviorDerivation() {
		return this.getImpl().invocationExpressionIsBehaviorDerivation();
	}

	/**
	 * An invocation expression is an association end read if its referent is an
	 * association end.
	 **/
	public boolean invocationExpressionIsAssociationEndDerivation() {
		return this.getImpl().invocationExpressionIsAssociationEndDerivation();
	}

	/**
	 * An invocation expression is an operation call if its referent is an
	 * operation.
	 **/
	public boolean invocationExpressionIsOperationDerivation() {
		return this.getImpl().invocationExpressionIsOperationDerivation();
	}

	/**
	 * An invocation expression is a destructor call either implicitly or if it
	 * is an explicit operation call to a destructor operation.
	 **/
	public boolean invocationExpressionIsDestructorDerivation() {
		return this.getImpl().invocationExpressionIsDestructorDerivation();
	}

	/**
	 * An invocation expression is an implicit object destruction if it has a
	 * feature with the name "destroy" and no explicit referents.
	 **/
	public boolean invocationExpressionIsImplicitDerivation() {
		return this.getImpl().invocationExpressionIsImplicitDerivation();
	}

	/**
	 * An invocation expression is a signal send if its referent is a signal.
	 **/
	public boolean invocationExpressionIsSignalDerivation() {
		return this.getImpl().invocationExpressionIsSignalDerivation();
	}

	/**
	 * The parameters of an invocation expression are given by the result of the
	 * parameterElements helper operation.
	 **/
	public boolean invocationExpressionParameterDerivation() {
		return this.getImpl().invocationExpressionParameterDerivation();
	}

	/**
	 * The type of an invocation expression is determined by the return
	 * parameter (if any) of the referent.
	 **/
	public boolean invocationExpressionTypeDerivation() {
		return this.getImpl().invocationExpressionTypeDerivation();
	}

	/**
	 * The multiplicity upper bound of an invocation expression is determined by
	 * the return parameter (if any) of the referent.
	 **/
	public boolean invocationExpressionUpperDerivation() {
		return this.getImpl().invocationExpressionUpperDerivation();
	}

	/**
	 * The multiplicity lower bound of an invocation expression is determined by
	 * the return parameter (if any) of the referent.
	 **/
	public boolean invocationExpressionLowerDerivation() {
		return this.getImpl().invocationExpressionLowerDerivation();
	}

	/**
	 * The assignments before the target expression of the feature reference of
	 * an invocation expression (if any) are the same as the assignments before
	 * the invocation expression.
	 **/
	public boolean invocationExpressionAssignmentsBefore() {
		return this.getImpl().invocationExpressionAssignmentsBefore();
	}

	/**
	 * Returns references to the elements that act as the parameters of the
	 * referent. For a behavior or operation, these are the owned parameters, in
	 * order. Otherwise (by default), they are actually any properties of the
	 * referent (e.g., signal attributes), which are treated as if they were in
	 * parameters. (This is defined as a helper operation, so that it can be
	 * overridden by subclasses of InvocationExpression, if necessary.)
	 **/
	public Collection<ElementReference> parameterElements() {
		return this.getImpl().parameterElements();
	}

	/**
	 * The assignments after an invocation expression are the same as those
	 * after the tuple of the expression.
	 **/
	public Collection<AssignedSource> updateAssignments() {
		return this.getImpl().updateAssignments();
	}

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.invocationExpressionIsBehaviorDerivation()) {
			violations.add(new ConstraintViolation(
					"invocationExpressionIsBehaviorDerivation", this));
		}
		if (!this.invocationExpressionIsAssociationEndDerivation()) {
			violations.add(new ConstraintViolation(
					"invocationExpressionIsAssociationEndDerivation", this));
		}
		if (!this.invocationExpressionIsOperationDerivation()) {
			violations.add(new ConstraintViolation(
					"invocationExpressionIsOperationDerivation", this));
		}
		if (!this.invocationExpressionIsDestructorDerivation()) {
			violations.add(new ConstraintViolation(
					"invocationExpressionIsDestructorDerivation", this));
		}
		if (!this.invocationExpressionIsImplicitDerivation()) {
			violations.add(new ConstraintViolation(
					"invocationExpressionIsImplicitDerivation", this));
		}
		if (!this.invocationExpressionIsSignalDerivation()) {
			violations.add(new ConstraintViolation(
					"invocationExpressionIsSignalDerivation", this));
		}
		if (!this.invocationExpressionParameterDerivation()) {
			violations.add(new ConstraintViolation(
					"invocationExpressionParameterDerivation", this));
		}
		if (!this.invocationExpressionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"invocationExpressionTypeDerivation", this));
		}
		if (!this.invocationExpressionUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"invocationExpressionUpperDerivation", this));
		}
		if (!this.invocationExpressionLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"invocationExpressionLowerDerivation", this));
		}
		if (!this.invocationExpressionAssignmentsBefore()) {
			violations.add(new ConstraintViolation(
					"invocationExpressionAssignmentsBefore", this));
		}
		Tuple tuple = this.getTuple();
		if (tuple != null) {
			tuple.checkConstraints(violations);
		}
	}

	public String toString() {
		return "(" + this.hashCode() + ")" + this.getImpl().toString();
	}

	public String _toString() {
		StringBuffer s = new StringBuffer(super._toString());
		Boolean isBehavior = this.getIsBehavior();
		if (isBehavior != null) {
			s.append(" /isBehavior:");
			s.append(isBehavior);
		}
		Boolean isAssociationEnd = this.getIsAssociationEnd();
		if (isAssociationEnd != null) {
			s.append(" /isAssociationEnd:");
			s.append(isAssociationEnd);
		}
		Boolean isOperation = this.getIsOperation();
		if (isOperation != null) {
			s.append(" /isOperation:");
			s.append(isOperation);
		}
		Boolean isDestructor = this.getIsDestructor();
		if (isDestructor != null) {
			s.append(" /isDestructor:");
			s.append(isDestructor);
		}
		Boolean isImplicit = this.getIsImplicit();
		if (isImplicit != null) {
			s.append(" /isImplicit:");
			s.append(isImplicit);
		}
		Boolean isSignal = this.getIsSignal();
		if (isSignal != null) {
			s.append(" /isSignal:");
			s.append(isSignal);
		}
		return s.toString();
	}

	public void print() {
		this.print("");
	}

	public void print(String prefix) {
		super.print(prefix);
		FeatureReference feature = this.getFeature();
		if (feature != null) {
			System.out.println(prefix + " /feature:" + feature);
		}
		Tuple tuple = this.getTuple();
		if (tuple != null) {
			System.out.println(prefix + " tuple:");
			tuple.print(prefix + "  ");
		}
		ElementReference referent = this.getReferent();
		if (referent != null) {
			System.out.println(prefix + " /referent:" + referent);
		}
		List<ElementReference> parameter = this.getParameter();
		if (parameter != null) {
			if (parameter.size() > 0) {
				System.out.println(prefix + " /parameter:");
			}
			for (Object _object : parameter.toArray()) {
				ElementReference _parameter = (ElementReference) _object;
				System.out.println(prefix + "  " + _parameter);
			}
		}
	}
} // InvocationExpression
