/*******************************************************************************
 * Copyright 2017 Model Driven Solutions, Inc.
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

public class SequenceExcludesAllFunctionBehaviorExecution implements OpaqueBehaviorExecution {

    @Override
    public void doBody(List<ParameterValue> inputs, List<ParameterValue> outputs, Debug debug) {
        final List<? extends Object> seq1 = inputs.get(0).getValues();
        final List<? extends Object> seq2 = inputs.get(1).getValues();
         
        boolean result = true;
        for (Object element: seq2) {
            result = !seq1.contains(element);
            if (!result) {
                break;
            }           
        }
        debug.println("[doBody] ExcludesAll result = " + result);
        
        outputs.get(0).addBooleanValue(result);
    }

    @Override
    public OpaqueBehaviorExecution new_() {
        return new SequenceExcludesAllFunctionBehaviorExecution();
    }

}
