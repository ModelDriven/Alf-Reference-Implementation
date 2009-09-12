
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.syntax.structural;

import alf.nodes.*;
import alf.syntax.SyntaxNode;
import alf.syntax.behavioral.*;
import alf.syntax.expressions.*;
import alf.syntax.namespaces.*;
import alf.syntax.structural.*;

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
