package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class JoinNode extends ControlNode implements
		org.modeldriven.alf.uml.JoinNode {
	public JoinNode() {
		this(UMLFactory.eINSTANCE.createJoinNode());
	}

	public JoinNode(fUML.Syntax.Activities.IntermediateActivities.JoinNode base) {
		super(base);
	}

	public org.eclipse.uml2.uml.JoinNode getBase() {
		return (org.eclipse.uml2.uml.JoinNode) this.base;
	}

}
