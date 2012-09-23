package org.modeldriven.alf.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.Element;

public class Comment implements org.modeldriven.alf.uml.Comment {
	public Comment() {
		this(new fUML.Syntax.Classes.Kernel.Comment());
	}

	protected fUML.Syntax.Classes.Kernel.Comment base;

	public Comment(fUML.Syntax.Classes.Kernel.Comment base) {
		this.base = base;
	}

	public fUML.Syntax.Classes.Kernel.Comment getBase() {
		return (fUML.Syntax.Classes.Kernel.Comment) this.base;
	}

	public List<org.modeldriven.alf.uml.Element> getAnnotatedElement() {
		List<org.modeldriven.alf.uml.Element> list = new ArrayList<org.modeldriven.alf.uml.Element>();
		for (fUML.Syntax.Classes.Kernel.Element element : this.getBase().annotatedElement) {
			list.add(new Element(element));
		}
		return list;
	}

	public void addAnnotatedElement(org.modeldriven.alf.uml.Element annotatedElement) {
		this.getBase().annotatedElement.add(((Element) annotatedElement).getBase());
	}

	public String getBody() {
		return this.getBase().body;
	}

	public void setBody(String body) {
		this.getBase().body = body;
	}

}
