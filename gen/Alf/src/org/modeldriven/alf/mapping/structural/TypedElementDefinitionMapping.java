
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

import org.modeldriven.alf.mapping.namespaces.*;

import fUML.Syntax.Classes.Kernel.*;

public abstract class TypedElementDefinitionMapping extends MemberMapping {

	public void mapTo(TypedElement typedElement,
			MultiplicityElement multiplicityElement) {
		super.mapTo(typedElement);

		TypedElementDefinition definition = this.getTypedElementDefinition();
		Member type = definition.getType();

		if (type != null && type.isError()) {
			this.setError(((ErrorMember) type).getError());
		} else {
			TypedElementDeclaration declaration = definition.getDeclaration();
			multiplicityElement.setLower(declaration.getLower());
			multiplicityElement.setUpper(declaration.getUpper());
			multiplicityElement.setIsOrdered(declaration.getOrdering());
			multiplicityElement.setIsUnique(!declaration.getNonuniqueness());

			if (type != null) {
				ClassifierDefinitionMapping mapping = (ClassifierDefinitionMapping) this
						.map((ClassifierDefinition) type);
				Classifier classifier = mapping.getClassifier();
				if (mapping.isError()) {
					this.setError(mapping.getError());
				} else {
					typedElement.setType(classifier);
				}
			}
		}
	} // mapTo

	public TypedElementDefinition getTypedElementDefinition() {
		return (TypedElementDefinition) this.getSourceNode();
	} // getTypedElementDefinition

} // TypedElementDefinitionMapping
