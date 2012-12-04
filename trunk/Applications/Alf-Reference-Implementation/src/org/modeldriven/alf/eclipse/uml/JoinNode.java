package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class JoinNode extends ControlNode implements
		org.modeldriven.alf.uml.JoinNode {
	public JoinNode() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createJoinNode());
	}

	public JoinNode(org.eclipse.uml2.uml.JoinNode base) {
		super(base);
	}

	public org.eclipse.uml2.uml.JoinNode getBase() {
		return (org.eclipse.uml2.uml.JoinNode) this.base;
	}

}
