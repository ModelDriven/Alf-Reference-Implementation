
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

import org.modeldriven.alf.syntax.expressions.impl.LinkOperationExpressionImpl;

/**
 * An expression used to create or destroy the links of an association.
 **/

public class LinkOperationExpression extends InvocationExpression {

	private String operation = "";
	private Boolean isCreation = null; // DERIVED
	private Boolean isClear = null; // DERIVED
	private QualifiedName associationName = null;

	public LinkOperationExpression() {
		this.impl = new LinkOperationExpressionImpl(this);
	}

	public LinkOperationExpressionImpl getImpl() {
		return (LinkOperationExpressionImpl) this.impl;
	}

	public String getOperation() {
		return this.operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Boolean getIsCreation() {
		if (this.isCreation == null) {
			this.isCreation = this.getImpl().deriveIsCreation();
		}
		return this.isCreation;
	}

	public Boolean getIsClear() {
		if (this.isClear == null) {
			this.isClear = this.getImpl().deriveIsClear();
		}
		return this.isClear;
	}

	public QualifiedName getAssociationName() {
		return this.associationName;
	}

	public void setAssociationName(QualifiedName associationName) {
		this.associationName = associationName;
	}

	/**
	 * A link operation expression is for link creation if its operation is
	 * "createLink".
	 **/
	public boolean linkOperationExpressionIsCreationDerivation() {
		return this.getImpl().linkOperationExpressionIsCreationDerivation();
	}

	/**
	 * A link operation expression is for clearing an association if the
	 * operation is "clearAssoc".
	 **/
	public boolean linkOperationExpressionIsClearDerivation() {
		return this.getImpl().linkOperationExpressionIsClearDerivation();
	}

	/**
	 * The referent for a link operation expression is the named association.
	 **/
	public boolean linkOperationExpressionReferentDerivation() {
		return this.getImpl().linkOperationExpressionReferentDerivation();
	}

	/**
	 * There is no feature for a link operation expression.
	 **/
	public boolean linkOperationExpressionFeatureDerivation() {
		return this.getImpl().linkOperationExpressionFeatureDerivation();
	}

	/**
	 * The qualified name of a link operation expression must resolve to a
	 * single association.
	 **/
	public boolean linkOperationExpressionAssociationReference() {
		return this.getImpl().linkOperationExpressionAssociationReference();
	}

	/**
	 * Each argument expression must be assignable to its corresponding
	 * expression.
	 **/
	public boolean linkOperationExpressionArgumentCompatibility() {
		return this.getImpl().linkOperationExpressionArgumentCompatibility();
	}

	/**
	 * For a clear association operation, returns a single, typeless parameter.
	 * Otherwise, returns the ends of the named association.
	 **/
	public ArrayList<ElementReference> parameterElements() {
		return this.getImpl().parameterElements();
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" operation:");
		s.append(this.getOperation());
		Boolean isCreation = this.getIsCreation();
		if (isCreation != null) {
			s.append(" /isCreation:");
			s.append(isCreation);
		}
		Boolean isClear = this.getIsClear();
		if (isClear != null) {
			s.append(" /isClear:");
			s.append(isClear);
		}
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		QualifiedName associationName = this.getAssociationName();
		if (associationName != null) {
			associationName.print(prefix + " ");
		}
	}
} // LinkOperationExpression
