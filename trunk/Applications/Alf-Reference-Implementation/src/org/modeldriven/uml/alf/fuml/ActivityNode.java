package org.modeldriven.uml.alf.fuml;

import java.util.ArrayList;
import java.util.List;

public class ActivityNode extends RedefinableElement implements
		org.modeldriven.alf.uml.ActivityNode {

	public ActivityNode(
			fUML.Syntax.Activities.IntermediateActivities.ActivityNode base) {
		super(base);
	}

	public fUML.Syntax.Activities.IntermediateActivities.ActivityNode getBase() {
		return (fUML.Syntax.Activities.IntermediateActivities.ActivityNode) this.base;
	}

	public org.modeldriven.alf.uml.StructuredActivityNode getInStructuredNode() {
		return new StructuredActivityNode(this.getBase().inStructuredNode);
	}

	public org.modeldriven.alf.uml.Activity getActivity() {
		return new Activity(this.getBase().activity);
	}

	public List<org.modeldriven.alf.uml.ActivityEdge> getOutgoing() {
		List<org.modeldriven.alf.uml.ActivityEdge> list = new ArrayList<org.modeldriven.alf.uml.ActivityEdge>();
		for (fUML.Syntax.Activities.IntermediateActivities.ActivityEdge element : this
				.getBase().outgoing) {
			list.add(new ActivityEdge(element));
		}
		return list;
	}

	public void addOutgoing(org.modeldriven.alf.uml.ActivityEdge outgoing) {
		outgoing.setSource(this);
	}

	public List<org.modeldriven.alf.uml.ActivityEdge> getIncoming() {
		List<org.modeldriven.alf.uml.ActivityEdge> list = new ArrayList<org.modeldriven.alf.uml.ActivityEdge>();
		for (fUML.Syntax.Activities.IntermediateActivities.ActivityEdge element : this
				.getBase().incoming) {
			list.add(new ActivityEdge(element));
		}
		return list;
	}

	public void addIncoming(org.modeldriven.alf.uml.ActivityEdge incoming) {
		incoming.setTarget(this);
	}

}
