package org.modeldriven.uml.alf.fuml;


public class ExecutableNode extends ActivityNode implements
		org.modeldriven.alf.uml.ExecutableNode {

	public ExecutableNode(
			fUML.Syntax.Activities.CompleteStructuredActivities.ExecutableNode base) {
		super(base);
	}

	public fUML.Syntax.Activities.CompleteStructuredActivities.ExecutableNode getBase() {
		return (fUML.Syntax.Activities.CompleteStructuredActivities.ExecutableNode) this.base;
	}

}
