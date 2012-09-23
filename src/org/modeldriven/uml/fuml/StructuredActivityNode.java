package org.modeldriven.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.Action;
import org.modeldriven.uml.fuml.Activity;
import org.modeldriven.uml.fuml.ActivityEdge;
import org.modeldriven.uml.fuml.ActivityNode;
import org.modeldriven.uml.fuml.InputPin;
import org.modeldriven.uml.fuml.OutputPin;

public class StructuredActivityNode extends Action implements
		org.modeldriven.alf.uml.StructuredActivityNode {
	public StructuredActivityNode() {
		this(
				new fUML.Syntax.Activities.CompleteStructuredActivities.StructuredActivityNode());
	}

	public StructuredActivityNode(
			fUML.Syntax.Activities.CompleteStructuredActivities.StructuredActivityNode base) {
		super(base);
	}

	public fUML.Syntax.Activities.CompleteStructuredActivities.StructuredActivityNode getBase() {
		return (fUML.Syntax.Activities.CompleteStructuredActivities.StructuredActivityNode) this.base;
	}

	public List<org.modeldriven.alf.uml.ActivityNode> getNode() {
		List<org.modeldriven.alf.uml.ActivityNode> list = new ArrayList<org.modeldriven.alf.uml.ActivityNode>();
		for (fUML.Syntax.Activities.IntermediateActivities.ActivityNode element : this
				.getBase().node) {
			list.add(new ActivityNode(element));
		}
		return list;
	}

	public void addNode(org.modeldriven.alf.uml.ActivityNode node) {
		this.getBase().addNode(((ActivityNode) node).getBase());
	}

	public org.modeldriven.alf.uml.Activity getActivity() {
		return new Activity(this.getBase().activity);
	}

	public boolean getMustIsolate() {
		return this.getBase().mustIsolate;
	}

	public void setMustIsolate(boolean mustIsolate) {
		this.getBase().setMustIsolate(mustIsolate);
	}

	public List<org.modeldriven.alf.uml.ActivityEdge> getEdge() {
		List<org.modeldriven.alf.uml.ActivityEdge> list = new ArrayList<org.modeldriven.alf.uml.ActivityEdge>();
		for (fUML.Syntax.Activities.IntermediateActivities.ActivityEdge element : this
				.getBase().edge) {
			list.add(new ActivityEdge(element));
		}
		return list;
	}

	public void addEdge(org.modeldriven.alf.uml.ActivityEdge edge) {
		this.getBase().addEdge(((ActivityEdge) edge).getBase());
	}

	public List<org.modeldriven.alf.uml.OutputPin> getStructuredNodeOutput() {
		List<org.modeldriven.alf.uml.OutputPin> list = new ArrayList<org.modeldriven.alf.uml.OutputPin>();
		for (fUML.Syntax.Actions.BasicActions.OutputPin element : this
				.getBase().structuredNodeOutput) {
			list.add(new OutputPin(element));
		}
		return list;
	}

	public void addStructuredNodeOutput(
			org.modeldriven.alf.uml.OutputPin structuredNodeOutput) {
		this.getBase().addStructuredNodeOutput(
				((OutputPin) structuredNodeOutput).getBase());
	}

	public List<org.modeldriven.alf.uml.InputPin> getStructuredNodeInput() {
		List<org.modeldriven.alf.uml.InputPin> list = new ArrayList<org.modeldriven.alf.uml.InputPin>();
		for (fUML.Syntax.Actions.BasicActions.InputPin element : this.getBase().structuredNodeInput) {
			list.add(new InputPin(element));
		}
		return list;
	}

	public void addStructuredNodeInput(
			org.modeldriven.alf.uml.InputPin structuredNodeInput) {
		this.getBase().addStructuredNodeInput(
				((InputPin) structuredNodeInput).getBase());
	}

}
