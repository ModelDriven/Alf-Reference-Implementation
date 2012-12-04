package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class CallOperationAction extends CallAction implements
		org.modeldriven.alf.uml.CallOperationAction {
	public CallOperationAction() {
		this(UMLFactory.eINSTANCE.createCallOperationAction());
	}

	public CallOperationAction(
			fUML.Syntax.Actions.BasicActions.CallOperationAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.CallOperationAction getBase() {
		return (org.eclipse.uml2.uml.CallOperationAction) this.base;
	}

	public org.modeldriven.alf.uml.Operation getOperation() {
		return new Operation(this.getBase().getOperation());
	}

	public void setOperation(org.modeldriven.alf.uml.Operation operation) {
		this.getBase().setOperation(
				operation == null ? null : ((Operation) operation).getBase());
	}

	public org.modeldriven.alf.uml.InputPin getTarget() {
		return new InputPin(this.getBase().getTarget());
	}

	public void setTarget(org.modeldriven.alf.uml.InputPin target) {
		this.getBase().setTarget(
				target == null ? null : ((InputPin) target).getBase());
	}

}
