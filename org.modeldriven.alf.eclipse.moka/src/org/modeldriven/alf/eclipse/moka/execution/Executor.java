/*******************************************************************************
 * Copyright 2011, 2017 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.moka.execution;

import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.IExecutor;
import org.eclipse.papyrus.moka.fuml.control.queue.ExecutionController;
import org.eclipse.papyrus.moka.fuml.control.queue.ExecutionLoop;
import org.modeldriven.alf.fuml.execution.Object_;
import org.modeldriven.alf.uml.Behavior;

public class Executor implements org.modeldriven.alf.fuml.execution.Executor {
    
    private IExecutor base = null;
    
    public Executor() {
        this(new org.eclipse.papyrus.moka.fuml.Semantics.impl.Loci.LociL1.Executor());
    }
    
    public Executor(IExecutor base) {
        this.base = base;
    }
    
    public IExecutor getBase() {
        return this.base;
    }

    @Override
    public void execute(Behavior behavior, Object_ context) {
		RootExecution rootExecution = new RootExecution(
				((org.modeldriven.alf.eclipse.uml.Behavior)behavior).getBase(), 
				this.getBase().getLocus(),
				context == null? null: 
					((org.modeldriven.alf.eclipse.moka.execution.Object_)context).getBase());
		ExecutionController.getInstance().setExecutionLoop(new ExecutionLoop());
		ExecutionController.getInstance().start(rootExecution);
    }

}
