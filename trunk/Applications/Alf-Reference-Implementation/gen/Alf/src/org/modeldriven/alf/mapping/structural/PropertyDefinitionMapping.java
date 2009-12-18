
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

public class PropertyDefinitionMapping extends TypedElementDefinitionMapping {

	private Property property = null;

	public void mapTo(Property property) {
		super.mapTo(property.typedElement, property.multiplicityElement);

		if (!this.isError()) {
			PropertyDefinition definition = this.getPropertyDefinition();
			property
					.setAggregation(definition.isComposite() ? AggregationKind.composite
							: AggregationKind.none);
			property.setName(property.typedElement.name);
		}

		// Initializer mapping not implemented.
	} // mapTo

	public Property getProperty() {
		if (this.property == null) {
			this.property = new Property();
			this.mapTo(property);
		}

		return this.property;
	} // getProperty

	public PropertyDefinition getPropertyDefinition() {
		return (PropertyDefinition) this.getSourceNode();
	} // getPropertyDefinition

	public ArrayList<Element> getModelElements() {
		ArrayList<Element> elements = new ArrayList<Element>();
		elements.add(this.getProperty());
		return elements;
	} // getModelElements

} // PropertyDefinitionMapping
