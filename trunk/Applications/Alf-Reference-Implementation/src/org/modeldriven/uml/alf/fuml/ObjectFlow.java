package org.modeldriven.uml.alf.fuml;


public class ObjectFlow extends ActivityEdge implements
		org.modeldriven.alf.uml.ObjectFlow {
	public ObjectFlow() {
		this(new fUML.Syntax.Activities.IntermediateActivities.ObjectFlow());
	}

	public ObjectFlow(
			fUML.Syntax.Activities.IntermediateActivities.ObjectFlow base) {
		super(base);
	}

	public fUML.Syntax.Activities.IntermediateActivities.ObjectFlow getBase() {
		return (fUML.Syntax.Activities.IntermediateActivities.ObjectFlow) this.base;
	}

}
