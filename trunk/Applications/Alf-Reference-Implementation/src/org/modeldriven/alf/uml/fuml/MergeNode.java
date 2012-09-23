package org.modeldriven.alf.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.ControlNode;

public class MergeNode extends ControlNode implements
		org.modeldriven.alf.uml.MergeNode {
	public MergeNode() {
		this(new fUML.Syntax.Activities.IntermediateActivities.MergeNode());
	}

	public MergeNode(
			fUML.Syntax.Activities.IntermediateActivities.MergeNode base) {
		super(base);
	}

	public fUML.Syntax.Activities.IntermediateActivities.MergeNode getBase() {
		return (fUML.Syntax.Activities.IntermediateActivities.MergeNode) this.base;
	}

}
