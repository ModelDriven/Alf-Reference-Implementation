/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.uml.fumlri;


public class LiteralInteger extends LiteralSpecification implements
		org.modeldriven.alf.uml.LiteralInteger {
	public LiteralInteger() {
		this(new fUML.Syntax.Classes.Kernel.LiteralInteger());
	}

	public LiteralInteger(fUML.Syntax.Classes.Kernel.LiteralInteger base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.LiteralInteger getBase() {
		return (fUML.Syntax.Classes.Kernel.LiteralInteger) this.base;
	}

	public int getValue() {
		return this.getBase().value;
	}

	public void setValue(int value) {
		this.getBase().setValue(value);
	}

}
