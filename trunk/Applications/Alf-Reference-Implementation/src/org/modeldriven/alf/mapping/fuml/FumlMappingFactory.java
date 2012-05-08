/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.mapping.fuml;

import java.util.Collection;

import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingFactory;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.statements.QualifiedNameList;
import org.modeldriven.alf.syntax.units.ActivityDefinition;
import org.modeldriven.alf.syntax.units.StereotypeAnnotation;
import org.modeldriven.fuml.library.LibraryFunctions;

import fUML.Debug;
import fUML.Semantics.Classes.Kernel.Value;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.OpaqueBehavior;

public class FumlMappingFactory extends MappingFactory {

    @Override
    public Mapping instantiateMapping(Object source) {
        if (source == null) {
            return new ErrorMapping(source, "Null source.");
        } else {
            String className = source.getClass().getName().
            replace(".syntax", ".mapping.fuml") + "Mapping";

            try {
                return (Mapping)Class.forName(className).newInstance();
            } catch (Exception e) {
                return new ErrorMapping(source, "No mapping: " + className);
            }
        }
    }
    
    private class UnimplementedBehaviorExecution extends OpaqueBehaviorExecution {
        
        @Override
        public void doBody(ParameterValueList inputParameters,
                ParameterValueList outputParameters) {
            /*
            throw new Error("Primitive behavior" + 
                    (this.types.size() == 0? "": " " + this.types.get(0).name) + 
                    " not implemented.") ;
            */
            Debug.println("[error] Primitive behavior" + 
                    (this.types.size() == 0? "": " " + this.types.get(0).name) + 
                    " not implemented.");
            LibraryFunctions.addEmptyValueListToOutputList(outputParameters);
        }

        @Override
        public Value new_() {
            return new UnimplementedBehaviorExecution();
        }
        
    }

    public OpaqueBehaviorExecution instantiatePrimitiveBehaviorPrototype(
            ActivityDefinition definition, 
            final OpaqueBehavior behavior) {
        OpaqueBehaviorExecution execution = new UnimplementedBehaviorExecution();
        for (StereotypeAnnotation annotation: definition.getAnnotation()) {
            if (annotation.getStereotypeName().getPathName().equals("primitive")) {
                QualifiedNameList nameList = annotation.getNames();
                if (nameList != null) {
                    Collection<QualifiedName> names = nameList.getName();
                    if (!names.isEmpty()) {
                        String name = 
                            ((QualifiedName)names.toArray()[0]).getPathName();
                        try {
                            execution = 
                                (OpaqueBehaviorExecution)Class.forName
                                    (classNameFor(definition, name)).
                                        newInstance();
                        } catch (Exception e) {
                        }
                        break;
                    }
                }
            }
        }
        execution.types.addValue(behavior);
        return execution;
    }
    
    private static String classNameFor(ActivityDefinition definition, String name) {
        QualifiedName definitionName = definition.getImpl().getQualifiedName();
        String rootName = definitionName.getNameBinding().get(0).getName();
        return "org.modeldriven." + 
            (rootName.equals("FoundationalModelLibrary")? "fuml": "alf") +
            ".library." + 
            definition.getNamespace().getName().toLowerCase() + "." + name;
    }

}
