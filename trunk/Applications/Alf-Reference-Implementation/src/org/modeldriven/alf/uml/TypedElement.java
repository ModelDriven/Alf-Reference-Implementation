package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.NamedElement;
import org.modeldriven.alf.uml.Type;

public interface TypedElement extends NamedElement {
	public Type getType();

	public void setType(Type type);
}
