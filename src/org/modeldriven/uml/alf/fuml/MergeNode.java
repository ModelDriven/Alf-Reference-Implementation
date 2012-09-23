package org.modeldriven.uml.alf.fuml;


public class MergeNode extends ControlNode implements
		org.modeldriven.alf.uml.MergeNode {
	public MergeNode() {
		this(new fUML.Syntax.Activities.IntermediateActivities.MergeNode());
	}

	public MergeNode(
			fUML.Syntax.Activities.IntermediateActivities.MergeNode base) {
		super(base);
	}

	public fUML.Syntax.Activities.IntermediateActivities.MergeNode getBase() {
		return (fUML.Syntax.Activities.IntermediateActivities.MergeNode) this.base;
	}

}
