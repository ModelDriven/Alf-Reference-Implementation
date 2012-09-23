package org.modeldriven.alf.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.Action;
import org.modeldriven.uml.fuml.OutputPin;

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
