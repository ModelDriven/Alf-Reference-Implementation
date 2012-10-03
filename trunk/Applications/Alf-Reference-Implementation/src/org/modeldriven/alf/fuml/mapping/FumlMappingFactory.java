/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.mapping;

import org.modeldriven.alf.execution.fuml.OpaqueBehaviorExecution;
import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingFactory;
import org.modeldriven.alf.syntax.units.ActivityDefinition;
import org.modeldriven.alf.uml.OpaqueBehavior;

public class FumlMappingFactory extends MappingFactory {

    @Override
    public Mapping instantiateMapping(Object source) {
        if (source == null) {
            return new ErrorMapping(source, "Null source.");
        } else {
            String className = source.getClass().getName().
            replace(".syntax", ".fuml.mapping") + "Mapping";

            try {
                return (Mapping)Class.forName(className).newInstance();
            } catch (Exception e) {
                return new ErrorMapping(source, "No mapping: " + className);
            }
        }
    }
    
    public OpaqueBehaviorExecution instantiatePrimitiveBehaviorPrototype(
            ActivityDefinition definition, 
            OpaqueBehavior behavior) {
        return null;
    }
    
}
