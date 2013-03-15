/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.eclipse.papyrus.library.bitstringfunctions;

import java.util.List;

import org.modeldriven.alf.eclipse.papyrus.library.LibraryFunctions;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.BooleanValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IntegerValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Value;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue;
import org.eclipse.papyrus.moka.fuml.debug.Debug;

public class BitStringIsSetFunctionBehaviorExecution extends
        OpaqueBehaviorExecution {

    @Override
    public void doBody(
            List<ParameterValue> inputParameters,
            List<ParameterValue> outputParameters) {

        int b = ((IntegerValue)inputParameters.get(0).values.get(0)).value;
        int n = ((IntegerValue)inputParameters.get(1).values.get(0)).value;        
        Debug.println("[doBody] argument = " + b);
        Debug.println("[doBody] argument = " + n);
    	
    	BooleanValue result = new BooleanValue();
    	result.value = n >= 0 && ((b >> n) & 1) == 1;
    	result.type = this.locus.factory.getBuiltInType("Boolean");

        Debug.println("[doBody] BitString IsSet result = " + result.value);

		LibraryFunctions.addValueToOutputList(result, outputParameters);
    }
    
    @Override
    public Value new_() {
        return new BitStringIsSetFunctionBehaviorExecution();
    }   

}
