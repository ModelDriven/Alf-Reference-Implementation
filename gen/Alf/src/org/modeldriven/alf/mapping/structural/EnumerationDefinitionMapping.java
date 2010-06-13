
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.structural;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

import fUML.Syntax.Classes.Kernel.*;

public class EnumerationDefinitionMapping extends ClassifierDefinitionMapping {

	public Classifier mapClassifier() {
		return new Enumeration();
	} // mapClassifier

	public void addMemberTo(Element element, NamedElement namespace) {
		if (element instanceof EnumerationLiteral) {
			((Enumeration) namespace)
					.addOwnedLiteral((EnumerationLiteral) element);
		} else {
			this.setError(new ErrorNode(this.getSourceNode(),
					"Only enumeration literals allowed."));
		}
	} // addMemberTo

} // EnumerationDefinitionMapping
