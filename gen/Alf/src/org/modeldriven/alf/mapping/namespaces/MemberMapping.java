
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.namespaces;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

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
