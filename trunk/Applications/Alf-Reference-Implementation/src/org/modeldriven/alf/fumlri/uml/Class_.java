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

public class Class_ extends BehavioredClassifier implements
		org.modeldriven.alf.uml.Class_ {
	public Class_() {
		this(new fUML.Syntax.Classes.Kernel.Class_());
	}

	public Class_(fUML.Syntax.Classes.Kernel.Class_ base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.Class_ getBase() {
		return (fUML.Syntax.Classes.Kernel.Class_) this.base;
	}

	public boolean getIsAbstract() {
		return this.getBase().isAbstract;
	}

	public void setIsAbstract(boolean isAbstract) {
		this.getBase().setIsAbstract(isAbstract);
	}

	public List<org.modeldriven.alf.uml.Operation> getOwnedOperation() {
		List<org.modeldriven.alf.uml.Operation> list = new ArrayList<org.modeldriven.alf.uml.Operation>();
		for (fUML.Syntax.Classes.Kernel.Operation element : this.getBase().ownedOperation) {
			list.add(new Operation(element));
		}
		return list;
	}

	public void addOwnedOperation(org.modeldriven.alf.uml.Operation ownedOperation) {
		this.getBase()
				.addOwnedOperation(ownedOperation==null? null: ((Operation) ownedOperation).getBase());
	}

	public List<org.modeldriven.alf.uml.Class_> getSuperClass() {
		List<org.modeldriven.alf.uml.Class_> list = new ArrayList<org.modeldriven.alf.uml.Class_>();
		for (fUML.Syntax.Classes.Kernel.Class_ element : this.getBase().superClass) {
			list.add(new Class_(element));
		}
		return list;
	}

	public boolean getIsActive() {
		return this.getBase().isActive;
	}

	public void setIsActive(boolean isActive) {
		this.getBase().setIsActive(isActive);
	}

	public List<org.modeldriven.alf.uml.Reception> getOwnedReception() {
		List<org.modeldriven.alf.uml.Reception> list = new ArrayList<org.modeldriven.alf.uml.Reception>();
		for (fUML.Syntax.CommonBehaviors.Communications.Reception element : this
				.getBase().ownedReception) {
			list.add(new Reception(element));
		}
		return list;
	}

	public void addOwnedReception(org.modeldriven.alf.uml.Reception ownedReception) {
		this.getBase()
				.addOwnedReception(ownedReception==null? null: ((Reception) ownedReception).getBase());
	}

	public List<org.modeldriven.alf.uml.Property> getOwnedAttribute() {
		List<org.modeldriven.alf.uml.Property> list = new ArrayList<org.modeldriven.alf.uml.Property>();
		for (fUML.Syntax.Classes.Kernel.Property element : this.getBase().ownedAttribute) {
			list.add(new Property(element));
		}
		return list;
	}

	public void addOwnedAttribute(org.modeldriven.alf.uml.Property ownedAttribute) {
		this.getBase().addOwnedAttribute(ownedAttribute==null? null: ((Property) ownedAttribute).getBase());
	}

	public List<org.modeldriven.alf.uml.Classifier> getNestedClassifier() {
		List<org.modeldriven.alf.uml.Classifier> list = new ArrayList<org.modeldriven.alf.uml.Classifier>();
		for (fUML.Syntax.Classes.Kernel.Classifier element : this.getBase().nestedClassifier) {
			list.add((Classifier)this.wrap(element));
		}
		return list;
	}

	public void addNestedClassifier(
			org.modeldriven.alf.uml.Classifier nestedClassifier) {
		this.getBase().addNestedClassifier(
				((Classifier) nestedClassifier).getBase());
	}

}
