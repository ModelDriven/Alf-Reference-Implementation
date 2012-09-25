
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.mapping.fuml.units;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.units.ClassifierDefinitionMapping;

import org.modeldriven.alf.syntax.units.DataTypeDefinition;

import fUML.Semantics.Loci.LociL1.ExecutionFactory;
import org.modeldriven.alf.uml.Classifier;
import org.modeldriven.alf.uml.DataType;
import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.NamedElement;
import org.modeldriven.alf.uml.PrimitiveType;
import org.modeldriven.alf.uml.Property;

public class DataTypeDefinitionMapping extends ClassifierDefinitionMapping {
    
    /**
     * 1. A data type definition that is not primitive maps to a data type (that
     * is not an enumeration or a primitive type).
     * 
     * 2. A data type definition that is primitive maps to a primitive type.
     * This primitive type is registered as a built-in type with the execution
     * factory at the execution locus for the unit.
     * 
     * Data Type Members
     * 
     * 3. A property definition maps to an owned attribute of the data type.
     */
    
    // Visibility is handled in MemberMapping.

    @Override
    public Classifier mapClassifier() {
        if (this.getDataTypeDefinition().getIsPrimitive()) {
            return this.create(PrimitiveType.class);            
        } else {
            return this.create(DataType.class);
        }   
    }
    
    @Override
    public void mapTo(Classifier classifier) throws MappingError {
        super.mapTo(classifier);
        
        if (classifier instanceof PrimitiveType) {
            DataTypeDefinition dataTypeDefinition = this.getDataTypeDefinition();
            ExecutionFactory executionFactory = getExecutionFactory();
            String name = classifier.getName();
            if (name == null) {
                this.throwError("Unnamed primitive type: " + dataTypeDefinition);
            }
            for (fUML.Syntax.Classes.Kernel.PrimitiveType builtInType: executionFactory.builtInTypes) {
                if (builtInType.name != null && builtInType.name.equals(name)) {
                    this.throwError("Duplicate primitive type: " + dataTypeDefinition);
                }
            }
            executionFactory.addBuiltInType(((org.modeldriven.alf.uml.fumlri.PrimitiveType)classifier).getBase());           
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
