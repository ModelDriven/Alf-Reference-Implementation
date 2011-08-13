
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

import org.modeldriven.alf.syntax.units.ClassDefinition;

import fUML.Syntax.Classes.Kernel.Class_;
import fUML.Syntax.Classes.Kernel.Classifier;
import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.Classes.Kernel.NamedElement;
import fUML.Syntax.Classes.Kernel.Operation;
import fUML.Syntax.Classes.Kernel.Property;

public class ClassDefinitionMapping extends ClassifierDefinitionMapping {

    @Override
	public Classifier mapClassifier() {
        return new Class_();
	}
    
    @Override
    public void mapTo(Classifier classifier) throws MappingError {
        super.mapTo(classifier);
        // TODO: Implement default constructor mapping.
    }

    @Override
    public void addMemberTo(Element element, NamedElement namespace) throws MappingError {
        Class_ class_ = (Class_)namespace;

        if (element instanceof Property) {
          class_.addOwnedAttribute((Property)element);
        } else if (element instanceof Operation) {
          class_.addOwnedOperation((Operation)element);
        } else if (element instanceof Classifier) {
          class_.addNestedClassifier((Classifier)element);
        } else {
          this.throwError("Member not legal for a class: " + element);
        }
    }

	public ClassDefinition getClassDefinition() {
		return (ClassDefinition) this.getSource();
	}
	
	@Override
	public String toString() {
	    Class_  class_ = (Class_)this.getElement();
	    return super.toString() + (class_ == null? "": " isActive:" + class_.isActive);
	}

} // ClassDefinitionMapping
