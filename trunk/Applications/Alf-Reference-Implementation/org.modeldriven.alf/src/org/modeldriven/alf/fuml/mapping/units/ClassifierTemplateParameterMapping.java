
/*******************************************************************************
 * Copyright 2011-2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.units;

import org.modeldriven.alf.fuml.mapping.units.ClassifierDefinitionMapping;
import org.modeldriven.alf.mapping.MappingError;

import org.modeldriven.alf.syntax.units.ClassifierTemplateParameter;

import org.modeldriven.alf.uml.Classifier;
import org.modeldriven.alf.uml.DataType;
import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.NamedElement;

import java.util.ArrayList;
import java.util.List;

public class ClassifierTemplateParameterMapping extends
		ClassifierDefinitionMapping {
    
    private org.modeldriven.alf.uml.ClassifierTemplateParameter templateParameter = null;
    
    @Override
    public Classifier mapClassifier() {
        return (Classifier)this.create(DataType.class);
    }

    @Override
    public void addMemberTo(Element element, NamedElement namespace) throws MappingError {        
    }
    
    public org.modeldriven.alf.uml.ClassifierTemplateParameter getTemplateParameter() 
            throws MappingError {
        if (this.templateParameter == null) {
            ClassifierTemplateParameter source = this.getClassifierTemplateParameter();
            if (!source.getImpl().isBound()) {
                Classifier parameteredElement = this.getClassifier();
                if (parameteredElement != null) {
                    this.templateParameter = this.create(
                            org.modeldriven.alf.uml.ClassifierTemplateParameter.class);
                    this.templateParameter.setAllowSubstitutable(false);
                    this.templateParameter.setOwnedParameteredElement(parameteredElement);
                    for (Classifier general: parameteredElement.getGeneral()) {
                        templateParameter.addConstrainingClassifier(general);
                    }
                }
            }
        }
        return this.templateParameter;
    }
    
    public Element getElement() {
        return this.templateParameter;
    }

	@Override
	public List<Element> getModelElements() throws MappingError {
		List<Element> elements = new ArrayList<Element>();
		org.modeldriven.alf.uml.ClassifierTemplateParameter templateParameter = 
		        this.getTemplateParameter();
		if (templateParameter != null) {
		    elements.add(templateParameter);
		}
		return elements;
	}

	public ClassifierTemplateParameter getClassifierTemplateParameter() {
		return (ClassifierTemplateParameter) this.getSource();
	}

} // ClassifierTemplateParameterMapping
