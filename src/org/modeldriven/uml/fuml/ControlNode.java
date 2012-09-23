package org.modeldriven.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.ActivityNode;

public class ControlNode extends ActivityNode implements
		org.modeldriven.alf.uml.ControlNode {

	public ControlNode(
			fUML.Syntax.Activities.IntermediateActivities.ControlNode base) {
		super(base);
	}

	public fUML.Syntax.Activities.IntermediateActivities.ControlNode getBase() {
		return (fUML.Syntax.Activities.IntermediateActivities.ControlNode) this.base;
	}

}
