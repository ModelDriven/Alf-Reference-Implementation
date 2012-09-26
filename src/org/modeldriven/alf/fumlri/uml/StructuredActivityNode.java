/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fumlri.uml;

import java.util.ArrayList;
import java.util.List;

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
			list.add((ActivityNode)this.wrap(element));
		}
		return list;
	}

	public void addNode(org.modeldriven.alf.uml.ActivityNode node) {
		this.getBase().addNode(node==null? null: ((ActivityNode) node).getBase());
	}

	public org.modeldriven.alf.uml.Activity getActivity() {
		return (Activity)this.wrap(this.getBase().activity);
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
			list.add((ActivityEdge)this.wrap(element));
		}
		return list;
	}

	public void addEdge(org.modeldriven.alf.uml.ActivityEdge edge) {
		this.getBase().addEdge(edge==null? null: ((ActivityEdge) edge).getBase());
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
