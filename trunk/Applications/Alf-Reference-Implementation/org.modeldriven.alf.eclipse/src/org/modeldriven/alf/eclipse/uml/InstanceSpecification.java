/*******************************************************************************
 * Copyright 2011-2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class InstanceSpecification extends PackageableElement implements
		org.modeldriven.alf.uml.InstanceSpecification {
	public InstanceSpecification() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE
				.createInstanceSpecification());
	}

	public InstanceSpecification(org.eclipse.uml2.uml.InstanceSpecification base) {
		super(base);
	}

	public org.eclipse.uml2.uml.InstanceSpecification getBase() {
		return (org.eclipse.uml2.uml.InstanceSpecification) this.base;
	}

	public List<org.modeldriven.alf.uml.Classifier> getClassifier() {
		List<org.modeldriven.alf.uml.Classifier> list = new ArrayList<org.modeldriven.alf.uml.Classifier>();
		for (org.eclipse.uml2.uml.Classifier element : this.getBase()
				.getClassifiers()) {
			list.add((org.modeldriven.alf.uml.Classifier) wrap(element));
		}
		return list;
	}

	public void addClassifier(org.modeldriven.alf.uml.Classifier classifier) {
		this.getBase().getClassifiers()
				.add(
						classifier == null ? null : ((Classifier) classifier)
								.getBase());
	}

	public List<org.modeldriven.alf.uml.Slot> getSlot() {
		List<org.modeldriven.alf.uml.Slot> list = new ArrayList<org.modeldriven.alf.uml.Slot>();
		for (org.eclipse.uml2.uml.Slot element : this.getBase().getSlots()) {
			list.add((org.modeldriven.alf.uml.Slot) wrap(element));
		}
		return list;
	}

	public void addSlot(org.modeldriven.alf.uml.Slot slot) {
		this.getBase().getSlots().add(
				slot == null ? null : ((Slot) slot).getBase());
	}

}
