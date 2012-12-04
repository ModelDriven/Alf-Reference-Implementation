package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class Comment implements org.modeldriven.alf.uml.Comment {
	public Comment() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createComment());
	}

	protected fUML.Syntax.Classes.Kernel.Comment base;

	public Comment(org.eclipse.uml2.uml.Comment base) {
		this.base = base;
	}

	public org.eclipse.uml2.uml.Comment getBase() {
		return (org.eclipse.uml2.uml.Comment) this.base;
	}

	public List< org.modeldriven.alf.uml.Element> getAnnotatedElement
() {
		List< org.modeldriven.alf.uml.Element> list = new ArrayList< org.modeldriven.alf.uml.Element>();
		for (org.eclipse.uml2.uml.Element
 element: this.getBase().getAnnotatedElement
s()) {
			list.add( new Element(element)
);
		}
		return list;
	}

	public void addAnnotatedElement
( org.modeldriven.alf.uml.Element annotatedElement) {
		this.getBase().getAnnotatedElement
s.add( annotatedElement == null? null: ((Element)annotatedElement).getBase()
);
	}

	public String getBody() {
		return this.getBase().getBody();
	}

	public void setBody(String body) {
		this.getBase().setBody(body);
	}

}
