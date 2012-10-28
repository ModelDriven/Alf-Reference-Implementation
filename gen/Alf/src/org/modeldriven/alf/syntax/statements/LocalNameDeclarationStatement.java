
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.statements;

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

import org.modeldriven.alf.syntax.statements.impl.LocalNameDeclarationStatementImpl;

/**
 * A statement that declares the type of a local name and assigns it an initial
 * value.
 **/

public class LocalNameDeclarationStatement extends Statement {

	public LocalNameDeclarationStatement() {
		this.impl = new LocalNameDeclarationStatementImpl(this);
	}

	public LocalNameDeclarationStatement(AlfParser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public LocalNameDeclarationStatement(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public LocalNameDeclarationStatementImpl getImpl() {
		return (LocalNameDeclarationStatementImpl) this.impl;
	}

	public String getName() {
		return this.getImpl().getName();
	}

	public void setName(String name) {
		this.getImpl().setName(name);
	}

	public Expression getExpression() {
		return this.getImpl().getExpression();
	}

	public void setExpression(Expression expression) {
		this.getImpl().setExpression(expression);
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

	public ElementReference getType() {
		return this.getImpl().getType();
	}

	public void setType(ElementReference type) {
		this.getImpl().setType(type);
	}

	/**
	 * The assignments before the expression of a local name declaration
	 * statement are the same as the assignments before the statement.
	 **/
	public boolean localNameDeclarationStatementAssignmentsBefore() {
		return this.getImpl().localNameDeclarationStatementAssignmentsBefore();
	}

	/**
	 * If the type name in a local name declaration statement is not empty, then
	 * it must resolve to a non-template classifier and the expression must be
	 * assignable to that classifier.
	 **/
	public boolean localNameDeclarationStatementType() {
		return this.getImpl().localNameDeclarationStatementType();
	}

	/**
	 * The local name in a local name declaration statement must be unassigned
	 * before the statement and before the expression in the statement. It must
	 * remain unassigned after the expression.
	 **/
	public boolean localNameDeclarationStatementLocalName() {
		return this.getImpl().localNameDeclarationStatementLocalName();
	}

	/**
	 * The assignments after a local name declaration statement are the
	 * assignments after the expression of the statement plus a new assignment
	 * for the local name defined by the statement. The assigned source for the
	 * local name is the local name declaration statement. The local name has
	 * the type denoted by the type name if this is not empty and is untyped
	 * otherwise. If the statement has multiplicity, then the multiplicity of
	 * the local name is [0..*], otherwise it is [0..1].
	 **/
	public boolean localNameDeclarationStatementAssignmentsAfter() {
		return this.getImpl().localNameDeclarationStatementAssignmentsAfter();
	}

	/**
	 * If a local name declaration statement does not have multiplicity, then
	 * the multiplicity of upper bound of the assigned expression must not be
	 * greater than 1.
	 **/
	public boolean localNameDeclarationStatementExpressionMultiplicity() {
		return this.getImpl()
				.localNameDeclarationStatementExpressionMultiplicity();
	}

	/**
	 * The type of a local name declaration statement with a type name is the
	 * single classifier referent of the type name. Otherwise the type is empty.
	 **/
	public boolean localNameDeclarationStatementTypeDerivation() {
		return this.getImpl().localNameDeclarationStatementTypeDerivation();
	}

	public void _deriveAll() {
		this.getType();
		super._deriveAll();
		Expression expression = this.getExpression();
		if (expression != null) {
			expression.deriveAll();
		}
		QualifiedName typeName = this.getTypeName();
		if (typeName != null) {
			typeName.deriveAll();
		}
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.localNameDeclarationStatementAssignmentsBefore()) {
			violations.add(new ConstraintViolation(
					"localNameDeclarationStatementAssignmentsBefore", this));
		}
		if (!this.localNameDeclarationStatementType()) {
			violations.add(new ConstraintViolation(
					"localNameDeclarationStatementType", this));
		}
		if (!this.localNameDeclarationStatementLocalName()) {
			violations.add(new ConstraintViolation(
					"localNameDeclarationStatementLocalName", this));
		}
		if (!this.localNameDeclarationStatementAssignmentsAfter()) {
			violations.add(new ConstraintViolation(
					"localNameDeclarationStatementAssignmentsAfter", this));
		}
		if (!this.localNameDeclarationStatementExpressionMultiplicity()) {
			violations
					.add(new ConstraintViolation(
							"localNameDeclarationStatementExpressionMultiplicity",
							this));
		}
		if (!this.localNameDeclarationStatementTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"localNameDeclarationStatementTypeDerivation", this));
		}
		Expression expression = this.getExpression();
		if (expression != null) {
			expression.checkConstraints(violations);
		}
		QualifiedName typeName = this.getTypeName();
		if (typeName != null) {
			typeName.checkConstraints(violations);
		}
	}

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		s.append(" name:");
		s.append(this.getName());
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
		Expression expression = this.getExpression();
		if (expression != null) {
			System.out.println(prefix + " expression:");
			expression.print(prefix + "  ", includeDerived);
		}
		QualifiedName typeName = this.getTypeName();
		if (typeName != null) {
			System.out.println(prefix + " typeName:");
			typeName.print(prefix + "  ", includeDerived);
		}
		if (includeDerived) {
			ElementReference type = this.getType();
			if (type != null) {
				System.out.println(prefix + " /type:"
						+ type.toString(includeDerived));
			}
		}
	}
} // LocalNameDeclarationStatement
