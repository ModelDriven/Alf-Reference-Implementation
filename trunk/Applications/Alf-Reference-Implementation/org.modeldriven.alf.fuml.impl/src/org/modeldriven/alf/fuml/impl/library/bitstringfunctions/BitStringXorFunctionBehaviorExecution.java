/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.impl.library.bitstringfunctions;

import UMLPrimitiveTypes.intList;

import fUML.Debug;

public class BitStringXorFunctionBehaviorExecution extends
        BitStringFunctionBehaviorExecution {

    @Override
    public int doBitStringFunction(intList arguments) {
        int result = arguments.get(0) ^ arguments.get(1);
        Debug.println("[doBitStringFunction] Xor result = " + result);
        return result;
    }   

    @Override
    public fUML.Semantics.Classes.Kernel.Value new_() {
        return new BitStringXorFunctionBehaviorExecution();
    }

}
