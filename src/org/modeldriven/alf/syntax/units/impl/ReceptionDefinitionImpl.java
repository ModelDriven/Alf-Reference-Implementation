
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

/**
 * The declaration of the ability of an active class to receive a signal.
 **/

public class ReceptionDefinitionImpl extends MemberImpl {

	private QualifiedName signalName = null;
	private ElementReference signal = null; // DERIVED

	public ReceptionDefinitionImpl(ReceptionDefinition self) {
		super(self);
	}

	@Override
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

    /**
     * The signal for a reception definition is the signal referent of the
     * signal name for the reception definition.
     **/
	protected ElementReference deriveSignal() {
	    ElementReference referent = null;
	    QualifiedName signalName = this.getSelf().getSignalName();
	    if (signalName != null) {
	        referent = signalName.getImpl().getSignalReferent();
	    }
		return referent;
	}
	
	/**
	 * A reception definition is a feature.
	 */
	@Override
	protected Boolean deriveIsFeature() {
	    return true;
	}
	
	/*
	 * Derivations
	 */

    public boolean receptionDefinitionSignalDerivation() {
        this.getSelf().getSignal();
        return true;
    }

    public boolean receptionDefinitionIsFeatureDerivation() {
        this.getSelf().getIsFeature();
        return true;
    }
    
    /*
     * Constraints
     */

	/**
	 * The signal name for a reception definition must have a single referent
	 * that is a signal. This referent must not be a template.
	 **/
	public boolean receptionDefinitionSignalName() {
	    ElementReference signal = this.getSelf().getSignal();
		return signal != null && !signal.getImpl().isTemplate();
	}
	
	/*
	 * Helper Methods
	 */

	/**
	 * Returns true if the annotation is for a stereotype that has a metaclass
	 * consistent with Reception.
	 **/
	public Boolean annotationAllowed(StereotypeAnnotation annotation) {
	    // TODO Allow stereotypes consistent with signal definitions.
		return false;
	} // annotationAllowed

	/**
	 * Return true if the given member is either a ReceptionDefinition, a
	 * SignalReceptionDefinition or an imported member whose referent is a
	 * ReceptionDefinition, a SignalReceptionDefinition or a Reception.
	 **/
	public Boolean isSameKindAs(Member member) {
		return member.getImpl().getReferent().getImpl().isReception();
	} // isSameKindAs

} // ReceptionDefinitionImpl
