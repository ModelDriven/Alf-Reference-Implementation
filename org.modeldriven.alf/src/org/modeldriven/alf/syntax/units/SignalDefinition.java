/*******************************************************************************
 * Copyright 2011, 2018 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.units;

import org.modeldriven.alf.parser.ParsedElement;
import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.syntax.common.*;
import java.util.Collection;
import org.modeldriven.alf.syntax.units.impl.SignalDefinitionImpl;

/**
 * The definition of a signal, whose members must all be properties.
 **/

public class SignalDefinition extends ClassifierDefinition {

	public SignalDefinition() {
		this.impl = new SignalDefinitionImpl(this);
	}

	public SignalDefinition(Parser parser) {
		this();
		this.init(parser);
	}

	public SignalDefinition(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
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
	@Override
    public Boolean matchForStub(UnitDefinition unit) {
		return this.getImpl().matchForStub(unit);
	}

	/**
	 * In addition to the annotations allowed for classifiers in general, a
	 * signal definition allows an annotation for any stereotype whose metaclass
	 * is consistent with Signal.
	 **/
	@Override
    public Boolean annotationAllowed(StereotypeAnnotation annotation) {
		return this.getImpl().annotationAllowed(annotation);
	}

	/**
	 * Return true if the given member is either a SignalDefinition or an
	 * imported member whose referent is a SignalDefinition or a Signal (where
	 * signal reception definitions are considered to be kinds of signal
	 * definitions).
	 **/
	@Override
    public Boolean isSameKindAs(Member member) {
		return this.getImpl().isSameKindAs(member);
	}

	@Override
    public void _deriveAll() {
		super._deriveAll();
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.signalDefinitionSpecializationReferent()) {
			violations.add(new ConstraintViolation(
					"signalDefinitionSpecializationReferent", this));
		}
	}

	@Override
    public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		return s.toString();
	}

	@Override
    public void print() {
		this.print("", false);
	}

	@Override
    public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	@Override
    public void print(String prefix, boolean includeDerived) {
		super.print(prefix, includeDerived);
	}
} // SignalDefinition
