
/*
 * Copyright 2010 Data Access Technologies, Inc. (Model Driven Solutions)
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

/**
 * An expression denoting the invocation of a behavior or operation, or the
 * sending of a signal.
 **/

public abstract class InvocationExpression extends Expression {

	private boolean isBehavior = false; // DERIVED
	private boolean isAssociationEnd = false; // DERIVED
	private FeatureReference feature = null; // DERIVED
	private Tuple tuple = null;
	private boolean isOperation = false; // DERIVED
	private boolean isDestructor = false; // DERIVED
	private boolean isImplicit = false; // DERIVED
	private ElementReference referent = null; // DERIVED
	private ArrayList<ElementReference> parameter = new ArrayList<ElementReference>(); // DERIVED
	private boolean isSignal = false; // DERIVED

	public boolean getIsBehavior() {
		return this.isBehavior;
	}

	public void setIsBehavior(boolean isBehavior) {
		this.isBehavior = isBehavior;
	}

	public boolean getIsAssociationEnd() {
		return this.isAssociationEnd;
	}

	public void setIsAssociationEnd(boolean isAssociationEnd) {
		this.isAssociationEnd = isAssociationEnd;
	}

	public FeatureReference getFeature() {
		return this.feature;
	}

	public void setFeature(FeatureReference feature) {
		this.feature = feature;
	}

	public Tuple getTuple() {
		return this.tuple;
	}

	public void setTuple(Tuple tuple) {
		this.tuple = tuple;
	}

	public boolean getIsOperation() {
		return this.isOperation;
	}

	public void setIsOperation(boolean isOperation) {
		this.isOperation = isOperation;
	}

	public boolean getIsDestructor() {
		return this.isDestructor;
	}

	public void setIsDestructor(boolean isDestructor) {
		this.isDestructor = isDestructor;
	}

	public boolean getIsImplicit() {
		return this.isImplicit;
	}

	public void setIsImplicit(boolean isImplicit) {
		this.isImplicit = isImplicit;
	}

	public ElementReference getReferent() {
		return this.referent;
	}

	public void setReferent(ElementReference referent) {
		this.referent = referent;
	}

	public ArrayList<ElementReference> getParameter() {
		return this.parameter;
	}

	public void setParameter(ArrayList<ElementReference> parameter) {
		this.parameter = parameter;
	}

	public void addParameter(ElementReference parameter) {
		this.parameter.add(parameter);
	}

	public boolean getIsSignal() {
		return this.isSignal;
	}

	public void setIsSignal(boolean isSignal) {
		this.isSignal = isSignal;
	}

	public ArrayList<ElementReference> parameterElements() {
		/*
		 * Returns references to the elements that act as the parameters of the
		 * referent. For a behavior or operation, these are the owned
		 * parameters, in order. Otherwise (by default), they are actually any
		 * properties of the referent (e.g., signal attributes), which are
		 * treated as if they were in parameters. (This is defined as a helper
		 * operation, so that it can be overridden by subclasses of
		 * InvocationExpression, if necessary.)
		 */
		return new ArrayList<ElementReference>(); // STUB
	} // parameterElements

	public ArrayList<AssignedSource> updateAssignments() {
		/*
		 * The assignments after an invocation expression are the same as those
		 * after the tuple of the expression.
		 */
		return new ArrayList<AssignedSource>(); // STUB
	} // updateAssignments

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		if (this.tuple != null) {
			this.tuple.print(prefix + " ");
		}
	}
} // InvocationExpression
