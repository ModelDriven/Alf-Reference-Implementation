/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.impl.execution;

import java.util.ArrayList;
import java.util.List;

import org.modeldriven.alf.fuml.execution.OpaqueBehaviorExecution;
import org.modeldriven.alf.uml.PrimitiveType;

public class ExecutionFactory implements org.modeldriven.alf.fuml.execution.ExecutionFactory {
    
    private fUML.Semantics.Loci.LociL1.ExecutionFactory base = null;
    
    public ExecutionFactory() {
        this(new org.modeldriven.fuml.environment.ExecutionFactory());
    }

    public ExecutionFactory(fUML.Semantics.Loci.LociL1.ExecutionFactory factory) {
        this.base = factory;
    }
    
    public fUML.Semantics.Loci.LociL1.ExecutionFactory getBase() {
        return this.base;
    }

    @Override
    public List<PrimitiveType> getBuiltInTypes() {
        List<PrimitiveType> builtInTypes = new ArrayList<PrimitiveType>();
        for (fUML.Syntax.Classes.Kernel.PrimitiveType type: this.getBase().builtInTypes) {
            builtInTypes.add(new org.modeldriven.alf.fuml.impl.uml.PrimitiveType(type));
        }
        return builtInTypes;
    }

    @Override
    public void addBuiltInType(PrimitiveType type) {
        this.getBase().addBuiltInType(
                ((org.modeldriven.alf.fuml.impl.uml.PrimitiveType)type).getBase());
    }

    @Override
    public void addPrimitiveBehaviorPrototype(OpaqueBehaviorExecution execution) {
        this.getBase().addPrimitiveBehaviorPrototype(
                ((org.modeldriven.alf.fuml.impl.execution.OpaqueBehaviorExecution)execution).getBase());
    }
    
}
