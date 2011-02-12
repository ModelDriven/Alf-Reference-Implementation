
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

import java.util.ArrayList;

import org.modeldriven.alf.syntax.expressions.impl.InvocationExpressionImpl;

/**
 * An expression denoting the invocation of a behavior or operation, or the
 * sending of a signal.
 **/

public abstract class InvocationExpression extends Expression {

	private Boolean isBehavior = null; // DERIVED
	private Boolean isAssociationEnd = null; // DERIVED
	private FeatureReference feature = null; // DERIVED
	private Tuple tuple = null;
	private Boolean isOperation = null; // DERIVED
	private Boolean isDestructor = null; // DERIVED
	private Boolean isImplicit = null; // DERIVED
	private ElementReference referent = null; // DERIVED
	private ArrayList<ElementReference> parameter = null; // DERIVED
	private Boolean isSignal = null; // DERIVED

	public InvocationExpressionImpl getImpl() {
		return (InvocationExpressionImpl) this.impl;
	}

	public Boolean getIsBehavior() {
		if (this.isBehavior == null) {
			this.isBehavior = this.getImpl().deriveIsBehavior();
		}
		return this.isBehavior;
	}

	public Boolean getIsAssociationEnd() {
		if (this.isAssociationEnd == null) {
			this.isAssociationEnd = this.getImpl().deriveIsAssociationEnd();
		}
		return this.isAssociationEnd;
	}

	public FeatureReference getFeature() {
		if (this.feature == null) {
			this.feature = this.getImpl().deriveFeature();
		}
		return this.feature;
	}

	public Tuple getTuple() {
		return this.tuple;
	}

	public void setTuple(Tuple tuple) {
		this.tuple = tuple;
	}

	public Boolean getIsOperation() {
		if (this.isOperation == null) {
			this.isOperation = this.getImpl().deriveIsOperation();
		}
		return this.isOperation;
	}

	public Boolean getIsDestructor() {
		if (this.isDestructor == null) {
			this.isDestructor = this.getImpl().deriveIsDestructor();
		}
		return this.isDestructor;
	}

	public Boolean getIsImplicit() {
		if (this.isImplicit == null) {
			this.isImplicit = this.getImpl().deriveIsImplicit();
		}
		return this.isImplicit;
	}

	public ElementReference getReferent() {
		if (this.referent == null) {
			this.referent = this.getImpl().deriveReferent();
		}
		return this.referent;
	}

	public ArrayList<ElementReference> getParameter() {
		if (this.parameter == null) {
			this.parameter = this.getImpl().deriveParameter();
		}
		return this.parameter;
	}

	public Boolean getIsSignal() {
		if (this.isSignal == null) {
			this.isSignal = this.getImpl().deriveIsSignal();
		}
		return this.isSignal;
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
	public ArrayList<ElementReference> parameterElements() {
		return this.getImpl().parameterElements();
	}

	/**
	 * The assignments after an invocation expression are the same as those
	 * after the tuple of the expression.
	 **/
	public ArrayList<AssignedSource> updateAssignments() {
		return this.getImpl().updateAssignments();
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
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

	public void print(String prefix) {
		super.print(prefix);
		FeatureReference feature = this.getFeature();
		if (feature != null) {
			System.out.println(prefix + " /" + feature);
		}
		Tuple tuple = this.getTuple();
		if (tuple != null) {
			tuple.print(prefix + " ");
		}
		ElementReference referent = this.getReferent();
		if (referent != null) {
			System.out.println(prefix + " /" + referent);
		}
		ArrayList<ElementReference> parameter = this.getParameter();
		if (parameter != null) {
			for (ElementReference item : this.getParameter()) {
				System.out.println(prefix + " /" + item);
			}
		}
	}
} // InvocationExpression
