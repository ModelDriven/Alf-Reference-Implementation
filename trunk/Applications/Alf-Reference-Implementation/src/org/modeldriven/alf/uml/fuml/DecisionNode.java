package org.modeldriven.alf.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.Behavior;
import org.modeldriven.uml.fuml.ControlNode;
import org.modeldriven.uml.fuml.ObjectFlow;

public class DecisionNode extends ControlNode implements
		org.modeldriven.alf.uml.DecisionNode {
	public DecisionNode() {
		this(new fUML.Syntax.Activities.IntermediateActivities.DecisionNode());
	}

	public DecisionNode(
			fUML.Syntax.Activities.IntermediateActivities.DecisionNode base) {
		super(base);
	}

	public fUML.Syntax.Activities.IntermediateActivities.DecisionNode getBase() {
		return (fUML.Syntax.Activities.IntermediateActivities.DecisionNode) this.base;
	}

	public org.modeldriven.alf.uml.Behavior getDecisionInput() {
		return new Behavior(this.getBase().decisionInput);
	}

	public void setDecisionInput(org.modeldriven.alf.uml.Behavior decisionInput) {
		this.getBase().setDecisionInput(((Behavior) decisionInput).getBase());
	}

	public org.modeldriven.alf.uml.ObjectFlow getDecisionInputFlow() {
		return new ObjectFlow(this.getBase().decisionInputFlow);
	}

	public void setDecisionInputFlow(
			org.modeldriven.alf.uml.ObjectFlow decisionInputFlow) {
		this.getBase().setDecisionInputFlow(
				((ObjectFlow) decisionInputFlow).getBase());
	}

}
