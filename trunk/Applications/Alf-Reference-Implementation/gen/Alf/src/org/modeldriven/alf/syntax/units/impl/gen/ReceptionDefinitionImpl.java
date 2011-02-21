
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

import org.omg.uml.*;

import java.util.ArrayList;

/**
 * The declaration of the ability of an active class to receive a signal.
 **/

public class ReceptionDefinitionImpl extends
		org.modeldriven.alf.syntax.units.impl.gen.MemberImpl {

	public ReceptionDefinitionImpl(ReceptionDefinition self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.units.ReceptionDefinition getSelf() {
		return (ReceptionDefinition) this.self;
	}

	public ElementReference deriveSignal() {
		return null; // STUB
	}

	/**
	 * The signal name for a reception definition must have a single referent
	 * that is a signal. This referent must not e a template.
	 **/
	public boolean receptionDefinitionSignalName() {
		return true;
	}

	/**
	 * The signal for a reception definition is the signal referent of the
	 * signal name for the reception definition.
	 **/
	public boolean receptionDefinitionSignalDerivation() {
		this.getSelf().getSignal();
		return true;
	}

	/**
	 * A reception definition is a feature.
	 **/
	public boolean receptionDefinitionIsFeatureDerivation() {
		this.getSelf().getIsFeature();
		return true;
	}

	/**
	 * Returns true if the annotation is for a stereotype that has a metaclass
	 * consistent with Reception.
	 **/
	public Boolean annotationAllowed(StereotypeAnnotation annotation) {
		return false; // STUB
	} // annotationAllowed

	/**
	 * Return true if the given member is either a ReceptionDefinition, a
	 * SignalReceptionDefinition or an imported member whose referent is a
	 * ReceptionDefinition, a SignalReceptionDefinition or a Reception.
	 **/
	public Boolean isSameKindAs(Member member) {
		return false; // STUB
	} // isSameKindAs

} // ReceptionDefinitionImpl
