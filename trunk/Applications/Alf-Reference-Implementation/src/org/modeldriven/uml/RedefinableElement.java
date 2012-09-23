package org.modeldriven.uml;

import java.util.List;

public interface RedefinableElement extends NamedElement {
	public boolean getIsLeaf();

	public void setIsLeaf(boolean isLeaf);

	public List<RedefinableElement> getRedefinedElement();

	public List<Classifier> getRedefinitionContext();
}
