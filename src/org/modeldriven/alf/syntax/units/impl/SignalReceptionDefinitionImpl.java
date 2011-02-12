
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units.impl;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

/**
 * The definition of both a signal and a reception of that signal as a feature
 * of the containing active class.
 **/

public class SignalReceptionDefinitionImpl extends
		org.modeldriven.alf.syntax.units.impl.SignalDefinitionImpl {

	public SignalReceptionDefinitionImpl(SignalReceptionDefinition self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.units.SignalReceptionDefinition getSelf() {
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
