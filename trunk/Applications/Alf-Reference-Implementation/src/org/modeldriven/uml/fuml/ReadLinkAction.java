package org.modeldriven.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.LinkAction;
import org.modeldriven.uml.fuml.OutputPin;

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
