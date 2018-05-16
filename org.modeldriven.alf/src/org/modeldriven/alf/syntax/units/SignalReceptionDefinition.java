/*******************************************************************************
 * Copyright 2011, 2017 Data Access Technologies, Inc. (Model Driven Solutions)
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
import org.modeldriven.alf.syntax.units.impl.SignalReceptionDefinitionImpl;

/**
 * The definition of both a signal and a reception of that signal as a feature
 * of the containing active class.
 **/

public class SignalReceptionDefinition extends SignalDefinition {

	public SignalReceptionDefinition() {
		this.impl = new SignalReceptionDefinitionImpl(this);
	}

	public SignalReceptionDefinition(Parser parser) {
		this();
		this.init(parser);
	}

	public SignalReceptionDefinition(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
    public SignalReceptionDefinitionImpl getImpl() {
		return (SignalReceptionDefinitionImpl) this.impl;
	}

	/**
	 * A signal reception definition is a feature.
	 **/
	public boolean signalReceptionDefinitionIsFeatureDerivation() {
		return this.getImpl().signalReceptionDefinitionIsFeatureDerivation();
	}

	@Override
    public void _deriveAll() {
		super._deriveAll();
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.signalReceptionDefinitionIsFeatureDerivation()) {
			violations.add(new ConstraintViolation(
					"signalReceptionDefinitionIsFeatureDerivation", this));
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
} // SignalReceptionDefinition
