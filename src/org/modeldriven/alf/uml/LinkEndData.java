package org.modeldriven.alf.uml;


public interface LinkEndData extends Element {
	public InputPin getValue();

	public void setValue(InputPin value);

	public Property getEnd();

	public void setEnd(Property end);
}
