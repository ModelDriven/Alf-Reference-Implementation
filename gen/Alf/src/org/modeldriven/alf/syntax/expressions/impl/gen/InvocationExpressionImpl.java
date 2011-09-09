
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions.impl.gen;

import org.modeldriven.alf.parser.AlfParser;

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

/**
 * An expression denoting the invocation of a behavior or operation, or the
 * sending of a signal.
 **/

public abstract class InvocationExpressionImpl extends
		org.modeldriven.alf.syntax.expressions.impl.gen.ExpressionImpl {

	private Boolean isBehavior = null; // DERIVED
	private Boolean isAssociationEnd = null; // DERIVED
	private FeatureReference feature = null; // DERIVED
	private Tuple tuple = null;
	private Boolean isOperation = null; // DERIVED
	private Boolean isDestructor = null; // DERIVED
	private Boolean isImplicit = null; // DERIVED
	private ElementReference referent = null; // DERIVED
	private List<ElementReference> parameter = null; // DERIVED
	private Boolean isSignal = null; // DERIVED

	public InvocationExpressionImpl(InvocationExpression self) {
		super(self);
	}

	public InvocationExpression getSelf() {
		return (InvocationExpression) this.self;
	}

	public Boolean getIsBehavior() {
		if (this.isBehavior == null) {
			this.setIsBehavior(this.deriveIsBehavior());
		}
		return this.isBehavior;
	}

	public void setIsBehavior(Boolean isBehavior) {
		this.isBehavior = isBehavior;
	}

	public Boolean getIsAssociationEnd() {
		if (this.isAssociationEnd == null) {
			this.setIsAssociationEnd(this.deriveIsAssociationEnd());
		}
		return this.isAssociationEnd;
	}

	public void setIsAssociationEnd(Boolean isAssociationEnd) {
		this.isAssociationEnd = isAssociationEnd;
	}

	public FeatureReference getFeature() {
		if (this.feature == null) {
			this.setFeature(this.deriveFeature());
		}
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

	public Boolean getIsOperation() {
		if (this.isOperation == null) {
			this.setIsOperation(this.deriveIsOperation());
		}
		return this.isOperation;
	}

	public void setIsOperation(Boolean isOperation) {
		this.isOperation = isOperation;
	}

	public Boolean getIsDestructor() {
		if (this.isDestructor == null) {
			this.setIsDestructor(this.deriveIsDestructor());
		}
		return this.isDestructor;
	}

	public void setIsDestructor(Boolean isDestructor) {
		this.isDestructor = isDestructor;
	}

	public Boolean getIsImplicit() {
		if (this.isImplicit == null) {
			this.setIsImplicit(this.deriveIsImplicit());
		}
		return this.isImplicit;
	}

	public void setIsImplicit(Boolean isImplicit) {
		this.isImplicit = isImplicit;
	}

	public ElementReference getReferent() {
		if (this.referent == null) {
			this.setReferent(this.deriveReferent());
		}
		return this.referent;
	}

	public void setReferent(ElementReference referent) {
		this.referent = referent;
	}

	public List<ElementReference> getParameter() {
		if (this.parameter == null) {
			this.setParameter(this.deriveParameter());
		}
		return this.parameter;
	}

	public void setParameter(List<ElementReference> parameter) {
		this.parameter = parameter;
	}

	public void addParameter(ElementReference parameter) {
		this.parameter.add(parameter);
	}

	public Boolean getIsSignal() {
		if (this.isSignal == null) {
			this.setIsSignal(this.deriveIsSignal());
		}
		return this.isSignal;
	}

	public void setIsSignal(Boolean isSignal) {
		this.isSignal = isSignal;
	}

	protected Boolean deriveIsBehavior() {
		return null; // STUB
	}

	protected Boolean deriveIsAssociationEnd() {
		return null; // STUB
	}

	protected FeatureReference deriveFeature() {
		return null; // STUB
	}

	protected Boolean deriveIsOperation() {
		return null; // STUB
	}

	protected Boolean deriveIsDestructor() {
		return null; // STUB
	}

	protected Boolean deriveIsImplicit() {
		return null; // STUB
	}

	protected ElementReference deriveReferent() {
		return null; // STUB
	}

	protected List<ElementReference> deriveParameter() {
		return null; // STUB
	}

	protected Boolean deriveIsSignal() {
		return null; // STUB
	}

	/**
	 * An invocation expression is a behavior invocation if its referent is a
	 * behavior.
	 **/
	public boolean invocationExpressionIsBehaviorDerivation() {
		this.getSelf().getIsBehavior();
		return true;
	}

	/**
	 * An invocation expression is an association end read if its referent is an
	 * association end.
	 **/
	public boolean invocationExpressionIsAssociationEndDerivation() {
		this.getSelf().getIsAssociationEnd();
		return true;
	}

	/**
	 * An invocation expression is an operation call if its referent is an
	 * operation.
	 **/
	public boolean invocationExpressionIsOperationDerivation() {
		this.getSelf().getIsOperation();
		return true;
	}

	/**
	 * An invocation expression is a destructor call either implicitly or if it
	 * is an explicit operation call to a destructor operation.
	 **/
	public boolean invocationExpressionIsDestructorDerivation() {
		this.getSelf().getIsDestructor();
		return true;
	}

	/**
	 * An invocation expression is an implicit object destruction if it has a
	 * feature with the name "destroy" and no explicit referents.
	 **/
	public boolean invocationExpressionIsImplicitDerivation() {
		this.getSelf().getIsImplicit();
		return true;
	}

	/**
	 * An invocation expression is a signal send if its referent is a signal.
	 **/
	public boolean invocationExpressionIsSignalDerivation() {
		this.getSelf().getIsSignal();
		return true;
	}

	/**
	 * The parameters of an invocation expression are given by the result of the
	 * parameterElements helper operation.
	 **/
	public boolean invocationExpressionParameterDerivation() {
		this.getSelf().getParameter();
		return true;
	}

	/**
	 * The type of an invocation expression is determined by the return
	 * parameter (if any) of the referent.
	 **/
	public boolean invocationExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	/**
	 * The multiplicity upper bound of an invocation expression is determined by
	 * the return parameter (if any) of the referent.
	 **/
	public boolean invocationExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

	/**
	 * The multiplicity lower bound of an invocation expression is determined by
	 * the return parameter (if any) of the referent.
	 **/
	public boolean invocationExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	/**
	 * The assignments before the target expression of the feature reference of
	 * an invocation expression (if any) are the same as the assignments before
	 * the invocation expression.
	 **/
	public boolean invocationExpressionAssignmentsBefore() {
		return true;
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
		return new ArrayList<ElementReference>(); // STUB
	} // parameterElements

	/**
	 * The assignments after an invocation expression are the same as those
	 * after the tuple of the expression.
	 **/
	public Collection<AssignedSource> updateAssignments() {
		return new ArrayList<AssignedSource>(); // STUB
	} // updateAssignments

} // InvocationExpressionImpl
