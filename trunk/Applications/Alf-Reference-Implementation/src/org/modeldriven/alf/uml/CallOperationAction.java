package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.CallAction;
import org.modeldriven.alf.uml.InputPin;
import org.modeldriven.uml.Operation;

public interface CallOperationAction extends CallAction {
	public Operation getOperation();

	public void setOperation(Operation operation);

	public InputPin getTarget();

	public void setTarget(InputPin target);
}
