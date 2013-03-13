/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.papyrus.execution;

import org.modeldriven.alf.uml.OpaqueBehavior;

public class OpaqueBehaviorExecution implements
        org.modeldriven.alf.fuml.execution.OpaqueBehaviorExecution {
    
    protected org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution base = null;
    
    public OpaqueBehaviorExecution(org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution base) {
        this.base = base;
    }
    
    public org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution getBase() {
        return this.base;
    }

	@Override
	public void addType(OpaqueBehavior type) {
		this.getBase().types.add(((org.modeldriven.alf.eclipse.uml.OpaqueBehavior)type).getBase());
	}

}
