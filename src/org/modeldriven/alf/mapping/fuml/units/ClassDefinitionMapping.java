/*
 * Copyright 2011-2012 Data Access Technologies, Inc. (Model Driven Solutions)
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

    /**
     * 1. A non-active class definition maps to a class with isActive=false.
     * 
     * Class Members
     * 
     * 2. A nested classifier definition maps to a classifier as specified for
     * the appropriate kind of classifier. If the nested classifier definition
     * is a stub declaration, then the stub declaration is mapped according to
     * the associated subunit definition. The resulting classifier is a nested
     * classifier of the class.
     * 
     * 3. A property definition maps to an owned attribute of the class.
     * 
     * 4. An operation definition maps to an owned operation of the class.
     * 
     * Default Constructors and Destructors
     * 
     * 5. If a class definition has no operation definitions that are
     * constructors, a public, concrete owned operation is added to the class
     * with the same name as the class and no parameters and the standard
     * «Create» stereotype applied. It has a corresponding method activity that
     * is a private owned behavior of the class with the default behavior.
     * Within this behavior, initializers for attributes of the class are mapped
     * as sequenced structured activity nodes containing the mappings of the
     * initializer expressions.
     * 
     * 6. If a class definition has no operation definitions that are
     * destructors, a public, concrete owned operation with the name “destroy”,
     * no parameters and the standard «Destroy» stereotype applied is added to
     * the class. It has a corresponding method activity that is a private owned
     * behavior of the class with the default behavior.
     */

    // For mapping of active class definitions, see
    // ActiveClassDefinitionMapping.
    // Subunits are handled by NamespaceDefinitionMapping.

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
        Class_ class_ = (Class_) namespace;

        if (element instanceof Property) {
            class_.addOwnedAttribute((Property) element);
        } else if (element instanceof Operation) {
            class_.addOwnedOperation((Operation) element);
        } else if (element instanceof Classifier) {
            class_.addNestedClassifier((Classifier) element);
        } else {
            this.throwError("Member not legal for a class: " + element);
        }
    }

    public ClassDefinition getClassDefinition() {
        return (ClassDefinition) this.getSource();
    }

    @Override
    public String toString() {
        Class_ class_ = (Class_) this.getElement();
        return super.toString() + 
                (class_ == null ? "" : " isActive:" + class_.isActive);
    }

} // ClassDefinitionMapping
