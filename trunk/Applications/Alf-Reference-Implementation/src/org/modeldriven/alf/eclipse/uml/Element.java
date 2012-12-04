package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class Element implements org.modeldriven.alf.uml.Element {

	protected fUML.Syntax.Classes.Kernel.Element base;

	public Element(org.eclipse.uml2.uml.Element base) {
		this.base = base;
	}

	public org.eclipse.uml2.uml.Element getBase() {
		return (org.eclipse.uml2.uml.Element) this.base;
	}

	public List< org.modeldriven.alf.uml.Element> getOwnedElement
() {
		List< org.modeldriven.alf.uml.Element> list = new ArrayList< org.modeldriven.alf.uml.Element>();
		for (org.eclipse.uml2.uml.Element
 element: this.getBase().getOwnedElement
s()) {
			list.add( new Element(element)
);
		}
		return list;
	}

	public org.modeldriven.alf.uml.Element getOwner() {
		return new Element(this.getBase().getOwner());
	}

	public List< org.modeldriven.alf.uml.Comment> getOwnedComment
() {
		List< org.modeldriven.alf.uml.Comment> list = new ArrayList< org.modeldriven.alf.uml.Comment>();
		for (org.eclipse.uml2.uml.Comment
 element: this.getBase().getOwnedComment
s()) {
			list.add( new Comment(element)
);
		}
		return list;
	}

	public void addOwnedComment
( org.modeldriven.alf.uml.Comment ownedComment) {
		this.getBase().getOwnedComment
s.add( ownedComment == null? null: ((Comment)ownedComment).getBase()
);
	}

}
