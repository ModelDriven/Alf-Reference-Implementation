/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fumlri.uml;


public class InstanceValue extends ValueSpecification implements
		org.modeldriven.alf.uml.InstanceValue {
	public InstanceValue() {
		this(new fUML.Syntax.Classes.Kernel.InstanceValue());
	}

	public InstanceValue(fUML.Syntax.Classes.Kernel.InstanceValue base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.InstanceValue getBase() {
		return (fUML.Syntax.Classes.Kernel.InstanceValue) this.base;
	}

	public org.modeldriven.alf.uml.InstanceSpecification getInstance() {
		return (InstanceSpecification)this.wrap(this.getBase().instance);
	}

	public void setInstance(org.modeldriven.alf.uml.InstanceSpecification instance) {
		this.getBase()
				.setInstance(instance==null? null: ((InstanceSpecification) instance).getBase());
	}

}
