
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

import org.omg.uml.Element;
import org.omg.uml.Profile;
import org.omg.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.statements.impl.SwitchClauseImpl;

/**
 * A clause in a switch statement with a set of cases and a sequence of
 * statements that may be executed if one of the cases matches the switch value.
 **/

public class SwitchClause extends SyntaxElement {

	public SwitchClause() {
		this.impl = new SwitchClauseImpl(this);
	}

	public SwitchClauseImpl getImpl() {
		return (SwitchClauseImpl) this.impl;
	}

	public Collection<Expression> getCase() {
		return this.getImpl().getCase();
	}

	public void setCase(Collection<Expression> case_) {
		this.getImpl().setCase(case_);
	}

	public void addCase(Expression case_) {
		this.getImpl().addCase(case_);
	}

	public Block getBlock() {
		return this.getImpl().getBlock();
	}

	public void setBlock(Block block) {
		this.getImpl().setBlock(block);
	}

	/**
	 * The assignments before any case expression of a switch clause are the
	 * same as the assignments before the clause. The assignments before the
	 * block of a switch clause are the assignments after all case expressions.
	 **/
	public boolean switchClauseAssignmentsBefore() {
		return this.getImpl().switchClauseAssignmentsBefore();
	}

	/**
	 * If a name is unassigned before a switch clause, then it must be
	 * unassigned after all case expressions of the clause (i.e., new local
	 * names may not be defined in case expressions).
	 **/
	public boolean switchClauseCaseLocalNames() {
		return this.getImpl().switchClauseCaseLocalNames();
	}

	/**
	 * The assignments before a switch clause are the assignments before any
	 * case expression of the clause.
	 **/
	public Collection<AssignedSource> assignmentsBefore() {
		return this.getImpl().assignmentsBefore();
	}

	/**
	 * The assignments after a switch clause are the assignments after the block
	 * of the switch clause.
	 **/
	public Collection<AssignedSource> assignmentsAfter() {
		return this.getImpl().assignmentsAfter();
	}

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.switchClauseAssignmentsBefore()) {
			violations.add(new ConstraintViolation(
					"switchClauseAssignmentsBefore", this));
		}
		if (!this.switchClauseCaseLocalNames()) {
			violations.add(new ConstraintViolation(
					"switchClauseCaseLocalNames", this));
		}
		for (Object _case_ : this.getCase().toArray()) {
			((Expression) _case_).checkConstraints(violations);
		}
		Block block = this.getBlock();
		if (block != null) {
			block.checkConstraints(violations);
		}
	}

	public String toString() {
		return "(" + this.hashCode() + ")" + this.getImpl().toString();
	}

	public String _toString() {
		StringBuffer s = new StringBuffer(super._toString());
		return s.toString();
	}

	public void print() {
		this.print("");
	}

	public void print(String prefix) {
		super.print(prefix);
		Collection<Expression> case_ = this.getCase();
		if (case_ != null) {
			if (case_.size() > 0) {
				System.out.println(prefix + " case:");
			}
			for (Object _object : case_.toArray()) {
				Expression _case_ = (Expression) _object;
				if (_case_ != null) {
					_case_.print(prefix + "  ");
				} else {
					System.out.println(prefix + "  null");
				}
			}
		}
		Block block = this.getBlock();
		if (block != null) {
			System.out.println(prefix + " block:");
			block.print(prefix + "  ");
		}
	}
} // SwitchClause
