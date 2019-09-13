/*******************************************************************************
 * Copyright 2017 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.library.bitstringfunctions;

import java.util.List;

import org.modeldriven.alf.fuml.library.Debug;
import org.modeldriven.alf.fuml.library.OpaqueBehaviorExecution;

public class BitStringXorFunctionBehaviorExecution extends
        BitStringFunctionBehaviorExecution {

    @Override
    public int doBitStringFunction(List<Integer> arguments, Debug debug) {
        int result = arguments.get(0) ^ arguments.get(1);
        debug.println("[doBitStringFunction] Xor result = " + result);
        return result;
    }   

    @Override
    public OpaqueBehaviorExecution new_() {
        return new BitStringXorFunctionBehaviorExecution();
    }

}
