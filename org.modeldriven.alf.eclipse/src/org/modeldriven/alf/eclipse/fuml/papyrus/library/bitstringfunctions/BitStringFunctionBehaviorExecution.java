/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.eclipse.fuml.papyrus.library.bitstringfunctions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IntegerValue;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue;
import org.eclipse.papyrus.moka.fuml.debug.Debug;
import org.modeldriven.alf.eclipse.fuml.papyrus.library.LibraryFunctions;

public abstract class BitStringFunctionBehaviorExecution extends
        OpaqueBehaviorExecution {

    @Override
    public void doBody(
            List<ParameterValue> inputParameters,
            List<ParameterValue> outputParameters) {
        // Extract bit string arguments (represented as integers) and perform a 
        // bit string function on them.

        List<Integer> integerArguments = new ArrayList<Integer>();

        for (int i = 0; i < inputParameters.size(); i++) {
            int value = ((IntegerValue) (inputParameters.get(i)).values.get(0)).value;
            Debug.println("[doBody] argument = " + value);
            integerArguments.add(value);
        }

        int value = this.doBitStringFunction(integerArguments);
        
        IntegerValue result = new IntegerValue();
        result.value = value;
        result.type = this.locus.factory.getBuiltInType("BitString");
        LibraryFunctions.addValueToOutputList(result, outputParameters);
    }

    public abstract int doBitStringFunction(List<Integer> arguments);
}
