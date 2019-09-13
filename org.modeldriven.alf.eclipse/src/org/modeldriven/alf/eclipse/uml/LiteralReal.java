/*******************************************************************************
 * Copyright 2016 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.uml;

public class LiteralReal extends LiteralSpecification implements
		org.modeldriven.alf.uml.LiteralReal {
	public LiteralReal() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createLiteralReal());
	}

	public LiteralReal(org.eclipse.uml2.uml.LiteralReal literalReal) {
		super(literalReal);
	}

	public org.eclipse.uml2.uml.LiteralReal getBase() {
		return (org.eclipse.uml2.uml.LiteralReal) this.base;
	}

	public double getValue() {
		return this.getBase().getValue();
	}

	public void setValue(double value) {
		this.getBase().setValue(value);
	}

}
