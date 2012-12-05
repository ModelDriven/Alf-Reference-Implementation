package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class ActivityNode extends RedefinableElement implements
		org.modeldriven.alf.uml.ActivityNode {

	public ActivityNode(org.eclipse.uml2.uml.ActivityNode base) {
		super(base);
	}

	public org.eclipse.uml2.uml.ActivityNode getBase() {
		return (org.eclipse.uml2.uml.ActivityNode) this.base;
	}

	public org.modeldriven.alf.uml.StructuredActivityNode getInStructuredNode() {
		return wrap(this.getBase().getInStructuredNode());
	}

	public org.modeldriven.alf.uml.Activity getActivity() {
		return wrap(this.getBase().getActivity());
	}

	public List<org.modeldriven.alf.uml.ActivityEdge> getOutgoing() {
		List<org.modeldriven.alf.uml.ActivityEdge> list = new ArrayList<org.modeldriven.alf.uml.ActivityEdge>();
		for (org.eclipse.uml2.uml.ActivityEdge element : this.getBase()
				.getOutgoings()) {
			list.add(wrap(element));
		}
		return list;
	}

	public void addOutgoing(org.modeldriven.alf.uml.ActivityEdge outgoing) {
		this.getBase().getOutgoings().add(
				outgoing == null ? null : ((ActivityEdge) outgoing).getBase());
	}

	public List<org.modeldriven.alf.uml.ActivityEdge> getIncoming() {
		List<org.modeldriven.alf.uml.ActivityEdge> list = new ArrayList<org.modeldriven.alf.uml.ActivityEdge>();
		for (org.eclipse.uml2.uml.ActivityEdge element : this.getBase()
				.getIncomings()) {
			list.add(wrap(element));
		}
		return list;
	}

	public void addIncoming(org.modeldriven.alf.uml.ActivityEdge incoming) {
		this.getBase().getIncomings().add(
				incoming == null ? null : ((ActivityEdge) incoming).getBase());
	}

}
