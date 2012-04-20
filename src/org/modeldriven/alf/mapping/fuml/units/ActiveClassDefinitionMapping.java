
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.units;

import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.mapping.fuml.units.ClassDefinitionMapping;

import org.modeldriven.alf.syntax.units.ActiveClassDefinition;
import org.modeldriven.alf.syntax.units.ActivityDefinition;

import fUML.Syntax.Classes.Kernel.Class_;
import fUML.Syntax.Classes.Kernel.Classifier;
import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.Classes.Kernel.NamedElement;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior;
import fUML.Syntax.CommonBehaviors.Communications.Reception;

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
    
    // Stubs are handled in NamespaceDefinitionMapping.

    public void mapTo(Classifier classifier) throws MappingError {
        super.mapTo(classifier);

        Class_ class_ = (Class_)classifier;
        class_.setIsActive(true);

        ActiveClassDefinition definition = this.getActiveClassDefinition();
        ActiveClassDefinition base = 
            (ActiveClassDefinition)definition.getImpl().getBase();
        ActivityDefinition classifierBehavior = 
            (base == null? definition: base).getClassifierBehavior();
        
        if (classifierBehavior != null) {
            FumlMapping mapping = this.fumlMap(classifierBehavior);
            if (!(mapping instanceof ActivityDefinitionMapping)) {
                this.throwError("Error mapping classifier behavior: " + mapping);
            } else {
                Behavior behavior = ((ActivityDefinitionMapping)mapping).getBehavior();
    
                // Give the classifier behavior a name to aid in execution tracing.
                if (behavior.name == null || behavior.name.equals("")) {
                    behavior.setName(makeDistinguishableActivityName(
                            definition, class_.name + "$classifierBehavior"));
                }

                class_.addOwnedBehavior(behavior);
                class_.setClassifierBehavior(behavior);
            }
        }

    }
    
    @Override
    public void addMemberTo(Element element, NamedElement namespace) throws MappingError {
        Class_ class_ = (Class_)namespace;

        if (element instanceof Reception) {
          class_.addOwnedReception((Reception)element);
        } else {
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
