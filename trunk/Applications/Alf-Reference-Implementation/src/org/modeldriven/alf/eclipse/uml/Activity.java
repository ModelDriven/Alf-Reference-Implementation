package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class Activity extends Behavior implements
		org.modeldriven.alf.uml.Activity {
	public Activity() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createActivity());
	}

	public Activity(org.eclipse.uml2.uml.Activity base) {
		super(base);
	}

	public org.eclipse.uml2.uml.Activity getBase() {
		return (org.eclipse.uml2.uml.Activity) this.base;
	}

	public List<org.modeldriven.alf.uml.StructuredActivityNode> getStructuredNode() {
		List<org.modeldriven.alf.uml.StructuredActivityNode> list = new ArrayList<org.modeldriven.alf.uml.StructuredActivityNode>();
		for (org.eclipse.uml2.uml.StructuredActivityNode element : this
				.getBase().getStructuredNodes()) {
			list.add(new StructuredActivityNode(element));
		}
		return list;
	}

	public List<org.modeldriven.alf.uml.ActivityNode> getNode() {
		List<org.modeldriven.alf.uml.ActivityNode> list = new ArrayList<org.modeldriven.alf.uml.ActivityNode>();
		for (org.eclipse.uml2.uml.ActivityNode element : this.getBase()
				.getNodes()) {
			list.add(new ActivityNode(element));
		}
		return list;
	}

	public void addNode(org.modeldriven.alf.uml.ActivityNode node) {
		this.getBase().getNodes().add(
				node == null ? null : ((ActivityNode) node).getBase());
	}

	public boolean getIsReadOnly() {
		return this.getBase().isReadOnly();
	}

	public void setIsReadOnly(boolean isReadOnly) {
		this.getBase().setIsReadOnly(isReadOnly);
	}

	public List<org.modeldriven.alf.uml.ActivityEdge> getEdge() {
		List<org.modeldriven.alf.uml.ActivityEdge> list = new ArrayList<org.modeldriven.alf.uml.ActivityEdge>();
		for (org.eclipse.uml2.uml.ActivityEdge element : this.getBase()
				.getEdges()) {
			list.add(new ActivityEdge(element));
		}
		return list;
	}

	public void addEdge(org.modeldriven.alf.uml.ActivityEdge edge) {
		this.getBase().getEdges().add(
				edge == null ? null : ((ActivityEdge) edge).getBase());
	}

}
