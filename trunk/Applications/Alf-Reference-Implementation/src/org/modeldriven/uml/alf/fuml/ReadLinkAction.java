package org.modeldriven.uml.alf.fuml;


public class ReadLinkAction extends LinkAction implements
		org.modeldriven.alf.uml.ReadLinkAction {
	public ReadLinkAction() {
		this(new fUML.Syntax.Actions.IntermediateActions.ReadLinkAction());
	}

	public ReadLinkAction(
			fUML.Syntax.Actions.IntermediateActions.ReadLinkAction base) {
		super(base);
	}

	public fUML.Syntax.Actions.IntermediateActions.ReadLinkAction getBase() {
		return (fUML.Syntax.Actions.IntermediateActions.ReadLinkAction) this.base;
	}

	public org.modeldriven.alf.uml.OutputPin getResult() {
		return new OutputPin(this.getBase().result);
	}

	public void setResult(org.modeldriven.alf.uml.OutputPin result) {
		this.getBase().setResult(((OutputPin) result).getBase());
	}

}
