package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.uml.MultiplicityElement;
import org.modeldriven.uml.Operation;
import org.modeldriven.uml.TypedElement;

public interface Parameter extends TypedElement, MultiplicityElement {
	public String getDirection();

	public void setDirection(String direction);

	public Operation getOperation();
}
