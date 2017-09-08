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

public class SequenceEqualsFunctionBehaviorExecution implements OpaqueBehaviorExecution {

    @Override
    public void doBody(List<ParameterValue> inputs, List<ParameterValue> outputs, Debug debug) {
        final List<? extends Object> seq1 = inputs.get(0).getValues();
        final List<? extends Object> seq2 = inputs.get(1).getValues();
         
        boolean result = false;
        if (seq1.size() == seq2.size()) {
            result = true;
            for (int i = 0; i < seq1.size(); i++) {
                if (!seq1.get(i).equals(seq2.get(i))) {
                    result = false;
                    break;
                }
            }
        }
        debug.println("[doBody] Equals result = " + result);
        
        outputs.get(0).addBooleanValue(result);
    }

    @Override
    public OpaqueBehaviorExecution new_() {
        return new SequenceEqualsFunctionBehaviorExecution();
    }

}
