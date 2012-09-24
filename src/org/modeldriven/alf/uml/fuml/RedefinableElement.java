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

public abstract class RedefinableElement extends NamedElement implements
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
			list.add((RedefinableElement)this.wrap(element));
		}
		return list;
	}

	public List<org.modeldriven.alf.uml.Classifier> getRedefinitionContext() {
		List<org.modeldriven.alf.uml.Classifier> list = new ArrayList<org.modeldriven.alf.uml.Classifier>();
		for (fUML.Syntax.Classes.Kernel.Classifier element : this.getBase().redefinitionContext) {
			list.add((Classifier)this.wrap(element));
		}
		return list;
	}

}
