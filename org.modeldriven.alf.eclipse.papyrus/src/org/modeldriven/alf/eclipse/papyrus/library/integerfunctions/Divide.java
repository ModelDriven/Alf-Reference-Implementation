/*******************************************************************************
 * Copyright 2011, 2017 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.eclipse.papyrus.library.integerfunctions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.RealValue;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IIntegerValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IRealValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IValue;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.IParameterValue;
import org.eclipse.papyrus.moka.fuml.debug.Debug;
import org.eclipse.uml2.uml.PrimitiveType;
import org.modeldriven.alf.eclipse.papyrus.library.LibraryFunctions;

public class Divide extends OpaqueBehaviorExecution {

	@Override
	public void doBody(List<IParameterValue> inputParameters, List<IParameterValue> outputParameters) {
		try {
			int x = ((IIntegerValue)inputParameters.get(0).getValues().get(0)).getValue();
			int y = ((IIntegerValue)inputParameters.get(1).getValues().get(0)).getValue();
			if (y == 0) {
	    		LibraryFunctions.addEmptyValueListToOutputList(outputParameters);
			} else {
				IRealValue result = new RealValue();
				result.setValue(((double)x)/((double)y));
				result.setType((PrimitiveType) this.locus.getFactory().getBuiltInType("Real"));
				List<IValue> outputs = new ArrayList<IValue>();
				outputs.add(result);
				outputParameters.get(0).setValues(outputs);
			}
		} catch (Exception e) {
    		Debug.println("[doBody] An error occured during the execution of Integer divide: " + e.getMessage());
    		LibraryFunctions.addEmptyValueListToOutputList(outputParameters);
    		return;
		}
	}

	@Override
	public IValue new_() {
		return new Divide();
	}

}
