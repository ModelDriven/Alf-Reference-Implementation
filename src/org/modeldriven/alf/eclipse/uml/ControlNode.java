package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class ControlNode extends ActivityNode implements
		org.modeldriven.alf.uml.ControlNode {

	public ControlNode(
			fUML.Syntax.Activities.IntermediateActivities.ControlNode base) {
		super(base);
	}

	public org.eclipse.uml2.uml.ControlNode getBase() {
		return (org.eclipse.uml2.uml.ControlNode) this.base;
	}

}
