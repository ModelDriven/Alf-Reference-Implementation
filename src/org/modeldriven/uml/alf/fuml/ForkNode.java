package org.modeldriven.uml.alf.fuml;


public class ForkNode extends ControlNode implements
		org.modeldriven.alf.uml.ForkNode {
	public ForkNode() {
		this(new fUML.Syntax.Activities.IntermediateActivities.ForkNode());
	}

	public ForkNode(fUML.Syntax.Activities.IntermediateActivities.ForkNode base) {
		super(base);
	}

	public fUML.Syntax.Activities.IntermediateActivities.ForkNode getBase() {
		return (fUML.Syntax.Activities.IntermediateActivities.ForkNode) this.base;
	}

}
