/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fumlri.uml;


public abstract class TypedElement extends NamedElement implements
		org.modeldriven.alf.uml.TypedElement {
	public TypedElement() {
		this(new fUML.Syntax.Classes.Kernel.TypedElement());
	}

	public TypedElement(fUML.Syntax.Classes.Kernel.TypedElement base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.TypedElement getBase() {
		return (fUML.Syntax.Classes.Kernel.TypedElement) this.base;
	}

	public org.modeldriven.alf.uml.Type getType() {
		return (Type)this.wrap(this.getBase().type);
	}

	public void setType(org.modeldriven.alf.uml.Type type) {
		this.getBase().setType(type==null? null: ((Type) type).getBase());
	}

}
