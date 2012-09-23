package org.modeldriven.alf.uml;

import java.util.List;

public interface ActivityNode extends RedefinableElement {
	public StructuredActivityNode getInStructuredNode();

	public Activity getActivity();

	public List<ActivityEdge> getOutgoing();

	public void addOutgoing(ActivityEdge outgoing);

	public List<ActivityEdge> getIncoming();

	public void addIncoming(ActivityEdge incoming);
}
