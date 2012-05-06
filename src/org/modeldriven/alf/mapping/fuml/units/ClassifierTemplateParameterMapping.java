
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.mapping.fuml.units;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.units.ClassifierDefinitionMapping;

import org.modeldriven.alf.syntax.units.ClassifierTemplateParameter;

import fUML.Syntax.Classes.Kernel.Classifier;
import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.Classes.Kernel.NamedElement;

import java.util.List;

public class ClassifierTemplateParameterMapping extends
		ClassifierDefinitionMapping {
    
    /**
     * For fUML, classifier template parameters are not mapped, since all
     * template bindings are expanded during static semantic analysis.
     */
    
    public ClassifierTemplateParameterMapping() {
        this.setErrorMessage("No mapping for ClassifierTemplateParameter.");
    }
    
    @Override
    public Classifier mapClassifier() {
        return null;
    }

    @Override
    public void addMemberTo(Element element, NamedElement namespace) throws MappingError {        
    }

    @Override
    public Classifier getClassifier() {
        return null;
    }

	public ClassifierTemplateParameter getClassifierTemplateParameter() {
		return (ClassifierTemplateParameter) this.getSource();
	}

	@Override
	public List<Element> getModelElements() throws MappingError {
		throw new MappingError(this, this.getErrorMessage());
	}

} // ClassifierTemplateParameterMapping
