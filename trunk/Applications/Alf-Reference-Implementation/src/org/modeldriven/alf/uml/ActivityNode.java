package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.ActivityEdge;
import org.modeldriven.alf.uml.StructuredActivityNode;
import org.modeldriven.uml.Activity;
import org.modeldriven.uml.RedefinableElement;

public interface ActivityNode extends RedefinableElement {
	public StructuredActivityNode getInStructuredNode();

	public Activity getActivity();

	public List<ActivityEdge> getOutgoing();

	public void addOutgoing(ActivityEdge outgoing);

	public List<ActivityEdge> getIncoming();

	public void addIncoming(ActivityEdge incoming);
}
