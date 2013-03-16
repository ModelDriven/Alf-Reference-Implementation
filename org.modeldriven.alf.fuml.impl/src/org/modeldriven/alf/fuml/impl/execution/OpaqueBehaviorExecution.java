/*******************************************************************************
 * Copyright 2011-2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.impl.execution;

public class OpaqueBehaviorExecution implements
        org.modeldriven.alf.fuml.execution.OpaqueBehaviorExecution {
    
    protected fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution base = null;
    
    public OpaqueBehaviorExecution(fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution base) {
        this.base = base;
    }
    
    public fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution getBase() {
        return this.base;
    }

    @Override
    public void addType(org.modeldriven.alf.uml.OpaqueBehavior type) {
        this.getBase().types.add(
                ((org.modeldriven.alf.fuml.impl.uml.OpaqueBehavior)type).getBase());
    }

}
