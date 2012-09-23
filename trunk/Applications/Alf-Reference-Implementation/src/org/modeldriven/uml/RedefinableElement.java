package org.modeldriven.uml;

import java.util.List;

import org.modeldriven.uml.Classifier;
import org.modeldriven.uml.NamedElement;
import org.modeldriven.uml.RedefinableElement;

public interface RedefinableElement extends NamedElement {
	public boolean getIsLeaf();

	public void setIsLeaf(boolean isLeaf);

	public List<RedefinableElement> getRedefinedElement();

	public List<Classifier> getRedefinitionContext();
}
