/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.impl.library.bitstringfunctions;

import org.modeldriven.fuml.library.LibraryFunctions;

import fUML.Debug;
import fUML.Semantics.Classes.Kernel.IntegerValue;
import fUML.Semantics.Classes.Kernel.StringValue;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;

public class BitStringToOctalStringFunctionBehaviorExecution extends
        OpaqueBehaviorExecution {

    @Override
    public void doBody(
            ParameterValueList inputParameters,
            ParameterValueList outputParameters) {

        Integer value = ((IntegerValue)inputParameters.getValue(0).values.getValue(0)).value;
		Debug.println("[doBody] argument = " + value);
    	
    	StringValue result = new StringValue();
    	result.value = Integer.toOctalString(value);
    	result.type = this.locus.factory.getBuiltInType("String");

        Debug.println("[doBody] BitString ToOctalString result = " + result.value);

		LibraryFunctions.addValueToOutputList(result, outputParameters);
    }
    
    @Override
    public fUML.Semantics.Classes.Kernel.Value new_() {
        return new BitStringToOctalStringFunctionBehaviorExecution();
    }   

}
