/*******************************************************************************
 * Copyright 2011, 2017 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.eclipse.moka.library.channel;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IBooleanValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IIntegerValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IPrimitiveValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IStringValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IUnlimitedNaturalValue;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.BooleanValue;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.IntegerValue;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.StringValue;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.UnlimitedNaturalValue;
import org.eclipse.uml2.uml.PrimitiveType;
import org.modeldriven.alf.eclipse.moka.library.common.Status;
import org.modeldriven.alf.eclipse.moka.library.libraryclass.OperationExecution;

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
        IPrimitiveValue resultValue = null;
        
        if (name.equals("readCharacter")) {
            String result = this.readCharacter(status);
            if (result != null) {
	            resultValue = new StringValue();
	            resultValue.setType((PrimitiveType) this.locus.getFactory().getBuiltInType("String"));
	            ((IStringValue)resultValue).setValue(result);
            }
            this.updateStatus(execution, status);
        } else if (name.equals("peekCharacter")) {
            String result = this.peekCharacter(status);
            if (result != null) {
	            resultValue = new StringValue();
	            resultValue.setType((PrimitiveType) this.locus.getFactory().getBuiltInType("String"));
	            ((IStringValue)resultValue).setValue(result);
            }
            this.updateStatus(execution, status);
        } else if (name.equals("readLine")) {
            String result = this.readLine(status);
            if (result != null) {
	            resultValue = new StringValue();
	            resultValue.setType((PrimitiveType) this.locus.getFactory().getBuiltInType("String"));
	            ((IStringValue)resultValue).setValue(result);
            }
            this.updateStatus(execution, status);
        } else if (name.equals("readInteger")) {
            Integer result = this.readInteger(status);
            if (result != null) {
	            resultValue = new IntegerValue();
	            resultValue.setType((PrimitiveType) this.locus.getFactory().getBuiltInType("Integer"));
	            ((IIntegerValue)resultValue).setValue(result);
            }
            this.updateStatus(execution, status);
        } else if (name.equals("readBoolean")) {
            Boolean result = this.readBoolean(status);
            if (result != null) {
	            resultValue = new BooleanValue();
	            resultValue.setType((PrimitiveType) this.locus.getFactory().getBuiltInType("Boolean"));
	            ((IBooleanValue)resultValue).setValue(result);
            }
            this.updateStatus(execution, status);
        } else if (name.equals("readUnlimitedNatural")) {
            Integer result = this.readUnlimitedNatural(status);
            if (result != null) {
	            resultValue = new UnlimitedNaturalValue();
	            resultValue.setType((PrimitiveType) this.locus.getFactory().getBuiltInType("UnlimitedNatural"));
	            ((IUnlimitedNaturalValue)resultValue).setValue(result);
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
