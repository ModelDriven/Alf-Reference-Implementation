
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

public class AssociationDefinition extends ClassifierDefinition {

	public boolean isCompletedBy(Member member) {
		return member instanceof AssociationDefinition
				&& super.isCompletedBy(member);
	} // isCompletedBy

	public boolean isDistinguishableFrom(Member other,
			NamespaceDefinition namespace) {
		return !(other instanceof AssociationDefinition)
				|| super.isDistinguishableFrom(other, namespace);
	} // isDistinguishableFrom

	public boolean canSpecialize(Member member) {
		return member instanceof AssociationDefinition;
	} // canSpecialize

} // AssociationDefinition
