/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.impl.uml;


public class EnumerationLiteral extends InstanceSpecification implements
		org.modeldriven.alf.uml.EnumerationLiteral {
	public EnumerationLiteral() {
		this(new fUML.Syntax.Classes.Kernel.EnumerationLiteral());
	}

	public EnumerationLiteral(fUML.Syntax.Classes.Kernel.EnumerationLiteral base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.EnumerationLiteral getBase() {
		return (fUML.Syntax.Classes.Kernel.EnumerationLiteral) this.base;
	}

	public org.modeldriven.alf.uml.Enumeration getEnumeration() {
		return (Enumeration)wrap(this.getBase().enumeration);
	}
	
	public void setEnumeration(org.modeldriven.alf.uml.Enumeration enumeration) {
	    this.getBase()._setEnumeration(((Enumeration)enumeration).getBase());
	}

}
