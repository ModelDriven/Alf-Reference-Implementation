
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

import org.modeldriven.alf.syntax.expressions.impl.BooleanLiteralExpressionImpl;

/**
 * An expression that comprises a Boolean literal.
 **/

public class BooleanLiteralExpression extends LiteralExpression {

	public BooleanLiteralExpression() {
		this.impl = new BooleanLiteralExpressionImpl(this);
	}

	public BooleanLiteralExpression(AlfParser parser) {
		this();
		this.setParserInfo(parser.getFileName(), parser.getLine(), parser
				.getColumn());
	}

	public BooleanLiteralExpression(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public BooleanLiteralExpressionImpl getImpl() {
		return (BooleanLiteralExpressionImpl) this.impl;
	}

	public String getImage() {
		return this.getImpl().getImage();
	}

	public void setImage(String image) {
		this.getImpl().setImage(image);
	}

	/**
	 * The type of a boolean literal expression is Boolean.
	 **/
	public boolean booleanLiteralExpressionTypeDerivation() {
		return this.getImpl().booleanLiteralExpressionTypeDerivation();
	}

	public void _deriveAll() {
		super._deriveAll();
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.booleanLiteralExpressionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"booleanLiteralExpressionTypeDerivation", this));
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
} // BooleanLiteralExpression
