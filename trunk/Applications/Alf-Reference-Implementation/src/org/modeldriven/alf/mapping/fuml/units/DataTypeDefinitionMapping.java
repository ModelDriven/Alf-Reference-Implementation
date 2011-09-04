
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

import org.modeldriven.alf.syntax.units.DataTypeDefinition;

import fUML.Semantics.Loci.LociL1.ExecutionFactory;
import fUML.Syntax.Classes.Kernel.Classifier;
import fUML.Syntax.Classes.Kernel.DataType;
import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.Classes.Kernel.NamedElement;
import fUML.Syntax.Classes.Kernel.PrimitiveType;
import fUML.Syntax.Classes.Kernel.Property;

public class DataTypeDefinitionMapping extends ClassifierDefinitionMapping {

    @Override
    public Classifier mapClassifier() {
        if (this.getDataTypeDefinition().getIsPrimitive()) {
            return new PrimitiveType();            
        } else {
            return new DataType();
        }   
    }
    
    @Override
    public void mapTo(Classifier classifier) throws MappingError {
        super.mapTo(classifier);
        if (classifier instanceof PrimitiveType) {
            DataTypeDefinition dataTypeDefinition = this.getDataTypeDefinition();
            ExecutionFactory executionFactory = getExecutionFactory();
            String name = classifier.name;
            if (name == null) {
                this.throwError("Unnamed primitive type: " + dataTypeDefinition);
            }
            for (PrimitiveType builtInType: executionFactory.builtInTypes) {
                if (builtInType.name != null && builtInType.name.equals(name)) {
                    this.throwError("Duplicate primitive type: " + dataTypeDefinition);
                }
            }
            executionFactory.addBuiltInType((PrimitiveType)classifier);           
        }
    }

    @Override
    public void addMemberTo(Element element, NamedElement namespace) throws MappingError {
        if (element instanceof Property) {
            ((DataType)namespace).addOwnedAttribute((Property)element);
          } else {
            this.throwError("Member not allowed for a data type: " + element);
          }
    }

	public DataTypeDefinition getDataTypeDefinition() {
		return (DataTypeDefinition) this.getSource();
	}

} // DataTypeDefinitionMapping