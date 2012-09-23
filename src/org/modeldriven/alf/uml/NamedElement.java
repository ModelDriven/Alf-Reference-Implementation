package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.uml.Element;
import org.modeldriven.uml.NamedElement;
import org.modeldriven.uml.Namespace;

public interface NamedElement extends Element {
	public String getName();

	public void setName(String name);

	public String getVisibility();

	public void setVisibility(String visibility);

	public String getQualifiedName();

	public Namespace getNamespace();

    public boolean isDistinguishableFrom(NamedElement otherElement, Namespace namespace);
}
