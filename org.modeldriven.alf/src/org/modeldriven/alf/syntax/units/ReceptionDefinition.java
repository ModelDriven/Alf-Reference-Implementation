
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
import org.modeldriven.alf.syntax.expressions.*;
import java.util.Collection;
import org.modeldriven.alf.syntax.units.impl.ReceptionDefinitionImpl;

/**
 * The declaration of the ability of an active class to receive a signal.
 **/

public class ReceptionDefinition extends Member {

	public ReceptionDefinition() {
		this.impl = new ReceptionDefinitionImpl(this);
	}

	public ReceptionDefinition(Parser parser) {
		this();
		this.init(parser);
	}

	public ReceptionDefinition(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
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
	@Override
    public Boolean annotationAllowed(StereotypeAnnotation annotation) {
		return this.getImpl().annotationAllowed(annotation);
	}

	/**
	 * Return true if the given member is either a ReceptionDefinition, a
	 * SignalReceptionDefinition or an imported member whose referent is a
	 * ReceptionDefinition, a SignalReceptionDefinition or a Reception.
	 **/
	@Override
    public Boolean isSameKindAs(Member member) {
		return this.getImpl().isSameKindAs(member);
	}

    @Override
    public void _addExternalReferences(Collection<ExternalElementReference> references) {
        super._addExternalReferences(references);
        addExternalReferencesFor(references, this.getSignalName());
    }

	@Override
    public void _deriveAll() {
		this.getSignal();
		super._deriveAll();
		QualifiedName signalName = this.getSignalName();
		if (signalName != null) {
			signalName.deriveAll();
		}
	}

	@Override
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
