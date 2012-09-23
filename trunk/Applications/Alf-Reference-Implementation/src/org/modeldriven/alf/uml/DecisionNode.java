package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.ControlNode;
import org.modeldriven.alf.uml.ObjectFlow;
import org.modeldriven.uml.Behavior;

public interface DecisionNode extends ControlNode {
	public Behavior getDecisionInput();

	public void setDecisionInput(Behavior decisionInput);

	public ObjectFlow getDecisionInputFlow();

	public void setDecisionInputFlow(ObjectFlow decisionInputFlow);
}
