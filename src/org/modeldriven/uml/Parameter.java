package org.modeldriven.uml;

import java.util.List;

public interface Parameter extends TypedElement, MultiplicityElement {
	public String getDirection();

	public void setDirection(String direction);

	public Operation getOperation();
}
