
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
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
	 * The assignments after a name left-hand side are the same as the
	 * assignments before.
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
	 * If a name left-hand side has an index, then the target name must already
	 * have an assigned source and the assignments before the index expression
	 * are the assignments before the left-hand side.
	 **/
	public boolean nameLeftHandSideAssignmentsBefore() {
		return this.getImpl().nameLeftHandSideAssignmentsBefore();
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
