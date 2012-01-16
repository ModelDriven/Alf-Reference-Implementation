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
import fUML.Semantics.Classes.Kernel.Value;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;

public class BitStringIsSetFunctionBehaviorExecution extends
        OpaqueBehaviorExecution {

    @Override
    public void doBody(
            ParameterValueList inputParameters,
            ParameterValueList outputParameters) {

        int b = ((IntegerValue)inputParameters.getValue(0).values.getValue(0)).value;
        int n = ((IntegerValue)inputParameters.getValue(1).values.getValue(0)).value;        
        Debug.println("[doBody] argument = " + b);
        Debug.println("[doBody] argument = " + n);
    	
    	BooleanValue result = new BooleanValue();
    	result.value = ((b >> n) & 1) == 1;
    	result.type = this.locus.factory.getBuiltInType("Boolean");

        Debug.println("[doBody] BitString IsSet result = " + result.value);

		LibraryFunctions.addValueToOutputList(result, outputParameters);
    }
    
    @Override
    public Value new_() {
        return new BitStringIsSetFunctionBehaviorExecution();
    }   

}
