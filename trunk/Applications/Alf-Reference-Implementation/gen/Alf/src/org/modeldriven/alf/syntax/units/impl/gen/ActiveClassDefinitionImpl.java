
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.units.impl.gen;

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
 * The definition of an active class.
 **/

public class ActiveClassDefinitionImpl extends
		org.modeldriven.alf.syntax.units.impl.gen.ClassDefinitionImpl {

	private ActivityDefinition classifierBehavior = null;

	public ActiveClassDefinitionImpl(ActiveClassDefinition self) {
		super(self);
	}

	public ActiveClassDefinition getSelf() {
		return (ActiveClassDefinition) this.self;
	}

	public ActivityDefinition getClassifierBehavior() {
		return this.classifierBehavior;
	}

	public void setClassifierBehavior(ActivityDefinition classifierBehavior) {
		this.classifierBehavior = classifierBehavior;
	}

	/**
	 * Returns true if the given unit definition matches this active class
	 * definition considered as a class definition and the subunit is for an
	 * active class definition.
	 **/
	public Boolean matchForStub(UnitDefinition unit) {
		return false; // STUB
	} // matchForStub

} // ActiveClassDefinitionImpl
