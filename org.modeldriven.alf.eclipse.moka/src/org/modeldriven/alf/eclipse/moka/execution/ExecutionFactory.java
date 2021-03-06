/*******************************************************************************
 * Copyright 2011, 2017 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.moka.execution;

import java.util.ArrayList;
import java.util.List;

import org.modeldriven.alf.fuml.execution.OpaqueBehaviorExecution;
import org.modeldriven.alf.uml.PrimitiveType;

public class ExecutionFactory implements org.modeldriven.alf.fuml.execution.ExecutionFactory {
    
    private org.eclipse.papyrus.moka.fuml.Semantics.impl.Loci.LociL1.ExecutionFactory base = null;
    
    public ExecutionFactory() {
        this(new org.eclipse.papyrus.moka.fuml.Semantics.impl.Loci.LociL3.ExecutionFactoryL3());
    }

    public ExecutionFactory(org.eclipse.papyrus.moka.fuml.Semantics.impl.Loci.LociL1.ExecutionFactory factory) {
        this.base = factory;
    }
    
    public org.eclipse.papyrus.moka.fuml.Semantics.impl.Loci.LociL1.ExecutionFactory getBase() {
        return this.base;
    }

    @Override
    public List<PrimitiveType> getBuiltInTypes() {
        List<PrimitiveType> builtInTypes = new ArrayList<PrimitiveType>();
        for (org.eclipse.uml2.uml.Type type: this.getBase().builtInTypes) {
            builtInTypes.add(new org.modeldriven.alf.eclipse.uml.PrimitiveType(
            		(org.eclipse.uml2.uml.PrimitiveType)type));
        }
        return builtInTypes;
    }

    @Override
    public void addBuiltInType(PrimitiveType type) {
        this.getBase().addBuiltInType(
                ((org.modeldriven.alf.eclipse.uml.PrimitiveType)type).getBase());
    }

    @Override
    public void addPrimitiveBehaviorPrototype(OpaqueBehaviorExecution execution) {
        this.getBase().addPrimitiveBehaviorPrototype(
                ((org.modeldriven.alf.eclipse.moka.execution.OpaqueBehaviorExecution)execution).getBase());
    }
    
}
