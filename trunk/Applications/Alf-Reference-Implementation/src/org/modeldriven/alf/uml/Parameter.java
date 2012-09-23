package org.modeldriven.alf.uml;


public interface Parameter extends TypedElement, MultiplicityElement {
	public String getDirection();

	public void setDirection(String direction);

	public Operation getOperation();
}
