package org.modeldriven.uml.alf.fuml;


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
