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

public abstract class BehavioredClassifier extends Classifier implements
		org.modeldriven.alf.uml.BehavioredClassifier {

	public BehavioredClassifier(
			fUML.Syntax.CommonBehaviors.BasicBehaviors.BehavioredClassifier base) {
		super(base);
	}

	public fUML.Syntax.CommonBehaviors.BasicBehaviors.BehavioredClassifier getBase() {
		return (fUML.Syntax.CommonBehaviors.BasicBehaviors.BehavioredClassifier) this.base;
	}

	public List<org.modeldriven.alf.uml.Behavior> getOwnedBehavior() {
		List<org.modeldriven.alf.uml.Behavior> list = new ArrayList<org.modeldriven.alf.uml.Behavior>();
		for (fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior element : this
				.getBase().ownedBehavior) {
			list.add((Behavior)this.wrap(element));
		}
		return list;
	}

	public void addOwnedBehavior(org.modeldriven.alf.uml.Behavior ownedBehavior) {
		this.getBase().addOwnedBehavior(((Behavior) ownedBehavior).getBase());
	}

	public org.modeldriven.alf.uml.Behavior getClassifierBehavior() {
		return (Behavior)this.wrap(this.getBase().classifierBehavior);
	}

	public void setClassifierBehavior(
			org.modeldriven.alf.uml.Behavior classifierBehavior) {
		this.getBase().setClassifierBehavior(
				((Behavior) classifierBehavior).getBase());
	}

}
