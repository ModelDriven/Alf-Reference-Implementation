/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.eclipse.fuml.papyrus.library.unlimitednaturalfunctions;

import java.util.ArrayList;
import java.util.List;

import org.modeldriven.alf.eclipse.fuml.papyrus.library.LibraryFunctions;

import org.eclipse.papyrus.moka.fuml.debug.Debug;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.BooleanValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.UnlimitedNaturalValue;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue;

public abstract class UnlimitedNaturalRelationalFunctionBehaviorExecution extends
        OpaqueBehaviorExecution {

    public void doBody(
            List<ParameterValue> inputParameters,
            List<ParameterValue> outputParameters) {
        // Extract unlimited natural arguments and perform an integer function on them.

        List<Integer> integerArguments = new ArrayList<Integer>();

        for (int i = 0; i < inputParameters.size(); i++) {
            int value = ((UnlimitedNaturalValue) (inputParameters.get(i)).values.get(0)).value;
            Debug.println("[doBody] argument = " + value);
            integerArguments.add(value);
        }

        BooleanValue result = new BooleanValue();
        result.value = this.doUnlimitedNaturalFunction(integerArguments);
        result.type = this.locus.factory.getBuiltInType("Boolean");

        Debug.println("[doBody] result = " + result.value);

		// Add output to the outputParameters list
		LibraryFunctions.addValueToOutputList(result, outputParameters);
    }

    public abstract boolean doUnlimitedNaturalFunction(List<Integer> arguments);
} // IntegerFunctionBehaviorExecution_ReturnBoolean
