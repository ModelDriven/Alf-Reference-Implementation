
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.mapping.fuml.units;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.units.TypedElementDefinitionMapping;

import org.modeldriven.alf.syntax.units.PropertyDefinition;

import fUML.Syntax.Classes.Kernel.AggregationKind;
import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.Classes.Kernel.MultiplicityElement;
import fUML.Syntax.Classes.Kernel.NamedElement;
import fUML.Syntax.Classes.Kernel.Property;
import fUML.Syntax.Classes.Kernel.TypedElement;

import java.util.ArrayList;
import java.util.List;

public class PropertyDefinitionMapping extends TypedElementDefinitionMapping {

    private Property property = null;
    
    /**
     * 1. A property definition maps to a property with the given name that is a
     * structural feature of the classifier mapped from the classifier
     * definition that is the namespace of the property definition. Its type and
     * multiplicity are mapped as a typed element definition.
     * 
     * 2. If the property definition is composite, then the property has
     * aggregation=composite. Otherwise it has aggregation = none.
     * 
     * 3. An initializer expression is not mapped as part of the property
     * definition, but, rather, as part of the mapping of the constructor(s) for
     * the owning class.
     */
    
    public void mapTo(Property property) throws MappingError {
        super.mapTo(property.typedElement, property.multiplicityElement);

        PropertyDefinition definition = this.getPropertyDefinition();
        property.setAggregation(definition.getIsComposite()? 
                AggregationKind.composite: AggregationKind.none);
        property.setName(property.typedElement.name);
    }
    
    @Override
    public NamedElement getNamedElement() throws MappingError {
        return this.getProperty();
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
        return super.toString() + " aggregation:" + this.property.aggregation;
    }
    
    @Override
    public void print(String prefix) {
        super.print(prefix);
        
        if (this.property != null) {
            System.out.println(prefix + " property: " + this.property);
        }
    }

} // PropertyDefinitionMapping
