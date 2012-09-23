package org.modeldriven.alf.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.ControlNode;

public class FinalNode extends ControlNode implements
		org.modeldriven.alf.uml.FinalNode {

	public FinalNode(
			fUML.Syntax.Activities.IntermediateActivities.FinalNode base) {
		super(base);
	}

	public fUML.Syntax.Activities.IntermediateActivities.FinalNode getBase() {
		return (fUML.Syntax.Activities.IntermediateActivities.FinalNode) this.base;
	}

}
