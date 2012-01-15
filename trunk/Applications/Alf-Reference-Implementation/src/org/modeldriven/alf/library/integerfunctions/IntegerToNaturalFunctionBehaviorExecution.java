/*
 * Copyright 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */



package org.modeldriven.alf.library.integerfunctions;

import org.modeldriven.fuml.library.LibraryFunctions;

import fUML.Debug;
import fUML.Semantics.Classes.Kernel.IntegerValue;
import fUML.Semantics.Classes.Kernel.StringValue;

public class IntegerToNaturalFunctionBehaviorExecution extends
        fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution {

    public void doBody(
            fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList inputParameters,
            fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList outputParameters) {

        StringValue sv = (StringValue) inputParameters.getValue(0).values.getValue(0);
    	String value = sv.value;
		Debug.println("[doBody] argument = " + value);

    	int resultInt;
    	try {
            int radix = 10;
            if (value.length() > 1 && value.charAt(0) == '0') {
                char radixChar = value.charAt(1);
                radix = 
                    radixChar == 'b' || radixChar == 'B'? 2: 
                    radixChar == 'x' || radixChar == 'X'? 16: 8;
                if (radix != 8) {
                    value = value.substring(2);
                }
            }
            resultInt = Integer.parseInt(value.replaceAll("_", ""), radix);
    	} catch (NumberFormatException e) {
    		// If the String does not specify an integer, simply return an empty values list
    		Debug.println("[doBody] string does not specify a natural: " + value);
    		LibraryFunctions.addEmptyValueListToOutputList(outputParameters);
    		return;
    	}
    	
    	IntegerValue result = new IntegerValue();
    	result.value = resultInt;

        Debug.println("[doBody] Integer ToInteger result = " + result.value);

		LibraryFunctions.addValueToOutputList(result, outputParameters);
    }
    
    public fUML.Semantics.Classes.Kernel.Value new_() {
        return new IntegerToNaturalFunctionBehaviorExecution();
    }   

}
