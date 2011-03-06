
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.omg.uml.*;

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

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		QualifiedName target = this.getTarget();
		if (target != null) {
			System.out.println(prefix + " target:");
			target.print(prefix + "  ");
		}
	}
} // NameLeftHandSide
