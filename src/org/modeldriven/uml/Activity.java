package org.modeldriven.uml;

import java.util.List;

import org.modeldriven.alf.uml.ActivityEdge;
import org.modeldriven.alf.uml.ActivityNode;
import org.modeldriven.alf.uml.StructuredActivityNode;
import org.modeldriven.uml.Behavior;

public interface Activity extends Behavior {
	public List<StructuredActivityNode> getStructuredNode();

	public List<ActivityNode> getNode();

	public void addNode(ActivityNode node);

	public boolean getIsReadOnly();

	public void setIsReadOnly(boolean isReadOnly);

	public List<ActivityEdge> getEdge();

	public void addEdge(ActivityEdge edge);
}
