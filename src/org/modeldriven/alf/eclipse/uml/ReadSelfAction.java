package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class ReadSelfAction extends Action implements
		org.modeldriven.alf.uml.ReadSelfAction {
	public ReadSelfAction() {
		this(UMLFactory.eINSTANCE.createReadSelfAction());
	}

	public ReadSelfAction(
			fUML.Syntax.Actions.IntermediateActions.ReadSelfAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.ReadSelfAction getBase() {
		return (org.eclipse.uml2.uml.ReadSelfAction) this.base;
	}

	public org.modeldriven.alf.uml.OutputPin getResult() {
		return new OutputPin(this.getBase().getResult());
	}

	public void setResult(org.modeldriven.alf.uml.OutputPin result) {
		this.getBase().setResult(
				result == null ? null : ((OutputPin) result).getBase());
	}

}
