/*******************************************************************************
 * Copyright 2017 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.eclipse.papyrus.library;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IValue;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.IParameterValue;

public class OpaqueBehaviorExecution 
	extends org.eclipse.papyrus.moka.fuml.Semantics.impl.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution {
	
	private final org.modeldriven.alf.fuml.library.OpaqueBehaviorExecution base;
	
	public OpaqueBehaviorExecution(org.modeldriven.alf.fuml.library.OpaqueBehaviorExecution base) {
		this.base = base;
	}
	
	@Override
	public void doBody(List<IParameterValue> inputParameters, List<IParameterValue> outputParameters) {
		this.base.doBody(this.wrap(inputParameters), this.wrap(outputParameters), new Debug());
	}
	
	private List<org.modeldriven.alf.fuml.library.ParameterValue> wrap(List<IParameterValue> parameterValues) {
		List<org.modeldriven.alf.fuml.library.ParameterValue> wrappedValues = 
				new ArrayList<org.modeldriven.alf.fuml.library.ParameterValue>();
		for (IParameterValue parameterValue: parameterValues) {
			wrappedValues.add(new ParameterValue(parameterValue, this.locus));
		}
		return wrappedValues;
	}

	@Override
	public IValue new_() {
		return new OpaqueBehaviorExecution(base.new_());
	}

}
