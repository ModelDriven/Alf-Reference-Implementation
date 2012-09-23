package org.modeldriven.uml.alf.fuml;


public class ReadSelfAction extends Action implements
		org.modeldriven.alf.uml.ReadSelfAction {
	public ReadSelfAction() {
		this(new fUML.Syntax.Actions.IntermediateActions.ReadSelfAction());
	}

	public ReadSelfAction(
			fUML.Syntax.Actions.IntermediateActions.ReadSelfAction base) {
		super(base);
	}

	public fUML.Syntax.Actions.IntermediateActions.ReadSelfAction getBase() {
		return (fUML.Syntax.Actions.IntermediateActions.ReadSelfAction) this.base;
	}

	public org.modeldriven.alf.uml.OutputPin getResult() {
		return new OutputPin(this.getBase().result);
	}

	public void setResult(org.modeldriven.alf.uml.OutputPin result) {
		this.getBase().setResult(((OutputPin) result).getBase());
	}

}
