/*******************************************************************************
 * Copyright 2011, 2017 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.papyrus.execution;

import org.modeldriven.alf.uml.Class_;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IObject_;
import org.eclipse.papyrus.moka.fuml.control.queue.ExecutionController;
import org.eclipse.papyrus.moka.fuml.control.queue.ExecutionLoop;

public class Object_ implements org.modeldriven.alf.fuml.execution.Object_ {
    
    private IObject_ base = null;
    
    public Object_(IObject_ base) {
        this.base = base;
    }
    
    public IObject_ getBase() {
        return this.base;
    }

    @Override
    public void startBehavior(Class_ classifier) {
		RootExecution rootExecution = new RootExecution(
				((org.modeldriven.alf.eclipse.uml.Class_)classifier).getBase(), 
				this.getBase().getLocus(), this.getBase());
		ExecutionController.getInstance().setExecutionLoop(new ExecutionLoop());
		ExecutionController.getInstance().start(rootExecution);
    }
    
    public String toString() {
        return this.getBase().toString();
    }

}
