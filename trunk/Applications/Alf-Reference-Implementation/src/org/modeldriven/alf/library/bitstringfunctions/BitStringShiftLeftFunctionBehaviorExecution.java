/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.library.bitstringfunctions;

import UMLPrimitiveTypes.intList;

import fUML.Debug;
import fUML.Semantics.Classes.Kernel.Value;

public class BitStringShiftLeftFunctionBehaviorExecution extends
        BitStringFunctionBehaviorExecution {

    @Override
    public int doBitStringFunction(intList arguments) {
        int n = arguments.get(1);
        int result = arguments.get(0);
        if (n >0) {
            result = result << n;
        }
        Debug.println("[doBitStringFunction] Shift left result = " + result);
        return result;
    }   

    @Override
    public Value new_() {
        return new BitStringShiftLeftFunctionBehaviorExecution();
    }

}
