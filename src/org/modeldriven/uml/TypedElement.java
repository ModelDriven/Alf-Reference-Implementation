package org.modeldriven.uml;

import java.util.List;

import org.modeldriven.uml.NamedElement;
import org.modeldriven.uml.Type;

public interface TypedElement extends NamedElement {
	public Type getType();

	public void setType(Type type);
}
