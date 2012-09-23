package org.modeldriven.uml.alf.fuml;


public class ControlNode extends ActivityNode implements
		org.modeldriven.alf.uml.ControlNode {

	public ControlNode(
			fUML.Syntax.Activities.IntermediateActivities.ControlNode base) {
		super(base);
	}

	public fUML.Syntax.Activities.IntermediateActivities.ControlNode getBase() {
		return (fUML.Syntax.Activities.IntermediateActivities.ControlNode) this.base;
	}

}
