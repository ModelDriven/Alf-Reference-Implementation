package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.InputPin;
import org.modeldriven.uml.Element;
import org.modeldriven.uml.Property;

public interface LinkEndData extends Element {
	public InputPin getValue();

	public void setValue(InputPin value);

	public Property getEnd();

	public void setEnd(Property end);
}
