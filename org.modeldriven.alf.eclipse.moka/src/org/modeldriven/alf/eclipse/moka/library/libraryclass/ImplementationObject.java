/*******************************************************************************
 * Copyright 2011, 2017 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.eclipse.moka.library.libraryclass;

import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.IExecution;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.Object_;
import org.eclipse.uml2.uml.Operation;
import org.modeldriven.alf.eclipse.moka.library.libraryclass.OperationExecution;

public abstract class ImplementationObject extends Object_ {

    public abstract void execute(OperationExecution execution);

    @Override
    public IExecution dispatch(Operation operation) {
        OperationExecution execution = new OperationExecution();
        this.locus.add(execution);
        execution.set(this, operation);
        return execution;
    }

} // ImplementationObject
