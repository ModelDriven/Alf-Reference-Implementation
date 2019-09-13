/*******************************************************************************
 * Copyright 2011, 2017 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.moka.library;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IValue;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.IParameterValue;

public class LibraryFunctions {

	/**
	 * Add the output value to the output ParameterValueList
	 * 
	 * @param parameterValue
	 * @param outputParamters
	 */
	public static void addValueToOutputList(IValue value, List<IParameterValue> outputParameters) {
		
		// The ParameterValue has already been created and added to the ParameterValueList.
		// Retrieve the first ParameterValue element in this list.
		IParameterValue parameterValue = outputParameters.get(0);

		// Create a new ValueList and add the value that is passed in as an argument
		List<IValue> valueList = new ArrayList<IValue>();
		valueList.add(value);
		
		// Connect the ParameterValue list to the ParameterValue
		parameterValue.setValues(valueList);		
	}
	
	/**
	 * Adds an empty values list to the output ParameterValueList.  This done when there is an
	 * error condition which should result in no values.
	 * 
	 * @param outputParameters
	 */
	public static void addEmptyValueListToOutputList(List<IParameterValue> outputParameters) {
		
		// The ParameterValue has already been created and added to the ParameterValueList.
		// Retrieve the first ParameterValue element in this list.
		IParameterValue parameterValue = outputParameters.get(0);

		// Create a new ValueList and leave it empty
        List<IValue> valueList = new ArrayList<IValue>();
		
		// Connect the empty ParameterValue list to the ParameterValue
		parameterValue.setValues(valueList);		
	}

} // LibraryFunctions
