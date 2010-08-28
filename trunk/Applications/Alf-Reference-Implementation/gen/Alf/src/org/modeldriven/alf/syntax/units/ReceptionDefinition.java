
/*
 * Copyright 2010 Data Access Technologies, Inc. (Model Driven Solutions)
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

import java.util.ArrayList;

/**
 * The declaration of the ability of an active class to receive a signal.
 **/

public class ReceptionDefinition extends Member {

	private QualifiedName signalName = null;
	private ElementReference signal = null; // DERIVED

	public QualifiedName getSignalName() {
		return this.signalName;
	}

	public void setSignalName(QualifiedName signalName) {
		this.signalName = signalName;
	}

	public ElementReference getSignal() {
		return this.signal;
	}

	public void setSignal(ElementReference signal) {
		this.signal = signal;
	}

	public boolean annotationAllowed(StereotypeAnnotation annotation) {
		/*
		 * Returns true if the annotation is for a stereotype that has a
		 * metaclass consistent with Reception.
		 */
		return false; // STUB
	} // annotationAllowed

	public boolean isSameKindAs(Member member) {
		/*
		 * Return true if the given member is either a ReceptionDefinition, a
		 * SignalReceptionDefinition or an imported member whose referent is a
		 * ReceptionDefinition, a SignalReceptionDefinition or a Reception.
		 */
		return false; // STUB
	} // isSameKindAs

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		if (this.signalName != null) {
			this.signalName.print(prefix + " ");
		}
	}
} // ReceptionDefinition
