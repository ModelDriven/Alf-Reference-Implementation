package org.modeldriven.alf.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.FinalNode;

public class ActivityFinalNode extends FinalNode implements
		org.modeldriven.alf.uml.ActivityFinalNode {
	public ActivityFinalNode() {
		this(
				new fUML.Syntax.Activities.IntermediateActivities.ActivityFinalNode());
	}

	public ActivityFinalNode(
			fUML.Syntax.Activities.IntermediateActivities.ActivityFinalNode base) {
		super(base);
	}

	public fUML.Syntax.Activities.IntermediateActivities.ActivityFinalNode getBase() {
		return (fUML.Syntax.Activities.IntermediateActivities.ActivityFinalNode) this.base;
	}

}
