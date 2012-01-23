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

public class BitStringUnsignedShiftRightFunctionBehaviorExecution extends
        BitStringFunctionBehaviorExecution {

    @Override
    public int doBitStringFunction(intList arguments) {
        int n = arguments.get(1);
        int result = arguments.get(0);
        if (n > 0) {
            result = result >>> n;
        }
        Debug.println("[doBitStringFunction] Unsigned shift right result = " + result);
        return result;
    }

    @Override
    public fUML.Semantics.Classes.Kernel.Value new_() {
        return new BitStringUnsignedShiftRightFunctionBehaviorExecution();
    }

}
