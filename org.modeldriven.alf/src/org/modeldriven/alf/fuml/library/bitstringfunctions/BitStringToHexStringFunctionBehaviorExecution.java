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

public class BitStringToHexStringFunctionBehaviorExecution implements
        OpaqueBehaviorExecution {

    @Override
    public void doBody(List<ParameterValue> inputs, List<ParameterValue> outputs, Debug debug) {

        Integer value = (Integer)inputs.get(0).getObjects().get(0);
		debug.println("[doBody] argument = " + value);
    	
		String result = Integer.toHexString(value);
		debug.println("[doBody] BitString ToHexString result = " + result);

		outputs.get(0).addStringValue(result);
    }

    @Override
    public OpaqueBehaviorExecution new_() {
        return new BitStringToHexStringFunctionBehaviorExecution();
    }   

}