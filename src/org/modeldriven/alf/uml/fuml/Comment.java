/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.uml.fuml;

import java.util.ArrayList;
import java.util.List;

public class Comment extends Element implements org.modeldriven.alf.uml.Comment {
	public Comment() {
		this(new fUML.Syntax.Classes.Kernel.Comment());
	}

	public Comment(fUML.Syntax.Classes.Kernel.Comment base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.Comment getBase() {
		return (fUML.Syntax.Classes.Kernel.Comment) this.base;
	}

	public List<org.modeldriven.alf.uml.Element> getAnnotatedElement() {
		List<org.modeldriven.alf.uml.Element> list = new ArrayList<org.modeldriven.alf.uml.Element>();
		for (fUML.Syntax.Classes.Kernel.Element element : this.getBase().annotatedElement) {
			list.add((ExecutableNode)this.wrap(element));
		}
		return list;
	}

	public void addAnnotatedElement(org.modeldriven.alf.uml.Element annotatedElement) {
		this.getBase().annotatedElement.add(annotatedElement==null? null: ((Element) annotatedElement).getBaseAsElement());
	}

	public String getBody() {
		return this.getBase().body;
	}

	public void setBody(String body) {
		this.getBase().body = body;
	}

}
