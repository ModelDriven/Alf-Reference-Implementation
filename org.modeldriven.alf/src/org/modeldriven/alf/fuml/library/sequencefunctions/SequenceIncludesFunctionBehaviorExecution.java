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

public class SequenceIncludesFunctionBehaviorExecution implements OpaqueBehaviorExecution {

    @Override
    public void doBody(List<ParameterValue> inputs, List<ParameterValue> outputs, Debug debug) {
        final List<? extends Object> seq = inputs.get(0).getValues();
        final Object element = inputs.get(1).getValues().get(0);
        debug.println("[doBody] Includes element = " + element);
        
        boolean result = seq.contains(element);
        debug.println("[doBody] Includes result = " + result);
        
        outputs.get(0).addBooleanValue(result);
    }

    @Override
    public OpaqueBehaviorExecution new_() {
        return new SequenceIncludesFunctionBehaviorExecution();
    }

}
