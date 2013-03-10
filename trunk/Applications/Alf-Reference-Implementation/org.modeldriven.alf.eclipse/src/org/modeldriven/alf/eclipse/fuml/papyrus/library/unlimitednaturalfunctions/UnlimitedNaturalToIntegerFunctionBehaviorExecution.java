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

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IntegerValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.UnlimitedNaturalValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Value;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue;
import org.eclipse.papyrus.moka.fuml.debug.Debug;

public class UnlimitedNaturalToIntegerFunctionBehaviorExecution extends
		OpaqueBehaviorExecution {

	@Override
	public void doBody(
			List<ParameterValue> inputParameters,
			List<ParameterValue> outputParameters) {

		// Get first UnlimitedNatural input argument
		UnlimitedNaturalValue unv1 = (UnlimitedNaturalValue) inputParameters.get(0).values.get(0);
		Integer un1 = unv1.value;
		Debug.println("[doBody] argument = " + un1);
		
		// If the unlimited natural is unbounded (equal to -1), it cannot be
		// converted to an integer.  Return an empty values list.
		if (un1 == -1) {
			Debug.println("[doBody] Unbounded input invalid for ToInteger function");
			LibraryFunctions.addEmptyValueListToOutputList(outputParameters);
		} else {
			IntegerValue resultObj = new IntegerValue();
			resultObj.value = un1;
			resultObj.type = this.locus.factory.getBuiltInType("Integer");
			Debug.println("[doBody] Unlimited Natural ToInteger result = " + resultObj.value);
			// Add output to the outputParameters list
			LibraryFunctions.addValueToOutputList(resultObj, outputParameters);
		}
	}
	
	@Override
    public Value new_() {
        return new UnlimitedNaturalToIntegerFunctionBehaviorExecution();
    }	

} // UnlimitedNaturalToIntegerFunctionBehaviorExecution
