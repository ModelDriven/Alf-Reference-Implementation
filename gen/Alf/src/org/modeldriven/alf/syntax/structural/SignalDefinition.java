
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.structural;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.SyntaxNode;
import org.modeldriven.alf.syntax.behavioral.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.namespaces.*;
import org.modeldriven.alf.syntax.structural.*;

import java.util.ArrayList;

public class SignalDefinition extends ClassifierDefinition {

	public boolean isCompletedBy(Member member) {
		return member instanceof SignalDefinition
				&& super.isCompletedBy(member);
	} // isCompletedBy

	public boolean isDistinguishableFrom(Member other,
			NamespaceDefinition namespace) {
		if (other instanceof ReceptionDefinition
				|| other instanceof OperationDefinition) {
			return other.isDistinguishableFrom(this, namespace); // In case this
																	// is a
																	// SignalReeptionDefinition
		} else {
			return !(other instanceof SignalDefinition)
					|| super.isDistinguishableFrom(other, namespace);
		}
	} // isDistinguishableFrom

	public boolean canSpecialize(Member member) {
		// TODO
		return false;
	} // canSpecialize

} // SignalDefinition
