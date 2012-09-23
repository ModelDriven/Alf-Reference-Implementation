package org.modeldriven.uml.alf.fuml;


public class JoinNode extends ControlNode implements
		org.modeldriven.alf.uml.JoinNode {
	public JoinNode() {
		this(new fUML.Syntax.Activities.IntermediateActivities.JoinNode());
	}

	public JoinNode(fUML.Syntax.Activities.IntermediateActivities.JoinNode base) {
		super(base);
	}

	public fUML.Syntax.Activities.IntermediateActivities.JoinNode getBase() {
		return (fUML.Syntax.Activities.IntermediateActivities.JoinNode) this.base;
	}

}
