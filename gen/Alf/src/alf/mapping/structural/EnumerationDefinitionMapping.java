
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.mapping.structural;

import alf.nodes.*;
import alf.syntax.SyntaxNode;
import alf.syntax.behavioral.*;
import alf.syntax.expressions.*;
import alf.syntax.namespaces.*;
import alf.syntax.structural.*;

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
			this.setError(new ErrorNode(this.getSource(),
					"Only enumeration literals allowed."));
		}
	} // addMemberTo

} // EnumerationDefinitionMapping
