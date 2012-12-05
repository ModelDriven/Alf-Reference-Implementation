package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class StructuredActivityNode extends Action implements
		org.modeldriven.alf.uml.StructuredActivityNode {
	public StructuredActivityNode() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE
				.createStructuredActivityNode());
	}

	public StructuredActivityNode(
			org.eclipse.uml2.uml.StructuredActivityNode base) {
		super(base);
	}

	public org.eclipse.uml2.uml.StructuredActivityNode getBase() {
		return (org.eclipse.uml2.uml.StructuredActivityNode) this.base;
	}

	public List<org.modeldriven.alf.uml.ActivityNode> getNode() {
		List<org.modeldriven.alf.uml.ActivityNode> list = new ArrayList<org.modeldriven.alf.uml.ActivityNode>();
		for (org.eclipse.uml2.uml.ActivityNode element : this.getBase()
				.getNodes()) {
			list.add(wrap(element));
		}
		return list;
	}

	public void addNode(org.modeldriven.alf.uml.ActivityNode node) {
		this.getBase().getNodes().add(
				node == null ? null : ((ActivityNode) node).getBase());
	}

	public org.modeldriven.alf.uml.Activity getActivity() {
		return wrap(this.getBase().getActivity());
	}

	public boolean getMustIsolate() {
		return this.getBase().mustIsolate();
	}

	public void setMustIsolate(boolean mustIsolate) {
		this.getBase().setMustIsolate(mustIsolate);
	}

	public List<org.modeldriven.alf.uml.ActivityEdge> getEdge() {
		List<org.modeldriven.alf.uml.ActivityEdge> list = new ArrayList<org.modeldriven.alf.uml.ActivityEdge>();
		for (org.eclipse.uml2.uml.ActivityEdge element : this.getBase()
				.getEdges()) {
			list.add(wrap(element));
		}
		return list;
	}

	public void addEdge(org.modeldriven.alf.uml.ActivityEdge edge) {
		this.getBase().getEdges().add(
				edge == null ? null : ((ActivityEdge) edge).getBase());
	}

	public List<org.modeldriven.alf.uml.OutputPin> getStructuredNodeOutput() {
		List<org.modeldriven.alf.uml.OutputPin> list = new ArrayList<org.modeldriven.alf.uml.OutputPin>();
		for (org.eclipse.uml2.uml.OutputPin element : this.getBase()
				.getStructuredNodeOutputs()) {
			list.add(wrap(element));
		}
		return list;
	}

	public void addStructuredNodeOutput(
			org.modeldriven.alf.uml.OutputPin structuredNodeOutput) {
		this.getBase().getStructuredNodeOutputs().add(
				structuredNodeOutput == null ? null
						: ((OutputPin) structuredNodeOutput).getBase());
	}

	public List<org.modeldriven.alf.uml.InputPin> getStructuredNodeInput() {
		List<org.modeldriven.alf.uml.InputPin> list = new ArrayList<org.modeldriven.alf.uml.InputPin>();
		for (org.eclipse.uml2.uml.InputPin element : this.getBase()
				.getStructuredNodeInputs()) {
			list.add(wrap(element));
		}
		return list;
	}

	public void addStructuredNodeInput(
			org.modeldriven.alf.uml.InputPin structuredNodeInput) {
		this.getBase().getStructuredNodeInputs().add(
				structuredNodeInput == null ? null
						: ((InputPin) structuredNodeInput).getBase());
	}

}
