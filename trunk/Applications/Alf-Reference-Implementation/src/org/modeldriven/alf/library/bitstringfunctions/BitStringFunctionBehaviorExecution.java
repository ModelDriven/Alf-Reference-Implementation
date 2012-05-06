/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.library.bitstringfunctions;

import org.modeldriven.fuml.library.LibraryFunctions;

import UMLPrimitiveTypes.intList;
import fUML.Debug;
import fUML.Semantics.Classes.Kernel.IntegerValue;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;

public abstract class BitStringFunctionBehaviorExecution extends
        OpaqueBehaviorExecution {

    @Override
    public void doBody(
            ParameterValueList inputParameters,
            ParameterValueList outputParameters) {
        // Extract bit string arguments (represented as integers) and perform a 
        // bit string function on them.

        intList integerArguments = new intList();

        for (int i = 0; i < inputParameters.size(); i++) {
            int value = ((IntegerValue) (inputParameters.getValue(i)).values.getValue(0)).value;
            Debug.println("[doBody] argument = " + value);
            integerArguments.addValue(value);
        }

        int value = this.doBitStringFunction(integerArguments);
        
        IntegerValue result = new IntegerValue();
        result.value = value;
        result.type = this.locus.factory.getBuiltInType("BitString");
        LibraryFunctions.addValueToOutputList(result, outputParameters);
    }

    public abstract int doBitStringFunction(intList arguments);
}
