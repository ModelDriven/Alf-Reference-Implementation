
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.units.*;

/**
 * The definition of a signal, whose members must all be properties.
 **/

public class SignalDefinitionImpl extends ClassifierDefinitionImpl {

	public SignalDefinitionImpl(SignalDefinition self) {
		super(self);
	}

	@Override
	public SignalDefinition getSelf() {
		return (SignalDefinition) this.self;
	}

	/*
	 * Constraints
	 */
	
	/**
	 * The specialization referents of a signal definition must all be signals.
	 **/
	public boolean signalDefinitionSpecializationReferent() {
        for (ElementReference referent: this.getSelf().getSpecializationReferent()) {
            if (!referent.getImpl().isSignal()) {
                return false;
            }
        }
        return true;
	}
	
	/*
	 * Helper Methods
	 */

	/**
	 * Returns true if the given unit definition matches this signal definition
	 * considered as a classifier definition and the subunit is for a signal
	 * definition.
	 **/
	@Override
	public Boolean matchForStub(UnitDefinition unit) {
		return unit.getDefinition() instanceof SignalDefinition &&
		        super.matchForStub(unit);
	} // matchForStub

	/**
	 * In addition to the annotations allowed for classifiers in general, a
	 * signal definition allows an annotation for any stereotype whose metaclass
	 * is consistent with Signal.
	 **/
	public Boolean annotationAllowed(StereotypeAnnotation annotation) {
	    // TODO Allow stereotypes consistent with signal definitions
		return super.annotationAllowed(annotation);
	} // annotationAllowed

	/**
	 * Return true if the given member is either a SignalDefinition or an
	 * imported member whose referent is a SignalDefinition or a Signal
	 * (where signal reception definitions are considered to be kinds of signal
	 * definitions).
	 **/
	public Boolean isSameKindAs(Member member) {
		return member.getImpl().getReferent().getImpl().isSignal();
	} // isSameKindAs

} // SignalDefinitionImpl