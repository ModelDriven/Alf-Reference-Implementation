/*******************************************************************************
 * Copyright 2011, 2017 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.moka.execution;

import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.IOpaqueBehaviorExecution;
import org.modeldriven.alf.uml.OpaqueBehavior;

public class OpaqueBehaviorExecution implements
        org.modeldriven.alf.fuml.execution.OpaqueBehaviorExecution {
    
    protected IOpaqueBehaviorExecution base = null;
    
    public OpaqueBehaviorExecution(IOpaqueBehaviorExecution execution) {
        this.base = execution;
    }
    
    public IOpaqueBehaviorExecution getBase() {
        return this.base;
    }

	@Override
	public void addType(OpaqueBehavior type) {
		this.getBase().addType(((org.modeldriven.alf.eclipse.uml.OpaqueBehavior)type).getBase());
	}

}
