
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

public class PackageDefinition extends NamespaceDefinition {

	public PackageDefinition() {
		super();
	} // PackageDefinition

	public PackageDefinition(String name) {
		this.setName(name);
	} // PackageDefinition

	public boolean isCompletedBy(Member member) {
		return member instanceof PackageDefinition;
	} // isCompletedBy

	public boolean isDistinguishableFrom(Member other,
			NamespaceDefinition namespace) {
		return !(other instanceof PackageDefinition)
				|| super.isDistinguishableFrom(other, namespace);
	} // isDistinguishableFrom

} // PackageDefinition
