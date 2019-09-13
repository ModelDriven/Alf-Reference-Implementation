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

public class IntegerToRealFunctionBehaviorExecution implements OpaqueBehaviorExecution {

    @Override
    public void doBody(List<ParameterValue> inputs, List<ParameterValue> outputs, Debug debug) {
        final int argument = (int)inputs.get(0).getObjects().get(0);
        debug.println("[doBody] argument = " + argument);
        
        final double result = (double)argument;
        debug.println("[doBody] Integer ToReal result = " + result);
        
        outputs.get(0).addRealValue(result);
    }

    public OpaqueBehaviorExecution new_() {
        return new IntegerToRealFunctionBehaviorExecution();
    }   

}
