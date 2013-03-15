/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class Comment extends Element implements org.modeldriven.alf.uml.Comment {
	public Comment() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createComment());
	}

	public Comment(org.eclipse.uml2.uml.Comment base) {
		super(base);
	}

	public org.eclipse.uml2.uml.Comment getBase() {
		return (org.eclipse.uml2.uml.Comment) this.base;
	}

	public List<org.modeldriven.alf.uml.Element> getAnnotatedElement() {
		List<org.modeldriven.alf.uml.Element> list = new ArrayList<org.modeldriven.alf.uml.Element>();
		for (org.eclipse.uml2.uml.Element element : this.getBase()
				.getAnnotatedElements()) {
			list.add((org.modeldriven.alf.uml.Element) wrap(element));
		}
		return list;
	}

	public void addAnnotatedElement(
			org.modeldriven.alf.uml.Element annotatedElement) {
		this.getBase().getAnnotatedElements().add(
				annotatedElement == null ? null : ((Element) annotatedElement)
						.getBase());
	}

	public String getBody() {
		return this.getBase().getBody();
	}

	public void setBody(String body) {
		this.getBase().setBody(body);
	}

}
