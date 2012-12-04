package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class MergeNode extends ControlNode implements
		org.modeldriven.alf.uml.MergeNode {
	public MergeNode() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createMergeNode());
	}

	public MergeNode(org.eclipse.uml2.uml.MergeNode base) {
		super(base);
	}

	public org.eclipse.uml2.uml.MergeNode getBase() {
		return (org.eclipse.uml2.uml.MergeNode) this.base;
	}

}
