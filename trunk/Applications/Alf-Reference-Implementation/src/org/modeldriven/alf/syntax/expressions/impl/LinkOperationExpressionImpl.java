
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0
 * (http://www.opensource.org/licenses/afl-3.0.php)
 *
 */

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;
import java.util.List;

/**
 * An expression used to create or destroy the links of an association.
 **/

public class LinkOperationExpressionImpl
		extends InvocationExpressionImpl {

	private String operation = "";
	private Boolean isCreation = null; // DERIVED
	private Boolean isClear = null; // DERIVED
	private QualifiedName associationName = null;

	public LinkOperationExpressionImpl(LinkOperationExpression self) {
		super(self);
	}

	@Override
	public LinkOperationExpression getSelf() {
		return (LinkOperationExpression) this.self;
	}

	public String getOperation() {
		return this.operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Boolean getIsCreation() {
		if (this.isCreation == null) {
			this.setIsCreation(this.deriveIsCreation());
		}
		return this.isCreation;
	}

	public void setIsCreation(Boolean isCreation) {
		this.isCreation = isCreation;
	}

	public Boolean getIsClear() {
		if (this.isClear == null) {
			this.setIsClear(this.deriveIsClear());
		}
		return this.isClear;
	}

	public void setIsClear(Boolean isClear) {
		this.isClear = isClear;
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
	protected Boolean deriveIsCreation() {
	    String operation = this.getSelf().getOperation();
		return operation != null && operation.equals("createLink");
	}

	/**
	 * A link operation expression is for clearing an association if the
	 * operation is "clearAssoc".
	 **/
	protected Boolean deriveIsClear() {
        String operation = this.getSelf().getOperation();
        return operation != null && operation.equals("clearAssoc");
	}
	
	/**
	 * The referent for a link operation expression is the named association.
	 **/
	@Override
	protected ElementReference deriveReferent() {
	    QualifiedName associationName = this.getSelf().getAssociationName();
	    return associationName == null? null: 
	                associationName.getImpl().getAssociationReferent();
	}
	
	/**
	 * There is no feature for a link operation expression.
	 **/
	@Override
	protected FeatureReference deriveFeature() {
	    return null;
	}
	
	/*
	 * Derivations
	 */

	public boolean linkOperationExpressionIsCreationDerivation() {
		this.getSelf().getIsCreation();
		return true;
	}

	public boolean linkOperationExpressionIsClearDerivation() {
		this.getSelf().getIsClear();
		return true;
	}

	public boolean linkOperationExpressionReferentDerivation() {
		this.getSelf().getReferent();
		return true;
	}

	public boolean linkOperationExpressionFeatureDerivation() {
		this.getSelf().getFeature();
		return true;
	}
	
	/*
	 * Constraints
	 */

	/**
	 * The qualified name of a link operation expression must resolve to a
	 * single association.
	 **/
	public boolean linkOperationExpressionAssociationReference() {
		return this.getSelf().getReferent() != null;
	}

	/**
	 * Each argument expression must be assignable to its corresponding
	 * expression.
	 **/
	public boolean linkOperationExpressionArgumentCompatibility() {
        LinkOperationExpression self = this.getSelf();
        Tuple tuple = self.getTuple();
        if (tuple == null) {
            return false;
        } else {
            for (NamedExpression input: tuple.getInput()) {
                if (!this.parameterIsAssignableFrom(input)) {
                    return false;
                }
            }
            return true;
        }
	}

	/*
	 * Helper Methods
	 */
	
	/**
	 * For a clear association operation, returns a single, typeless parameter.
	 * Otherwise, returns the ends of the named association.
	 **/
	@Override
	public List<FormalParameter> parameters() {
        LinkOperationExpression self = this.getSelf();
        ElementReference referent = self.getReferent();
        List<FormalParameter> parameters = new ArrayList<FormalParameter>();
        if (self.getIsClear()) {
            FormalParameter parameter = new FormalParameter();
            parameter.setDirection("in");
            parameter.setLower(1);
            parameter.setUpper(1);
            parameters.add(parameter);
        } else if (referent != null) {
            for (ElementReference property: referent.getImpl().getAssociationEnds()) {
                parameters.add(parameterFromProperty(property));
            }
        }
		return parameters;
	} // parameterElements
	
	@Override
	public void setCurrentScope(NamespaceDefinition currentScope) {
	    super.setCurrentScope(currentScope);
	    QualifiedName associationName = this.getSelf().getAssociationName();
	    if (associationName != null) {
	        associationName.getImpl().setCurrentScope(currentScope);
	    }
	}

} // LinkOperationExpressionImpl
