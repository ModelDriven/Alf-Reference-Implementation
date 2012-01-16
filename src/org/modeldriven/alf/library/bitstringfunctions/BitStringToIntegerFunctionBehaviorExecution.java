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
import fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;

public class BitStringToIntegerFunctionBehaviorExecution extends
        OpaqueBehaviorExecution {

    @Override
    public void doBody(
            ParameterValueList inputParameters,
            ParameterValueList outputParameters) {

        Integer value = ((IntegerValue)inputParameters.getValue(0).values.getValue(0)).value;
		Debug.println("[doBody] argument = " + value);
    	
    	IntegerValue result = new IntegerValue();
    	result.value = value;
    	result.type = this.locus.factory.getBuiltInType("Integer");

        Debug.println("[doBody] BitString ToInteger result = " + result.value);

		LibraryFunctions.addValueToOutputList(result, outputParameters);
    }
    
    @Override
    public fUML.Semantics.Classes.Kernel.Value new_() {
        return new BitStringToIntegerFunctionBehaviorExecution();
    }   

}
