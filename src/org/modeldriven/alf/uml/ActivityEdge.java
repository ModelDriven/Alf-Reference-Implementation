package org.modeldriven.alf.uml;

public interface ActivityEdge extends RedefinableElement {
	public Activity getActivity();

	public ActivityNode getSource();

	public void setSource(ActivityNode source);

	public ActivityNode getTarget();

	public void setTarget(ActivityNode target);

	public ValueSpecification getGuard();

	public void setGuard(ValueSpecification guard);

	public StructuredActivityNode getInStructuredNode();
}
