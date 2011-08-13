package org.modeldriven.alf.mapping.fuml;

import java.util.Collection;

import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingFactory;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.statements.QualifiedNameList;
import org.modeldriven.alf.syntax.units.ActivityDefinition;
import org.modeldriven.alf.syntax.units.StereotypeAnnotation;

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

    public OpaqueBehaviorExecution instantiatePrimitiveBehaviorPrototype(
            ActivityDefinition definition, 
            final OpaqueBehavior behavior) {
        OpaqueBehaviorExecution execution = new OpaqueBehaviorExecution() {
            
            @Override
            public void doBody(ParameterValueList inputParameters,
                    ParameterValueList outputParameters) {
                throw new Error("Primitive behavior " + behavior.name + 
                        " not implemented.") ;
            }

            @Override
            public Value new_() {
                return this;
            }
            
        };
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
        return "org.modeldriven.fuml.library." + 
            definition.getNamespace().getName().toLowerCase() + "." + name;
    }

}
