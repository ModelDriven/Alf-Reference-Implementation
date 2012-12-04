package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class ControlFlow extends ActivityEdge implements
		org.modeldriven.alf.uml.ControlFlow {
	public ControlFlow() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createControlFlow());
	}

	public ControlFlow(org.eclipse.uml2.uml.ControlFlow base) {
		super(base);
	}

	public org.eclipse.uml2.uml.ControlFlow getBase() {
		return (org.eclipse.uml2.uml.ControlFlow) this.base;
	}

}
