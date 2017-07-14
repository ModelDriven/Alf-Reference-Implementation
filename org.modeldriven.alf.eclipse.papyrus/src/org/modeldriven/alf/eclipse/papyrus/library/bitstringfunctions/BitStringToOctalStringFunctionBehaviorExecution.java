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

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IIntegerValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IStringValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IValue;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.IParameterValue;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.StringValue;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution;
import org.eclipse.papyrus.moka.fuml.debug.Debug;
import org.eclipse.uml2.uml.PrimitiveType;
import org.modeldriven.alf.eclipse.papyrus.library.LibraryFunctions;

public class BitStringToOctalStringFunctionBehaviorExecution extends
        OpaqueBehaviorExecution {

    @Override
    public void doBody(
            List<IParameterValue> inputParameters,
            List<IParameterValue> outputParameters) {

        Integer value = ((IIntegerValue)inputParameters.get(0).getValues().get(0)).getValue();
		Debug.println("[doBody] argument = " + value);
    	
    	IStringValue result = new StringValue();
    	result.setValue(Integer.toOctalString(value));
    	result.setType((PrimitiveType) this.locus.getFactory().getBuiltInType("String"));

        Debug.println("[doBody] BitString ToOctalString result = " + result.getValue());

		LibraryFunctions.addValueToOutputList(result, outputParameters);
    }
    
    @Override
    public IValue new_() {
        return new BitStringToOctalStringFunctionBehaviorExecution();
    }   

}
