
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions;

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

import org.modeldriven.alf.syntax.expressions.impl.SequenceConstructionExpressionImpl;

/**
 * An expression used to construct a sequence of values.
 **/

public class SequenceConstructionExpression extends Expression {

	public SequenceConstructionExpression() {
		this.impl = new SequenceConstructionExpressionImpl(this);
	}

	public SequenceConstructionExpression(AlfParser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public SequenceConstructionExpression(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public SequenceConstructionExpressionImpl getImpl() {
		return (SequenceConstructionExpressionImpl) this.impl;
	}

	public SequenceElements getElements() {
		return this.getImpl().getElements();
	}

	public void setElements(SequenceElements elements) {
		this.getImpl().setElements(elements);
	}

	public Boolean getHasMultiplicity() {
		return this.getImpl().getHasMultiplicity();
	}

	public void setHasMultiplicity(Boolean hasMultiplicity) {
		this.getImpl().setHasMultiplicity(hasMultiplicity);
	}

	public QualifiedName getTypeName() {
		return this.getImpl().getTypeName();
	}

	public void setTypeName(QualifiedName typeName) {
		this.getImpl().setTypeName(typeName);
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

	public void _deriveAll() {
		super._deriveAll();
		SequenceElements elements = this.getElements();
		if (elements != null) {
			elements.deriveAll();
		}
		QualifiedName typeName = this.getTypeName();
		if (typeName != null) {
			typeName.deriveAll();
		}
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.sequenceConstructionExpressionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"sequenceConstructionExpressionTypeDerivation", this));
		}
		if (!this.sequenceConstructionExpressionUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"sequenceConstructionExpressionUpperDerivation", this));
		}
		if (!this.sequenceConstructionExpressionLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"sequenceConstructionExpressionLowerDerivation", this));
		}
		if (!this.sequenceConstructionExpressionType()) {
			violations.add(new ConstraintViolation(
					"sequenceConstructionExpressionType", this));
		}
		SequenceElements elements = this.getElements();
		if (elements != null) {
			elements.checkConstraints(violations);
		}
		QualifiedName typeName = this.getTypeName();
		if (typeName != null) {
			typeName.checkConstraints(violations);
		}
	}

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		s.append(" hasMultiplicity:");
		s.append(this.getHasMultiplicity());
		return s.toString();
	}

	public void print() {
		this.print("", false);
	}

	public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	public void print(String prefix, boolean includeDerived) {
		super.print(prefix, includeDerived);
		SequenceElements elements = this.getElements();
		if (elements != null) {
			System.out.println(prefix + " elements:");
			elements.print(prefix + "  ", includeDerived);
		}
		QualifiedName typeName = this.getTypeName();
		if (typeName != null) {
			System.out.println(prefix + " typeName:");
			typeName.print(prefix + "  ", includeDerived);
		}
	}
} // SequenceConstructionExpression
