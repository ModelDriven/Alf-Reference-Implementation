/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.eclipse.papyrus.library.channel;

import org.modeldriven.alf.eclipse.papyrus.library.channel.OutputChannelObject;
import org.modeldriven.alf.eclipse.papyrus.library.common.Status;
import org.modeldriven.alf.eclipse.papyrus.library.libraryclass.OperationExecution;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.BooleanValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IntegerValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.StringValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.UnlimitedNaturalValue;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue;

public abstract class TextOutputChannelObject extends OutputChannelObject {

    public abstract void writeString(String value, Status errorStatus);
    public abstract void writeNewLine(Status errorStatus);

    public void writeLine(String value, Status errorStatus) {
        this.writeString(value, errorStatus);
        this.writeNewLine(errorStatus);
    }

    public void writeInteger(int value, Status errorStatus) {
        this.writeString(Integer.toString(value), errorStatus);
    }

    public void writeBoolean(boolean value, Status errorStatus) {
        this.writeString(Boolean.toString(value), errorStatus);
    }

    public void writeUnlimitedNatural(Integer value, Status errorStatus) {
        if (value < 0) {
            this.writeString("*", errorStatus);
        } else {
            this.writeString(Integer.toString(value), errorStatus);
        }

    }

    public void execute(OperationExecution execution) {
        String name = execution.getOperationName();
        // Debug.println("[execute] operation = " + name);

        ParameterValue parameterValue = execution.getParameterValue("value");
        // if ((parameterValue != null) && (parameterValue.values.size() > 0)) {
        // Debug.println("[execute] argument = " +
        // parameterValue.values.getValue(0));
        // }
        
        Status status = new Status(this.locus, "TextOutputChannel");

        if (name.equals("writeNewLine")) {
            this.writeNewLine(status);            
            this.updateStatus(execution, status);
        } else if (name.equals("writeString")) {
            this.writeString(((StringValue) (parameterValue.values.get(0))).value, status);
            this.updateStatus(execution, status);
        } else if (name.equals("writeLine")) {
            this.writeLine(((StringValue) (parameterValue.values.get(0))).value, status);
            this.updateStatus(execution, status);
        } else if (name.equals("writeInteger")) {
            this.writeInteger(((IntegerValue) (parameterValue.values.get(0))).value, status);
            this.updateStatus(execution, status);
        } else if (name.equals("writeBoolean")) {
            this.writeBoolean(((BooleanValue) (parameterValue.values.get(0))).value, status);
            this.updateStatus(execution, status);
        } else if (name.equals("writeUnlimitedNatural")) {
            this.writeUnlimitedNatural(((UnlimitedNaturalValue) (parameterValue.values
                            .get(0))).value, status);
            this.updateStatus(execution, status);
        } else {
            super.execute(execution);
        }
    }

} // TextOutputChannelObject
