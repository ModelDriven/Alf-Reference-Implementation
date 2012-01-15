/*
 * Copyright 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.library.bitstringfunctions;

import UMLPrimitiveTypes.intList;

import fUML.Debug;
import fUML.Semantics.Classes.Kernel.IntegerValue;

public class BitStringAndFunctionBehaviorExecution extends
        BitStringFunctionBehaviorExecution {

    @Override
    public IntegerValue doBitStringFunction(intList arguments) {
        IntegerValue result = new IntegerValue();
        result.value = arguments.get(0) & arguments.get(1);

        Debug.println("[doBitStringFunction] And result = " + result.value);
        return result;
    }   

    public fUML.Semantics.Classes.Kernel.Value new_() {
        return new BitStringAndFunctionBehaviorExecution();
    }

}
