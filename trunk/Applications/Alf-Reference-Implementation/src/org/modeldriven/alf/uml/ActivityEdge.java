package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.ActivityNode;
import org.modeldriven.alf.uml.StructuredActivityNode;
import org.modeldriven.alf.uml.ValueSpecification;
import org.modeldriven.uml.Activity;
import org.modeldriven.uml.RedefinableElement;

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
