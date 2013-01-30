/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.eclipse.papyrus.fuml.library.listfunctions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.moka.fuml.debug.Debug;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Value;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue;

public class ListConcatFunctionBehaviorExecution extends OpaqueBehaviorExecution {

	@Override
    public void doBody(
            List<ParameterValue> inputParameters,
            List<ParameterValue> outputParameters) {
    	
    	List<Value> list1 = inputParameters.get(0).values;
    	List<Value> list2 = inputParameters.get(1).values;
    	
    	List<Value> result = new ArrayList<Value>();
    	result.addAll(list1);
    	result.addAll(list2);

        Debug.println("[doBody] List Concat, result=" + result);

		outputParameters.get(0).values = result;
    }
    
    public Value new_() {
        // Create a new instance of this kind of function behavior execution.
        return new ListConcatFunctionBehaviorExecution();
    }   

} // ListSizeFunctionBehaviorExecution
