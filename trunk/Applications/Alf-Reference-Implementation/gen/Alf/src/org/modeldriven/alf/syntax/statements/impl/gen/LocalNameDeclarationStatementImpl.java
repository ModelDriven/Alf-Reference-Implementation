
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.statements.impl.gen;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.omg.uml.*;

import java.util.ArrayList;

/**
 * A statement that declares the type of a local name and assigns it an initial
 * value.
 **/

public class LocalNameDeclarationStatementImpl extends
		org.modeldriven.alf.syntax.statements.impl.gen.StatementImpl {

	public LocalNameDeclarationStatementImpl(LocalNameDeclarationStatement self) {
		super(self);
	}

	public LocalNameDeclarationStatement getSelf() {
		return (LocalNameDeclarationStatement) this.self;
	}

	public ElementReference deriveType() {
		return null; // STUB
	}

	/**
	 * The assignments before the expression of a local name declaration
	 * statement are the same as the assignments before the statement.
	 **/
	public boolean localNameDeclarationStatementAssignmentsBefore() {
		return true;
	}

	/**
	 * If the type name in a local name declaration statement is not empty, then
	 * it must resolve to a non-template classifier and the expression must be
	 * assignable to that classifier.
	 **/
	public boolean localNameDeclarationStatementType() {
		return true;
	}

	/**
	 * The local name in a local name declaration statement must be unassigned
	 * before the statement and before the expression in the statement. It must
	 * remain unassigned after the expression.
	 **/
	public boolean localNameDeclarationStatementLocalName() {
		return true;
	}

	/**
	 * The assignments after a local name declaration statement are the
	 * assignments before the statement plus a new assignment for the local name
	 * defined by the statement. The assigned source for the local name is the
	 * local name declaration statement. The local name has the type denoted by
	 * the type name if this is not empty and is untyped otherwise. If the
	 * statement has multiplicity, then the multiplicity of the local name is
	 * [0..*], otherwise it is [0..1].
	 **/
	public boolean localNameDeclarationStatementAssignmentsAfter() {
		return true;
	}

	/**
	 * If a local name declaration statement does not have multiplicity, then
	 * the multiplicity of upper bound of the assigned expression must not be
	 * greater than 1.
	 **/
	public boolean localNameDeclarationStatementExpressionMultiplicity() {
		return true;
	}

	/**
	 * The type of a local name declaration statement with a type name is the
	 * single classifier referent of the type name. Otherwise it is the type of
	 * the expression of the statement.
	 **/
	public boolean localNameDeclarationStatementTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

} // LocalNameDeclarationStatementImpl
