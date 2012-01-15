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
import fUML.Semantics.Classes.Kernel.BooleanValue;
import fUML.Semantics.Classes.Kernel.IntegerValue;

public class BitStringIsSetFunctionBehaviorExecution extends
        fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution {

    public void doBody(
            fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList inputParameters,
            fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList outputParameters) {

        Integer b = ((IntegerValue)inputParameters.getValue(0).values.getValue(0)).value;
        Integer n = ((IntegerValue)inputParameters.getValue(1).values.getValue(0)).value;        
		Debug.println("[doBody] arguments = " + b + ", " + n);
    	
    	BooleanValue result = new BooleanValue();
    	result.value = ((b >> n) & 1) == 1;

        Debug.println("[doBody] BitString IsSet result = " + result.value);

		LibraryFunctions.addValueToOutputList(result, outputParameters);
    }
    
    public fUML.Semantics.Classes.Kernel.Value new_() {
        return new BitStringIsSetFunctionBehaviorExecution();
    }   

}
