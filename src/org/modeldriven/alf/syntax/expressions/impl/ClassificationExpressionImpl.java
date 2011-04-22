
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

/**
 * An expression used to test the dynamic type of its operand.
 **/

public class ClassificationExpressionImpl extends UnaryExpressionImpl {

	private ElementReference referent = null; // DERIVED
	private Boolean isDirect = null; // DERIVED
	private QualifiedName typeName = null;

	public ClassificationExpressionImpl(ClassificationExpression self) {
		super(self);
	}

	@Override
	public ClassificationExpression getSelf() {
		return (ClassificationExpression) this.self;
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

	public Boolean getIsDirect() {
		if (this.isDirect == null) {
			this.setIsDirect(this.deriveIsDirect());
		}
		return this.isDirect;
	}

	public void setIsDirect(Boolean isDirect) {
		this.isDirect = isDirect;
	}

	public QualifiedName getTypeName() {
		return this.typeName;
	}

	public void setTypeName(QualifiedName typeName) {
		this.typeName = typeName;
	}

	/**
	 * The referent of a classification expression is the classifier to which
	 * the type name resolves.
	 **/
	protected ElementReference deriveReferent() {
	    QualifiedName typeName = this.getSelf().getTypeName();
		return typeName == null? null: typeName.getImpl().getNonTemplateClassifierReferent();
	}

    /**
     * A classification expression is direct if its operator is "hastype".
     **/
	protected Boolean deriveIsDirect() {
	    String operator = this.getSelf().getOperator();
		return operator != null && operator.equals("hastype");
	}

    /**
     * A classification expression has type Boolean.
     **/
	@Override
	protected ElementReference deriveType() {
	    return RootNamespace.getBooleanType();
	}
	
    /**
     * A classification expression has a multiplicity lower bound that is the
     * same as the lower bound of its operand expression.
     **/
	@Override
	protected Integer deriveLower() {
	    Expression operand = this.getSelf().getOperand();
	    return operand == null? 1: operand.getLower();
	}
	
    /**
     * A classification expression has a multiplicity upper bound of 1.
     **/
	@Override
	protected Integer deriveUpper() {
	    return 1;
	}
	
	/*
	 * Derivations
	 */

	public boolean classificationExpressionIsDirectDerivation() {
		this.getSelf().getIsDirect();
		return true;
	}

	public boolean classificationExpressionReferentDerivation() {
		this.getSelf().getReferent();
		return true;
	}

	public boolean classificationExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	public boolean classificationExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	public boolean classificationExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}
	
	/*
	 * Constraints
	 */

	/**
	 * The type name in a classification expression must resolve to a
	 * classifier.
	 **/
	public boolean classificationExpressionTypeName() {
		return this.getSelf().getType() != null;
	}

	/**
	 * The operand expression of a classification expression must have a
	 * multiplicity upper bound of 1.
	 **/
	public boolean classificationExpressionOperand() {
	    Expression operand = this.getSelf().getOperand();
		return operand != null && operand.getUpper() == 1;
	}
	
	/*
	 * Helper Methods
	 */
	
	@Override
	public void setCurrentScope(NamespaceDefinition currentScope) {
	    super.setCurrentScope(currentScope);
	    QualifiedName typeName = this.getSelf().getTypeName();
	    if (typeName != null) {
	        typeName.getImpl().setCurrentScope(currentScope);
	    }
	}

} // ClassificationExpressionImpl
