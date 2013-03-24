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

public class Signal extends Classifier implements
		org.modeldriven.alf.uml.Signal {
	public Signal() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createSignal());
	}

	public Signal(org.eclipse.uml2.uml.Signal base) {
		super(base);
	}

	public org.eclipse.uml2.uml.Signal getBase() {
		return (org.eclipse.uml2.uml.Signal) this.base;
	}

	public List<org.modeldriven.alf.uml.Property> getOwnedAttribute() {
		List<org.modeldriven.alf.uml.Property> list = new ArrayList<org.modeldriven.alf.uml.Property>();
		for (org.eclipse.uml2.uml.Property element : this.getBase()
				.getOwnedAttributes()) {
			list.add((org.modeldriven.alf.uml.Property) wrap(element));
		}
		return list;
	}

	public void addOwnedAttribute(
			org.modeldriven.alf.uml.Property ownedAttribute) {
		this.getBase().getOwnedAttributes().add(
				ownedAttribute == null ? null : ((Property) ownedAttribute)
						.getBase());
	}

}
