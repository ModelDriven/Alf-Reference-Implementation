package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.Classifier;
import org.modeldriven.alf.uml.NamedElement;
import org.modeldriven.alf.uml.RedefinableElement;

public interface RedefinableElement extends NamedElement {
	public boolean getIsLeaf();

	public void setIsLeaf(boolean isLeaf);

	public List<RedefinableElement> getRedefinedElement();

	public List<Classifier> getRedefinitionContext();
}
