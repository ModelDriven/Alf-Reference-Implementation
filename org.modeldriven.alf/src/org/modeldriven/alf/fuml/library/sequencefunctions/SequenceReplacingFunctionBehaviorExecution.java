/*******************************************************************************
 * Copyright 2017 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.library.sequencefunctions;

import java.util.ArrayList;
import java.util.List;

import org.modeldriven.alf.fuml.library.Debug;
import org.modeldriven.alf.fuml.library.OpaqueBehaviorExecution;
import org.modeldriven.alf.fuml.library.ParameterValue;
import org.modeldriven.alf.fuml.library.Value;

public class SequenceReplacingFunctionBehaviorExecution implements OpaqueBehaviorExecution {

    @Override
    public void doBody(List<ParameterValue> inputs, List<ParameterValue> outputs, Debug debug) {
        final List<? extends Value> seq = inputs.get(0).getValues();
        final List<? extends Value> arg = inputs.get(1).getValues();
        final Value element = arg.get(0);
        final List<? extends Value> optElement = inputs.get(2).getValues();
        debug.println("[doBody] Replacing element = " + element);
       
        final List<Value> result = new ArrayList<Value>();
        result.addAll(seq);
        
        if (optElement.isEmpty()) {
            result.removeAll(arg);
        } else {
            final Value newElement = optElement.get(0);
            debug.println("[doBody] Replacing newElement = " + newElement);
            
            for (int i = 0; i < result.size(); i++) {
                if (result.get(i).equals(element)) {
                    result.set(i, newElement);
                }
            }
        }
        
        outputs.get(0).addValues(result);
    }

    @Override
    public OpaqueBehaviorExecution new_() {
        return new SequenceReplacingFunctionBehaviorExecution();
    }

}
