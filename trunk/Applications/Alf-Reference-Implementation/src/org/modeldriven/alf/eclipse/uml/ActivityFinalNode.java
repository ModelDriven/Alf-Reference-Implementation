package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class ActivityFinalNode extends FinalNode implements
		org.modeldriven.alf.uml.ActivityFinalNode {
	public ActivityFinalNode() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE
				.createActivityFinalNode());
	}

	public ActivityFinalNode(org.eclipse.uml2.uml.ActivityFinalNode base) {
		super(base);
	}

	public org.eclipse.uml2.uml.ActivityFinalNode getBase() {
		return (org.eclipse.uml2.uml.ActivityFinalNode) this.base;
	}

}
