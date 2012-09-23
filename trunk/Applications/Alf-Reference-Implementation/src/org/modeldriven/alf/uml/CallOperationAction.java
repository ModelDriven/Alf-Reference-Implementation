package org.modeldriven.alf.uml;


public interface CallOperationAction extends CallAction {
	public Operation getOperation();

	public void setOperation(Operation operation);

	public InputPin getTarget();

	public void setTarget(InputPin target);
}
