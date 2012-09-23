
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
 * The declaration of the ability of an active class to receive a signal.
 **/

public class ReceptionDefinitionImpl extends
		org.modeldriven.alf.syntax.units.impl.gen.MemberImpl {

	private QualifiedName signalName = null;
	private ElementReference signal = null; // DERIVED

	public ReceptionDefinitionImpl(ReceptionDefinition self) {
		super(self);
	}

	public ReceptionDefinition getSelf() {
		return (ReceptionDefinition) this.self;
	}

	public QualifiedName getSignalName() {
		return this.signalName;
	}

	public void setSignalName(QualifiedName signalName) {
		this.signalName = signalName;
	}

	public ElementReference getSignal() {
		if (this.signal == null) {
			this.setSignal(this.deriveSignal());
		}
		return this.signal;
	}

	public void setSignal(ElementReference signal) {
		this.signal = signal;
	}

	protected ElementReference deriveSignal() {
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
