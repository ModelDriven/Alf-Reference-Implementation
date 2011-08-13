
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.units;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.units.TypedElementDefinitionMapping;

import org.modeldriven.alf.syntax.units.PropertyDefinition;

import fUML.Syntax.Classes.Kernel.AggregationKind;
import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.Classes.Kernel.MultiplicityElement;
import fUML.Syntax.Classes.Kernel.Property;
import fUML.Syntax.Classes.Kernel.TypedElement;

import java.util.ArrayList;
import java.util.List;

public class PropertyDefinitionMapping extends TypedElementDefinitionMapping {

    private Property property = null;
    
    public void mapTo(Property property) throws MappingError {
        super.mapTo(property.typedElement, property.multiplicityElement);

        PropertyDefinition definition = this.getPropertyDefinition();
        property.setAggregation(definition.getIsComposite()? 
                AggregationKind.composite: AggregationKind.none);
        property.setName(property.typedElement.name);
    }
    
    public Property getProperty() throws MappingError {
        if (this.property == null) {
            this.property = new Property();
            this.property.typedElement = new TypedElement();
            this.property.multiplicityElement = new MultiplicityElement();
            this.mapTo(property);
        }

        return this.property;
    }
    
	public PropertyDefinition getPropertyDefinition() {
		return (PropertyDefinition) this.getSource();
	}

    @Override
    public MultiplicityElement getMultiplicityElement() {
        return this.property == null? null: this.property.multiplicityElement;
    }

    @Override
    public TypedElement getTypedElement() {
        return this.property == null? null: this.property.typedElement;
    }

    @Override
    public Element getElement() {
        return this.property;
    }

    @Override
    public List<Element> getModelElements() throws MappingError {
        ArrayList<Element> elements = new ArrayList<Element>();
        elements.add(this.getProperty());
        return elements;
	}
    
     @Override
    public String toString() {
        return super.toString() + "aggregation:" + this.property.aggregation;
    }

} // PropertyDefinitionMapping
