/*
 * Copyright 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */



package org.modeldriven.alf.library.bitstringfunctions;

import org.modeldriven.fuml.library.LibraryFunctions;

import fUML.Debug;
import fUML.Semantics.Classes.Kernel.IntegerValue;

public class BitStringToIntegerFunctionBehaviorExecution extends
        fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution {

    public void doBody(
            fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList inputParameters,
            fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList outputParameters) {

        Integer value = ((IntegerValue)inputParameters.getValue(0).values.getValue(0)).value;
		Debug.println("[doBody] argument = " + value);
    	
    	IntegerValue result = new IntegerValue();
    	result.value = value;

        Debug.println("[doBody] BitString ToInteger result = " + result.value);

		LibraryFunctions.addValueToOutputList(result, outputParameters);
    }
    
    public fUML.Semantics.Classes.Kernel.Value new_() {
        return new BitStringToIntegerFunctionBehaviorExecution();
    }   

}
