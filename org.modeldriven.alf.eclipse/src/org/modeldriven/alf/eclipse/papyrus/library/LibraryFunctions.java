/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.papyrus.library;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Value;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue;

public class LibraryFunctions {

	/**
	 * Add the output value to the output ParameterValueList
	 * 
	 * @param parameterValue
	 * @param outputParamters
	 */
	public static void addValueToOutputList(Value value, List<ParameterValue> outputParameters) {
		
		// The ParameterValue has already been created and added to the ParameterValueList.
		// Retrieve the first ParameterValue element in this list.
		ParameterValue parameterValue = outputParameters.get(0);

		// Create a new ValueList and add the value that is passed in as an argument
		List<Value> valueList = new ArrayList<Value>();
		valueList.add(value);
		
		// Connect the ParameterValue list to the ParameterValue
		parameterValue.values = valueList;		
	}
	
	/**
	 * Adds an empty values list to the output ParameterValueList.  This done when there is an
	 * error condition which should result in no values.
	 * 
	 * @param outputParameters
	 */
	public static void addEmptyValueListToOutputList(List<ParameterValue> outputParameters) {
		
		// The ParameterValue has already been created and added to the ParameterValueList.
		// Retrieve the first ParameterValue element in this list.
		ParameterValue parameterValue = outputParameters.get(0);

		// Create a new ValueList and leave it empty
        List<Value> valueList = new ArrayList<Value>();
		
		// Connect the empty ParameterValue list to the ParameterValue
		parameterValue.values = valueList;
	}

} // LibraryFunctions
