
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

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

import org.modeldriven.alf.syntax.units.impl.ReceptionDefinitionImpl;

/**
 * The declaration of the ability of an active class to receive a signal.
 **/

public class ReceptionDefinition extends Member {

	public ReceptionDefinition() {
		this.impl = new ReceptionDefinitionImpl(this);
	}

	public ReceptionDefinition(AlfParser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public ReceptionDefinition(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
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

	public void _deriveAll() {
		this.getSignal();
		super._deriveAll();
		QualifiedName signalName = this.getSignalName();
		if (signalName != null) {
			signalName.deriveAll();
		}
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
		QualifiedName signalName = this.getSignalName();
		if (signalName != null) {
			System.out.println(prefix + " signalName:");
			signalName.print(prefix + "  ", includeDerived);
		}
		if (includeDerived) {
			ElementReference signal = this.getSignal();
			if (signal != null) {
				System.out.println(prefix + " /signal:"
						+ signal.toString(includeDerived));
			}
		}
	}
} // ReceptionDefinition
