
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl.gen;

import org.modeldriven.alf.parser.AlfParser;
import org.modeldriven.alf.parser.Token;

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
import java.util.TreeSet;

/**
 * An expression used to test the dynamic type of its operand.
 **/

public class ClassificationExpressionImpl extends
		org.modeldriven.alf.syntax.expressions.impl.gen.UnaryExpressionImpl {

	private ElementReference referent = null; // DERIVED
	private Boolean isDirect = null; // DERIVED
	private QualifiedName typeName = null;

	public ClassificationExpressionImpl(ClassificationExpression self) {
		super(self);
	}

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

	protected ElementReference deriveReferent() {
		return null; // STUB
	}

	protected Boolean deriveIsDirect() {
		return null; // STUB
	}

	/**
	 * A classification expression is direct if its operator is "hastype".
	 **/
	public boolean classificationExpressionIsDirectDerivation() {
		this.getSelf().getIsDirect();
		return true;
	}

	/**
	 * The referent of a classification expression is the classifier to which
	 * the type name resolves.
	 **/
	public boolean classificationExpressionReferentDerivation() {
		this.getSelf().getReferent();
		return true;
	}

	/**
	 * A classification expression has type Boolean.
	 **/
	public boolean classificationExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	/**
	 * A classification expression has a multiplicity lower bound that is the
	 * same as the lower bound of its operand expression.
	 **/
	public boolean classificationExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	/**
	 * A classification expression has a multiplicity upper bound of 1.
	 **/
	public boolean classificationExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

	/**
	 * The type name in a classification expression must resolve to a
	 * non-template classifier.
	 **/
	public boolean classificationExpressionTypeName() {
		return true;
	}

	/**
	 * The operand expression of a classification expression must have a
	 * multiplicity upper bound of 1.
	 **/
	public boolean classificationExpressionOperand() {
		return true;
	}

} // ClassificationExpressionImpl
