package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class ActivityParameterNode extends ObjectNode implements
		org.modeldriven.alf.uml.ActivityParameterNode {
	public ActivityParameterNode() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE
				.createActivityParameterNode());
	}

	public ActivityParameterNode(org.eclipse.uml2.uml.ActivityParameterNode base) {
		super(base);
	}

	public org.eclipse.uml2.uml.ActivityParameterNode getBase() {
		return (org.eclipse.uml2.uml.ActivityParameterNode) this.base;
	}

	public org.modeldriven.alf.uml.Parameter getParameter() {
		return new Parameter(this.getBase().getParameter());
	}

	public void setParameter(org.modeldriven.alf.uml.Parameter parameter) {
		this.getBase().setParameter(
				parameter == null ? null : ((Parameter) parameter).getBase());
	}

}
