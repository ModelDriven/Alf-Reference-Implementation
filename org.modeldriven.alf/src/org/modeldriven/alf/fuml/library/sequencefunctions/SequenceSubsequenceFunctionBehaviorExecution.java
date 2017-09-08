/*******************************************************************************
 * Copyright 2017 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.library.sequencefunctions;

import java.util.List;

import org.modeldriven.alf.fuml.library.Debug;
import org.modeldriven.alf.fuml.library.OpaqueBehaviorExecution;
import org.modeldriven.alf.fuml.library.ParameterValue;
import org.modeldriven.alf.fuml.library.Value;

public class SequenceSubsequenceFunctionBehaviorExecution implements OpaqueBehaviorExecution {

    @Override
    public void doBody(List<ParameterValue> inputs, List<ParameterValue> outputs, Debug debug) {
        final List<? extends Value> seq = inputs.get(0).getValues();
        final int lower = (int)inputs.get(1).getObjects().get(0);
        final int upper = (int)inputs.get(2).getObjects().get(0);
        debug.println("[doBody] Subsequence lower = " + lower);
        debug.println("[doBody] Subsequence upper = " + upper);
        
        outputs.get(0).addValues(
                seq.subList(lower < 1? 0: lower - 1, upper > seq.size()? seq.size(): upper));
    }

    @Override
    public OpaqueBehaviorExecution new_() {
        return new SequenceSubsequenceFunctionBehaviorExecution();
    }

}
