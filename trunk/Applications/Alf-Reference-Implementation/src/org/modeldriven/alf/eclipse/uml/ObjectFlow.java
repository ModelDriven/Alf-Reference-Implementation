package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class ObjectFlow extends ActivityEdge implements
		org.modeldriven.alf.uml.ObjectFlow {
	public ObjectFlow() {
		this(UMLFactory.eINSTANCE.createObjectFlow());
	}

	public ObjectFlow(
			fUML.Syntax.Activities.IntermediateActivities.ObjectFlow base) {
		super(base);
	}

	public org.eclipse.uml2.uml.ObjectFlow getBase() {
		return (org.eclipse.uml2.uml.ObjectFlow) this.base;
	}

}
