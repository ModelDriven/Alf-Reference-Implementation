package org.modeldriven.uml.alf.fuml;


public class FinalNode extends ControlNode implements
		org.modeldriven.alf.uml.FinalNode {

	public FinalNode(
			fUML.Syntax.Activities.IntermediateActivities.FinalNode base) {
		super(base);
	}

	public fUML.Syntax.Activities.IntermediateActivities.FinalNode getBase() {
		return (fUML.Syntax.Activities.IntermediateActivities.FinalNode) this.base;
	}

}
