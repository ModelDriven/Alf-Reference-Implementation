package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class RedefinableElement extends NamedElement implements
		org.modeldriven.alf.uml.RedefinableElement {

	public RedefinableElement(fUML.Syntax.Classes.Kernel.RedefinableElement base) {
		super(base);
	}

	public org.eclipse.uml2.uml.RedefinableElement getBase() {
		return (org.eclipse.uml2.uml.RedefinableElement) this.base;
	}

	public boolean getIsLeaf() {
		return this.getBase().getIsLeaf();
	}

	public void setIsLeaf(boolean isLeaf) {
		this.getBase().setIsLeaf(isLeaf);
	}

	public List<org.modeldriven.alf.uml.RedefinableElement> getRedefinedElement() {
		List<org.modeldriven.alf.uml.RedefinableElement> list = new ArrayList<org.modeldriven.alf.uml.RedefinableElement>();
		for (org.eclipse.uml2.uml.RedefinableElement element : this.getBase()
				.getRedefinedElement()) {
			list.add(new RedefinableElement(element));
		}
		return list;
	}

	public List<org.modeldriven.alf.uml.Classifier> getRedefinitionContext() {
		List<org.modeldriven.alf.uml.Classifier> list = new ArrayList<org.modeldriven.alf.uml.Classifier>();
		for (org.eclipse.uml2.uml.Classifier element : this.getBase()
				.getRedefinitionContext()) {
			list.add(new Classifier(element));
		}
		return list;
	}

}
