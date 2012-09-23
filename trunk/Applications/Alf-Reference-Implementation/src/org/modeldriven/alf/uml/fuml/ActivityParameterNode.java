package org.modeldriven.alf.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.ObjectNode;
import org.modeldriven.uml.fuml.Parameter;

public class ActivityParameterNode extends ObjectNode implements
		org.modeldriven.alf.uml.ActivityParameterNode {
	public ActivityParameterNode() {
		this(
				new fUML.Syntax.Activities.IntermediateActivities.ActivityParameterNode());
	}

	public ActivityParameterNode(
			fUML.Syntax.Activities.IntermediateActivities.ActivityParameterNode base) {
		super(base);
	}

	public fUML.Syntax.Activities.IntermediateActivities.ActivityParameterNode getBase() {
		return (fUML.Syntax.Activities.IntermediateActivities.ActivityParameterNode) this.base;
	}

	public org.modeldriven.alf.uml.Parameter getParameter() {
		return new Parameter(this.getBase().parameter);
	}

	public void setParameter(org.modeldriven.alf.uml.Parameter parameter) {
		this.getBase().setParameter(((Parameter) parameter).getBase());
	}

}
