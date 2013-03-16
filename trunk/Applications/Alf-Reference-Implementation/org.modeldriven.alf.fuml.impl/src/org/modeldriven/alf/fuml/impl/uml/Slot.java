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

public class Slot extends Element implements org.modeldriven.alf.uml.Slot {
	public Slot() {
		this(new fUML.Syntax.Classes.Kernel.Slot());
	}

	public Slot(fUML.Syntax.Classes.Kernel.Slot base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.Slot getBase() {
		return (fUML.Syntax.Classes.Kernel.Slot) this.base;
	}

	public org.modeldriven.alf.uml.InstanceSpecification getOwningInstance() {
		return (InstanceSpecification)this.wrap(this.getBase().owningInstance);
	}

	public org.modeldriven.alf.uml.StructuralFeature getDefiningFeature() {
		return (StructuralFeature)this.wrap(this.getBase().definingFeature);
	}

	public void setDefiningFeature(
			org.modeldriven.alf.uml.StructuralFeature definingFeature) {
		this.getBase().setDefiningFeature(
				((StructuralFeature) definingFeature).getBase());
	}

	public List<org.modeldriven.alf.uml.ValueSpecification> getValue() {
		List<org.modeldriven.alf.uml.ValueSpecification> list = new ArrayList<org.modeldriven.alf.uml.ValueSpecification>();
		for (fUML.Syntax.Classes.Kernel.ValueSpecification element : this
				.getBase().value) {
			list.add((ValueSpecification)this.wrap(element));
		}
		return list;
	}

	public void addValue(org.modeldriven.alf.uml.ValueSpecification value) {
		this.getBase().addValue(value==null? null: ((ValueSpecification) value).getBase());
	}

}
