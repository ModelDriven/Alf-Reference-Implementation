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

public class BehavioredClassifier extends Classifier implements
		org.modeldriven.alf.uml.BehavioredClassifier {

	public BehavioredClassifier(org.eclipse.uml2.uml.BehavioredClassifier base) {
		super(base);
	}

	public org.eclipse.uml2.uml.BehavioredClassifier getBase() {
		return (org.eclipse.uml2.uml.BehavioredClassifier) this.base;
	}

	public List<org.modeldriven.alf.uml.Behavior> getOwnedBehavior() {
		List<org.modeldriven.alf.uml.Behavior> list = new ArrayList<org.modeldriven.alf.uml.Behavior>();
		for (org.eclipse.uml2.uml.Behavior element : this.getBase()
				.getOwnedBehaviors()) {
			list.add((org.modeldriven.alf.uml.Behavior) wrap(element));
		}
		return list;
	}

	public void addOwnedBehavior(org.modeldriven.alf.uml.Behavior ownedBehavior) {
		this.getBase().getOwnedBehaviors().add(
				ownedBehavior == null ? null : ((Behavior) ownedBehavior)
						.getBase());
	}

	public org.modeldriven.alf.uml.Behavior getClassifierBehavior() {
		return (org.modeldriven.alf.uml.Behavior) wrap(this.getBase()
				.getClassifierBehavior());
	}

	public void setClassifierBehavior(
			org.modeldriven.alf.uml.Behavior classifierBehavior) {
		this.getBase().setClassifierBehavior(
				classifierBehavior == null ? null
						: ((Behavior) classifierBehavior).getBase());
	}

}
