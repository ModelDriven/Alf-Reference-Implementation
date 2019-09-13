/*******************************************************************************
 * Copyright 2011, 2017 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.eclipse.moka.library.channel;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IBooleanValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IValue;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.BooleanValue;
import org.eclipse.uml2.uml.PrimitiveType;
import org.modeldriven.alf.eclipse.moka.library.common.Status;
import org.modeldriven.alf.eclipse.moka.library.libraryclass.OperationExecution;

public abstract class InputChannelObject extends ChannelObject {

    public abstract boolean hasMore();
    public abstract IValue read(Status errorStatus);
    public abstract IValue peek(Status errorStatus);

    public void execute(OperationExecution execution) {
        String name = execution.getOperationName();
        
        Status status = new Status(this.locus, "InputChannel");

        if (name.equals("hasMore")) {
            IBooleanValue hasMoreValue = new BooleanValue();
            hasMoreValue.setValue(this.hasMore());
            hasMoreValue.setType((PrimitiveType) this.locus.getFactory().getBuiltInType("Boolean"));
            execution.setReturnParameterValue(hasMoreValue);
        } else if (name.equals("read")) {
        	IValue value = this.read(status);
        	if (value != null) {
        		execution.setParameterValue("value", value);
        	}
            this.updateStatus(execution, status);
        } else if (name.equals("peek")) {
        	IValue value = this.peek(status);
        	if (value != null) {
        		execution.setParameterValue("value", value);
        	}
            this.updateStatus(execution, status);
        } else {
            super.execute(execution);
        }
    }

} // InputChannelObject
