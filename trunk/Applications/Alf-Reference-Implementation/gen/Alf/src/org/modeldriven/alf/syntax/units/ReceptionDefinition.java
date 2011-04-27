
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.omg.uml.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.units.impl.ReceptionDefinitionImpl;

/**
 * The declaration of the ability of an active class to receive a signal.
 **/

public class ReceptionDefinition extends Member {

	public ReceptionDefinition() {
		this.impl = new ReceptionDefinitionImpl(this);
	}

	public ReceptionDefinitionImpl getImpl() {
		return (ReceptionDefinitionImpl) this.impl;
	}

	public QualifiedName getSignalName() {
		return this.getImpl().getSignalName();
	}

	public void setSignalName(QualifiedName signalName) {
		this.getImpl().setSignalName(signalName);
	}

	public ElementReference getSignal() {
		return this.getImpl().getSignal();
	}

	public void setSignal(ElementReference signal) {
		this.getImpl().setSignal(signal);
	}

	/**
	 * The signal name for a reception definition must have a single referent
	 * that is a signal. This referent must not e a template.
	 **/
	public boolean receptionDefinitionSignalName() {
		return this.getImpl().receptionDefinitionSignalName();
	}

	/**
	 * The signal for a reception definition is the signal referent of the
	 * signal name for the reception definition.
	 **/
	public boolean receptionDefinitionSignalDerivation() {
		return this.getImpl().receptionDefinitionSignalDerivation();
	}

	/**
	 * A reception definition is a feature.
	 **/
	public boolean receptionDefinitionIsFeatureDerivation() {
		return this.getImpl().receptionDefinitionIsFeatureDerivation();
	}

	/**
	 * Returns true if the annotation is for a stereotype that has a metaclass
	 * consistent with Reception.
	 **/
	public Boolean annotationAllowed(StereotypeAnnotation annotation) {
		return this.getImpl().annotationAllowed(annotation);
	}

	/**
	 * Return true if the given member is either a ReceptionDefinition, a
	 * SignalReceptionDefinition or an imported member whose referent is a
	 * ReceptionDefinition, a SignalReceptionDefinition or a Reception.
	 **/
	public Boolean isSameKindAs(Member member) {
		return this.getImpl().isSameKindAs(member);
	}

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.receptionDefinitionSignalName()) {
			violations.add(new ConstraintViolation(
					"receptionDefinitionSignalName", this));
		}
		if (!this.receptionDefinitionSignalDerivation()) {
			violations.add(new ConstraintViolation(
					"receptionDefinitionSignalDerivation", this));
		}
		if (!this.receptionDefinitionIsFeatureDerivation()) {
			violations.add(new ConstraintViolation(
					"receptionDefinitionIsFeatureDerivation", this));
		}
		QualifiedName signalName = this.getSignalName();
		if (signalName != null) {
			signalName.checkConstraints(violations);
		}
	}

	public String toString() {
		return this.getImpl().toString();
	}

	public String _toString() {
		StringBuffer s = new StringBuffer(super._toString());
		return s.toString();
	}

	public void print() {
		this.print("");
	}

	public void print(String prefix) {
		super.print(prefix);
		QualifiedName signalName = this.getSignalName();
		if (signalName != null) {
			System.out.println(prefix + " signalName:");
			signalName.print(prefix + "  ");
		}
		ElementReference signal = this.getSignal();
		if (signal != null) {
			System.out.println(prefix + " /signal:" + signal);
		}
	}
} // ReceptionDefinition
