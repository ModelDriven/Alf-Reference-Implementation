
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.units;

import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.fuml.mapping.units.ClassDefinitionMapping;
import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;

import org.modeldriven.alf.syntax.units.ActiveClassDefinition;
import org.modeldriven.alf.syntax.units.ActivityDefinition;

import org.modeldriven.alf.uml.*;

public class ActiveClassDefinitionMapping extends ClassDefinitionMapping {
    
    /**
     * 1. An active class is mapped like a passive class, except an active class
     * has isActive=true and the following additional rules for mapping the
     * classifier behavior and receptions.
     * 
     * 2. If the behavior clause for an active class is a name, then the
     * classifier behavior for the class is the named activity. If the behavior
     * clause is a block, then the classifier behavior is an activity with the
     * activity nodes and edges mapped from the block.
     * 
     * 3. An active feature definition maps to an owned reception of the class.
     * An active feature stub declaration is mapped according to the associated
     * subunit definition.
     */
    
    // Subunits are handled by NamespaceDefinitionMapping.
    
    @Override
    public Classifier mapClassifier() {
        Classifier classifier = super.mapClassifier();
        ((Class_)classifier).setIsActive(true);
        return classifier;
    }

    public void mapTo(Classifier classifier) throws MappingError {
        Class_ class_ = (Class_)classifier;
        class_.setIsActive(true);

        ActiveClassDefinition definition = this.getActiveClassDefinition();
        ActivityDefinition classifierBehavior = definition.getClassifierBehavior();
        
        if (classifierBehavior != null) {
            FumlMapping mapping = this.fumlMap(classifierBehavior);
            
            if (!(mapping instanceof ActivityDefinitionMapping)) {
                this.throwError("Error mapping classifier behavior: " + mapping);
            } else {
                Behavior behavior = ((ActivityDefinitionMapping)mapping).getBehavior();
    
                // Give the classifier behavior a name to aid in execution tracing.
                if (behavior.getName() == null || behavior.getName().equals("")) {
                    behavior.setName(makeDistinguishableName(
                            definition, definition.getName() + "$classifierBehavior"));
                }

                class_.addOwnedBehavior(behavior);
                class_.setClassifierBehavior(behavior);
                
                this.otherElements.addAll(mapping.getModelElements());
                this.otherElements.remove(behavior);
            }
        }
        
        // NOTE: The classifier behavior (if any) is mapped first so that it can
        // then be skipped by addMemberTo, so it is not also added as a
        // nested classifier.
        super.mapTo(classifier);
    }
    
    @Override
    public void addMemberTo(Element element, NamedElement namespace) throws MappingError {
        Class_ class_ = (Class_)namespace;
        
        if (element instanceof Reception) {
          class_.addOwnedReception((Reception)element);
        } else if (!element.equals(class_.getClassifierBehavior())){
          super.addMemberTo(element, namespace);
        }
    }
    
    public ActiveClassDefinition getActiveClassDefinition() {
		return (ActiveClassDefinition) this.getSource();
	}
    
    @Override 
    public void print(String prefix) {
        super.print(prefix);
        ActiveClassDefinition definition = this.getActiveClassDefinition();
        ActivityDefinition classifierBehavior = definition.getClassifierBehavior();
        if (classifierBehavior != null) {
            Mapping mapping = this.fumlMap(classifierBehavior);
            System.out.println(prefix + " classifierBehavior:");
            mapping.printChild(prefix);
        }
    }

} // ActiveClassDefinitionMapping
