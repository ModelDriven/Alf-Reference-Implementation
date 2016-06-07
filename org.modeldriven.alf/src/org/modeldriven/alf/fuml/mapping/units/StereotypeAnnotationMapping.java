/*******************************************************************************
 * Copyright 2011-2016 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.units;

import org.modeldriven.alf.fuml.mapping.common.ElementReferenceMapping;
import org.modeldriven.alf.fuml.mapping.common.SyntaxElementMapping;
import org.modeldriven.alf.fuml.mapping.expressions.BooleanLiteralExpressionMapping;
import org.modeldriven.alf.fuml.mapping.expressions.NaturalLiteralExpressionMapping;
import org.modeldriven.alf.fuml.mapping.expressions.RealLiteralExpressionMapping;
import org.modeldriven.alf.fuml.mapping.expressions.UnboundedLiteralExpressionMapping;
import org.modeldriven.alf.fuml.mapping.expressions.StringLiteralExpressionMapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.units.StereotypeAnnotation;
import org.modeldriven.alf.syntax.units.TaggedValue;
import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.Stereotype;
import org.modeldriven.alf.uml.StereotypeApplication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StereotypeAnnotationMapping extends SyntaxElementMapping {

    /**
     * A stereotype annotation, other than for the special cases, maps formally
     * to the application of the identified stereotype to the element mapped
     * from the annotated member. However, an implementation may also use such
     * stereotypes to specify special implementation-specific semantics for the
     * annotated element, except for the standard stereotypes <<Create>> and
     * <<Destroy>>, which are used in the standard Alf mapping for constructors
     * and destructors and <<ModelLibrary>>, which is used to suppress the
     * inclusion of implicit imports.
     */
    
    public void mapApplicationTo(Element element) {
        StereotypeAnnotation annotation = this.getStereotypeAnnotation();
        Stereotype stereotype = annotation.getStereotype();
        if (stereotype != null) {
            StereotypeApplication.addStereotypeApplication(
                    element, stereotype, this.mapTaggedValues());
        }
    }
    
    public Collection<StereotypeApplication.TaggedValue> mapTaggedValues() {
        Collection<StereotypeApplication.TaggedValue> taggedValues =
                new ArrayList<StereotypeApplication.TaggedValue>();
        StereotypeAnnotation annotation = this.getStereotypeAnnotation();
        if (annotation.getTaggedValues() != null) {
            for (TaggedValue taggedValue: annotation.getTaggedValues().getTaggedValue()) {
                taggedValues.add(new StereotypeApplication.TaggedValue(
                        taggedValue.getName(), this.mapTaggedValue(taggedValue)));
            }
        } else if (annotation.getNames() != null) {
            List<ElementReference> attributes = annotation.getImpl().getStereotypeReference().getImpl().getAttributes();
            if (!attributes.isEmpty()) {
                ElementReference attribute = attributes.get(0);
                String name = attribute.getImpl().getName();
                List<Object> values = new ArrayList<Object>();
                for (QualifiedName qualifiedName: annotation.getImpl().getNamesWithScope()) {
                    for (ElementReference reference: qualifiedName.getReferent()) {
                        Element element = ((ElementReferenceMapping)this.fumlMap(reference)).getElement();
                        if (element != null) {
                            values.add(element);
                        }
                    }
                }
                taggedValues.add(new StereotypeApplication.TaggedValue(name, 
                        attribute.getImpl().getUpper() != 1? values:
                        !values.isEmpty()? values.get(0): null));
                        
            }
        }
        return taggedValues;
    }
    
    public Object mapTaggedValue(TaggedValue taggedValue) {
        String value = taggedValue.getValue();
        return taggedValue.getImpl().isBooleanValue()? BooleanLiteralExpressionMapping.valueOf(value):
               taggedValue.getImpl().isNaturalValue()? 
                       ("-".equals(taggedValue.getOperator())? -1: +1) *
                       NaturalLiteralExpressionMapping.valueOf(value):
               taggedValue.getImpl().isRealValue()? 
                       ("-".equals(taggedValue.getOperator())? -1.0: +1.0) *
                       RealLiteralExpressionMapping.valueOf(value):
               taggedValue.getImpl().isUnboundedValue()? UnboundedLiteralExpressionMapping.UNBOUNDED_VALUE:
               taggedValue.getImpl().isStringValue()? StringLiteralExpressionMapping.valueOf(value):
               null;
    }

    @Override
    public Element getElement() {
        return null;
    }

    public List<Element> getModelElements() throws MappingError {
        throw new MappingError(this, this.getErrorMessage());
    }

    public StereotypeAnnotation getStereotypeAnnotation() {
        return (StereotypeAnnotation) this.getSource();
    }

} // StereotypeAnnotationMapping
