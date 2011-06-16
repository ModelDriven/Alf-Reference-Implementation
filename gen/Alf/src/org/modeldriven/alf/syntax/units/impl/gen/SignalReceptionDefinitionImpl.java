
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units.impl.gen;

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
 * The definition of both a signal and a reception of that signal as a feature
 * of the containing active class.
 **/

public class SignalReceptionDefinitionImpl extends
		org.modeldriven.alf.syntax.units.impl.gen.SignalDefinitionImpl {

	public SignalReceptionDefinitionImpl(SignalReceptionDefinition self) {
		super(self);
	}

	public SignalReceptionDefinition getSelf() {
		return (SignalReceptionDefinition) this.self;
	}

	/**
	 * A signal reception definition is a feature.
	 **/
	public boolean signalReceptionDefinitionIsFeatureDerivation() {
		this.getSelf().getIsFeature();
		return true;
	}

} // SignalReceptionDefinitionImpl
