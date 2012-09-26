/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fumlri.uml;

import java.util.ArrayList;
import java.util.List;

public abstract class Behavior extends Class_ implements org.modeldriven.alf.uml.Behavior {

	public Behavior(fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior base) {
		super(base);
	}

	public fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior getBase() {
		return (fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior) this.base;
	}

	public boolean getIsReentrant() {
		return this.getBase().isReentrant;
	}

	public void setIsReentrant(boolean isReentrant) {
		this.getBase().isReentrant = isReentrant;
	}

	public org.modeldriven.alf.uml.BehavioralFeature getSpecification() {
		return (BehavioralFeature)this.wrap(this.getBase().specification);
	}

	public void setSpecification(
			org.modeldriven.alf.uml.BehavioralFeature specification) {
		specification.addMethod(this);
	}

	public List<org.modeldriven.alf.uml.Parameter> getOwnedParameter() {
		List<org.modeldriven.alf.uml.Parameter> list = new ArrayList<org.modeldriven.alf.uml.Parameter>();
		for (fUML.Syntax.Classes.Kernel.Parameter element : this.getBase().ownedParameter) {
			list.add(new Parameter(element));
		}
		return list;
	}

	public void addOwnedParameter(org.modeldriven.alf.uml.Parameter ownedParameter) {
		this.getBase()
				.addOwnedParameter(ownedParameter==null? null: ((Parameter) ownedParameter).getBase());
	}

	public org.modeldriven.alf.uml.BehavioredClassifier getContext() {
		return (BehavioredClassifier)this.wrap(this.getBase().context);
	}

}
