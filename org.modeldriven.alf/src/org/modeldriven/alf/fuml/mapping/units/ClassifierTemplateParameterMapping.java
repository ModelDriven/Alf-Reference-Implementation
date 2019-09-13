
/*******************************************************************************
 * Copyright 2011-2017 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.units;

import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.fuml.mapping.common.ElementReferenceMapping;
import org.modeldriven.alf.fuml.mapping.units.ClassifierDefinitionMapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.units.ClassifierTemplateParameter;
import org.modeldriven.alf.uml.Activity;
import org.modeldriven.alf.uml.Association;
import org.modeldriven.alf.uml.Class_;
import org.modeldriven.alf.uml.Classifier;
import org.modeldriven.alf.uml.DataType;
import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.Enumeration;
import org.modeldriven.alf.uml.NamedElement;
import org.modeldriven.alf.uml.Signal;

import java.util.ArrayList;
import java.util.List;

public class ClassifierTemplateParameterMapping extends
		ClassifierDefinitionMapping {
    
    private org.modeldriven.alf.uml.ClassifierTemplateParameter templateParameter = null;
    
    @Override
    public Classifier mapClassifier() {
        ClassifierTemplateParameter source = this.getClassifierTemplateParameter();
        
        for (ElementReference specializationReferent: source.getSpecializationReferent()) {
            Classifier constrainingClassifier = (Classifier)specializationReferent.getImpl().getUml();
            if (constrainingClassifier == null) {
                FumlMapping mapping = this.fumlMap(specializationReferent);
                if (mapping instanceof ElementReferenceMapping) {
                    mapping = ((ElementReferenceMapping)mapping).getMapping();
                }
                if (mapping instanceof ClassifierDefinitionMapping) {
                    constrainingClassifier = ((ClassifierDefinitionMapping)mapping).getClassifierOnly();        
                }
            }
            if (constrainingClassifier instanceof Activity) {
                return (Classifier)this.create(Activity.class);
            } else if (constrainingClassifier instanceof Class_) {
                return (Classifier)this.create(Class_.class);
            } else if (constrainingClassifier instanceof DataType) {
                return (Classifier)this.create(DataType.class);
            } else if (constrainingClassifier instanceof Association) {
                return (Classifier)this.create(Association.class);
            } else if (constrainingClassifier instanceof Enumeration) {
                return (Classifier)this.create(Enumeration.class);
            } else if (constrainingClassifier instanceof Signal) {
                return (Classifier)this.create(Signal.class);
           }
        }
        
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
