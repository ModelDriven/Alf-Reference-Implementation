/*
 * Copyright 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.library.bitstringfunctions;

import org.modeldriven.fuml.library.LibraryFunctions;

import UMLPrimitiveTypes.intList;
import fUML.Debug;
import fUML.Semantics.Classes.Kernel.IntegerValue;

public abstract class BitStringFunctionBehaviorExecution extends
        fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution {

    public void doBody(
            fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList inputParameters,
            fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList outputParameters) {
        // Extract bit string arguments (represented as integers) and perform an 
        // integer function on them.

        intList integerArguments = new intList();

        for (int i = 0; i < inputParameters.size(); i++) {
            int value = ((IntegerValue) (inputParameters.getValue(i)).values.getValue(0)).value;
            Debug.println("[doBody] argument = " + value);
            integerArguments.addValue(value);
        }

        // Call the method specific to the integer function
        IntegerValue value = this.doBitStringFunction(integerArguments);
        
        if (value == null) {
            // if null, then there is an invalid input argument, so return
            // an empty list
            LibraryFunctions.addEmptyValueListToOutputList(outputParameters);     
        } else {
            // Add output to the outputParameters list
            value.type = this.locus.factory.getBuiltInType("BitString");
            LibraryFunctions.addValueToOutputList(value, outputParameters);
        }             
    }

    public abstract IntegerValue doBitStringFunction(UMLPrimitiveTypes.intList arguments);
}
