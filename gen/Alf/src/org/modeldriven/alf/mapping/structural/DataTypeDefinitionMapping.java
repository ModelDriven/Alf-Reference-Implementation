
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.structural;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.SyntaxNode;
import org.modeldriven.alf.syntax.behavioral.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.namespaces.*;
import org.modeldriven.alf.syntax.structural.*;

import java.util.ArrayList;

import fUML.Syntax.Classes.Kernel.*;

public class DataTypeDefinitionMapping extends ClassifierDefinitionMapping {

	public Classifier mapClassifier() {
		return new DataType();
	} // mapClassifier

	public void addMemberTo(Element element, NamedElement namespace) {
		if (element instanceof Property) {
			((DataType) namespace).addOwnedAttribute((Property) element);
		} else {
			this.setError(new ErrorNode(this.getSourceNode(),
					"Member not allowed for a data type."));
		}
	} // addMemberTo

} // DataTypeDefinitionMapping
