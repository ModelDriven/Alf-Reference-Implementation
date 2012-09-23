package org.modeldriven.uml.alf.fuml;


public class WriteLinkAction extends LinkAction implements
		org.modeldriven.alf.uml.WriteLinkAction {

	public WriteLinkAction(
			fUML.Syntax.Actions.IntermediateActions.WriteLinkAction base) {
		super(base);
	}

	public fUML.Syntax.Actions.IntermediateActions.WriteLinkAction getBase() {
		return (fUML.Syntax.Actions.IntermediateActions.WriteLinkAction) this.base;
	}

}
