
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

public class ClassDefinitionMapping extends ClassifierDefinitionMapping {

	public Classifier mapClassifier() {
		return new Class_();
	} // mapClassifier

	public void addMemberTo(Element element, NamedElement namespace) {
		Class_ class_ = (Class_) namespace;

		if (element instanceof Property) {
			class_.addOwnedAttribute((Property) element);
		} else if (element instanceof Operation) {
			class_.addOwnedOperation((Operation) element);
		} else if (element instanceof Classifier) {
			this.setError(new ErrorNode(this.getSourceNode(),
					"Nested classifiers not supported."));
		} else {
			this.setError(new ErrorNode(this.getSourceNode(),
					"Member not legal for a class."));
		}
	} // addMemberTo

	public ClassDefinition getClassDefinition() {
		return (ClassDefinition) this.getSourceNode();
	} // getClassDefinition

} // ClassDefinitionMapping
