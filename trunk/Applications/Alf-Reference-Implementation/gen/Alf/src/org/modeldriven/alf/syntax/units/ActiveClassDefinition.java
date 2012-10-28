
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units;

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

import org.modeldriven.alf.syntax.units.impl.ActiveClassDefinitionImpl;

/**
 * The definition of an active class.
 **/

public class ActiveClassDefinition extends ClassDefinition {

	public ActiveClassDefinition() {
		this.impl = new ActiveClassDefinitionImpl(this);
	}

	public ActiveClassDefinition(AlfParser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public ActiveClassDefinition(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public ActiveClassDefinitionImpl getImpl() {
		return (ActiveClassDefinitionImpl) this.impl;
	}

	public ActivityDefinition getClassifierBehavior() {
		return this.getImpl().getClassifierBehavior();
	}

	public void setClassifierBehavior(ActivityDefinition classifierBehavior) {
		this.getImpl().setClassifierBehavior(classifierBehavior);
	}

	/**
	 * If an active class definition is not abstract, then it must have a
	 * classifier behavior.
	 **/
	public boolean activeClassDefinitionClassifierBehavior() {
		return this.getImpl().activeClassDefinitionClassifierBehavior();
	}

	/**
	 * Returns true if the given unit definition matches this active class
	 * definition considered as a class definition and the subunit is for an
	 * active class definition.
	 **/
	public Boolean matchForStub(UnitDefinition unit) {
		return this.getImpl().matchForStub(unit);
	}

	public void _deriveAll() {
		super._deriveAll();
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.activeClassDefinitionClassifierBehavior()) {
			violations.add(new ConstraintViolation(
					"activeClassDefinitionClassifierBehavior", this));
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
		ActivityDefinition classifierBehavior = this.getClassifierBehavior();
		if (classifierBehavior != null) {
			System.out.println(prefix + " classifierBehavior:"
					+ classifierBehavior.toString(includeDerived));
		}
	}
} // ActiveClassDefinition
