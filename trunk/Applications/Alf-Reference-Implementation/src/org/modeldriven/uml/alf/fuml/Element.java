package org.modeldriven.uml.alf.fuml;

import java.util.ArrayList;
import java.util.List;

public class Element implements org.modeldriven.alf.uml.Element {

	protected fUML.Syntax.Classes.Kernel.Element base;

	public Element(fUML.Syntax.Classes.Kernel.Element base) {
		this.base = base;
	}

	public fUML.Syntax.Classes.Kernel.Element getBase() {
		return (fUML.Syntax.Classes.Kernel.Element) this.base;
	}

	public List<org.modeldriven.alf.uml.Element> getOwnedElement() {
		List<org.modeldriven.alf.uml.Element> list = new ArrayList<org.modeldriven.alf.uml.Element>();
		for (fUML.Syntax.Classes.Kernel.Element element : this.getBase().ownedElement) {
			list.add(new Element(element));
		}
		return list;
	}

	public org.modeldriven.alf.uml.Element getOwner() {
		return new Element(this.getBase().owner);
	}

	public List<org.modeldriven.alf.uml.Comment> getOwnedComment() {
		List<org.modeldriven.alf.uml.Comment> list = new ArrayList<org.modeldriven.alf.uml.Comment>();
		for (fUML.Syntax.Classes.Kernel.Comment element : this.getBase().ownedComment) {
			list.add(new Comment(element));
		}
		return list;
	}

	public void addOwnedComment(org.modeldriven.alf.uml.Comment ownedComment) {
		this.getBase().ownedComment.add(((Comment) ownedComment).getBase());
	}

    public String toString(boolean includeDerived) {
        return this.toString();
    }

    public void print(String prefix) {
        System.out.println(prefix + this.toString());
    }
}
