/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.impl.uml;

import java.util.ArrayList;
import java.util.List;

public class InstanceSpecification extends NamedElement implements
		org.modeldriven.alf.uml.InstanceSpecification {
	public InstanceSpecification() {
		this(new fUML.Syntax.Classes.Kernel.InstanceSpecification());
	}

	public InstanceSpecification(
			fUML.Syntax.Classes.Kernel.InstanceSpecification base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.InstanceSpecification getBase() {
		return (fUML.Syntax.Classes.Kernel.InstanceSpecification) this.base;
	}

	public List<org.modeldriven.alf.uml.Classifier> getClassifier() {
		List<org.modeldriven.alf.uml.Classifier> list = new ArrayList<org.modeldriven.alf.uml.Classifier>();
		for (fUML.Syntax.Classes.Kernel.Classifier element : this.getBase().classifier) {
			list.add((Classifier)this.wrap(element));
		}
		return list;
	}

	public void addClassifier(org.modeldriven.alf.uml.Classifier classifier) {
		this.getBase().addClassifier(classifier==null? null: ((Classifier) classifier).getBase());
	}

	public List<org.modeldriven.alf.uml.Slot> getSlot() {
		List<org.modeldriven.alf.uml.Slot> list = new ArrayList<org.modeldriven.alf.uml.Slot>();
		for (fUML.Syntax.Classes.Kernel.Slot element : this.getBase().slot) {
			list.add(new Slot(element));
		}
		return list;
	}

	public void addSlot(org.modeldriven.alf.uml.Slot slot) {
		this.getBase().addSlot(slot==null? null: ((Slot) slot).getBase());
	}

}
