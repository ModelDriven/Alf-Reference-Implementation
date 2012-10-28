
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
import java.util.TreeSet;

import org.modeldriven.alf.syntax.expressions.impl.NameLeftHandSideImpl;

/**
 * A left-hand side that is a name.
 **/

public class NameLeftHandSide extends LeftHandSide {

	public NameLeftHandSide() {
		this.impl = new NameLeftHandSideImpl(this);
	}

	public NameLeftHandSide(AlfParser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public NameLeftHandSide(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public NameLeftHandSideImpl getImpl() {
		return (NameLeftHandSideImpl) this.impl;
	}

	public QualifiedName getTarget() {
		return this.getImpl().getTarget();
	}

	public void setTarget(QualifiedName target) {
		this.getImpl().setTarget(target);
	}

	/**
	 * If a name left-hand side has an index, then the assignments after the
	 * left-hand side are the same as the assignments after the index. If the
	 * left-hand side has no index, but its target disambiguates to a feature
	 * reference, then the assignments after the left-hand side are the
	 * assignments after the feature expression. Otherwise the assignments after
	 * the left-hand side are the same as the assignments before the left-hand
	 * side.
	 **/
	public boolean nameLeftHandSideAssignmentAfterDerivation() {
		return this.getImpl().nameLeftHandSideAssignmentAfterDerivation();
	}

	/**
	 * The target of a name left hand side may not already have an assigned
	 * source that is a loop variable definition, an annotation, a sequence
	 * expansion expression or a parameter that is an in parameter.
	 **/
	public boolean nameLeftHandSideTargetAssignment() {
		return this.getImpl().nameLeftHandSideTargetAssignment();
	}

	/**
	 * If the target of a name left-hand side disambiguates to a feature
	 * reference, then the assignments before the expression of the feature
	 * reference are the assignments before the left-hand side. If a name
	 * left-hand side has an index, then the target must either disambiguate to
	 * a feature reference or already have an assigned source, and the
	 * assignments before the index expression are the assignments before the
	 * left-hand side or, if the target disambiguates to a feature reference,
	 * the assignments after the expression of the feature reference.
	 **/
	public boolean nameLeftHandSideAssignmentsBefore() {
		return this.getImpl().nameLeftHandSideAssignmentsBefore();
	}

	/**
	 * If the target of a name left-hand side disambiguates to a structural
	 * feature, then the referent of the left-hand side is that feature. If the
	 * target resolves to a parameter, then the referent is that parameter. If
	 * the target resolves to a local name, then the referent is the assigned
	 * source for that local name, if it has one.
	 **/
	public boolean nameLeftHandSideReferentDerivation() {
		return this.getImpl().nameLeftHandSideReferentDerivation();
	}

	/**
	 * If a name left-hand side is indexed, then its lower bound is 1.
	 * Otherwise, if the left-hand side is for a local name with an assignment,
	 * than its lower bound is that of the assignment, else, if it has a
	 * referent, then its lower bound is that of the referent.
	 **/
	public boolean nameLeftHandSideLowerDerivation() {
		return this.getImpl().nameLeftHandSideLowerDerivation();
	}

	/**
	 * If a name left-hand side is indexed, then its upper bound is 1.
	 * Otherwise, if the left-hand side is for a local name with an assignment,
	 * than its upper bound is that of the assignment, else, if it has a
	 * referent, then its upper bound is that of the referent.
	 **/
	public boolean nameLeftHandSideUpperDerivation() {
		return this.getImpl().nameLeftHandSideUpperDerivation();
	}

	/**
	 * If a name left-hand side is for a local name with an assignment, then its
	 * type is that of that assignment. Otherwise, if the left-hand side has a
	 * referent, then its type is the type of that referent.
	 **/
	public boolean nameLeftHandSideTypeDerivation() {
		return this.getImpl().nameLeftHandSideTypeDerivation();
	}

	/**
	 * If the target of a name left-hand side is qualified, then, if it does not
	 * disambiguate to a feature, it must have a referent that is a parameter of
	 * an operation or behavior that is the current scope the left-hand is in,
	 * and, if it does disambiguate to a feature, it must have a single referent
	 * that is a structural feature.
	 **/
	public boolean nameLeftHandSideTargetResolution() {
		return this.getImpl().nameLeftHandSideTargetResolution();
	}

	/**
	 * If the target of a name left-hand side disambiguates to a feature
	 * reference, and the left-hand side has an index, then the referent of the
	 * feature reference must be ordered and non-unique.
	 **/
	public boolean nameLeftHandSideIndexedFeature() {
		return this.getImpl().nameLeftHandSideIndexedFeature();
	}

	/**
	 * The target of a name left-hand side must not have a template binding.
	 **/
	public boolean nameLeftHandSideNontemplateTarget() {
		return this.getImpl().nameLeftHandSideNontemplateTarget();
	}

	public void _deriveAll() {
		super._deriveAll();
		QualifiedName target = this.getTarget();
		if (target != null) {
			target.deriveAll();
		}
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.nameLeftHandSideAssignmentAfterDerivation()) {
			violations.add(new ConstraintViolation(
					"nameLeftHandSideAssignmentAfterDerivation", this));
		}
		if (!this.nameLeftHandSideTargetAssignment()) {
			violations.add(new ConstraintViolation(
					"nameLeftHandSideTargetAssignment", this));
		}
		if (!this.nameLeftHandSideAssignmentsBefore()) {
			violations.add(new ConstraintViolation(
					"nameLeftHandSideAssignmentsBefore", this));
		}
		if (!this.nameLeftHandSideReferentDerivation()) {
			violations.add(new ConstraintViolation(
					"nameLeftHandSideReferentDerivation", this));
		}
		if (!this.nameLeftHandSideLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"nameLeftHandSideLowerDerivation", this));
		}
		if (!this.nameLeftHandSideUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"nameLeftHandSideUpperDerivation", this));
		}
		if (!this.nameLeftHandSideTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"nameLeftHandSideTypeDerivation", this));
		}
		if (!this.nameLeftHandSideTargetResolution()) {
			violations.add(new ConstraintViolation(
					"nameLeftHandSideTargetResolution", this));
		}
		if (!this.nameLeftHandSideIndexedFeature()) {
			violations.add(new ConstraintViolation(
					"nameLeftHandSideIndexedFeature", this));
		}
		if (!this.nameLeftHandSideNontemplateTarget()) {
			violations.add(new ConstraintViolation(
					"nameLeftHandSideNontemplateTarget", this));
		}
		QualifiedName target = this.getTarget();
		if (target != null) {
			target.checkConstraints(violations);
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
		QualifiedName target = this.getTarget();
		if (target != null) {
			System.out.println(prefix + " target:");
			target.print(prefix + "  ", includeDerived);
		}
	}
} // NameLeftHandSide
