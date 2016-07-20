package org.modeldriven.alf.eclipse.papyrus.library.integerfunctions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IntegerValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.RealValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Value;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue;
import org.eclipse.papyrus.moka.fuml.debug.Debug;
import org.eclipse.uml2.uml.PrimitiveType;
import org.modeldriven.alf.eclipse.papyrus.library.LibraryFunctions;

public class Divide extends OpaqueBehaviorExecution {

	@Override
	public void doBody(List<ParameterValue> inputParameters, List<ParameterValue> outputParameters) {
		try {
			int x = ((IntegerValue)inputParameters.get(0).values.get(0)).value;
			int y = ((IntegerValue)inputParameters.get(1).values.get(0)).value;
			if (y == 0) {
	    		LibraryFunctions.addEmptyValueListToOutputList(outputParameters);
			} else {
				RealValue result = new RealValue();
				result.value = ((float)x)/((float)y);
				result.type = (PrimitiveType) this.locus.factory.getBuiltInType("Real");
				List<Value> outputs = new ArrayList<Value>();
				outputs.add(result);
				outputParameters.get(0).values = outputs;
			}
		} catch (Exception e) {
    		Debug.println("[doBody] An error occured during the execution of Integer divide: " + e.getMessage());
    		LibraryFunctions.addEmptyValueListToOutputList(outputParameters);
    		return;
		}
	}

	@Override
	public Value new_() {
		return new Divide();
	}

}
