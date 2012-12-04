package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class ExecutableNode extends ActivityNode implements
		org.modeldriven.alf.uml.ExecutableNode {

	public ExecutableNode(org.eclipse.uml2.uml.ExecutableNode base) {
		super(base);
	}

	public org.eclipse.uml2.uml.ExecutableNode getBase() {
		return (org.eclipse.uml2.uml.ExecutableNode) this.base;
	}

}
