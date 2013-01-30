/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.impl.uml;


public class LiteralNull extends LiteralSpecification implements
		org.modeldriven.alf.uml.LiteralNull {
	public LiteralNull() {
		this(new fUML.Syntax.Classes.Kernel.LiteralNull());
	}

	public LiteralNull(fUML.Syntax.Classes.Kernel.LiteralNull base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.LiteralNull getBase() {
		return (fUML.Syntax.Classes.Kernel.LiteralNull) this.base;
	}

}
