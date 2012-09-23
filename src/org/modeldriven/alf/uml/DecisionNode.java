package org.modeldriven.alf.uml;


public interface DecisionNode extends ControlNode {
	public Behavior getDecisionInput();

	public void setDecisionInput(Behavior decisionInput);

	public ObjectFlow getDecisionInputFlow();

	public void setDecisionInputFlow(ObjectFlow decisionInputFlow);
}
