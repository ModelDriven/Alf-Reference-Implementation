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

public class UnlimitedNaturalToStringFunctionBehaviorExecution extends
		OpaqueBehaviorExecution {

    public void doBody(
            List<ParameterValue> inputParameters,
            List<ParameterValue> outputParameters) {

		UnlimitedNaturalValue unv1 = (UnlimitedNaturalValue) inputParameters.get(0).values.get(0);
		Debug.println("[doBody] argument = " + unv1.value);
		
		// Perform the toString operation.  If value is -1, return "*"
		StringValue resultObj = new StringValue();
		resultObj.value = unv1.toString();
		resultObj.type = this.locus.factory.getBuiltInType("String");

		Debug.println("[doBody] Unlimited Natural ToString result = " + resultObj.value);
				
		// Add output to the outputParameters list
		LibraryFunctions.addValueToOutputList(resultObj, outputParameters);
	}
	
    @Override
    public Value new_() {
        return new UnlimitedNaturalToStringFunctionBehaviorExecution();
    }	

} // UnlimitedNaturalToStringFunctionBehaviorExecution
