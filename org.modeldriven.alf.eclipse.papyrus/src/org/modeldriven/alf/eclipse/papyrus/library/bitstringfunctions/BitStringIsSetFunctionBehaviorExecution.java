/*******************************************************************************
 * Copyright 2011, 2017 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.eclipse.papyrus.library.bitstringfunctions;

import java.util.List;

import org.modeldriven.alf.eclipse.papyrus.library.LibraryFunctions;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.IParameterValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IBooleanValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IIntegerValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IValue;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.BooleanValue;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution;
import org.eclipse.papyrus.moka.fuml.debug.Debug;
import org.eclipse.uml2.uml.PrimitiveType;

public class BitStringIsSetFunctionBehaviorExecution extends
        OpaqueBehaviorExecution {

    @Override
    public void doBody(
            List<IParameterValue> inputParameters,
            List<IParameterValue> outputParameters) {

        int b = ((IIntegerValue)inputParameters.get(0).getValues().get(0)).getValue();
        int n = ((IIntegerValue)inputParameters.get(1).getValues().get(0)).getValue();        
        Debug.println("[doBody] argument = " + b);
        Debug.println("[doBody] argument = " + n);
    	
    	IBooleanValue result = new BooleanValue();
    	result.setValue(n >= 0 && ((b >> n) & 1) == 1);
    	result.setType((PrimitiveType) this.locus.getFactory().getBuiltInType("Boolean"));

        Debug.println("[doBody] BitString IsSet result = " + result.getValue());

		LibraryFunctions.addValueToOutputList(result, outputParameters);
    }
    
    @Override
    public IValue new_() {
        return new BitStringIsSetFunctionBehaviorExecution();
    }   

}
