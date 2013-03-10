/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.eclipse.fuml.papyrus.library.bitstringfunctions;

import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Value;
import org.eclipse.papyrus.moka.fuml.debug.Debug;

public class BitStringUnsignedShiftRightFunctionBehaviorExecution extends
        BitStringFunctionBehaviorExecution {

    @Override
    public int doBitStringFunction(List<Integer> arguments) {
        int n = arguments.get(1);
        int result = arguments.get(0);
        if (n > 0) {
            result = result >>> n;
        }
        Debug.println("[doBitStringFunction] Unsigned shift right result = " + result);
        return result;
    }

    @Override
    public Value new_() {
        return new BitStringUnsignedShiftRightFunctionBehaviorExecution();
    }

}
