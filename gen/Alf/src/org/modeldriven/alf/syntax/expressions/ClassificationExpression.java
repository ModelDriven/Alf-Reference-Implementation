
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

import org.omg.uml.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.expressions.impl.ClassificationExpressionImpl;

/**
 * An expression used to test the dynamic type of its operand.
 **/

public class ClassificationExpression extends UnaryExpression {

	public ClassificationExpression() {
		this.impl = new ClassificationExpressionImpl(this);
	}

	public ClassificationExpressionImpl getImpl() {
		return (ClassificationExpressionImpl) this.impl;
	}

	public ElementReference getReferent() {
		return this.getImpl().getReferent();
	}

	public void setReferent(ElementReference referent) {
		this.getImpl().setReferent(referent);
	}

	public Boolean getIsDirect() {
		return this.getImpl().getIsDirect();
	}

	public void setIsDirect(Boolean isDirect) {
		this.getImpl().setIsDirect(isDirect);
	}

	public QualifiedName getTypeName() {
		return this.getImpl().getTypeName();
	}

	public void setTypeName(QualifiedName typeName) {
		this.getImpl().setTypeName(typeName);
	}

	/**
	 * A classification expression is direct if its operator is "hastype".
	 **/
	public boolean classificationExpressionIsDirectDerivation() {
		return this.getImpl().classificationExpressionIsDirectDerivation();
	}

	/**
	 * The referent of a classification expression is the classifier to which
	 * the type name resolves.
	 **/
	public boolean classificationExpressionReferentDerivation() {
		return this.getImpl().classificationExpressionReferentDerivation();
	}

	/**
	 * A classification expression has type Boolean.
	 **/
	public boolean classificationExpressionTypeDerivation() {
		return this.getImpl().classificationExpressionTypeDerivation();
	}

	/**
	 * A classification expression has a multiplicity lower bound that is the
	 * same as the lower bound of its operand expression.
	 **/
	public boolean classificationExpressionLowerDerivation() {
		return this.getImpl().classificationExpressionLowerDerivation();
	}

	/**
	 * A classification expression has a multiplicity upper bound of 1.
	 **/
	public boolean classificationExpressionUpperDerivation() {
		return this.getImpl().classificationExpressionUpperDerivation();
	}

	/**
	 * The type name in a classification expression must resolve to a
	 * classifier.
	 **/
	public boolean classificationExpressionTypeName() {
		return this.getImpl().classificationExpressionTypeName();
	}

	/**
	 * The operand expression of a classification expression must have a
	 * multiplicity upper bound of 1.
	 **/
	public boolean classificationExpressionOperand() {
		return this.getImpl().classificationExpressionOperand();
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		Boolean isDirect = this.getIsDirect();
		if (isDirect != null) {
			s.append(" /isDirect:");
			s.append(isDirect);
		}
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		ElementReference referent = this.getReferent();
		if (referent != null) {
			System.out.println(prefix + " /referent:" + referent);
		}
		QualifiedName typeName = this.getTypeName();
		if (typeName != null) {
			System.out.println(prefix + " typeName:");
			typeName.print(prefix + "  ");
		}
	}
} // ClassificationExpression
