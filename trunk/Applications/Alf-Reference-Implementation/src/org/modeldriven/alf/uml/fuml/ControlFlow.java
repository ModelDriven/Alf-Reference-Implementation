package org.modeldriven.alf.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.ActivityEdge;

public class ControlFlow extends ActivityEdge implements
		org.modeldriven.alf.uml.ControlFlow {
	public ControlFlow() {
		this(new fUML.Syntax.Activities.IntermediateActivities.ControlFlow());
	}

	public ControlFlow(
			fUML.Syntax.Activities.IntermediateActivities.ControlFlow base) {
		super(base);
	}

	public fUML.Syntax.Activities.IntermediateActivities.ControlFlow getBase() {
		return (fUML.Syntax.Activities.IntermediateActivities.ControlFlow) this.base;
	}

}
