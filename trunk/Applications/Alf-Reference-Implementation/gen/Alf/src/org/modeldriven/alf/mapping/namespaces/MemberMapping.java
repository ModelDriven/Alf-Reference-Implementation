
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.namespaces;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.SyntaxNode;
import org.modeldriven.alf.syntax.behavioral.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.namespaces.*;
import org.modeldriven.alf.syntax.structural.*;

import java.util.ArrayList;

import org.modeldriven.alf.mapping.*;

import fUML.Syntax.Classes.Kernel.*;

public abstract class MemberMapping extends DocumentedNodeMapping {

	public void mapTo(NamedElement namedElement) {
		super.mapTo(namedElement);
		namedElement.setName(this.getMember().getName());
	} // mapTo

	public Member getMember() {
		return (Member) this.getSourceNode();
	} // getMember

} // MemberMapping
