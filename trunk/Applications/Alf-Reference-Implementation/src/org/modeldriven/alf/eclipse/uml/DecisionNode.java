package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class DecisionNode extends ControlNode implements
		org.modeldriven.alf.uml.DecisionNode {
	public DecisionNode() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createDecisionNode());
	}

	public DecisionNode(org.eclipse.uml2.uml.DecisionNode base) {
		super(base);
	}

	public org.eclipse.uml2.uml.DecisionNode getBase() {
		return (org.eclipse.uml2.uml.DecisionNode) this.base;
	}

	public org.modeldriven.alf.uml.Behavior getDecisionInput() {
		return new Behavior(this.getBase().getDecisionInput());
	}

	public void setDecisionInput(org.modeldriven.alf.uml.Behavior decisionInput) {
		this.getBase().setDecisionInput(
				decisionInput == null ? null : ((Behavior) decisionInput)
						.getBase());
	}

	public org.modeldriven.alf.uml.ObjectFlow getDecisionInputFlow() {
		return new ObjectFlow(this.getBase().getDecisionInputFlow());
	}

	public void setDecisionInputFlow(
			org.modeldriven.alf.uml.ObjectFlow decisionInputFlow) {
		this.getBase().setDecisionInputFlow(
				decisionInputFlow == null ? null
						: ((ObjectFlow) decisionInputFlow).getBase());
	}

}
