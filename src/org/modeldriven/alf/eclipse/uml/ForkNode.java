package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class ForkNode extends ControlNode implements
		org.modeldriven.alf.uml.ForkNode {
	public ForkNode() {
		this(UMLFactory.eINSTANCE.createForkNode());
	}

	public ForkNode(fUML.Syntax.Activities.IntermediateActivities.ForkNode base) {
		super(base);
	}

	public org.eclipse.uml2.uml.ForkNode getBase() {
		return (org.eclipse.uml2.uml.ForkNode) this.base;
	}

}
