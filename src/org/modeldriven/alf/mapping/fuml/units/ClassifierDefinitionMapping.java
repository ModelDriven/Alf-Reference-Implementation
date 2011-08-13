
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.units;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.mapping.fuml.common.ElementReferenceMapping;
import org.modeldriven.alf.mapping.fuml.units.NamespaceDefinitionMapping;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.units.ClassifierDefinition;

import fUML.Syntax.Classes.Kernel.Classifier;
import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.Classes.Kernel.Generalization;

public abstract class ClassifierDefinitionMapping extends
		NamespaceDefinitionMapping {
    
    private Classifier classifier = null;
    
    public void mapTo(Classifier classifier) throws MappingError {
        super.mapTo(classifier);

        ClassifierDefinition definition = this.getClassifierDefinition();
        classifier.setIsAbstract(definition.getIsAbstract());

        Collection<ElementReference> referents = 
            definition.getSpecializationReferent();

        for (ElementReference referent: referents) {
            FumlMapping mapping = this.fumlMap(referent);
            if (mapping instanceof ElementReferenceMapping) {
                mapping = ((ElementReferenceMapping)mapping).getMapping();
            }
            if (!(mapping instanceof ClassifierDefinitionMapping)) {
                this.throwError("Error mapping generalization: " + referent);
            } else {
                Classifier general = 
                    ((ClassifierDefinitionMapping)mapping).getClassifier();        
                Generalization generalization = new Generalization();
                generalization.setGeneral(general);
                classifier.addGeneralization(generalization);                   
            }
        }

        /*
        System.out.println("mapTo: " + classifier.name + " members:");
        for (int i = 0; i < classifier.member.size(); i++) {
          System.out.println(classifier.member.getValue(i).name);
        }
        */

    }
    
    public abstract Classifier mapClassifier();
    
    public Classifier getClassifier() throws MappingError {
        if (this.classifier == null) {
            this.classifier = this.mapClassifier();
            this.mapTo(classifier);
        }

        return this.classifier;
    }
    
    public Element getElement() {
        return this.classifier;
    }

	public ClassifierDefinition getClassifierDefinition() {
		return (ClassifierDefinition) this.getSource();
	}
	
	@Override
	public List<Element> getModelElements() throws MappingError {
	    ArrayList<Element> elements = new ArrayList<Element>();
        Classifier classifier = this.getClassifier();
        if (classifier != null) {
            elements.add(this.getClassifier());
        }
	    return elements;
	}
	
	@Override
	public String toString() {
	    return super.toString() + (this.classifier == null? "":
	        " isAbstract:" + this.classifier.isAbstract);
	}
	
	@Override
	public void print(String prefix) {
	    super.print(prefix);
	    System.out.println(prefix + " classifier: " + this.classifier);
	    if (this.classifier != null && !this.classifier.general.isEmpty()) {
	        System.out.println(prefix + " general:");
    	    for (Classifier general: this.classifier.general) {
    	        System.out.println(prefix + "  " + general);
    	    }
	    }
	}

} // ClassifierDefinitionMapping
