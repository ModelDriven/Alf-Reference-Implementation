package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class WriteLinkAction extends LinkAction implements
		org.modeldriven.alf.uml.WriteLinkAction {

	public WriteLinkAction(
			fUML.Syntax.Actions.IntermediateActions.WriteLinkAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.WriteLinkAction getBase() {
		return (org.eclipse.uml2.uml.WriteLinkAction) this.base;
	}

}
