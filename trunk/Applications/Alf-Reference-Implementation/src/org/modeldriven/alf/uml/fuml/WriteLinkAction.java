package org.modeldriven.alf.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.LinkAction;

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
