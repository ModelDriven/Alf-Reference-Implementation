
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
import fUML.Syntax.CommonBehaviors.Communications.*;

public class SignalDefinitionMapping extends ClassifierDefinitionMapping {

	public Classifier mapClassifier() {
		return new Signal();
	} // mapClassifier

	public void addMemberTo(Element element, NamedElement namespace) {
		if (element instanceof Property) {
			((Signal) namespace).addOwnedAttribute((Property) element);
		} else {
			this.setError(new ErrorNode(this.getSourceNode(),
					"Member not allowed for a data type."));
		}
	} // addMemberTo

} // SignalDefinitionMapping
