/*******************************************************************************
 * Copyright 2016 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.impl.uml;

public class LiteralReal extends LiteralSpecification implements
		org.modeldriven.alf.uml.LiteralReal {
	public LiteralReal() {
		this(new fUML.Syntax.Classes.Kernel.LiteralReal());
	}

	public LiteralReal(fUML.Syntax.Classes.Kernel.LiteralReal base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.LiteralReal getBase() {
		return (fUML.Syntax.Classes.Kernel.LiteralReal) this.base;
	}

	public float getValue() {
		return this.getBase().value;
	}

	public void setValue(float value) {
		this.getBase().setValue(value);
	}

}
