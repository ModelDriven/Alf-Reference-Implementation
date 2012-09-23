
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl.gen;

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

/**
 * A left-hand side that is a name.
 **/

public class NameLeftHandSideImpl extends
		org.modeldriven.alf.syntax.expressions.impl.gen.LeftHandSideImpl {

	private QualifiedName target = null;

	public NameLeftHandSideImpl(NameLeftHandSide self) {
		super(self);
	}

	public NameLeftHandSide getSelf() {
		return (NameLeftHandSide) this.self;
	}

	public QualifiedName getTarget() {
		return this.target;
	}

	public void setTarget(QualifiedName target) {
		this.target = target;
	}

	/**
	 * The assignments after a name left-hand side are the same as the
	 * assignments before.
	 **/
	public boolean nameLeftHandSideAssignmentAfterDerivation() {
		this.getSelf().getAssignmentAfter();
		return true;
	}

	/**
	 * The target of a name left hand side may not already have an assigned
	 * source that is a loop variable definition, an annotation, a sequence
	 * expansion expression or a parameter that is an in parameter.
	 **/
	public boolean nameLeftHandSideTargetAssignment() {
		return true;
	}

	/**
	 * If a name left-hand side has an index, then the target name must already
	 * have an assigned source and the assignments before the index expression
	 * are the assignments before the left-hand side.
	 **/
	public boolean nameLeftHandSideAssignmentsBefore() {
		return true;
	}

} // NameLeftHandSideImpl
