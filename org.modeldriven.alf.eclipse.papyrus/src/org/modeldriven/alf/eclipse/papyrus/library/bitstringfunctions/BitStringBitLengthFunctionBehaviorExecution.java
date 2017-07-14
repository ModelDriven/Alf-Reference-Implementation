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
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.IParameterValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IIntegerValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IValue;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.IntegerValue;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution;
import org.eclipse.papyrus.moka.fuml.debug.Debug;
import org.eclipse.uml2.uml.PrimitiveType;

public class BitStringBitLengthFunctionBehaviorExecution extends
        OpaqueBehaviorExecution {

    @Override
    public void doBody(
            List<IParameterValue> inputParameters,
            List<IParameterValue> outputParameters) {

    	IIntegerValue result = new IntegerValue();
    	result.setValue(Integer.SIZE);
    	result.setType((PrimitiveType) this.locus.getFactory().getBuiltInType("Integer"));

        Debug.println("[doBody] BitString BitLength result = " + result.getValue());

		LibraryFunctions.addValueToOutputList(result, outputParameters);
    }
    
    @Override
    public IValue new_() {
        return new BitStringBitLengthFunctionBehaviorExecution();
    }   

}
