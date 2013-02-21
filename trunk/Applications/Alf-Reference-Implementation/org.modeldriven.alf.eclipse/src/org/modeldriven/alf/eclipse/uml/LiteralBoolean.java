/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.uml;

public class LiteralBoolean extends LiteralSpecification implements
		org.modeldriven.alf.uml.LiteralBoolean {
	public LiteralBoolean() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createLiteralBoolean());
	}

	public LiteralBoolean(org.eclipse.uml2.uml.LiteralBoolean base) {
		super(base);
	}

	public org.eclipse.uml2.uml.LiteralBoolean getBase() {
		return (org.eclipse.uml2.uml.LiteralBoolean) this.base;
	}

	public boolean getValue() {
		return this.getBase().isValue();
	}

	public void setValue(boolean value) {
		this.getBase().setValue(value);
	}

}
