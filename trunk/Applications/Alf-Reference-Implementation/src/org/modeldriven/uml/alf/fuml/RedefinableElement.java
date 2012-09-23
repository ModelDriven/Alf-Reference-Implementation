package org.modeldriven.uml.alf.fuml;

import java.util.ArrayList;
import java.util.List;

public class RedefinableElement extends NamedElement implements
		org.modeldriven.alf.uml.RedefinableElement {

	public RedefinableElement(fUML.Syntax.Classes.Kernel.RedefinableElement base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.RedefinableElement getBase() {
		return (fUML.Syntax.Classes.Kernel.RedefinableElement) this.base;
	}

	public boolean getIsLeaf() {
		return this.getBase().isLeaf;
	}

	public void setIsLeaf(boolean isLeaf) {
		this.getBase().setIsLeaf(isLeaf);
	}

	public List<org.modeldriven.alf.uml.RedefinableElement> getRedefinedElement() {
		List<org.modeldriven.alf.uml.RedefinableElement> list = new ArrayList<org.modeldriven.alf.uml.RedefinableElement>();
		for (fUML.Syntax.Classes.Kernel.RedefinableElement element : this
				.getBase().redefinedElement) {
			list.add(new RedefinableElement(element));
		}
		return list;
	}

	public List<org.modeldriven.alf.uml.Classifier> getRedefinitionContext() {
		List<org.modeldriven.alf.uml.Classifier> list = new ArrayList<org.modeldriven.alf.uml.Classifier>();
		for (fUML.Syntax.Classes.Kernel.Classifier element : this.getBase().redefinitionContext) {
			list.add(new Classifier(element));
		}
		return list;
	}

}
