/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.eclipse.papyrus.fuml.library.channel;

import org.modeldriven.alf.eclipse.papyrus.fuml.library.channel.ChannelObject;
import org.modeldriven.alf.eclipse.papyrus.fuml.library.common.Status;
import org.modeldriven.alf.eclipse.papyrus.fuml.library.libraryclass.OperationExecution;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.BooleanValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Value;

public abstract class OutputChannelObject extends ChannelObject {

    public abstract void write(Value value, Status errorStatus);
    public abstract boolean isFull();

    public void execute(OperationExecution execution) {
        String name = execution.getOperationName();

        if (name.equals("write")) {
        	Status status = new Status(this.locus, "OutputChannelObject");
            this.write(execution.getParameterValue("value").values.get(0), status);
            this.updateStatus(execution, status);
        } else if (name.equals("isFull")) {
            BooleanValue isFullValue = new BooleanValue();
            isFullValue.value = this.isFull();
            isFullValue.type = this.locus.factory.getBuiltInType("Boolean");
            execution.setReturnParameterValue(isFullValue);
        } else {
            super.execute(execution);
        }

    }

} // OutputChannelObject
