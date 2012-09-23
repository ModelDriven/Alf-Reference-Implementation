package org.modeldriven.alf.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.ActivityNode;

public class ExecutableNode extends ActivityNode implements
		org.modeldriven.alf.uml.ExecutableNode {

	public ExecutableNode(
			fUML.Syntax.Activities.CompleteStructuredActivities.ExecutableNode base) {
		super(base);
	}

	public fUML.Syntax.Activities.CompleteStructuredActivities.ExecutableNode getBase() {
		return (fUML.Syntax.Activities.CompleteStructuredActivities.ExecutableNode) this.base;
	}

}
