/*******************************************************************************
 * Copyright 2011, 2017 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.units;

import java.util.Collection;

import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.syntax.common.ConstraintViolation;
import org.modeldriven.alf.syntax.common.ParsedElement;
import org.modeldriven.alf.syntax.units.impl.ActiveClassDefinitionImpl;

/**
 * The definition of an active class.
 **/

public class ActiveClassDefinition extends ClassDefinition {

	public ActiveClassDefinition() {
		this.impl = new ActiveClassDefinitionImpl(this);
	}

	public ActiveClassDefinition(Parser parser) {
		this();
		this.init(parser);
	}

	public ActiveClassDefinition(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
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
	@Override
    public Boolean matchForStub(UnitDefinition unit) {
		return this.getImpl().matchForStub(unit);
	}

	@Override
    public void _deriveAll() {
		super._deriveAll();
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.activeClassDefinitionClassifierBehavior()) {
			violations.add(new ConstraintViolation(
					"activeClassDefinitionClassifierBehavior", this));
		}
	}

	@Override
    public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		return s.toString();
	}

	@Override
    public void print() {
		this.print("", false);
	}

	@Override
    public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	@Override
    public void print(String prefix, boolean includeDerived) {
		super.print(prefix, includeDerived);
		ActivityDefinition classifierBehavior = this.getClassifierBehavior();
		if (classifierBehavior != null) {
			System.out.println(prefix + " classifierBehavior:"
					+ classifierBehavior.toString(includeDerived));
		}
	}
} // ActiveClassDefinition
