/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.eclipse.fuml.papyrus.library.unlimitednaturalfunctions;

import java.util.List;

import org.modeldriven.alf.eclipse.fuml.papyrus.library.LibraryFunctions;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.StringValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.UnlimitedNaturalValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Value;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue;
import org.eclipse.papyrus.moka.fuml.debug.Debug;

public class UnlimitedNaturalToUnlimitedNaturalFunctionBehaviorExecution extends
		OpaqueBehaviorExecution {

	@Override
	public void doBody(
			List<ParameterValue> inputParameters,
			List<ParameterValue> outputParameters) {

		// Get first StringValue input argument
		StringValue sv1 = (StringValue) inputParameters.get(0).values.get(0);
		String s = sv1.value;
		Debug.println("[doBody] argument = " + s);
		
		UnlimitedNaturalValue resultObj = new UnlimitedNaturalValue();
		resultObj.type = this.locus.factory.getBuiltInType("UnlimitedNatural");
		
		// If the String is value "*", it specifies unbounded.
		if (s.equals("*")) {
			resultObj.value = -1;
			Debug.println("[doBody] Unlimited Natural ToUnlimitedNatural result = -1");			
			// Add output to the outputParameters list
			LibraryFunctions.addValueToOutputList(resultObj, outputParameters);
			return;
		}
		
		// Convert String to integer.  This throws a NumberFormatException if the String
		// does not specify an integer
		int i = 0;
		try {
			i = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			Debug.println("[doBody] Cannot be converted to an UnlimitedNatural: " + s);
			LibraryFunctions.addEmptyValueListToOutputList(outputParameters);
			return;
		}
		
		// If the integer is less than -1, it cannot be converted to an UnlimitedNatural.
		// Return an empty values list
		if (i < 0) {
			Debug.println("[doBody] Cannot be converted to an UnlimitedNatural: " + i);
			LibraryFunctions.addEmptyValueListToOutputList(outputParameters);
		} else {
			Debug.println("[doBody] Unlimited Natural ToUnlimitedNatural result = " + i);
			resultObj.value = i;
			// Add output to the outputParameters list
			LibraryFunctions.addValueToOutputList(resultObj, outputParameters);
		}
		
		return;
	}
	
    @Override
    public Value new_() {
        return new UnlimitedNaturalToUnlimitedNaturalFunctionBehaviorExecution();
    }	

} // UnlimitedNaturalToUnlimitedNaturalFunctionBehaviorExecution
