/*******************************************************************************
 * Copyright 2017 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.library.integerfunctions;

import java.util.List;

import org.modeldriven.alf.fuml.library.Debug;
import org.modeldriven.alf.fuml.library.OpaqueBehaviorExecution;
import org.modeldriven.alf.fuml.library.ParameterValue;

public class IntegerToNaturalFunctionBehaviorExecution implements OpaqueBehaviorExecution {

    @Override
    public void doBody(List<ParameterValue> inputs, List<ParameterValue> outputs, Debug debug) {

        String value = (String)inputs.get(0).getObjects().get(0);
		debug.println("[doBody] argument = " + value);

		int result;
		try {
		    int radix = 10;
		    if (value.length() > 1 && value.charAt(0) == '0') {
		        char radixChar = value.charAt(1);
		        radix = 
		                radixChar == 'b' || radixChar == 'B'? 2: 
		                    radixChar == 'x' || radixChar == 'X'? 16: 8;
		        if (radix != 8) {
		            value = value.substring(2);
		        }
		    }
		    result = Integer.parseInt(value.replaceAll("_", ""), radix);
		} catch (NumberFormatException e) {
		    // If the String does not specify an integer, simply return an empty values list
		    debug.println("[doBody] string does not specify a natural: " + value);
		    outputs.get(0).addValue(null);
		    return;
		}

		debug.println("[doBody] Integer ToInteger result = " + result);

		outputs.get(0).addIntegerValue(result);
    }

    public OpaqueBehaviorExecution new_() {
        return new IntegerToNaturalFunctionBehaviorExecution();
    }   

}
