package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.MultiplicityElement;
import org.modeldriven.alf.uml.Operation;
import org.modeldriven.alf.uml.TypedElement;

public interface Parameter extends TypedElement, MultiplicityElement {
	public String getDirection();

	public void setDirection(String direction);

	public Operation getOperation();
}
