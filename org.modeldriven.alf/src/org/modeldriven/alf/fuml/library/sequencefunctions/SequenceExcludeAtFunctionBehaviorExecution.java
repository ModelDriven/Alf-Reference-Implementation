/*******************************************************************************
 * Copyright 2017 Data Access Technologies, Inc. (Model Driven Solutions)
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

public class SequenceExcludeAtFunctionBehaviorExecution implements OpaqueBehaviorExecution {

    @Override
    public void doBody(List<ParameterValue> inputs, List<ParameterValue> outputs, Debug debug) {
        final List<? extends Value> seq = inputs.get(0).getValues();
        final int index = (int)inputs.get(1).getObjects().get(0);
        
        final List<Value> result = new ArrayList<Value>();
        result.addAll(seq);
        
        if (index >= 1 && index <= seq.size()) {
            debug.println("[doBody] ExcludeAt index = " + index);
            result.remove(index - 1);
        }
        
        outputs.get(0).addValues(result);
    }

    @Override
    public OpaqueBehaviorExecution new_() {
        return new SequenceExcludeAtFunctionBehaviorExecution();
    }

}
