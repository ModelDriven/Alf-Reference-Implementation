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

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IntegerValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Value;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue;
import org.eclipse.papyrus.moka.fuml.debug.Debug;

public class BitStringBitLengthFunctionBehaviorExecution extends
        OpaqueBehaviorExecution {

    @Override
    public void doBody(
            List<ParameterValue> inputParameters,
            List<ParameterValue> outputParameters) {

    	IntegerValue result = new IntegerValue();
    	result.value = Integer.SIZE;
    	result.type = this.locus.factory.getBuiltInType("Integer");

        Debug.println("[doBody] BitString BitLength result = " + result.value);

		LibraryFunctions.addValueToOutputList(result, outputParameters);
    }
    
    @Override
    public Value new_() {
        return new BitStringBitLengthFunctionBehaviorExecution();
    }   

}
