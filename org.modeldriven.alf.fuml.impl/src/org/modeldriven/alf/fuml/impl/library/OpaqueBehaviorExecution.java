/*******************************************************************************
 * Copyright 2017 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.impl.library;

import java.util.ArrayList;
import java.util.List;

import fUML.Semantics.Classes.Kernel.Value;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;

public class OpaqueBehaviorExecution 
	extends fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution {
	
	private final org.modeldriven.alf.fuml.library.OpaqueBehaviorExecution base;
	
	public OpaqueBehaviorExecution(org.modeldriven.alf.fuml.library.OpaqueBehaviorExecution base) {
		this.base = base;
	}
	
	@Override
	public void doBody(ParameterValueList inputParameters, ParameterValueList outputParameters) {
		this.base.doBody(this.wrap(inputParameters), this.wrap(outputParameters), new Debug());
	}
	
	private List<org.modeldriven.alf.fuml.library.ParameterValue> wrap(ParameterValueList parameterValues) {
		List<org.modeldriven.alf.fuml.library.ParameterValue> wrappedValues = 
				new ArrayList<org.modeldriven.alf.fuml.library.ParameterValue>();
		for (fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue parameterValue: parameterValues) {
			wrappedValues.add(new ParameterValue(parameterValue, this.locus));
		}
		return wrappedValues;
	}

	@Override
	public Value new_() {
		return new OpaqueBehaviorExecution(base.new_());
	}

}
