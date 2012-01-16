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
import fUML.Semantics.Classes.Kernel.Value;

public class BitStringShiftLeftFunctionBehaviorExecution extends
        BitStringFunctionBehaviorExecution {

    @Override
    public int doBitStringFunction(intList arguments) {
        int result = arguments.get(0) << arguments.get(1);
        Debug.println("[doBitStringFunction] Shift left result = " + result);
        return result;
    }   

    @Override
    public Value new_() {
        return new BitStringShiftLeftFunctionBehaviorExecution();
    }

}
