
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.mapping.namespaces;

import alf.nodes.*;
import alf.syntax.SyntaxNode;
import alf.syntax.behavioral.*;
import alf.syntax.expressions.*;
import alf.syntax.namespaces.*;
import alf.syntax.structural.*;

import java.util.ArrayList;

import alf.mapping.*;

import fUML.Syntax.Classes.Kernel.*;

public abstract class MemberMapping extends DocumentedNodeMapping {

	public void mapTo(NamedElement namedElement) {
		super.mapTo(namedElement);
		namedElement.setName(this.getMember().getName());
	} // mapTo

	public Member getMember() {
		return (Member) this.getSource();
	} // getMember

} // MemberMapping
