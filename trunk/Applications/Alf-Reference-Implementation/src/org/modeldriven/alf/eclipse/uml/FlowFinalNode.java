package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class FlowFinalNode extends FinalNode implements
		org.modeldriven.alf.uml.FlowFinalNode {
	public FlowFinalNode() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createFlowFinalNode());
	}

	public FlowFinalNode(org.eclipse.uml2.uml.FlowFinalNode base) {
		super(base);
	}

	public org.eclipse.uml2.uml.FlowFinalNode getBase() {
		return (org.eclipse.uml2.uml.FlowFinalNode) this.base;
	}

}
