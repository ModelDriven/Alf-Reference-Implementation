/*******************************************************************************
 * Copyright 2011, 2017 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.eclipse.papyrus.library.bitstringfunctions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IIntegerValue;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.IParameterValue;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.IntegerValue;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution;
import org.eclipse.papyrus.moka.fuml.debug.Debug;
import org.eclipse.uml2.uml.PrimitiveType;
import org.modeldriven.alf.eclipse.papyrus.library.LibraryFunctions;

public abstract class BitStringFunctionBehaviorExecution extends
        OpaqueBehaviorExecution {

    @Override
    public void doBody(
            List<IParameterValue> inputParameters,
            List<IParameterValue> outputParameters) {
        // Extract bit string arguments (represented as integers) and perform a 
        // bit string function on them.

        List<Integer> integerArguments = new ArrayList<Integer>();

        for (int i = 0; i < inputParameters.size(); i++) {
            int value = ((IIntegerValue) (inputParameters.get(i)).getValues().get(0)).getValue();
            Debug.println("[doBody] argument = " + value);
            integerArguments.add(value);
        }

        int value = this.doBitStringFunction(integerArguments);
        
        IIntegerValue result = new IntegerValue();
        result.setValue(value);
        result.setType((PrimitiveType) this.locus.getFactory().getBuiltInType("BitString"));
        LibraryFunctions.addValueToOutputList(result, outputParameters);
    }

    public abstract int doBitStringFunction(List<Integer> arguments);
}
