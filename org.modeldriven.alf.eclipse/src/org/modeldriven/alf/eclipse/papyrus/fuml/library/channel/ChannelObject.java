/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.eclipse.papyrus.fuml.library.channel;

import org.modeldriven.alf.eclipse.papyrus.fuml.library.common.Status;
import org.modeldriven.alf.eclipse.papyrus.fuml.library.libraryclass.ImplementationObject;
import org.modeldriven.alf.eclipse.papyrus.fuml.library.libraryclass.OperationExecution;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.BooleanValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.StringValue;

public abstract class ChannelObject extends ImplementationObject {
	
	// NOTE: Initial status is not set here because this.locus will not be
	// set when the object is first created.
	protected Status status = null;

    public abstract String getName();
    public abstract void open(Status errorStatus);
    public abstract void close(Status errorStatus);
    public abstract boolean isOpen();
    
    public Status getStatus() {
    	if (this.status == null) {
    		this.status = new Status(this.locus, "ChannelObject");
    	}
    	return this.status;
    }
    
    public void updateStatus(OperationExecution execution, Status status) {
        if (!status.isNormal()) {
        	execution.setParameterValue("errorStatus", status.getValue());
        }
        this.status = status;
    }

    public void execute(OperationExecution execution) {
        String name = execution.getOperationName();
        
        Status status = new Status(this.locus, "ChannelObject");

        if (name.equals("getName")) {
            StringValue nameValue = new StringValue();
            nameValue.value = this.getName();
            nameValue.type = this.locus.factory.getBuiltInType("String");
            execution.setReturnParameterValue(nameValue);
        } else if (name.equals("open")) {
            this.open(status);
            this.updateStatus(execution, status);
        } else if (name.equals("close")) {
            this.close(status);
            this.updateStatus(execution, status);
        } else if (name.equals("isOpen")) {
            BooleanValue isOpenValue = new BooleanValue();
            isOpenValue.value = this.isOpen();
            isOpenValue.type = this.locus.factory.getBuiltInType("Boolean");
            execution.setReturnParameterValue(isOpenValue);
        } else if (name.equals("getStatus")) {
        	Status result = this.getStatus();        	
            execution.setReturnParameterValue(result.getValue());
        }        
    }

} // ChannelObject
