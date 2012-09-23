package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.Action;
import org.modeldriven.alf.uml.ActivityEdge;
import org.modeldriven.alf.uml.ActivityNode;
import org.modeldriven.alf.uml.InputPin;
import org.modeldriven.alf.uml.OutputPin;
import org.modeldriven.uml.Activity;

public interface StructuredActivityNode extends Action {
	public List<ActivityNode> getNode();

	public void addNode(ActivityNode node);

	public Activity getActivity();

	public boolean getMustIsolate();

	public void setMustIsolate(boolean mustIsolate);

	public List<ActivityEdge> getEdge();

	public void addEdge(ActivityEdge edge);

	public List<OutputPin> getStructuredNodeOutput();

	public void addStructuredNodeOutput(OutputPin structuredNodeOutput);

	public List<InputPin> getStructuredNodeInput();

	public void addStructuredNodeInput(InputPin structuredNodeInput);
}
