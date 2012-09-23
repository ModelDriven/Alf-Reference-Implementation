package org.modeldriven.alf.uml;


public interface TypedElement extends NamedElement {
	public Type getType();

	public void setType(Type type);
}
