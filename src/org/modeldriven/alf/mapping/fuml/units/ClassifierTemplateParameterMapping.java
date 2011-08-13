
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

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
