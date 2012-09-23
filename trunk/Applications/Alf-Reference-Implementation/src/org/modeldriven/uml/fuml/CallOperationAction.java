package org.modeldriven.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.CallAction;
import org.modeldriven.uml.fuml.InputPin;
import org.modeldriven.uml.fuml.Operation;

public class CallOperationAction extends CallAction implements
		org.modeldriven.alf.uml.CallOperationAction {
	public CallOperationAction() {
		this(new fUML.Syntax.Actions.BasicActions.CallOperationAction());
	}

	public CallOperationAction(
			fUML.Syntax.Actions.BasicActions.CallOperationAction base) {
		super(base);
	}

	public fUML.Syntax.Actions.BasicActions.CallOperationAction getBase() {
		return (fUML.Syntax.Actions.BasicActions.CallOperationAction) this.base;
	}

	public org.modeldriven.alf.uml.Operation getOperation() {
		return new Operation(this.getBase().operation);
	}

	public void setOperation(org.modeldriven.alf.uml.Operation operation) {
		this.getBase().setOperation(((Operation) operation).getBase());
	}

	public org.modeldriven.alf.uml.InputPin getTarget() {
		return new InputPin(this.getBase().target);
	}

	public void setTarget(org.modeldriven.alf.uml.InputPin target) {
		this.getBase().setTarget(((InputPin) target).getBase());
	}

}
