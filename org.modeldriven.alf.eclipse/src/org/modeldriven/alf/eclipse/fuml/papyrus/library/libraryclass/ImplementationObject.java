/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.eclipse.fuml.papyrus.library.libraryclass;

import org.modeldriven.alf.eclipse.fuml.papyrus.library.libraryclass.OperationExecution;

public abstract class ImplementationObject 
    extends org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Object_ {

    public abstract void execute(OperationExecution execution);

    @Override
    public org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.Execution dispatch(
            org.eclipse.uml2.uml.Operation operation) {
        OperationExecution execution = new OperationExecution();
        this.locus.add(execution);
        execution.set(this, operation);
        return execution;
    }

} // ImplementationObject
