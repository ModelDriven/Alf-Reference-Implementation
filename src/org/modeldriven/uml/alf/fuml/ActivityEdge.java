package org.modeldriven.uml.alf.fuml;


public class ActivityEdge extends RedefinableElement implements
		org.modeldriven.alf.uml.ActivityEdge {

	public ActivityEdge(
			fUML.Syntax.Activities.IntermediateActivities.ActivityEdge base) {
		super(base);
	}

	public fUML.Syntax.Activities.IntermediateActivities.ActivityEdge getBase() {
		return (fUML.Syntax.Activities.IntermediateActivities.ActivityEdge) this.base;
	}

	public org.modeldriven.alf.uml.Activity getActivity() {
		return new Activity(this.getBase().activity);
	}

	public org.modeldriven.alf.uml.ActivityNode getSource() {
		return new ActivityNode(this.getBase().source);
	}

	public void setSource(org.modeldriven.alf.uml.ActivityNode source) {
		this.getBase().setSource(((ActivityNode) source).getBase());
	}

	public org.modeldriven.alf.uml.ActivityNode getTarget() {
		return new ActivityNode(this.getBase().target);
	}

	public void setTarget(org.modeldriven.alf.uml.ActivityNode target) {
		this.getBase().setTarget(((ActivityNode) target).getBase());
	}

	public org.modeldriven.alf.uml.ValueSpecification getGuard() {
		return new ValueSpecification(this.getBase().guard);
	}

	public void setGuard(org.modeldriven.alf.uml.ValueSpecification guard) {
		this.getBase().setGuard(((ValueSpecification) guard).getBase());
	}

	public org.modeldriven.alf.uml.StructuredActivityNode getInStructuredNode() {
		return new StructuredActivityNode(this.getBase().inStructuredNode);
	}

}
