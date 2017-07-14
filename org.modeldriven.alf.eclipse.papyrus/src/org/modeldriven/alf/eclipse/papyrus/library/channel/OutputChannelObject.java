/*******************************************************************************
 * Copyright 2011, 2017 Data Access Technologies, Inc. (Model Driven Solutions)
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
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IBooleanValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IValue;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.BooleanValue;
import org.eclipse.uml2.uml.PrimitiveType;

public abstract class OutputChannelObject extends ChannelObject {

    public abstract void write(IValue iValue, Status errorStatus);
    public abstract boolean isFull();

    public void execute(OperationExecution execution) {
        String name = execution.getOperationName();

        if (name.equals("write")) {
        	Status status = new Status(this.locus, "OutputChannelObject");
            this.write(execution.getParameterValue("value").getValues().get(0), status);
            this.updateStatus(execution, status);
        } else if (name.equals("isFull")) {
            IBooleanValue isFullValue = new BooleanValue();
            isFullValue.setValue(this.isFull());
            isFullValue.setType((PrimitiveType) this.locus.getFactory().getBuiltInType("Boolean"));
            execution.setReturnParameterValue(isFullValue);
        } else {
            super.execute(execution);
        }

    }

} // OutputChannelObject
