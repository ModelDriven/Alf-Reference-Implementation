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

public class RedefinableElement extends NamedElement implements
		org.modeldriven.alf.uml.RedefinableElement {

	public RedefinableElement(org.eclipse.uml2.uml.RedefinableElement base) {
		super(base);
	}

	public org.eclipse.uml2.uml.RedefinableElement getBase() {
		return (org.eclipse.uml2.uml.RedefinableElement) this.base;
	}

	public boolean getIsLeaf() {
		return this.getBase().isLeaf();
	}

	public void setIsLeaf(boolean isLeaf) {
		this.getBase().setIsLeaf(isLeaf);
	}

	public List<org.modeldriven.alf.uml.RedefinableElement> getRedefinedElement() {
		List<org.modeldriven.alf.uml.RedefinableElement> list = new ArrayList<org.modeldriven.alf.uml.RedefinableElement>();
		for (org.eclipse.uml2.uml.RedefinableElement element : this.getBase()
				.getRedefinedElements()) {
			list
					.add((org.modeldriven.alf.uml.RedefinableElement) wrap(element));
		}
		return list;
	}

	public List<org.modeldriven.alf.uml.Classifier> getRedefinitionContext() {
		List<org.modeldriven.alf.uml.Classifier> list = new ArrayList<org.modeldriven.alf.uml.Classifier>();
		for (org.eclipse.uml2.uml.Classifier element : this.getBase()
				.getRedefinitionContexts()) {
			list.add((org.modeldriven.alf.uml.Classifier) wrap(element));
		}
		return list;
	}

}
