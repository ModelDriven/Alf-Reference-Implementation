/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.eclipse.papyrus.fuml.library.channel;

import org.modeldriven.alf.eclipse.papyrus.fuml.library.channel.InputChannelObject;
import org.modeldriven.alf.eclipse.papyrus.fuml.library.common.Status;
import org.modeldriven.alf.eclipse.papyrus.fuml.library.libraryclass.OperationExecution;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.BooleanValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IntegerValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.PrimitiveValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.StringValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.UnlimitedNaturalValue;

public abstract class TextInputChannelObject extends InputChannelObject {

	public abstract String readCharacter(Status errorStatus);
	public abstract String peekCharacter(Status errorStatus);
	public abstract String readLine(Status errorStatus);
	public abstract Integer readInteger(Status errorStatus);
	public abstract Boolean readBoolean(Status errorStatus);
	public abstract Integer readUnlimitedNatural(Status errorStatus);

    public void execute(OperationExecution execution) {
        String name = execution.getOperationName();

        Status status = new Status(this.locus, "TextInputChannel");
        PrimitiveValue resultValue = null;
        
        if (name.equals("readCharacter")) {
            String result = this.readCharacter(status);
            if (result != null) {
	            resultValue = new StringValue();
	            resultValue.type = this.locus.factory.getBuiltInType("String");
	            ((StringValue)resultValue).value = result;
            }
            this.updateStatus(execution, status);
        } else if (name.equals("peekCharacter")) {
            String result = this.peekCharacter(status);
            if (result != null) {
	            resultValue = new StringValue();
	            resultValue.type = this.locus.factory.getBuiltInType("String");
	            ((StringValue)resultValue).value = result;
            }
            this.updateStatus(execution, status);
        } else if (name.equals("readLine")) {
            String result = this.readLine(status);
            if (result != null) {
	            resultValue = new StringValue();
	            resultValue.type = this.locus.factory.getBuiltInType("String");
	            ((StringValue)resultValue).value = result;
            }
            this.updateStatus(execution, status);
        } else if (name.equals("readInteger")) {
            Integer result = this.readInteger(status);
            if (result != null) {
	            resultValue = new IntegerValue();
	            resultValue.type = this.locus.factory.getBuiltInType("Integer");
	            ((IntegerValue)resultValue).value = result;
            }
            this.updateStatus(execution, status);
        } else if (name.equals("readBoolean")) {
            Boolean result = this.readBoolean(status);
            if (result != null) {
	            resultValue = new BooleanValue();
	            resultValue.type = this.locus.factory.getBuiltInType("Boolean");
	            ((BooleanValue)resultValue).value = result;
            }
            this.updateStatus(execution, status);
        } else if (name.equals("readUnlimitedNatural")) {
            Integer result = this.readUnlimitedNatural(status);
            if (result != null) {
	            resultValue = new UnlimitedNaturalValue();
	            resultValue.type = this.locus.factory.getBuiltInType("UnlimitedNatural");
	            ((UnlimitedNaturalValue)resultValue).value = result;
            }
            this.updateStatus(execution, status);
        } else {
            super.execute(execution);
        }
        
        if (resultValue != null) {
        	execution.setReturnParameterValue(resultValue);
        }
    }

} // TextOutputChannelObject
