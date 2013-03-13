/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.eclipse.papyrus.library.channel;

import org.modeldriven.alf.eclipse.papyrus.library.channel.ChannelObject;
import org.modeldriven.alf.eclipse.papyrus.library.common.Status;
import org.modeldriven.alf.eclipse.papyrus.library.libraryclass.OperationExecution;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.BooleanValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Value;

public abstract class InputChannelObject extends ChannelObject {

    public abstract boolean hasMore();
    public abstract Value read(Status errorStatus);
    public abstract Value peek(Status errorStatus);

    public void execute(OperationExecution execution) {
        String name = execution.getOperationName();
        
        Status status = new Status(this.locus, "InputChannel");

        if (name.equals("hasMore")) {
            BooleanValue hasMoreValue = new BooleanValue();
            hasMoreValue.value = this.hasMore();
            hasMoreValue.type = this.locus.factory.getBuiltInType("Boolean");
            execution.setReturnParameterValue(hasMoreValue);
        } else if (name.equals("read")) {
        	Value value = this.read(status);
        	if (value != null) {
        		execution.setParameterValue("value", value);
        	}
            this.updateStatus(execution, status);
        } else if (name.equals("peek")) {
        	Value value = this.peek(status);
        	if (value != null) {
        		execution.setParameterValue("value", value);
        	}
            this.updateStatus(execution, status);
        } else {
            super.execute(execution);
        }
    }

} // InputChannelObject
