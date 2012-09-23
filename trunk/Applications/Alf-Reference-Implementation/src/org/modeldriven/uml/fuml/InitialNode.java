package org.modeldriven.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.ControlNode;

public class InitialNode extends ControlNode implements
		org.modeldriven.alf.uml.InitialNode {
	public InitialNode() {
		this(new fUML.Syntax.Activities.IntermediateActivities.InitialNode());
	}

	public InitialNode(
			fUML.Syntax.Activities.IntermediateActivities.InitialNode base) {
		super(base);
	}

	public fUML.Syntax.Activities.IntermediateActivities.InitialNode getBase() {
		return (fUML.Syntax.Activities.IntermediateActivities.InitialNode) this.base;
	}

}
