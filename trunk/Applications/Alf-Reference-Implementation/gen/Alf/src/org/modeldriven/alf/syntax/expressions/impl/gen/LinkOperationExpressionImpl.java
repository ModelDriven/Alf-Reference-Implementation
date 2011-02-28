
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions.impl.gen;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.omg.uml.*;

import java.util.ArrayList;

/**
 * An expression used to create or destroy the links of an association.
 **/

public class LinkOperationExpressionImpl
		extends
		org.modeldriven.alf.syntax.expressions.impl.gen.InvocationExpressionImpl {

	public LinkOperationExpressionImpl(LinkOperationExpression self) {
		super(self);
	}

	public LinkOperationExpression getSelf() {
		return (LinkOperationExpression) this.self;
	}

	public Boolean deriveIsCreation() {
		return null; // STUB
	}

	public Boolean deriveIsClear() {
		return null; // STUB
	}

	/**
	 * A link operation expression is for link creation if its operation is
	 * "createLink".
	 **/
	public boolean linkOperationExpressionIsCreationDerivation() {
		this.getSelf().getIsCreation();
		return true;
	}

	/**
	 * A link operation expression is for clearing an association if the
	 * operation is "clearAssoc".
	 **/
	public boolean linkOperationExpressionIsClearDerivation() {
		this.getSelf().getIsClear();
		return true;
	}

	/**
	 * The referent for a link operation expression is the named association.
	 **/
	public boolean linkOperationExpressionReferentDerivation() {
		this.getSelf().getReferent();
		return true;
	}

	/**
	 * There is no feature for a link operation expression.
	 **/
	public boolean linkOperationExpressionFeatureDerivation() {
		this.getSelf().getFeature();
		return true;
	}

	/**
	 * The qualified name of a link operation expression must resolve to a
	 * single association.
	 **/
	public boolean linkOperationExpressionAssociationReference() {
		return true;
	}

	/**
	 * Each argument expression must be assignable to its corresponding
	 * expression.
	 **/
	public boolean linkOperationExpressionArgumentCompatibility() {
		return true;
	}

	/**
	 * For a clear association operation, returns a single, typeless parameter.
	 * Otherwise, returns the ends of the named association.
	 **/
	public ArrayList<ElementReference> parameterElements() {
		return new ArrayList<ElementReference>(); // STUB
	} // parameterElements

} // LinkOperationExpressionImpl
