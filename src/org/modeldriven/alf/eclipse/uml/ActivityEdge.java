package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class ActivityEdge extends RedefinableElement implements
		org.modeldriven.alf.uml.ActivityEdge {

	public ActivityEdge(
			fUML.Syntax.Activities.IntermediateActivities.ActivityEdge base) {
		super(base);
	}

	public org.eclipse.uml2.uml.ActivityEdge getBase() {
		return (org.eclipse.uml2.uml.ActivityEdge) this.base;
	}

	public org.modeldriven.alf.uml.Activity getActivity() {
		return new Activity(this.getBase().getActivity());
	}

	public org.modeldriven.alf.uml.ActivityNode getSource() {
		return new ActivityNode(this.getBase().getSource());
	}

	public void setSource(org.modeldriven.alf.uml.ActivityNode source) {
		this.getBase().setSource(
				source == null ? null : ((ActivityNode) source).getBase());
	}

	public org.modeldriven.alf.uml.ActivityNode getTarget() {
		return new ActivityNode(this.getBase().getTarget());
	}

	public void setTarget(org.modeldriven.alf.uml.ActivityNode target) {
		this.getBase().setTarget(
				target == null ? null : ((ActivityNode) target).getBase());
	}

	public org.modeldriven.alf.uml.ValueSpecification getGuard() {
		return new ValueSpecification(this.getBase().getGuard());
	}

	public void setGuard(org.modeldriven.alf.uml.ValueSpecification guard) {
		this.getBase().setGuard(
				guard == null ? null : ((ValueSpecification) guard).getBase());
	}

	public org.modeldriven.alf.uml.StructuredActivityNode getInStructuredNode() {
		return new StructuredActivityNode(this.getBase().getInStructuredNode());
	}

}
