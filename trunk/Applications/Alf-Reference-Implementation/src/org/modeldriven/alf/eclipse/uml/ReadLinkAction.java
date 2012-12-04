package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class ReadLinkAction extends LinkAction implements
		org.modeldriven.alf.uml.ReadLinkAction {
	public ReadLinkAction() {
		this(UMLFactory.eINSTANCE.createReadLinkAction());
	}

	public ReadLinkAction(
			fUML.Syntax.Actions.IntermediateActions.ReadLinkAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.ReadLinkAction getBase() {
		return (org.eclipse.uml2.uml.ReadLinkAction) this.base;
	}

	public org.modeldriven.alf.uml.OutputPin getResult() {
		return new OutputPin(this.getBase().getResult());
	}

	public void setResult(org.modeldriven.alf.uml.OutputPin result) {
		this.getBase().setResult(
				result == null ? null : ((OutputPin) result).getBase());
	}

}
