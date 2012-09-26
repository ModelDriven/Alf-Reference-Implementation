/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fumlri.uml;


public class LiteralString extends LiteralSpecification implements
		org.modeldriven.alf.uml.LiteralString {
	public LiteralString() {
		this(new fUML.Syntax.Classes.Kernel.LiteralString());
	}

	public LiteralString(fUML.Syntax.Classes.Kernel.LiteralString base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.LiteralString getBase() {
		return (fUML.Syntax.Classes.Kernel.LiteralString) this.base;
	}

	public String getValue() {
		return this.getBase().value;
	}

	public void setValue(String value) {
		this.getBase().setValue(value);
	}

}
