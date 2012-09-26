/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fumlri.uml;


public class LiteralUnlimitedNatural extends LiteralSpecification implements
		org.modeldriven.alf.uml.LiteralUnlimitedNatural {
	public LiteralUnlimitedNatural() {
		this(new fUML.Syntax.Classes.Kernel.LiteralUnlimitedNatural());
	}

	public LiteralUnlimitedNatural(
			fUML.Syntax.Classes.Kernel.LiteralUnlimitedNatural base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.LiteralUnlimitedNatural getBase() {
		return (fUML.Syntax.Classes.Kernel.LiteralUnlimitedNatural) this.base;
	}

	public int getValue() {
		return this.getBase().value.naturalValue;
	}

	public void setValue(int value) {
		this.getBase().setValue(value);
	}

}
