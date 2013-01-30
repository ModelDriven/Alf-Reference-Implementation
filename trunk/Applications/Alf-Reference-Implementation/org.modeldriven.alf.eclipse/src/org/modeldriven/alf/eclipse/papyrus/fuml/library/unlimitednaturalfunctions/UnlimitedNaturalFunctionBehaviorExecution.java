/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.eclipse.papyrus.fuml.library.unlimitednaturalfunctions;

import java.util.ArrayList;
import java.util.List;

import org.modeldriven.alf.eclipse.papyrus.fuml.library.LibraryFunctions;

import org.eclipse.papyrus.moka.fuml.debug.Debug;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.UnlimitedNaturalValue;

public abstract class UnlimitedNaturalFunctionBehaviorExecution extends OpaqueBehaviorExecution {

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

        // Call the method specific to the integer function
        int value = this.doUnlimitedNaturalFunction(integerArguments);
        
        // Add output to the outputParameters list
        UnlimitedNaturalValue result = new UnlimitedNaturalValue();
        result.value = value;
        result.type = this.locus.factory.getBuiltInType("UnlimitedNatural");
        LibraryFunctions.addValueToOutputList(result, outputParameters);
	}
	
    public abstract int doUnlimitedNaturalFunction(List<Integer> arguments);	

} // UnlimitedNaturalFunctionBehaviorExecution
