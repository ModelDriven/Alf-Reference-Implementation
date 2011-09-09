
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions;

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

import org.modeldriven.alf.syntax.expressions.impl.NaturalLiteralExpressionImpl;

/**
 * An expression that comprises a natural literal.
 **/

public class NaturalLiteralExpression extends LiteralExpression {

	public NaturalLiteralExpression() {
		this.impl = new NaturalLiteralExpressionImpl(this);
	}

	public NaturalLiteralExpression(AlfParser parser) {
		this();
		this.setParserInfo(parser.getFileName(), parser.getLine(), parser
				.getColumn());
	}

	public NaturalLiteralExpression(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public NaturalLiteralExpressionImpl getImpl() {
		return (NaturalLiteralExpressionImpl) this.impl;
	}

	public String getImage() {
		return this.getImpl().getImage();
	}

	public void setImage(String image) {
		this.getImpl().setImage(image);
	}

	/**
	 * The type of a natural literal is the Alf library type Natural.
	 * 
	 * NOTE: If the context of a natural literal expression unambiguously
	 * requires either an Integer or an UnlimitedNatural value, then the result
	 * of the literal expression is implicitly downcast to the required type. If
	 * the context is ambiguous, however, than an explicit cast to Integer or
	 * UnlimitedNatural must be used.
	 **/
	public boolean naturalLiteralExpressionTypeDerivation() {
		return this.getImpl().naturalLiteralExpressionTypeDerivation();
	}

	public void _deriveAll() {
		super._deriveAll();
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.naturalLiteralExpressionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"naturalLiteralExpressionTypeDerivation", this));
		}
	}

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		s.append(" image:");
		s.append(this.getImage());
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
	}
} // NaturalLiteralExpression
