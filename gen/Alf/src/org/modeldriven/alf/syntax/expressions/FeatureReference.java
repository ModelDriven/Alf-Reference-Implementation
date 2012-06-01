
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

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

import org.modeldriven.alf.syntax.expressions.impl.FeatureReferenceImpl;

/**
 * A reference to a structural or behavioral feature of the type of its target
 * expression or a binary association end the opposite end of which is typed by
 * the type of its target expression.
 **/

public class FeatureReference extends SyntaxElement {

	public FeatureReference() {
		this.impl = new FeatureReferenceImpl(this);
	}

	public FeatureReference(AlfParser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public FeatureReference(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public FeatureReferenceImpl getImpl() {
		return (FeatureReferenceImpl) this.impl;
	}

	public Expression getExpression() {
		return this.getImpl().getExpression();
	}

	public void setExpression(Expression expression) {
		this.getImpl().setExpression(expression);
	}

	public Collection<ElementReference> getReferent() {
		return this.getImpl().getReferent();
	}

	public void setReferent(Collection<ElementReference> referent) {
		this.getImpl().setReferent(referent);
	}

	public void addReferent(ElementReference referent) {
		this.getImpl().addReferent(referent);
	}

	public NameBinding getNameBinding() {
		return this.getImpl().getNameBinding();
	}

	public void setNameBinding(NameBinding nameBinding) {
		this.getImpl().setNameBinding(nameBinding);
	}

	/**
	 * The features referenced by a feature reference include the features of
	 * the type of the target expression and the association ends of any binary
	 * associations whose opposite ends are typed by the type of the target
	 * expression.
	 **/
	public boolean featureReferenceReferentDerivation() {
		return this.getImpl().featureReferenceReferentDerivation();
	}

	/**
	 * The target expression of the feature reference may not be untyped, nor
	 * may it have a primitive or enumeration type.
	 **/
	public boolean featureReferenceTargetType() {
		return this.getImpl().featureReferenceTargetType();
	}

	public void _deriveAll() {
		this.getReferent();
		super._deriveAll();
		Expression expression = this.getExpression();
		if (expression != null) {
			expression.deriveAll();
		}
		NameBinding nameBinding = this.getNameBinding();
		if (nameBinding != null) {
			nameBinding.deriveAll();
		}
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.featureReferenceReferentDerivation()) {
			violations.add(new ConstraintViolation(
					"featureReferenceReferentDerivation", this));
		}
		if (!this.featureReferenceTargetType()) {
			violations.add(new ConstraintViolation(
					"featureReferenceTargetType", this));
		}
		Expression expression = this.getExpression();
		if (expression != null) {
			expression.checkConstraints(violations);
		}
		NameBinding nameBinding = this.getNameBinding();
		if (nameBinding != null) {
			nameBinding.checkConstraints(violations);
		}
	}

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
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
		Expression expression = this.getExpression();
		if (expression != null) {
			System.out.println(prefix + " expression:");
			expression.print(prefix + "  ", includeDerived);
		}
		if (includeDerived) {
			Collection<ElementReference> referent = this.getReferent();
			if (referent != null && referent.size() > 0) {
				System.out.println(prefix + " /referent:");
				for (Object _object : referent.toArray()) {
					ElementReference _referent = (ElementReference) _object;
					System.out.println(prefix + "  "
							+ _referent.toString(includeDerived));
				}
			}
		}
		NameBinding nameBinding = this.getNameBinding();
		if (nameBinding != null) {
			System.out.println(prefix + " nameBinding:");
			nameBinding.print(prefix + "  ", includeDerived);
		}
	}
} // FeatureReference
