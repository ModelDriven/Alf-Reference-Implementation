package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class InitialNode extends ControlNode implements
		org.modeldriven.alf.uml.InitialNode {
	public InitialNode() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createInitialNode());
	}

	public InitialNode(org.eclipse.uml2.uml.InitialNode base) {
		super(base);
	}

	public org.eclipse.uml2.uml.InitialNode getBase() {
		return (org.eclipse.uml2.uml.InitialNode) this.base;
	}

}
