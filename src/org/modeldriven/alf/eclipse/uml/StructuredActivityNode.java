package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class StructuredActivityNode extends Action implements
		org.modeldriven.alf.uml.StructuredActivityNode {
	public StructuredActivityNode() {
		this(UMLFactory.eINSTANCE.createStructuredActivityNode());
	}

	public StructuredActivityNode(
			fUML.Syntax.Activities.CompleteStructuredActivities.StructuredActivityNode base) {
		super(base);
	}

	public org.eclipse.uml2.uml.StructuredActivityNode getBase() {
		return (org.eclipse.uml2.uml.StructuredActivityNode) this.base;
	}

	public List<org.modeldriven.alf.uml.ActivityNode> getNode() {
		List<org.modeldriven.alf.uml.ActivityNode> list = new ArrayList<org.modeldriven.alf.uml.ActivityNode>();
		for (org.eclipse.uml2.uml.ActivityNode element : this.getBase()
				.getNode()) {
			list.add(new ActivityNode(element));
		}
		return list;
	}

	public void addNode(org.modeldriven.alf.uml.ActivityNode node) {
		this.getBase().addNode(
				node == null ? null : ((ActivityNode) node).getBase());
	}

	public org.modeldriven.alf.uml.Activity getActivity() {
		return new Activity(this.getBase().getActivity());
	}

	public boolean getMustIsolate() {
		return this.getBase().getMustIsolate();
	}

	public void setMustIsolate(boolean mustIsolate) {
		this.getBase().setMustIsolate(mustIsolate);
	}

	public List<org.modeldriven.alf.uml.ActivityEdge> getEdge() {
		List<org.modeldriven.alf.uml.ActivityEdge> list = new ArrayList<org.modeldriven.alf.uml.ActivityEdge>();
		for (org.eclipse.uml2.uml.ActivityEdge element : this.getBase()
				.getEdge()) {
			list.add(new ActivityEdge(element));
		}
		return list;
	}

	public void addEdge(org.modeldriven.alf.uml.ActivityEdge edge) {
		this.getBase().addEdge(
				edge == null ? null : ((ActivityEdge) edge).getBase());
	}

	public List<org.modeldriven.alf.uml.OutputPin> getStructuredNodeOutput() {
		List<org.modeldriven.alf.uml.OutputPin> list = new ArrayList<org.modeldriven.alf.uml.OutputPin>();
		for (org.eclipse.uml2.uml.OutputPin element : this.getBase()
				.getStructuredNodeOutput()) {
			list.add(new OutputPin(element));
		}
		return list;
	}

	public void addStructuredNodeOutput(
			org.modeldriven.alf.uml.OutputPin structuredNodeOutput) {
		this.getBase().addStructuredNodeOutput(
				structuredNodeOutput == null ? null
						: ((OutputPin) structuredNodeOutput).getBase());
	}

	public List<org.modeldriven.alf.uml.InputPin> getStructuredNodeInput() {
		List<org.modeldriven.alf.uml.InputPin> list = new ArrayList<org.modeldriven.alf.uml.InputPin>();
		for (org.eclipse.uml2.uml.InputPin element : this.getBase()
				.getStructuredNodeInput()) {
			list.add(new InputPin(element));
		}
		return list;
	}

	public void addStructuredNodeInput(
			org.modeldriven.alf.uml.InputPin structuredNodeInput) {
		this.getBase().addStructuredNodeInput(
				structuredNodeInput == null ? null
						: ((InputPin) structuredNodeInput).getBase());
	}

}
