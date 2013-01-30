
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

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
