/*******************************************************************************
 * Copyright 2011, 2017 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.eclipse.papyrus.library.integerfunctions;

import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IIntegerValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IStringValue;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.IParameterValue;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.IntegerValue;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution;
import org.eclipse.papyrus.moka.fuml.debug.Debug;
import org.eclipse.uml2.uml.PrimitiveType;
import org.modeldriven.alf.eclipse.papyrus.library.LibraryFunctions;

public class ToNatural extends OpaqueBehaviorExecution {

    public void doBody(
            List<IParameterValue> inputParameters,
            List<IParameterValue> outputParameters) {

        IStringValue sv = (IStringValue) inputParameters.get(0).getValues().get(0);
    	String value = sv.getValue();
		Debug.println("[doBody] argument = " + value);

    	int resultInt;
    	try {
            int radix = 10;
            if (value.length() > 1 && value.charAt(0) == '0') {
                char radixChar = value.charAt(1);
                radix = 
                    radixChar == 'b' || radixChar == 'B'? 2: 
                    radixChar == 'x' || radixChar == 'X'? 16: 8;
                if (radix != 8) {
                    value = value.substring(2);
                }
            }
            resultInt = Integer.parseInt(value.replaceAll("_", ""), radix);
    	} catch (NumberFormatException e) {
    		// If the String does not specify an integer, simply return an empty values list
    		Debug.println("[doBody] string does not specify a natural: " + value);
    		LibraryFunctions.addEmptyValueListToOutputList(outputParameters);
    		return;
    	}
    	
    	IIntegerValue result = new IntegerValue();
    	result.setValue(resultInt);
    	result.setType((PrimitiveType) this.locus.getFactory().getBuiltInType("Integer"));

        Debug.println("[doBody] Integer ToInteger result = " + result.getValue());

		LibraryFunctions.addValueToOutputList(result, outputParameters);
    }
    
    public org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.Value new_() {
        return new ToNatural();
    }   

}
