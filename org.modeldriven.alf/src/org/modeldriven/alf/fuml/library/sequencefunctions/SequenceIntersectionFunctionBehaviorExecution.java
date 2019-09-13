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

public class SequenceIntersectionFunctionBehaviorExecution implements OpaqueBehaviorExecution {

    @Override
    public void doBody(List<ParameterValue> inputs, List<ParameterValue> outputs, Debug debug) {
        final List<? extends Value> seq1 = inputs.get(0).getValues();
        final List<? extends Value> seq2 = inputs.get(1).getValues();        
        debug.println("[doBody] Intersection");

        final List<Value> result = new ArrayList<Value>();
        result.addAll(seq1);
        result.retainAll(seq2);
        outputs.get(0).addValues(result);
    }

    @Override
    public OpaqueBehaviorExecution new_() {
        return new SequenceIntersectionFunctionBehaviorExecution();
    }

}
