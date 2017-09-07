/*******************************************************************************
 * Copyright 2017 Data Access Technologies, Inc. (Model Driven Solutions)
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
import org.modeldriven.alf.fuml.library.ParameterValue;

public class BitStringToOctalStringFunctionBehaviorExecution implements
        OpaqueBehaviorExecution {

    @Override
    public void doBody(List<ParameterValue> inputs, List<ParameterValue> outputs, Debug debug) {

        int value = (int)inputs.get(0).getValues().get(0);
		debug.println("[doBody] argument = " + value);
    	
		String result = Integer.toOctalString(value);
        debug.println("[doBody] BitString ToOctalString result = " + result);

		outputs.get(0).addStringValue(result);
    }
    
    @Override
    public OpaqueBehaviorExecution new_() {
        return new BitStringToOctalStringFunctionBehaviorExecution();
    }   

}
