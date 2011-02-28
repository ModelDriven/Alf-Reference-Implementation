
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units.impl;

import org.modeldriven.alf.syntax.units.*;

/**
 * The definition of both a signal and a reception of that signal as a feature
 * of the containing active class.
 **/

public class SignalReceptionDefinitionImpl extends SignalDefinitionImpl {

	public SignalReceptionDefinitionImpl(SignalReceptionDefinition self) {
		super(self);
	}

	@Override
	public SignalReceptionDefinition getSelf() {
		return (SignalReceptionDefinition) this.self;
	}
	
	/**
	 * A signal reception definition is a feature.
	 **/
	@Override
	public Boolean deriveIsFeature() {
	    return true;
	}

	/*
	 * Derivations
	 */
	
	public boolean signalReceptionDefinitionIsFeatureDerivation() {
		this.getSelf().getIsFeature();
		return true;
	}

} // SignalReceptionDefinitionImpl
