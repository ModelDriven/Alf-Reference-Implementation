/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.papyrus.fuml.mapping;

import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.eclipse.papyrus.fuml.library.LibraryFunctions;
import org.modeldriven.alf.fuml.execution.OpaqueBehaviorExecution;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.statements.QualifiedNameList;
import org.modeldriven.alf.syntax.units.ActivityDefinition;
import org.modeldriven.alf.syntax.units.StereotypeAnnotation;
import org.modeldriven.alf.uml.OpaqueBehavior;

import org.eclipse.papyrus.moka.fuml.debug.Debug;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Value;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue;

public class FumlMappingFactory extends org.modeldriven.alf.fuml.mapping.FumlMappingFactory {

    @Override
    public OpaqueBehaviorExecution instantiatePrimitiveBehaviorPrototype(
            ActivityDefinition definition, 
            final OpaqueBehavior behavior) {
        org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution execution = 
                new UnimplementedBehaviorExecution();
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
                                (org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution)
                                Class.forName
                                    (classNameFor(definition, name)).
                                        newInstance();
                        } catch (Exception e) {
                        }
                        break;
                    }
                }
            }
        }
        execution.types.add(((org.modeldriven.alf.eclipse.uml.Behavior)behavior).getBase());
        return new org.modeldriven.alf.eclipse.papyrus.fuml.execution.OpaqueBehaviorExecution(execution);
    }
    
    private class UnimplementedBehaviorExecution 
        extends org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution {
        
        @Override
        public void doBody(List<ParameterValue> inputParameters,
                List<ParameterValue> outputParameters) {
            /*
            throw new Error("Primitive behavior" + 
                    (this.types.size() == 0? "": " " + this.types.get(0).name) + 
                    " not implemented.") ;
            */
            Debug.println("[error] Primitive behavior" + 
                    (this.types.size() == 0? "": " " + this.types.get(0).getName()) + 
                    " not implemented.");
            LibraryFunctions.addEmptyValueListToOutputList(outputParameters);
        }

        @Override
        public Value new_() {
            return new UnimplementedBehaviorExecution();
        }
        
    }

    private static String classNameFor(ActivityDefinition definition, String name) {
        return "org.modeldriven.alf.eclipse.papyrus.fuml.library." + 
            definition.getNamespace().getName().toLowerCase() + "." + name;
    }

}
