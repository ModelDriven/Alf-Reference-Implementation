
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
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

import org.modeldriven.alf.syntax.units.impl.SignalDefinitionImpl;

/**
 * The definition of a signal, whose members must all be properties.
 **/

public class SignalDefinition extends ClassifierDefinition {

	public SignalDefinition() {
		this.impl = new SignalDefinitionImpl(this);
	}

	public SignalDefinition(AlfParser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public SignalDefinition(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public SignalDefinitionImpl getImpl() {
		return (SignalDefinitionImpl) this.impl;
	}

	/**
	 * The specialization referents of a signal definition must all be signals.
	 **/
	public boolean signalDefinitionSpecializationReferent() {
		return this.getImpl().signalDefinitionSpecializationReferent();
	}

	/**
	 * Returns true if the given unit definition matches this signal definition
	 * considered as a classifier definition and the subunit is for a signal
	 * definition.
	 **/
	public Boolean matchForStub(UnitDefinition unit) {
		return this.getImpl().matchForStub(unit);
	}

	/**
	 * In addition to the annotations allowed for classifiers in general, a
	 * signal definition allows an annotation for any stereotype whose metaclass
	 * is consistent with Signal.
	 **/
	public Boolean annotationAllowed(StereotypeAnnotation annotation) {
		return this.getImpl().annotationAllowed(annotation);
	}

	/**
	 * Return true if the given member is either a SignalDefinition or an
	 * imported member whose referent is a SignalDefinition or a Reception
	 * (where signal reception definitions are considered to be kinds of signal
	 * definitions).
	 **/
	public Boolean isSameKindAs(Member member) {
		return this.getImpl().isSameKindAs(member);
	}

	public void _deriveAll() {
		super._deriveAll();
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.signalDefinitionSpecializationReferent()) {
			violations.add(new ConstraintViolation(
					"signalDefinitionSpecializationReferent", this));
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
	}
} // SignalDefinition
