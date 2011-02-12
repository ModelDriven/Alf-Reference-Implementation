
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

import org.modeldriven.alf.syntax.expressions.impl.SequenceConstructionExpressionImpl;

/**
 * An expression used to construct a sequence of values.
 **/

public class SequenceConstructionExpression extends Expression {

	private SequenceElements elements = null;
	private Boolean hasMultiplicity = false;
	private QualifiedName typeName = null;

	public SequenceConstructionExpression() {
		this.impl = new SequenceConstructionExpressionImpl(this);
	}

	public SequenceConstructionExpressionImpl getImpl() {
		return (SequenceConstructionExpressionImpl) this.impl;
	}

	public SequenceElements getElements() {
		return this.elements;
	}

	public void setElements(SequenceElements elements) {
		this.elements = elements;
	}

	public Boolean getHasMultiplicity() {
		return this.hasMultiplicity;
	}

	public void setHasMultiplicity(Boolean hasMultiplicity) {
		this.hasMultiplicity = hasMultiplicity;
	}

	public QualifiedName getTypeName() {
		return this.typeName;
	}

	public void setTypeName(QualifiedName typeName) {
		this.typeName = typeName;
	}

	/**
	 * The type of a sequence construction expression is the named type.
	 **/
	public boolean sequenceConstructionExpressionTypeDerivation() {
		return this.getImpl().sequenceConstructionExpressionTypeDerivation();
	}

	/**
	 * If a sequence construction expression has multiplicity, then its
	 * multiplicity upper bound is given by its elements, if this is not empty,
	 * and zero otherwise. If a sequence construction expression does not have
	 * multiplicity, then its multiplicity upper bound is one.
	 **/
	public boolean sequenceConstructionExpressionUpperDerivation() {
		return this.getImpl().sequenceConstructionExpressionUpperDerivation();
	}

	/**
	 * If a sequence construction expression has multiplicity, then its
	 * multiplicity lower bound is given by its elements, if this is not empty,
	 * and zero otherwise. If a sequence construction expression does not have
	 * multiplicity, then its multiplicity lower bound is one.
	 **/
	public boolean sequenceConstructionExpressionLowerDerivation() {
		return this.getImpl().sequenceConstructionExpressionLowerDerivation();
	}

	/**
	 * The type name of a sequence construction expression must resolve to a
	 * non-template classifier. If the expression does not have multiplicity,
	 * then this classifier must be the instantiation of a collection class.
	 **/
	public boolean sequenceConstructionExpressionType() {
		return this.getImpl().sequenceConstructionExpressionType();
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" hasMultiplicity:");
		s.append(this.getHasMultiplicity());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		SequenceElements elements = this.getElements();
		if (elements != null) {
			elements.print(prefix + " ");
		}
		QualifiedName typeName = this.getTypeName();
		if (typeName != null) {
			typeName.print(prefix + " ");
		}
	}
} // SequenceConstructionExpression
