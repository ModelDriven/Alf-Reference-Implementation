package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class FinalNode extends ControlNode implements
		org.modeldriven.alf.uml.FinalNode {

	public FinalNode(org.eclipse.uml2.uml.FinalNode base) {
		super(base);
	}

	public org.eclipse.uml2.uml.FinalNode getBase() {
		return (org.eclipse.uml2.uml.FinalNode) this.base;
	}

}
