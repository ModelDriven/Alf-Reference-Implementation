
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.statements;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.omg.uml.*;

import java.util.ArrayList;

import org.modeldriven.alf.syntax.statements.impl.ForStatementImpl;

/**
 * A looping statement that gives successive values to one or more loop
 * variables on each iteration.
 **/

public class ForStatement extends Statement {

	private Block body = null;
	private ArrayList<LoopVariableDefinition> variableDefinition = new ArrayList<LoopVariableDefinition>();
	private Boolean isParallel = null; // DERIVED

	public ForStatement() {
		this.impl = new ForStatementImpl(this);
	}

	public ForStatementImpl getImpl() {
		return (ForStatementImpl) this.impl;
	}

	public Block getBody() {
		return this.body;
	}

	public void setBody(Block body) {
		this.body = body;
	}

	public ArrayList<LoopVariableDefinition> getVariableDefinition() {
		return this.variableDefinition;
	}

	public void setVariableDefinition(
			ArrayList<LoopVariableDefinition> variableDefinition) {
		this.variableDefinition = variableDefinition;
	}

	public void addVariableDefinition(LoopVariableDefinition variableDefinition) {
		this.variableDefinition.add(variableDefinition);
	}

	public Boolean getIsParallel() {
		if (this.isParallel == null) {
			this.isParallel = this.getImpl().deriveIsParallel();
		}
		return this.isParallel;
	}

	/**
	 * The assignments before a loop variable definition in a for statement are
	 * the same as before the for statement. The assignments before the body of
	 * the statement include all the assignments before the statement plus any
	 * new assignments from the loop variable definitions, except that, if the
	 * statement is parallel, the assigned sources of any names given in @parallel
	 * annotations are changed to be the for statement itself.
	 **/
	public boolean forStatementAssignmentsBefore() {
		return this.getImpl().forStatementAssignmentsBefore();
	}

	/**
	 * The loop variables are unassigned after a for statement. Other than the
	 * loop variables, if the assigned source for a name after the body of a for
	 * statement is the same as after the for variable definitions, then the
	 * assigned source for the name after the for statement is the same as after
	 * the for variable definitions. If a name is unassigned after the for
	 * variable definitions, then it is unassigned after the for statement (even
	 * if it is assigned in the body of the for statement). If, after the loop
	 * variable definitions, a name has an assigned source, and it has a
	 * different assigned source after the body of the for statement, then the
	 * assigned source after the for statement is the for statement itself.
	 **/
	public boolean forStatementAssignmentsAfter() {
		return this.getImpl().forStatementAssignmentsAfter();
	}

	/**
	 * A @parallel annotation of a for statement may include a list of names.
	 * Each such name must be already assigned after the loop variable
	 * definitions of the for statement, with a multiplicity of [0..*]. These
	 * names may only be used within the body of the for statement as the first
	 * argument to for the CollectionFunctions::add behavior.
	 **/
	public boolean forStatementParallelAnnotationNames() {
		return this.getImpl().forStatementParallelAnnotationNames();
	}

	/**
	 * If, after the loop variable definitions of a parallel for statement, a
	 * name has an assigned source, then it must have the same assigned source
	 * after the block of the for statement. Other than for names defined in the @parallel
	 * annotation of the for statement, the assigned source for such names is
	 * the same after the for statement as before it. Any names defined in the @parallel
	 * annotation have the for statement itself as their assigned source after
	 * the for statement. Other than names given in the @parallel annotation, if
	 * a name is unassigned after the loop variable definitions, then it is
	 * considered unassigned after the for statement, even if it is assigned in
	 * the block of the for statement.
	 **/
	public boolean forStatementParallelAssignmentsAfter() {
		return this.getImpl().forStatementParallelAssignmentsAfter();
	}

	/**
	 * The isFirst attribute of the first loop variable definition for a for
	 * statement is true while the isFirst attribute if false for any other
	 * definitions.
	 **/
	public boolean forStatementVariableDefinitions() {
		return this.getImpl().forStatementVariableDefinitions();
	}

	/**
	 * The assigned sources for loop variables after the body of a for statement
	 * must be the for statement (the same as before the body).
	 **/
	public boolean forStatementLoopVariables() {
		return this.getImpl().forStatementLoopVariables();
	}

	/**
	 * A for statement is parallel if it has a @parallel annotation.
	 **/
	public boolean forStatementIsParallelDerivation() {
		return this.getImpl().forStatementIsParallelDerivation();
	}

	/**
	 * The enclosing statement for all statements in the body of a for statement
	 * are the for statement.
	 **/
	public boolean forStatementEnclosedStatements() {
		return this.getImpl().forStatementEnclosedStatements();
	}

	/**
	 * In addition to an @isolated annotation, a for statement may have a @parallel
	 * annotation.
	 **/
	public Boolean annotationAllowed(Annotation annotation) {
		return this.getImpl().annotationAllowed(annotation);
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		Boolean isParallel = this.getIsParallel();
		if (isParallel != null) {
			s.append(" /isParallel:");
			s.append(isParallel);
		}
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		Block body = this.getBody();
		if (body != null) {
			System.out.println(prefix + " body:" + body);
		}
		ArrayList<LoopVariableDefinition> variableDefinition = this
				.getVariableDefinition();
		if (variableDefinition != null) {
			if (variableDefinition.size() > 0) {
				System.out.println(prefix + " variableDefinition:");
			}
			for (LoopVariableDefinition _variableDefinition : (ArrayList<LoopVariableDefinition>) variableDefinition
					.clone()) {
				if (_variableDefinition != null) {
					_variableDefinition.print(prefix + "  ");
				} else {
					System.out.println(prefix + "  null");
				}
			}
		}
	}
} // ForStatement
