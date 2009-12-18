
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
