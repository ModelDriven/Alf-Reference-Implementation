package org.modeldriven.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.ActivityEdge;
import org.modeldriven.uml.fuml.ActivityNode;
import org.modeldriven.uml.fuml.Behavior;
import org.modeldriven.uml.fuml.StructuredActivityNode;

public class Activity extends Behavior implements org.modeldriven.alf.uml.Activity {
	public Activity() {
		this(new fUML.Syntax.Activities.IntermediateActivities.Activity());
	}

	public Activity(fUML.Syntax.Activities.IntermediateActivities.Activity base) {
		super(base);
	}

	public fUML.Syntax.Activities.IntermediateActivities.Activity getBase() {
		return (fUML.Syntax.Activities.IntermediateActivities.Activity) this.base;
	}

	public List<org.modeldriven.alf.uml.StructuredActivityNode> getStructuredNode() {
		List<org.modeldriven.alf.uml.StructuredActivityNode> list = new ArrayList<org.modeldriven.alf.uml.StructuredActivityNode>();
		for (fUML.Syntax.Activities.CompleteStructuredActivities.StructuredActivityNode element : this
				.getBase().structuredNode) {
			list.add(new StructuredActivityNode(element));
		}
		return list;
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

	public boolean getIsReadOnly() {
		return this.getBase().isReadOnly;
	}

	public void setIsReadOnly(boolean isReadOnly) {
		this.getBase().setIsReadOnly(isReadOnly);
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

}
