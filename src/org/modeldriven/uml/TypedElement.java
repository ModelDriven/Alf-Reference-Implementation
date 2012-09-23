package org.modeldriven.uml;

import java.util.List;

public interface TypedElement extends NamedElement {
	public Type getType();

	public void setType(Type type);
}
